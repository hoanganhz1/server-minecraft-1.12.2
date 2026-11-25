package xyz.pixelatedw.mineminenomi.abilities.gomu;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IVisibilityPredicate;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$RenderType;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SToggleDrumsOfLiberationSoundPacket;

public class GearFifthAbility extends MorphAbility {
	// Fields
	public static final int HOLD_TIME = 1200;
	private static final int MIN_COOLDOWN = 200;
	private static final float MAX_COOLDOWN = 800F;
	private static final Color COLOR = WyHelper.hexToRGB("#FFFFFF30");
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GearFifthAbility>> INSTANCE;
	private static final AbilityOverlay OVERLAY;
	private static final AttributeModifier GRAVITY_MODIFIER;
	private static final AttributeModifier SPEED_MODIFIER;
	private final SkinOverlayComponent skinOverlayComponent;
	private boolean playJumpSound;

	public GearFifthAbility(AbilityCore<? extends GearFifthAbility> var1) {
		super((AbilityCore)(var1));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.skinOverlayComponent};
		this.addComponents(var2);
		this.statsComponent.addAttributeModifier(Attributes.GRAVITY,GRAVITY_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,SPEED_MODIFIER);
		this.continuousComponent.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.addCanUseCheck(GomuHelper.canUseGearCheck(((AbilityCore)(INSTANCE.get()))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.showAll(var1);
		if (!(var1.level().isClientSide)){
			ModNetwork.sendToAllTrackingAndSelf(new SToggleDrumsOfLiberationSoundPacket((LivingEntity)(var1),true),var1);
		}
		this.playJumpSound = false;
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			GomuGomuNoPistolAbility var4 = ((GomuGomuNoPistolAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoPistolAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.switchFifthGear(var1);
			}
			return;
		}
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((var1.onGround()) && (!(this.playJumpSound))){
			this.playJumpSound = true;
		}else{
			if ((!(var1.onGround())) && (this.playJumpSound)){
				SoundEvent var3 = ((SoundEvent)(ModSounds.BOUNCE_1.get()));
				if (var1.getRandom().nextBoolean()){
					var3 = ((SoundEvent)(ModSounds.BOUNCE_2.get()));
				}
				var1.level().playSound((Player)(null),var1.blockPosition(),var3,SoundSource.PLAYERS,2F,(0.75F) + ((var1.getRandom().nextFloat()) / (2F)));
				this.playJumpSound = false;
			}else{
				return;
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.hideAll(var1);
		if (!(var1.level().isClientSide)){
			ModNetwork.sendToAllTrackingAndSelf(new SToggleDrumsOfLiberationSoundPacket((LivingEntity)(var1),false),var1);
		}
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			GomuGomuNoPistolAbility var4 = ((GomuGomuNoPistolAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoPistolAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.switchNoGear(var1);
			}
			float var5 = Math.max(200F,this.continuousComponent.getContinueTime());
			this.cooldownComponent.startCooldown(var1,var5);
			return;
		}
	}
	public float getContinuityHoldTime() {
		return 1200F;
	}
	public MorphInfo getTransformation() {
		return ((MorphInfo)(ModMorphs.GEAR_FIFTH.get()));
	}
	private static boolean canUnlock(LivingEntity var0) {
		return ((Boolean)(DevilFruitData.get(var0).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::hasAwakenedFruit)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The absolute peak bringing joy and freedom to those around them."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F,800F),ContinuousComponent.getTooltip(1200F),ChangeStatsComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GearFifthAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(GearFifthAbility::canUnlock)))).setHidden((AbilityCore$IVisibilityPredicate)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IVisibilityPredicate)(ServerConfig::hasAwakeningsEnabled)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gear_fifth","Gear Fifth",var0));
		OVERLAY = new AbilityOverlay$Builder().setColor(COLOR).setRenderType(AbilityOverlay$RenderType.ENERGY).build();
		GRAVITY_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_GRAVITY_UUID,-0.03999999910593033D,AttributeModifier.Operation.ADD_VALUE);
		SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID,1.149999976158142D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	}
}