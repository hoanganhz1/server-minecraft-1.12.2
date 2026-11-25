package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class AbilityPool {
	// Fields
	private final Set<AbilityCore<? extends IAbility>> abilities;
	private final HashMap<String, AbilityPool$FlagValue<?>> flags;

	public AbilityPool() {
		this.abilities = new HashSet();
		this.flags = (HashMap) (new HashMap());
	}
	public AbilityPool addFlag(String var1, boolean var2) {
		this.flags.put(var1,(AbilityPool$FlagValue<?>)(new AbilityPool$BooleanFlagValue(Boolean.valueOf(var2))));
		return this;
	}
	public <T> T getFlagValue(String var1, Supplier<T> var2) {
		AbilityPool$FlagValue var3 = ((AbilityPool$FlagValue)(this.flags.get(var1)));
		if ((var3) != (null)){
			return (T) (var3.get());
		}else{
			return var2.get();
		}
	}
	public void addAbilityCore(AbilityCore<? extends IAbility> var1) {
		this.abilities.add(var1);
	}
	public boolean hasAbilityCore(AbilityCore<? extends IAbility> var1) {
		return this.abilities.contains(var1);
	}
	public Stream<AbilityCore<? extends IAbility>> getAllPoolCores() {
		return (Stream<AbilityCore<? extends IAbility>>) (Stream) (this.abilities.stream());
	}
}