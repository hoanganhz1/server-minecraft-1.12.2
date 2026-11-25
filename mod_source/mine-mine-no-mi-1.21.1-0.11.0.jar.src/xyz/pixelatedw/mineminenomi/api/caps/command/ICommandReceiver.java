package xyz.pixelatedw.mineminenomi.api.caps.command;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;

public interface ICommandReceiver {
	public abstract void setCurrentCommand(@Nullable() LivingEntity var0, NPCCommand var1);
	public abstract NPCCommand getCurrentCommand();
	public abstract boolean canReceiveCommandFrom(LivingEntity var0);
	public default boolean canMaintainCommand() {
		return true;
	}
	public abstract long getLastCommandTime();
	@Nullable()
	public abstract @Nullable() LivingEntity getLastCommandSender();
}