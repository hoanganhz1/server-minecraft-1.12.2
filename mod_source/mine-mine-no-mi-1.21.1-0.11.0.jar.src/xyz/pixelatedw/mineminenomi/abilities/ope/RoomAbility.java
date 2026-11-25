package xyz.pixelatedw.mineminenomi.abilities.ope;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class RoomAbility extends Ability {
	// Fields
	public static final int MIN_ROOM_SIZE = 8;
	public static final int MAX_ROOM_SIZE = 45;
	public static final int MAX_THRESHOLD = 2;
	private static final int CHARGE_TIME = 20;
	private static final float MIN_COOLDOWN = 10F;
	private static final float MAX_COOLDOWN = 50F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<RoomAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private SphereEntity roomEntity;
	private float roomSize;
	private BlockPos centerPos;
	private boolean isShrinking;
	private Interval checkPositionInterval;
	private Interval playSoundInterval;

	public RoomAbility(AbilityCore<RoomAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.roomSize = 0F;
		this.isShrinking = false;
		this.checkPositionInterval = new Interval(20);
		this.playSoundInterval = new Interval(18);
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.continuousComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (this.chargeComponent.isCharging()){
			this.chargeComponent.stopCharging(var1);
		}else{
			if (this.continuousComponent.isContinuous()){
				this.isShrinking = true;
				this.chargeComponent.startCharging(var1,10F);
				return;
			}else{
				this.checkPositionInterval.restartIntervalToZero();
				var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.ROOM_CREATE_SFX.get())),SoundSource.PLAYERS,5F,1F);
				this.isShrinking = false;
				this.setupSphere(var1);
				float var3 = (20F) * ((2F) - ((var1.getHealth()) / (var1.getMaxHealth())));
				this.chargeComponent.startCharging(var1,var3);
			}
		}
	}
	private void onChargeTick(LivingEntity var1, IAbility var2) {
		this.chargeTickSphere(var1);
		if (this.playSoundInterval.canTick()){
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.ROOM_CHARGE_SFX.get())),SoundSource.PLAYERS,5F,1F);
		}
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.endChargeSphere(var1);
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.ROOM_EXPAND_SFX.get())),SoundSource.PLAYERS,5F,1F);
			this.continuousComponent.startContinuity(var1,-1F);
			return;
		}
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (!(this.continuityTickSphere(var1))){
				return;
			}else{
				return;
			}
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.continuityEndSphere(var1);
		this.centerPos = null;
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.ROOM_CLOSE_SFX.get())),SoundSource.PLAYERS,5F,1F);
		float var3 = (2F) - ((var1.getHealth()) / (var1.getMaxHealth()));
		float var4 = ((10F) * (var3)) * ((this.roomSize) / (45F));
		var4 = Mth.clamp(var4,10F,50F);
		this.cooldownComponent.startCooldown(var1,var4);
	}
	private void setupSphere(LivingEntity var1) {
		this.roomEntity = new SphereEntity((Level)(var1.level()),(LivingEntity)(var1));
		this.roomEntity.setColor(new Color(0,255,255,50));
		this.roomEntity.setRadius(0F);
		this.roomEntity.setDetailLevel(32);
		this.roomEntity.setAnimationSpeed(1);
		var1.level().addFreshEntity((Entity)(this.roomEntity));
		this.centerPos = this.roomEntity.blockPosition();
	}
	private void chargeTickSphere(LivingEntity var1) {
		if ((this.roomEntity) != (null)){
			float var2 = 0F;
			float var3 = 0.0F;
			if (this.isShrinking){
				var2 = ((this.roomSize) * ((1F) - (this.chargeComponent.getChargePercentage()))) / (45F);
				var3 = (EasingFunctionHelper.easeOutCubic(Float.valueOf(var2))) * (45F);
			}else{
				var2 = ((45F) * (this.chargeComponent.getChargePercentage())) / (45F);
				var3 = (EasingFunctionHelper.easeInCubic(Float.valueOf(var2))) * (45F);
			}
			this.roomEntity.setRadius(var3);
			this.roomSize = (float)((int)(var3));
		}
	}
	private void endChargeSphere(LivingEntity var1) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (this.isShrinking){
				if ((this.roomEntity) != (null)){
					this.roomEntity.discard();
				}
				this.roomEntity = null;
			}else{
				float var2 = Mth.clamp((45F) * (this.chargeComponent.getChargePercentage()),8F,45F);
				this.roomEntity.setRadius(var2);
				this.roomSize = (float)((int)(var2));
			}
			return;
		}
	}
	private boolean continuityTickSphere(LivingEntity var1) {
		if ((this.checkPositionInterval.canTick()) && (!(this.isEntityInRoom((Entity)(var1))))){
			this.continuousComponent.stopContinuity(var1);
			return false;
		}else{
			if (((this.roomEntity) != (null)) && (this.roomEntity.isAlive())){
				return true;
			}else{
				this.continuousComponent.stopContinuity(var1);
				return false;
			}
		}
	}
	private void continuityEndSphere(LivingEntity var1) {
		if ((this.roomEntity) != (null)){
			this.roomEntity.discard();
		}
		this.roomEntity = null;
	}
	public float getROOMSize() {
		return this.roomSize;
	}
	public BlockPos getCenterBlock() {
		return this.centerPos;
	}
	public boolean isEntityInRoom(Entity var1) {
		if (this.isPositionInRoom(var1.blockPosition())){
			return true;
		}else{
			if ((this.roomEntity) == (null)){
				return false;
			}else{
				return (var1.distanceToSqr((Entity)(this.roomEntity))) <= ((double)((this.roomSize) * (this.roomSize)));
			}
		}
	}
	public boolean isPositionInRoom(BlockPos var1) {
		if ((this.centerPos) == (null)){
			return false;
		}else{
			return var1.closerThan((Vec3i)(this.centerPos),(double)(this.roomSize));
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Creates a spherical space around the user in which they can manipulate anything or use other skills"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(10F,50F),ChargeComponent.getTooltip(20F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(RoomAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("room","ROOM",var0));
	}
	public static Result hasRoomActive(LivingEntity var0, IAbility var1) {
		Function<AbilityData, RoomAbility> var2 = (l0) -> {
			return ((RoomAbility)(l0.getEquippedAbility(((AbilityCore)(INSTANCE.get())))));
		};
		RoomAbility var3 = ((RoomAbility)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var2)).orElse(null)));
		if (((var3) != (null)) && (var3.isEntityInRoom((Entity)(var0)))){
			return Result.success();
		}else{
			return Result.fail(ModI18nAbilities.MESSAGE_ONLY_IN_ROOM);
		}
	}
}