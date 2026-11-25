package xyz.pixelatedw.mineminenomi.api;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public final record TradeEntry(ItemStack itemStack) {
	// Fields
	public static final StreamCodec<RegistryFriendlyByteBuf, TradeEntry> STREAM_CODEC;

	public int getCount() {
		return this.itemStack.getCount();
	}
	public TradeEntry setCount(int var1) {
		this.itemStack.setCount(var1);
		return this;
	}
	public boolean hasInfiniteStock() {
		return this.itemStack.has(ModDataComponents.INFINITE_STOCK);
	}
	public int getPrice() {
		return ((Integer)(this.itemStack.get(ModDataComponents.PRICE))).intValue();
	}
	static  {
		Function<TradeEntry, ItemStack> var0 = TradeEntry::itemStack;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ItemStack.STREAM_CODEC,var0,(Function)(((Function<ItemStack, TradeEntry>)(TradeEntry::new)))));
	}
}