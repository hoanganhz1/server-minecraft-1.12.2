package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.function.Consumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class PauseTickComponent extends AbilityComponent<IAbility> {
	// Fields
	private final PriorityEventPool<PauseTickComponent$IPauseEvent> pauseEvents;
	private final PriorityEventPool<PauseTickComponent$IResumeEvent> resumeEvents;
	private boolean isPaused;

	public PauseTickComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.PAUSE_TICK.get())),var1);
		this.pauseEvents = (PriorityEventPool) (new PriorityEventPool());
		this.resumeEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public void setPause(LivingEntity var1, boolean var2) {
		this.ensureIsRegistered();
		this.isPaused = var2;
		if (this.isPaused){
			PauseTickComponent var3 = this;
			LivingEntity var4 = var1;
			final LivingEntity var4_f1 = var4;
			Consumer<PauseTickComponent$IPauseEvent> var5 = (l0) -> {
			l0.pause(var4_f1,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.pauseEvents)).dispatch((Consumer)(var5));
		}else{
			PauseTickComponent var3_1 = this;
			LivingEntity var4_1 = var1;
			final LivingEntity var4_1_f1 = var4_1;
			Consumer<PauseTickComponent$IResumeEvent> var5_1 = (l0) -> {
			l0.resume(var4_1_f1,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.resumeEvents)).dispatch((Consumer)(var5_1));
		}
	}
	public boolean isPaused() {
		return this.isPaused;
	}
	public PauseTickComponent addPauseEvent(int var1, PauseTickComponent$IPauseEvent var2) {
		this.pauseEvents.addEvent(var1,var2);
		return this;
	}
	public PauseTickComponent addResumeEvent(int var1, PauseTickComponent$IResumeEvent var2) {
		this.resumeEvents.addEvent(var1,var2);
		return this;
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		var2.putBoolean("isPaused",this.isPaused);
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		this.isPaused = var2.getBoolean("isPaused");
	}
}