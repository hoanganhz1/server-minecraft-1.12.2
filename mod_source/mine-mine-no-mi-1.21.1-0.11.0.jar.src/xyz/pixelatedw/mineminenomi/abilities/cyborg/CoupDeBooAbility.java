package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ColaUsageComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.MobEffectComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CoupDeBooAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 200F;
	private static final int COLA_REQUIRED = 20;
	private static final int RANGE = 5;
	private static final int POISON_EFFECT_TIME = 200;
	private static final int TECHNOLOGY_POINTS = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<CoupDeBooAbility>> INSTANCE;
	private final DamageTakenComponent damageTakenComponent;
	private final RangeComponent rangeComponent;
	private final ColaUsageComponent colaUsageComponent;
	private final MobEffectComponent effectsComponent;
	private boolean hasFallDamage;

	public CoupDeBooAbility(AbilityCore<CoupDeBooAbility> var1) {
		super((AbilityCore)(var1));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.colaUsageComponent = new ColaUsageComponent((IAbility)(this));
		this.effectsComponent = new MobEffectComponent((IAbility)(this));
		this.hasFallDamage = true;
		AbilityComponent[] var2 = new AbilityComponent[]{this.damageTakenComponent,this.rangeComponent,this.colaUsageComponent,this.effectsComponent};
		this.addComponents(var2);
		this.addCanUseCheck(ColaUsageComponent.hasEnoughCola(20));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.hasFallDamage = false;
		if ((((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))))) == (null)){
			return;
		}else{
			Vec3 var3 = var1.getLookAngle().multiply(2D,1.5D,2D);
			AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,(var3.y) + (3.5D),var3.z);
			this.colaUsageComponent.consumeCola(var1,20);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.COUP_DE_BOO.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			Iterator var4 = this.rangeComponent.getTargetsInArea(var1,5F).iterator();
			do{
				if (var4.hasNext()){
					LivingEntity var5 = ((LivingEntity)(var4.next()));
					this.effectsComponent.applyEffect(var5,MobEffects.POISON,200,1);
					continue;
				}else{
					break;
				}
			} while (true);
			this.cooldownComponent.startCooldown(var1,200F);
			return;
		}
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((!(this.hasFallDamage)) && (var3.is(DamageTypes.FALL))){
			this.hasFallDamage = true;
			return 0F;
		}else{
			return var4;
		}
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(15F,0F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.technology(5F);
		NodeUnlockAction var3 = SubtractPointsAction.technology(5F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[2];
		var4[0] = ((AbilityCore)(FreshFireAbility.INSTANCE.get())).getNode(var0);
		var4[1] = ((AbilityCore)(CoupDeVentAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("Launches the user into the sky and poisons all nearby enemies on launch."),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			SourceType[] lv5_4 = new SourceType[]{SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv5_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv5_6 = CoupDeBooAbility::createNode;
			lv5_5[0] = lv5_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(CoupDeBooAbility::new)))).addDescriptionLine(lv5_3).setSourceType(lv5_4).setNodeFactories(lv5_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("coup_de_boo","Coup De Boo",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(200F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ColaUsageComponentTooltip> var4 = (l0) -> {
			l0.cola(20F);
		};
		ColaUsageComponentTooltip.generate(var2,var4);
		Consumer<RangeComponentTooltip> var5 = (l0) -> {
			l0.range(5F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var5);
		Consumer<MobEffectComponentTooltip> var6 = (l0) -> {
			l0.addEffect(MobEffects.POISON,200);
		};
		MobEffectComponentTooltip.generate(var2,var6);
	}
}