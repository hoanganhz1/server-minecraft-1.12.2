package xyz.pixelatedw.mineminenomi.abilities.bane;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class SpringHopperAbility extends Ability {
	// Fields
	private static final ResourceLocation JUMP_HEIGHT_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.spring_hopper.jump_height");
	private static final int COOLDOWN = 200;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SpringHopperAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final MorphComponent morphComponent;
	private final ChangeStatsComponent changeStatsComponent;
	private int jumpPower;
	private boolean canIncreaseJumpPower;
	private boolean startedFalling;

	public SpringHopperAbility(AbilityCore<SpringHopperAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.morphComponent = new MorphComponent((IAbility)(this));
		this.changeStatsComponent = new ChangeStatsComponent((IAbility)(this));
		this.jumpPower = 0;
		this.canIncreaseJumpPower = false;
		this.startedFalling = false;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.morphComponent,this.changeStatsComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	public void onContinuityStart(LivingEntity var1, IAbility var2) {
		this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.SPRING_LEGS.get())));
	}
	
	public void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.morphComponent.stopMorph(var1);
		this.cooldownComponent.startCooldown(var1,200F);
	}
	public AttributeModifier getJumpHeight(int var1) {
		return new AttributeModifier(JUMP_HEIGHT_ID,((double)(var1)) * (1.5D),AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("By turning the entity's legs into springs, they can jump around with great ease bouncing around surfaces"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ContinuousComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SpringHopperAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("spring_hopper","Spring Hopper",var0));
	}
public void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (this.canUse(var1).isFail()){
			this.jumpPower = 0;
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			if ((var1.horizontalCollision) && ((this.jumpPower) > (2))){
				Vec3 var3 = var1.getLookAngle().multiply(-2D,-2D,-2D);
				AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,var3.y,var3.z);
				if ((this.jumpPower) < (9)){
					this.jumpPower = (this.jumpPower) + (1);
				}
				if (var1 instanceof Player){
					Player var4 = ((Player)(var1));
					var1.level().playSound(var4,var1.blockPosition(),((SoundEvent)(ModSounds.SPRING_SFX.get())),SoundSource.PLAYERS,1F,(float)(Mth.clamp((var1.getRandom().nextDouble()) + (0.30000001192092896D),0.5D,1.5D)));
				}
			}
			if (var1.onGround()){
				this.startedFalling = true;
				if (((Boolean)(EntityStatsData.get(var1).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isJumping)))).orElse(Boolean.valueOf(false)))).booleanValue()){
					if ((this.jumpPower) > (3)){
						Vec3 var3_1 = var1.getLookAngle().multiply((0.25D) + (((double)(this.jumpPower)) * (0.25D)),1D,(0.25D) + (((double)(this.jumpPower)) * (0.25D)));
						AbilityHelper.setDeltaMovement((Entity)(var1),var3_1.x,var1.getDeltaMovement().y,var3_1.z);
					}
					if (((this.jumpPower) < (9)) && (this.canIncreaseJumpPower)){
						this.jumpPower = (this.jumpPower) + (1);
					}
					if ((var1.getAttribute(ModAttributes.JUMP_HEIGHT).getValue()) != (((double)(this.jumpPower)) * (1.5D))){
						this.changeStatsComponent.removeModifiers(var1);
						SpringHopperAbility var3_2 = this;
						Predicate<LivingEntity> var4_1 = (l0) -> {
			return this.continuousComponent.isContinuous();
		};
						this.changeStatsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT,this.getJumpHeight(this.jumpPower),var4_1);
					}
					if (var1 instanceof Player){
						Player var3_3 = ((Player)(var1));
						var1.level().playSound(var3_3,var1.blockPosition(),((SoundEvent)(ModSounds.SPRING_SFX.get())),SoundSource.PLAYERS,0.3F,(float)(Mth.clamp((var1.getRandom().nextDouble()) + (0.30000001192092896D),0.800000011920929D,1.5D)));
					}
					this.canIncreaseJumpPower = true;
				}else{
					this.jumpPower = 0;
				}
			}else{
				if (var1.verticalCollision){
					this.jumpPower = (this.jumpPower) - (1);
				}
				if ((0D) > (var1.getDeltaMovement().y)){
					if (this.startedFalling){
						this.canIncreaseJumpPower = (AbilityHelper.getDifferenceToFloor((Entity)(var1))) > ((double)(this.jumpPower));
						this.startedFalling = false;
					}
					if ((this.jumpPower) > (3)){
						AbilityHelper.setDeltaMovement((Entity)(var1),var1.getDeltaMovement().scale(1.15D));
					}else{
						return;
					}
				}else{
					return;
				}
			}
			return;
		}
	}}