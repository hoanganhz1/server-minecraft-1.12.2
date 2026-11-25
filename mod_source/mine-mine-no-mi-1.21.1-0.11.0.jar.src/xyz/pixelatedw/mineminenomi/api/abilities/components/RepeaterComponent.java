package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class RepeaterComponent extends AbilityComponent<IAbility> {
	// Fields
	private int maxTriggers;
	private Interval triggerInterval;
	private boolean isActive;
	private int triggers;
	private final PriorityEventPool<RepeaterComponent$IRepeaterTriggerEvent> triggerRepeaterEvents;
	private final PriorityEventPool<RepeaterComponent$IRepeaterStopEvent> stopRepeaterEvents;

	public RepeaterComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.REPEATER.get())),var1);
		this.triggerRepeaterEvents = (PriorityEventPool) (new PriorityEventPool());
		this.stopRepeaterEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public RepeaterComponent addTriggerEvent(RepeaterComponent$IRepeaterTriggerEvent var1) {
		this.triggerRepeaterEvents.addEvent(var1);
		return this;
	}
	public RepeaterComponent addTriggerEvent(int var1, RepeaterComponent$IRepeaterTriggerEvent var2) {
		this.triggerRepeaterEvents.addEvent(var1,var2);
		return this;
	}
	public RepeaterComponent addStopEvent(RepeaterComponent$IRepeaterStopEvent var1) {
		this.stopRepeaterEvents.addEvent(var1);
		return this;
	}
	public RepeaterComponent addStopEvent(int var1, RepeaterComponent$IRepeaterStopEvent var2) {
		this.stopRepeaterEvents.addEvent(var1,var2);
		return this;
	}
	public boolean hasFinished() {
		return (this.triggers) >= (this.maxTriggers);
	}
	private void resetShots() {
		this.triggers = 0;
	}
	protected void doTick(LivingEntity var1) {
		RepeaterComponent var2 = this;
		Consumer var3 = (Consumer) ((Consumer<AltModeComponent>) ((l0) -> {
			l0.setDisabled(this.isActive);
		}));
		this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.ALT_MODE.get()))).ifPresent(var3);
		if ((this.isActive) && (this.triggerInterval.canTick())){
			this.triggers = (this.triggers) + (1);
			RepeaterComponent var4 = this;
			LivingEntity var5 = var1;
			final LivingEntity var5_f1 = var5;
			Consumer<RepeaterComponent$IRepeaterTriggerEvent> var6 = (l0) -> {
			l0.trigger(var5_f1,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.triggerRepeaterEvents)).dispatch((Consumer)(var6));
			if (this.hasFinished()){
				this.stop(var1);
			}else{
				return;
			}
		}
	}
	public void start(LivingEntity var1, int var2, int var3) {
		this.ensureIsRegistered();
		if (this.isActive){
			return;
		}else{
			this.isActive = true;
			this.maxTriggers = var2;
			this.triggerInterval = new Interval(var3);
			this.resetShots();
			return;
		}
	}
	public void stop(LivingEntity var1) {
		if (!(this.isActive)){
			return;
		}else{
			this.isActive = false;
			RepeaterComponent var2 = this;
			LivingEntity var3 = var1;
			final LivingEntity var3_f1 = var3;
			Consumer<RepeaterComponent$IRepeaterStopEvent> var4 = (l0) -> {
			l0.stop(var3_f1,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.stopRepeaterEvents)).dispatch((Consumer)(var4));
			return;
		}
	}
	public int getTriggerCount() {
		return this.triggers;
	}
	public int getMaxTriggers() {
		return this.maxTriggers;
	}
}