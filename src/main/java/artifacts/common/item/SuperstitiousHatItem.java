package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.client.render.model.curio.SuperstitiousHatModel;
import artifacts.common.init.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;

public class SuperstitiousHatItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/superstitious_hat.png");

    public SuperstitiousHatItem() {
        super(new Properties(), "superstitious_hat");
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return Curio.createProvider(new Curio(this) {
            private Object model;

            @Override
            @OnlyIn(Dist.CLIENT)
            protected SuperstitiousHatModel getModel() {
                if (model == null) {
                    model = new SuperstitiousHatModel();
                }
                return (SuperstitiousHatModel) model;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE;
            }
        });
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onLootingLevel(LootingLevelEvent event) {
            Entity killerEntity = event.getDamageSource().getTrueSource();
            if (killerEntity instanceof LivingEntity && CuriosApi.getCuriosHelper().findEquippedCurio(Items.SUPERSTITIOUS_HAT, (LivingEntity) killerEntity).isPresent()) {
                event.setLootingLevel(event.getLootingLevel() + 1);
            }
        }
    }
}
