package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class AttributeHelper {
	// Fields
	public static final ResourceLocation EFFECT_MOVEMENT_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.movement_speed");
	public static final ResourceLocation EFFECT_SWIM_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.swim_speed");
	public static final ResourceLocation EFFECT_ATTACK_REACH_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.attack_reach");
	public static final ResourceLocation EFFECT_ATTACK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.attack_damage");
	public static final ResourceLocation EFFECT_ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.attack_speed");
	public static final ResourceLocation EFFECT_KNOCKBACK_RESISTANCE_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.knockback_resistance");
	public static final ResourceLocation EFFECT_JUMP_HEIGHT_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.jump_height");
	public static final ResourceLocation EFFECT_REGEN_RATE_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.regen_rate");
	public static final ResourceLocation EFFECT_TOUGHNESS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.toughness");
	public static final ResourceLocation EFFECT_MINING_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.mining_speed");
	public static final ResourceLocation EFFECT_TIME_PROGRESSION_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","effect.time_progression");
	public static final ResourceLocation MORPH_HEALTH_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.health");
	public static final ResourceLocation MORPH_MOVEMENT_SPEED_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.movement_speed");
	public static final ResourceLocation MORPH_ARMOR_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.armor");
	public static final ResourceLocation MORPH_ARMOR_TOUGHNESS_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.armor_toughness");
	public static final ResourceLocation MORPH_STRENGTH_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.strength");
	public static final ResourceLocation MORPH_ATTACK_SPEED_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.attack_speed");
	public static final ResourceLocation MORPH_ATTACK_REACH_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.attack_reach");
	public static final ResourceLocation MORPH_STEP_HEIGHT_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.step_height");
	public static final ResourceLocation MORPH_KNOCKBACK_RESISTANCE_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.knockback_resistance");
	public static final ResourceLocation MORPH_FALL_RESISTANCE_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.fall_resistance");
	public static final ResourceLocation MORPH_TOUGHNESS_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.toughness");
	public static final ResourceLocation MORPH_JUMP_BOOST_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.jump_height");
	public static final ResourceLocation MORPH_REGEN_RATE_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.regen_rate");
	public static final ResourceLocation MORPH_SWIM_SPEED_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.swim_speed");
	public static final ResourceLocation MORPH_GRAVITY_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.gravity");
	public static final ResourceLocation MORPH_MINING_SPEED_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.mining_speed");
	public static final ResourceLocation MORPH_FRICTION_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.friction");
	public static final ResourceLocation MORPH_GCD_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","morph.gcd");
	public static final ResourceLocation PASSIVE_HEALTH_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","passive.health");
	public static final ResourceLocation PASSIVE_TOUGHNESS_UUID = ResourceLocation.fromNamespaceAndPath("mineminenomi","passive.toughness");
	public static final ResourceLocation PASSIVE_SWORDSMAN_ATTACK_BONUS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","passive.swordsman.attack");
	@Deprecated() public static final ResourceLocation ATTACK_DAMAGE_MODIFIER = Item.BASE_ATTACK_DAMAGE_ID;
	public static final ResourceLocation SPEED_MODIFIER_SPRINTING_UUID = ResourceLocation.withDefaultNamespace("sprinting");

	public static void updateHPAttribute(LivingEntity var0) {
		if (((var0) != (null)) && ((!(var0.level().isClientSide)) && (ServerConfig.isExtraHeartsEnabled()))){
			AttributeInstance var1 = var0.getAttribute(Attributes.MAX_HEALTH);
			if ((var1) == (null)){
				return;
			}else{
				double var2 = ((Double)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
				double var3 = ((var2) / ((double)(ServerConfig.getHealthGainFrequency()))) - (var1.getBaseValue());
				var1.removeModifier(PASSIVE_HEALTH_UUID);
				if ((var3) > (0D)){
					var1.addPermanentModifier(new AttributeModifier(PASSIVE_HEALTH_UUID,var3,AttributeModifier.Operation.ADD_VALUE));
				}
				if (((double)(var0.getHealth())) > (var1.getValue())){
					var0.setHealth((float)(var1.getValue()));
				}
				if (var0 instanceof ServerPlayer){
					ServerPlayer var4 = ((ServerPlayer)(var0));
					var4.connection.send((Packet)(new ClientboundSetHealthPacket(var0.getHealth(),var4.getFoodData().getFoodLevel(),var4.getFoodData().getSaturationLevel())));
				}else{
					return;
				}
			}
		}
	}
	public static void updateToughnessAttribute(LivingEntity var0) {
		if (((var0) != (null)) && (!(var0.level().isClientSide))){
			AttributeInstance var1 = var0.getAttribute(ModAttributes.TOUGHNESS);
			if ((var1) == (null)){
				return;
			}else{
				double var2 = ((Double)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
				AttributeModifier var3 = new AttributeModifier(PASSIVE_TOUGHNESS_UUID,((0.08D) * ((var2) / ((double)(ServerConfig.getDorikiLimit())))) * (100D),AttributeModifier.Operation.ADD_VALUE);
				var1.removeModifier(PASSIVE_TOUGHNESS_UUID);
				var1.addTransientModifier(var3);
			}
		}
	}
	public static double getAttackRangeDistance(LivingEntity var0, double var1) {
		AttributeInstance var2 = var0.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
		return ((var2) != (null)) ? ((var1) + (var2.getValue())) : (var1);
	}
	public static double getSquaredAttackRangeDistance(LivingEntity var0, double var1) {
		double var2 = getAttackRangeDistance(var0,Math.sqrt(var1));
		return (var2) * (var2);
	}
}