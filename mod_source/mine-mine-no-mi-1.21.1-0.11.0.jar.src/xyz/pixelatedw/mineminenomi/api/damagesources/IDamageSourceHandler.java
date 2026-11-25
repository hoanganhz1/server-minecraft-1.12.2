package xyz.pixelatedw.mineminenomi.api.damagesources;

import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public interface IDamageSourceHandler {
	public static IDamageSourceHandler getHandler(DamageSource var0) {
		return ((IDamageSourceHandler)(var0));
	}
	public abstract IDamageSourceHandler clone();
	public abstract boolean isInitialized();
	public abstract Map<AbilityCore<?>, CompoundTag> getTags();
	@Nullable()
	public abstract @Nullable() CompoundTag getAbilityTags(AbilityCore<?> var0);
	public abstract void addAbilityPiercing(float var0, AbilityCore<?> var1);
	public abstract float getAbilityPiercing(@Nullable() AbilityCore<?> var0);
	public abstract void addGlobalPiercing(float var0);
	public abstract float getGlobalPiercing();
	public abstract void bypassLogia();
	public abstract boolean isBypassingLogia();
	public abstract void bypassFriendly();
	public abstract boolean isBypassingFriendly();
	public abstract void setUnavoidable();
	public abstract boolean isUnavoidable();
	public abstract void reset();
	public abstract void clearDamage();
	public abstract void addVanillaPunch(float var0);
	public abstract void addAbilityDamage(float var0, @Nullable() AbilityCore<?> var1);
	public abstract void addDamage(float var0, SourceElement var1, SourceHakiNature var2, SourceType... var3);
	public abstract void addDamage(float var0, IDamageSourceHandler$NuDamageValue var1);
	public abstract Set<Map.Entry<IDamageSourceHandler$NuDamageValue, Float>> getDamageValues();
	public abstract float getTotalDamage();
	public abstract float getTotalBaseDamage();
	public abstract float getTotalAbilityDamage();
	public abstract float getTotalAbilityDamageForType(SourceType var0);
	public abstract float getTotalAbilityDamageForHaki(SourceHakiNature var0);
	public abstract boolean hasElement(SourceElement var0);
	public abstract boolean hasHakiNature(SourceHakiNature var0);
	public abstract boolean hasType(SourceType var0);
	public abstract boolean hasAbility(AbilityCore<?> var0);
	public abstract Set<AbilityCore<?>> getAbilitiesForElement(SourceElement var0);
	public abstract Set<AbilityCore<?>> getAbilitiesForHakiNature(SourceHakiNature var0);
	public abstract Set<AbilityCore<?>> getAbilitiesForType(SourceType var0);
}