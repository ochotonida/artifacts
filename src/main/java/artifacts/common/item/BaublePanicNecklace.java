package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.ModItems;
import artifacts.client.model.ModelPanicNecklace;
import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BaublePanicNecklace extends BaubleAmulet {

    public BaublePanicNecklace() {
        super("panic_necklace", new ResourceLocation(Artifacts.MODID, "textures/entity/layer/panic_necklace.png"));
        model = new ModelPanicNecklace();
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof EntityPlayer && event.getAmount() >= 1) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.PANIC_NECKLACE) != -1) {
                event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.SPEED, 70, 1, true, false));
            }
        }
    }
}
