package xyz.pixelatedw.mineminenomi.api.events.ability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.neoforged.bus.api.Event;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModifyDevilFruitEvent extends Event {
	// Fields
	private final AkumaNoMiItem fruit;
	private final ImmutableList<Supplier<? extends AbilityCore<?>>> originalAbilities;
	private List<Supplier<? extends AbilityCore<?>>> abilities;

	public ModifyDevilFruitEvent(AkumaNoMiItem var1, Supplier<? extends AbilityCore<?>>... var2) {
		this.fruit = var1;
		this.originalAbilities = (ImmutableList) (ImmutableList.copyOf(var2));
		this.abilities = Lists.newArrayList(var2);
	}
	public void addAbilityCore(Supplier<? extends AbilityCore<?>> var1) {
		if (!(this.abilities.contains(var1))){
			this.abilities.add(var1);
		}
	}
	public void removeAbilityCore(Supplier<? extends AbilityCore<?>> var1) {
		this.abilities.remove(var1);
	}
	public void removeAbilityCore(Class<? extends Ability> var1) {
		try{
			this.abilities.removeIf((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<Supplier>) ((l0) -> {
			return ((java.lang.Object)(((AbilityCore)(l0.get())).createAbility().getClass())).equals((Object)(var1));
		}))))));
		}catch(Exception var2){

		}
	}
	public Supplier<? extends AbilityCore<?>>[] getAbilities() {
		return ((Supplier[])(this.abilities.toArray(new Supplier[0])));
	}
	public final ImmutableList<Supplier<? extends AbilityCore<?>>> getOriginalAbilities() {
		return this.originalAbilities;
	}
	public final AkumaNoMiItem getFruitItem() {
		return this.fruit;
	}
}