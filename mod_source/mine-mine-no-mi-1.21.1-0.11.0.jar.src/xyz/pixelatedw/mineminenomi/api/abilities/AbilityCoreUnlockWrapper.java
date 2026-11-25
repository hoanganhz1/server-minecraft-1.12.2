package xyz.pixelatedw.mineminenomi.api.abilities;

import java.time.Instant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;

public class AbilityCoreUnlockWrapper<A extends IAbility> {
	// Fields
	private final AbilityCore<A> abilityCore;
	private final AbilityUnlock unlockedMethod;
	private final long unlockedRealTimestamp;
	private final long unlockedGameTimestamp;

	public AbilityCoreUnlockWrapper(LivingEntity var1, AbilityCore<A> var2, AbilityUnlock var3) {
		this.abilityCore = var2;
		this.unlockedMethod = var3;
		this.unlockedRealTimestamp = Instant.now().getEpochSecond();
		this.unlockedGameTimestamp = var1.level().getGameTime();
	}
	private AbilityCoreUnlockWrapper(AbilityCore<A> var1, AbilityUnlock var2, long var3, long var4) {
		this.abilityCore = var1;
		this.unlockedMethod = var2;
		this.unlockedRealTimestamp = var3;
		this.unlockedGameTimestamp = var4;
	}
	public AbilityCore<A> getAbilityCore() {
		return (AbilityCore<A>) (AbilityCore) (this.abilityCore);
	}
	public AbilityUnlock getUnlockType() {
		return this.unlockedMethod;
	}
	public long getRealTimestamp() {
		return this.unlockedRealTimestamp;
	}
	public long getGameTimestamp() {
		return this.unlockedGameTimestamp;
	}
	public CompoundTag save() {
		CompoundTag var1 = new CompoundTag();
		var1.putString("id",this.abilityCore.getRegistryKey().toString());
		var1.putString("unlockedMethod",this.unlockedMethod.name());
		var1.putLong("realTimestamp",this.unlockedRealTimestamp);
		var1.putLong("gameTimestamp",this.unlockedGameTimestamp);
		return var1;
	}
	public static <A extends IAbility> AbilityCoreUnlockWrapper<A> of(CompoundTag var0) {
		AbilityCore var1 = ((AbilityCore)(WyRegistry.ABILITIES.get(ResourceLocation.parse(var0.getString("id")))));
		AbilityUnlock var2 = AbilityUnlock.valueOf(var0.getString("unlockedMethod"));
		long var3 = var0.getLong("realTimestamp");
		long var4 = var0.getLong("gameTimestamp");
		return new AbilityCoreUnlockWrapper(var1,var2,var3,var4);
	}
}