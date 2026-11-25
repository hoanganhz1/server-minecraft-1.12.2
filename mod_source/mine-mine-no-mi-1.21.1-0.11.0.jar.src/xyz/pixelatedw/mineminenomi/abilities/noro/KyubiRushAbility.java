package xyz.pixelatedw.mineminenomi.abilities.noro;

import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent$IUpdateDamageSource;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.PunchDamageTooltip;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KyubiRushAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 100F;
	private static final float MIN_DAMAGE = 1F;
	private static final float MAX_DAMAGE = 100F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KyubiRushAbility>> INSTANCE;
	protected final DamageSourceComponent damageSourceComponent;

	public KyubiRushAbility(AbilityCore<KyubiRushAbility> var1) {
		super((AbilityCore)(var1));
		this.damageSourceComponent = new DamageSourceComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.damageSourceComponent};
		super.addComponents(var2);
		this.damageSourceComponent.addDamageSourceUpdateEvent(100,(DamageSourceComponent$IUpdateDamageSource)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent$IUpdateDamageSource)(this::addPunchDamageToSource))));
	}
	private void addPunchDamageToSource(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (!(this.continuousComponent.isContinuous())){
			return;
		}else{
			float var6 = 0.0F;
			if (var2.hasEffect((Holder)(ModEffects.NORO_SLOWNESS))){
				var6 = Math.min(((float)(var2.getEffect((Holder)(ModEffects.NORO_SLOWNESS)).getDuration())) / (15F),100F);
				int var7 = (var2.getEffect((Holder)(ModEffects.NORO_SLOWNESS)).getDuration()) / (2);
				int var8 = Math.min((var2.getEffect((Holder)(ModEffects.NORO_SLOWNESS)).getAmplifier()) - (2),0);
				var2.removeEffect((Holder)(ModEffects.NORO_SLOWNESS));
				var2.addEffect(new MobEffectInstance((Holder)(ModEffects.NORO_SLOWNESS),var7,var8));
			}else{
				var6 = 1F;
			}
			WyHelper.spawnDamageIndicatorParticles(var1.level(),var2,Math.round(var6));
			IDamageSourceHandler.getHandler(var3).addAbilityDamage(var6,super.getCore());
			return;
		}
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
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
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("While the opponent is slowed, the user delivers a series of punches, which hits the opponent all at once (a stronger slowness effect causes more damage)"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			SourceType[] lv2_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KyubiRushAbility::new)))).addDescriptionLine(lv2_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kyubi_rush","Kyubi Rush",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<PunchDamageTooltip> var3 = (l0) -> {
			l0.damage(1F,100F);
		};
		PunchDamageTooltip.generate(var2,var3);
	}
}