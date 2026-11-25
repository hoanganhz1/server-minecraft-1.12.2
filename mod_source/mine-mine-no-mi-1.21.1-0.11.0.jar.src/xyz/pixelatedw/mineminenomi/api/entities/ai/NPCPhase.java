package xyz.pixelatedw.mineminenomi.api.entities.ai;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;

public abstract class NPCPhase<E extends Mob> {
	// Fields
	private String name;
	private final E entity;
	private final Set<Pair<Integer, Goal>> availableGoals;
	private GoalSelector goals;

	public NPCPhase(String var1, E var2) {
		this.availableGoals = Sets.newLinkedHashSet();
		this.name = var1;
		this.entity = var2;
		this.goals = new GoalSelector(var2.level().getProfilerSupplier());
	}
	public void addGoal(int var1, Goal var2) {
		if (this.availableGoals.stream().anyMatch((Predicate)(((Predicate<Pair>)((l0) -> {
			return ((java.lang.Object)(((Goal)(l0.getValue())))).equals((Object)(var2));
		}))))){
			return;
		}else{
			((java.util.Set)(this.availableGoals)).add((Object)(ImmutablePair.of(Integer.valueOf(var1),var2)));
			this.goals.addGoal(var1,var2);
			return;
		}
	}
	public void addGoals(NPCPhase<E> var1) {
		Iterator var2 = var1.getGoalsSet().iterator();
		do{
			if (var2.hasNext()){
				Pair var3 = ((Pair)(var2.next()));
				this.addGoal(((Integer)(var3.getKey())).intValue(),((Goal)(var3.getValue())));
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public final void startPhase() {
		((xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase)(this)).start((Mob)(this.entity));
	}
	public abstract void start(E var0);
	public final void stopPhase() {
		this.goals.getAvailableGoals().forEach((Consumer)(((Consumer<WrappedGoal>)(WrappedGoal::stop))));
		((xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase)(this)).stop((Mob)(this.entity));
	}
	public abstract void stop(E var0);
	public final void tick() {
		if (((((this.entity.level().getServer().getTickCount()) + (this.entity.getId())) % (2)) != (0)) && ((this.entity.tickCount) > (1))){
			this.goals.tickRunningGoals(false);
		}else{
			this.goals.tick();
		}
		this.doTick();
	}
	public abstract void doTick();
	public String getName() {
		return this.name;
	}
	public String toString() {
		return this.getName();
	}
	public <T extends IPhasesEntity> boolean isActive(T var1) {
		NPCPhase var2 = var1.getPhaseManager().getCurrentPhase();
		if ((var2) == (null)){
			return false;
		}else{
			return ((java.lang.Object)(var2)).equals((Object)(this));
		}
	}
	public Set<Pair<Integer, Goal>> getGoalsSet() {
		return this.availableGoals;
	}
}