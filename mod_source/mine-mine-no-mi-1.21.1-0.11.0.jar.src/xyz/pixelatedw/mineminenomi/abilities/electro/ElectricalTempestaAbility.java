package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChargeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.DealDamageComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.chat.WyComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectroVisualProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalTempestaAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.5F;
	private static final float DAMAGE_BONUS = 2F;
	private static final float RANGE_BONUS = 2F;
	private static final int COOLDOWN = 160;
	private static final int CHARGE_TIME = 10;
	private static final int RANGE = 10;
	private static final int DAMAGE = 20;
	private static final int ELECLAW_STACKS = 1;
	private static final int DORIKI = 3000;
	private static final int MARTIAL_ARTS_POINTS = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ElectricalTempestaAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private LightningDischargeEntity ballEntity;

	public ElectricalTempestaAbility(AbilityCore<ElectricalTempestaAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.ballEntity = null;
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addCanUseCheck(ElectroHelper.requireEleclaw(1));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,10F);
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,0,false,false));
		if (((this.chargeComponent.getChargeTime()) % (2F)) == (0F)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.ELECTRO_CHARGING.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
		if ((this.ballEntity) == (null)){
			LightningDischargeEntity var3 = new LightningDischargeEntity((Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),var1.getYRot(),var1.getXRot());
			var1.level().addFreshEntity((Entity)(var3));
			this.ballEntity = var3;
		}else{
			float var3_1 = 0.5F;
			Vec3 var4 = var1.getLookAngle();
			double var5 = (var1.getX()) + ((var4.x) * ((double)(var3_1)));
			double var6 = (var1.getY()) + ((var4.x) * ((double)(var3_1)));
			double var7 = (var1.getZ()) + ((var4.z) * ((double)(var3_1)));
			Vec3 var8 = new Vec3(var5,var6,var7);
			float var9 = (1F) - ((this.chargeComponent.getChargeTime()) / (this.chargeComponent.getMaxChargeTime()));
			this.ballEntity.setSize((var9) * (0.1F));
			this.ballEntity.setLightningLength(2F);
			this.ballEntity.moveTo(var8.x(),var8.y(),var8.z(),var1.getYRot(),var1.getXRot());
		}
	}
	
	
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(11F,9F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(3000D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.MINK.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(5F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(7.5F))));
		NodeUnlockAction var5 = SubtractPointsAction.martialArts(5F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(7.5F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var6 = new AbilityNode[1];
		var6[0] = ((AbilityCore)(ElectricalMissileAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var6);
		var1.setUnlockRule(var4,var5);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv6_2 = new Pair[2];
			((java.lang.Object[]) (lv6_2))[0] = ImmutablePair.of((Object)("The user releases a charge of energy that deals damage to nearby enemies and knocks them back."),(Object)(null));
			Object[] lv6_3 = new Object[4];
			lv6_3[0] = WyComponent.reference((Holder)(SulongAbility.INSTANCE));
			int lv6_4 = Math.round(50F);
			String lv6_5 = "" + lv6_4 + "%";
			lv6_3[1] = MentionHelper.mentionText(lv6_5);
			int lv6_6 = Math.round((Math.abs(-1F)) * (100F));
			String lv6_7 = "" + lv6_6 + "%";
			lv6_3[2] = MentionHelper.mentionText(lv6_7);
			int lv6_8 = Math.round((Math.abs(-1F)) * (100F));
			String lv6_9 = "" + lv6_8 + "%";
			lv6_3[3] = MentionHelper.mentionText(lv6_9);
			((java.lang.Object[]) (lv6_2))[1] = ImmutablePair.of((Object)("While %s is active the cooldown of this ability is reduced by %s, the damage is increased by %s and the range of the ability is increased by %s."),lv6_3);
			Component[] lv6_10 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv6_2);
			Component[] lv6_11 = new Component[]{lv6_10[0]};
			AbilityDescriptionLine$IDescriptionLine[] lv6_12 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine$IDescriptionLine.of(lv6_10[1])};
			AbilityCore$INodeFactory[] lv6_13 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv6_14 = ElectricalTempestaAbility::createNode;
			lv6_13[0] = lv6_14;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ElectricalTempestaAbility::new)))).addDescriptionLine(lv6_11).addAdvancedDescriptionLine(lv6_12).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(lv6_13).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("electrical_tempesta","Electrical Tempesta",var0));
	}
