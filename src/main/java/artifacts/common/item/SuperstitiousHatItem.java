package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.head.SuperstitiousHatModel;
import artifacts.common.config.ModConfig;
import artifacts.common.util.DamageSourceHelper;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class SuperstitiousHatItem extends CurioItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/superstitious_hat.png");

    public SuperstitiousHatItem() {
        addListener(LivingDeathEvent.class, this::onLivingDeath, event -> DamageSourceHelper.getAttacker(event.getSource()));
    }

    private void onLivingDeath(LivingDeathEvent event, LivingEntity wearer) {
        damageEquippedStacks(wearer);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected BipedModel<LivingEntity> createModel() {
        return new SuperstitiousHatModel();
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public int getLootingBonus(String identifier, LivingEntity livingEntity, ItemStack curio, int index) {
        return ModConfig.server.isCosmetic(this) ? 0 : ModConfig.server.superstitiousHat.lootingBonus.get();
    }
}
