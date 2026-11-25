package xyz.pixelatedw.mineminenomi.abilities.gomu;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilitySet;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class GomuHelper {
	// Fields
	private static final AbilitySet GEARS = new AbilitySet(new Supplier[]{GearSecondAbility.INSTANCE,GearThirdAbility.INSTANCE,GearFourthAbility.INSTANCE});
	public static final Component SECOND_GEAR_TOOLTIP;
	public static final Component THIRD_GEAR_TOOLTIP;
	public static final Component FOURTH_GEAR_TOOLTIP;
	public static final Component FIFTH_GEAR_TOOLTIP;
	public static final AbilityDescriptionLine$IDescriptionLine SECOND_GEAR_REQ;
	public static final AbilityDescriptionLine$IDescriptionLine THIRD_GEAR_REQ;
	public static final AbilityDescriptionLine$IDescriptionLine FOURTH_GEAR_REQ;
	public static final AbilityDescriptionLine$IDescriptionLine FIFTH_GEAR_REQ;

	public static Result requiresGearFifth(LivingEntity var0, IAbility var1) {
		Function<AbilityData, GearFifthAbility> var2 = (l0) -> {
			return ((GearFifthAbility)(l0.getEquippedAbility(((AbilityCore)(GearFifthAbility.INSTANCE.get())))));
		};
		if (((Boolean)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var2)).map((Function)(((Function<GearFifthAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()){
			return Result.success();
		}else{
			return Result.fail(null);
		}
	}
	public static Ability$ICanUseEvent canUseGearCheck(AbilityCore<?> var0) {
		AbilityCore<?> var1 = var0;
		final AbilityCore<?> var1_f1 = var1;
		Ability$ICanUseEvent var2 = (l0, l1) -> {
			AbilityData lv2_3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(l0))).orElse((Object)(null))));
			if ((lv2_3) == (null)){
				return Result.fail(null);
			}else{
				boolean lv2_4 = false;
				Iterator lv2_5 = GEARS.iterator();
				do{
					if (lv2_5.hasNext()){
						Supplier lv2_6 = ((Supplier)(lv2_5.next()));
						if (var1_f1.equals(lv2_6.get())){
							continue;
						}else{
							IAbility lv2_7 = lv2_3.getEquippedAbility(((AbilityCore)(lv2_6.get())));
							if (((lv2_7) == (null)) || (!(((Boolean)(lv2_7.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Boolean>)(ContinuousComponent::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()))){
								continue;
							}else{
								lv2_4 = true;
								break;
							}
						}
					}else{
						break;
					}
				} while (true);
				if (lv2_4){
					return Result.fail(ModI18nAbilities.MESSAGE_GEAR_ACTIVE);
				}else{
					return Result.success();
				}
			}
		};
		return var2;
	}
	static  {
		Object[] var0 = new Object[]{MentionHelper.tryMention(GearSecondAbility.INSTANCE)};
		SECOND_GEAR_TOOLTIP = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var0);
		Object[] var1 = new Object[]{MentionHelper.tryMention(GearThirdAbility.INSTANCE)};
		THIRD_GEAR_TOOLTIP = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var1);
		Object[] var2 = new Object[]{MentionHelper.tryMention(GearFourthAbility.INSTANCE)};
		FOURTH_GEAR_TOOLTIP = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var2);
		Object[] var3 = new Object[]{MentionHelper.tryMention(GearFifthAbility.INSTANCE)};
		FIFTH_GEAR_TOOLTIP = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var3);
		Object[] var4 = new Object[]{MentionHelper.tryMention(GearSecondAbility.INSTANCE)};
		SECOND_GEAR_REQ = AbilityDescriptionLine$IDescriptionLine.of((Component)(Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var4)));
		Object[] var5 = new Object[]{MentionHelper.tryMention(GearThirdAbility.INSTANCE)};
		THIRD_GEAR_REQ = AbilityDescriptionLine$IDescriptionLine.of((Component)(Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var5)));
		Object[] var6 = new Object[]{MentionHelper.tryMention(GearFourthAbility.INSTANCE)};
		FOURTH_GEAR_REQ = AbilityDescriptionLine$IDescriptionLine.of((Component)(Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var6)));
		Object[] var7 = new Object[]{MentionHelper.tryMention(GearFifthAbility.INSTANCE)};
		FIFTH_GEAR_REQ = AbilityDescriptionLine$IDescriptionLine.of((Component)(Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var7)));
	}
}