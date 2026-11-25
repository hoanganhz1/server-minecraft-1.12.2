package xyz.pixelatedw.mineminenomi.handlers;

import java.util.Iterator;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import xyz.pixelatedw.mineminenomi.api.MappedTag;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.events.ability.ModifyDevilFruitEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.init.ModTags$Entities;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.world.DynamicDimensionManager;

@EventBusSubscriber(modid="mineminenomi")
public class ForgeEventHandler {
	@SubscribeEvent()
	public static void onServerTick(ServerTickEvent.Pre var0) {
		MinecraftServer var1 = ServerLifecycleHooks.getCurrentServer();
		if ((var1) != (null)){
			DynamicDimensionManager.INSTANCE.registerQueuedLevels(var1);
		}
	}
	@SubscribeEvent()
	public static void serverStarted(ServerStartedEvent var0) {
		NPCWorldData.get().setup(var0.getServer().getLevel(Level.OVERWORLD));
		ExtendedWorldData var1 = ExtendedWorldData.get();
		if (ServerConfig.isNewWorldModeEnabledFromStart()){
			var1.triggerNewWorld();
		}
	}
	@SubscribeEvent()
	public static void serverStarting(ServerStartingEvent var0) {
		Iterator var1 = ModValues.DEVIL_FRUITS.iterator();
		do{
			if (var1.hasNext()){
				AkumaNoMiItem var2 = ((AkumaNoMiItem)(var1.next()));
				ModifyDevilFruitEvent var3 = new ModifyDevilFruitEvent(var2,var2.getRawRegistryAbilities());
				NeoForge.EVENT_BUS.post((Event)(var3));
				var2.setAbilities(var3.getAbilities());
				continue;
			}else{
				break;
			}
		} while (true);
	}
	@SubscribeEvent()
	public static void serverAboutToStart(ServerAboutToStartEvent var0) {
		if (WyDebug.isDebug()){
			var0.getServer().setUsesAuthentication(false);
		}
		if ((ServerConfig.getConfigVersion()) != (4)){
			ServerConfig.updateConfigVersion();
		}
	}
	@SubscribeEvent()
	public static void addReloadListeners(AddReloadListenerEvent var0) {
		var0.addListener((PreparableReloadListener)(ModTags$Items.CONDUCTIVE));
		var0.addListener((PreparableReloadListener)(ModTags$Items.IRON));
		var0.addListener((PreparableReloadListener)(ModTags$Items.STRIKER_FUEL));
		var0.addListener((PreparableReloadListener)(ModTags$Entities.CONDUCTIVE));
	}
}