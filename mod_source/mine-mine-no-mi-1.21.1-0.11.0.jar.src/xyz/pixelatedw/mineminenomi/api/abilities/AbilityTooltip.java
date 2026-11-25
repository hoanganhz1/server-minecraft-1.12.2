package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class AbilityTooltip {
	// Fields
	private Player player;
	private IAbility ability;
	private Map<Integer, Collection<Component>> tooltips;
	private boolean orderEntries;
	private int currentPage;
	private int pages;
	private boolean isInitialized;

	public AbilityTooltip(Player var1, IAbility var2) {
		this.tooltips = new LinkedHashMap();
		this.orderEntries = true;
		this.isInitialized = false;
		this.player = var1;
		this.ability = var2;
	}
	public Player getPlayer() {
		return this.player;
	}
	public IAbility getAbility() {
		return this.ability;
	}
	public void init(Runnable var1) {
		if (!(this.isInitialized)){
			var1.run();
			this.isInitialized = true;
		}
	}
	public void ignoreOrder() {
		this.orderEntries = false;
	}
	public void setPages(int var1, int var2) {
		this.pages = var1;
		this.currentPage = var2;
	}
	public int getCurrentPage() {
		return this.currentPage;
	}
	public int getMaxPages() {
		return this.pages;
	}
	public boolean hasPages() {
		return (this.pages) > (0);
	}
	public void cyclePage() {
		this.currentPage = ((this.currentPage) + (1)) % (this.pages);
	}
	public void clearTooltips() {
		this.tooltips.clear();
	}
	public boolean isEmpty() {
		return this.tooltips.isEmpty();
	}
	public void add(Component var1) {
		this.add((AbilityComponentKey)(null),var1);
	}
	public void add(Collection<Component> var1) {
		var1.forEach((Consumer)(((Consumer<Component>)(this::add))));
	}
	public void add(@Nullable() AbilityComponentKey<?> var1, Collection<Component> var2) {
		AbilityTooltip var3 = this;
		AbilityComponentKey<?> var4 = var1;
		final AbilityComponentKey<?> var4_f1 = var4;
		Consumer<? super Component> var5 = (l0) -> {
			this.add(var4_f1,l0);
		};
		((java.lang.Iterable)(var2)).forEach((Consumer)(var5));
	}
	public void add(@Nullable() AbilityComponentKey<?> var1, Component var2) {
		int var3 = 100;
		if (((var1) != (null)) && (this.orderEntries)){
			var3 = ((Integer)(ModAbilityComponents.TOOLTIP_ORDER.getOrDefault(var1.getId(),Integer.valueOf(100)))).intValue();
		}
		this.add(var3,var2);
	}
	public void add(int var1, Component var2) {
		Function<? super Integer, ? extends Collection<Component>> var3 = (Function) ((Function<Integer, Collection>) ((l0) -> {
			return new LinkedList();
		}));
		((java.util.Map)(this.tooltips)).computeIfAbsent((Object)(Integer.valueOf(var1)),(Function)(var3));
		Component var4 = var2;
		final Component var4_f1 = var4;
		BiFunction<? super Integer, ? super Collection<Component>, ? extends Collection<Component>> var5 = (BiFunction) ((BiFunction<Integer, Collection, Collection>) ((l0, l1) -> {
			l1.add(var4_f1);
			return l1;
		}));
		((java.util.Map)(this.tooltips)).computeIfPresent((Object)(Integer.valueOf(var1)),(BiFunction)(var5));
	}
	public void set(AbilityComponentKey<?> var1, Component var2) {
		Objects.requireNonNull(var1,"Component Key should not be null here as it might wrongly replace unwanted tooltip lines!");
		if ((((this.orderEntries) && (ModAbilityComponents.TOOLTIP_ORDER.containsKey(var1.getId()))) ? (1) : (0)) != (0)){
			this.set(((Integer)(ModAbilityComponents.TOOLTIP_ORDER.getOrDefault(var1.getId(),Integer.valueOf((100) + ((((java.lang.Object)(var1)).hashCode()) % (500)))))).intValue(),var2);
		}else{
			String var3 = String.valueOf((Object)(var1.getId()));
			String var4 = "Trying to fully replace tooltips with for component " + var3 + " which does not have an order set. Using fallback to avoid data loss";
			WyDebug.warn(var4);
			this.add(var1,var2);
		}
	}
	public void set(AbilityComponentKey<?> var1, Collection<Component> var2) {
		Objects.requireNonNull(var1,"Component Key should not be null here as it might wrongly replace unwanted tooltip lines!");
		if ((((this.orderEntries) && (ModAbilityComponents.TOOLTIP_ORDER.containsKey(var1.getId()))) ? (1) : (0)) != (0)){
			this.set(((Integer)(((java.util.Map)(ModAbilityComponents.TOOLTIP_ORDER)).getOrDefault((Object)(var1),(Object)(Integer.valueOf((100) + ((((java.lang.Object)(var1)).hashCode()) % (500))))))).intValue(),var2);
		}else{
			String var3 = String.valueOf((Object)(var1.getId()));
			String var4 = "Trying to fully replace tooltips with for component " + var3 + " which does not have an order set. Using fallback to avoid data loss";
			WyDebug.warn(var4);
			AbilityTooltip var5 = this;
			AbilityComponentKey<?> var6 = var1;
			final AbilityComponentKey<?> var6_f1 = var6;
			Consumer<? super Component> var7 = (l0) -> {
			this.add(var6_f1,l0);
		};
			((java.lang.Iterable)(var2)).forEach((Consumer)(var7));
		}
	}
	public void set(int var1, Component var2) {
		this.tooltips.remove(Integer.valueOf(var1));
		LinkedList var3 = new LinkedList();
		var3.add(var2);
		this.tooltips.put(Integer.valueOf(var1),(Collection<Component>)(var3));
	}
	public void set(int var1, Collection<Component> var2) {
		this.tooltips.remove(Integer.valueOf(var1));
		this.tooltips.put(Integer.valueOf(var1),var2);
	}
	public void remove(int var1) {
		this.tooltips.remove(Integer.valueOf(var1));
	}
	public int count() {
		return this.tooltips.size();
	}
	public Collection<Component> generateTooltips() {
		LinkedList var1 = new LinkedList();
		if (!(this.orderEntries)){
			List var2 = var1;
			final List var2_f1 = var2;
			Consumer var3 = (Consumer) ((Consumer<Collection>) ((l0) -> {
			var2_f1.addAll(l0);
		}));
			this.tooltips.values().stream().forEach(var3);
			return var1;
		}else{
			LinkedHashSet var2_1 = new LinkedHashSet();
			LinkedHashMap var3_1 = new LinkedHashMap(this.tooltips);
			Predicate var4 = (Predicate) ((Predicate<Map.Entry>) ((l0) -> {
			return (l0.getKey()) == (null);
		}));
			Set var5 = var2_1;
			Map var6 = var3_1;
			final Set var5_f1 = var5;
			final Map var6_f2 = var6;
			Consumer var7 = (Consumer) ((Consumer<Map.Entry>) ((l0) -> {
			var5_f1.addAll(((Collection)(l0.getValue())));
			var6_f2.remove(l0.getKey());
		}));
			var3_1.entrySet().stream().filter(var4).forEach(var7);
			Comparator var8 = (Comparator) ((Comparator<Map.Entry>) ((l0, l1) -> {
			return ((Integer)(l1.getKey())).compareTo(((Integer)(l0.getKey())));
		}));
			List var9 = var1;
			final List var9_f3 = var9;
			Consumer var10 = (Consumer) ((Consumer<Map.Entry>) ((l0) -> {
			var9_f3.addAll(((Collection)(l0.getValue())));
		}));
			var3_1.entrySet().stream().sorted(var8).forEach(var10);
			var1.addAll((Collection)(var2_1));
			return var1;
		}
	}
}