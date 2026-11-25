package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;

public class SniperGogglesItem extends Mod3DArmorItem {
	public SniperGogglesItem() {
		super(ModMaterials.SNIPER_GOGGLES_MATERIAL,ArmorItem.Type.HELMET);
	}
	@Deprecated()		
@OnlyIn(value=Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> var1) {
		var1.accept((IClientItemExtensions)(new SniperGogglesItem$1(this)));
	}
}