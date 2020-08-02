package artifacts.common.item;

import artifacts.Artifacts;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import top.theillusivec4.curios.api.CuriosAPI;

public class PocketPistonItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/pocket_piston_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/pocket_piston_slim.png");

    public PocketPistonItem() {
        super(new Properties(), "pocket_piston");
        MinecraftForge.EVENT_BUS.addListener(this::onLivingKnockBack);
    }

    public void onLivingKnockBack(LivingKnockBackEvent event) {
        if (event.getAttacker() instanceof LivingEntity && CuriosAPI.getCurioEquipped(this, (LivingEntity) event.getAttacker()).isPresent()) {
            event.setStrength(event.getStrength() * 2);
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return Curio.createProvider(new GloveCurio(this) {
            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getSlimTexture() {
                return TEXTURE_SLIM;
            }

            @Override
            @OnlyIn(Dist.CLIENT)
            protected ResourceLocation getTexture() {
                return TEXTURE_DEFAULT;
            }
        });
    }
}
