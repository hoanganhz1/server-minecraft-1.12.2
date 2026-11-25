package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class DashComponent extends AbilityComponent<IAbility> {
	// Fields
	private Consumer<LivingEntity> dashConsumer;
	private int dashDuration;
	private boolean fixedCamera;

	public DashComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.DASH.get())),var1);
		this.fixedCamera = false;
	}
	public DashComponent setFixedCamera(boolean var1) {
		this.fixedCamera = var1;
		return this;
	}
	protected void doTick(LivingEntity var1) {
		if ((this.getAbility().hasComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())))) && (((DisableComponent)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get())).isDisabled())){
			return;
		}else{
			if ((this.dashDuration) > (0)){
				this.dashConsumer.accept(var1);
				int var2 = (this.dashDuration) - (1);
				this.dashDuration = var2;
				if ((var2) == (0)){

				}
			}
			return;
		}
	}
	public void dashXZ(LivingEntity var1, float var2) {
		this.move(var1,var2,0F,0F,false);
	}
	public void dashXYZ(LivingEntity var1, float var2) {
		this.dashXYZ(var1,var2,var2);
	}
	public void dashXYZ(LivingEntity var1, float var2, float var3) {
		this.move(var1,var2,var3,0F,true);
	}
	public void dashXYZ(LivingEntity var1, float var2, float var3, float var4) {
		this.move(var1,var2,var3,var4,true);
	}
	public void timedDashXZ(LivingEntity var1, float var2, int var3) {
		this.timedMove(var1,var2,0F,false,var3);
	}
	public void timedDashXYZ(LivingEntity var1, float var2, int var3) {
		this.timedMove(var1,var2,var2,true,var3);
	}
	public void timedDashXYZ(LivingEntity var1, float var2, float var3, int var4) {
		this.timedMove(var1,var2,var3,true,var4);
	}
	private void move(LivingEntity var1, float var2, float var3, float var4, boolean var5) {
		float var6 = Mth.clamp(var2,-5F,5F);
		float var7 = Mth.clamp(var3,-5F,5F);
		this.dashDuration = 0;
		Vec3 var8 = var1.getLookAngle().normalize().multiply((double)(var6),(var5) ? ((double)(var7)) : (0D),(double)(var6));
		AbilityHelper.setDeltaMovement((Entity)(var1),var8.x,(var5) ? ((var8.y) + ((double)(var4))) : (var1.getDeltaMovement().y()),var8.z);
	}
	private void timedMove(LivingEntity var1, float var2, float var3, boolean var4, int var5) {
		float var6 = Mth.clamp(var2,-5F,5F);
		float var7 = Mth.clamp(var3,-5F,5F);
		this.dashDuration = var5;
		Vec3 var8 = var1.getLookAngle().normalize().multiply((double)(var6),(var4) ? ((double)(var7)) : (0D),(double)(var6));
		DashComponent var9 = this;
		Vec3 var10 = var8;
		LivingEntity var11 = var1;
		float var12 = var6;
		boolean var13 = var4;
		float var14 = var7;
		final Vec3 var10_f1 = var10;
		final LivingEntity var11_f2 = var11;
		final float var12_f3 = var12;
		final boolean var13_f4 = var13;
		final float var14_f5 = var14;
		Consumer<LivingEntity> var15 = (l0) -> {
			Vec3 lv1_7 = var10_f1;
			if (!(this.fixedCamera)){
				lv1_7 = var11_f2.getLookAngle().normalize().multiply((double)(var12_f3),(var13_f4) ? ((double)(var14_f5)) : (0D),(double)(var12_f3));
			}
			AbilityHelper.setDeltaMovement((Entity)(var11_f2),lv1_7.x,(var13_f4) ? (lv1_7.y) : (var11_f2.getDeltaMovement().y()),lv1_7.z);
		};
		this.dashConsumer = var15;
	}
}