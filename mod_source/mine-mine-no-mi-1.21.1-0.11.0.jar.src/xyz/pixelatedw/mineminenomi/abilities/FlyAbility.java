package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IOnRemoveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.extensions.ILivingEntityExtension;
import xyz.pixelatedw.mineminenomi.api.extensions.IPlayerAbilitiesExtension;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler$Common;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public abstract class FlyAbility extends PassiveAbility {
	// Fields
	private static final Component ELEMENTAL_FLIGHT_NAME = Component.translatable(ModRegistry.registerAbilityName("elemental_flight","Elemental Flight"));
	private static final ResourceLocation ELEMENTAL_FLIGHT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/special_fly.png");
	private boolean isFlying;
	private int jumpTriggerTime;
	private boolean jumpLock;
	private final AnimationComponent animationComponent;

	public FlyAbility(AbilityCore<? extends FlyAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent};
		this.addComponents(var2);
		this.setDisplayName(ELEMENTAL_FLIGHT_NAME);
		this.setDisplayIcon(ELEMENTAL_FLIGHT_ICON);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassiveEvent))));
		this.addRemoveEvent((PassiveAbility$IOnRemoveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IOnRemoveEvent)(this::onRemoveEvent))));
	}
	public void tick(LivingEntity var1) {
		super.tick(var1);
	}
	public void duringPassiveEvent(LivingEntity var1) {
		if (var1 instanceof Player){
			Player var2 = ((Player)(var1));
			if (!(this.canUse(var1).isSuccess())){
				this.disableFlight(var2);
				return;
			}else{
				if ((((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))))) == (null)){
					return;
				}else{
					IPlayerAbilitiesExtension var3 = ((IPlayerAbilitiesExtension)(var2.getAbilities()));
					if (!(var1.level().isClientSide)){
						boolean var4 = ((ILivingEntityExtension)(var2)).isJumping();
						if (!(var4)){
							this.jumpLock = false;
						}
						if ((this.jumpTriggerTime) > (0)){
							this.jumpTriggerTime = (this.jumpTriggerTime) - (1);
						}
						if ((!(this.jumpLock)) && (var4)){
							if ((this.jumpTriggerTime) == (0)){
								this.jumpTriggerTime = 7;
								this.jumpLock = true;
							}else{
								this.isFlying = !(this.isFlying);
								var3.setCustomFlight(this.isFlying);
								ModNetwork.sendTo(new SUpdateAbilityNBTPacket((LivingEntity)(var2),(IAbility)(this)),var2);
								var2.setNoGravity(this.isFlying);
								this.jumpTriggerTime = 0;
								if (!(this.isFlying)){
									this.disableFlight(var2);
								}
							}
						}
						if (this.isFlying){
							if (var1.onGround()){
								this.disableFlight(var2);
								return;
							}else{
								var2.fallDistance = 0F;
							}
						}
					}else{
						var3.setCustomFlight(this.isFlying);
					}
					if ((this.isFlying) && (!(var1.level().isClientSide))){
						float var4_1 = this.getSpeed(var2);
						InputHandler$Common.handleFreeFlyingLogic(var2,var4_1,var4_1,this.getDrag(var2));
						double var5 = (double)(this.getHeightDifference(var2));
						if ((!(var2.level().isClientSide)) && (((var5) != (0D)) && (!(ServerConfig.hasYRestrictionRemoved())))){
							boolean var6 = AbilityHelper.flyingAtMaxHeight((LivingEntity)(var2),var5);
							AbilityHelper.vanillaFlightThreshold((LivingEntity)(var2),(var6) ? (256) : (((int)(var2.getY())) - (1)));
						}
						boolean var6 = ((DevilFruitData)(DevilFruitData.get((LivingEntity)(var2)).get())).getCurrentMorph().isEmpty();
						if ((this.isFlying) && (var6)){
							this.animationComponent.start(var1,ModAnimations.SPECIAL_FLY);
							WyHelper.spawnParticleEffect(((ParticleEffect)(this.getParticleEffects().get())),(Entity)(var2),var2.getX(),var2.getY(),var2.getZ());
						}else{
							this.animationComponent.stop(var1);
						}
					}
					return;
				}
			}
		}else{
			return;
		}
	}
	public void onRemoveEvent(LivingEntity var1, IAbility var2) {
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			if (!(AbilityHelper.isInCreativeOrSpectator(var1))){
				this.disableFlight(var3);
			}else{
				return;
			}
		}
	}
	public void enableFlight(Player var1) {
		IPlayerAbilitiesExtension var2 = ((IPlayerAbilitiesExtension)(var1.getAbilities()));
		this.isFlying = true;
		var2.setCustomFlight(true);
		ModNetwork.sendTo(new SUpdateAbilityNBTPacket((LivingEntity)(var1),(IAbility)(this)),var1);
		var1.setNoGravity(true);
	}
	public void disableFlight(Player var1) {
		IPlayerAbilitiesExtension var2 = ((IPlayerAbilitiesExtension)(var1.getAbilities()));
		this.isFlying = false;
		var2.setCustomFlight(false);
		ModNetwork.sendTo(new SUpdateAbilityNBTPacket((LivingEntity)(var1),(IAbility)(this)),var1);
		var1.setNoGravity(false);
		this.animationComponent.stop((LivingEntity)(var1));
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			var3.setFlyingSpeed(Vec3.ZERO);
			return;
		}
	}
	public abstract Supplier<ParticleEffect<?>> getParticleEffects();
	public abstract int getHeightDifference(Player var0);
	public abstract float getSpeed(Player var0);
	public float getDrag(Player var1) {
		return 0.65F;
	}
	public Result canUse(LivingEntity var1) {
		if (AbilityHelper.isInCreativeOrSpectator(var1)){
			return Result.fail(null);
		}else{
			if ((var1.getAttribute(ModAttributes.JUMP_HEIGHT).getValue()) < (0D)){
				return Result.fail(null);
			}else{
				return super.canUse(var1);
			}
		}
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putBoolean("isFlying",this.isFlying);
	}
	public void loadAdditional(CompoundTag var1) {
		this.isFlying = var1.getBoolean("isFlying");
	}
}