package xyz.pixelatedw.mineminenomi.abilities.netsu;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$RenderType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class NetsuEnhancementAbility extends PunchAbility {
	// Fields
	private static final int HOLD_TIME = 800;
	private static final int MIN_COOLDOWN = 100;
	private static final int MAX_COOLDOWN = 500;
	public static final ResourceLocation ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.netsu_enchantment.attack_speed");
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<NetsuEnhancementAbility>> INSTANCE;
	private static final AttributeModifier ATTACK_SPEED_MULTIPLIER;
	private static final AbilityOverlay OVERLAY;
	private final SkinOverlayComponent skinOverlayComponent;

	public NetsuEnhancementAbility(AbilityCore<NetsuEnhancementAbility> var1) {
		super((AbilityCore)(var1));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.skinOverlayComponent};
		this.addComponents(var2);
		this.clearUseChecks();
		this.statsComponent.addAttributeModifier(Attributes.ATTACK_SPEED,ATTACK_SPEED_MULTIPLIER);
		this.continuousComponent.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent))));
		this.continuousComponent.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.showAll(var1);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((!(var1.level().isClientSide)) && (((this.continuousComponent.getContinueTime()) % (5F)) == (0F))){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.NETSU_ENCHANTMENT.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.consumeUseAndStop();
		this.skinOverlayComponent.hideAll(var1);
	}
	public float getPunchDamage() {
		return 10F;
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		AbilityHelper.setSecondsOnFireBy((Entity)(var2),5,var1);
		return true;
	}
	public boolean isParallel() {
		return true;
	}
	public int getUseLimit() {
		return -1;
	}
	public float getPunchCooldown() {
		return (100F) + ((this.continuousComponent.getContinueTime()) / (2F));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Increases the user's attacks and body functions through heat."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(NetsuEnhancementAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("netsu_enhancement","Netsu Enhancement",var0));
		ATTACK_SPEED_MULTIPLIER = new AttributeModifier(ATTACK_SPEED_ID,0.25D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		OVERLAY = new AbilityOverlay$Builder().setRenderType(AbilityOverlay$RenderType.ENERGY).setColor(WyHelper.hexToRGB("#962A2AAA")).build();
	}
}