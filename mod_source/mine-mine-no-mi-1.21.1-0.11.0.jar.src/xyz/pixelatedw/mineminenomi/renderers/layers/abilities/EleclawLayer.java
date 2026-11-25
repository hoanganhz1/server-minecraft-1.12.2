package xyz.pixelatedw.mineminenomi.renderers.layers.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.electro.EleclawAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalShowerAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalTempestaAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.SulongAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.handlers.ability.MinkPassivesHandler$Client;

public class EleclawLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public EleclawLayer(RenderLayerParent<T, M> var1) {
		super(var1);
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		if (!(this.getParentModel() instanceof ArmedModel)){
			return;
		}else{
			AbilityData var11 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var4))).orElse((Object)(null))));
			if ((var11) == (null)){
				return;
			}else{
				EleclawAbility var12 = ((EleclawAbility)(var11.getEquippedAbility(((AbilityCore)(EleclawAbility.INSTANCE.get())))));
				if (((((var12) != (null)) && (var12.isContinuous())) ? (1) : (0)) == (0)){
					return;
				}else{
					SulongAbility var13 = ((SulongAbility)(var11.getEquippedAbility(((AbilityCore)(SulongAbility.INSTANCE.get())))));
					int var14 = (((var13) != (null)) && (var13.isContinuous())) ? (1) : (0);
					ElectricalTempestaAbility var15 = ((ElectricalTempestaAbility)(var11.getEquippedAbility(((AbilityCore)(ElectricalTempestaAbility.INSTANCE.get())))));
					int var16 = (((var15) != (null)) && (var15.isCharging())) ? (1) : (0);
					ElectricalShowerAbility var17 = ((ElectricalShowerAbility)(var11.getEquippedAbility(((AbilityCore)(ElectricalShowerAbility.INSTANCE.get())))));
					int var18 = (((var17) != (null)) && (var17.isCharging())) ? (1) : (0);
					if (((var16) == (0)) && ((var18) == (0))){
						int var19 = (5) + (((var14) != (0)) ? (3) : (0));
						var1.pushPose();
						var1.translate(((var4.getMainArm()) == (HumanoidArm.RIGHT)) ? (-0.05D) : (0.05D),0D,0D);
						MinkPassivesHandler$Client.renderElectro(var1,((ArmedModel)(this.getParentModel())),var2,var4,var7,0.01F,var19);
						var1.popPose();
						return;
					}else{
						return;
					}
				}
			}
		}
	}
}