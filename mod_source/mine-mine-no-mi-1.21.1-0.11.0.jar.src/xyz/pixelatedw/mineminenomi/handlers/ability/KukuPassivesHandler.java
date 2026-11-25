package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.abilities.kuku.GourmetamorphosisAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class KukuPassivesHandler {
	// Fields
	private static final Component FOOD_TOOLTIP = ModI18n.ITEM_GOURMETAMORPHOSIS_FOOD.copy().setStyle(Style.EMPTY.applyFormat(ChatFormatting.YELLOW));

	public static void addKukuEdibleTooltips(Player var0, ItemStack var1, List<Component> var2) {
		if (var1.getItem() instanceof AkumaNoMiItem){
			return;
		}else{
			Function<AbilityData, GourmetamorphosisAbility> var3 = (l0) -> {
			return ((GourmetamorphosisAbility)(l0.getEquippedAbility(((AbilityCore)(GourmetamorphosisAbility.INSTANCE.get())))));
		};
			Function<GourmetamorphosisAbility, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.isContinuous());
		};
			if ((((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).map((Function)(var3)))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue()) && (!(var2.contains((Object)(FOOD_TOOLTIP))))){
				((java.util.List)(var2)).add((Object)(Component.empty()));
				var2.add(FOOD_TOOLTIP);
			}
			return;
		}
	}
	public static void enableKukuEating(Player var0, ItemStack var1) {
		if (var1.getItem() instanceof AkumaNoMiItem){
			return;
		}else{
			Function<AbilityData, GourmetamorphosisAbility> var2 = (l0) -> {
			return ((GourmetamorphosisAbility)(l0.getEquippedAbility(((AbilityCore)(GourmetamorphosisAbility.INSTANCE.get())))));
		};
			Function<GourmetamorphosisAbility, Boolean> var3 = (l0) -> {
			return Boolean.valueOf(l0.isContinuous());
		};
			if (((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).map((Function)(var2)))).map((Function)(var3)).orElse(Boolean.valueOf(false)))).booleanValue()){
				int var4 = 2;
				float var5 = 0.25F;
				FoodProperties var6 = var1.getItem().getFoodProperties(var1,(LivingEntity)(var0));
				if ((var6) != (null)){
					var4 = (var4) + (var6.nutrition());
					var5 = (var5) + (var6.saturation());
				}
				var0.spawnItemParticles(var1,16);
				var0.playSound(var0.getEatingSound(var1),(0.5F) + ((0.5F) * ((float)(var0.getRandom().nextInt(2)))),(((var0.getRandom().nextFloat()) - (var0.getRandom().nextFloat())) * (0.2F)) + (1F));
				var0.onEquippedItemBroken(var1.getItem(),EquipmentSlot.MAINHAND);
				var0.getFoodData().eat(var4,var5);
				var1.shrink(1);
			}
			return;
		}
	}
}