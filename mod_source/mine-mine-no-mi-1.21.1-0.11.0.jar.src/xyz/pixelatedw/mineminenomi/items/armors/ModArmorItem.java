package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.Optional;
import java.util.function.Consumer;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class ModArmorItem extends ArmorItem {
	// Fields
	public static final ResourceLocation NO_ARMOR = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/armor/no_armor.png");
	protected boolean isFit;

	public ModArmorItem(Holder<ArmorMaterial> var1, ArmorItem.Type var2) {
		this(var1,var2,new Item.Properties());
	}
	public ModArmorItem(Holder<ArmorMaterial> var1, ArmorItem.Type var2, Item.Properties var3) {
		super(var1,var2,var3);
		this.isFit = true;
	}
	@Nonnull()
	public ResourceLocation getArmorTexture(ItemStack var1, Entity var2, EquipmentSlot var3, ArmorMaterial.Layer var4, boolean var5) {
		Object[] var6 = new Object[1];
		var6[0] = ((var3) == (EquipmentSlot.LEGS)) ? ("bot") : ("top");
		String var7 = String.format("_%s",var6);
		ResourceLocation var8 = ResourceLocation.fromNamespaceAndPath(var4.assetName.getNamespace(),String.format("textures/models/armor/%s%s%s.png",new Object[]{var4.assetName.getPath(),var7,var4.suffix}));
		if (Minecraft.getInstance().getResourceManager().getResource(var8).isEmpty()){
			return NO_ARMOR;
		}else{
			return var8;
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> var1) {
		var1.accept((IClientItemExtensions)(new ModArmorItem$1(this)));
	}
	public <T extends ModArmorItem> T setBaggyTrousers() {
		this.isFit = false;
		return (T) (this);
	}
}