package xyz.pixelatedw.mineminenomi.api.quests;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

public class ProjectileStartPosTracker {
	// Fields
	private final Map<UUID, Vec3> startPositions;

	public ProjectileStartPosTracker() {
		this.startPositions = new HashMap();
	}
	public void recordProjectile(Projectile var1) {
		if ((var1) != (null)){
			this.startPositions.put(var1.getUUID(),var1.position());
		}
	}
	public Vec3 getStartPos(Projectile var1, Vec3 var2) {
		if ((var1) == (null)){
			return var2;
		}else{
			return ((Vec3)(((java.util.Map)(this.startPositions)).getOrDefault((Object)(var1.getUUID()),(Object)(var2))));
		}
	}
	public Vec3 getStartPos(LivingEntity var1, DamageSource var2) {
		if ((var2) == (null)){
			return var1.position();
		}else{
			Entity var3 = var2.getDirectEntity();
			if (var3 instanceof Projectile){
				return this.getStartPos(((Projectile)(var3)),var1.position());
			}else{
				return var1.position();
			}
		}
	}
	public void clear() {
		this.startPositions.clear();
	}
}