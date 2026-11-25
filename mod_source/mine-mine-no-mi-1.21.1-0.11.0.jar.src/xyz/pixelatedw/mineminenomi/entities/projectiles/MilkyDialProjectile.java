package xyz.pixelatedw.mineminenomi.entities.projectiles;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.blocks.SkyBlockBlock;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class MilkyDialProjectile extends NuProjectileEntity {
	public MilkyDialProjectile(EntityType<? extends MilkyDialProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public MilkyDialProjectile(Level var1, LivingEntity var2) {
		super(((EntityType)(ModProjectiles.MILKY_DIAL_PROJECTILE.get())),var1,var2);
		this.setMaxLife(40);
		this.setPhysical();
		this.setPassThroughBlocks();
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void tickEvent() {
		Iterator var1 = BlockPos.betweenClosed(this.blockPosition().offset(-1,-1,-1),this.blockPosition().offset(1,0,1)).iterator();
		do{
			if (var1.hasNext()){
				BlockPos var2 = ((BlockPos)(var1.next()));
				BlockState var3 = ((BlockState)(((Block)(ModBlocks.SKY_BLOCK.get())).defaultBlockState().setValue((Property)(SkyBlockBlock.TYPE),(Comparable)(Integer.valueOf(this.random.nextInt(4))))));
				NuWorld.setBlockState((Entity)(this.getOwner()),var2,var3,3,DefaultProtectionRules.AIR);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}