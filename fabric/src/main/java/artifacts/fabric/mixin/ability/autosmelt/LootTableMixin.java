package artifacts.fabric.mixin.ability.autosmelt;

import artifacts.event.ArtifactHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LootTable.class)
public class LootTableMixin {

    @ModifyReturnValue(method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;", at = @At("RETURN"))
    public ObjectArrayList<ItemStack> modifyBlockDrops(ObjectArrayList<ItemStack> original, LootContext context) {
        Vec3 origin = context.getOptional(LootContextParams.ORIGIN);
        if (origin != null) {
            MutableInt experience = new MutableInt(0);
            ObjectArrayList<ItemStack> result = new ObjectArrayList<>(original.size());
            for (ItemStack stack : original) {
                result.add(ArtifactHooks.applySmeltOresAbility(
                        stack,
                        context.getOptional(LootContextParams.THIS_ENTITY),
                        context.getOptional(LootContextParams.BLOCK_STATE),
                        experience::add
                ));
            }
            ExperienceOrb.award(context.getLevel(), origin, experience.intValue());
            return result;
        }
        return original;
    }
}
