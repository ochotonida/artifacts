package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.necklace.CharmOfSinkingModel;
import artifacts.common.capability.swimhandler.SwimHandlerCapability;
import artifacts.common.config.ModConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;

public class CharmOfSinkingItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/charm_of_sinking.png");

    public CharmOfSinkingItem() {
        addListener(EventPriority.HIGH, PlayerEvent.BreakSpeed.class, this::onBreakSpeed);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity.tickCount % 20 == 0 && livingEntity.isEyeInFluid(FluidTags.WATER)) {
            damageStack(identifier, index, livingEntity, stack);
        }
    }

    public void onBreakSpeed(PlayerEvent.BreakSpeed event, LivingEntity wearer) {
        if (wearer.isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(wearer)) {
            event.setNewSpeed(event.getNewSpeed() * 5);
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (!ModConfig.server.isCosmetic(this) && slotContext.getWearer() instanceof ServerPlayerEntity) {
            slotContext.getWearer().getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                    handler -> {
                        handler.setSinking(true);
                        handler.syncSinking((ServerPlayerEntity) slotContext.getWearer());
                    }
            );
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack originalStack, ItemStack newStack) {
        if (!ModConfig.server.isCosmetic(this) && slotContext.getWearer() instanceof ServerPlayerEntity) {
            slotContext.getWearer().getCapability(SwimHandlerCapability.INSTANCE).ifPresent(
                    handler -> {
                        handler.setSinking(false);
                        handler.syncSinking((ServerPlayerEntity) slotContext.getWearer());
                    }
            );
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new CharmOfSinkingModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
