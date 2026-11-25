package xyz.pixelatedw.mineminenomi.abilities.gomu;

import java.awt.Color;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GearSecondAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 1000;
	private static final int MIN_COOLDOWN = 20;
	private static final float MAX_COOLDOWN = 666.6667F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GearSecondAbility>> INSTANCE;
	private static final AbilityOverlay OVERLAY;
	private static final AttributeModifier JUMP_HEIGHT;
	private static final AttributeModifier STRENGTH_MODIFIER;
	private static final AttributeModifier ATTACK_SPEED_MODIFIER;
	private static final AttributeModifier STEP_HEIGHT;
	private static final AttributeModifier GCD_MODIFIER;
	private final ContinuousComponent continuousComponent;
	private final ChangeStatsComponent changeStatsComponent;
	private final SkinOverlayComponent skinOverlayComponent;
	private boolean prevSprintValue;

	public GearSecondAbility(AbilityCore<GearSecondAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.changeStatsComponent = new ChangeStatsComponent((IAbility)(this));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		this.prevSprintValue = false;
		this.setOGCD();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.changeStatsComponent,this.skinOverlayComponent};
		this.addComponents(var2);
		this.changeStatsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT,JUMP_HEIGHT);
		this.changeStatsComponent.addAttributeModifier(Attributes.STEP_HEIGHT,STEP_HEIGHT);
		this.changeStatsComponent.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,ATTACK_SPEED_MODIFIER);
		this.changeStatsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE,STRENGTH_MODIFIER);
		this.changeStatsComponent.addAttributeModifier(ModAttributes.GCD,GCD_MODIFIER);
		this.addCanUseCheck(GomuHelper.canUseGearCheck(((AbilityCore)(INSTANCE.get()))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,1000F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.changeStatsComponent.applyModifiers(var1);
		this.skinOverlayComponent.showAll(var1);
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			GomuGomuNoPistolAbility var4 = ((GomuGomuNoPistolAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoPistolAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.switchSecondGear(var1);
			}
			GomuGomuNoGatlingAbility var5 = ((GomuGomuNoGatlingAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoGatlingAbility.INSTANCE.get())))));
			if ((var5) != (null)){
				var5.switchSecondGear(var1);
			}
			GomuGomuNoBazookaAbility var6 = ((GomuGomuNoBazookaAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoBazookaAbility.INSTANCE.get())))));
			if ((var6) != (null)){
				var6.switchSecondGear(var1);
			}
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GEAR_SECOND_SFX.get())),SoundSource.PLAYERS,2F,1F);
			this.prevSprintValue = var1.isSprinting();
			return;
		}
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (((this.continuousComponent.getContinueTime()) % (10F)) == (0F)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GEAR_SECOND.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
		if (AbilityUseConditions.canUseMomentumAbilities(var1).isFail()){
			return;
		}else{
			if (!(var1.isSprinting())){
				this.prevSprintValue = false;
				return;
			}else{
				if (!(this.prevSprintValue)){
					var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.TELEPORT_SFX.get())),SoundSource.PLAYERS,2F,1F);
				}
				float var3 = 1.75F;
				Vec3 var4 = var1.getLookAngle();
				if (var1.onGround()){
					AbilityHelper.setDeltaMovement((Entity)(var1),(var4.x) * ((double)(var3)),var1.getDeltaMovement().y,(var4.z) * ((double)(var3)));
				}else{
					AbilityHelper.setDeltaMovement((Entity)(var1),((var4.x) * ((double)(var3))) * (0.5D),var1.getDeltaMovement().y,((var4.z) * ((double)(var3))) * (0.5D));
				}
				this.prevSprintValue = var1.isSprinting();
				return;
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.changeStatsComponent.removeModifiers(var1);
		this.skinOverlayComponent.hideAll(var1);
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			GomuGomuNoPistolAbility var4 = ((GomuGomuNoPistolAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoPistolAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.switchNoGear(var1);
			}
			GomuGomuNoGatlingAbility var5 = ((GomuGomuNoGatlingAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoGatlingAbility.INSTANCE.get())))));
			if ((var5) != (null)){
				var5.switchNoGear(var1);
			}
			GomuGomuNoBazookaAbility var6 = ((GomuGomuNoBazookaAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoBazookaAbility.INSTANCE.get())))));
			if ((var6) != (null)){
				var6.switchNoGear(var1);
			}
			float var7 = Math.max(20F,(this.continuousComponent.getContinueTime()) / (1.5F));
			this.cooldownComponent.startCooldown(var1,var7);
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("By speding up their blood flow, the user gains strength, speed and mobility."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(20F,666.6667F),ContinuousComponent.getTooltip(1000F),ChangeStatsComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GearSecondAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gear_second","Gear Second",var0));
		OVERLAY = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.BODY).setColor(new Color(232,54,54,74)).build();
		JUMP_HEIGHT = new AttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID,5D,AttributeModifier.Operation.ADD_VALUE);
		STRENGTH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID,4D,AttributeModifier.Operation.ADD_VALUE);
		ATTACK_SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID,4D,AttributeModifier.Operation.ADD_VALUE);
		STEP_HEIGHT = new AttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
		GCD_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_GCD_UUID,-0.5D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	}
}