package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.extensions.IAgeableListModelExtension;

@OnlyIn(value=Dist.CLIENT)
public abstract class StuckInMorphBodyLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public StuckInMorphBodyLayer(LivingEntityRenderer<T, M> var1) {
		super(var1);
	}
	protected abstract int numStuck(T var0);
	protected abstract void renderStuckItem(PoseStack var0, MultiBufferSource var1, int var2, Entity var3, float var4, float var5, float var6, float var7);
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		int var11 = this.numStuck(var4);
		RandomSource var12 = RandomSource.create((long)(var4.getId()));
		if ((var11) > (0)){
			int var13 = 0;
			do{
				if ((var13) < (var11)){
					var1.pushPose();
					ModelPart var14 = this.getRandomPart(var12);
					if (((var14) != (null)) && ((!(var14.isEmpty())) && (var14.visible))){
						ModelPart.Cube var15 = var14.getRandomCube(var12);
						var14.translateAndRotate(var1);
						float var16 = var12.nextFloat();
						float var17 = var12.nextFloat();
						float var18 = var12.nextFloat();
						float var19 = (Mth.lerp(var16,var15.minX,var15.maxX)) / (16F);
						float var20 = (Mth.lerp(var17,var15.minY,var15.maxY)) / (16F);
						var1.translate(var19,var20,(Mth.lerp(var18,var15.minZ,var15.maxZ)) / (16F));
						var16 = (-1F) * (((var16) * (2F)) - (1F));
						var17 = (-1F) * (((var17) * (2F)) - (1F));
						var18 = (-1F) * (((var18) * (2F)) - (1F));
						this.renderStuckItem(var1,var2,var3,var4,var16,var17,var18,var7);
						var1.popPose();
					}
					var13++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	@Nullable()
	private ModelPart getRandomPart(RandomSource var1) {
		EntityModel var2 = this.getParentModel();
		if (var2 instanceof PlayerModel){
			return ((PlayerModel)(var2)).getRandomModelPart(var1);
		}else{
			EntityModel var3 = this.getParentModel();
			if (var3 instanceof AgeableListModel){
				Iterable var4 = ((IAgeableListModelExtension)(((AgeableListModel)(var3)))).getParts();
				return ((ModelPart)(Iterables.get(var4,var1.nextInt(Iterables.size(var4)))));
			}else{
				EntityModel var4_1 = this.getParentModel();
				if (var4_1 instanceof HierarchicalModel){
					return ((ModelPart)(((java.util.Optional)(((HierarchicalModel)(var4_1)).root().getAllParts().findAny())).orElse((Object)(null))));
				}else{
					return null;
				}
			}
		}
	}
}