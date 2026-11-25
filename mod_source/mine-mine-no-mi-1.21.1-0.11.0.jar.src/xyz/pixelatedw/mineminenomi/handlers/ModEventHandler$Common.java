package xyz.pixelatedw.mineminenomi.handlers;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

@EventBusSubscriber(modid="mineminenomi")
public class ModEventHandler$Common {
	@SubscribeEvent()
	public static void onSpawnPlacement(RegisterSpawnPlacementsEvent var0) {
		ModMobs.setupSpawnPlacement(var0);
	}
	@SubscribeEvent()
	public static void onAttributeCreation(EntityAttributeCreationEvent var0) {
		ModMobs.createAttributes(var0);
	}
	@SubscribeEvent()
	public static void onAttributeModification(EntityAttributeModificationEvent var0) {
		ModAttributes.changeAttributes(var0);
	}
	@SubscribeEvent()
	public static void reloadConfig(ModConfigEvent.Reloading var0) {
		if ((var0.getConfig().getModId().equals("mineminenomi")) && (var0.getConfig().getType().equals(ModConfig.Type.SERVER))){
			MinecraftServer var1 = ServerLifecycleHooks.getCurrentServer();
			if ((var1) != (null)){
				Commands var2 = var1.getCommands();
				Objects.requireNonNull(var2);
				var1.getPlayerList().getPlayers().forEach((Consumer)(((Consumer<ServerPlayer>)(var2::sendCommands))));
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void registerTicketController(RegisterTicketControllersEvent var0) {
		var0.register(MineMineNoMi.TICKET_CONTROLLER);
	}
}