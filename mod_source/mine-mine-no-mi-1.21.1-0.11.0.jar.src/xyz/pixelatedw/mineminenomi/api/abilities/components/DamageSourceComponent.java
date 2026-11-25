package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class DamageSourceComponent extends AbilityComponent<IAbility> {
	// Fields
	private final PriorityEventPool<DamageSourceComponent$IUpdateDamageSource> damageSourceEvents;

	public DamageSourceComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.DAMAGE_SOURCE.get())),var1);
		this.damageSourceEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public void updateDamageSource(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		DamageSourceComponent var4 = this;
		LivingEntity var5 = var1;
		LivingEntity var6 = var2;
		DamageSource var7 = var3;
		final LivingEntity var5_f1 = var5;
		final LivingEntity var6_f2 = var6;
		final DamageSource var7_f3 = var7;
		Consumer<? super DamageSourceComponent$IUpdateDamageSource> var8 = (l0) -> {
			l0.updateDamageSource(var5_f1,var6_f2,var7_f3,this.getAbility());
		};
		((java.util.stream.Stream)(this.damageSourceEvents.getEventsStream())).forEach((Consumer)(var8));
	}
	public DamageSourceComponent addDamageSourceUpdateEvent(int var1, DamageSourceComponent$IUpdateDamageSource var2) {
		this.damageSourceEvents.addEvent(var1,var2);
		return this;
	}
}