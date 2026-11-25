package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ori;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class AwaseBaoriProjectile extends NuProjectileEntity {
	public AwaseBaoriProjectile(EntityType<? extends AwaseBaoriProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public AwaseBaoriProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.AWASE_BAORI.get())),var1,var2,var3);
		this.setDamage(6F);
		this.setMaxLife(10);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		AwaseBaoriProjectile var2 = this;
		EntityHitResult var3 = var1;
		final EntityHitResult var3_f1 = var3;
		Consumer<IAbility> var4 = (l0) -> {
			AwaseBaoriProjectile lv2_3 = this;
			EntityHitResult lv2_4 = var3_f1;
			final EntityHitResult lv2_4_f1 = lv2_4;
			Consumer<BlockTrackerComponent> lv2_5 = (l2_0) -> {
				SimpleBlockPlacer lv1_3 = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.ORI_BARS.get())).defaultBlockState()).setSize(2).setHollow().setRule(DefaultProtectionRules.AIR_FOLIAGE_LIQUID).setFlag(259);
				lv1_3.generate(this.level(),BlockPos.containing((Position)(lv2_4_f1.getLocation())).above(),BlockGenerators.CUBE);
				l2_0.addPositions(lv1_3.getPlacedPositions());
			};
			((java.util.Optional)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.BLOCK_TRACKER.get()))))).ifPresent((Consumer)(lv2_5));
		};
		((java.util.Optional)(this.getParent())).ifPresent((Consumer)(var4));
	}
}