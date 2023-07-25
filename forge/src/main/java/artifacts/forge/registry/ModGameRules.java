package artifacts.forge.registry;

import artifacts.Artifacts;
import artifacts.forge.mixin.gamerule.BooleanValueInvoker;
import artifacts.forge.mixin.gamerule.IntegerValueInvoker;
import artifacts.forge.network.BooleanGameRuleChangedPacket;
import artifacts.forge.network.IntegerGameRuleChangedPacket;
import artifacts.forge.network.NetworkHandler;
import com.google.common.base.CaseFormat;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModGameRules {

    private static final Map<String, BooleanValue> BOOLEAN_VALUES = new HashMap<>();
    private static final Map<String, IntegerValue> INTEGER_VALUES = new HashMap<>();

    public static final BooleanValue
            ANTIDOTE_VESSEL_ENABLED = booleanValue(createName(ModItems.ANTIDOTE_VESSEL, "enabled")),
            AQUA_DASHERS_ENABLED = booleanValue(createName(ModItems.AQUA_DASHERS, "enabled")),
            BUNNY_HOPPERS_DO_CANCEL_FALL_DAMAGE = booleanValue(createName(ModItems.BUNNY_HOPPERS, "doCancelFallDamage")),
            CHARM_OF_SINKING_ENABLED = booleanValue(createName(ModItems.CHARM_OF_SINKING, "enabled")),
            CLOUD_IN_A_BOTTLE_ENABLED = booleanValue(createName(ModItems.CLOUD_IN_A_BOTTLE, "enabled")),
            ETERNAL_STEAK_ENABLED = booleanValue(createName(ModItems.ETERNAL_STEAK, "enabled")),
            EVERLASTING_BEEF_ENABLED = booleanValue(createName(ModItems.EVERLASTING_BEEF, "enabled")),
            FLAME_PENDANT_DO_GRANT_FIRE_RESISTANCE = booleanValue(createName(ModItems.FLAME_PENDANT, "doGrantFireResistance")),
            KITTY_SLIPPERS_ENABLED = booleanValue(createName(ModItems.KITTY_SLIPPERS, "enabled")),
            NIGHT_VISION_GOGGLES_ENABLED = booleanValue(createName(ModItems.NIGHT_VISION_GOGGLES, "enabled")),
            RUNNING_SHOES_DO_INCREASE_STEP_HEIGHT = booleanValue(createName(ModItems.RUNNING_SHOES, "doIncreaseStepHeight")),
            SCARF_OF_INVISIBILITY_ENABLED = booleanValue(createName(ModItems.SCARF_OF_INVISIBILITY, "enabled")),
            SHOCK_PENDANT_DO_CANCEL_LIGHTNING_DAMAGE = booleanValue(createName(ModItems.SHOCK_PENDANT, "doCancelLightningDamage")),
            SNORKEL_ENABLED = booleanValue(createName(ModItems.SNORKEL, "enabled")),
            STEADFAST_SPIKES_ENABLED = booleanValue(createName(ModItems.STEADFAST_SPIKES, "enabled")),
            UMBRELLA_IS_SHIELD = booleanValue(createName(ModItems.UMBRELLA, "isShield")),
            UMBRELLA_IS_GLIDER = booleanValue(createName(ModItems.UMBRELLA, "isGlider")),
            UNIVERSAL_ATTRACTOR_ENABLED = booleanValue(createName(ModItems.UNIVERSAL_ATTRACTOR, "enabled"));

    public static final IntegerValue
            ANTIDOTE_VESSEL_MAX_EFFECT_DURATION = integerValue(createName(ModItems.ANTIDOTE_VESSEL, "maxEffectDuration"), 5),
            BUNNY_HOPPERS_JUMP_BOOST_LEVEL = integerValue(createName(ModItems.BUNNY_HOPPERS, "jumpBoostLevel"), 2),
            CLOUD_IN_A_BOTTLE_SPRINT_JUMP_VERTICAL_VELOCITY = integerValue(createName(ModItems.CLOUD_IN_A_BOTTLE, "sprintJumpVerticalVelocity"), 50),
            CLOUD_IN_A_BOTTLE_SPRINT_JUMP_HORIZONTAL_VELOCITY = integerValue(createName(ModItems.CLOUD_IN_A_BOTTLE, "sprintJumpHorizontalVelocity"), 50),
            CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS = integerValue(createName(ModItems.CROSS_NECKLACE, "bonusInvincibilityTicks"), 20),
            CRYSTAL_HEART_HEALTH_BONUS = integerValue(createName(ModItems.CRYSTAL_HEART, "healthBonus"), 10),
            DIGGING_CLAWS_DIG_SPEED_BONUS = integerValue(createName(ModItems.DIGGING_CLAWS, "digSpeedBonus"), 32),
            DIGGING_CLAWS_TOOL_TIER = integerValue(createName(ModItems.DIGGING_CLAWS, "toolTier"), 2),
            ETERNAL_STEAK_COOLDOWN = integerValue(createName(ModItems.ETERNAL_STEAK, "cooldown"), 15),
            EVERLASTING_BEEF_COOLDOWN = integerValue(createName(ModItems.EVERLASTING_BEEF, "cooldown"), 15),
            FERAL_CLAWS_ATTACK_SPEED_BONUS = integerValue(createName(ModItems.FERAL_CLAWS, "attackSpeedBonus"), 50),
            FIRE_GAUNTLET_FIRE_DURATION = integerValue(createName(ModItems.FIRE_GAUNTLET, "fireDuration"), 8),
            FLAME_PENDANT_FIRE_DURATION = integerValue(createName(ModItems.FLAME_PENDANT, "fireDuration"), 10),
            FLAME_PENDANT_STRIKE_CHANCE = integerValue(createName(ModItems.FLAME_PENDANT, "strikeChance"), 40),
            FLIPPERS_SWIM_SPEED_BONUS = integerValue(createName(ModItems.FLIPPERS, "swimSpeedBonus"), 100),
            GOLDEN_HOOK_EXPERIENCE_BONUS = integerValue(createName(ModItems.GOLDEN_HOOK, "experienceBonus"), 75),
            HELIUM_FLAMINGO_FLIGHT_DURATION = integerValue(createName(ModItems.HELIUM_FLAMINGO, "flightDuration"), 8),
            HELIUM_FLAMINGO_RECHARGE_DURATION = integerValue(createName(ModItems.HELIUM_FLAMINGO, "rechargeDuration"), 15),
            LUCKY_SCARF_FORTUNE_BONUS = integerValue(createName(ModItems.LUCKY_SCARF, "fortuneBonus"), 1),
            NOVELTY_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER = integerValue(createName(ModItems.NOVELTY_DRINKING_HAT, "drinkingDurationMultiplier"), 30),
            NOVELTY_DRINKING_HAT_EATING_DURATION_MULTIPLIER = integerValue(createName(ModItems.NOVELTY_DRINKING_HAT, "eatingDurationMultiplier"), 60),
            OBSIDIAN_SKULL_FIRE_RESISTANCE_COOLDOWN = integerValue(createName(ModItems.OBSIDIAN_SKULL, "fireResistanceCooldown"), 60),
            OBSIDIAN_SKULL_FIRE_RESISTANCE_DURATION = integerValue(createName(ModItems.OBSIDIAN_SKULL, "fireResistanceDuration"), 30),
            PANIC_NECKLACE_SPEED_DURATION = integerValue(createName(ModItems.PANIC_NECKLACE, "speedDuration"), 8),
            PANIC_NECKLACE_SPEED_LEVEL = integerValue(createName(ModItems.PANIC_NECKLACE, "speedLevel"), 1),
            PLASTIC_DRINKING_HAT_DRINKING_DURATION_MULTIPLIER = integerValue(createName(ModItems.PLASTIC_DRINKING_HAT, "drinkingDurationMultiplier"), 30),
            PLASTIC_DRINKING_HAT_EATING_DURATION_MULTIPLIER = integerValue(createName(ModItems.PLASTIC_DRINKING_HAT, "eatingDurationMultiplier"), 60),
            POCKET_PISTON_KNOCKBACK_STRENGTH = integerValue(createName(ModItems.POCKET_PISTON, "knockbackStrength"), 15),
            POWER_GLOVE_ATTACK_DAMAGE_BONUS = integerValue(createName(ModItems.POWER_GLOVE, "attackDamageBonus"), 4),
            RUNNING_SHOES_SPEED_BONUS = integerValue(createName(ModItems.RUNNING_SHOES, "speedBonus"), 40),
            SHOCK_PENDANT_STRIKE_CHANCE = integerValue(createName(ModItems.SHOCK_PENDANT, "strikeChance"), 25),
            SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS = integerValue(createName(ModItems.SUPERSTITIOUS_HAT, "lootingLevelBonus"), 1),
            THORN_PENDANT_MAX_DAMAGE = integerValue(createName(ModItems.THORN_PENDANT, "maxDamage"), 6),
            THORN_PENDANT_MIN_DAMAGE = integerValue(createName(ModItems.THORN_PENDANT, "minDamage"), 2),
            THORN_PENDANT_STRIKE_CHANCE = integerValue(createName(ModItems.THORN_PENDANT, "strikeChance"), 50),
            VAMPIRIC_GLOVE_ABSORPTION_RATIO = integerValue(createName(ModItems.VAMPIRIC_GLOVE, "absorptionRatio"), 20),
            VAMPIRIC_GLOVE_MAX_HEALING_PER_HIT = integerValue(createName(ModItems.VAMPIRIC_GLOVE, "maxHealingPerHit"), 6),
            VILLAGER_HAT_REPUTATION_BONUS = integerValue(createName(ModItems.VILLAGER_HAT, "reputationBonus"), 100),
            WHOOPEE_CUSHION_FART_CHANCE = integerValue(createName(ModItems.WHOOPEE_CUSHION, "fartChance"), 12);

    private static String createName(RegistrySupplier<? extends Item> item, String name) {
        return String.format("%s.%s.%s",
                Artifacts.MOD_ID,
                CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, item.getId().getPath()),
                name
        );
    }

    private static BooleanValue booleanValue(String name) {
        BooleanValue result = new BooleanValue();
        GameRules.Type<GameRules.BooleanValue> type = BooleanValueInvoker.invokeCreate(true, (server, value) -> {
            result.update(value.get());
            NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new BooleanGameRuleChangedPacket(name, value.get()));
        });
        result.key = GameRules.register(name, GameRules.Category.PLAYER, type);
        BOOLEAN_VALUES.put(name, result);
        return result;
    }

    private static IntegerValue integerValue(String name, int defaultValue) {
        return integerValue(name, defaultValue, (server, value) -> { });
    }

    private static IntegerValue integerValue(String name, int defaultValue, BiConsumer<MinecraftServer, GameRules.IntegerValue> onChanged) {
        IntegerValue result = new IntegerValue();
        result.update(defaultValue);
        GameRules.Type<GameRules.IntegerValue> type = IntegerValueInvoker.invokeCreate(defaultValue, (server, value) -> {
            result.update(value.get());
            NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new IntegerGameRuleChangedPacket(name, value.get()));
            onChanged.accept(server, value);
        });
        result.key = GameRules.register(name, GameRules.Category.PLAYER, type);

        INTEGER_VALUES.put(name, result);
        return result;
    }

    public static void updateValue(String key, boolean value) {
        BOOLEAN_VALUES.get(key).update(value);
    }

    public static void updateValue(String key, int value) {
        INTEGER_VALUES.get(key).update(value);
    }

    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            BOOLEAN_VALUES.forEach((key, value) -> NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new BooleanGameRuleChangedPacket(key, value.get())));
            INTEGER_VALUES.forEach((key, value) -> NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new IntegerGameRuleChangedPacket(key, value.get())));
        }
    }

    public static void onServerStarted(ServerStartedEvent event) {
        BOOLEAN_VALUES.values().forEach(value -> value.update(event.getServer()));
        INTEGER_VALUES.values().forEach(value -> value.update(event.getServer()));
    }

    public static class BooleanValue implements Supplier<Boolean> {

        private Boolean value = true;
        private GameRules.Key<GameRules.BooleanValue> key;

        public Boolean get() {
            return value;
        }

        private void update(MinecraftServer server) {
            update(server.getGameRules().getBoolean(key));
        }

        private void update(boolean value) {
            this.value = value;
        }
    }

    public static class IntegerValue implements Supplier<Integer> {

        private Integer value;
        private GameRules.Key<GameRules.IntegerValue> key;

        public Integer get() {
            return value;
        }

        private void update(MinecraftServer server) {
            update(server.getGameRules().getInt(key));
        }

        private void update(int value) {
            this.value = value;
        }
    }
}
