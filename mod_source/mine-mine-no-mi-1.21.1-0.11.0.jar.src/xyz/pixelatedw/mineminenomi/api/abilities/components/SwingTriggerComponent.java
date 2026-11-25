package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSwingTriggerPacket;

public class SwingTriggerComponent extends AbilityComponent<IAbility> {
	// Fields
	private final PriorityEventPool<SwingTriggerComponent$IOnSwingEvent> swingEvents;
	private long lastSwingTime;

	public SwingTriggerComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.SWING_TRIGGER.get())),var1);
		this.swingEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public SwingTriggerComponent addSwingEvent(SwingTriggerComponent$IOnSwingEvent var1) {
		this.swingEvents.addEvent(var1);
		return this;
	}
	public SwingTriggerComponent addSwingEvent(int var1, SwingTriggerComponent$IOnSwingEvent var2) {
		this.swingEvents.addEvent(var1,var2);
		return this;
	}
	public void swing(LivingEntity var1) {
		LivingEntity var3 = null;
		this.ensureIsRegistered();
		if ((this.getAbility().hasComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())))) && (((DisableComponent)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get())).isDisabled())){
			return;
		}else{
			if ((this.getAbility().hasComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())))) && (((CooldownComponent)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).get())).isOnCooldown())){
				return;
			}else{
				SwingTriggerComponent var2 = this;
				var3 = var1;
				final LivingEntity var3_f1 = var3;
				Consumer<SwingTriggerComponent$IOnSwingEvent> var4 = (l0) -> {
			l0.swing(var3_f1,this.getAbility());
		};
				((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.swingEvents)).dispatch((Consumer)(var4));
				this.lastSwingTime = var1.level().getGameTime();
				if (!(var1.level().isClientSide)){
					ModNetwork.sendToAllTrackingAndSelf(new SSwingTriggerPacket((LivingEntity)(var1),(IAbility)(this.getAbility())),var1);
				}
				return;
			}
		}
	}
	public long getLastSwingTime() {
		return this.lastSwingTime;
	}
}