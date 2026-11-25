package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.Optional;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Mod3DArmorItem extends ModArmorItem {
	public Mod3DArmorItem(Holder<ArmorMaterial> var1, ArmorItem.Type var2) {
		this(var1,var2,new Item.Properties());
	}
	public Mod3DArmorItem(Holder<ArmorMaterial> var1, ArmorItem.Type var2, Item.Properties var3) {
		super(var1,var2,var3);
		this.setBaggyTrousers();
	}
	@Nonnull()
	public ResourceLocation getArmorTexture(ItemStack var1, Entity var2, EquipmentSlot var3, ArmorMaterial.Layer var4, boolean var5) {
		ResourceLocation var6 = ResourceLocation.fromNamespaceAndPath(var4.assetName.getNamespace(),String.format("textures/models/armor/%s%s.png",new Object[]{var4.assetName.getPath(),var4.suffix}));
		if (Minecraft.getInstance().getResourceManager().getResource(var6).isEmpty()){
			return NO_ARMOR;
		}else{
			return var6;
		}
	}
}