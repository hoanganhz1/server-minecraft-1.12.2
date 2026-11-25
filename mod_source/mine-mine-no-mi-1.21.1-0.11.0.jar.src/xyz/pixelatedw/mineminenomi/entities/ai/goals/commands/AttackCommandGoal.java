package xyz.pixelatedw.mineminenomi.entities.ai.goals.commands;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class AttackCommandGoal extends TickedGoal<Mob> {
	// Fields
	private ICommandReceiver command;

	public AttackCommandGoal(Mob var1, ICommandReceiver var2) {
		super(var1);
		this.command = var2;
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
	}
	public boolean canUse() {
		if ((this.command) == (null)){
			return false;
		}else{
			if (!(this.command.getCurrentCommand().equals(NPCCommand.ATTACK))){
				return false;
			}else{
				return true;
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(this.command.getCurrentCommand().equals(NPCCommand.ATTACK))){
			return false;
		}else{
			if (((this.entity.getTarget()) != (null)) && (this.entity.getTarget().isAlive())){
				return true;
			}else{
				return false;
			}
		}
	}
	public void start() {
		super.start();
	}
	public void tick() {
		super.tick();
	}
	public void stop() {
		super.stop();
	}
}