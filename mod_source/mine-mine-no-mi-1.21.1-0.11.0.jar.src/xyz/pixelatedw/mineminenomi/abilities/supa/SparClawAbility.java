package xyz.pixelatedw.mineminenomi.abilities.supa;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SparClawAbility extends PunchAbility {
	// Fields
	private static final float HOLD_TIME = 1000F;
	private static final float MIN_COOLDOWN = 20F;
	private static final float MAX_COOLDOWN = 1020F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SparClawAbility>> INSTANCE;
	private final MorphComponent morphComponent;

	public SparClawAbility(AbilityCore<SparClawAbility> var1) {
		super((AbilityCore)(var1));
		this.morphComponent = new MorphComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.morphComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::continuousStartEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::continuousStopEvent))));
	}
	private void continuousStartEvent(LivingEntity var1, IAbility var2) {
		this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.SPAR_CLAW.get())));
	}
	private void continuousStopEvent(LivingEntity var1, IAbility var2) {
		this.consumeUseAndStop();
		this.morphComponent.stopMorph(var1);
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		return true;
	}
	public int getUseLimit() {
		return -1;
	}
	public boolean isParallel() {
		return true;
	}
	public float getPunchHoldTime() {
		return 1000F;
	}
	public float getPunchDamage() {
		return 15F;
	}
	public float getPunchCooldown() {
		return (20F) + (this.continuousComponent.getContinueTime());
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Turns the undersides of the user's fingers into blades to slash opponents with"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			SourceType[] lv2_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SparClawAbility::new)))).addDescriptionLine(lv2_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("spar_claw","Spar Claw",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(20F,1020F);
		};
		CooldownComponentTooltip.generate(var2,var3);
	}
}