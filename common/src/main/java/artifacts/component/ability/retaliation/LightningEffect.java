package artifacts.component.ability.retaliation;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LightningEffect extends RetaliationEffect {

    public static final Codec<LightningEffect> CODEC
            = ActivationParams.CODEC.codec().xmap(LightningEffect::new, LightningEffect::activationParams);

    public static final StreamCodec<ByteBuf, LightningEffect> STREAM_CODEC
            = ActivationParams.STREAM_CODEC.map(LightningEffect::new, LightningEffect::activationParams);

    public LightningEffect(ActivationParams activationParams) {
        super("lightning", activationParams);
    }

    @Override
    protected boolean applyEffect(LivingEntity target, LivingEntity attacker) {
        if (attacker.level().canSeeSky(BlockPos.containing(attacker.position()))) {
            LightningBolt lightningBolt = EntityTypes.LIGHTNING_BOLT.create(attacker.level(), EntitySpawnReason.TRIGGERED);
            if (lightningBolt != null) {
                lightningBolt.setPos(Vec3.atBottomCenterOf(attacker.blockPosition()));
                lightningBolt.setCause(target instanceof ServerPlayer player ? player : null);
                attacker.level().addFreshEntity(lightningBolt);
                return true;
            }
        }
        return false;
    }
}
