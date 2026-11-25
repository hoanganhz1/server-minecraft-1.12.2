package xyz.pixelatedw.mineminenomi.data.entity;

import com.google.common.base.Strings;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class DevilFruitData implements INBTSerializable<CompoundTag> {
	// Fields
	private int version;
	private LivingEntity owner;
	private Optional<ResourceLocation> devilFruit;
	private boolean hasAwakenedFruit;
	private Deque<ResourceLocation> morphs;
	private boolean isMorphDirty;
	private Optional<MorphInfo> lastMorph;
	private boolean hasYamiPower;
	private boolean hasYomiPower;
	private boolean hasStrongWaterWeakness;
	private boolean hasWeakWaterWeakness;
	private boolean hasKairosekiWeakness;
	private boolean isInsideLogiaBlock;

	public static DevilFruitData createCapability(IAttachmentHolder var0) {
		return new DevilFruitData(((LivingEntity)(var0)));
	}
	public static Optional<DevilFruitData> get(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.DEVIL_FRUIT))){
			var0.setData(ModAttachments.DEVIL_FRUIT,new DevilFruitData(var0));
		}
		return (Optional<DevilFruitData>) (Optional) (var0.getExistingData(ModAttachments.DEVIL_FRUIT));
	}
	public static DevilFruitData getOrCreate(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.DEVIL_FRUIT))){
			var0.setData(ModAttachments.DEVIL_FRUIT,new DevilFruitData(var0));
		}
		return ((DevilFruitData)(var0.getData(ModAttachments.DEVIL_FRUIT)));
	}
	private DevilFruitData(LivingEntity var1) {
		this.version = 2;
		this.devilFruit = (Optional) (((java.util.Optional)(Optional.empty())));
		this.morphs = new ArrayDeque();
		this.lastMorph = (Optional) (((java.util.Optional)(Optional.empty())));
		this.hasYamiPower = false;
		this.hasYomiPower = false;
		this.hasStrongWaterWeakness = false;
		this.hasWeakWaterWeakness = false;
		this.hasKairosekiWeakness = false;
		this.isInsideLogiaBlock = false;
		this.owner = var1;
	}
	public int getVersion() {
		return this.version;
	}
	public Optional<ResourceLocation> getDevilFruit() {
		return this.devilFruit;
	}
	public Item getDevilFruitItem() {
		if (!(this.hasAnyDevilFruit())){
			return Items.AIR;
		}else{
			return ((Item)(BuiltInRegistries.ITEM.get(((ResourceLocation)(this.getDevilFruit().get())))));
		}
	}
	public void setDevilFruit(@Nullable() ResourceLocation var1) {
		this.devilFruit = (Optional) (Optional.ofNullable(var1));
	}
	public void setDevilFruit(AkumaNoMiItem var1) {
		this.devilFruit = (Optional) (Optional.ofNullable(var1.getRegistryKey()));
	}
	public boolean hasAnyDevilFruit() {
		return this.devilFruit.isPresent();
	}
	public boolean hasDevilFruit(Supplier<AkumaNoMiItem> var1) {
		return this.hasDevilFruit(((AkumaNoMiItem)(var1.get())));
	}
	public boolean hasDevilFruit(AkumaNoMiItem var1) {
		AkumaNoMiItem var2 = var1;
		final AkumaNoMiItem var2_f1 = var2;
		Function<ResourceLocation, Boolean> var3 = (l0) -> {
			return Boolean.valueOf(((l0) != (null)) && (l0.equals(var2_f1.getRegistryKey())));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.getDevilFruit())).map((Function)(var3)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public void removeDevilFruit() {
		this.setDevilFruit(((ResourceLocation)(null)));
		this.morphs.clear();
		this.setYamiPower(false);
		this.setYomiPower(false);
	}
	public boolean isLogia() {
		return false;
	}
	public boolean hasYamiPower() {
		return this.hasYamiPower;
	}
	public void setYamiPower(boolean var1) {
		this.hasYamiPower = var1;
	}
	public boolean hasYomiPower() {
		return this.hasYomiPower;
	}
	public void setYomiPower(boolean var1) {
		this.hasYomiPower = var1;
	}
	public Optional<MorphInfo> getCurrentMorph() {
		return (Optional<MorphInfo>) (Optional) (this.getCurrentMorph(false));
	}
	public Optional<MorphInfo> getCurrentMorph(boolean var1) {
		if ((!(this.isMorphDirty)) && (this.lastMorph.isPresent())){
			return this.lastMorph;
		}else{
			ResourceLocation var2 = ((ResourceLocation)(this.morphs.peekLast()));
			if ((var2) != (null)){
				MorphInfo var3 = ((MorphInfo)(WyRegistry.MORPHS.get(var2)));
				if (((var3) != (null)) && (!((!(var1)) && (var3.isPartial())))){
					this.lastMorph = (Optional) (Optional.ofNullable(var3));
					this.isMorphDirty = false;
					return this.lastMorph;
				}
			}
			return (Optional<MorphInfo>) (Optional) (Optional.empty());
		}
	}
	public Set<MorphInfo> getActiveMorphs() {
		Function<ResourceLocation, MorphInfo> var1 = (l0) -> {
			return ((MorphInfo)(WyRegistry.MORPHS.get(l0)));
		};
		return ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.morphs.stream())).map((Function)(var1)).filter((Predicate)(((Predicate<MorphInfo>)(Objects::nonNull)))))).collect((Collector)(Collectors.toSet()))));
	}
	public boolean hasMorphInQueue(MorphInfo var1) {
		if ((!(this.isMorphDirty)) && ((this.lastMorph.isPresent()) && (((java.lang.Object)(((MorphInfo)(this.lastMorph.get())))).equals((Object)(var1))))){
			return true;
		}else{
			return this.morphs.contains(var1.getKey());
		}
	}
	public void addMorph(MorphInfo var1) {
		if (this.morphs.contains(var1.getKey())){
			return;
		}else{
			this.morphs.addLast(var1.getKey());
			this.isMorphDirty = true;
			if ((this.owner) != (null)){
				var1.updateMorphSize(this.owner);
			}
			return;
		}
	}
	public void removeMorph(MorphInfo var1) {
		if ((this.morphs.size()) <= (0)){
			return;
		}else{
			this.morphs.removeLastOccurrence(var1.getKey());
			this.isMorphDirty = true;
			return;
		}
	}
	public void removeMorph() {
		if ((this.morphs.size()) <= (0)){
			return;
		}else{
			this.morphs.removeLast();
			this.isMorphDirty = true;
			return;
		}
	}
	public void clearMorphs() {
		this.morphs.clear();
		this.isMorphDirty = true;
	}
	public boolean hasAwakenedFruit() {
		if (!(ServerConfig.hasAwakeningsEnabled())){
			return false;
		}else{
			if ((this.owner) == (null)){
				return false;
			}else{
				return this.hasAwakenedFruit;
			}
		}
	}
	public void setAwakenedFruit(boolean var1) {
		this.hasAwakenedFruit = var1;
	}
	public boolean hasStrongWaterWeakness() {
		return this.hasStrongWaterWeakness;
	}
	public void setStrongWaterWeakness(boolean var1) {
		this.hasStrongWaterWeakness = var1;
	}
	public boolean hasWeakWaterWeakness() {
		return this.hasWeakWaterWeakness;
	}
	public void setWeakWaterWeakness(boolean var1) {
		this.hasWeakWaterWeakness = var1;
	}
	public boolean hasKairosekiWeakness() {
		return this.hasKairosekiWeakness;
	}
	public void setKairosekiWeakness(boolean var1) {
		this.hasKairosekiWeakness = var1;
	}
	public boolean isInsideLogiaBlock() {
		return this.isInsideLogiaBlock;
	}
	public void setInsideLogiaBlock(boolean var1) {
		this.isInsideLogiaBlock = var1;
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.putInt("version",this.getVersion());
		var2.putString("devilFruit",((String)(this.getDevilFruit().map((Function)(((Function<ResourceLocation, String>)(ResourceLocation::toString)))).orElse(""))));
		var2.putBoolean("hasAwakenedFruit",this.hasAwakenedFruit());
		var2.putBoolean("hasYamiPower",this.hasYamiPower());
		var2.putBoolean("hasYomiPower",this.hasYomiPower());
		int var3 = this.morphs.size();
		var2.putInt("morphsSize",var3);
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				ResourceLocation var5 = ((ResourceLocation)(this.morphs.peek()));
				int var6 = var4;
				String var7 = "morph" + var6;
				var2.putString(var7,var5.toString());
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		String var3 = var2.getString("devilFruit");
		ResourceLocation var4 = null;
		if ((var2.contains("devilFruit")) && (!(Strings.isNullOrEmpty(var3)))){
			var4 = ResourceLocation.parse(var3);
		}
		this.setDevilFruit(var4);
		if (ServerConfig.hasAwakeningsEnabled()){
			this.setAwakenedFruit(var2.getBoolean("hasAwakenedFruit"));
		}else{
			this.setAwakenedFruit(false);
		}
		this.setYamiPower(var2.getBoolean("hasYamiPower"));
		this.setYomiPower(var2.getBoolean("hasYomiPower"));
		int var5 = var2.getInt("morphsSize");
		do{
			if ((var5) > (0)){
				int var6 = (var5) - (1);
				String var7 = "morph" + var6;
				ResourceLocation var8 = ResourceLocation.parse(var2.getString(var7));
				this.morphs.add(var8);
				var5--;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}