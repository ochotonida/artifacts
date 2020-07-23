package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.init.Items;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

public class PocketPistonItem extends ArtifactItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/pocket_piston_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/pocket_piston_slim.png");

    public PocketPistonItem() {
        super(new Properties(), "pocket_piston");
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

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    @SuppressWarnings("unused")
    public static class Events {

        @SubscribeEvent
        public static void onLivingAttack(LivingAttackEvent event) {
            if (event.getSource().getTrueSource() instanceof LivingEntity && CuriosApi.getCuriosHelper().findEquippedCurio(Items.POCKET_PISTON, (LivingEntity) event.getSource().getTrueSource()).isPresent()) {
                LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
                event.getEntityLiving().func_233627_a_(1.5F, MathHelper.sin((float) (attacker.rotationYaw * (Math.PI / 180))), -MathHelper.cos((float) (attacker.rotationYaw * (Math.PI / 180))));
            }
        }
    }
}
