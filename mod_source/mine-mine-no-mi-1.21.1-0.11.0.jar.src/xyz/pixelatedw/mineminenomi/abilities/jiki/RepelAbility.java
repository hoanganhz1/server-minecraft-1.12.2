package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.PunkGibsonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Entities;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class RepelAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 100;
	private static final int HOLD_TIME = 100;
	private static final int RANGE = 20;
	private static final int PUNK_CROSS_RANGE = 3;
	private static final int PUNK_CROSS_DAMAGE = 20;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<RepelAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ProjectileComponent projectileComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private PunkGibsonAbility punkGibson;
	private HashSet<LivingEntity> punkCrossTargets;

	public RepelAbility(AbilityCore<RepelAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<PunkGibsonProjectile>) (this::createProjectile))))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.punkCrossTargets = (HashSet) (new HashSet());
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.projectileComponent,this.dealDamageComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		Iterator var3 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var1.position(),20D,TargetPredicate.EVERYTHING,Entity.class).iterator();
		do{
			if (var3.hasNext()){
				Entity var4 = ((Entity)(var3.next()));
				boolean var5 = false;
				if (var4.getType().is(ModTags$Entities.MAGNETIC)){
					var5 = true;
				}
				if (var4 instanceof LivingEntity){
					boolean var6 = ((Boolean)(EntityStatsData.get(((LivingEntity)(var4))).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isCyborg)))).orElse(Boolean.valueOf(false)))).booleanValue();
					if ((!((this.getIronArmorCoverPercentage(((LivingEntity)(var4)))) >= (0.5F))) && (!(var6))){
						if (((LivingEntity)(var4)).hasEffect((Holder)(ModEffects.PUNK_CROSS))){
							var5 = true;
							((IMobEffectInstanceMixin)(((LivingEntity)(var4)).getEffect((Holder)(ModEffects.PUNK_CROSS)))).setAmplifier(0);
							this.punkCrossTargets.add(((LivingEntity)(var4)));
						}
					}else{
						var5 = true;
					}
				}
				if (var5){
					Vec3 var6_1 = var4.position().subtract(var1.position()).normalize().scale(2D);
					AbilityHelper.setDeltaMovement(var4,var6_1.x,var6_1.y,var6_1.z);
				}
				if (var4 instanceof ItemEntity){
					ItemEntity var6_2 = ((ItemEntity)(var4));
					if ((!(var6_2.getItem().isEmpty())) && (var6_2.getItem().is(ModTags$Items.MAGNETIC))){
						Vec3 var7 = var6_2.position().subtract(var1.position()).add(0D,-1D,0D);
						double var8 = 8D;
						double var9 = 2D;
						double var10 = Math.min(var9,(-(var7.x)) / (var8));
						AbilityHelper.setDeltaMovement((Entity)(var6_2),-(var10),0.1D,-(Math.min(var9,(-(var7.z)) / (var8))));
						continue;
					}else{
						continue;
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		Iterator var4_1 = this.punkCrossTargets.iterator();
		LOOP_1:
		do{
			if (var4_1.hasNext()){
				LivingEntity var5_1 = ((LivingEntity)(var4_1.next()));
				if ((var5_1.isAlive()) && (!(var5_1.onGround()))){
					List var6_3 = this.rangeComponent.getTargetsInArea(var1,3F);
					var6_3.remove(var5_1);
					Iterator var7_1 = var6_3.iterator();
					do{
						if (var7_1.hasNext()){
							LivingEntity var8_1 = ((LivingEntity)(var7_1.next()));
							if (this.dealDamageComponent.hurtTarget(var1,var8_1,20F)){
								Vec3 var9_1 = var1.getLookAngle().normalize();
								AbilityHelper.setDeltaMovement((Entity)(var8_1),var9_1.x,0.5D,var9_1.z);
								continue;
							}else{
								continue;
							}
						}else{
							continue LOOP_1;
						}
					} while (true);
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,100F);
	}
	private PunkGibsonProjectile createProjectile(LivingEntity var1) {
		if ((this.punkGibson) != (null)){
			PunkGibsonProjectile var2 = new PunkGibsonProjectile(var1.level(),var1,this.punkGibson.getMagneticItems(),(IAbility)(this));
			return var2;
		}else{
			PunkGibsonProjectile var2_1 = new PunkGibsonProjectile(var1.level(),var1,(List)(new ArrayList()),(IAbility)(this));
			return var2_1;
		}
	}
	public float getIronArmorCoverPercentage(LivingEntity var1) {
		Iterable var2 = var1.getArmorSlots();
		int var3 = 0;
		int var4 = 0;
		Iterator var5 = var2.iterator();
		do{
			if (var5.hasNext()){
				ItemStack var6 = ((ItemStack)(var5.next()));
				if ((!(var6.isEmpty())) && (var6.is(ModTags$Items.MAGNETIC))){
					var4++;
				}
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		return ((var3) > (0)) ? (((float)(var4)) / ((float)(var3))) : (0F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[3];
			((java.lang.Object[]) (lv3_2))[0] = ImmutablePair.of((Object)("Repels all metallic objects or projectiles near the user."),(Object)(null));
			Object[] lv3_3 = new Object[]{PunkGibsonAbility.INSTANCE};
			((java.lang.Object[]) (lv3_2))[1] = ImmutablePair.of((Object)("When %s is active this ability will instead shoot the metalic arm forward like a projectile."),lv3_3);
			Object[] lv3_4 = new Object[]{ModEffects.PUNK_CROSS};
			((java.lang.Object[]) (lv3_2))[2] = ImmutablePair.of((Object)("Repeling enemies affected by %s into other enemies will deal damage and knock them back."),lv3_4);
			Component[] lv3_5 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			Component[] lv3_6 = new Component[]{lv3_5[0]};
			AbilityDescriptionLine$IDescriptionLine[] lv3_7 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F),ContinuousComponent.getTooltip(100F),RangeComponent.getTooltip(20F,RangeComponent$RangeType.AOE)};
			AbilityDescriptionLine$IDescriptionLine[] lv3_8 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			Component[] lv3_9 = new Component[]{lv3_5[1]};
			AbilityDescriptionLine$IDescriptionLine[] lv3_10 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			AbilityDescriptionLine$IDescriptionLine[] lv3_11 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			Component[] lv3_12 = new Component[]{lv3_5[2]};
			AbilityDescriptionLine$IDescriptionLine[] lv3_13 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,DealDamageComponent.getTooltip(20F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(RepelAbility::new)))).addDescriptionLine(lv3_6).addAdvancedDescriptionLine(lv3_7).addDescriptionLine(lv3_8).addDescriptionLine(lv3_9).addAdvancedDescriptionLine(lv3_10).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).addDescriptionLine(lv3_11).addDescriptionLine(lv3_12).addAdvancedDescriptionLine(lv3_13).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("repel","Repel",var0));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		Function<AbilityData, PunkGibsonAbility> var3 = (l0) -> {
			return ((PunkGibsonAbility)(l0.getEquippedAbility(((AbilityCore)(PunkGibsonAbility.INSTANCE.get())))));
		};
		this.punkGibson = ((PunkGibsonAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).orElse(null)));
		if (((((this.punkGibson) != (null)) && (this.punkGibson.isContinuous())) ? (1) : (0)) != (0)){
			this.projectileComponent.shoot(var1,4F,0.5F);
			this.punkGibson.stopItemDrops();
			LivingEntity var4 = var1;
			final LivingEntity var4_f1 = var4;
			Consumer<ContinuousComponent> var5 = (l0) -> {
			l0.stopContinuity(var4_f1);
		};
			((java.util.Optional)(this.punkGibson.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var5));
		}else{
			this.continuousComponent.triggerContinuity(var1,100F);
		}
	}
}