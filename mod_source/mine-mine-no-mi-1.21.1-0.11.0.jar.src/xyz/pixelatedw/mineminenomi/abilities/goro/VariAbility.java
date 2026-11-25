package xyz.pixelatedw.mineminenomi.abilities.goro;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class VariAbility extends Ability {
	// Fields
	static final Component ONE_MILLION_V_VARI_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"1_million_vari","1 Million Vari"));
	static final Component TWENTY_MILLION_V_VARI_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"20_million_vari","20 Million Vari"));
	static final Component ONE_HUNDRED_MILLION_V_VARI_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"100_million_vari","100 Million Vari"));
	static final Component TWO_HUNDRED_MILLION_V_VARI_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"200_million_vari","200 Million Vari"));
	private static final int CHARGE_TIME = 20;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<VariAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final AltModeComponent<VariAbility$Mode> altModeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private final Interval dischargeInterval;

	public VariAbility(AbilityCore<VariAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this),true).addStartEvent(100,(ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),VariAbility$Mode.class,VariAbility$Mode.ONE_MILLION_V).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::onAltModeChange)))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.dischargeInterval = new Interval(5);
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.altModeComponent,this.dealDamageComponent,this.animationComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,20F);
	}
	private void onChargeStart(LivingEntity var1, IAbility var2) {
		this.dischargeInterval.restartIntervalToZero();
		this.animationComponent.start(var1,ModAnimations.POINT_ARMS);
	}
	private void onChargeTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide()){
			return;
		}else{
			if (this.dischargeInterval.canTick()){
				Vec3 var3 = VectorHelper.getRelativeOffset(var1.position(),var1.yBodyRot,new Vec3(0D,1.15D,0.8D));
				float var4 = 1F;
				if (((MorphInfo)(ModMorphs.VOLT_AMARU.get())).isActive(var1)){
					var4 = (var4) + (0.25F);
				}
				LightningDischargeEntity var5 = new LightningDischargeEntity((Entity)(var1),var3.x,var3.y,var3.z,var1.getYRot(),var1.getXRot());
				var5.setAliveTicks(5);
				var5.setLightningLength((0.2F) * (var4));
				var5.setColor(Color.WHITE);
				var5.setOutlineColor((MineMineNoMi.hasBlueGoroPackSelected()) ? (ElThorAbility.BLUE_THUNDER) : (ElThorAbility.YELLOW_THUNDER));
				var5.setDetails(20);
				var5.setDensity(40);
				var5.setSize((0.2F) * (var4));
				var5.setSkipSegments(1);
				var1.level().addFreshEntity((Entity)(var5));
			}
			return;
		}
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide()){
			return;
		}else{
			float var3 = 1F;
			if (((MorphInfo)(ModMorphs.VOLT_AMARU.get())).isActive(var1)){
				var3 = (var3) + (0.25F);
			}
			AbilityDamageSource var4 = this.dealDamageComponent.getDamageSource(var1);
			IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var4);
			var5.addAbilityPiercing(0.75F,this.getCore());
			var5.setUnavoidable();
			float var6 = (12F) * (this.chargeComponent.getChargePercentage());
			Vec3 var7 = VectorHelper.getRelativeOffset(var1.position(),var1.yBodyRot,new Vec3(0.5D,1.15D,0.8D));
			LightningDischargeEntity var8 = new LightningDischargeEntity((Entity)(var1),var7.x,var7.y,var7.z,var1.getYRot(),var1.getXRot());
			var8.setAliveTicks(15);
			var8.setLightningLength(((var6) / (2F)) * (var3));
			var8.setColor(Color.WHITE);
			var8.setOutlineColor((MineMineNoMi.hasBlueGoroPackSelected()) ? (ElThorAbility.BLUE_THUNDER) : (ElThorAbility.YELLOW_THUNDER));
			var8.setDetails(20);
			var8.setDensity(40);
			var8.setSize(((var6) / (2F)) * (var3));
			var8.setSkipSegments(1);
			var1.level().addFreshEntity((Entity)(var8));
			List var9 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var7,(double)(var6),TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
			var9.remove(var1);
			VariAbility$Mode var10 = ((VariAbility$Mode)(this.altModeComponent.getCurrentMode()));
			Iterator var11 = var9.iterator();
			do{
				if (var11.hasNext()){
					LivingEntity var12 = ((LivingEntity)(var11.next()));
					float var13 = (float)(((double)((var10.getDamage()) * (this.chargeComponent.getChargePercentage()))) * ((1D) - ((var7.distanceTo(var12.position())) / ((double)((var6) * (3F))))));
					if (this.dealDamageComponent.hurtTarget(var1,var12,(var13) * (var3),var4)){
						var12.addEffect(new MobEffectInstance((Holder)(ModEffects.PARALYSIS),10,0,false,false,true));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			this.animationComponent.stop(var1);
			this.cooldownComponent.startCooldown(var1,var10.getCooldown());
			return;
		}
	}
	private boolean onAltModeChange(LivingEntity var1, IAbility var2, VariAbility$Mode var3) {
		this.setDisplayName(var3.getDisplayName());
		return true;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv9_2 = new Pair[]{ImmutablePair.of((Object)("A basic move where the user discharges variable amounts of electricity"),(Object)(null))};
			Component[] lv9_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv9_2);
			AbilityDescriptionLine$IDescriptionLine[] lv9_4 = new AbilityDescriptionLine$IDescriptionLine[4];
			lv9_4[0] = AbilityDescriptionLine.NEW_LINE;
			AbilityDescriptionLine$IDescriptionLine lv9_5 = (l2_0, l2_1) -> {
				return ONE_MILLION_V_VARI_NAME.copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv9_4[1] = lv9_5;
			AbilityDescriptionLine$IDescriptionLine lv9_6 = (l2_0, l2_1) -> {
				return CooldownComponent.getTooltip(VariAbility$Mode.ONE_MILLION_V.cooldown).expand(l2_0,l2_1);
			};
			lv9_4[2] = lv9_6;
			lv9_4[3] = ChargeComponent.getTooltip(20F);
			AbilityDescriptionLine$IDescriptionLine[] lv9_7 = new AbilityDescriptionLine$IDescriptionLine[4];
			lv9_7[0] = AbilityDescriptionLine.NEW_LINE;
			AbilityDescriptionLine$IDescriptionLine lv9_8 = (l2_0, l2_1) -> {
				return TWENTY_MILLION_V_VARI_NAME.copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv9_7[1] = lv9_8;
			AbilityDescriptionLine$IDescriptionLine lv9_9 = (l2_0, l2_1) -> {
				return CooldownComponent.getTooltip(VariAbility$Mode.TWENTY_MILLION_V.cooldown).expand(l2_0,l2_1);
			};
			lv9_7[2] = lv9_9;
			lv9_7[3] = ChargeComponent.getTooltip(20F);
			AbilityDescriptionLine$IDescriptionLine[] lv9_10 = new AbilityDescriptionLine$IDescriptionLine[4];
			lv9_10[0] = AbilityDescriptionLine.NEW_LINE;
			AbilityDescriptionLine$IDescriptionLine lv9_11 = (l2_0, l2_1) -> {
				return ONE_HUNDRED_MILLION_V_VARI_NAME.copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv9_10[1] = lv9_11;
			AbilityDescriptionLine$IDescriptionLine lv9_12 = (l2_0, l2_1) -> {
				return CooldownComponent.getTooltip(VariAbility$Mode.ONE_HUNDRED_MILLION_V.cooldown).expand(l2_0,l2_1);
			};
			lv9_10[2] = lv9_12;
			lv9_10[3] = ChargeComponent.getTooltip(20F);
			AbilityDescriptionLine$IDescriptionLine[] lv9_13 = new AbilityDescriptionLine$IDescriptionLine[4];
			lv9_13[0] = AbilityDescriptionLine.NEW_LINE;
			AbilityDescriptionLine$IDescriptionLine lv9_14 = (l2_0, l2_1) -> {
				return TWO_HUNDRED_MILLION_V_VARI_NAME.copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv9_13[1] = lv9_14;
			AbilityDescriptionLine$IDescriptionLine lv9_15 = (l2_0, l2_1) -> {
				return CooldownComponent.getTooltip(VariAbility$Mode.TWO_HUNDRED_MILLION_V.cooldown).expand(l2_0,l2_1);
			};
			lv9_13[2] = lv9_15;
			lv9_13[3] = ChargeComponent.getTooltip(20F);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(VariAbility::new)))).addDescriptionLine(lv9_3).addAdvancedDescriptionLine(lv9_4).addAdvancedDescriptionLine(lv9_7).addAdvancedDescriptionLine(lv9_10).addAdvancedDescriptionLine(lv9_13).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHTNING).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("vari","Vari",var0));
	}
}