private void endChargeEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			boolean var3 = ElectroHelper.hasSulongActive(var1);
			this.dealDamageComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_DAMAGE_BONUS);
			this.rangeComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_RANGE_BONUS);
			this.cooldownComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_COOLDOWN_BONUS);
			if (var3){
				this.dealDamageComponent.getBonusManager().addBonus(ElectroHelper.SULONG_DAMAGE_BONUS,"Sulong Damage Bonus",BonusOperation.MUL,2F);
				this.rangeComponent.getBonusManager().addBonus(ElectroHelper.SULONG_RANGE_BONUS,"Sulong Range Bonus",BonusOperation.MUL,2F);
				this.cooldownComponent.getBonusManager().addBonus(ElectroHelper.SULONG_COOLDOWN_BONUS,"Sulong Cooldown Bonus",BonusOperation.MUL,0.5F);
			}
			if ((this.ballEntity) != (null)){
				this.ballEntity.discard();
				this.ballEntity = null;
			}
			int var4 = 0;
			do{
				if ((var4) < (3)){
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.ELECTRICAL_TEMPESTA_2.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
					var4++;
					continue;
				}else{
					break;
				}
			} while (true);
			List var5 = this.rangeComponent.getTargetsInArea(var1,10F);
			AbilityDamageSource var6 = this.dealDamageComponent.getDamageSource(var1);
			IDamageSourceHandler.getHandler(var6).setUnavoidable();
			Iterator var7 = var5.iterator();
			do{
				if (var7.hasNext()){
					LivingEntity var8 = ((LivingEntity)(var7.next()));
					if (this.dealDamageComponent.hurtTarget(var1,var8,20F,var6)){
						Vec3 var9 = var1.position().subtract(var8.position()).normalize();
						var8.addEffect(new MobEffectInstance((Holder)(ModEffects.PARALYSIS),10,0,false,false,true));
						AbilityHelper.setDeltaMovement((Entity)(var8),(-(var9.x)) * (4D),1D,(-(var9.z)) * (4D));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			float var8_1 = this.rangeComponent.getRange();
			int var9_1 = 32;
			int var10 = 0;
			do{
				if ((var10) < (var9_1)){
					float var11 = (float)(WyHelper.randomWithRange(3,(int)(var8_1)));
					ElectroVisualProjectile var12 = new ElectroVisualProjectile(var1.level(),var1,(IAbility)(this),var11);
					var12.moveTo(var12.position().add(0D,(double)((-(var1.getBbHeight())) / (3F)),0D));
					var1.level().addFreshEntity((Entity)(var12));
					var10++;
					continue;
				}else{
					break;
				}
			} while (true);
			Function<AbilityData, EleclawAbility> var11_1 = (l0) -> {
			return ((EleclawAbility)(l0.getEquippedAbility(((AbilityCore)(EleclawAbility.INSTANCE.get())))));
		};
			EleclawAbility var12_1 = ((EleclawAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var11_1)).orElse(null)));
			if ((var12_1) != (null)){
				var12_1.reduceUsage(var1,1);
			}
			this.cooldownComponent.startCooldown(var1,160F);
			return;
		}
	}public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(160F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ChargeComponentTooltip> var4 = (l0) -> {
			l0.charge(10F);
		};
		ChargeComponentTooltip.generate(var2,var4);
		Consumer<RangeComponentTooltip> var5 = (l0) -> {
			l0.range(10F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var5);
		Consumer<DealDamageComponentTooltip> var6 = (l0) -> {
			l0.damage(20F);
		};
		DealDamageComponentTooltip.generate(var2,var6);
	}}