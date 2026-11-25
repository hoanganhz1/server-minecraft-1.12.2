package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class NPCPhaseManager {
	// Fields
	private final Mob entity;
	private NPCPhase<?> currentPhase;
	private NPCPhase<?> previousPhase;

	public NPCPhaseManager(Mob var1) {
		this.entity = var1;
	}
	public void setPhase(NPCPhase<?> var1) {
		if (((this.currentPhase) != (null)) && (((java.lang.Object)(var1)).equals((Object)(this.currentPhase)))){

		}else{
			if ((this.currentPhase) != (null)){
				this.currentPhase.stopPhase();
			}
			this.previousPhase = (NPCPhase) (this.currentPhase);
			this.currentPhase = var1;
			this.currentPhase.startPhase();
			if (FGCommand.SHOW_NPC_ABILITY_LOG){
				WyDebug.debug(String.format("Switched %s's phase to: %s",new Object[]{this.entity.getDisplayName().getString(),this.currentPhase.toString()}));
			}else{
				return;
			}
		}
	}
	public void tick() {
		if (this.entity.hasEffect((Holder)(ModEffects.IN_EVENT))){
			return;
		}else{
			if ((this.currentPhase) != (null)){
				this.currentPhase.tick();
			}
			return;
		}
	}
	@Nullable()
	public @Nullable() NPCPhase<?> getPreviousPhase() {
		return (NPCPhase<?>) (NPCPhase) (this.previousPhase);
	}
	@Nullable()
	public @Nullable() NPCPhase<?> getCurrentPhase() {
		return (NPCPhase<?>) (NPCPhase) (this.currentPhase);
	}
	@Nullable()
	public @Nullable() boolean isCurrentPhase(NPCPhase<?> var1) {
		return ((java.lang.Object)(this.currentPhase)).equals((Object)(var1));
	}
}