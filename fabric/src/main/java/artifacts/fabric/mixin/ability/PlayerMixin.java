package artifacts.fabric.mixin.ability;

import artifacts.component.SwimData;
import artifacts.fabric.extensions.PlayerExtensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin implements PlayerExtensions {

    @Unique
    private SwimData artifacts$swimData = new SwimData();

    @Override
    public SwimData artifacts$getSwimData() {
        return artifacts$swimData;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void saveSwimData(ValueOutput output, CallbackInfo ci) {
        output.store("artifacts:swim_data", SwimData.CODEC, artifacts$swimData);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readSwimData(ValueInput input, CallbackInfo ci) {
        artifacts$swimData = input.read("artifacts:swim_data", SwimData.CODEC).orElseGet(SwimData::new);
    }
}
