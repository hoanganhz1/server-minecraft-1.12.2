package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class SwordsmanPassivesHandler {
	public static void applySwordsmanBounsAttribute(ItemAttributeModifierEvent var0, ItemStack var1) {
		if ((!(var1.isEmpty())) && (ItemsHelper.isSword(var1))){
			double var2 = ((Double)(var1.getOrDefault(ModDataComponents.SWORDSMAN_BONUS,Double.valueOf(0D)))).doubleValue();
			if ((var2) > (0D)){
				AttributeModifier var3 = new AttributeModifier(AttributeHelper.PASSIVE_SWORDSMAN_ATTACK_BONUS_ID,var2,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
				var0.addModifier(Attributes.ATTACK_DAMAGE,var3,EquipmentSlotGroup.MAINHAND);
			}else{
				if ((var2) == (0D)){
					var0.removeModifier(Attributes.ATTACK_DAMAGE,AttributeHelper.PASSIVE_SWORDSMAN_ATTACK_BONUS_ID);
				}else{
					return;
				}
			}
			return;
		}else{
			return;
		}
	}
	public static void applySwordsmanBonusTag(LivingEntity var0, EquipmentSlot var1, ItemStack var2) {
		if (!(var1.equals(EquipmentSlot.MAINHAND))){
			return;
		}else{
			if ((!(var2.isEmpty())) && (ItemsHelper.isSword(var2))){
				if (((Boolean)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isSwordsman)))).orElse(Boolean.valueOf(false)))).booleanValue()){
					double var3 = ((Double)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
					var2.set(ModDataComponents.SWORDSMAN_BONUS,Double.valueOf((var3) * (2.5e-05D)));
				}else{
					var2.remove(ModDataComponents.SWORDSMAN_BONUS);
				}
			}
			return;
		}
	}
}