package xyz.pixelatedw.mineminenomi.abilities.kame;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KameGuardPointAbility extends MorphAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KameGuardPointAbility>> INSTANCE;
	private static final AttributeModifier HEALTH_MODIFIER;
	private static final AttributeModifier ARMOR_MODIFIER;
	private static final AttributeModifier ATTACK_SPEED_MODIFIER;
	private static final AttributeModifier KNOCKBACK_RESISTANCE_MODIFIER;
	private static final AttributeModifier JUMP_BOOST_MODIFIER;
	private static final AttributeModifier GROUND_SPEED_MODIFIER;
	private static final AttributeModifier ICE_SPEED_MODIFIER;
	private static final AttributeModifier SWIM_SPEED_MODIFIER;
	private static final AttributeModifier FRICTION_MODIFIER;
	protected final DamageTakenComponent damageTakenComponent;
	private boolean hasIceBuff;

	public KameGuardPointAbility(AbilityCore<KameGuardPointAbility> var1) {
		super((AbilityCore)(var1));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnHurtEvent(100,(DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onHurtEvent))));
		this.hasIceBuff = false;
		AbilityComponent[] var2 = new AbilityComponent[]{this.damageTakenComponent};
		this.addComponents(var2);
		this.statsComponent.addAttributeModifier(Attributes.ARMOR,ARMOR_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.MAX_HEALTH,HEALTH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ATTACK_SPEED,ATTACK_SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,KNOCKBACK_RESISTANCE_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT,JUMP_BOOST_MODIFIER);
		this.statsComponent.addAttributeModifier(NeoForgeMod.SWIM_SPEED,SWIM_SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.FRICTION,FRICTION_MODIFIER,(Predicate)(((Predicate<LivingEntity>)(this::slideOnIce))));
		this.continuousComponent.addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent))));
	}
	private float onHurtEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((this.continuousComponent.isContinuous()) && ((!(var1.level().isClientSide)) && (var1.isCrouching()))){
			var4 = (var4) * (0.1F);
		}
		return var4;
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		boolean var3 = false;
		if ((!(this.hasIceBuff)) && (this.slideOnIce(var1))){
			this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,ICE_SPEED_MODIFIER);
			this.hasIceBuff = true;
			var3 = true;
		}else{
			if ((this.hasIceBuff) && (!(this.slideOnIce(var1)))){
				this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,GROUND_SPEED_MODIFIER);
				this.hasIceBuff = false;
				var3 = true;
			}
		}
		if (var3){
			this.statsComponent.applyModifiers(var1);
		}
	}
	public boolean slideOnIce(LivingEntity var1) {
		if (var1.isCrouching()){
			return false;
		}else{
			BlockState var2 = var1.level().getBlockState(var1.blockPosition().below());
			if ((var1.onGround()) && (var2.is(BlockTags.ICE))){
				return true;
			}else{
				return false;
			}
		}
	}
	public MorphInfo getTransformation() {
		return ((MorphInfo)(ModMorphs.KAME_GUARD.get()));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Transforms the user into a turtle, which focuses on defense."),(Object)(null)),ImmutablePair.of((Object)("Sneaking allows you to retract into your shell."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KameGuardPointAbility::new)))).addDescriptionLine(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kame_guard_point","Kame Guard Point",var0));
		HEALTH_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_HEALTH_UUID,10D,AttributeModifier.Operation.ADD_VALUE);
		ARMOR_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ARMOR_UUID,20D,AttributeModifier.Operation.ADD_VALUE);
		ATTACK_SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID,-0.15000000596046448D,AttributeModifier.Operation.ADD_VALUE);
		KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID,2D,AttributeModifier.Operation.ADD_VALUE);
		JUMP_BOOST_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID,-10D,AttributeModifier.Operation.ADD_VALUE);
		GROUND_SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID,-0.85D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		ICE_SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID,-0.25D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		SWIM_SPEED_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_SWIM_SPEED_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
		FRICTION_MODIFIER = new AttributeModifier(AttributeHelper.MORPH_FRICTION_UUID,1.899999976158142D,AttributeModifier.Operation.ADD_VALUE);
	}
}