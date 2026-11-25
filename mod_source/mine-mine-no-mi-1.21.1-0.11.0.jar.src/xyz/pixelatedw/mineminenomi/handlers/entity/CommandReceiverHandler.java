package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import xyz.pixelatedw.mineminenomi.api.caps.command.CommandReceiverCapability;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.entities.commands.MarineCommand;
import xyz.pixelatedw.mineminenomi.entities.commands.TamableCommand;
import xyz.pixelatedw.mineminenomi.entities.commands.TraceableCommand;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class CommandReceiverHandler {
	public static void canMaintainCommand(Mob var0) {
		Optional var1 = CommandReceiverCapability.get((LivingEntity)(var0));
		Consumer<ICommandReceiver> var2 = (l0) -> {
			if (!(l0.canMaintainCommand())){
				l0.setCurrentCommand((LivingEntity)(null),NPCCommand.IDLE);
			}
		};
		((java.util.Optional)(var1)).ifPresent((Consumer)(var2));
	}
	public static void registerCommandReceiver(RegisterCapabilitiesEvent var0) {
		Iterator var1 = BuiltInRegistries.ENTITY_TYPE.iterator();
		do{
			if (var1.hasNext()){
				EntityType var2 = ((EntityType)(var1.next()));
				EntityType var3 = var2;
				final EntityType var3_f1 = var3;
				ICapabilityProvider var4 = (l0$sam, l1$sam) -> {
			Entity l0 = ((Entity)(l0$sam));
			Void l1 = ((Void)(l1$sam));

			if (l0 instanceof Mob){
				Mob lv2_3 = ((Mob)(l0));
				if ((var3_f1) == (ModMobs.MARINE_GRUNT.get())){
					return new MarineCommand(lv2_3,MarineRank.COMMODORE);
				}else{
					if (((var3_f1) != (ModMobs.MARINE_BRUTE.get())) && (((var3_f1) != (ModMobs.MARINE_SNIPER.get())) && ((lv2_3.getType()) != (ModMobs.MARINE_CAPTAIN.get())))){
						if (lv2_3 instanceof OwnableEntity){
							return new TamableCommand(lv2_3);
						}else{
							if ((!(lv2_3 instanceof DoppelmanEntity)) && ((!(lv2_3 instanceof NightmareSoldierEntity)) && (!(lv2_3 instanceof BlackKnightEntity)))){
								return null;
							}else{
								return new TraceableCommand(lv2_3);
							}
						}
					}else{
						return new MarineCommand(lv2_3,MarineRank.ADMIRAL);
					}
				}
			}else{
				return null;
			}
		};
				var0.registerEntity(CommandReceiverCapability.COMMAND_RECEIVER,var2,var4);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}