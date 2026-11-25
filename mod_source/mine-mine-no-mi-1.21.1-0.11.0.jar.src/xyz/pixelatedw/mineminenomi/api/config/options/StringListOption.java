package xyz.pixelatedw.mineminenomi.api.config.options;

import com.google.common.base.Strings;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class StringListOption extends ListOption<String> {
	// Fields
	private static final Predicate<Object> STRING_VALIDATOR;

	public StringListOption(List<String> var1, String var2, @Nullable() String var3) {
		super(var1,STRING_VALIDATOR,var2,var3);
	}
	protected ModConfigSpec.ConfigValue<List<? extends String>> buildValue(ModConfigSpec.Builder var1) {
		return (ModConfigSpec.ConfigValue<List<? extends String>>) (ModConfigSpec.ConfigValue) (((net.neoforged.neoforge.common.ModConfigSpec.Builder)(var1)).defineList((String)(this.getTitle()),(List)(this.getDefault()),(Supplier)(null),(Predicate)(this.getValidator())));
	}
	static  {
		Predicate<Object> var0 = (l0) -> {
			if (l0 instanceof String){
				String lv1_1 = ((String)(l0));
				if (Strings.isNullOrEmpty(lv1_1)){
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		};
		STRING_VALIDATOR = var0;
	}
}