package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.pixelatedw.mineminenomi.models.blocks.FlagModel;
import xyz.pixelatedw.mineminenomi.models.blocks.WantedPosterModel;
import xyz.pixelatedw.mineminenomi.renderers.blocks.FlagBlockEntityRenderer;
import xyz.pixelatedw.mineminenomi.renderers.blocks.WantedPosterBlockEntityRenderer;

public class ModBlockEntities$Client {
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions var0) {
		var0.registerLayerDefinition(WantedPosterModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(WantedPosterModel::createLayer))));
		var0.registerLayerDefinition(WantedPosterModel.FACE_LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(WantedPosterModel::createFaceLayer))));
		var0.registerLayerDefinition(WantedPosterModel.FACE_OVERLAY_LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(WantedPosterModel::createFaceOverlayLayer))));
		var0.registerLayerDefinition(FlagModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(FlagModel::createBodyLayer))));
	}
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers var0, EntityRendererProvider.Context var1) {
		var0.registerBlockEntityRenderer(((BlockEntityType)(ModBlockEntities.WANTED_POSTER.get())),(BlockEntityRendererProvider)(((net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider)(WantedPosterBlockEntityRenderer::new))));
		var0.registerBlockEntityRenderer(((BlockEntityType)(ModBlockEntities.FLAG.get())),(BlockEntityRendererProvider)(((net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider)(FlagBlockEntityRenderer::new))));
	}
}