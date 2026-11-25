package xyz.pixelatedw.mineminenomi.abilities.horu;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GanmenSeichoHormoneAbility extends PunchAbility {
	// Fields
	private static final int COOLDOWN = 400;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GanmenSeichoHormoneAbility>> INSTANCE;
	private final MorphComponent morphComponent;

	public GanmenSeichoHormoneAbility(AbilityCore<GanmenSeichoHormoneAbility> var1) {
		super((AbilityCore)(var1));
		this.morphComponent = new MorphComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.morphComponent};
		this.addComponents(var2);
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::tickEvent))));
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.isCrouching()){
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.GANMEN_SEICHO_HORMONE),300,0));
			this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.GANMEN_SEICHO.get())));
			super.consumeUseAndStop();
		}
	}
	private void tickEvent(LivingEntity var1, IAbility var2) {
		if (this.morphComponent.isMorphed()){
			if ((((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var1))).orElse((Object)(null))))) == (null)){
				return;
			}else{
				if (!(var1.hasEffect((Holder)(ModEffects.GANMEN_SEICHO_HORMONE)))){
					this.morphComponent.stopMorph(var1);
				}else{
					return;
				}
			}
		}
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.GANMEN_SEICHO_HORMONE),300,0));
		this.morphComponent.startMorph(var2,((MorphInfo)(ModMorphs.GANMEN_SEICHO.get())));
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchCooldown() {
		return 400F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("By injecting special growth hormones into a target, their head expands to enormous proportions."),(Object)(null)),ImmutablePair.of((Object)("§aSHIFT-USE§r: User injects themselves boosting the §aDeath Wink§r ability as well"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST,SourceType.FRIENDLY};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GanmenSeichoHormoneAbility::new)))).addDescriptionLine(lv1_3).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("ganmen_seicho_hormone","Ganmen Seicho Hormone",var0));
	}
}