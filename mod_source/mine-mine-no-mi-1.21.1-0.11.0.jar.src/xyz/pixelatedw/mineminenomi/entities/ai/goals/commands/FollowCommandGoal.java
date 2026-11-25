package xyz.pixelatedw.mineminenomi.entities.ai.goals.commands;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class FollowCommandGoal extends TickedGoal<Mob> {
	// Fields
	private ICommandReceiver command;

	public FollowCommandGoal(Mob var1, ICommandReceiver var2) {
		super(var1);
		this.command = var2;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP,Goal.Flag.LOOK,Goal.Flag.MOVE,Goal.Flag.TARGET));
	}
	public boolean canUse() {
		if ((this.command) == (null)){
			return false;
		}else{
			if (!(this.command.getCurrentCommand().equals(NPCCommand.FOLLOW))){
				return false;
			}else{
				return true;
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(this.command.getCurrentCommand().equals(NPCCommand.FOLLOW))){
			return false;
		}else{
			if (((this.command.getLastCommandSender()) != (null)) && (this.command.getLastCommandSender().isAlive())){
				return true;
			}else{
				return false;
			}
		}
	}
	public void start() {
		super.start();
		Mob var1 = this.entity;
		if (var1 instanceof TamableAnimal){
			((TamableAnimal)(var1)).setOrderedToSit(false);
		}
		this.entity.setTarget((LivingEntity)(null));
	}
	public void tick() {
		super.tick();
		this.entity.setTarget((LivingEntity)(null));
		this.entity.getNavigation().moveTo((Entity)(this.command.getLastCommandSender()),1.5D);
	}
	public void stop() {
		super.stop();
	}
}