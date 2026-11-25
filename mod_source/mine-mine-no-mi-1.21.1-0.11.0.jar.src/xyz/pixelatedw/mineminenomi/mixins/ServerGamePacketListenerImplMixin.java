package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

@Mixin(value={ServerGamePacketListenerImpl.class})
public abstract class ServerGamePacketListenerImplMixin {
	// Fields
	@Unique() private boolean mineminenomi$duplicateCall;

	public ServerGamePacketListenerImplMixin() {
		this.mineminenomi$duplicateCall = false;
	}
	@Shadow()
	public abstract ServerPlayer getPlayer();
	@Inject(method={"handleSetCreativeModeSlot"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/inventory/InventoryMenu;getSlot(I)Lnet/minecraft/world/inventory/Slot;", shift=At.Shift.BEFORE)}, locals=LocalCapture.CAPTURE_FAILHARD)
	public void mineminenomi$onFruitFromCreativeSlotChange(ServerboundSetCreativeModeSlotPacket var1, CallbackInfo var2, boolean var3, ItemStack var4) {
		ServerGamePacketListenerImpl var5 = ((ServerGamePacketListenerImpl)(this));
		Item var6 = var4.getItem();
		if (var6 instanceof AkumaNoMiItem){
			AkumaNoMiItem var7 = ((AkumaNoMiItem)(var6));
			if (!(this.mineminenomi$duplicateCall)){
				OneFruitWorldData.get().inventoryOneFruit(var7,(LivingEntity)(var5.getPlayer()),"Pulled from Creative Inventory");
				this.mineminenomi$duplicateCall = true;
			}
		}else{
			this.mineminenomi$duplicateCall = false;
		}
		if (var4.isEmpty()){
			Item var7_1 = this.getPlayer().inventoryMenu.getSlot((int)(var1.slotNum())).getItem().getItem();
			if (var7_1 instanceof AkumaNoMiItem){
				AkumaNoMiItem var8 = ((AkumaNoMiItem)(var7_1));
				OneFruitWorldData.get().lostOneFruit(var8.getRegistryKey(),(LivingEntity)(var5.getPlayer()),"Removed from Creative Inventory");
			}else{
				return;
			}
		}
	}
}