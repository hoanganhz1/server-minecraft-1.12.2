package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.GunArrayAbility;
import xyz.pixelatedw.mineminenomi.abilities.MH5Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class WootzArmorModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","wootz_armor"),"main");
	private final ModelPart armor;
	private final ModelPart bodyArmor;
	private final ModelPart rightShoulderBase;
	private final ModelPart rightShoulder1;
	private final ModelPart rightShoulder2;
	private final ModelPart rightShoulderGuns;
	private final ModelPart rightShoulderGun1;
	private final ModelPart rightShoulderGun2;
	private final ModelPart leftShoulderBase;
	private final ModelPart leftShoulder1;
	private final ModelPart leftShoulder2;
	private final ModelPart leftShoulderGuns;
	private final ModelPart leftShoulderGun2;
	private final ModelPart leftShoulderGun1;
	private final ModelPart rightArmArmor;
	private final ModelPart leftArmArmor;
	private boolean isChargingMH5;

	public WootzArmorModel(ModelPart var1) {
		super(var1);
		this.isChargingMH5 = false;
		this.armor = var1.getChild("armor");
		this.bodyArmor = this.armor.getChild("bodyArmor");
		this.rightShoulderBase = this.armor.getChild("rightShoulderBase");
		this.rightShoulder1 = this.rightShoulderBase.getChild("rightShoulder1");
		this.rightShoulder2 = this.rightShoulder1.getChild("rightShoulder2");
		this.rightShoulderGuns = this.armor.getChild("rightShoulderGuns");
		this.rightShoulderGun1 = this.rightShoulderGuns.getChild("rightShoulderGun1");
		this.rightShoulderGun2 = this.rightShoulderGuns.getChild("rightShoulderGun2");
		this.leftShoulderBase = this.armor.getChild("leftShoulderBase");
		this.leftShoulder1 = this.leftShoulderBase.getChild("leftShoulder1");
		this.leftShoulder2 = this.leftShoulder1.getChild("leftShoulder2");
		this.leftShoulderGuns = this.armor.getChild("leftShoulderGuns");
		this.leftShoulderGun2 = this.leftShoulderGuns.getChild("leftShoulderGun2");
		this.leftShoulderGun1 = this.leftShoulderGuns.getChild("leftShoulderGun1");
		this.rightArmArmor = var1.getChild("rightArmArmor");
		this.leftArmArmor = var1.getChild("leftArmArmor");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("armor",CubeListBuilder.create(),PartPose.offset(0F,0.25F,0F));
		var2.addOrReplaceChild("bodyArmor",CubeListBuilder.create().texOffs(0,0).addBox(-5.5F,0F,-3F,11F,11F,6F,new CubeDeformation(0F)),PartPose.offset(0F,-0.25F,0F));
		var2.addOrReplaceChild("rightShoulderBase",CubeListBuilder.create().texOffs(21,17).addBox(-4F,0F,-7F,8F,1F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-8F,0.25F,3.4F,0F,0.0456F,-0.5236F)).addOrReplaceChild("rightShoulder1",CubeListBuilder.create().texOffs(0,24).addBox(-3.5F,-1F,-6.5F,7F,1F,7F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("rightShoulder2",CubeListBuilder.create().texOffs(0,17).addBox(-11.0531F,-1.5F,-2.6F,6F,1F,6F,new CubeDeformation(0F)),PartPose.offset(8F,-0.5F,-3.4F));
		PartDefinition var3 = var2.addOrReplaceChild("rightShoulderGuns",CubeListBuilder.create().texOffs(0,0).addBox(0F,0F,0F,0F,0F,0F,new CubeDeformation(0F)),PartPose.offset(-6F,-0.25F,0F));
		var3.addOrReplaceChild("rightShoulderGun1",CubeListBuilder.create().texOffs(48,16).addBox(-1.5F,-2.3F,-4F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var3.addOrReplaceChild("rightShoulderGun2",CubeListBuilder.create().texOffs(48,16).addBox(-1.5F,-2.3F,-4F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offset(-3.9F,1F,0F));
		var2.addOrReplaceChild("leftShoulderBase",CubeListBuilder.create().texOffs(21,17).mirror().addBox(-4F,0F,-7F,8F,1F,8F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(8F,0.25F,3.4F,0F,-0.0456F,0.5236F)).addOrReplaceChild("leftShoulder1",CubeListBuilder.create().texOffs(0,24).mirror().addBox(-3.5F,-1F,-6.5F,7F,1F,7F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0F,0F,0F)).addOrReplaceChild("leftShoulder2",CubeListBuilder.create().texOffs(0,17).mirror().addBox(5.0531F,-1.5F,-2.6F,6F,1F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-8F,-0.5F,-3.4F));
		PartDefinition var4 = var2.addOrReplaceChild("leftShoulderGuns",CubeListBuilder.create().texOffs(0,0).addBox(0F,0F,0F,0F,0F,0F,new CubeDeformation(0F)),PartPose.offset(6F,-0.25F,0F));
		var4.addOrReplaceChild("leftShoulderGun2",CubeListBuilder.create().texOffs(48,16).addBox(-1.5F,-2.3F,-4F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offset(3.9F,1F,0F));
		var4.addOrReplaceChild("leftShoulderGun1",CubeListBuilder.create().texOffs(48,16).addBox(-1.5F,-2.3F,-4F,3F,3F,5F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F));
		var1.addOrReplaceChild("rightArmArmor",CubeListBuilder.create().texOffs(34,0).addBox(-3.5F,-2F,-2.5F,5F,10F,5F,new CubeDeformation(0F)),PartPose.offset(-6F,1.75F,0F));
		var1.addOrReplaceChild("leftArmArmor",CubeListBuilder.create().texOffs(34,0).addBox(-1.5F,-2F,-2.5F,5F,10F,5F,new CubeDeformation(0F)),PartPose.offset(6F,1.75F,0F));
		return LayerDefinition.create(var0,64,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.armor.copyFrom(this.body);
		this.leftArmArmor.copyFrom(this.leftArm);
		this.rightArmArmor.copyFrom(this.rightArm);
		this.armor.render(var1,var2,var3,var4,var5);
		this.rightArmArmor.render(var1,var2,var3,var4,var5);
		this.leftArmArmor.render(var1,var2,var3,var4,var5);
		if (this.isChargingMH5){
			var1.pushPose();
			var1.scale(1.5F,1.5F,1.5F);
			var1.translate(0D,-0.1D,0.2D);
			this.leftShoulderBase.render(var1,var2,var3,var4,-1);
			var1.popPose();
		}
	}
	public void updateState(LivingEntity var1) {
		boolean var2 = false;
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			GunArrayAbility var4 = ((GunArrayAbility)(var3.getEquippedAbility(((AbilityCore)(GunArrayAbility.INSTANCE.get())))));
			if (((var4) != (null)) && (((Boolean)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Boolean>)(ContinuousComponent::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue())){
				var2 = true;
			}
			MH5Ability var5 = ((MH5Ability)(var3.getEquippedAbility(((AbilityCore)(MH5Ability.INSTANCE.get())))));
			if (((var5) != (null)) && (((Boolean)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))).map((Function)(((Function<ChargeComponent, Boolean>)(ChargeComponent::isCharging)))).orElse(Boolean.valueOf(false)))).booleanValue())){
				this.isChargingMH5 = true;
				var2 = false;
			}
			if (this.isChargingMH5){
				this.leftShoulderBase.xRot = (float)(Math.toRadians(80D));
				this.leftShoulderBase.x = 2F;
				this.leftShoulderBase.z = -7F;
			}
			if (var2){
				this.leftShoulderBase.xRot = (float)(Math.toRadians(-70D));
				this.rightShoulderBase.xRot = (float)(Math.toRadians(-70D));
				this.leftShoulderGuns.visible = true;
				this.rightShoulderGuns.visible = true;
			}else{
				this.leftShoulderBase.xRot = (float)(Math.toRadians(0D));
				this.rightShoulderBase.xRot = (float)(Math.toRadians(0D));
				this.leftShoulderGuns.visible = false;
				this.rightShoulderGuns.visible = false;
			}
			return;
		}
	}
}