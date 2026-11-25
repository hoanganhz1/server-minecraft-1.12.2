package xyz.pixelatedw.mineminenomi.init;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.pixelatedw.mineminenomi.renderers.layers.AuraLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.AwakeningSmokeLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.BodyCoatingLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.HandcuffsLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.MobEffectLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.PartialMorphFeaturesLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.RacialFeaturesLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.AbareHimatsuriLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.CandleLockLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.CycloneTempoLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.DoruBallLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.EleclawLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.GomuDawnWhipLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.PunkCrossLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.SpiralEffectLayer;

public class ModLayers {
	public static void addEntityLayers(EntityRenderersEvent.AddLayers var0) {
		Minecraft var1 = Minecraft.getInstance();
		EntityRendererProvider.Context var2 = var0.getContext();
		Iterator var3 = var1.getEntityRenderDispatcher().renderers.entrySet().iterator();
		do{
			if (var3.hasNext()){
				EntityRenderer var4 = ((EntityRenderer)(((Map.Entry)(var3.next())).getValue()));
				if (var4 instanceof LivingEntityRenderer){
					LivingEntityRenderer var5 = ((LivingEntityRenderer)(var4));
					addExtraFeatureLayers(var2,var5);
					addGenericLayers(var2,var5);
					addAbilityLayers(var2,var5);
				}
				if (var4 instanceof HumanoidMobRenderer){
					HumanoidMobRenderer var5_1 = ((HumanoidMobRenderer)(var4));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		Iterator var4_1 = var0.getSkins().iterator();
		do{
			if (var4_1.hasNext()){
				PlayerSkin.Model var5_2 = ((PlayerSkin.Model)(var4_1.next()));
				int var6 = ((var5_2) == (PlayerSkin.Model.SLIM)) ? (1) : (0);
				LivingEntityRenderer var7 = ((LivingEntityRenderer)(var0.getSkin(var5_2)));
				addExtraFeatureLayers(var2,var7);
				addGenericLayers(var2,var7);
				addAbilityLayers(var2,var7);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static <T extends LivingEntity, M extends EntityModel<T>> void addGenericLayers(EntityRendererProvider.Context var0, LivingEntityRenderer<T, M> var1) {
		var1.addLayer((RenderLayer)(new BodyCoatingLayer(var1)));
		var1.addLayer((RenderLayer)(new MobEffectLayer(var1)));
		var1.addLayer((RenderLayer)(new AuraLayer(var1)));
		var1.addLayer((RenderLayer)(new AwakeningSmokeLayer(var0,var1)));
		var1.addLayer((RenderLayer)(new SpiralEffectLayer(var0,var1)));
		var1.addLayer((RenderLayer)(new HandcuffsLayer(var0,var1)));
	}
	public static <T extends LivingEntity, M extends EntityModel<T>> void addExtraFeatureLayers(EntityRendererProvider.Context var0, LivingEntityRenderer<T, M> var1) {
		var1.addLayer((RenderLayer)(new RacialFeaturesLayer(var1)));
		var1.addLayer((RenderLayer)(new PartialMorphFeaturesLayer(var1)));
	}
	public static <T extends LivingEntity, M extends EntityModel<T>> void addAbilityLayers(EntityRendererProvider.Context var0, LivingEntityRenderer<T, M> var1) {
		var1.addLayer((RenderLayer)(new CycloneTempoLayer(var0,var1)));
		var1.addLayer((RenderLayer)(new PunkCrossLayer(var0,var1)));
		var1.addLayer((RenderLayer)(new AbareHimatsuriLayer(var1)));
		var1.addLayer((RenderLayer)(new GomuDawnWhipLayer(var0,var1)));
		var1.addLayer((RenderLayer)(new DoruBallLayer(var0,var1)));
		var1.addLayer((RenderLayer)(new CandleLockLayer(var0,var1)));
		var1.addLayer((RenderLayer)(new EleclawLayer(var1)));
	}
}