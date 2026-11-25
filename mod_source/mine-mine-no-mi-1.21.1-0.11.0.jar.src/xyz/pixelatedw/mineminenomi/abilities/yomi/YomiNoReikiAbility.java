package xyz.pixelatedw.mineminenomi.abilities.yomi;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.OutOfBodyAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.entities.PhysicalBodyEntity;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class YomiNoReikiAbility extends OutOfBodyAbility {
	// Fields
	private static final int HOLD_TIME = 4000;
	private static final int MIN_COOLDOWN = 100;
	private static final int MAX_COOLDOWN = 2000;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<YomiNoReikiAbility>> INSTANCE;
	private final DamageTakenComponent damageTakenComponent;

	public YomiNoReikiAbility(AbilityCore<YomiNoReikiAbility> var1) {
		super((AbilityCore)(var1));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::damageTakenEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.damageTakenComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		AbilityHelper.setDeltaMovement((Entity)(var1),0D,5D,0D);
		PhysicalBodyEntity var3 = new PhysicalBodyEntity(((EntityType)(ModMobs.SKELLY_BODY.get())),var1.level());
		var3.moveTo(var1.getX(),var1.getY(),var1.getZ(),var1.getYRot(),var1.getXRot());
		var3.setOwner(var1);
		var1.level().addFreshEntity((Entity)(var3));
		var3.setParentAbility((Ability)(this));
		this.setOriginalBody((LivingEntity)(var3));
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		float var3 = Math.max(100F,(this.continuousComponent.getContinueTime()) / (2F));
		this.cooldownComponent.startCooldown(var1,var3);
	}
	public float damageTakenEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((this.isActive()) && (!(var3.is(ModDamageTypes.OUT_OF_BODY)))){
			return 0F;
		}else{
			return var4;
		}
	}
	public int getHoldTime() {
		return 4000;
	}
	public float getMaxRange() {
		return 100F;
	}
	public boolean isPhysical() {
		return false;
	}
	private static boolean canUnlock(LivingEntity var0) {
		return ((Boolean)(DevilFruitData.get(var0).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::hasYomiPower)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("The user's spirit leaves their body, allowing them to freely explore the nearby areas."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(YomiNoReikiAbility::new)))).addDescriptionLine(lv2_3).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(YomiNoReikiAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("yomi_no_reiki","Yomi no Reiki",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(100F,2000F);
		};
		CooldownComponentTooltip.generate(var2,var3);
	}
}