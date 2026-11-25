package xyz.pixelatedw.mineminenomi.items;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.items.components.WantedPosterData;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.WantedPosterBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenWantedPosterScreenPacket;

public class WantedPosterItem extends BlockItem {
	public WantedPosterItem(Block var1) {
		super(var1,new Item.Properties().stacksTo(1));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if ((!(var2.level().isClientSide)) && (var4.has(ModDataComponents.WANTED_POSTER_DATA))){
			WantedPosterData var5 = ((WantedPosterData)(var4.get(ModDataComponents.WANTED_POSTER_DATA)));
			if (var5.isExpired(var1)){
				var5 = var5.setExpired();
			}
			ModNetwork.sendTo(new SOpenWantedPosterScreenPacket((WantedPosterData)(var5)),var2);
		}
		return InteractionResultHolder.success(var4);
	}
	protected boolean updateCustomBlockEntityTag(BlockPos var1, Level var2, @Nullable() Player var3, ItemStack var4, BlockState var5) {
		if ((var3) != (null)){
			WantedPosterBlockEntity var6 = ((WantedPosterBlockEntity)(var2.getBlockEntity(var1)));
			if (((var6) != (null)) && (var4.has(ModDataComponents.WANTED_POSTER_DATA))){
				WantedPosterData var7 = ((WantedPosterData)(var4.get(ModDataComponents.WANTED_POSTER_DATA)));
				if (var7.isExpired(var2)){
					var7 = var7.setExpired();
				}
				var6.setWantedPosterData(var7);
				var6.setChanged();
			}
		}
		return super.updateCustomBlockEntityTag(var1,var2,var3,var4,var5);
	}
	public void appendHoverText(ItemStack var1, Item.TooltipContext var2, List<Component> var3, TooltipFlag var4) {
		if (var1.has(ModDataComponents.WANTED_POSTER_DATA)){
			WantedPosterData var5 = ((WantedPosterData)(var1.get(ModDataComponents.WANTED_POSTER_DATA)));
			if (var5.trackedPosition().isPresent()){
				Vec3 var6 = ((Vec3)(var5.trackedPosition().get()));
				((java.util.List)(var3)).add((Object)(Component.empty()));
				int var7 = (int)(var6.x);
				int var8 = (int)(var6.y);
				int var9 = (int)(var6.z);
				String var10 = "x: " + var7 + " y: " + var8 + " z: " + var9;
				((java.util.List)(var3)).add((Object)(Component.literal(var10)));
			}else{
				return;
			}
		}
	}
	public static void setCanvasSize(ItemStack var0, CanvasSize var1) {
		var0.set(ModDataComponents.CANVAS_SIZE,(Object)(var1));
		String var2 = WyHelper.capitalize(var1.getSerializedName());
		String var3 = var0.getItem().getName(var0).getString();
		String var4 = var2 + " " + var3;
		var0.set(DataComponents.CUSTOM_NAME,(Object)(Component.literal(var4)));
	}
	public static boolean upgradeCanvasSize(ItemStack var0) {
		CanvasSize var1 = getCanvasSize(var0);
		if (var1.isMaximumSize()){
			return false;
		}else{
			setCanvasSize(var0,CanvasSize.values()[(var1.ordinal()) + (1)]);
			return true;
		}
	}
	public static CanvasSize getCanvasSize(ItemStack var0) {
		return ((CanvasSize)(var0.getOrDefault(ModDataComponents.CANVAS_SIZE,CanvasSize.SMALL)));
	}
	public static ItemStack getWantedPosterStack(Player var0, long var1) {
		WantedPosterData var2 = WantedPosterData.create((LivingEntity)(var0),var1);
		ItemStack var3 = ((Block)(ModBlocks.WANTED_POSTER.get())).asItem().getDefaultInstance();
		var3.set(ModDataComponents.WANTED_POSTER_DATA,(Object)(var2));
		return var3;
	}
}