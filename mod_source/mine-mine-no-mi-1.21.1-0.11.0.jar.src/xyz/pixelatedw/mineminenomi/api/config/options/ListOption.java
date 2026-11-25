package xyz.pixelatedw.mineminenomi.api.config.options;

import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public abstract class ListOption<T extends Object> extends ConfigOption<List<? extends T>> {
	// Fields
	private Predicate<Object> validator;

	public ListOption(List<T> var1, Predicate<Object> var2, String var3, @Nullable() String var4) {
		super(var1,var3,var4);
		this.validator = var2;
	}
	public List<T> getDefault() {
		return (List<T>) (List) (((List)(this.defaultValue)));
	}
	public Predicate<Object> getValidator() {
		return (Predicate<Object>) (Predicate) (this.validator);
	}
}