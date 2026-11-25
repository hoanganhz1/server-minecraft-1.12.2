package xyz.pixelatedw.mineminenomi.api.entities.ai;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.logging.log4j.Marker;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public abstract class AbilityWrapperGoal<E extends Mob, A extends IAbility> extends TickedGoal<E> {
	// Fields
	private AbilityCore<A> abilityCore;
	private A ability;
	private Optional<AbilityData> props;
	private final Optional<StackComponent> stackComponent;
	private final Optional<ContinuousComponent> continousComponent;
	private final Optional<ChargeComponent> chargeComponent;
	private boolean stopOnCooldown;
	private boolean trackContinuity;
	private boolean trackCharging;

	public AbilityWrapperGoal(E var1, AbilityCore<A> var2) {
		super(var1);
		this.stopOnCooldown = true;
		this.trackContinuity = false;
		this.trackCharging = false;
		this.props = (Optional) (AbilityData.get(var1));
		this.abilityCore = var2;
		this.ability = (A) ((var2.getType().equals(AbilityType.ACTION)) ? (MobsHelper.unlockAndEquipAbility(var1,var2)) : (MobsHelper.unlockAbility(var1,var2)));
		if (((this.ability) == (null)) && (this.props.isPresent())){
			AbilityData var3 = ((AbilityData)(this.props.get()));
			this.ability = (A) (var3.getEquippedOrPassiveAbility(var2));
		}
		if ((this.ability) == (null)){
			throw new RuntimeException((String)("Ability assigned is null !?"));
		}else{
			this.continousComponent = (Optional) (this.ability.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))));
			AbilityWrapperGoal var3_1 = this;
			Consumer<? super ContinuousComponent> var4 = (l0) -> {
			this.trackContinuity = true;
			String lv1_2 = this.ability.getDisplayName().getString();
			String lv1_3 = "Track Continuity for " + lv1_2;
			WyDebug.debug(lv1_3);
		};
			((java.util.Optional)(this.continousComponent)).ifPresent((Consumer)(var4));
			this.chargeComponent = (Optional) (this.ability.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))));
			AbilityWrapperGoal var5 = this;
			Consumer<? super ChargeComponent> var6 = (l0) -> {
			this.trackCharging = true;
			String lv2_2 = this.ability.getDisplayName().getString();
			String lv2_3 = "Track Charging for " + lv2_2;
			WyDebug.debug(lv2_3);
		};
			((java.util.Optional)(this.chargeComponent)).ifPresent((Consumer)(var6));
			this.stackComponent = (Optional) (this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.STACK.get()))));
			if (this.stackComponent.isPresent()){
				this.dontStopOnCooldown();
			}
			return;
		}
	}
	public AbilityWrapperGoal<E, A> dontStopOnCooldown() {
		this.stopOnCooldown = false;
		return this;
	}
	public boolean canUse() {
		if ((this.entity.isAlive()) && ((!(this.entity.isBaby())) && (!(this.entity.hasEffect((Holder)(ModEffects.IN_EVENT)))))){
			if (((this.entity.getTarget()) != (null)) && (this.entity.getTarget() instanceof Monster)){
				return false;
			}else{
				Iterator var1 = this.entity.getActiveEffects().iterator();
				do{
					if (var1.hasNext()){
						MobEffectInstance var2 = ((MobEffectInstance)(var1.next()));
						if ((var2.getEffect() instanceof IBindHandsEffect) && (((IBindHandsEffect)(var2.getEffect())).isBlockingSwings())){
							return false;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if ((this.canUseWrapper()) && (!(this.ability.canUse((LivingEntity)(this.entity)).isFail()))){
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	public abstract boolean canUseWrapper();
	public boolean canContinueToUse() {
		if ((this.entity.isAlive()) && (!(this.entity.hasEffect((Holder)(ModEffects.IN_EVENT))))){
			if (((Boolean)(this.ability.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).map((Function)(((Function<DisableComponent, Boolean>)(DisableComponent::isDisabled)))).orElse(Boolean.valueOf(false)))).booleanValue()){
				return false;
			}else{
				if ((this.stopOnCooldown) && (((Boolean)(this.ability.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).map((Function)(((Function<CooldownComponent, Boolean>)(CooldownComponent::isOnCooldown)))).orElse(Boolean.valueOf(false)))).booleanValue())){
					return false;
				}else{
					if ((this.trackContinuity) && ((this.continousComponent.isPresent()) && (!(((ContinuousComponent)(this.continousComponent.get())).isContinuous())))){
						return false;
					}else{
						if ((this.stackComponent.isPresent()) && ((((StackComponent)(this.stackComponent.get())).getStacks()) <= (0))){
							return false;
						}else{
							if (!(this.entity.level().isClientSide)){
								Optional var1 = ProtectedAreasData.get(((ServerLevel)(this.entity.level()))).getProtectedArea(this.entity.blockPosition().getX(),this.entity.blockPosition().getY(),this.entity.blockPosition().getZ());
								if ((var1.isPresent()) && (!(((ProtectedArea)(var1.get())).canUseAbility(this.getCore())))){
									return false;
								}
							}
							if (!(this.canContinueToUseWrapper())){
								return false;
							}else{
								return true;
							}
						}
					}
				}
			}
		}else{
			return false;
		}
	}
	public abstract boolean canContinueToUseWrapper();
	public final void start() {
		super.start();
		this.startWrapper();
		this.ability.use((LivingEntity)(this.entity));
		if (FGCommand.SHOW_NPC_ABILITY_LOG){
			WyDebug.debug(WyDebug.ABILITY_LOG,String.format("%s started %s (%s) at tick %s",new Object[]{this.entity.getName().getString(),this.ability.getDisplayName().getString(),this.getClass().getSimpleName(),Integer.valueOf(this.entity.tickCount)}));
		}
	}
	public abstract void startWrapper();
	public final void tick() {
		this.tickWrapper();
		if ((this.stackComponent.isPresent()) && (((((StackComponent)(this.stackComponent.get())).getStacks()) > (0)) && (this.getAbility().canUse((LivingEntity)(this.entity)).isSuccess()))){
			this.getAbility().use((LivingEntity)(this.entity));
			if (FGCommand.SHOW_NPC_ABILITY_LOG){
				WyDebug.debug(WyDebug.ABILITY_LOG,String.format("%s reused %s (%s) at tick %s",new Object[]{this.entity.getName().getString(),this.ability.getDisplayName().getString(),this.getClass().getSimpleName(),Integer.valueOf(this.entity.tickCount)}));
			}
		}
		super.tick();
	}
	public abstract void tickWrapper();
	public final void stop() {
		super.stop();
		this.stopWrapper();
		AbilityWrapperGoal var1 = this;
		Consumer<ContinuousComponent> var2 = (l0) -> {
			l0.stopContinuity((LivingEntity)(this.entity));
		};
		((java.util.Optional)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var2));
		if (FGCommand.SHOW_NPC_ABILITY_LOG){
			WyDebug.debug(WyDebug.ABILITY_LOG,String.format("%s stopped %s at tick %s",new Object[]{this.entity.getName().getString(),this.ability.getDisplayName().getString(),Integer.valueOf(this.entity.tickCount)}));
		}
	}
	public abstract void stopWrapper();
	public A getAbility() {
		return (A) (this.ability);
	}
	public AbilityCore<A> getCore() {
		return (AbilityCore<A>) (AbilityCore) (this.abilityCore);
	}
	public Optional<AbilityData> getProps() {
		return this.props;
	}
}