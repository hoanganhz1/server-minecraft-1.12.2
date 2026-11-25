package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bari;

import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.abilities.bari.BarrierAbility;
import xyz.pixelatedw.mineminenomi.abilities.bari.BarrierbilityStairsAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BarrierbilityStairsProjectile extends NuProjectileEntity {
	public BarrierbilityStairsProjectile(EntityType<BarrierbilityStairsProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BarrierbilityStairsProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.BARRIERBILITY_STAIRS.get())),var1,var2,var3);
		this.setMaxLife(40);
		this.setPhysical();
		this.setPassThroughEntities();
		this.setPassThroughBlocks();
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void tickEvent() {
		if ((this.getOwner()) == (null)){
			return;
		}else{
			AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(this.getOwner()))).orElse((Object)(null))));
			if ((var1) != (null)){
				BarrierbilityStairsAbility var2 = ((BarrierbilityStairsAbility)(var1.getEquippedAbility(((AbilityCore)(BarrierbilityStairsAbility.INSTANCE.get())))));
				if (((var2) != (null)) && (var2.isContinuous())){
					SimpleBlockPlacer var3 = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.BARRIER.get())).defaultBlockState()).setRule(BarrierAbility.GRIEF_RULE).setSize(1);
					var3.generate(this.level(),this.blockPosition().below(2),BlockGenerators.CUBE);
					var2.fillBlocksList(var3.getPlacedPositions());
				}else{
					return;
				}
			}
			return;
		}
	}
}