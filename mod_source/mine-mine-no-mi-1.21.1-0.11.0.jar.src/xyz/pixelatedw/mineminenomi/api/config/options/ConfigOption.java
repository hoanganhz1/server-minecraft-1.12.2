package xyz.pixelatedw.mineminenomi.api.config.options;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public abstract class ConfigOption<T extends Object> {
	// Fields
	private static final Component WORLD_RESTART_REQUIRED = Component.literal("Requires a world restart in order to take effect!");
	private ModConfigSpec.ConfigValue<T> value;
	protected T defaultValue;
	private String translationKey;
	private String optionKey;
	private String defaultTitle;
	private @Nullable() String defaultDescription;
	private Component title;
	private Component description;
	private boolean requiresWorldRestart;

	public ConfigOption(T var1, String var2, @Nullable() String var3) {
		this.description = Component.empty();
		this.defaultValue = var1;
		this.defaultTitle = var2;
		this.optionKey = WyHelper.getResourceName(var2);
		String var4 = this.optionKey;
		String var5 = "option." + var4;
		this.translationKey = ModRegistry.registerName(ModRegistry$I18nCategory.CONFIG,var5,this.defaultTitle);
		this.title = Component.translatable(this.translationKey);
		if ((var3) != (null)){
			this.defaultDescription = var3;
			String var6 = this.optionKey;
			String var7 = "option." + var6 + ".tooltip";
			this.description = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.CONFIG,var7,var3));
		}
	}
	public <C extends ConfigOption<T>> C worldRestart() {
		this.requiresWorldRestart = true;
		return (C) (this);
	}
	public void resetValue() {
		this.value.set(this.getDefault());
	}
	protected abstract ModConfigSpec.ConfigValue<T> buildValue(ModConfigSpec.Builder var0);
	public ModConfigSpec.ConfigValue<T> createValue(ModConfigSpec.Builder var1) {
		var1.translation(this.translationKey);
		if ((this.getDescription()) != (null)){
			var1.comment(this.getDescription());
		}
		if (this.requiresWorldRestart){
			var1.worldRestart();
			var1.comment("Requires a world restart in order to take effect!");
		}
		this.value = this.buildValue(var1);
		return (ModConfigSpec.ConfigValue<T>) (ModConfigSpec.ConfigValue) (this.value);
	}
	public ModConfigSpec.ConfigValue<T> getValue() {
		return (ModConfigSpec.ConfigValue<T>) (ModConfigSpec.ConfigValue) (this.value);
	}
	public T get() {
		try{
			return (T) (this.value.get());
		}catch(Exception var1){
			return (T) (this.defaultValue);
		}
	}
	public T getDefault() {
		return (T) (this.defaultValue);
	}
	public String getOptionKey() {
		return this.optionKey;
	}
	public String getTitle() {
		return this.defaultTitle;
	}
	@Nullable()
	public @Nullable() String getDescription() {
		return this.defaultDescription;
	}
	public Component getTitleComponent() {
		return this.title;
	}
	@Nullable()
	public @Nullable() Component[] getDescriptionComponent() {
		if (this.requiresWorldRestart){
			Component[] var1 = new Component[]{this.description,WORLD_RESTART_REQUIRED};
			return var1;
		}else{
			Component[] var1_1 = new Component[]{this.description};
			return var1_1;
		}
	}
}