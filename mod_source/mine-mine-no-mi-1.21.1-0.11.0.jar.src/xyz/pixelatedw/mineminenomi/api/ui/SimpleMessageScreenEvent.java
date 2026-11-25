package xyz.pixelatedw.mineminenomi.api.ui;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SSimpleMessageScreenEventPacket;

public class SimpleMessageScreenEvent implements INBTSerializable<CompoundTag> {
	// Fields
	private int id;
	private Component message;
	private boolean isFlashing;
	private int timeVisible;

	public SimpleMessageScreenEvent() {
		this(0);
	}
	public SimpleMessageScreenEvent(int var1) {
		this.message = Component.empty();
		this.isFlashing = false;
		this.timeVisible = 100;
		this.id = var1;
	}
	public int getId() {
		return this.id;
	}
	public SimpleMessageScreenEvent setMessage(Component var1) {
		this.message = var1;
		return this;
	}
	public Component getMessage() {
		return this.message;
	}
	public SimpleMessageScreenEvent setFlash() {
		this.isFlashing = true;
		return this;
	}
	public boolean isFlashing() {
		return this.isFlashing;
	}
	public SimpleMessageScreenEvent setTimeVisible(int var1) {
		this.timeVisible = var1;
		return this;
	}
	public int getTimeVisible() {
		return this.timeVisible;
	}
	public void sendEvent(ServerPlayer var1) {
		ModNetwork.sendTo(new SSimpleMessageScreenEventPacket(this),(Player)(var1));
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		String var3 = Component.Serializer.toJson(this.message,var1);
		var2.putInt("id",this.id);
		var2.putString("message",var3);
		var2.putBoolean("isFlashing",this.isFlashing);
		var2.putInt("timeVisible",this.timeVisible);
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		String var3 = var2.getString("message");
		this.id = var2.getInt("id");
		this.message = Component.Serializer.fromJson(var3,var1);
		this.isFlashing = var2.getBoolean("isFlashing");
		this.timeVisible = var2.getInt("timeVisible");
	}
}