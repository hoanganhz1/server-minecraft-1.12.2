package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.items.armors.WootzArmorItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ClimaTactItem;

public class AbilityUseConditions {
	public static Result canEnableHaki(LivingEntity var0, IAbility var1) {
		HakiData var2 = ((HakiData)(((java.util.Optional)(HakiData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return Result.fail(null);
		}else{
			if ((var2.getHakiOveruse()) < (var2.getMaxOveruse())){
				return Result.success();
			}else{
				return Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_HAKI);
			}
		}
	}
	public static Result requiresOnGround(LivingEntity var0, IAbility var1) {
		if (var0.onGround()){
			return Result.success();
		}else{
			return Result.fail(ModI18nAbilities.MESSAGE_ONLY_IN_GROUND);
		}
	}
	public static Result canUseMomentumAbilities(LivingEntity var0) {
		AttributeInstance var1 = var0.getAttribute(ModAttributes.JUMP_HEIGHT);
		if (((var1) != (null)) && ((var1.getValue()) <= (0D))){
			return Result.fail(ModI18nAbilities.MESSAGE_CANT_MOVE);
		}else{
			AttributeInstance var2 = var0.getAttribute(Attributes.MOVEMENT_SPEED);
			if (((var2) != (null)) && ((var2.getValue()) <= (0D))){
				return Result.fail(ModI18nAbilities.MESSAGE_CANT_MOVE);
			}else{
				return Result.success();
			}
		}
	}
	public static Result canUseMomentumAbilities(LivingEntity var0, IAbility var1) {
		return canUseMomentumAbilities(var0);
	}
	public static Result requiresActiveAbility(LivingEntity var0, IAbility var1, AbilityCore<?>... var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var3) == (null)){
			return Result.fail(null);
		}else{
			AbilityCore<?>[] var4 = var2;
			int var5 = var4.length;
			int var6 = 0;
			do{
				if ((var6) < (var5)){
					IAbility var7 = var3.getEquippedAbility(var4[var6]);
					if (((var7) != (null)) && (((Boolean)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Boolean>)(ContinuousComponent::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue())){
						return Result.success();
					}else{
						var6++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			MutableComponent var7_1 = null;
			if ((var2.length) == (1)){
				Object[] var8 = new Object[]{var2[0].getLocalizedName()};
				var7_1 = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var8);
			}else{
				if ((var2.length) >= (2)){
					Object[] var8_1 = new Object[]{var2[0].getLocalizedName(),var2[1].getLocalizedName()};
					var7_1 = Component.translatable(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE,var8_1);
				}
			}
			return Result.fail((Component)(var7_1));
		}
	}
	public static Result requiresMorph(LivingEntity var0, IAbility var1, MorphInfo... var2) {
		MorphInfo[] var3 = var2;
		int var4 = var3.length;
		int var5 = 0;
		do{
			if ((var5) < (var4)){
				if (var3[var5].isActive(var0)){
					return Result.success();
				}else{
					var5++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		MutableComponent var6 = null;
		if ((var2.length) == (1)){
			Object[] var7 = new Object[]{var2[0].getDisplayName()};
			var6 = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var7);
		}else{
			if ((var2.length) >= (2)){
				Object[] var7_1 = new Object[]{var2[0].getDisplayName(),var2[1].getDisplayName()};
				var6 = Component.translatable(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE,var7_1);
			}
		}
		return Result.fail((Component)(var6));
	}
	public static Result requiresTransformationSpace(LivingEntity var0, IAbility var1) {
		MorphInfo var2 = null;
		if (var1 instanceof MorphAbility){
			var2 = ((MorphAbility)(var1)).getTransformation();
		}else{
			if (var1.hasComponent(((AbilityComponentKey)(ModAbilityComponents.MORPH.get())))){
				var2 = ((MorphComponent)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.MORPH.get()))).get())).getMorphInfo();
			}
		}
		if ((var2) == (null)){
			return Result.fail(null);
		}else{
			EntityDimensions var3 = var2.getSize(var0,Pose.STANDING);
			if ((var3) == (null)){
				return Result.success();
			}else{
				float var4 = var3.height();
				BlockPos.MutableBlockPos var5 = new BlockPos.MutableBlockPos(var0.position().x(),(double)(Math.round(var0.position().y())),var0.position().z());
				int var6 = 0;
				do{
					if (((float)(var6)) < (var4)){
						if (!(var0.level().getBlockState((BlockPos)(var5)).isFaceSturdy((BlockGetter)(var0.level()),(BlockPos)(var5),Direction.UP))){
							var5 = var5.move(0,1,0);
							var6++;
							continue;
						}else{
							return Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_SPACE);
						}
					}else{
						break;
					}
				} while (true);
				return Result.success();
			}
		}
	}
	public static Result requiresOneFreeHand(LivingEntity var0, IAbility var1) {
		if ((!(var0.getMainHandItem().isEmpty())) && (!(var0.getOffhandItem().isEmpty()))){
			return Result.fail(ModI18nAbilities.MESSAGE_NEED_ONE_FREE_HAND);
		}else{
			return Result.success();
		}
	}
	public static Result requiresEmptyHand(LivingEntity var0, IAbility var1) {
		Result var2 = requiresHands(var0,var1);
		if (var2.isFail()){
			return var2;
		}else{
			ItemStack var3 = var0.getMainHandItem();
			if ((!(var3.isEmpty())) && (!(var3.is(ModTags$Items.PUNCH)))){
				return Result.fail(ModI18nAbilities.MESSAGE_NEED_FIST);
			}else{
				return Result.success();
			}
		}
	}
	public static Result requiresHands(LivingEntity var0, IAbility var1) {
		if (var0.hasEffect((Holder)(ModEffects.NO_HANDS))){
			return Result.fail(ModI18nAbilities.MESSAGE_NO_LIMBS);
		}else{
			return Result.success();
		}
	}
	public static Result requiresClimaTact(LivingEntity var0, IAbility var1) {
		if (var0.getMainHandItem().getItem() instanceof ClimaTactItem){
			return Result.success();
		}else{
			return Result.fail(ModI18nAbilities.MESSAGE_NEED_CLIMA_TACT);
		}
	}
	public static Result requiresBluntWeapon(LivingEntity var0, IAbility var1) {
		if (ItemsHelper.isBlunt(var0.getMainHandItem())){
			return Result.success();
		}else{
			return Result.fail(ModI18nAbilities.MESSAGE_NEED_BLUNT);
		}
	}
	public static Result requiresSword(LivingEntity var0, IAbility var1) {
		ItemStack var2 = var0.getMainHandItem();
		if (ItemsHelper.isSword(var2)){
			return Result.success();
		}else{
			Function<AbilityData, Ability> var3 = (l0) -> {
			return ((Ability)(l0.getEquippedAbility(((AbilityCore)(SparClawAbility.INSTANCE.get())))));
		};
			Function var4 = (Function) ((Function<Ability, Optional>) ((l0) -> {
			return l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())));
		}));
			Optional var5 = AbilityData.get(var0).filter((Predicate)(((Predicate<AbilityData>)(Objects::nonNull)))).map(var3).map(var4).filter((Predicate)(((Predicate<Optional>)(Optional::isPresent)))).map((Function)(((Function<Optional<ContinuousComponent>, ContinuousComponent>)(Optional::get))));
			if (((((var5.isPresent()) && (((ContinuousComponent)(var5.get())).isContinuous())) ? (1) : (0)) != (0)) && (var2.isEmpty())){
				return Result.success();
			}else{
				return Result.fail(ModI18nAbilities.MESSAGE_NEED_SWORD);
			}
		}
	}
	public static Result requiresMeleeWeapon(LivingEntity var0, IAbility var1) {
		if (requiresSword(var0,var1).isSuccess()){
			return Result.success();
		}else{
			if (requiresBluntWeapon(var0,var1).isSuccess()){
				return Result.success();
			}else{
				return Result.fail(ModI18nAbilities.MESSAGE_NEED_MELEE_WEAPON);
			}
		}
	}
	public static Result requiresMedicBag(LivingEntity var0, IAbility var1) {
		if (ItemsHelper.hasItemInSlot(var0,EquipmentSlot.CHEST,((Item)(ModArmors.MEDIC_BAG.get())))){
			return Result.success();
		}else{
			return Result.fail(ModI18nAbilities.MESSAGE_NEED_MEDIC_BAG);
		}
	}
	public static Result requiresAirTime(LivingEntity var0, IAbility var1) {
		if (var0.onGround()){
			return Result.fail(ModI18nAbilities.MESSAGE_NEED_AIR_TIME);
		}else{
			return Result.success();
		}
	}
	public static Result requiresDryUser(LivingEntity var0, IAbility var1) {
		if (!(var0.isInWaterOrRain())){
			return Result.success();
		}else{
			return Result.fail(ModI18nAbilities.MESSAGE_ONLY_WHEN_DRY);
		}
	}
	public static Result requiresWootzArmor(LivingEntity var0, IAbility var1) {
		if (WootzArmorItem.hasArmorEquipped(var0)){
			return Result.success();
		}else{
			return Result.fail(null);
		}
	}
	public static Result requiresInAir(LivingEntity var0, IAbility var1) {
		if (!(var0.onGround())){
			return Result.success();
		}else{
			return Result.fail(ModI18nAbilities.MESSAGE_ONLY_IN_AIR);
		}
	}
}