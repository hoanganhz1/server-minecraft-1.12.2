package xyz.pixelatedw.mineminenomi.api.config.options;

import net.neoforged.neoforge.common.ModConfigSpec;

public class EnumOption<T extends Enum<T>> extends ConfigOption<T> {
	// Fields
	private Class<T> enumClass;
	private T[] enumValues;

	public EnumOption(T var1, T[] var2, String var3, String var4) {
		super(var1,var3,var4);
		this.enumClass = (Class) (var1.getClass());
		this.enumValues = var2;
	}
	public Class<T> getEnumClass() {
		return (Class<T>) (Class) (this.enumClass);
	}
	protected ModConfigSpec.ConfigValue<T> buildValue(ModConfigSpec.Builder var1) {
		if ((this.getDescription()) != (null)){
			var1.comment(this.getDescription());
		}
		return var1.defineEnum(this.getTitle(),((Enum)(this.getDefault())),this.enumValues);
	}
}