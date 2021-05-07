package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.necklace.ScarfModel;
import artifacts.common.config.ModConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.BlockEvent;

public class LuckyScarfItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/lucky_scarf.png");

    public LuckyScarfItem() {
        addListener(BlockEvent.BreakEvent.class, this::onBreakBlock, BlockEvent.BreakEvent::getPlayer);
    }

    public void onBreakBlock(BlockEvent.BreakEvent event) {
        damageEquippedStacks(event.getPlayer());
    }

    @Override
    public int getFortuneBonus(String identifier, LivingEntity livingEntity, ItemStack curio, int index) {
        return ModConfig.server.isCosmetic(this) ? 0 : ModConfig.server.luckyScarf.fortuneBonus.get();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new ScarfModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}
