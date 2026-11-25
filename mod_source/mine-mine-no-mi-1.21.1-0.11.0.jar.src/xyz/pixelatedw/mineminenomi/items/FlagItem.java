package xyz.pixelatedw.mineminenomi.items;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.items.components.FlagData;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFactions;

public class FlagItem extends BlockItem {
	public FlagItem(Block var1) {
		super(var1,new Item.Properties().stacksTo(1));
	}
	protected BlockState getPlacementState(BlockPlaceContext var1) {
		Player var2 = var1.getPlayer();
		ItemStack var3 = var1.getItemInHand();
		if (!(var2.level().isClientSide)){
			Faction var4 = ((Faction)(EntityStatsData.get((LivingEntity)(var2)).flatMap((Function)(((Function<EntityStatsData, Optional>)(EntityStatsData::getFaction)))).orElse(((Faction)(ModFactions.CIVILIAN.get())))));
			Crew var5 = FactionsWorldData.get().getCrewWithMember(var2.getUUID());
			Optional var6 = Optional.empty();
			if ((var5) != (null)){
				var6 = Optional.ofNullable(var5.getJollyRoger());
			}
			FlagData var7 = new FlagData(var2.getUUID(),var4,var6);
			var3.set(ModDataComponents.FLAG_DATA,(Object)(var7));
		}
		return super.getPlacementState(var1);
	}
	protected boolean updateCustomBlockEntityTag(BlockPos var1, Level var2, @Nullable() Player var3, ItemStack var4, BlockState var5) {
		if ((var3) != (null)){
			FlagBlockEntity var6 = ((FlagBlockEntity)(var2.getBlockEntity(var1)));
			if (((var6) != (null)) && (var4.has(ModDataComponents.FLAG_DATA))){
				FlagData var7 = ((FlagData)(var4.get(ModDataComponents.FLAG_DATA)));
				if (var7.jollyRoger().isPresent()){
					var6.setJollyRoger(((JollyRoger)(var7.jollyRoger().get())));
				}
				var6.setChanged();
				return true;
			}
		}
		return super.updateCustomBlockEntityTag(var1,var2,var3,var4,var5);
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
}