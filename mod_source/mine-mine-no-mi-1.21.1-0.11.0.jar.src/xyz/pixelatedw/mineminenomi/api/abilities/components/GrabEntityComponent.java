package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class GrabEntityComponent extends AbilityComponent<IAbility> {
	// Fields
	private LivingEntity grabbedEntity;
	private boolean handleTargetCamera;
	private boolean handleTargetPosition;
	private boolean handleTargetPulling;
	private float heldDistance;
	private float startHealth;
	private double previousDistance;
	private final PriorityEventPool<GrabEntityComponent$IOnGrabEvent> grabEvents;
	private final PriorityEventPool<GrabEntityComponent$IOnPullStartEvent> pullStartEvents;
	private final PriorityEventPool<GrabEntityComponent$IOnPullTickEvent> pullTickEvents;
	private final PriorityEventPool<GrabEntityComponent$IOnPullEndEvent> pullEndEvents;
	private final PriorityEventPool<GrabEntityComponent$IOnReleaseEvent> releaseEvents;
	private GrabEntityComponent$GrabState state;

	public GrabEntityComponent(IAbility var1, boolean var2, boolean var3, float var4) {
		this(var1,var2,var3,false,var4);
	}
	public GrabEntityComponent(IAbility var1, boolean var2, boolean var3, boolean var4, float var5) {
		super(((AbilityComponentKey)(ModAbilityComponents.GRAB.get())),var1);
		this.grabbedEntity = null;
		this.handleTargetCamera = false;
		this.handleTargetPosition = false;
		this.handleTargetPulling = false;
		this.heldDistance = 1F;
		this.grabEvents = (PriorityEventPool) (new PriorityEventPool());
		this.pullStartEvents = (PriorityEventPool) (new PriorityEventPool());
		this.pullTickEvents = (PriorityEventPool) (new PriorityEventPool());
		this.pullEndEvents = (PriorityEventPool) (new PriorityEventPool());
		this.releaseEvents = (PriorityEventPool) (new PriorityEventPool());
		this.state = GrabEntityComponent$GrabState.IDLE;
		this.handleTargetCamera = var2;
		this.handleTargetPosition = var3;
		this.handleTargetPulling = var4;
		this.heldDistance = var5;
	}
	public GrabEntityComponent addGrabEvent(GrabEntityComponent$IOnGrabEvent var1) {
		this.grabEvents.addEvent(100,var1);
		return this;
	}
	public GrabEntityComponent addGrabEvent(int var1, GrabEntityComponent$IOnGrabEvent var2) {
		this.grabEvents.addEvent(var1,var2);
		return this;
	}
	public GrabEntityComponent addPullStartEvent(GrabEntityComponent$IOnPullStartEvent var1) {
		this.pullStartEvents.addEvent(100,var1);
		return this;
	}
	public GrabEntityComponent addPullStartEvent(int var1, GrabEntityComponent$IOnPullStartEvent var2) {
		this.pullStartEvents.addEvent(var1,var2);
		return this;
	}
	public GrabEntityComponent addPullTickEvent(GrabEntityComponent$IOnPullTickEvent var1) {
		this.pullTickEvents.addEvent(100,var1);
		return this;
	}
	public GrabEntityComponent addPullTickEvent(int var1, GrabEntityComponent$IOnPullTickEvent var2) {
		this.pullTickEvents.addEvent(var1,var2);
		return this;
	}
	public GrabEntityComponent addPullEndEvent(GrabEntityComponent$IOnPullEndEvent var1) {
		this.pullEndEvents.addEvent(100,var1);
		return this;
	}
	public GrabEntityComponent addPullEndEvent(int var1, GrabEntityComponent$IOnPullEndEvent var2) {
		this.pullEndEvents.addEvent(var1,var2);
		return this;
	}
	public GrabEntityComponent addReleaseEvent(GrabEntityComponent$IOnReleaseEvent var1) {
		this.releaseEvents.addEvent(100,var1);
		return this;
	}
	public GrabEntityComponent addReleaseEvent(int var1, GrabEntityComponent$IOnReleaseEvent var2) {
		this.releaseEvents.addEvent(var1,var2);
		return this;
	}
	public void doTick(LivingEntity var1) {
		LivingEntity var10 = null;
		if (var1.level().isClientSide){
			return;
		}else{
			if (this.canContinueGrab(var1)){
				if (((this.state) == (GrabEntityComponent$GrabState.PULLING)) && (this.handleTargetPulling)){
					this.grabbedEntity.addEffect(new MobEffectInstance((Holder)(ModEffects.GRABBED),2,3));
					Vec3 var2 = var1.getEyePosition(1F).add(0D,-1D,0D).subtract(this.grabbedEntity.position());
					double var3 = Math.abs(var1.getEyePosition(1F).distanceTo(this.grabbedEntity.position()));
					if ((var3) > ((this.previousDistance) + (2D))){
						this.release(var1);
						return;
					}else{
						this.previousDistance = var3;
						double var4 = (double)(Mth.sqrt((float)(((var2.x) * (var2.x)) + ((var2.z) * (var2.z)))));
						double var5 = ((var2.y) != (0D)) ? ((1D) / (Math.abs(var2.y))) : (0D);
						Vec3 var6 = var2.multiply((1D) / (var4),var5,(1D) / (var4)).scale(0.1D);
						AbilityHelper.setDeltaMovement((Entity)(this.grabbedEntity),this.grabbedEntity.getDeltaMovement().add(var6));
						AttributeInstance var7 = var1.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
						float var8 = 3F;
						if ((var7) != (null)){
							var8 = (float)(((double)(var8)) + (var7.getValue()));
						}
						if (this.isTargetInRange(var1,var8,((var1.getBbWidth()) / (2F)) + (0.1F))){
							this.stopPulling(var1);
						}else{
							GrabEntityComponent var9 = this;
							var10 = var1;
							final LivingEntity var10_f1 = var10;
							Consumer<GrabEntityComponent$IOnPullTickEvent> var11 = (l0) -> {
			l0.pullTick(var10_f1,super.getAbility());
		};
							((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.pullTickEvents)).dispatch((Consumer)(var11));
						}
					}
				}
				if ((this.state) == (GrabEntityComponent$GrabState.GRABBED)){
					if (this.handleTargetCamera){
						LivingEntity var2_1 = this.grabbedEntity;
						if (var2_1 instanceof ServerPlayer){
							ServerPlayer var3_1 = ((ServerPlayer)(var2_1));
							if (!(CameraLockData.hasCameraLocked((Player)(var3_1)))){
								CameraLockData.pinFixed((Player)(var3_1));
							}
						}
					}
					if (this.handleTargetPosition){
						this.grabbedEntity.addEffect(new MobEffectInstance((Holder)(ModEffects.GRABBED),2,3));
						Vec3 var2_2 = var1.getLookAngle().normalize();
						Vec3 var3_2 = new Vec3((var2_2.x) * ((double)(this.heldDistance)),(((double)(var1.getEyeHeight())) - (1D)) + ((var2_2.y) * ((double)(this.heldDistance))),(var2_2.z) * ((double)(this.heldDistance)));
						AbilityHelper.setDeltaMovement((Entity)(this.grabbedEntity),var1.position().add(var3_2).subtract(this.grabbedEntity.position()),true);
						this.grabbedEntity.fallDistance = 0F;
					}else{
						return;
					}
				}else{
					return;
				}
			}else{
				if ((this.grabbedEntity) != (null)){
					if ((this.state) == (GrabEntityComponent$GrabState.PULLING)){
						this.stopPulling(var1);
					}
					this.release(var1);
				}else{
					return;
				}
			}
			return;
		}
	}
	public void triggerPulling(LivingEntity var1) {
		if ((this.state) == (GrabEntityComponent$GrabState.PULLING)){
			this.stopPulling(var1);
		}else{
			this.startPulling(var1);
		}
	}
	public void startPulling(LivingEntity var1) {
		this.state = GrabEntityComponent$GrabState.PULLING;
		GrabEntityComponent var2 = this;
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		Consumer<GrabEntityComponent$IOnPullStartEvent> var4 = (l0) -> {
			l0.pullStart(var3_f1,super.getAbility());
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.pullStartEvents)).dispatch((Consumer)(var4));
	}
	public void stopPulling(LivingEntity var1) {
		this.state = GrabEntityComponent$GrabState.GRABBED;
		GrabEntityComponent var2 = this;
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		Consumer<GrabEntityComponent$IOnPullEndEvent> var4 = (l0) -> {
			l0.pullEnd(var3_f1,super.getAbility());
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.pullEndEvents)).dispatch((Consumer)(var4));
	}
	public void throwTarget(LivingEntity var1, double var2, double var3) {
		this.state = GrabEntityComponent$GrabState.THROWN;
		if (this.canContinueGrab(var1)){
			this.grabbedEntity.removeEffect((Holder)(ModEffects.GRABBED));
			Vec3 var4 = var1.position().subtract(this.grabbedEntity.position()).normalize().multiply(-(var2),-(var3),-(var2));
			AbilityHelper.setDeltaMovement((Entity)(this.grabbedEntity),var4);
		}
	}
	public void release(LivingEntity var1) {
		this.state = GrabEntityComponent$GrabState.IDLE;
		if (((this.grabbedEntity) != (null)) && (this.grabbedEntity.isAlive())){
			GrabEntityComponent var2 = this;
			LivingEntity var3 = var1;
			final LivingEntity var3_f1 = var3;
			Consumer<GrabEntityComponent$IOnReleaseEvent> var4 = (l0) -> {
			l0.release(var3_f1,this.grabbedEntity,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.releaseEvents)).dispatch((Consumer)(var4));
			LivingEntity var5 = this.grabbedEntity;
			if (var5 instanceof ServerPlayer){
				CameraLockData.resetLock((Player)(((ServerPlayer)(var5))));
			}
		}
		this.grabbedEntity = null;
	}
	public boolean grabManually(LivingEntity var1, LivingEntity var2) {
		this.ensureIsRegistered();
		if (((var2) != (null)) && ((var2.isAlive()) && (!(CombatHelper.isTargetBlocking(var1,var2))))){
			if (((!(this.grabEvents.dispatchCancelable((Predicate)(((Predicate<GrabEntityComponent$IOnGrabEvent>)((l0) -> {
			return !(l0.grab(var1,var2,this.getAbility()));
		})))))) ? (1) : (0)) != (0)){
				this.grabbedEntity = var2;
				this.previousDistance = Math.abs(var1.getEyePosition(1F).distanceTo(this.grabbedEntity.position()));
				this.startHealth = var1.getHealth();
				this.state = GrabEntityComponent$GrabState.GRABBED;
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public boolean isTargetInRange(LivingEntity var1) {
		return this.isTargetInRange(var1,3F,0.4F);
	}
	public boolean isTargetInRange(LivingEntity var1, float var2, float var3) {
		float var4 = (float)(Math.sqrt(AttributeHelper.getSquaredAttackRangeDistance(var1,(double)(var2))));
		return TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var1.blockPosition(),(double)(var4),(double)(var1.getBbHeight()),(double)(var3),(TargetPredicate)(null),LivingEntity.class).stream().anyMatch((Predicate)(((Predicate<LivingEntity>)((l0) -> {
			return l0.equals(this.grabbedEntity);
		}))));
	}
	public boolean grabNearest(LivingEntity var1) {
		return this.grabNearest(var1,(float)(Math.sqrt(AttributeHelper.getSquaredAttackRangeDistance(var1,3D))),0.4F,true);
	}
	public boolean grabNearest(LivingEntity var1, boolean var2) {
		return this.grabNearest(var1,(float)(Math.sqrt(AttributeHelper.getSquaredAttackRangeDistance(var1,3D))),0.4F,var2);
	}
	public boolean grabNearest(LivingEntity var1, float var2, float var3) {
		return this.grabNearest(var1,var2,var3,true);
	}
	public boolean grabNearest(LivingEntity var1, float var2, float var3, boolean var4) {
		this.ensureIsRegistered();
		LivingEntity var5 = CombatHelper.canGrab(var1,var2,var3,var4);
		GrabEntityComponent var6 = this;
		LivingEntity var7 = var1;
		LivingEntity var8 = var5;
		final LivingEntity var7_f1 = var7;
		final LivingEntity var8_f2 = var8;
		Predicate<GrabEntityComponent$IOnGrabEvent> var9 = (l0) -> {
			return !(l0.grab(var7_f1,var8_f2,this.getAbility()));
		};
		if ((((!(this.grabEvents.dispatchCancelable(var9))) ? (1) : (0)) != (0)) && (((var5) != (null)) && (var5.isAlive()))){
			this.grabbedEntity = var5;
			this.previousDistance = Math.abs(var1.getEyePosition(1F).distanceTo(this.grabbedEntity.position()));
			this.startHealth = var1.getHealth();
			this.state = GrabEntityComponent$GrabState.GRABBED;
			return true;
		}else{
			return false;
		}
	}
	public boolean canContinueGrab(LivingEntity var1) {
		if (((this.grabbedEntity) != (null)) && (this.grabbedEntity.isAlive())){
			if (CombatHelper.isDodging(this.grabbedEntity)){
				return false;
			}else{
				if (CombatHelper.isLogiaBlocking(this.grabbedEntity)){
					return false;
				}else{
					if ((var1.getHealth()) < ((this.startHealth) - (10F))){
						return false;
					}else{
						return true;
					}
				}
			}
		}else{
			return false;
		}
	}
	@Nullable()
	public @Nullable() LivingEntity getGrabbedEntity() {
		return this.grabbedEntity;
	}
	public boolean hasGrabbedEntity() {
		return ((this.grabbedEntity) != (null)) && (this.grabbedEntity.isAlive());
	}
	public static boolean hasGrabbed(LivingEntity var0, IAbility var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return false;
		}else{
			Iterator var3 = var2.getEquippedAndPassiveAbilities().iterator();
			do{
				if (var3.hasNext()){
					IAbility var4 = ((IAbility)(var3.next()));
					if ((!(var4.hasComponent(((AbilityComponentKey)(ModAbilityComponents.GRAB.get()))))) || (var4.equals(var1))){
						continue;
					}else{
						boolean var5 = ((Boolean)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).map((Function)(((Function<CooldownComponent, Boolean>)(CooldownComponent::isOnCooldown)))).orElse(Boolean.valueOf(false)))).booleanValue();
						boolean var6 = ((Boolean)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).map((Function)(((Function<DisableComponent, Boolean>)(DisableComponent::isDisabled)))).orElse(Boolean.valueOf(false)))).booleanValue();
						if ((!(var5)) && (!((var6) || (!(((GrabEntityComponent)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.GRAB.get()))).get())).hasGrabbedEntity()))))){
							return true;
						}else{
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			return false;
		}
	}
	public GrabEntityComponent$GrabState getState() {
		return this.state;
	}
	public void setState(GrabEntityComponent$GrabState var1) {
		this.state = var1;
	}
}