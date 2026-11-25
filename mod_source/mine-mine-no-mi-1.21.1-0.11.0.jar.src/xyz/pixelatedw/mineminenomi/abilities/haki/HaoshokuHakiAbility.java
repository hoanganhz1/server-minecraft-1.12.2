package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.effects.IWeakenedByHaoshokuEffect;
import xyz.pixelatedw.mineminenomi.api.events.ability.HakiKnockoutEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.ability.HakiKnockoutEvent$Pre;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.EffectsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig$HaoshokuUnlockLogic;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.haki.HaoshokuHakiParticleEffect$Details;

public class HaoshokuHakiAbility extends Ability {
	// Fields
	private static final ResourceLocation[] ICONS;
	public static final Supplier<AbilityCore<HaoshokuHakiAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private HaoshokuHakiParticleEffect$Details particleDetails;
	private LightningDischargeEntity discharge;
	private Color color;
	private int burstSize;
	private int radius;
	private int unconsciousTimer;
	private int haoMastery;
	private final Interval effectInterval;

	public HaoshokuHakiAbility(AbilityCore<HaoshokuHakiAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.particleDetails = new HaoshokuHakiParticleEffect$Details();
		this.color = new Color(16711680);
		this.burstSize = 0;
		this.radius = 0;
		this.unconsciousTimer = 0;
		this.haoMastery = 0;
		this.effectInterval = new Interval(20);
		this.setOGCD();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canEnableHaki))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.HAKI_RELEASE_SFX.get())),SoundSource.PLAYERS,4F,1F);
		float var3 = (((Float)(HakiData.get(var1).map((Function)(((Function<HakiData, Float>)(HakiData::getTotalHakiExp)))).orElse(Float.valueOf(0F)))).floatValue()) / (100F);
		if ((var3) <= (1F)){
			this.radius = 10;
			this.unconsciousTimer = 20;
			this.burstSize = 3;
			this.haoMastery = 0;
		}else{
			if (((var3) > (1F)) && ((var3) <= (1.75F))){
				this.radius = 25;
				this.unconsciousTimer = 40;
				this.burstSize = 5;
				this.haoMastery = 1;
			}else{
				if ((var3) > (1.75F)){
					this.radius = 40;
					this.unconsciousTimer = 50;
					this.burstSize = 10;
					this.haoMastery = 2;
				}
			}
		}
		this.color = new Color(HakiHelper.getHaoshokuColour(var1));
		this.discharge = new LightningDischargeEntity((Entity)(var1),var1.getX(),(var1.getY()) + (1.5D),var1.getZ(),var1.getYRot(),var1.getXRot());
		this.discharge.setAliveTicks(-1);
		this.discharge.setUpdateRate(8);
		this.discharge.setLightningLength((float)((this.radius) * (2)));
		this.discharge.setColor(new Color(0,0,0,100));
		this.discharge.setOutlineColor(this.color);
		this.discharge.setDetails(16);
		int var4 = ((this.haoMastery) == (2)) ? (32) : (16);
		this.discharge.setDensity(var4);
		this.discharge.setSize(1F);
		this.discharge.setSkipSegments(1);
		if ((this.haoMastery) == (0)){
			this.discharge.setSplit();
		}
		var1.level().addFreshEntity((Entity)(this.discharge));
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			int var3 = (int)(this.continuousComponent.getContinueTime());
			HakiData var4 = ((HakiData)(((java.util.Optional)(HakiData.get(var1))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				if (this.effectInterval.canTick()){
					Iterator var5 = var1.getActiveEffects().iterator();
					do{
						if (var5.hasNext()){
							MobEffectInstance var6 = ((MobEffectInstance)(var5.next()));
							Holder var7 = var6.getEffect();
							if (var7 instanceof IWeakenedByHaoshokuEffect){
								IWeakenedByHaoshokuEffect var8 = ((IWeakenedByHaoshokuEffect)(var7));
								((IMobEffectInstanceMixin)(var6)).setDuration((var6.getDuration()) - (var8.haoshokuReductionTicks()));
								WyHelper.sendApplyEffectToAllNearby(var1,var1.position(),100,var6);
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
				if (((var3) % (5)) == (0)){
					this.particleDetails.setSize(this.burstSize);
					this.particleDetails.setColor(this.color.getRGB());
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HAOSHOKU_HAKI.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),(ParticleEffect$Details)(this.particleDetails));
					this.discharge.setPos(var1.getX(),(var1.getY()) + (1D),var1.getZ());
				}
				if (((var3) % (10)) == (0)){
					var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.HAKI_RELEASE_SFX.get())),SoundSource.PLAYERS,3F,(0.5F) + (var1.getRandom().nextFloat()));
				}
				if (((var3) % (20)) == (0)){
					var4.alterHakiOveruse(200);
					this.knockoutNearbyEnemies(var1);
					if (((!(HakiHelper.canEnableHaki(var1))) ? (1) : (0)) != (0)){
						this.continuousComponent.stopContinuity(var1);
						return;
					}
				}
				if (((var3) % (100)) == (0)){
					if ((this.radius) == (25)){
						this.destroyNearbyBlocks(var1,1F,3);
					}else{
						if ((this.radius) > (25)){
							this.destroyNearbyBlocks(var1,2F,5);
						}else{
							return;
						}
					}
				}
				return;
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		float var3 = (this.continuousComponent.getContinueTime()) / (2F);
		this.cooldownComponent.startCooldown(var1,var3);
		if ((this.discharge) != (null)){
			this.discharge.setAliveTicks(30);
		}
	}
	private void knockoutNearbyEnemies(LivingEntity var1) {
		List var2 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var1.blockPosition(),(double)(this.radius),TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
		float var3 = (((Float)(HakiData.get(var1).map((Function)(((Function<HakiData, Float>)(HakiData::getTotalHakiExp)))).orElse(Float.valueOf(0F)))).floatValue()) / (100F);
		Iterator var4 = var2.iterator();
		do{
			if (var4.hasNext()){
				LivingEntity var5 = ((LivingEntity)(var4.next()));
				if ((((double)((((Float)(HakiData.get(var5).map((Function)(((Function<HakiData, Float>)(HakiData::getTotalHakiExp)))).orElse(Float.valueOf(0F)))).floatValue()) / (100F))) + (0.5D)) >= ((double)(var3))){
					continue;
				}else{
					if ((this.haoMastery) > (0)){
						HakiKnockoutEvent$Post var6 = null;
						if (var5.hasEffect((Holder)(ModEffects.UNCONSCIOUS))){
							MobEffectInstance var7 = var5.getEffect((Holder)(ModEffects.UNCONSCIOUS));
							int var8 = (var7.getDuration()) + (this.unconsciousTimer);
							if (((HakiKnockoutEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(new HakiKnockoutEvent$Pre(var5,var1,true,var8))))).isCanceled()){
								return;
							}else{
								EffectsHelper.updateEffectDuration(var5,var7,var8);
								var6 = new HakiKnockoutEvent$Post(var5,var1,true,var8);
							}
						}else{
							MobEffectInstance var7_1 = new MobEffectInstance((Holder)(ModEffects.UNCONSCIOUS),this.unconsciousTimer,1,false,false);
							if (((HakiKnockoutEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(new HakiKnockoutEvent$Pre(var5,var1,false,this.unconsciousTimer))))).isCanceled()){
								return;
							}else{
								var5.addEffect(var7_1);
								var6 = new HakiKnockoutEvent$Post(var5,var1,false,this.unconsciousTimer);
							}
						}
						NeoForge.EVENT_BUS.post((Event)(var6));
						continue;
					}else{
						var5.addEffect(new MobEffectInstance(MobEffects.CONFUSION,200,0));
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
	}
	private void destroyNearbyBlocks(LivingEntity var1, float var2, int var3) {
		BlockPos.MutableBlockPos var4 = new BlockPos.MutableBlockPos();
		float var5 = (float)(var1.getX());
		float var6 = (float)(var1.getZ());
		int var7 = 0;
		int var8 = (int)(((float)(-(var3))) * (var2));
		LOOP_1:
		do{
			if (((double)(var8)) <= ((3.141592653589793D) * ((double)(var2)))){
				int var9 = 0;
				do{
					if (((float)(var9)) <= (((float)(var3)) * (var2))){
						if (((++(var7)) % (5)) != (0)){

						}else{
							float var10 = ((float)(var9)) / (var2);
							float var11 = ((float)(var8)) / (var2);
							int var12 = 0;
							do{
								if ((var12) <= (20)){
									float var13 = (float)((((double)(var5)) + ((((double)(var12)) * (Math.cos((double)(var10)))) * (Math.cos((double)(var11))))) + ((double)(WyHelper.randomWithRange(-3,3))));
									float var14 = (float)(((var1.getY()) - (2D)) + (((double)(var12)) * (Math.sin((double)(var10)))));
									var4.set((double)(var13),(double)(var14),(double)((float)((((double)(var6)) + ((((double)(var12)) * (Math.cos((double)(var10)))) * (Math.sin((double)(var11))))) + ((double)(WyHelper.randomWithRange(-3,3))))));
									BlockState var15 = var1.level().getBlockState((BlockPos)(var4));
									if ((!(var15.isAir())) && ((!(var4.closerThan((Vec3i)(var1.blockPosition()),3D))) && (NuWorld.setBlockState((Entity)(var1),(BlockPos)(var4),Blocks.AIR.defaultBlockState(),3,DefaultProtectionRules.CORE_FOLIAGE_ORE)))){
										var1.level().levelEvent(2001,(BlockPos)(var4),Block.getId(var15));
									}
									var12 = (var12) + (5);
									continue;
								}else{
									break;
								}
							} while (true);
						}
						var9++;
						continue;
					}else{
						break;
					}
				} while (true);
				var8++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
	}
	public ResourceLocation getIcon(LivingEntity var1) {
		float var2 = (((Float)(HakiData.get(var1).map((Function)(((Function<HakiData, Float>)(HakiData::getTotalHakiExp)))).orElse(Float.valueOf(0F)))).floatValue()) / (100F);
		int var3 = 0;
		if (((var2) > (1F)) && ((var2) <= (1.75F))){
			var3 = 1;
		}else{
			if ((var2) > (1.75F)){
				var3 = 2;
			}
		}
		return ICONS[var3];
	}
	static  {
		ResourceLocation[] var0 = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/haoshoku_haki_0.png"),ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/haoshoku_haki_1.png"),ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/haoshoku_haki_2.png")};
		ICONS = var0;
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("A burst of the unique Conqueror's haki, that knocks out enemies that are weaker than the user."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.HAKI,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HaoshokuHakiAbility::new)))).setIcon(ICONS[0]).addDescriptionLine(lv2_3).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(HaoshokuHakiAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = ModRegistry.registerAbility("haoshoku_haki","Haoshoku Haki",var1);
	}
	private static boolean canUnlock(LivingEntity var0) {
		Supplier var8 = null;
		if ((ServerConfig.getHaoshokuUnlockLogic()) == (ServerConfig$HaoshokuUnlockLogic.NONE)){
			return false;
		}else{
			if (var0 instanceof Player){
				Player var1 = ((Player)(var0));
				if ((ServerConfig.isHaoshokuUnlockLogicChanceBased()) && (HakiHelper.isHaoshokuBorn(var1))){
					return true;
				}else{
					if (ServerConfig.isHaoshokuUnlockLogicExpBased()){
						HakiData var2 = ((HakiData)(((java.util.Optional)(HakiData.get(var0))).orElse((Object)(null))));
						if ((var2) == (null)){
							return false;
						}else{
							int var3 = (((double)(var2.getTotalHakiExp())) >= (WyHelper.percentage(80D,(double)(var2.getMaxHakiExp())))) ? (1) : (0);
							boolean var4 = false;
							int var5 = 0;
							Iterator var6 = ModAbilities.HAKI_ABILITIES.iterator();
							do{
								if (var6.hasNext()){
									Supplier var7 = ((Supplier)(var6.next()));
									if ((var5) >= (2)){
										var4 = true;
										break;
									}else{
										if ((var7.get()) != (INSTANCE.get())){
											var8 = var7;
											final Supplier var8_f1 = var8;
											Function<AbilityData, Boolean> var9 = (l0) -> {
			return Boolean.valueOf(l0.hasUnlockedAbility(((AbilityCore)(var8_f1.get()))));
		};
											if (((Boolean)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var9)).orElse(Boolean.valueOf(false)))).booleanValue()){
												var5++;
												continue;
											}else{
												continue;
											}
										}else{
											continue;
										}
									}
								}else{
									break;
								}
							} while (true);
							return ((var3) != (0)) && (var4);
						}
					}else{
						return false;
					}
				}
			}else{
				return false;
			}
		}
	}
}