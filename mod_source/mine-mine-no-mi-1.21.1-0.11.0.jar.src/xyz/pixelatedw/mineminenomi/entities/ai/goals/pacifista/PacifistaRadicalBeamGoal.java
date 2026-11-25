package xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.RadicalBeamAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager$BonusValue;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PacifistaRadicalBeamGoal extends ProjectileAbilityWrapperGoal<RadicalBeamAbility> {
	// Fields
	private static final BonusManager$BonusValue NO_COLA_BONUS = new BonusManager$BonusValue(UUID.fromString("68573843-cc45-4639-84ec-67efaabc464a"),"No Cola Bonus",BonusOperation.MUL,0F);

	public PacifistaRadicalBeamGoal(Mob var1) {
		super(var1,((AbilityCore)(RadicalBeamAbility.INSTANCE.get())));
		RadicalBeamAbility var2 = ((RadicalBeamAbility)(this.getAbility()));
		Consumer<ColaUsageComponent> var3 = (l0) -> {
			l0.getColaBonusManager().addBonus(NO_COLA_BONUS);
		};
		((java.util.Optional)(var2.getComponent(((AbilityComponentKey)(ModAbilityComponents.COLA_USAGE.get()))))).ifPresent((Consumer)(var3));
		if ((this.chargeComponent) != (null)){
			AnimationComponent var4 = ((AnimationComponent)(((RadicalBeamAbility)(this.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.ANIMATION.get()))).orElse(null)));
			AnimationComponent var5 = var4;
			Mob var6 = var1;
			final AnimationComponent var5_f1 = var5;
			final Mob var6_f2 = var6;
			ChargeComponent$IStartChargingEvent var7 = (l0, l1) -> {
			if ((var5_f1) != (null)){
				var5_f1.start((LivingEntity)(var6_f2),ModAnimations.HEAD_LASER_CHARGE);
			}
			var6_f2.level().broadcastEntityEvent((Entity)(var6_f2),(byte)(100));
		};
			this.chargeComponent.addStartEvent(110,var7);
			AnimationComponent var8 = var4;
			Mob var9 = var1;
			final AnimationComponent var8_f3 = var8;
			final Mob var9_f4 = var9;
			ChargeComponent$IEndChargingEvent var10 = (l0, l1) -> {
			if ((var8_f3) != (null)){
				var8_f3.stop((LivingEntity)(var9_f4));
			}
			var9_f4.level().broadcastEntityEvent((Entity)(var9_f4),(byte)(101));
		};
			this.chargeComponent.addEndEvent(110,var10);
		}
	}
	public void tickWrapper() {
		super.tickWrapper();
		this.entity.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),10,0));
		LivingEntity var1 = this.entity.getTarget();
		if ((var1) != (null)){
			GoalHelper.lookAtEntity(this.entity,(Entity)(var1));
		}
	}
}