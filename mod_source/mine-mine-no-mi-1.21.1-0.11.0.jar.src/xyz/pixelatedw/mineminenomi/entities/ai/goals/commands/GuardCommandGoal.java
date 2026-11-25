package xyz.pixelatedw.mineminenomi.entities.ai.goals.commands;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;

public class GuardCommandGoal extends TickedGoal<Mob> {
	// Fields
	private static final TargetPredicate PREDICATE = new TargetPredicate().testEnemyFaction().testSimpleInView();
	private Vec3 guardPosition;
	private long lastManualStart;
	private ICommandReceiver command;

	public GuardCommandGoal(Mob var1, ICommandReceiver var2) {
		super(var1);
		this.command = var2;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE,Goal.Flag.TARGET));
	}
	public boolean canUse() {
		if ((this.command) == (null)){
			return false;
		}else{
			if (!(this.command.getCurrentCommand().equals(NPCCommand.GUARD))){
				return false;
			}else{
				if (!(this.hasTimePassedSinceLastEnd(WyHelper.secondsToTicks(5F)))){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(this.command.getCurrentCommand().equals(NPCCommand.GUARD))){
			return false;
		}else{
			if (((this.entity.getTarget()) != (null)) && (this.entity.getTarget().isAlive())){
				return false;
			}else{
				return true;
			}
		}
	}
	public void start() {
		super.start();
		this.entity.setTarget((LivingEntity)(null));
		this.entity.setLastHurtByMob((LivingEntity)(null));
		this.entity.getNavigation().stop();
		if (((this.guardPosition) != (null)) && ((this.lastManualStart) == (this.command.getLastCommandTime()))){

		}else{
			this.guardPosition = this.entity.position();
		}
		this.lastManualStart = this.command.getLastCommandTime();
	}
	public void tick() {
		super.tick();
		if (((this.getTickCount()) % (20L)) != (0L)){
			return;
		}else{
			if ((this.entity.position().distanceTo(this.guardPosition)) > (6D)){
				this.entity.getNavigation().moveTo(this.guardPosition.x,this.guardPosition.y,this.guardPosition.z,1.25D);
			}
			LivingEntity var1 = ((LivingEntity)(((java.util.Optional)(((java.util.stream.Stream)(TargetHelper.getEntitiesInArea((LevelAccessor)(this.entity.level()),(LivingEntity)(this.entity),10D,PREDICATE,LivingEntity.class).stream())).sorted((Comparator)(TargetHelper.closestComparator(this.guardPosition))).findFirst())).orElse((Object)(null))));
			this.entity.setTarget(var1);
			return;
		}
	}
	public void stop() {
		super.stop();
	}
}