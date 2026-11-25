package xyz.pixelatedw.mineminenomi.abilities.doku;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class VenomDemonAbility extends MorphAbility {
	// Fields
	private static final int COOLDOWN = 2000;
	private static final int HOLD_TIME = 1200;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<VenomDemonAbility>> INSTANCE;
	private static final AttributeModifier ATTACK_MODIFIER;
	private static final AttributeModifier REACH_MODIFIER;
	private static final AttributeModifier SPEED_MODIFIER;
	private static final AttributeModifier ATTACK_SPEED;
	private static final AttributeModifier STEP_ASSIST;
	private static final AttributeModifier JUMP_HEIGHT;
	private static final AttributeModifier KNOCKBACK_RESISTANCE;
	private final HitTriggerComponent hitTriggerComponent;

	public VenomDemonAbility(AbilityCore<VenomDemonAbility> var1) {
		super((AbilityCore)(var1));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addOnHitEvent((HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::hitTriggerEvent)))).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.hitTriggerComponent};
		this.addComponents(var2);
		this.statsComponent.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,REACH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE,REACH_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE,ATTACK_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ATTACK_SPEED,ATTACK_SPEED);
		this.statsComponent.addAttributeModifier(Attributes.STEP_HEIGHT,STEP_ASSIST);
		this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE,STEP_ASSIST);
		this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT,JUMP_HEIGHT);
		this.statsComponent.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,KNOCKBACK_RESISTANCE);
		this.continuousComponent.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent))));
		this.continuousComponent.addEndEvent(80,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::earlyEndContinuityEvent))));
		this.continuousComponent.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
	}
	public void startContinuityEvent(LivingEntity var1, IAbility var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			HydraAbility var4 = ((HydraAbility)(var3.getEquippedAbility(((AbilityCore)(HydraAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.setVenomMode(var1);
			}
			ChloroBallAbility var5 = ((ChloroBallAbility)(var3.getEquippedAbility(((AbilityCore)(ChloroBallAbility.INSTANCE.get())))));
			if ((var5) != (null)){
				var5.setVenomMode(var1);
			}
			DokuGumoAbility var6 = ((DokuGumoAbility)(var3.getEquippedAbility(((AbilityCore)(DokuGumoAbility.INSTANCE.get())))));
			if ((var6) != (null)){
				var6.setVenomMode(var1);
			}
			VenomRoadAbility var7 = ((VenomRoadAbility)(var3.getEquippedAbility(((AbilityCore)(VenomRoadAbility.INSTANCE.get())))));
			if ((var7) != (null)){
				var7.setVenomMode(var1);
			}
			DokuFuguAbility var8 = ((DokuFuguAbility)(var3.getEquippedAbility(((AbilityCore)(DokuFuguAbility.INSTANCE.get())))));
			if ((var8) != (null)){
				AbilityHelper.emergencyStopAbility(var1,var8);
			}
			return;
		}
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (!(AbilityHelper.isWeakenedByKairosekiOrWater(var1))){
				BlockPos.MutableBlockPos var3 = new BlockPos.MutableBlockPos();
				int var4 = -1;
				LOOP_1:
				do{
					if ((var4) < (1)){
						int var5 = -1;
						do{
							if ((var5) < (1)){
								var3.set((var1.getX()) + ((double)(var4)),var1.getY(),(var1.getZ()) + ((double)(var5)));
								if (!(var1.level().getBlockState(var3.below()).isAir())){
									NuWorld.setBlockState((Entity)(var1),(BlockPos)(var3),((Block)(ModBlocks.DEMON_POISON.get())).defaultBlockState(),3,DefaultProtectionRules.AIR_FOLIAGE);
								}
								var5++;
								continue;
							}else{
								break;
							}
						} while (true);
						var4++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
			}
			if (((this.continuousComponent.getContinueTime()) % (2F)) == (0F)){
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.VENOM_DEMON.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			}
			return;
		}
	}
	private void earlyEndContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,2000F);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			HydraAbility var4 = ((HydraAbility)(var3.getEquippedAbility(((AbilityCore)(HydraAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.setNormalMode(var1);
			}
			ChloroBallAbility var5 = ((ChloroBallAbility)(var3.getEquippedAbility(((AbilityCore)(ChloroBallAbility.INSTANCE.get())))));
			if ((var5) != (null)){
				var5.setNormalMode(var1);
			}
			DokuGumoAbility var6 = ((DokuGumoAbility)(var3.getEquippedAbility(((AbilityCore)(DokuGumoAbility.INSTANCE.get())))));
			if ((var6) != (null)){
				var6.setNormalMode(var1);
			}
			VenomRoadAbility var7 = ((VenomRoadAbility)(var3.getEquippedAbility(((AbilityCore)(VenomRoadAbility.INSTANCE.get())))));
			if ((var7) != (null)){
				var7.setNormalMode(var1);
			}
			return;
		}
	}
	public MorphInfo getTransformation() {
		return ((MorphInfo)(ModMorphs.VENOM_DEMON.get()));
	}
	public float getContinuityHoldTime() {
		return 1200F;
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if ((this.continuousComponent.isContinuous()) && (var1.getMainHandItem().isEmpty())){
			return HitTriggerComponent$HitResult.HIT;
		}else{
			return HitTriggerComponent$HitResult.PASS;
		}
	}
	public boolean hitTriggerEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.DOKU_POISON),100,1));
		return true;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user coats themselves in layers of strong corrosive venom, becoming a Venom Demon and leaving a highly poisonous trail. Also enhances all Posion abilities."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(2000F),ContinuousComponent.getTooltip(),ChangeStatsComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(VenomDemonAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("venom_demon","Venom Demon",var0));
		ATTACK_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID,8D,AttributeModifier.Operation.ADD_VALUE);
		REACH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID,3D,AttributeModifier.Operation.ADD_VALUE);
		SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID,0.02D,AttributeModifier.Operation.ADD_VALUE);
		ATTACK_SPEED = new AttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID,0.15D,AttributeModifier.Operation.ADD_VALUE);
		STEP_ASSIST = new AttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID,1.5D,AttributeModifier.Operation.ADD_VALUE);
		JUMP_HEIGHT = new AttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID,2D,AttributeModifier.Operation.ADD_VALUE);
		KNOCKBACK_RESISTANCE = new AttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
	}
}