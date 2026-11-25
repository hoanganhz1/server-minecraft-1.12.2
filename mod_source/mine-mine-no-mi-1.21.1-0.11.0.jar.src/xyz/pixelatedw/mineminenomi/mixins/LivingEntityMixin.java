package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.events.entity.MobEffectAfterAddedEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.MobEffectAfterRemovedEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.MorphsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

@Mixin(value={LivingEntity.class})
public abstract class LivingEntityMixin implements ILivingEntityExtension, xyz.pixelatedw.mineminenomi.api.extensions.ILivingEntityExtension {
	// Fields
	@Shadow() public boolean jumping;

	public boolean isJumping() {
		return this.jumping;
	}
	@Nullable()
	public PartEntity<?>[] getParts() {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(((LivingEntity)(this))))).orElse((Object)(null))));
		if ((var1) == (null)){
			return null;
		}else{
			Predicate<IAbility> var2 = (l0) -> {
			return l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.MORPH.get())));
		};
			Iterator var3 = var1.getEquippedAndPassiveAbilities(var2).iterator();
			do{
				if (var3.hasNext()){
					MorphComponent var4 = ((MorphComponent)(((IAbility)(var3.next())).getComponent(((AbilityComponentKey)(ModAbilityComponents.MORPH.get()))).get()));
					if (!(var4.getParts().isEmpty())){
						return ((PartEntity[])(var4.getParts().values().toArray(new PartEntity[0])));
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return null;
		}
	}
	public boolean isMultipartEntity() {
		LivingEntity var1 = ((LivingEntity)(this));
		return ((var1.getParts()) != (null)) && ((var1.getParts().length) > (0));
	}
	@Inject(method={"isImmobile"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$isImmobile(CallbackInfoReturnable<Boolean> var1) {
		LivingEntity var2 = ((LivingEntity)(this));
		Class var3 = IBindBodyEffect.class;
		Objects.requireNonNull(var3);
		Predicate<Holder> var4 = (l0) -> {
			return ((IBindBodyEffect)(l0)).isBlockingRotation();
		};
		if ((((var2.getActiveEffects().stream().map((Function)(((Function<MobEffectInstance, Holder>)(MobEffectInstance::getEffect)))).filter((Predicate)(((Predicate<Holder>)(var3::isInstance)))).filter(var4).count()) > (0L)) ? (1) : (0)) != (0)){
			var1.setReturnValue(Boolean.valueOf(true));
			return;
		}else{
			return;
		}
	}
	@Inject(method={"swing(Lnet/minecraft/world/InteractionHand;Z)V"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$swing(InteractionHand var1, boolean var2, CallbackInfo var3) {
		LivingEntity var4 = ((LivingEntity)(this));
		if (var4 instanceof Mob){
			Mob var5 = ((Mob)(var4));
			Class var6 = IBindHandsEffect.class;
			Objects.requireNonNull(var6);
			Predicate<Holder> var7 = (l0) -> {
			return ((IBindHandsEffect)(l0)).isBlockingSwings();
		};
			if ((((var5.getActiveEffects().stream().map((Function)(((Function<MobEffectInstance, Holder>)(MobEffectInstance::getEffect)))).filter((Predicate)(((Predicate<Holder>)(var6::isInstance)))).filter(var7).count()) > (0L)) ? (1) : (0)) != (0)){
				var3.cancel();
			}else{
				return;
			}
		}
	}
	@Inject(method={"aiStep"}, at={@At(value="TAIL")}, cancellable=true)
	public void mineminenomi$aiStep(CallbackInfo var1) {
		LivingEntity var2 = ((LivingEntity)(this));
		if (var2 instanceof Mob){
			Mob var3 = ((Mob)(var2));
			Class var4 = IBindBodyEffect.class;
			Objects.requireNonNull(var4);
			Predicate<Holder> var5 = (l0) -> {
			return ((IBindBodyEffect)(l0)).isBlockingRotation();
		};
			if ((((var3.getActiveEffects().stream().map((Function)(((Function<MobEffectInstance, Holder>)(MobEffectInstance::getEffect)))).filter((Predicate)(((Predicate<Holder>)(var4::isInstance)))).filter(var5).count()) > (0L)) ? (1) : (0)) != (0)){
				var3.getMoveControl().setWantedPosition(var3.getX(),var3.getY(),var3.getZ(),0D);
				var3.zza = 0F;
				var3.getNavigation().stop();
				var3.setTarget((LivingEntity)(null));
			}else{
				return;
			}
		}
	}
	@Inject(method={"dropAllDeathLoot"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$dropAllDeathLoot(ServerLevel var1, DamageSource var2, CallbackInfo var3) {
		if (NuWorld.isChallengeDimension(((LivingEntity)(this)).level())){
			var3.cancel();
		}
	}
	@Inject(method={"getDimensions"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$updateDimensions(Pose var1, CallbackInfoReturnable<EntityDimensions> var2) {
		EntityDimensions var3 = MorphsHelper.getMorphDimensions(((LivingEntity)(this)),var1);
		if ((var3) != (null)){
			var2.setReturnValue(var3);
		}
	}
	@Inject(method={"getEyeHeight"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$getEyeHeight(Pose var1, EntityDimensions var2, CallbackInfoReturnable<Float> var3) {
		float var4 = MorphsHelper.getMorphEyeHeight(((LivingEntity)(this)));
		if ((var4) > (0F)){
			var3.setReturnValue(Float.valueOf(var4));
		}
	}
	@Inject(method={"onEffectAdded"}, at={@At(value="HEAD")})
	public void mineminenomi$fireAfterEffectAddedEvent(MobEffectInstance var1, @Nullable() Entity var2, CallbackInfo var3) {
		LivingEntity var4 = ((LivingEntity)(this));
		NeoForge.EVENT_BUS.post((Event)(new MobEffectAfterAddedEvent(var4,var1)));
	}
	@Inject(method={"onEffectRemoved"}, at={@At(value="HEAD")})
	public void mineminenomi$fireAfterEffectRemoveEvent(MobEffectInstance var1, CallbackInfo var2) {
		LivingEntity var3 = ((LivingEntity)(this));
		NeoForge.EVENT_BUS.post((Event)(new MobEffectAfterRemovedEvent(var3,var1)));
	}
	@Inject(method={"canStandOnFluid"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$canStandOnFluid(FluidState var1, CallbackInfoReturnable<Boolean> var2) {
		if (((java.lang.Object)(var1.getType().getFluidType())).equals(NeoForgeMod.WATER_TYPE.value())){
			LivingEntity var3 = ((LivingEntity)(this));
			if ((((Boolean)(DevilFruitData.get(var3).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::hasYomiPower)))).orElse(Boolean.valueOf(false)))).booleanValue()) && (var3.isSprinting())){
				var2.setReturnValue(Boolean.valueOf(true));
			}else{
				return;
			}
		}
	}
	@ModifyVariable(method={"travel(Lnet/minecraft/world/phys/Vec3;)V"}, at=@At(value="STORE"), ordinal=0)
	private float mineminenomi$getFriction(float var1) {
		AttributeInstance var2 = ((LivingEntity)(this)).getAttribute(ModAttributes.FRICTION);
		if (((var2) != (null)) && ((var2.getValue()) > (0D))){
			return (float)(var2.getValue());
		}else{
			return var1;
		}
	}
}