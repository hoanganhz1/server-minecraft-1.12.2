package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class Race implements ICharacterCreatorEntry {
	// Fields
	private ResourceLocation key;
	private String labelId;
	private Component label;
	private CharacterCreatorSelectionInfo bookInfo;
	private boolean inBook;
	private boolean hasRenderFeatures;
	private boolean isSubRace;
	private List<Supplier<Race>> subRaces;

	public Race() {
		this.inBook = false;
		this.hasRenderFeatures = false;
		this.subRaces = new ArrayList();
	}
	public static Race subRace() {
		Race var0 = new Race();
		var0.isSubRace = true;
		return var0;
	}
	public Race setRenderFeatures() {
		this.hasRenderFeatures = true;
		return this;
	}
	public Race setBookDetails(CharacterCreatorSelectionInfo var1) {
		this.bookInfo = var1;
		this.inBook = true;
		return this;
	}
	public boolean hasRenderFeatures() {
		return this.hasRenderFeatures;
	}
	public boolean isMainRace() {
		return !(this.isSubRace);
	}
	public boolean isSubRace() {
		return this.isSubRace;
	}
	public boolean hasSubRaces() {
		return (this.subRaces.size()) > (0);
	}
	public List<Supplier<Race>> getSubRaces() {
		return this.subRaces;
	}
	public Race setSubRaces(List<Supplier<Race>> var1) {
		this.subRaces = var1;
		return this;
	}
	protected String getOrCreateLabelId() {
		if ((this.labelId) == (null)){
			ResourceLocation var1 = WyRegistry.RACES.getKey(this);
			if ((var1) != (null)){
				this.labelId = Util.makeDescriptionId(ModRegistry$I18nCategory.RACE.getId(),var1);
			}
		}
		return this.labelId;
	}
	public String getTitleId() {
		return this.labelId;
	}
	public Component getLabel() {
		if ((this.label) == (null)){
			this.label = Component.translatable(this.getOrCreateLabelId());
		}
		return this.label;
	}
	public CharacterCreatorSelectionInfo getSelectionInfo() {
		return this.bookInfo;
	}
	public boolean isInBook() {
		return this.inBook;
	}
	public int getBookOrder() {
		if ((this.bookInfo) == (null)){
			return -1;
		}else{
			return this.bookInfo.getOrder();
		}
	}
	@Nullable()
	public static @Nullable() Race get(ResourceLocation var0) {
		return ((Race)(WyRegistry.RACES.get(var0)));
	}
	public ResourceLocation getRegistryName() {
		if ((this.key) == (null)){
			this.key = WyRegistry.RACES.getKey(this);
		}
		return this.key;
	}
}