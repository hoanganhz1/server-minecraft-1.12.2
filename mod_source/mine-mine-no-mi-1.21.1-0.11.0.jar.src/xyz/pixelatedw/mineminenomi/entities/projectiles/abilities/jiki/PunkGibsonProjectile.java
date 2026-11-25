package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.jiki.JikiHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class PunkGibsonProjectile extends NuProjectileEntity {
	// Fields
	private List<ItemStack> magneticItems;

	public PunkGibsonProjectile(EntityType<? extends PunkGibsonProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.magneticItems = new ArrayList();
	}
	public PunkGibsonProjectile(Level var1, LivingEntity var2, List<ItemStack> var3, IAbility var4) {
		super(((EntityType)(ModProjectiles.PUNK_GIBSON.get())),var1,var2,var4);
		this.magneticItems = new ArrayList();
		this.setDamage(40F);
		this.setFist();
		this.setEntityCollisionSize(4D);
		this.magneticItems = var3;
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		JikiHelper.dropComponentItems(this.level(),var1.getLocation(),this.magneticItems);
	}
}