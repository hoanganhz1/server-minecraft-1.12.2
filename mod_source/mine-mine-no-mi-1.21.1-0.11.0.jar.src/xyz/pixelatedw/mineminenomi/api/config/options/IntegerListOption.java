package xyz.pixelatedw.mineminenomi.api.config.options;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public class IntegerListOption extends ListOption<Integer> {
	// Fields
	private static final Predicate<Object> VALIDATOR;

	public IntegerListOption(List<Integer> var1, String var2, @Nullable() String var3) {
		super(var1,VALIDATOR,var2,var3);
	}
	protected ModConfigSpec.ConfigValue<List<? extends Integer>> buildValue(ModConfigSpec.Builder var1) {
		if ((this.getDescription()) != (null)){
			var1.comment(this.getDescription());
		}
		return (ModConfigSpec.ConfigValue<List<? extends Integer>>) (ModConfigSpec.ConfigValue) (((net.neoforged.neoforge.common.ModConfigSpec.Builder)(var1)).defineList((String)(this.getTitle()),(List)(this.getDefault()),(Supplier)(null),(Predicate)(this.getValidator())));
	}
	static  {
		Predicate<Object> var0 = (l0) -> {
			if (l0 instanceof Integer){
				Integer lv1_1 = ((Integer)(l0));
				if ((lv1_1.intValue()) < (0)){
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		};
		VALIDATOR = var0;
	}
}