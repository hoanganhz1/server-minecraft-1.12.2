package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro;

import java.awt.Color;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.handlers.ability.MinkPassivesHandler;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ElectroVisualProjectile extends NuHorizontalLightningEntity {
	public ElectroVisualProjectile(EntityType<? extends ElectroVisualProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public ElectroVisualProjectile(Level var1, LivingEntity var2, IAbility var3, float var4) {
		super(((EntityType)(ModProjectiles.ELECTRO_VISUAL.get())),var2,var4,8F,var3);
		this.setYRot((float)(WyHelper.randomWithRange(0,360)));
		this.setXRot((float)(WyHelper.randomWithRange(-20,20)));
		this.setColor(MinkPassivesHandler.MINK_LIGHTNING_COLOR);
		this.setAngle(60);
		this.setMaxLife(20);
		this.setDamage(0F);
		this.setSize((var4) / (600F));
		this.setBranches(WyHelper.randomWithRange(1,3));
		this.setSegments((int)(((double)(var4)) * (0.6D)));
	}
}