package xyz.pixelatedw.mineminenomi.effects;

import java.util.Iterator;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.jiki.GenocideRaidAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.GenocideRaidEffectEntity;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class GenocideRaidEffect extends DamageOverTimeEffect {
	public GenocideRaidEffect() {
		super(GenocideRaidEffect::createDamageSource,10F,20);
	}
	private static DamageSource createDamageSource(LivingEntity var0) {
		LivingEntity var1 = null;
		Iterator var2 = TargetHelper.getEntitiesInArea((LevelAccessor)(var0.level()),var0,var0.position(),1D,TargetPredicate.EVERYTHING,GenocideRaidEffectEntity.class).iterator();
		do{
			if (var2.hasNext()){
				LivingEntity var3 = ((GenocideRaidEffectEntity)(var2.next())).getOwner();
				if (((var3) != (null)) && (var3.isAlive())){
					var1 = var3;
					break;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return ModDamageSources.getInstance((LevelAccessor)(var0.level())).ability((Entity)(var1),((AbilityCore)(GenocideRaidAbility.INSTANCE.get())));
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean shouldUpdateClient() {
		return true;
	}
}