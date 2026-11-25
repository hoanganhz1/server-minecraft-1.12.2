package xyz.pixelatedw.mineminenomi.abilities.horu;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TensionHormoneAbility extends PunchAbility {
	// Fields
	private static final int COOLDOWN = 400;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<TensionHormoneAbility>> INSTANCE;

	public TensionHormoneAbility(AbilityCore<TensionHormoneAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.isCrouching()){
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.TENSION_HORMONE),200,1));
			this.cooldownComponent.startCooldown(var1,400F);
		}
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.TENSION_HORMONE),200,1));
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
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user injects the target with special hormones providing a supply of adrenaline that strengthens them."),(Object)(null)),ImmutablePair.of((Object)("§aSHIFT-USE§r: User injects themselves"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST,SourceType.FRIENDLY};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(TensionHormoneAbility::new)))).addDescriptionLine(lv1_3).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("tension_hormone","Tension Hormone",var0));
	}
}