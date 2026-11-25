package xyz.pixelatedw.mineminenomi.mixins;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

@Mixin(value={ItemStack.class})
public class ItemStackMixin {
	@Inject(method={"hurtAndBreak"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$hurtAndBreak(int var1, ServerLevel var2, @Nullable() ServerPlayer var3, Consumer<Item> var4, CallbackInfo var5) {
		ItemStack var6 = ((ItemStack)(this));
		Item var7 = var6.getItem();
		if (((var7.getFoodProperties(var6,(LivingEntity)(var3))) == (null)) && (!(ServerConfig.isItemBannedFromImbuing(var7)))){
			if (HakiHelper.hasImbuingActive((LivingEntity)(var3))){
				var5.cancel();
			}
			return;
		}else{
			return;
		}
	}
}