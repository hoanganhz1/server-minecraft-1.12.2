package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pero;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class CandyEscalatorProjectile extends NuProjectileEntity {
	// Fields
	private final SimpleBlockPlacer placer;

	public CandyEscalatorProjectile(EntityType<? extends CandyEscalatorProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.placer = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.CANDY.get())).defaultBlockState()).setRule(DefaultProtectionRules.AIR).setSize(1);
	}
	public CandyEscalatorProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.CANDY_ESCALATOR.get())),var1,var2,var3);
		this.placer = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.CANDY.get())).defaultBlockState()).setRule(DefaultProtectionRules.AIR).setSize(1);
		this.setMaxLife(30);
		this.setPhysical();
		this.setPassThroughEntities();
		this.setPassThroughBlocks();
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onTickEvent() {
		this.placer.generate(this.level(),this.blockPosition().below(2),BlockGenerators.CUBE);
	}
}