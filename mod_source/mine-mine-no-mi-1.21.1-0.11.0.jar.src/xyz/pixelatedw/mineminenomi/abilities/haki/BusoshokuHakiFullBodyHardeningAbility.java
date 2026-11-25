package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent$IUpdateDamageSource;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChangeStatsComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.HakiDamageTooltip;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BusoshokuHakiFullBodyHardeningAbility extends Ability {
	// Fields
	public static final Supplier<AbilityCore<BusoshokuHakiFullBodyHardeningAbility>> INSTANCE;
	public static final AbilityOverlay OVERLAY;
	private Predicate<LivingEntity> emptyHandPredicate;
	private static final ResourceLocation ARMOR_ID;
	private static final ResourceLocation ARMOR_THOUGNESS_ID;
	private static final ResourceLocation TOUGHNESS_ID;
	private final ContinuousComponent continuousComponent;
	private final ChangeStatsComponent statsComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final SkinOverlayComponent skinOverlayComponent;
	private final PoolComponent poolComponent;
	private final DamageSourceComponent damageSourceComponent;

	public BusoshokuHakiFullBodyHardeningAbility(AbilityCore<BusoshokuHakiFullBodyHardeningAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::continuousStartEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::continuousStopEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::continuousTickEvent))));
		this.statsComponent = new ChangeStatsComponent((IAbility)(this));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent(100,(HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent(100,(HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHitEvent))));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.BODY_BUSOSHOKU_HAKI,new AbilityPool[0]);
		this.damageSourceComponent = new DamageSourceComponent((IAbility)(this)).addDamageSourceUpdateEvent(100,(DamageSourceComponent$IUpdateDamageSource)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent$IUpdateDamageSource)(this::onDamageSourceUpdate))));
		this.setOGCD();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.statsComponent,this.hitTriggerComponent,this.skinOverlayComponent,this.poolComponent,this.damageSourceComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canEnableHaki))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void continuousStartEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.BUSOSHOKU_HAKI_SFX.get())),SoundSource.PLAYERS,2F,0.8F);
		this.skinOverlayComponent.showAll(var1);
	}
	private void continuousStopEvent(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.hideAll(var1);
	}
	private void continuousTickEvent(LivingEntity var1, IAbility var2) {
		if (HakiHelper.checkForHakiOveruse(var1,0)){
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (!(this.continuousComponent.isContinuous())){
			return HitTriggerComponent$HitResult.PASS;
		}else{
			return HitTriggerComponent$HitResult.HIT;
		}
	}
	private boolean onHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		return true;
	}
	public void onDamageSourceUpdate(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (this.continuousComponent.isContinuous()){
			IDamageSourceHandler.getHandler(var3).bypassLogia();
		}
	}
	private AttributeModifier getArmorMod(double var1) {
		return new AttributeModifier(ARMOR_ID,var1,AttributeModifier.Operation.ADD_VALUE);
	}
	private AttributeModifier getArmorThougnessMod(double var1) {
		return new AttributeModifier(ARMOR_THOUGNESS_ID,var1,AttributeModifier.Operation.ADD_VALUE);
	}
	private AttributeModifier getToughnessMod(double var1) {
		return new AttributeModifier(TOUGHNESS_ID,var1,AttributeModifier.Operation.ADD_VALUE);
	}
	private static boolean canUnlock(LivingEntity var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		HakiData var2 = ((HakiData)(((java.util.Optional)(HakiData.get(var0))).orElse((Object)(null))));
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
		if (((var1) != (null)) && (((var2) != (null)) && ((var3) != (null)))){
			return (var1.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get())))) && (((var3.getDoriki()) > (5000D)) && (((double)(var2.getBusoshokuHakiExp())) > (HakiHelper.getBusoshokuFullBodyExpNeeded(var0))));
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv8_2 = new Pair[]{ImmutablePair.of((Object)("Covers the whole body of the user user in a layer of Armament haki, used for a balance between offense and defense."),(Object)(null))};
			Component[] lv8_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv8_2);
			SourceType[] lv8_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.HAKI,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BusoshokuHakiFullBodyHardeningAbility::new)))).addDescriptionLine(lv8_3).setSourceType(lv8_4).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(BusoshokuHakiFullBodyHardeningAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = ModRegistry.registerAbility("busoshoku_haki_full_body_hardening","Busoshoku Haki: Full Body Hardening",var0);
		OVERLAY = new AbilityOverlay$Builder().setTexture(ModResources.BUSOSHOKU_HAKI_ARM).setColor(WyHelper.hexToRGB("#FFFFFFAA")).build();
		ARMOR_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","busoshoku_haki_fullbody.armor");
		ARMOR_THOUGNESS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","busoshoku_haki_fullbody.armor_toughness");
		TOUGHNESS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","busoshoku_haki_fullbody.toughness");
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if ((this.emptyHandPredicate) == (null)){
			BusoshokuHakiFullBodyHardeningAbility var3 = this;
			Predicate<LivingEntity> var4 = (l0) -> {
			return (this.continuousComponent.isContinuous()) && (l0.getMainHandItem().isEmpty());
		};
			this.emptyHandPredicate = var4;
		}
		HakiData var3_1 = ((HakiData)(((java.util.Optional)(HakiData.get(var1))).orElse((Object)(null))));
		if ((var3_1) == (null)){
			return;
		}else{
			double var4_1 = (double)((var3_1.getBusoshokuHakiExp()) / (12.5F));
			BusoshokuHakiFullBodyHardeningAbility var5 = this;
			Predicate<LivingEntity> var6 = (l0) -> {
			return this.continuousComponent.isContinuous();
		};
			this.statsComponent.addAttributeModifier(Attributes.ARMOR,this.getArmorMod(var4_1),var6);
			BusoshokuHakiFullBodyHardeningAbility var7 = this;
			Predicate<LivingEntity> var8 = (l0) -> {
			return this.continuousComponent.isContinuous();
		};
			this.statsComponent.addAttributeModifier(Attributes.ARMOR_TOUGHNESS,this.getArmorThougnessMod((var4_1) / (4D)),var8);
			BusoshokuHakiFullBodyHardeningAbility var9 = this;
			Predicate<LivingEntity> var10 = (l0) -> {
			return this.continuousComponent.isContinuous();
		};
			this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS,this.getToughnessMod((double)((8F) * ((var3_1.getBusoshokuHakiExp()) / ((float)(ServerConfig.getHakiExpLimit()))))),var10);
			this.continuousComponent.triggerContinuity(var1);
			return;
		}
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<ContinuousComponentTooltip> var3 = (l0) -> {
		};
		ContinuousComponentTooltip.generate(var2,var3);
		Consumer<ChangeStatsComponentTooltip> var4 = (l0) -> {
		};
		ChangeStatsComponentTooltip.generate(var2,var4);
		Player var5 = var1;
		final Player var5_f1 = var5;
		Consumer<HakiDamageTooltip> var6 = (l0) -> {
			l0.damage((float)(HakiHelper.getBasicBusoshokuHakiDamageBoost((LivingEntity)(var5_f1),1F))).setPunch();
		};
		HakiDamageTooltip.generate(var2,var6);
	}
}