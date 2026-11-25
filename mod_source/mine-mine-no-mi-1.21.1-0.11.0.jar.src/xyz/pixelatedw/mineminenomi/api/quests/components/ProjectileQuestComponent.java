package xyz.pixelatedw.mineminenomi.api.quests.components;

import java.util.function.Consumer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public class ProjectileQuestComponent extends QuestComponent {
	// Fields
	private final PriorityEventPool<ProjectileQuestComponent$IProjectileShotEvent> onProjectileShotEvents;
	private final PriorityEventPool<ProjectileQuestComponent$IProjectileDespawnEvent> onProjectileDespawnEvents;

	public ProjectileQuestComponent(Objective var1) {
		super(var1);
		this.onProjectileShotEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onProjectileDespawnEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public ProjectileQuestComponent addOnProjectileShotEvent(ProjectileQuestComponent$IProjectileShotEvent var1) {
		return this.addOnProjectileShotEvent(100,var1);
	}
	public ProjectileQuestComponent addOnProjectileShotEvent(int var1, ProjectileQuestComponent$IProjectileShotEvent var2) {
		this.onProjectileShotEvents.addEvent(var1,var2);
		return this;
	}
	public ProjectileQuestComponent addOnProjectileDespawnEvent(ProjectileQuestComponent$IProjectileDespawnEvent var1) {
		return this.addOnProjectileDespawnEvent(100,var1);
	}
	public ProjectileQuestComponent addOnProjectileDespawnEvent(int var1, ProjectileQuestComponent$IProjectileDespawnEvent var2) {
		this.onProjectileDespawnEvents.addEvent(var1,var2);
		return this;
	}
	public void projectileShot(Player var1, Projectile var2) {
		if (!(super.isValid())){
			return;
		}else{
			ProjectileQuestComponent var3 = this;
			Player var4 = var1;
			Projectile var5 = var2;
			final Player var4_f1 = var4;
			final Projectile var5_f2 = var5;
			Consumer<ProjectileQuestComponent$IProjectileShotEvent> var6 = (l0) -> {
			l0.projectileShot(var4_f1,var5_f2,super.getObjective());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onProjectileShotEvents)).dispatch((Consumer)(var6));
			return;
		}
	}
	public void projectileDespawn(Player var1, Projectile var2) {
		if (!(super.isValid())){
			return;
		}else{
			ProjectileQuestComponent var3 = this;
			Player var4 = var1;
			Projectile var5 = var2;
			final Player var4_f1 = var4;
			final Projectile var5_f2 = var5;
			Consumer<ProjectileQuestComponent$IProjectileDespawnEvent> var6 = (l0) -> {
			l0.projectileDespawn(var4_f1,var5_f2,super.getObjective());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onProjectileDespawnEvents)).dispatch((Consumer)(var6));
			return;
		}
	}
}