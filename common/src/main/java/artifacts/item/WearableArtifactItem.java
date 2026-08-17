package artifacts.item;

import artifacts.Artifacts;
import artifacts.component.ability.AttributeModifiers;
import artifacts.component.ability.EnchantmentLevelModifiers;
import artifacts.component.ability.EntityCondition;
import artifacts.component.ability.SimpleAbility;
import artifacts.component.ability.mobeffect.EquipmentMobEffects;
import artifacts.component.ability.mobeffect.MobEffectProvider;
import artifacts.config.value.Value;
import artifacts.integration.ModCompat;
import artifacts.registry.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// TODO (>1.21.1 to avoid breaking RAR-Compat) migrate the missing dependency check/tooltip to an ability, delete this class
public class WearableArtifactItem extends Item {

    public WearableArtifactItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        if (Artifacts.CONFIG.client.showTooltips.get()
                && !ModCompat.CURIOS.isLoaded()
                && !ModCompat.TRINKETS.isLoaded()
                && !ModCompat.ACCESSORIES.isLoaded()
        ) {
            list.add(Component.translatable("%s.tooltip.missing_dependency".formatted(Artifacts.MOD_ID)).withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        } else {
            super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        }
    }

    public static class Builder {

        private final String itemName;
        private final Item.Properties properties = new Item.Properties();
        private final List<AttributeModifiers.Entry> attributes = new ArrayList<>();
        private final List<EnchantmentLevelModifiers.Entry> enchantments = new ArrayList<>();

        public Builder(String itemName) {
            this.itemName = itemName;
            equipSound(SoundEvents.ARMOR_EQUIP_GENERIC);
        }

        public Builder equipSound(SoundEvent equipSound) {
            return equipSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(equipSound));
        }

        public Builder equipSound(Holder<SoundEvent> equipSound) {
            properties.component(ModDataComponents.EQUIP_SOUND.get(), equipSound.value());
            return this;
        }

        public Builder addAttributeModifier(Holder<Attribute> attribute, Value<Double> amount, AttributeModifier.Operation operation) {
            return addAttributeModifier(attribute, amount, operation, true);
        }

        public Builder addAttributeModifier(Holder<Attribute> attribute, Value<Double> amount, AttributeModifier.Operation operation, boolean ignoreCooldown) {
            attributes.add(new AttributeModifiers.Entry(attribute, amount, operation,
                    Artifacts.id(itemName + '/' + attribute.unwrapKey().orElseThrow().location().getPath()), ignoreCooldown)
            );
            return this;
        }

        public Builder mobEffect(Holder<MobEffect> effect, Value<Integer> level, Value<Integer> duration, EntityCondition condition) {
            return component(ModDataComponents.MOB_EFFECTS.get(), new EquipmentMobEffects(List.of(
                    new EquipmentMobEffects.Entry(new MobEffectProvider(effect, level, duration, Value.of(false), Value.of(true), condition))
            )));
        }

        public Builder increasesEnchantment(ResourceKey<Enchantment> enchantment, Value<Integer> amount) {
            enchantments.add(new EnchantmentLevelModifiers.Entry(enchantment, amount));
            return this;
        }

        public Builder component(DataComponentType<Unit> type) {
            return component(type, Unit.INSTANCE);
        }

        public Builder component(DataComponentType<SimpleAbility> type, Value<Boolean> enabled) {
            return component(type, new SimpleAbility(enabled));
        }

        public <T> Builder component(DataComponentType<T> type, T component) {
            properties.component(type, component);
            return this;
        }

        public Builder properties(Consumer<Properties> consumer) {
            consumer.accept(this.properties);
            return this;
        }

        public WearableArtifactItem build() {
            properties.stacksTo(1).rarity(Rarity.RARE).fireResistant();
            if (!attributes.isEmpty()) {
                properties.component(ModDataComponents.ATTRIBUTE_MODIFIERS.get(), new AttributeModifiers(attributes));
            }
            if (!enchantments.isEmpty()) {
                properties.component(ModDataComponents.ENCHANTMENT_LEVEL_MODIFIERS.get(), new EnchantmentLevelModifiers(enchantments));
            }
            return new WearableArtifactItem(properties);
        }
    }
}
