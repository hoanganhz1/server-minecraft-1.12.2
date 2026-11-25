package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityUseEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityUseEvent$Pre;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;

public abstract class TempoAbility extends PassiveAbility {
	public TempoAbility(AbilityCore<? extends TempoAbility> var1) {
		super((AbilityCore)(var1));
	}
	public void use(LivingEntity var1) {
		if (((AbilityUseEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(new AbilityUseEvent$Pre(var1,(IAbility)(this)))))).isCanceled()){
			return;
		}else{
			this.useTempo(var1);
			NeoForge.EVENT_BUS.post((Event)(new AbilityUseEvent$Post(var1,(IAbility)(this))));
			return;
		}
	}
	public void useTempo(LivingEntity var1) {
	}
	public boolean isUnderWeatherCloud(WeatherCloudEntity var1, LivingEntity var2) {
		if ((Math.sqrt(var2.distanceToSqr(new Vec3(var1.getX(),var2.getY(),var1.getZ())))) < ((double)((var1.getDimensions(Pose.STANDING).width()) / (2F)))){
			return true;
		}else{
			return false;
		}
	}
}