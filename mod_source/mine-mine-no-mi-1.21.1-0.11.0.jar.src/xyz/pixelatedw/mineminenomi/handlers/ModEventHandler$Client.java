package xyz.pixelatedw.mineminenomi.handlers;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import xyz.pixelatedw.mineminenomi.init.ModArmors$Client;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities$Client;
import xyz.pixelatedw.mineminenomi.init.ModEntities$Client;
import xyz.pixelatedw.mineminenomi.init.ModLayers;
import xyz.pixelatedw.mineminenomi.init.ModMobs$Client;
import xyz.pixelatedw.mineminenomi.init.ModMorphs$Client;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles$Client;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

@EventBusSubscriber(modid="mineminenomi", value={Dist.CLIENT})
public class ModEventHandler$Client {
	// Fields
	public static final ResourceLocation COMBAT_INDICATORS = ResourceLocation.fromNamespaceAndPath("mineminenomi","combat_indicators");
	public static final ResourceLocation OOB_VIGNETTE = ResourceLocation.fromNamespaceAndPath("mineminenomi","oob_vignette");

	public static void registerGuiLayers(RegisterGuiLayersEvent var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	@SubscribeEvent()
	public static void creativeTabSetup(BuildCreativeModeTabContentsEvent var0) {
		if ((var0.getTabKey()) == (CreativeModeTabs.SPAWN_EGGS)){
			Iterator var1 = ModRegistry.getModSpawnEggs().iterator();
			do{
				if (var1.hasNext()){
					var0.accept((ItemLike)(((DeferredItem)(var1.next()))));
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	@SubscribeEvent()
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions var0) {
		ModBlockEntities$Client.registerLayers(var0);
		ModEntities$Client.registerLayers(var0);
		ModProjectiles$Client.registerLayers(var0);
		ModMobs$Client.registerLayers(var0);
		ModArmors$Client.registerLayers(var0);
		ModMorphs$Client.registerLayers(var0);
	}
	@SubscribeEvent()
	public static void addLayers(EntityRenderersEvent.AddLayers var0) {
		Minecraft var1 = Minecraft.getInstance();
		EntityRendererProvider.Context var2 = new EntityRendererProvider.Context(var1.getEntityRenderDispatcher(),var1.getItemRenderer(),var1.getBlockRenderer(),var1.gameRenderer.itemInHandRenderer,var1.getResourceManager(),var1.getEntityModels(),var1.font);
		ModLayers.addEntityLayers(var0);
		ModArmors$Client.addLayers(var0,var2);
		ModMorphs$Client.addLayers(var0,var2);
	}
	@SubscribeEvent()
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers var0) {
		Minecraft var1 = Minecraft.getInstance();
		EntityRendererProvider.Context var2 = new EntityRendererProvider.Context(var1.getEntityRenderDispatcher(),var1.getItemRenderer(),var1.getBlockRenderer(),var1.gameRenderer.itemInHandRenderer,var1.getResourceManager(),var1.getEntityModels(),var1.font);
		ModBlockEntities$Client.registerRenderers(var0,var2);
		ModEntities$Client.registerRenderers(var0,var2);
		ModProjectiles$Client.registerRenderers(var0,var2);
		ModMobs$Client.registerRenderers(var0,var2);
	}
}