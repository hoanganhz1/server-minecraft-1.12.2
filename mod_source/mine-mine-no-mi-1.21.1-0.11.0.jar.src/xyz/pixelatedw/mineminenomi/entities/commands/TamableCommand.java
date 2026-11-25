package xyz.pixelatedw.mineminenomi.entities.commands;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public class TamableCommand implements ICommandReceiver {
	// Fields
	private Mob owner;
	private long lastCommandTime;
	private LivingEntity lastCommandSender;
	private NPCCommand currentCommand;

	public TamableCommand(Mob var1) {
		this.currentCommand = NPCCommand.IDLE;
		this.owner = var1;
	}
	public boolean canReceiveCommandFrom(LivingEntity var1) {
		Mob var2 = this.owner;
		if (var2 instanceof OwnableEntity){
			OwnableEntity var3 = ((OwnableEntity)(var2));
			if ((var3.getOwner()) == (null)){
				return false;
			}else{
				return var3.getOwner().equals(var1);
			}
		}else{
			return false;
		}
	}
	public void setCurrentCommand(@Nullable() LivingEntity var1, NPCCommand var2) {
		this.lastCommandTime = this.owner.level().getGameTime();
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