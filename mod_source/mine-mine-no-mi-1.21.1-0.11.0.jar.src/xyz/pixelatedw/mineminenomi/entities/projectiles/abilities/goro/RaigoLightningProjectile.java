package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro;

import java.awt.Color;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.abilities.goro.ElThorAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class RaigoLightningProjectile extends NuLightningEntity {
	public RaigoLightningProjectile(EntityType<? extends RaigoLightningProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public RaigoLightningProjectile(Level var1, LivingEntity var2, double var3, double var4, double var5, float var6, float var7, float var8, IAbility var9) {
		super(((EntityType)(ModProjectiles.RAIGO_LIGHTNING.get())),var2,var3,var4,var5,var6,var7,var8,var8,var9);
		this.setAngle(20);
		this.setMaxLife(20);
		this.setDamage(0F);
		this.setSize((var8) / (800F));
		this.setColor((MineMineNoMi.hasBlueGoroPackSelected()) ? (ElThorAbility.BLUE_THUNDER) : (ElThorAbility.YELLOW_THUNDER));
		this.setBranches(3);
		this.setSegments(10);
	}
}