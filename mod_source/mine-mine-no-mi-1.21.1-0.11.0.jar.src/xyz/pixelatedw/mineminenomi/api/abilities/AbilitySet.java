package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.HashSet;
import java.util.function.Supplier;

public class AbilitySet extends HashSet<Supplier<? extends AbilityCore<?>>> {
	// Fields
	private static final long serialVersionUID = -8128261614648267402L;

	@SafeVarargs()
	public AbilitySet(Supplier<? extends AbilityCore<?>>... var1) {
		Supplier<? extends AbilityCore<?>>[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				super.add(var2[var4]);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}