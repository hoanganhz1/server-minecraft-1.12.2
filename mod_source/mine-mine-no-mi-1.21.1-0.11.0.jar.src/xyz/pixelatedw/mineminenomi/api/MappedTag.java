package xyz.pixelatedw.mineminenomi.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class MappedTag<K extends Object> extends SimpleJsonResourceReloadListener {
	// Fields
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private HashMap<K, Float> values;
	private final ResourceLocation id;
	private final ResourceKey<Registry<K>> registry;

	public MappedTag(ResourceLocation var1, String var2, ResourceKey<Registry<K>> var3) {
		super(GSON,var2);
		this.values = new HashMap();
		this.id = var1;
		this.registry = var3;
	}
	protected void apply(Map<ResourceLocation, JsonElement> var1, ResourceManager var2, ProfilerFiller var3) {
		Map.Entry var11 = null;
		JsonObject var4 = ((JsonElement)(var1.get(this.id))).getAsJsonObject();
		HolderLookup.RegistryLookup var5 = this.getRegistryLookup().lookupOrThrow(this.registry);
		Iterator var6 = var4.entrySet().iterator();
		do{
			if (var6.hasNext()){
				Map.Entry var7 = ((Map.Entry)(var6.next()));
				ResourceLocation var8 = ResourceLocation.parse(((String)(var7.getKey())));
				Optional var9 = var5.get(ResourceKey.create(this.registry,var8));
				MappedTag var10 = this;
				var11 = var7;
				final Map.Entry var11_f1 = var11;
				Consumer<Holder.Reference> var12 = (l0) -> {
			this.values.put((K)(l0.value()),Float.valueOf(((JsonElement)(var11_f1.getValue())).getAsFloat()));
		};
				((java.util.Optional)(var9)).ifPresent((Consumer)(var12));
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public float getValue(K var1) {
		return ((Float)(((java.util.HashMap)(this.values)).getOrDefault((Object)(var1),(Object)(Float.valueOf(0F))))).floatValue();
	}
}