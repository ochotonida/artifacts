package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.ScarfModel;
import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import top.theillusivec4.curios.api.CuriosAPI;

import java.util.List;

public class LuckyScarfItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/lucky_scarf.png");

    public LuckyScarfItem() {
        super(new Properties(), "lucky_scarf");
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ScarfModel getModel() {
                if (model == null) {
                    model = new ScarfModel();
                }
                return (ScarfModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }

    public static class FortuneBonusModifier extends LootModifier {

        protected FortuneBonusModifier(ILootCondition[] conditions) {
            super(conditions);
        }

        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            ItemStack tool = context.get(LootParameters.TOOL);

            if (tool == null || tool.getOrCreateTag().getBoolean("HasAppliedFortuneBonus")) {
                return generatedLoot;
            }

            Entity entity = context.get(LootParameters.THIS_ENTITY);
            BlockState blockState = context.get(LootParameters.BLOCK_STATE);
            if (blockState == null || !(entity instanceof LivingEntity) || !CuriosAPI.getCurioEquipped(artifacts.common.init.Items.LUCKY_SCARF, (LivingEntity) entity).isPresent()) {
                return generatedLoot;
            }

            ItemStack fakeTool = tool.isEmpty() ? new ItemStack(Items.BARRIER) : tool.copy();
            fakeTool.getOrCreateTag().putBoolean("HasAppliedFortuneBonus", true);

            fakeTool.addEnchantment(Enchantments.FORTUNE, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, fakeTool) + 1);
            LootContext.Builder builder = new LootContext.Builder(context);
            builder.withParameter(LootParameters.TOOL, fakeTool);
            LootContext newContext = builder.build(LootParameterSets.BLOCK);
            LootTable lootTable = context.getWorld().getServer().getLootTableManager().getLootTableFromLocation(blockState.getBlock().getLootTable());
            return lootTable.generate(newContext);
        }

        public static class Serializer extends GlobalLootModifierSerializer<FortuneBonusModifier> {

            @Override
            public FortuneBonusModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
                return new FortuneBonusModifier(conditions);
            }
        }
    }
}
