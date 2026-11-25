package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.List;
import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;

public class BigRedNoseItem extends Mod3DArmorItem {
	// Fields
	private static final Component DESCRIPTION = createDescription();

	private static Component createDescription() {
		StringBuilder var0 = new StringBuilder();
		var0.append("§k");
		Random var1 = new Random();
		int var2 = (50) + (var1.nextInt(100));
		int var3 = 0;
		do{
			if ((var3) < (var2)){
				var0.append("?");
				if ((((var1.nextInt(100)) == (0)) ? (1) : (0)) != (0)){
					var0.append("\n");
				}
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		var0.append("§r");
		return Component.literal(var0.toString());
	}
	public BigRedNoseItem() {
		super(ModMaterials.BIG_RED_NOSE_MATERIAL,ArmorItem.Type.HELMET);
	}
	@OnlyIn(value=Dist.CLIENT)
	public void appendHoverText(ItemStack var1, Item.TooltipContext var2, List<Component> var3, TooltipFlag var4) {
		var3.add(DESCRIPTION);
	}
}