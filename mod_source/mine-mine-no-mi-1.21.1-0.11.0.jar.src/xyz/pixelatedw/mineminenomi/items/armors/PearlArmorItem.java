package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;

public class PearlArmorItem extends ArmorItem {
	public PearlArmorItem(ArmorItem.Type var1) {
		super(ModMaterials.PEARL_MATERIAL,var1,new Item.Properties());
	}
	@Nullable()
	public @Nullable() ResourceLocation getArmorTexture(ItemStack var1, Entity var2, EquipmentSlot var3, ArmorMaterial.Layer var4, boolean var5) {
		return ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/armor/pearl_armor.png");
	}
	@OnlyIn(value=Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> var1) {
		var1.accept((IClientItemExtensions)(new PearlArmorItem$1(this)));
	}
}