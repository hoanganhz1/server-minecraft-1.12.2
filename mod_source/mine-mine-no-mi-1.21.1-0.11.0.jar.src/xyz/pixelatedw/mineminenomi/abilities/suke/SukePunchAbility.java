package xyz.pixelatedw.mineminenomi.abilities.suke;

import java.util.Optional;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SukePunchAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 1200F;
	private static final float PER_PERSON_COOLDOWN = 3600F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SukePunchAbility>> INSTANCE;

	public SukePunchAbility(AbilityCore<SukePunchAbility> var1) {
		super((AbilityCore)(var1));
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		EntityStatsData var4 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var2))).orElse((Object)(null))));
		if (((var4) != (null)) && (!((var4.getAllyInflictedInvisibilityCooldown()) > (0F)))){
			var4.setAllyInflictedInvisibilityCooldown(3600F);
			if (var2.hasEffect((Holder)(ModEffects.SUKE_INVISIBILITY))){
				var2.removeEffect((Holder)(ModEffects.SUKE_INVISIBILITY));
			}else{
				var2.addEffect(new MobEffectInstance((Holder)(ModEffects.SUKE_INVISIBILITY),2400,0,false,false,true));
			}
			return true;
		}else{
			return true;
		}
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchCooldown() {
		return 1200F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Turns an entity's entire body invisible after hitting them."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SukePunchAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("suke_punch","Suke Punch",var0));
	}
}