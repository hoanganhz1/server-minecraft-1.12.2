package xyz.pixelatedw.mineminenomi.api.ui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IEventReceiverScreen<T extends INBTSerializable<CompoundTag>> {
	public abstract void handleEvent(T var0);
	public abstract INBTSerializable<CompoundTag> decode(HolderLookup.Provider var0, CompoundTag var1);
	public static <T extends INBTSerializable<CompoundTag>> void tryHandle(Screen var0, HolderLookup.Provider var1, CompoundTag var2) {
		if (var0 instanceof IEventReceiverScreen){
			IEventReceiverScreen var3 = ((IEventReceiverScreen)(var0));
			var3.handleEvent(var3.decode(var1,var2));
		}
	}
}