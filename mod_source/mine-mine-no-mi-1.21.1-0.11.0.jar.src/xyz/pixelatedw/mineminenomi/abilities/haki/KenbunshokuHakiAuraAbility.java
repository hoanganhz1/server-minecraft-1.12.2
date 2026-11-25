package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypeBuffers;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class KenbunshokuHakiAuraAbility extends Ability {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KenbunshokuHakiAuraAbility>> INSTANCE;
	private static final int HOLD_TIME = 2400;
	private static final int MIN_COOLDOWN = 60;
	private final ContinuousComponent continuousComponent;

	public KenbunshokuHakiAuraAbility(AbilityCore<KenbunshokuHakiAuraAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.setOGCD();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,2400F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.KENBUNSHOKU_HAKI_ON_SFX.get())),SoundSource.PLAYERS,2F,1F);
		if (var1.level().isClientSide){
			ModRenderTypeBuffers.getInstance().toggleHakiAuraShader(true);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.KENBUNSHOKU_HAKI_OFF.get())),SoundSource.PLAYERS,2F,1F);
		float var3 = Math.max(60F,this.continuousComponent.getContinueTime());
		this.cooldownComponent.startCooldown(var1,var3);
		if (var1.level().isClientSide){
			ModRenderTypeBuffers.getInstance().toggleHakiAuraShader(false);
		}
	}
	
	
	private static boolean canUnlock(LivingEntity var0) {
		HakiData var1 = ((HakiData)(((java.util.Optional)(HakiData.get(var0))).orElse((Object)(null))));
		EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
		if (((var1) != (null)) && ((var2) != (null))){
			return ((var2.getDoriki()) > (1500D)) && (((double)(var1.getKenbunshokuHakiExp())) > (HakiHelper.getKenbunshokuAuraExpNeeded(var0)));
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("Uses Observation Haki to see the auras of all nearby creatures, differentiated by colors."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.HAKI,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KenbunshokuHakiAuraAbility::new)))).addDescriptionLine(lv4_3).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(KenbunshokuHakiAuraAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kenbunshoku_haki_aura","Kenbunshoku Haki: Aura",var0));
	}
public double getViewDistance(Player var1) {
		double var2 = ((Double)(EntityStatsData.get((LivingEntity)(var1)).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
		float var3 = ((Float)(HakiData.get((LivingEntity)(var1)).map((Function)(((Function<HakiData, Float>)(HakiData::getKenbunshokuHakiExp)))).orElse(Float.valueOf(0F)))).floatValue();
		Function<DevilFruitData, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(((AkumaNoMiItem)(ModFruits.GORO_GORO_NO_MI.get()))));
		};
		boolean var5 = ((Boolean)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1)))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue();
		double var6 = (var2) / (1000D);
		float var7 = (var3) / (2F);
		return (((var6) + ((double)(var7))) * (1.05D)) * ((var5) ? (4D) : (1D));
	}public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(60F,2400F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
			l0.holdTime(2400F);
		};
		ContinuousComponentTooltip.generate(var2,var4);
		var2.add(100,new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_RANGE),(float)(this.getViewDistance(var1))).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_AOE_BLOCKS).buildComponent());
	}}