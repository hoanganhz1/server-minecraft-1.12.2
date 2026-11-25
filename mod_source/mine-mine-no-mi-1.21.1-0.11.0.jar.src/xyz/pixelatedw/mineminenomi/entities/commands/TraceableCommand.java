package xyz.pixelatedw.mineminenomi.entities.commands;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class TraceableCommand implements ICommandReceiver {
	// Fields
	private final Mob entity;
	private long lastCommandTime;
	private LivingEntity lastCommandSender;
	private NPCCommand currentCommand;

	public TraceableCommand(Mob var1) {
		this.currentCommand = NPCCommand.IDLE;
		this.entity = var1;
		CommandAbility.addCommandGoals(var1,this);
	}
	public boolean canReceiveCommandFrom(LivingEntity var1) {
		Mob var2 = this.entity;
		int var3 = 0;
		if (var2 instanceof TraceableEntity){
			TraceableEntity var4 = ((TraceableEntity)(var2));
			if (var4.getOwner().equals(var1)){
				var3 = 1;
			}else{
				var3 = 0;
			}
		}else{
			var3 = 0;
		}
		return (((var3) & 1) != 0);
	}
	public void setCurrentCommand(@Nullable() LivingEntity var1, NPCCommand var2) {
		this.lastCommandTime = this.entity.level().getGameTime();
		this.lastCommandSender = var1;
		this.currentCommand = var2;
	}
	public NPCCommand getCurrentCommand() {
		return this.currentCommand;
	}
	@Nullable()
	public @Nullable() LivingEntity getLastCommandSender() {
		return this.lastCommandSender;
	}
	public long getLastCommandTime() {
		return this.lastCommandTime;
	}
}