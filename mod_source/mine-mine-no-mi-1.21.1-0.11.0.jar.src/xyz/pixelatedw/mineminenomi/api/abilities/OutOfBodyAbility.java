package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public abstract class OutOfBodyAbility extends Ability {
	// Fields
	private LivingEntity body;
	private BlockPos pivotPoint;
	protected final ContinuousComponent continuousComponent;

	public OutOfBodyAbility(AbilityCore<? extends OutOfBodyAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(200,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent(200,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent(200,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::stopContinuityEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		Ability$ICanUseEvent var3 = (l0, l1) -> {
			if (AbilityHelper.isInCreativeOrSpectator(l0)){
				return Result.fail(ModI18nAbilities.MESSAGE_SUVIVAL_ONLY);
			}else{
				return Result.success();
			}
		};
		this.addCanUseCheck(var3);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,(float)(this.getHoldTime()));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.setPivotPoint(this.body.blockPosition());
		var1.setOnGround(false);
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			var3.getAbilities().flying = true;
			if (var3 instanceof ServerPlayer){
				((ServerPlayer)(var3)).onUpdateAbilities();
			}else{
				return;
			}
		}
	}
	private void stopContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.body) != (null)){
			var1.teleportTo((double)(this.getPivotPoint().getX()),(double)(this.getPivotPoint().getY()),(double)(this.getPivotPoint().getZ()));
			this.body.remove(Entity.RemovalReason.DISCARDED);
			this.body = null;
		}
		if (!(this.isPhysical())){
			var1.noPhysics = false;
		}
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			var3.getAbilities().flying = false;
			if (var3 instanceof ServerPlayer){
				((ServerPlayer)(var3)).onUpdateAbilities();
			}else{
				return;
			}
		}
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.isPassenger()){
			var1.stopRiding();
		}
		if (!(this.isPhysical())){
			var1.setOnGround(false);
			var1.noPhysics = true;
			var1.clearFire();
		}
		if (var1.level().isClientSide){
			return;
		}else{
			if ((!((Math.sqrt(var1.distanceToSqr((double)(this.getPivotPoint().getX()),(double)(this.getPivotPoint().getY()),(double)(this.getPivotPoint().getZ())))) > ((double)(this.getMaxRange())))) && ((this.getOriginalBody()) != (null))){
				if ((((double)(this.getPivotPoint().getX())) == (this.getOriginalBody().position().x)) && ((((double)(this.getPivotPoint().getZ())) == (this.getOriginalBody().position().z)) && (((double)(this.getPivotPoint().getY())) == (this.getOriginalBody().position().y)))){

				}else{
					this.setPivotPoint(this.getOriginalBody().blockPosition());
				}
				if ((this.getOriginalBody()) == (null)){
					this.continuousComponent.stopContinuity(var1);
					return;
				}else{
					if (!(this.getOriginalBody().isAlive())){
						var1.hurt(ModDamageSources.getInstance((LevelAccessor)(var1.level())).outOfBody(),var1.getMaxHealth());
					}
					return;
				}
			}else{
				this.continuousComponent.stopContinuity(var1);
				return;
			}
		}
	}
	public LivingEntity getOriginalBody() {
		return this.body;
	}
	public double getDistanceFromPivot(Entity var1) {
		if ((this.getPivotPoint()) == (null)){
			return -1D;
		}else{
			return Math.sqrt(var1.distanceToSqr((double)(this.getPivotPoint().getX()),(double)(this.getPivotPoint().getY()),(double)(this.getPivotPoint().getZ())));
		}
	}
	public void setOriginalBody(LivingEntity var1) {
		this.body = var1;
	}
	public BlockPos getPivotPoint() {
		return this.pivotPoint;
	}
	public void setPivotPoint(BlockPos var1) {
		this.pivotPoint = var1;
	}
	public void saveAdditional(CompoundTag var1) {
		if ((this.pivotPoint) != (null)){
			var1.putDouble("x",(double)(this.pivotPoint.getX()));
			var1.putDouble("y",(double)(this.pivotPoint.getY()));
			var1.putDouble("z",(double)(this.pivotPoint.getZ()));
		}
	}
	public void loadAdditional(CompoundTag var1) {
		double var2 = var1.getDouble("x");
		double var3 = var1.getDouble("y");
		double var4 = var1.getDouble("z");
		this.pivotPoint = BlockPos.containing(var2,var3,var4);
	}
	public int getHoldTime() {
		return -1;
	}
	public boolean isActive() {
		return this.continuousComponent.isContinuous();
	}
	public abstract float getMaxRange();
	public abstract boolean isPhysical();
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		OutOfBodyAbility var3 = this;
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
			l0.holdTime((float)(this.getHoldTime()));
		};
		ContinuousComponentTooltip.generate(var2,var4);
		OutOfBodyAbility var5 = this;
		Consumer<RangeComponentTooltip> var6 = (l0) -> {
			l0.range(this.getMaxRange(),RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var6);
	}
}