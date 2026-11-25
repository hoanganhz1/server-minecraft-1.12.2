package xyz.pixelatedw.mineminenomi.entities.ai.goals.commands;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class StayCommandGoal extends TickedGoal<Mob> {
	// Fields
	private ICommandReceiver command;

	public StayCommandGoal(Mob var1, ICommandReceiver var2) {
		super(var1);
		this.command = var2;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP,Goal.Flag.MOVE,Goal.Flag.TARGET));
	}
	public boolean canUse() {
		if ((this.command) == (null)){
			return false;
		}else{
			if (!(this.command.getCurrentCommand().equals(NPCCommand.STAY))){
				return false;
			}else{
				return true;
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(this.command.getCurrentCommand().equals(NPCCommand.STAY))){
			return false;
		}else{
			if ((this.entity.getLastHurtByMob()) != (null)){
				this.entity.setTarget(this.entity.getLastHurtByMob());
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
		Mob var1 = this.entity;
		if (var1 instanceof TamableAnimal){
			TamableAnimal var2 = ((TamableAnimal)(var1));
			var2.setInSittingPose(true);
			var2.setOrderedToSit(true);
		}
	}
	public void tick() {
		super.tick();
		this.entity.setTarget((LivingEntity)(null));
		this.entity.getNavigation().stop();
	}
	public void stop() {
		super.stop();
		Mob var1 = this.entity;
		if (var1 instanceof TamableAnimal){
			TamableAnimal var2 = ((TamableAnimal)(var1));
			var2.setInSittingPose(false);
			var2.setOrderedToSit(false);
		}
	}
}