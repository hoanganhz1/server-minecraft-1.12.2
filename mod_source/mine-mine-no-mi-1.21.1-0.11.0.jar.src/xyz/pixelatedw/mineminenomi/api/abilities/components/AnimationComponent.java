package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$State;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeAnimationStatePacket;

public class AnimationComponent extends AbilityComponent<IAbility> {
	// Fields
	private Function<LivingEntity, Boolean> stopCondition;
	private Function<LivingEntity, Boolean> pauseCondition;
	private AnimationId<?> animationId;
	private long startTime;
	private long animTick;
	private int animDuration;
	private boolean isInfinite;
	private Animation$State state;

	public AnimationComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.ANIMATION.get())),var1);
		this.state = Animation$State.STOP;
	}
	public AnimationComponent setPauseCondition(Function<LivingEntity, Boolean> var1) {
		this.pauseCondition = var1;
		return this;
	}
	public AnimationComponent setStopCondition(Function<LivingEntity, Boolean> var1) {
		this.stopCondition = var1;
		return this;
	}
	public <A extends Animation<?, ?>> void start(LivingEntity var1, AnimationId<A> var2) {
		this.start((LivingEntity)(var1),(AnimationId)(var2),-1);
	}
	public <A extends Animation<?, ?>> void start(LivingEntity var1, AnimationId<A> var2, int var3) {
		this.ensureIsRegistered();
		if ((this.animDuration) > (0)){
			return;
		}else{
			if (((var3) <= (0)) && (this.isPlaying())){
				return;
			}else{
				this.animationId = var2;
				this.startTime = var1.level().getGameTime();
				this.animTick = 0L;
				this.animDuration = var3;
				this.isInfinite = (this.animDuration) == (-1);
				this.state = Animation$State.PLAY;
				if (!(var1.level().isClientSide)){
					ModNetwork.sendToAllTrackingAndSelf(new SChangeAnimationStatePacket(var1,this.getAbility(),var2,this.state,this.animDuration),var1);
				}
				return;
			}
		}
	}
	public void stop(LivingEntity var1) {
		if (this.isStopped()){
			return;
		}else{
			this.animTick = 0L;
			this.animDuration = 0;
			this.state = Animation$State.STOP;
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SChangeAnimationStatePacket(var1,this.getAbility(),this.animationId,this.state,0),var1);
			}
			return;
		}
	}
	public void pause(LivingEntity var1) {
		if (!(this.isPlaying())){
			return;
		}else{
			this.state = Animation$State.PAUSE;
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SChangeAnimationStatePacket(var1,this.getAbility(),this.animationId,this.state,this.animDuration),var1);
			}
			return;
		}
	}
	public void resume(LivingEntity var1) {
		if (!(this.isPaused())){
			return;
		}else{
			this.state = Animation$State.PLAY;
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SChangeAnimationStatePacket(var1,this.getAbility(),this.animationId,this.state,this.animDuration),var1);
			}
			return;
		}
	}
	public void doTick(LivingEntity var1) {
		if (!(var1.level().isClientSide)){
			if (this.isPlaying()){
				if (((this.pauseCondition) != (null)) && (((Boolean)(((java.util.function.Function)(this.pauseCondition)).apply((Object)(var1)))).booleanValue())){
					this.pause(var1);
					return;
				}else{
					if (((this.stopCondition) != (null)) && (((Boolean)(((java.util.function.Function)(this.stopCondition)).apply((Object)(var1)))).booleanValue())){
						this.stop(var1);
						return;
					}else{
						if ((!(this.isInfinite)) && ((this.animDuration) <= (0))){
							this.stop(var1);
							return;
						}
					}
				}
			}else{
				if ((this.isPaused()) && (((this.pauseCondition) != (null)) && (!(((Boolean)(((java.util.function.Function)(this.pauseCondition)).apply((Object)(var1)))).booleanValue())))){
					this.resume(var1);
					return;
				}
			}
		}
		if (this.isPlaying()){
			this.animTick = (this.animTick) + (1L);
			this.animDuration = (this.animDuration) - (1);
		}
	}
	public long getAnimationTick() {
		return this.animTick;
	}
	public boolean isPlaying() {
		return (this.state) == (Animation$State.PLAY);
	}
	public boolean isStopped() {
		return (this.state) == (Animation$State.STOP);
	}
	public boolean isPaused() {
		return (this.state) == (Animation$State.PAUSE);
	}
}