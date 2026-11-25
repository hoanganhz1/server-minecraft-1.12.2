package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.baku.BakuFactoryAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

@Mixin(value={AbstractContainerMenu.class})
public class AbstractContainerMenuMixin {
	@Inject(method={"stillValid"}, at={@At(value="HEAD")}, cancellable=true)
	private static void mineminenomi$isWithinUsableDistance(ContainerLevelAccess var0, Player var1, Block var2, CallbackInfoReturnable<Boolean> var3) {
		if ((var2) == (Blocks.CRAFTING_TABLE)){
			AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).orElse((Object)(null))));
			if ((var4) != (null)){
				BakuFactoryAbility var5 = ((BakuFactoryAbility)(var4.getEquippedAbility(((AbilityCore)(BakuFactoryAbility.INSTANCE.get())))));
				if (((((var5) != (null)) && (var5.isContinuous())) ? (1) : (0)) != (0)){
					var3.setReturnValue(Boolean.valueOf(true));
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	@Inject(method={"doClick"}, at={@At(value="HEAD")}, cancellable=true)
	private void mineminenomi$doClick(int var1, int var2, ClickType var3, Player var4, CallbackInfo var5) {
		if (ServerConfig.hasOneFruitPerWorldExtendedLogic()){
			AbstractContainerMenu var6 = ((AbstractContainerMenu)(this));
			if (((var3) == (ClickType.QUICK_MOVE)) && (!(((var2) != (0)) && ((var2) != (1))))){
				try{
					Slot var7 = ((Slot)(var6.slots.get(var1)));
					if ((var7) != (null)){
						ItemStack var8 = var7.getItem();
						if ((!(var8.isEmpty())) && ((var8.getItem() instanceof AkumaNoMiItem) && (var7.mayPickup(var4)))){
							var5.cancel();
						}else{
							return;
						}
					}else{
						return;
					}
				}catch(Exception var7_1){
					var7_1.printStackTrace();
					var5.cancel();
				}
			}else{
				return;
			}
		}
	}
}