package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Map;
import java.util.Optional;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModMorphs$Client;
import xyz.pixelatedw.mineminenomi.mixins.client.IEntityRenderDispatcherMixin;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class MorphsHelper$Client {
	@Nullable()
	public static <T extends LivingEntity> @Nullable() EntityRenderer<T> getMorphRenderer(T var0) {
		DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return (EntityRenderer<T>) (EntityRenderer) (null);
		}else{
			Minecraft var2 = Minecraft.getInstance();
			int var3 = (((var0) == (var2.player)) && ((var2.options.getCameraType()) == (CameraType.FIRST_PERSON))) ? (1) : (0);
			int var4 = (!(var0.getMainHandItem().isEmpty())) ? (1) : (0);
			if (((var3) != (0)) && ((var4) != (0))){
				return (EntityRenderer<T>) (EntityRenderer) (null);
			}else{
				MorphInfo var5 = ((MorphInfo)(((java.util.Optional)(var1.getCurrentMorph())).orElse((Object)(null))));
				if ((var5) == (null)){
					return (EntityRenderer<T>) (EntityRenderer) (null);
				}else{
					EntityRenderDispatcher var6 = var2.getEntityRenderDispatcher();
					EntityRenderer var7 = null;
					if (var0 instanceof AbstractClientPlayer){
						PlayerSkin.Model var8 = ((AbstractClientPlayer)(var0)).getSkin().model();
						EntityRenderer var9 = ((EntityRenderer)(((IEntityRenderDispatcherMixin)(var6)).getPlayerRenderers().get(var8)));
						var7 = ((var9) != (null)) ? (var9) : ((EntityRenderer)(((EntityRenderer)(((IEntityRenderDispatcherMixin)(var6)).getPlayerRenderers().get(PlayerSkin.Model.WIDE)))));
					}else{
						var7 = ((EntityRenderer)(var6.renderers.get(var0.getType())));
					}
					EntityRenderer var8_1 = ((EntityRenderer)(ModMorphs$Client.MORPH_RENDERERS.get(var5)));
					if (((var8_1) != (null)) && (var8_1 instanceof MorphRenderer)){
						MorphRenderer var9_1 = ((MorphRenderer)(var8_1));
						var9_1.setOriginalRenderer(var7);
						return var9_1;
					}else{
						return var7;
					}
				}
			}
		}
	}
}