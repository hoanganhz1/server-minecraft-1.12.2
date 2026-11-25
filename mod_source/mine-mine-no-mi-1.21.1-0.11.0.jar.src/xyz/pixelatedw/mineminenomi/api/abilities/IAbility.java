package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.ComponentNotRegisteredException;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public interface IAbility extends Comparable<IAbility> {
	public abstract void use(LivingEntity var0);
	public abstract void tick(LivingEntity var0);
	public abstract Result canUse(LivingEntity var0);
	public abstract AbilityCore<? extends IAbility> getCore();
	public abstract Component getDisplayName();
	public abstract ResourceLocation getIcon(LivingEntity var0);
	public abstract void onEquip(LivingEntity var0);
	public abstract void onRemove(LivingEntity var0);
	public abstract <C extends AbilityComponent<?>> Optional<C> getComponent(AbilityComponentKey<C> var0);
	public default <C extends AbilityComponent<?>> C getComponentOrThrow(AbilityComponentKey<C> var1) {
try {
		AbilityComponentKey<C> var2 = var1;
		final AbilityComponentKey<C> var2_f1 = var2;
		Supplier<ComponentNotRegisteredException> var3 = () -> {
			return new ComponentNotRegisteredException(var2_f1);
		};
		return (C) (((AbilityComponent)(((Optional<?>)(((java.util.Optional)(((xyz.pixelatedw.mineminenomi.api.abilities.IAbility)(this)).getComponent((AbilityComponentKey)(var1)))))).orElseThrow((Supplier)(var3)))));

} catch (java.lang.Throwable jdec$escape$0) {
throw jdec$rethrow$0(jdec$escape$0);
}
	}
	public abstract Map<AbilityComponentKey<?>, AbilityComponent<?>> getComponents();
	public abstract boolean hasComponent(AbilityComponentKey<?> var0);
	public abstract boolean isOGCD();
	public default void save(HolderLookup.Provider var1, CompoundTag var2) {
		CompoundTag var3 = new CompoundTag();
		Iterator var4 = this.getComponents().entrySet().iterator();
		do{
			if (var4.hasNext()){
				Map.Entry var5 = ((Map.Entry)(var4.next()));
				CompoundTag var6 = ((AbilityComponent)(var5.getValue())).save(var1);
				if ((var6) != (null)){
					var3.put(((AbilityComponentKey)(var5.getKey())).getId().toString(),(Tag)(var6));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		var2.put("components",(Tag)(var3));
	}
	public default void load(HolderLookup.Provider var1, CompoundTag var2) {
		CompoundTag var3 = var2.getCompound("components");
		Iterator var4 = this.getComponents().entrySet().iterator();
		do{
			if (var4.hasNext()){
				Map.Entry var5 = ((Map.Entry)(var4.next()));
				CompoundTag var6 = var3.getCompound(((AbilityComponentKey)(var5.getKey())).getId().toString());
				if ((var6) != (null)){
					((AbilityComponent)(var5.getValue())).load(var1,var6);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public default boolean isAbilityDisabled() {
		return ((Boolean)(this.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).map((Function)(((Function<DisableComponent, Boolean>)(DisableComponent::isDisabled)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public default boolean isAbilityPaused() {
		return ((Boolean)(this.getComponent(((AbilityComponentKey)(ModAbilityComponents.PAUSE_TICK.get()))).map((Function)(((Function<PauseTickComponent, Boolean>)(PauseTickComponent::isPaused)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public default int compareTo(IAbility var1) {
		if ((var1) == (null)){
			return 1;
		}else{
			return this.getCore().compareTo((AbilityCore<?>)(AbilityCore)(var1.getCore()));
		}
	}
	static <E extends java.lang.Throwable> java.lang.RuntimeException jdec$rethrow$0(java.lang.Throwable failure) throws E {throw (E) failure;}
}