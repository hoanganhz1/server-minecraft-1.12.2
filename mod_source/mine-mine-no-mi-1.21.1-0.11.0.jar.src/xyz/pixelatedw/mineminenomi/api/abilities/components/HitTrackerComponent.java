package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class HitTrackerComponent extends AbilityComponent<IAbility> {
	// Fields
	private Set<Entity> hits;
	private Set<UUID> hitsUUIDs;

	public HitTrackerComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.HIT_TRACKER.get())),var1);
		this.hits = new HashSet();
		this.hitsUUIDs = new HashSet();
	}
	public void clearHits() {
		this.hits.clear();
		this.hitsUUIDs.clear();
	}
	public boolean canHit(Entity var1) {
		this.ensureIsRegistered();
		if (this.hits.contains(var1)){
			return false;
		}else{
			this.hits.add(var1);
			this.hitsUUIDs.add(var1.getUUID());
			return true;
		}
	}
	public Set<Entity> getHits() {
		return this.hits;
	}
	public Set<UUID> getHitsUUIDs() {
		return this.hitsUUIDs;
	}
	protected void doTick(LivingEntity var1) {
	}
}