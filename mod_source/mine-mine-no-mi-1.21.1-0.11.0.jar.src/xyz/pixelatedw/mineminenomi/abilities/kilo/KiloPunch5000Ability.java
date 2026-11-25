package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IAbilityGoalFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KiloPunch5000Ability extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 140F;
	private static final ResourceLocation SPEED_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.kilo_punch.speed");
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KiloPunch5000Ability>> INSTANCE;
	private static final AttributeModifier MOVEMENT_MODIFIER;

	public KiloPunch5000Ability(AbilityCore<KiloPunch5000Ability> var1) {
		super((AbilityCore)(var1));
		this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,MOVEMENT_MODIFIER);
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public boolean isParallel() {
		return true;
	}
	public float getPunchDamage() {
		return 20F;
	}
	public float getPunchCooldown() {
		return 140F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Delivers a 5000 kilo punch, the user is slowed down due to the extra kilos"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			SourceType[] lv2_4 = new SourceType[]{SourceType.FIST};
			AbilityCore$IAbilityGoalFactory[] lv2_5 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv2_6 = KiloPunch5000Ability::createWrapper;
			lv2_5[0] = lv2_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KiloPunch5000Ability::new)))).addDescriptionLine(lv2_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_4).addGoalWrapper(lv2_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("5000_kilo_punch","5000 Kilo Punch",var0));
		MOVEMENT_MODIFIER = new AttributeModifier(SPEED_ID,-0.01D,AttributeModifier.Operation.ADD_VALUE);
	}

	public static CloseMeleeAbilityWrapperGoal<KiloPunch5000Ability> createWrapper(Mob var0, AbilityCore<KiloPunch5000Ability> var1) {
		CloseMeleeAbilityWrapperGoal var2 = new CloseMeleeAbilityWrapperGoal(var0,((AbilityCore)(INSTANCE.get())));
		KiloPunch5000Ability var3 = ((KiloPunch5000Ability)(var2.getAbility()));
		Mob var4 = var0;
		final Mob var4_f1 = var4;
		Ability$ICanUseEvent var5 = (l0, l1) -> {
			if (!(var4_f1.onGround())){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		};
		var3.addCanUseCheck(90,var5);
		return var2;
	}}