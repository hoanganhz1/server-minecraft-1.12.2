package xyz.pixelatedw.mineminenomi.abilities.beta;

import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class BetaCoatingAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 800;
	private static final int MIN_COOLDOWN = 100;
	private static final int MAX_COOLDOWN = 400;
	private static final ResourceLocation SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.beta_coating.speed");
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BetaCoatingAbility>> INSTANCE;
	private static final List<ResourceKey<DamageType>> EXPLOSIVE_SOURCES;
	private static final List<SourceElement> EXPLOSIVE_ELEMENTS;
	private static final AttributeModifier SPEED_MULTIPLIER;
	private static final AbilityOverlay OVERLAY;
	private final ContinuousComponent continuousComponent;
	private final SkinOverlayComponent skinOverlayComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final ChangeStatsComponent changeStatsComponent;
	private final ExplosionComponent explosionComponent;
	private boolean exploded;

	public BetaCoatingAbility(AbilityCore<BetaCoatingAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::damageTakenEvent))));
		this.changeStatsComponent = new ChangeStatsComponent((IAbility)(this));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		this.exploded = false;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.skinOverlayComponent,this.damageTakenComponent,this.changeStatsComponent,this.explosionComponent};
		this.addComponents(var2);
		this.changeStatsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,SPEED_MULTIPLIER);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,800F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.exploded = false;
		this.skinOverlayComponent.showAll(var1);
		this.changeStatsComponent.applyModifiers(var1);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (((this.continuousComponent.getContinueTime()) % (10F)) == (0F)){
			this.checkIfOnFire(var1);
		}
		this.climbingMovement(var1);
	}
	private void climbingMovement(LivingEntity var1) {
		boolean var2 = false;
		AABB var3 = var1.getBoundingBox().inflate(0.25D,0.25D,0.25D);
		int var4 = Mth.floor(var3.minX);
		int var5 = Mth.floor(var3.minY);
		int var6 = Mth.floor(var3.minZ);
		BlockPos.MutableBlockPos var7 = new BlockPos.MutableBlockPos();
		int var8 = var5;
		LOOP_2:
		do{
			if (((double)(var8)) < (var3.maxY)){
				int var9 = var4;
				LOOP_1:
				do{
					if (((double)(var9)) < (var3.maxX)){
						int var10 = var6;
						do{
							if (((double)(var10)) < (var3.maxZ)){
								var7.set(var9,var8,var10);
								if (Block.canSupportCenter((LevelReader)(var1.level()),(BlockPos)(var7),Direction.UP)){
									var2 = true;
									break;
								}else{
									var10++;
									continue;
								}
							}else{
								break;
							}
						} while (true);
						var9++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var8++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		if (((var1.horizontalCollision) && (!(var1.verticalCollision))) || ((var1.isCrouching()) && (var2))){
			double var9_1 = Math.min(0.1D,(var1.getLookAngle().y) * (0.5D));
			if (var1.isCrouching()){
				AbilityHelper.setDeltaMovement((Entity)(var1),var1.getDeltaMovement().x,0D,var1.getDeltaMovement().z);
			}else{
				AbilityHelper.setDeltaMovement((Entity)(var1),var1.getDeltaMovement().x,var9_1,var1.getDeltaMovement().z);
			}
			var1.fallDistance = 0F;
		}
	}
	private void checkIfOnFire(LivingEntity var1) {
		boolean var2 = false;
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) != (null)){
			Iterator var4 = var3.getEquippedAbilities().iterator();
			do{
				if (var4.hasNext()){
					IAbility var5 = ((IAbility)(var4.next()));
					if ((!(var5.getCore().getSourceElement().equals(SourceElement.FIRE))) && (!(var5.getCore().getSourceElement().equals(SourceElement.MAGMA)))){
						continue;
					}else{
						boolean var6 = ((Boolean)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Boolean>)(ContinuousComponent::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue();
						if (var6){
							var2 = true;
							break;
						}else{
							var6 = ((Boolean)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))).map((Function)(((Function<ChargeComponent, Boolean>)(ChargeComponent::isCharging)))).orElse(Boolean.valueOf(false)))).booleanValue();
							if (var6){
								var2 = true;
								break;
							}else{
								continue;
							}
						}
					}
				}else{
					break;
				}
			} while (true);
		}
		if ((!(var1.isOnFire())) && (!(var2))){

		}else{
			this.exploded = true;
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.hideAll(var1);
		this.changeStatsComponent.removeModifiers(var1);
		float var3 = 0F;
		if (this.exploded){
			var3 = 600F;
			var1.clearFire();
			AbilityExplosion var4 = this.explosionComponent.createExplosion((Entity)(var1),var1,var1.getX(),var1.getY(),var1.getZ(),6F);
			var4.setExplosionSound(true);
			var4.setDamageOwner(true);
			var4.setDestroyBlocks(true);
			var4.setFireAfterExplosion(true);
			var4.setStaticDamage(100F);
			var4.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION6));
			var4.setDamageEntities(true);
			var4.explode();
		}
		float var4_1 = (Math.min(100F,(this.continuousComponent.getContinueTime()) / (2F))) + (var3);
		this.cooldownComponent.startCooldown(var1,var4_1);
	}
	private float damageTakenEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if (!(this.isContinuous())){
			return var4;
		}else{
			IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var3);
			if ((!(var5.isUnavoidable())) && ((!(var3.is(DamageTypes.MAGIC))) && (!(var3.is(DamageTypes.INDIRECT_MAGIC))))){
				Iterator var6 = EXPLOSIVE_ELEMENTS.iterator();
				do{
					if (var6.hasNext()){
						if (var5.hasElement(((SourceElement)(var6.next())))){
							this.exploded = true;
							this.continuousComponent.stopContinuity(var1);
							return var4;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				Iterator var7 = EXPLOSIVE_SOURCES.iterator();
				do{
					if (var7.hasNext()){
						if (var3.is(((ResourceKey)(var7.next())))){
							this.exploded = true;
							this.continuousComponent.stopContinuity(var1);
							return var4;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if ((var3.getDirectEntity() instanceof LivingEntity) && (var3.getDirectEntity().isOnFire())){
					return var4;
				}else{
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BETA_COATING.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
					return 0F;
				}
			}else{
				return var4;
			}
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Covers the user in a thick mucus coat, which makes them immune to almost all attacks, but extremely vulnerable to fire."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F,400F),ContinuousComponent.getTooltip(800F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BetaCoatingAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("beta_coating","Beta Coating",var0));
		ResourceKey[] var1 = new ResourceKey[]{DamageTypes.EXPLOSION,DamageTypes.IN_FIRE,DamageTypes.LIGHTNING_BOLT,DamageTypes.ON_FIRE,DamageTypes.LAVA};
		EXPLOSIVE_SOURCES = (List) (Arrays.asList(var1));
		SourceElement[] var2 = new SourceElement[]{SourceElement.FIRE,SourceElement.LIGHTNING,SourceElement.EXPLOSION};
		EXPLOSIVE_ELEMENTS = (List) (Arrays.asList(var2));
		SPEED_MULTIPLIER = new AttributeModifier(SPEED_MODIFIER_ID,-0.25D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		OVERLAY = new AbilityOverlay$Builder().setTexture(ModResources.BETA_COATING).setColor(WyHelper.hexToRGB("#FFFFFFA6")).build();
	}
}