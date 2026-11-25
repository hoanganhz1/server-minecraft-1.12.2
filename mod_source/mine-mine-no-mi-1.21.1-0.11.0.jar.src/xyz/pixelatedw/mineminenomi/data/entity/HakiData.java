package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.events.stats.HakiExpEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.stats.HakiExpEvent$Pre;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;

public class HakiData implements INBTSerializable<CompoundTag> {
	// Fields
	private LivingEntity owner;
	private float kenbunshokuExp;
	private float busoshokuHardeningExp;
	private int hakiOveruse;
	private int haoshokuHakiColour;

	public static HakiData createCapability(IAttachmentHolder var0) {
		return new HakiData(((LivingEntity)(var0)));
	}
	public static Optional<HakiData> get(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.HAKI))){
			var0.setData(ModAttachments.HAKI,new HakiData(var0));
		}
		return (Optional<HakiData>) (Optional) (var0.getExistingData(ModAttachments.HAKI));
	}
	public static HakiData getOrCreate(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.HAKI))){
			var0.setData(ModAttachments.HAKI,new HakiData(var0));
		}
		return ((HakiData)(var0.getData(ModAttachments.HAKI)));
	}
	private HakiData(LivingEntity var1) {
		this.owner = var1;
	}
	public float getTotalHakiExp() {
		return (this.kenbunshokuExp) + (this.busoshokuHardeningExp);
	}
	public float getMaxHakiExp() {
		return (float)((ServerConfig.getHakiExpLimit()) * ((HakiType.values().length) - (1)));
	}
	public int getHakiOveruse() {
		return this.hakiOveruse;
	}
	public int getMaxOveruse() {
		return (int)((this.getTotalHakiExp()) * (140F));
	}
	public void alterHakiOveruse(int var1) {
		this.hakiOveruse = Mth.clamp((this.hakiOveruse) + (var1),0,this.getMaxOveruse());
	}
	public void setHakiOveruse(int var1) {
		this.hakiOveruse = Mth.clamp(var1,0,this.getMaxOveruse());
	}
	public float getKenbunshokuHakiExp() {
		return this.kenbunshokuExp;
	}
	public boolean alterKenbunshokuHakiExp(float var1, StatChangeSource var2) {
		LivingEntity var3 = this.owner;
		if (var3 instanceof Player){
			Player var4 = ((Player)(var3));
			HakiExpEvent$Pre var5 = ((HakiExpEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(new HakiExpEvent$Pre(var4,var1,HakiType.KENBUNSHOKU,var2)))));
			if (var5.isCanceled()){
				return false;
			}else{
				var1 = var5.getHakiExp();
			}
		}
		this.kenbunshokuExp = Mth.clamp((this.kenbunshokuExp) + (var1),0F,(float)(ServerConfig.getHakiExpLimit()));
		LivingEntity var4_1 = this.owner;
		if (var4_1 instanceof Player){
			Player var5_1 = ((Player)(var4_1));
			HakiExpEvent$Post var6 = new HakiExpEvent$Post(var5_1,var1,HakiType.KENBUNSHOKU,var2);
			NeoForge.EVENT_BUS.post((Event)(var6));
		}
		return true;
	}
	public void setKenbunshokuHakiExp(float var1) {
		this.kenbunshokuExp = Mth.clamp(var1,0F,(float)(ServerConfig.getHakiExpLimit()));
	}
	public float getBusoshokuHakiExp() {
		return this.busoshokuHardeningExp;
	}
	public boolean alterBusoshokuHakiExp(float var1, StatChangeSource var2) {
		LivingEntity var3 = this.owner;
		if (var3 instanceof Player){
			Player var4 = ((Player)(var3));
			HakiExpEvent$Pre var5 = ((HakiExpEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(new HakiExpEvent$Pre(var4,var1,HakiType.BUSOSHOKU,var2)))));
			if (var5.isCanceled()){
				return false;
			}else{
				var1 = var5.getHakiExp();
			}
		}
		this.busoshokuHardeningExp = Mth.clamp((this.busoshokuHardeningExp) + (var1),0F,(float)(ServerConfig.getHakiExpLimit()));
		LivingEntity var4_1 = this.owner;
		if (var4_1 instanceof Player){
			Player var5_1 = ((Player)(var4_1));
			HakiExpEvent$Post var6 = new HakiExpEvent$Post(var5_1,var1,HakiType.BUSOSHOKU,var2);
			NeoForge.EVENT_BUS.post((Event)(var6));
		}
		return true;
	}
	public void setBusoshokuHakiExp(float var1) {
		this.busoshokuHardeningExp = Mth.clamp(var1,0F,(float)(ServerConfig.getHakiExpLimit()));
	}
	public int getHaoshokuHakiColour() {
		return this.haoshokuHakiColour;
	}
	public void setHaoshokuHakiColour(int var1) {
		this.haoshokuHakiColour = var1;
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.putFloat("kenHakiExp",this.getKenbunshokuHakiExp());
		var2.putFloat("busoHakiExp",this.getBusoshokuHakiExp());
		var2.putInt("hakiOveruse",this.getHakiOveruse());
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		this.setKenbunshokuHakiExp(var2.getFloat("kenHakiExp"));
		this.setBusoshokuHakiExp(var2.getFloat("busoHakiExp"));
		this.setHakiOveruse(var2.getInt("hakiOveruse"));
	}
}