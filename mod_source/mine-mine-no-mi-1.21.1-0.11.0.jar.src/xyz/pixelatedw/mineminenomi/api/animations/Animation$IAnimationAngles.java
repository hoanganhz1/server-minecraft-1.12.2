package xyz.pixelatedw.mineminenomi.api.animations;

import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface()
public interface Animation$IAnimationAngles<E extends LivingEntity, M extends EntityModel<E>> {
	public abstract void setAnimationAngles(E var0, M var1, float var2, float var3, float var4, float var5, float var6);
}