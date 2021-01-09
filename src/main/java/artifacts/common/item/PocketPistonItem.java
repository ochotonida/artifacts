package artifacts.common.item;

import artifacts.Artifacts;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class PocketPistonItem extends GloveItem {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/pocket_piston_default.png");
    private static final ResourceLocation TEXTURE_SLIM = new ResourceLocation(Artifacts.MODID, "textures/entity/curio/pocket_piston_slim.png");

    public PocketPistonItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onLivingAttack);
    }

    public void onLivingAttack(LivingAttackEvent event) {
        if (event.getSource().getTrueSource() instanceof LivingEntity && isEquippedBy((LivingEntity) event.getSource().getTrueSource())) {
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            event.getEntityLiving().applyKnockback(1.5F, MathHelper.sin((float) (attacker.rotationYaw * (Math.PI / 180))), -MathHelper.cos((float) (attacker.rotationYaw * (Math.PI / 180))));
        }
    }

    @Override
    protected SoundEvent getEquipSound() {
        return SoundEvents.BLOCK_PISTON_EXTEND;
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
