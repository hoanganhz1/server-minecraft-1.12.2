package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.HaoshokuHakiInfusionAbility;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler$NuDamageValue;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.mixins.ILivingEntityMixin;

public class AttributesHandler {
	// Fields
	private static final float MAX_PROTECTION_THRESHOLD = 0.95F;
	private static final float PROTECTION_ARMOR_PERCENTAGE = 0.4F;
	private static final float PROTECTION_TOUGHNESS_PERCENTAGE = 0.6F;
	public static final float PLOT_ARMOR_REDUCTION = 0.4F;
	private static final int JUMP_TICK_DELAY = 1;
	private static final int MAX_JUMP_TICKS = 9;

	public static float applyDamageBonuses(DamageSource var0, LivingEntity var1, IDamageSourceHandler$NuDamageValue var2, float var3) {
		IDamageSourceHandler var4 = IDamageSourceHandler.getHandler(var0);
		ItemStack var5 = ItemStack.EMPTY;
		LivingEntity var6 = null;
		Entity var7 = var0.getEntity();
		Entity var8 = var0.getDirectEntity();
		int var9 = 0;
		boolean var10 = false;
		if (var8 instanceof LivingEntity){
			LivingEntity var11 = ((LivingEntity)(var8));
			var6 = var11;
			var5 = var6.getMainHandItem();
			var9 = ((var5.isEmpty()) || (var5.is(ModTags$Items.HARDENING_DAMAGE_BONUS))) ? (1) : (0);
			var10 = var5.is(ModTags$Items.IMBUING_DAMAGE_BONUS);
		}
		if (var7 instanceof LivingEntity){
			LivingEntity var11_1 = ((LivingEntity)(var7));
			var6 = var11_1;
			var5 = var6.getMainHandItem();
		}
		float var11_2 = 0F;
		float var12 = 0F;
		if ((var6) == (null)){
			return var3;
		}else{
			boolean var13 = var0.is(ModDamageTypes.ABILITY);
			int var14 = ((var0.is(DamageTypes.PLAYER_ATTACK)) || (var0.is(DamageTypes.MOB_ATTACK))) ? (1) : (0);
			if (var4.hasType(SourceType.INDIRECT)){
				var14 = 0;
			}
			AttributeInstance var15 = var6.getAttribute(Attributes.ATTACK_DAMAGE);
			AttributeInstance var16 = var6.getAttribute(ModAttributes.PUNCH_DAMAGE);
			float var17 = ItemsHelper.getItemDamage(var6.getMainHandItem());
			int var18 = ((var4.hasType(SourceType.SLASH)) && (ItemsHelper.isSword(var5))) ? (1) : (0);
			int var19 = ((var4.hasType(SourceType.BLUNT)) && (ItemsHelper.isBlunt(var5))) ? (1) : (0);
			int var20 = ((var4.hasType(SourceType.FIST)) && (var5.isEmpty())) ? (1) : (0);
			if (var13){
				if (((var18) == (0)) && ((var19) == (0))){
					if (((var20) != (0)) && ((var16) != (null))){
						var11_2 = (float)(var16.getValue());
					}
				}else{
					var11_2 = var17;
				}
			}else{
				if (((var14) != (0)) && (((var20) != (0)) && ((var16) != (null)))){
					var11_2 = (float)(var16.getValue());
				}
			}
			boolean var21 = HakiHelper.hasHardeningActive(var6,false,false);
			boolean var22 = HakiHelper.hasImbuingActive(var6,false,false);
			boolean var23 = HakiHelper.hasAdvancedBusoActive(var6);
			boolean var24 = var4.hasHakiNature(SourceHakiNature.SPECIAL);
			int var25 = ((((var9) != (0)) || ((var24) || (var4.hasHakiNature(SourceHakiNature.HARDENING)))) && (var21)) ? (1) : (0);
			int var26 = (((var10) || ((var24) || (var4.hasHakiNature(SourceHakiNature.IMBUING)))) && (var22)) ? (1) : (0);
			int var27 = ((((var9) != (0)) || ((var10) || ((var24) || ((var4.hasHakiNature(SourceHakiNature.HARDENING)) || (var4.hasHakiNature(SourceHakiNature.IMBUING)))))) && (var23)) ? (1) : (0);
			int var28 = ((!(var4.hasHakiNature(SourceHakiNature.UNKNOWN))) && (HakiHelper.hasInfusionActive(var6))) ? (1) : (0);
			if ((var27) != (0)){
				var12 = (float)(((double)(var12)) + (HakiHelper.getAdvancedBusoshokuHakiDamageBoost(var6,var3)));
			}else{
				if (((var25) == (0)) && ((var26) == (0))){

				}else{
					var12 = (var12) + ((float)(HakiHelper.getBasicBusoshokuHakiDamageBoost(var6,var3)));
				}
			}
			if ((var28) != (0)){
				var12 = (float)(((double)(var12)) + (HaoshokuHakiInfusionAbility.getDamageBoost(var6,var3)));
			}
			float var29 = (var11_2) + ((var12) * (0.4F));
			if (var6 instanceof Player){
				Player var30 = ((Player)(var6));
				if ((var0.is(DamageTypes.PLAYER_ATTACK)) && (!((!(var2.hasType(SourceType.FIST))) && (!(var2.hasType(SourceType.PHYSICAL)))))){
					float var31 = var30.getAttackStrengthScale(0.5F);
					var29 = (var29) * ((0.2F) + (((var31) * (var31)) * (0.8F)));
				}
			}
			float var30_1 = ((Double)(EntityStatsData.get(var6).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDamageMultiplier)))).orElse(Double.valueOf(1D)))).floatValue();
			var3 = (var3) + (var29);
			var3 = (var3) * (var30_1);
			return var3;
		}
	}
	public static float getDamageAfterProtection(DamageSource var0, LivingEntity var1) {
		IDamageSourceHandler var2 = IDamageSourceHandler.getHandler(var0);
		AttributeInstance var3 = var1.getAttribute(ModAttributes.TOUGHNESS);
		AttributeInstance var4 = var1.getAttribute(Attributes.ARMOR);
		double var5 = 0D;
		double var6 = 0D;
		float var7 = 0F;
		if ((var3) != (null)){
			var5 = (var3.getValue()) / (((RangedAttribute)(var3.getAttribute().value())).getMaxValue());
		}
		if ((var4) != (null)){
			var6 = (var4.getValue()) / (((RangedAttribute)(var4.getAttribute().value())).getMaxValue());
		}
		float var8 = 0F;
		float var10 = 0.0F;
		if (var0.is(DamageTypeTags.BYPASSES_ARMOR)){
			var10 = 1F;
		}else{
			var10 = var2.getGlobalPiercing();
		}
		double var11 = (var5) * (0.6000000238418579D);
		double var12 = (var6) * (0.4000000059604645D);
		Iterator var13 = var2.getDamageValues().iterator();
		do{
			if (var13.hasNext()){
				Map.Entry var14 = ((Map.Entry)(var13.next()));
				float var15 = Mth.clamp((var2.getAbilityPiercing(((IDamageSourceHandler$NuDamageValue)(var14.getKey())).ability())) + (var10),0F,1F);
				float var16 = ((Float)(var14.getValue())).floatValue();
				Entity var17 = var0.getDirectEntity();
				if (var17 instanceof Player){
					Player var18 = ((Player)(var17));
					if ((var0.is(DamageTypes.PLAYER_ATTACK)) && (!(((!(((IDamageSourceHandler$NuDamageValue)(var14.getKey())).hasType(SourceType.FIST))) && (!(((IDamageSourceHandler$NuDamageValue)(var14.getKey())).hasType(SourceType.PHYSICAL)))) || (((IDamageSourceHandler$NuDamageValue)(var14.getKey())).hasType(SourceType.VANILLA))))){
						float var19 = var18.getAttackStrengthScale(0.5F);
						var16 = (var16) * ((0.2F) + (((var19) * (var19)) * (0.8F)));
					}
				}
				if ((((IDamageSourceHandler$NuDamageValue)(var14.getKey())).ability()) != (null)){
					var16 = (var16) * (0.4F);
				}
				float var18_1 = applyDamageBonuses(var0,var1,((IDamageSourceHandler$NuDamageValue)(var14.getKey())),var16);
				var7 = (var7) + (((var18_1) - ((var18_1) * (0.72F))) * ((1F) - (var15)));
				var18_1 = (var18_1) - ((float)(((double)(var18_1)) * (Math.min((var11) + ((var12) * ((1D) - ((double)(var15)))),0.949999988079071D))));
				var8 = (var8) + (var18_1);
				continue;
			}else{
				break;
			}
		} while (true);
		if (var1 instanceof Player){
			((Player)(var1)).hurtArmor(var0,var7);
		}
		return var8;
	}
	public static void handleHurtMath(DamageSource var0, LivingEntity var1, float var2) {
		var2 = Math.max(var2,0.1F);
		float var3 = Math.max((var2) - (var1.getAbsorptionAmount()),0F);
		var1.setAbsorptionAmount((var1.getAbsorptionAmount()) - ((var2) - (var3)));
		float var4 = (var2) - (var3);
		if (((var4) > (0F)) && ((var4) < (3.4F))){
			if ((var1 instanceof Player) && (FGCommand.SHOW_DEBUG_DAMAGE)){
				WyDebug.debug(String.format("Absorbsion Damaged by %f",new Object[]{Float.valueOf(var4)}));
			}
			Entity var5 = var0.getEntity();
			if (var5 instanceof ServerPlayer){
				((ServerPlayer)(var5)).awardStat(Stats.DAMAGE_DEALT_ABSORBED,Math.round((var4) * (10F)));
			}
		}
		if ((var0.getEntity() instanceof Player) && (FGCommand.SHOW_DEBUG_DAMAGE)){
			WyDebug.debug(String.format("%s got hit with %f",new Object[]{var1.getName().getString(),Float.valueOf(var3)}));
		}
		if ((var3) != (0F)){
			var1.getCombatTracker().recordDamage(var0,var3);
			var1.setHealth((var1.getHealth()) - (var3));
			var1.setAbsorptionAmount((var1.getAbsorptionAmount()) - (var3));
			var1.gameEvent((Holder)(GameEvent.ENTITY_DAMAGE));
		}
	}
	public static float applyBreakSpeed(Player var0, float var1) {
		AttributeInstance var2 = var0.getAttribute(ModAttributes.MINING_SPEED);
		if ((var2) != (null)){
			return (float)(((double)(var1)) * (var2.getValue()));
		}else{
			return var1;
		}
	}
	public static float applyFallResistance(LivingEntity var0, float var1) {
		AttributeInstance var2 = var0.getAttribute(ModAttributes.FALL_RESISTANCE);
		if ((var2) != (null)){
			return (var1) - ((float)(var2.getValue()));
		}else{
			return var1;
		}
	}
	private static double lambertWMinusOne(double var0) {
		double var1 = 0.0;
		if ((var0) < (-0.3D)){
			var1 = (-1D) - (Math.sqrt((2D) * (((2.718281828459045D) * (var0)) + (1D))));
		}else{
			double var2 = Math.log(-(var0));
			var1 = (var2) - (Math.log(-(var2)));
		}
		int var2_1 = 0;
		do{
			if ((var2_1) < (8)){
				double var3 = Math.exp(var1);
				double var4 = ((var1) * (var3)) - (var0);
				var1 = (var1) - ((var4) / (((var3) * ((var1) + (1D))) - ((((var1) + (2D)) * (var4)) / (((2D) * (var1)) + (2D)))));
				var2_1++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var1;
	}
	public static void applyJumpForce(LivingEntity var0) {
		EntityStatsData var1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			if (var0.onGround()){
				var1.setJumpTicks(-1);
				return;
			}else{
				if (!(var1.isJumping())){
					var1.setJumpTicks(9);
					return;
				}else{
					if ((var1.getJumpTicks()) < (0)){
						var1.alterJumpTicks(1);
						return;
					}else{
						if ((var1.getJumpTicks()) >= (9)){
							return;
						}else{
							AttributeInstance var2 = var0.getAttribute(ModAttributes.JUMP_HEIGHT);
							if ((var2) == (null)){
								return;
							}else{
								double var3 = (var2.getValue()) - (1D);
								if ((var3) <= (0D)){
									return;
								}else{
									double var4 = var0.getAttributeValue(Attributes.GRAVITY);
									double var5 = (double)(((ILivingEntityMixin)(var0)).invokeGetJumpPower());
									double var6 = 0.98D;
									double var7 = (1D) - (var6);
									double var8 = ((var6) * (var4)) / (var7);
									double var9 = (var5) + (var8);
									int var10 = ((int)(Math.floor((Math.log((var8) / (var9))) / (Math.log(var6))))) + (1);
									double var11 = ((((var9) * ((1D) - (Math.pow(var6,(double)(var10))))) / (var7)) - ((var8) * ((double)(var10)))) + (var3);
									double var12 = ((1D) - (Math.pow(var6,9D))) / ((var7) * (var7));
									double var13 = (var12) / (9D);
									double var14 = (Math.pow(var6,2D)) * (var9);
									double var15 = (var8) / (Math.log(var6));
									double var16 = (var11) - ((((((var9) / (var7)) - ((var8) * (2D))) - ((var8) / (var7))) - ((var13) * (var14))) - ((var15) * (Math.log(var8))));
									double var17 = ((var13) / (var15)) * (Math.exp((var16) / (var15)));
									int var18 = (int)(Math.ceil((2D) + ((Math.log((var8) / (((var15) / (var13)) * (lambertWMinusOne(var17))))) / (Math.log(var6)))));
									int var19 = ((var18) - (1)) - (1);
									double var20 = (((var9) * ((1D) - (Math.pow(var6,(double)(var18))))) / (var7)) - ((var8) * ((double)(var18)));
									double var21 = (((var11) - (var20)) / ((var12) - (((9D) * (Math.pow(var6,(double)(var19)))) / (var7)))) * (Math.pow(var6,(double)(var1.getJumpTicks())));
									double var22 = (((double)(var0.getYRot())) * (3.141592653589793D)) / (180D);
									double var23 = ((var0.getDeltaMovement().y) + (var21)) * (0.2D);
									double var24 = (var0.isSprinting()) ? ((-(Math.sin(var22))) * (var23)) : (0D);
									var0.push(var24,var21,(var0.isSprinting()) ? ((Math.cos(var22)) * (var23)) : (0D));
									var1.alterJumpTicks(1);
									return;
								}
							}
						}
					}
				}
			}
		}
	}
	public static void applyJumpHeight(LivingEntity var0) {
		AttributeInstance var1 = var0.getAttribute(ModAttributes.JUMP_HEIGHT);
		if (((var1) != (null)) && ((var1.getValue()) <= (0D))){
			AbilityHelper.setDeltaMovement((Entity)(var0),0D,0D,0D);
		}
	}
	public static float applyExtraHeal(LivingEntity var0, float var1) {
		AttributeInstance var2 = var0.getAttribute(ModAttributes.REGEN_RATE);
		if ((var2) != (null)){
			float var3 = (float)(var2.getValue());
			if ((var3) != (1F)){
				return (var1) * (var3);
			}
		}
		return var1;
	}
}