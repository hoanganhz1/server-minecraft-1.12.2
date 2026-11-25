package xyz.pixelatedw.mineminenomi.abilities.doru;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility$GuardBreakKind;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility$GuardValue;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DoruDoruBallAbility extends GuardAbility {
	// Fields
	private static final float HOLD_TIME = 200F;
	private static final float MIN_COOLDOWN = 30F;
	private static final float MAX_COOLDOWN = 230F;
	private static final GuardAbility$GuardValue STANDARD_GUARD_VALUE = GuardAbility$GuardValue.percentage(0.2F,GuardAbility$GuardBreakKind.TOTAL,200F);
	private static final GuardAbility$GuardValue CHAMPION_GUARD_VALUE = GuardAbility$GuardValue.percentage(0.5F,GuardAbility$GuardBreakKind.TOTAL,400F);
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DoruDoruBallAbility>> INSTANCE;
	private final PoolComponent poolComponent;
	public double rotateAngleX;
	public double rotateAngleZ;
	public float[] color;
	private boolean canChanceColor;
	private GuardAbility$GuardValue guardValue;

	public DoruDoruBallAbility(AbilityCore<DoruDoruBallAbility> var1) {
		super((AbilityCore)(var1));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GUARD_ABILITY,new AbilityPool[0]);
		this.rotateAngleX = 0D;
		this.rotateAngleZ = 0D;
		this.color = new float[3];
		this.guardValue = STANDARD_GUARD_VALUE;
		AbilityComponent[] var2 = new AbilityComponent[]{this.poolComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.rotateAngleX = 0D;
		this.rotateAngleZ = 0D;
		this.canChanceColor = true;
	}
	
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.rotateAngleX = 0D;
		this.rotateAngleZ = 0D;
		var1.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
		var1.removeEffect(MobEffects.DIG_SLOWDOWN);
		float var3 = (30F) + (this.continuousComponent.getContinueTime());
		this.cooldownComponent.startCooldown(var1,var3);
	}
	public float getHoldTime() {
		return 200F;
	}
	public void onGuard(LivingEntity var1, DamageSource var2, float var3, float var4) {
	}
	public GuardAbility$GuardValue getGuard(LivingEntity var1) {
		return this.guardValue;
	}
	public void randomizeColor(LivingEntity var1) {
		if (!(this.canChanceColor)){
			return;
		}else{
			this.color[0] = 1F;
			this.color[1] = 1F;
			this.color[2] = 1F;
			if ((ItemsHelper.countItemInInventory(var1,((Item)(ModItems.COLOR_PALETTE.get())))) > (0)){
				this.color[0] = var1.getRandom().nextFloat();
				this.color[1] = var1.getRandom().nextFloat();
				this.color[2] = var1.getRandom().nextFloat();
			}
			this.canChanceColor = false;
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Puts the user into a hardened wax ball for max defense"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(30F,230F),ContinuousComponent.getTooltip(200F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DoruDoruBallAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("doru_doru_ball","Doru Doru Ball",var0));
	}
private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.isOnFire()){
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			var1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20,5,true,false));
			var1.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,20,5,true,false));
			Function<AbilityData, CandleChampionAbility> var3 = (l0) -> {
			return ((CandleChampionAbility)(l0.getEquippedAbility(((AbilityCore)(CandleChampionAbility.INSTANCE.get())))));
		};
			boolean var4 = ((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).map((Function)(((Function<CandleChampionAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue();
			this.guardValue = STANDARD_GUARD_VALUE;
			if (var4){
				this.guardValue = CHAMPION_GUARD_VALUE;
			}
			return;
		}
	}}