package xyz.pixelatedw.mineminenomi.abilities.chiyu;

import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.HealComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HealingTouchAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 200F;
	private static final float HEAL_AMOUNT = 20F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HealingTouchAbility>> INSTANCE;
	private final HealComponent healComponent;

	public HealingTouchAbility(AbilityCore<HealingTouchAbility> var1) {
		super((AbilityCore)(var1));
		this.healComponent = new HealComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.healComponent};
		this.addComponents(var2);
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		this.healComponent.healTarget(var1,var2,20F);
		var2.addEffect(new MobEffectInstance(MobEffects.REGENERATION,400,1));
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HEALING_TOUCH.get())),(Entity)(var1),var2.getX(),var2.getY(),var2.getZ());
		this.increaseUses();
		return true;
	}
	public float getPunchCooldown() {
		return 200F;
	}
	public int getUseLimit() {
		return 1;
	}
	public boolean isParallel() {
		return true;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Touch the target to heal them"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			SourceType[] lv2_4 = new SourceType[]{SourceType.FIST,SourceType.FRIENDLY};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HealingTouchAbility::new)))).addDescriptionLine(lv2_3).setSourceType(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("healing_touch","Healing Touch",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<HealComponentTooltip> var3 = (l0) -> {
			l0.heal(20F);
		};
		HealComponentTooltip.generate(var2,var3);
	}
}