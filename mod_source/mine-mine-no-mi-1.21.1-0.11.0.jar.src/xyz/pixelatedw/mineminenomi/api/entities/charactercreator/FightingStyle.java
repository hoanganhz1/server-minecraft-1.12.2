package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class FightingStyle implements ICharacterCreatorEntry {
	// Fields
	private ResourceLocation key;
	private String labelId;
	private Component label;
	private CharacterCreatorSelectionInfo bookInfo;
	private boolean inBook;
	private TrainingPointType trainingAffinity;

	public FightingStyle() {
		this.inBook = false;
	}
	protected String getOrCreateLabelId() {
		if ((this.labelId) == (null)){
			ResourceLocation var1 = WyRegistry.FIGHTING_STYLES.getKey(this);
			if ((var1) != (null)){
				this.labelId = Util.makeDescriptionId(ModRegistry$I18nCategory.STYLE.getId(),var1);
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
	public FightingStyle setTrainingAffinity(TrainingPointType var1) {
		this.trainingAffinity = var1;
		return this;
	}
	@Nullable()
	public @Nullable() TrainingPointType getTrainingAffinity() {
		return this.trainingAffinity;
	}
	public FightingStyle setBookDetails(CharacterCreatorSelectionInfo var1) {
		this.bookInfo = var1;
		this.inBook = true;
		return this;
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
	public static @Nullable() FightingStyle get(ResourceLocation var0) {
		return ((FightingStyle)(WyRegistry.FIGHTING_STYLES.get(var0)));
	}
	public ResourceLocation getRegistryName() {
		if ((this.key) == (null)){
			this.key = WyRegistry.FIGHTING_STYLES.getKey(this);
		}
		return this.key;
	}
}