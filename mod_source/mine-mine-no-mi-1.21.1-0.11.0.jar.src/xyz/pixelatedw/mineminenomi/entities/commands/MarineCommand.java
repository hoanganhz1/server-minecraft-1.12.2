package xyz.pixelatedw.mineminenomi.entities.commands;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class MarineCommand implements ICommandReceiver {
	// Fields
	private final Mob owner;
	private final MarineRank neededRank;
	private long lastCommandTime;
	private LivingEntity lastCommandSender;
	private NPCCommand currentCommand;

	public MarineCommand(Mob var1, MarineRank var2) {
		this.currentCommand = NPCCommand.IDLE;
		this.owner = var1;
		this.neededRank = var2;
	}
	public boolean canReceiveCommandFrom(LivingEntity var1) {
		if (!(((Boolean)(EntityStatsData.get((LivingEntity)(this.owner)).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isMarine)))).orElse(Boolean.valueOf(false)))).booleanValue())){
			return false;
		}else{
			EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
			if ((var2) == (null)){
				return false;
			}else{
				if (!(var2.isMarine())){
					return false;
				}else{
					if (!(var2.hasRank(this.neededRank))){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public void setCurrentCommand(@Nullable() LivingEntity var1, NPCCommand var2) {
		this.lastCommandTime = this.owner.level().getGameTime();
		this.lastCommandSender = var1;
		this.currentCommand = var2;
	}
	public boolean canMaintainCommand() {
		if (((this.lastCommandSender) != (null)) && ((this.lastCommandSender.isAlive()) && (((Boolean)(EntityStatsData.get(this.lastCommandSender).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isRogue)))).orElse(Boolean.valueOf(false)))).booleanValue()))){
			return false;
		}else{
			return true;
		}
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