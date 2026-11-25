package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HanpatsuAbility extends PunchAbility {
	// Fields
	private static final int COOLDOWN = 100;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HanpatsuAbility>> INSTANCE;

	public HanpatsuAbility(AbilityCore<HanpatsuAbility> var1) {
		super((AbilityCore)(var1));
	}
	public float getPunchDamage() {
		return 0F;
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		double var4 = (WyHelper.randomDouble()) * (400D);
		double var5 = 0.0;
		if ((var4) >= (0D)){
			var5 = Mth.clamp(var4,200D,400D);
		}else{
			var5 = Mth.clamp(var4,-400D,-200D);
		}
		double var6 = (WyHelper.randomDouble()) * (400D);
		double var7 = 0.0;
		if ((var6) >= (0D)){
			var7 = Mth.clamp(var6,200D,400D);
		}else{
			var7 = Mth.clamp(var6,-400D,-200D);
		}
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.LAUNCHED),1200,0));
		AbilityHelper.setDeltaMovement((Entity)(var2),var5,10D,var7);
		var2.fallDistance = 0F;
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.PAD_HO_SFX.get())),SoundSource.PLAYERS,2F,0.75F);
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchCooldown() {
		return 100F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Anyone the user punches gets sent flying far into the air"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HanpatsuAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("hanpatsu","Hanpatsu",var0));
	}
}