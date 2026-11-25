package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goe;

import java.awt.Color;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TodorokiProjectile extends NuHorizontalLightningEntity {
	// Fields
	public static final Color COLOR = WyHelper.hexToRGB("#93d6eb");

	public TodorokiProjectile(EntityType<? extends TodorokiProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public TodorokiProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.TODOROKI.get())),var2,128F,12F,var3);
		this.setFadeTime(20);
		this.setDamage(15F);
		this.setSize(0.75F);
		this.setColor(COLOR,100);
		this.setRotationSpeed(120F);
	}
	public void setOuterRings() {
		this.setVisualOnly();
		this.setSize(0.9F);
		this.setColor(255,255,255,100);
		this.setSegmentLength(4);
		this.setDepth(1);
		this.setRotationSpeed(-120F);
	}
}