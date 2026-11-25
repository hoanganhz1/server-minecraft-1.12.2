package xyz.pixelatedw.mineminenomi.abilities;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IOnRemoveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IPauseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IResumeEvent;
import xyz.pixelatedw.mineminenomi.api.extensions.ILivingEntityExtension;
import xyz.pixelatedw.mineminenomi.api.extensions.IPlayerAbilitiesExtension;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;

public abstract class PropelledFlightAbility extends PassiveAbility {
	// Fields
	private static final ResourceLocation SPECIAL_FLY_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/special_fly.png");
	private static final float HORIZONTAL_RETENTION = 0.91F;
	private static final float VERTICAL_RETENTION = 0.98F;
	protected boolean isRecovering;
	protected boolean hasLanded;
	protected double stamina;
	protected float speed;
	private Vec3 oldMovement;
	private Interval staminaInterval;
	private boolean isFlying;
	private int jumpTriggerTime;
	private boolean jumpLock;

	public PropelledFlightAbility(AbilityCore<? extends PropelledFlightAbility> var1) {
		super((AbilityCore)(var1));
		this.isRecovering = false;
		this.hasLanded = false;
		this.stamina = 100D;
		this.speed = 0F;
		this.oldMovement = Vec3.ZERO;
		this.staminaInterval = new Interval(20);
		this.setDisplayIcon(SPECIAL_FLY_ICON);
		if (super.isClientSide()){
			GaugeComponent var2 = new GaugeComponent((IAbility)(this),(GaugeComponent$IGaugeRenderer)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer)((GaugeComponent$IGaugeRenderer) ((GaugeComponent$IGaugeRenderer<IAbility>) (this::renderGauge))))));
			AbilityComponent[] var3 = new AbilityComponent[]{var2};
			this.addComponents(var3);
		}
		this.addRemoveEvent((PassiveAbility$IOnRemoveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IOnRemoveEvent)(this::onRemove))));
		this.pauseTickComponent.addResumeEvent(100,(PauseTickComponent$IResumeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IResumeEvent)(this::onResume)))).addPauseEvent(100,(PauseTickComponent$IPauseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IPauseEvent)(this::onPause))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::onDuringPassive))));
	}
	public void tick(LivingEntity var1) {
		super.tick(var1);
	}
	private void onRemove(LivingEntity var1, PassiveAbility var2) {
		if (var1 instanceof Player){
			this.disableFlight(((Player)(var1)));
		}
	}
	private void onResume(LivingEntity var1, IAbility var2) {
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			if (this.canUse(var1).isSuccess()){
				this.enableFlight(var3);
			}
			return;
		}else{
			return;
		}
	}
	private void onDuringPassive(LivingEntity var1) {
		if (var1 instanceof Player){
			Player var2 = ((Player)(var1));
			if ((((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))))) == (null)){
				return;
			}else{
				IPlayerAbilitiesExtension var3 = ((IPlayerAbilitiesExtension)(var2.getAbilities()));
				if (this.isRecovering){
					if ((!(this.hasLanded)) && (!(var1.level().isEmptyBlock(var1.blockPosition().below(5))))){
						this.hasLanded = true;
					}
					if (this.hasLanded){
						this.disableFlight(var2);
						return;
					}
				}
				float var4 = var2.getHealth();
				float var5 = var2.getMaxHealth();
				if ((!(this.isRecovering)) && (((this.stamina) > (0D)) && ((this.isFlying) && (((var4) < (var5)) && (this.staminaInterval.canTick()))))){
					this.alterStamina(((double)(((var5) - (var4)) / (var5))) * (-5D));
				}
				if (AbilityHelper.isInCreativeOrSpectator(var1)){
					if (var3.hasCustomFlight()){
						this.disableFlight(var2);
					}
					return;
				}else{
					if (!(var1.level().isClientSide)){
						boolean var6 = ((ILivingEntityExtension)(var2)).isJumping();
						if (!(var6)){
							this.jumpLock = false;
						}
						if ((this.jumpTriggerTime) > (0)){
							this.jumpTriggerTime = (this.jumpTriggerTime) - (1);
						}
						if ((!(this.jumpLock)) && (var6)){
							if ((this.jumpTriggerTime) == (0)){
								this.jumpTriggerTime = 7;
								this.jumpLock = true;
							}else{
								this.isFlying = !(this.isFlying);
								var3.setCustomFlight(this.isFlying);
								ModNetwork.sendTo(new SUpdateAbilityNBTPacket((LivingEntity)(var2),(IAbility)(this)),var2);
								var2.setNoGravity(this.isFlying);
								this.jumpTriggerTime = 0;
							}
						}
						if (this.isFlying){
							boolean var7 = this.isMoving(var1);
							float var8 = this.getMaxSpeed((LivingEntity)(var2));
							float var9 = this.getAcceleration((LivingEntity)(var2));
							var9 = (var9) * (((this.speed) > (0F)) ? ((1F) - ((this.speed) / (var8))) : (1F));
							var9 = (var7) ? (var9) : ((-(var8)) / (20F));
							this.speed = Mth.clamp((this.speed) + (var9),((var9) > (0F)) ? ((var8) / (5F)) : (0F),var8);
							this.speed = this.getSpeed((LivingEntity)(var2));
							BlockPos var10 = var2.getOnPos();
							float var11 = var2.level().getBlockState(var10).getFriction((LevelReader)(var2.level()),var10,(Entity)(var2));
							float var12 = (var2.onGround()) ? ((var11) * (0.91F)) : (0.91F);
							var12 = (var2.horizontalCollision) ? (0F) : (var12);
							Vec3 var13 = this.getMovementVector(var1);
							Vec3 var14 = new Vec3(var13.x,(this.isRecovering) ? (-0.5D) : (var13.y),var13.z);
							Vec3 var15 = var14.subtract(this.oldMovement.multiply((double)(var12),0.9800000190734863D,(double)(var12)));
							Vec3 var16 = var2.getDeltaMovement().add(var15);
							var2.xxa = 0F;
							var2.zza = 0F;
							this.oldMovement = var14;
							AbilityHelper.setDeltaMovement((Entity)(var2),var16);
							double var17 = (double)(this.getHeightDifference((LivingEntity)(var2)));
							if (((var17) != (0D)) && (!(ServerConfig.hasYRestrictionRemoved()))){
								AbilityHelper.vanillaFlightThreshold((LivingEntity)(var2),(AbilityHelper.flyingAtMaxHeight((LivingEntity)(var2),var17)) ? (256) : (((int)(var2.getY())) - (1)));
							}
						}
						var2.fallDistance = 0F;
					}
					return;
				}
			}
		}else{
			return;
		}
	}
	private void onPause(LivingEntity var1, IAbility var2) {
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			this.disableFlight(var3);
			return;
		}else{
			return;
		}
	}
	public boolean canRenderGauge(Player var1) {
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	private void renderGauge(Player var1, GuiGraphics var2, int var3, int var4, IAbility var5) {
		if (!(this.canRenderGauge(var1))){
			return;
		}else{
			Minecraft var6 = Minecraft.getInstance();
			RenderSystem.setShaderTexture(0,ModResources.WIDGETS);
			RendererHelper.drawIcon(SPECIAL_FLY_ICON,var2.pose(),(float)(var3),(float)((var4) - (38)),0F,32F,32F);
			String var7 = new DecimalFormat("#0.0").format(this.stamina);
			RendererHelper.drawStringWithBorder(var6.font,var2,var7,((var3) + (15)) - ((var6.font.width(var7)) / (2)),(var4) - (25),Color.WHITE.getRGB());
			return;
		}
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putBoolean("isRecovering",this.isRecovering);
		var1.putBoolean("hasLanded",this.hasLanded);
		var1.putDouble("stamina",this.stamina);
		var1.putBoolean("isFlying",this.isFlying);
	}
	public void loadAdditional(CompoundTag var1) {
		this.isRecovering = var1.getBoolean("isRecovering");
		this.hasLanded = var1.getBoolean("hasLanded");
		this.stamina = var1.getDouble("stamina");
		this.isFlying = var1.getBoolean("isFlying");
	}
	public double getStamina() {
		return this.stamina;
	}
	public void alterStamina(double var1) {
		this.stamina = Mth.clamp((this.stamina) + (var1),0D,100D);
	}
	public boolean isMoving(LivingEntity var1) {
		int var2 = ((((var1.zza) != (0F)) || ((var1.xxa) != (0F))) && ((!(var1.verticalCollision)) && (!(var1.horizontalCollision)))) ? (1) : (0);
		if (var1 instanceof Player){
			return (this.isFlying) && ((var2) != (0));
		}else{
			return (((var2) & 1) != 0);
		}
	}
	public float getSpeed(LivingEntity var1) {
		return this.speed;
	}
	public abstract float getMaxSpeed(LivingEntity var0);
	protected abstract float getAcceleration(LivingEntity var0);
	protected abstract int getHeightDifference(LivingEntity var0);
	protected Vec3 getMovementVector(LivingEntity var1) {
		Vec3 var2 = var1.getLookAngle().normalize();
		double var3 = ((var2.x) * ((double)(this.speed))) * ((double)(var1.zza));
		double var4 = (((var2.y) * ((double)(this.speed))) * ((double)(var1.zza))) + ((Math.cos(((double)(var1.tickCount)) / (4D))) / (15D));
		double var5 = ((var2.z) * ((double)(this.speed))) * ((double)(var1.zza));
		return new Vec3(var3,var4,var5);
	}
	public void enableFlight(Player var1) {
		IPlayerAbilitiesExtension var2 = ((IPlayerAbilitiesExtension)(var1.getAbilities()));
		this.isFlying = true;
		var2.setCustomFlight(true);
		ModNetwork.sendTo(new SUpdateAbilityNBTPacket((LivingEntity)(var1),(IAbility)(this)),var1);
		var1.setNoGravity(true);
	}
	public void disableFlight(Player var1) {
		IPlayerAbilitiesExtension var2 = ((IPlayerAbilitiesExtension)(var1.getAbilities()));
		this.isFlying = false;
		var2.setCustomFlight(false);
		ModNetwork.sendTo(new SUpdateAbilityNBTPacket((LivingEntity)(var1),(IAbility)(this)),var1);
		var1.setNoGravity(false);
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			var3.setFlyingSpeed(Vec3.ZERO);
			return;
		}
	}
}