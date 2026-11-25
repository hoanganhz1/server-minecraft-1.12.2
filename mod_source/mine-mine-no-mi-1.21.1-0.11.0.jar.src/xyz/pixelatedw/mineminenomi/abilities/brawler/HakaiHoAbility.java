package xyz.pixelatedw.mineminenomi.abilities.brawler;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ExplosionComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class HakaiHoAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 240F;
	private static final int RANGE = 2;
	private static final int DAMAGE = 20;
	private static final int NODE_POINTS_MA = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HakaiHoAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;

	public HakaiHoAbility(AbilityCore<HakaiHoAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
	}
	
	public boolean isParallel() {
		return true;
	}
	public float getPunchCooldown() {
		return 240F;
	}
	public float getPunchDamage() {
		return 20F;
	}
	public int getUseLimit() {
		return 1;
	}
	
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-2F,4F));
		boolean var2 = ((Boolean)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isBrawler)))).orElse(Boolean.valueOf(false)))).booleanValue();
		TrainingPointsUnlockCondition var3 = TrainingPointsUnlockCondition.martialArts(5F);
		NodeUnlockAction var4 = SubtractPointsAction.martialArts(5F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		if (!(var2)){
			AbilityNode[] var5 = new AbilityNode[1];
			var5[0] = ((AbilityCore)(GenkotsuMeteorAbility.INSTANCE.get())).getNode(var0);
			var1.addPrerequisites(var5);
		}
		var1.setUnlockRule(var3,var4);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("The user punches with enough force to create a small explosion damaging all nearby enemies."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			SourceType[] lv4_4 = new SourceType[]{SourceType.FIST};
			AbilityCore$INodeFactory[] lv4_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_6 = HakaiHoAbility::createNode;
			lv4_5[0] = lv4_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HakaiHoAbility::new)))).addDescriptionLine(lv4_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv4_4).setNodeFactories(lv4_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("hakai_ho","Hakai Ho",var0));
	}
public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		List var4 = this.rangeComponent.getTargetsInArea(var2,2F);
		var4.remove(var1);
		var4.remove(var2);
		Vec3 var5 = var1.getLookAngle().multiply(5D,0D,5D);
		AbilityDamageSource var6 = this.dealDamageComponent.getDamageSource(var1);
		var6.addType(SourceType.INDIRECT);
		Iterator var7 = var4.iterator();
		do{
			if (var7.hasNext()){
				LivingEntity var8 = ((LivingEntity)(var7.next()));
				if (this.dealDamageComponent.hurtTarget(var1,var8,10F,var6)){
					var8.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),60,0,false,false));
					AbilityHelper.setDeltaMovement((Entity)(var8),var8.getDeltaMovement().add(var5.x,0.2D,var5.z));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		AbilityExplosion var8_1 = new AbilityExplosion((Entity)(var1),(IAbility)(this),var2.getX(),var2.getY(),var2.getZ(),2F);
		var8_1.setExplosionSound(true);
		var8_1.setDamageOwner(false);
		var8_1.setDestroyBlocks(false);
		var8_1.setFireAfterExplosion(false);
		var8_1.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
		var8_1.setDamageEntities(false);
		var8_1.explode();
		Predicate<IAbility> var9 = (l0) -> {
			return (l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get())))) && (((PoolComponent)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get()))).get())).containsPool(ModAbilityPools.GUARD_ABILITY));
		};
		AbilityHelper.disableAbilities(var2,100,var9);
		return true;
	}public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<RangeComponentTooltip> var3 = (l0) -> {
			l0.range(2F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var3);
		Consumer<ExplosionComponentTooltip> var4 = (l0) -> {
			l0.damage(10F);
		};
		ExplosionComponentTooltip.generate(var2,var4);
	}}