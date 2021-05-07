package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PocketPistonItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/pocket_piston_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/pocket_piston_slim.png");

    public PocketPistonItem() {
        addListener(LivingAttackEvent.class, this::onLivingAttack, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    public void onLivingAttack(LivingAttackEvent event) {
        LivingEntity attacker = DamageSourceHelper.getAttacker(event.getSource());
        float knockbackBonus = (float) (double) ModConfig.server.pocketPiston.knockbackBonus.get();
        // noinspection ConstantConditions
        event.getEntityLiving().knockback(knockbackBonus, MathHelper.sin((float) (attacker.yRot * (Math.PI / 180))), -MathHelper.cos((float) (attacker.yRot * (Math.PI / 180))));
        damageEquippedStacks(attacker);
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.PISTON_EXTEND, 1, 1);
    }

    @Override
    protected ResourceLocation getSlimTexture() {
        return TEXTURE_SLIM;
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE_DEFAULT;
    }
}
