package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.HakiDamageTooltip;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HaoshokuHakiInfusionAbility extends PunchAbility {
	// Fields
	public static final float BASE_INFUSION_DAMAGE = 5F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HaoshokuHakiInfusionAbility>> INSTANCE;
	private static final AbilityOverlay OVERLAY;
	private static final ResourceLocation STRENGTH_ID;
	private final PoolComponent poolComponent;
	private final SkinOverlayComponent skinOverlayComponent;

	public HaoshokuHakiInfusionAbility(AbilityCore<HaoshokuHakiInfusionAbility> var1) {
		super((AbilityCore)(var1));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.ADVANCED_HAOSHOKU_HAKI,new AbilityPool[0]);
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		this.setOGCD();
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.poolComponent,this.skinOverlayComponent};
		this.addComponents(var2);
		this.clearUseChecks();
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canEnableHaki))));
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		Color var3 = WyHelper.intToRGB(HakiHelper.getHaoshokuColour(var1),50);
		AbilityOverlay var4 = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.LIMB).setColor(var3).build();
		this.skinOverlayComponent.show(var1,var4);
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (HakiHelper.checkForHakiOveruse(var1,3)){
				super.consumeUseAndStop();
			}
			return;
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.hideAll(var1);
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		IDamageSourceHandler.getHandler(var3).bypassLogia();
		if ((var1.getRandom().nextInt(10)) < (1)){
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.HAKI_RELEASE_SFX.get())),SoundSource.PLAYERS,2F,0.7F);
		}
		Color var4 = WyHelper.intToRGB(HakiHelper.getHaoshokuColour(var1),50);
		LightningDischargeEntity var5 = new LightningDischargeEntity((Entity)(var2),var2.getX(),(var2.getY()) + (1.5D),var2.getZ(),var2.getYRot(),var2.getXRot());
		var5.setAliveTicks(15);
		var5.setLightningLength(6F);
		var5.setColor(new Color(0,0,0,100));
		var5.setOutlineColor(var4);
		var5.setDetails(4);
		var5.setDensity(4);
		var5.setSize(1F);
		var5.setSkipSegments(1);
		var1.level().addFreshEntity((Entity)(var5));
		return true;
	}
	public int getUseLimit() {
		return -1;
	}
	public boolean isParallel() {
		return true;
	}
	public float getPunchCooldown() {
		return 0F;
	}
	public static double getDamageBoost(LivingEntity var0, float var1) {
		double var2 = ((Double)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
		float var3 = ((Float)(HakiData.get(var0).map((Function)(((Function<HakiData, Float>)(HakiData::getBusoshokuHakiExp)))).orElse(Float.valueOf(0F)))).floatValue();
		double var4 = (var2) / ((double)(ServerConfig.getDorikiLimit()));
		return (5D) + ((((double)(var1)) * (0.5D)) * (((0.1D) * (var4)) + ((0.9D) * ((double)((var3) / ((float)(ServerConfig.getHakiExpLimit())))))));
	}
	private AttributeModifier getEntryAttackDamage(double var1) {
		return new AttributeModifier(STRENGTH_ID,var1,AttributeModifier.Operation.ADD_VALUE);
	}
	public Result canTriggerHit(LivingEntity var1) {
		return Result.success();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to infuse their haoshoku haki into whatever weapon they're holding."),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			SourceType[] lv3_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.HAKI,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HaoshokuHakiInfusionAbility::new)))).addDescriptionLine(lv3_3).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(HaoshokuHakiInfusionAbility::canUnlock)))).setSourceType(lv3_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("haoshoku_haki_infusion","Haoshoku Haki: Infusion",var0));
		OVERLAY = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.LIMB).setColor(new Color(255,0,0,50)).build();
		STRENGTH_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","haoshoku_haki_infusion.strength");
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Player var3 = var1;
		final Player var3_f1 = var3;
		Consumer<HakiDamageTooltip> var4 = (l0) -> {
			l0.damage((float)(getDamageBoost((LivingEntity)(var3_f1),1F))).setBaseDamage(5F);
		};
		HakiDamageTooltip.generate(var2,var4);
	}
	private static boolean canUnlock(LivingEntity var0) {
		HakiData var1 = ((HakiData)(((java.util.Optional)(HakiData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return false;
		}else{
			Function<AbilityData, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(l0.hasUnlockedAbility(((AbilityCore)(HaoshokuHakiAbility.INSTANCE.get()))));
		};
			boolean var3 = ((Boolean)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue();
			return ((var1.getTotalHakiExp()) >= ((var1.getMaxHakiExp()) * (0.85F))) && (var3);
		}
	}
}