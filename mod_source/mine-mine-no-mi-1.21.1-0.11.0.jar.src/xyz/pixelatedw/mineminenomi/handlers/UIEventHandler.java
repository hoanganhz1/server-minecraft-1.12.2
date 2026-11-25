package xyz.pixelatedw.mineminenomi.handlers;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.UpdateCombatStateEvent;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.handlers.ui.BossUIHandler;
import xyz.pixelatedw.mineminenomi.handlers.ui.CombatModeHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.CraftingHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.OneFruitPerWorldHandler;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

@EventBusSubscriber(modid="mineminenomi", value={Dist.CLIENT})
public class UIEventHandler {
	@SubscribeEvent()
	public static void onRenderBossEventOverlay(CustomizeGuiOverlayEvent.BossEventProgress var0) {
		LocalPlayer var1 = Minecraft.getInstance().player;
		GuiGraphics var2 = var0.getGuiGraphics();
		LerpingBossEvent var3 = var0.getBossEvent();
		int var4 = var0.getX();
		int var5 = BossUIHandler.renderBossHealth((AbstractClientPlayer)(var1),var2,var3,var4,var0.getY());
		if ((var5) >= (0)){
			var0.setIncrement(var5);
		}
	}
	@SubscribeEvent()
	public static void onCombatStateUpdates(UpdateCombatStateEvent var0) {
		if (var0.getEntity().level().isClientSide){
			if (ClientConfig.hasCombatUpdateChatMessageEnabled()){
				LivingEntity var1 = var0.getEntity();
				if (var1 instanceof Player){
					Player var2 = ((Player)(var1));
					var2.displayClientMessage((var0.getCurrentInCombatState()) ? (ModI18n.INFO_ENTERING_COMBAT) : (ModI18n.INFO_LEAVING_COMBAT),false);
				}
			}
			CombatModeHandler.isInCombat = var0.getCurrentInCombatState();
		}
	}
	@SubscribeEvent()
	public static void onScreenInit(ScreenEvent.Init.Post var0) {
		Minecraft var1 = Minecraft.getInstance();
		Screen var2 = var0.getScreen();
		int var3 = ((var2.width) - (256)) / (2);
		int var4 = ((var2.height) - (256)) / (2);
		if (var2 instanceof CraftingScreen){
			CraftingScreen var5 = ((CraftingScreen)(var2));
			Objects.requireNonNull(var0);
			CraftingHandler.initNewDyeLayerWidget(var1,var5,(Consumer)(((Consumer<GuiEventListener>)(var0::addListener))));
		}
	}
	@SubscribeEvent()
	public static void mouseClickEvent(ScreenEvent.MouseButtonPressed.Pre var0) {
		if ((Minecraft.getInstance().player) == (null)){
			return;
		}else{
			if (ServerConfig.hasOneFruitPerWorldExtendedLogic()){
				var0.setCanceled(OneFruitPerWorldHandler.cancelMouseClick(var0.getScreen()));
			}
			return;
		}
	}
	@SubscribeEvent()
	public static void mouseReleasedEvent(ScreenEvent.MouseButtonReleased.Pre var0) {
		if ((Minecraft.getInstance().player) == (null)){
			return;
		}else{
			if (ServerConfig.hasOneFruitPerWorldExtendedLogic()){
				var0.setCanceled(OneFruitPerWorldHandler.cancelMouseReleaseEvent(var0.getScreen()));
			}
			return;
		}
	}
}