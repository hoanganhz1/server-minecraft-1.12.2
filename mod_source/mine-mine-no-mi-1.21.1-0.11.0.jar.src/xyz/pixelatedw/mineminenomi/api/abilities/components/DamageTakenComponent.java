package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class DamageTakenComponent extends AbilityComponent<IAbility> {
	// Fields
	private final PriorityEventPool<DamageTakenComponent$IGetHitEvent> onAttackEvents;
	private final PriorityEventPool<DamageTakenComponent$IGetHitEvent> onHurtEvents;
	private final PriorityEventPool<DamageTakenComponent$IGetHitEvent> onDamageEvents;

	public DamageTakenComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.DAMAGE_TAKEN.get())),var1);
		this.onAttackEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onHurtEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onDamageEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public DamageTakenComponent addOnAttackEvent(DamageTakenComponent$IGetHitEvent var1) {
		this.onAttackEvents.addEvent(100,var1);
		return this;
	}
	public DamageTakenComponent addOnAttackEvent(int var1, DamageTakenComponent$IGetHitEvent var2) {
		this.onAttackEvents.addEvent(var1,var2);
		return this;
	}
	public DamageTakenComponent addOnHurtEvent(DamageTakenComponent$IGetHitEvent var1) {
		this.onHurtEvents.addEvent(100,var1);
		return this;
	}
	public DamageTakenComponent addOnHurtEvent(int var1, DamageTakenComponent$IGetHitEvent var2) {
		this.onHurtEvents.addEvent(var1,var2);
		return this;
	}
	public DamageTakenComponent addOnDamageEvent(DamageTakenComponent$IGetHitEvent var1) {
		this.onDamageEvents.addEvent(100,var1);
		return this;
	}
	public DamageTakenComponent addOnDamageEvent(int var1, DamageTakenComponent$IGetHitEvent var2) {
		this.onDamageEvents.addEvent(var1,var2);
		return this;
	}
	public float checkDamageTaken(LivingEntity var1, DamageSource var2, float var3, DamageTakenComponent$DamageState var4) {
		this.ensureIsRegistered();
		if ((!(this.getAbility().isAbilityDisabled())) && (!(this.getAbility().isAbilityPaused()))){
			List var5 = new ArrayList();
			switch (var4.ordinal()){
			case 0:
				var5 = this.onAttackEvents.getEventsStream().toList();
				break;
			case 1:
				var5 = this.onHurtEvents.getEventsStream().toList();
				break;
			case 2:
				var5 = this.onDamageEvents.getEventsStream().toList();
				break;
			}
			Iterator var6 = var5.iterator();
			do{
				if (var6.hasNext()){
					var3 = ((DamageTakenComponent$IGetHitEvent)(var6.next())).damageCheck(var1,this.getAbility(),var2,var3);
					continue;
				}else{
					break;
				}
			} while (true);
			return var3;
		}else{
			return var3;
		}
	}
}