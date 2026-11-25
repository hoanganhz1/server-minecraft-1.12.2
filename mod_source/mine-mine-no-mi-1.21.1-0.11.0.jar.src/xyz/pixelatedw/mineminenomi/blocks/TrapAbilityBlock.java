package xyz.pixelatedw.mineminenomi.blocks;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public abstract class TrapAbilityBlock extends Block {
	// Fields
	private int damageDealt;
	private double horizontalFallSpeed;
	private double verticalFallSpeed;
	private Function<Level, DamageSource> damageSource;
	private Supplier<MobEffectInstance> potionEffect;

	public TrapAbilityBlock(BlockBehaviour.Properties var1) {
		super(var1);
		this.damageDealt = 0;
		this.horizontalFallSpeed = 0.05D;
		this.verticalFallSpeed = 0.25D;
		this.potionEffect = null;
	}
	public abstract boolean canWalkOn(LivingEntity var0);
	public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
		if (var4 instanceof LivingEntity){
			if (!(this.canWalkOn(((LivingEntity)(var4))))){
				var4.makeStuckInBlock(var1,new Vec3(this.getHorizontalFallSpeed(),this.getVerticalFallSpeed(),this.getHorizontalFallSpeed()));
			}else{
				return;
			}
		}else{
			if (var4 instanceof Boat){
				((Boat)(var4)).kill();
			}else{
				var4.makeStuckInBlock(var1,new Vec3(this.getVerticalFallSpeed(),this.getHorizontalFallSpeed(),this.getVerticalFallSpeed()));
			}
		}
	}
	public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		if (var4 instanceof EntityCollisionContext){
			Entity var5 = ((EntityCollisionContext)(var4)).getEntity();
			if ((var5 instanceof LivingEntity) && (this.canWalkOn(((LivingEntity)(var5))))){
				return var1.getShape(var2,var3);
			}
		}
		return Shapes.empty();
	}
	public int getDamageDealt() {
		return this.damageDealt;
	}
	public void setDamageDealt(int var1) {
		this.damageDealt = var1;
	}
	public double getVerticalFallSpeed() {
		return this.verticalFallSpeed;
	}
	public void setVerticalFallSpeed(double var1) {
		this.verticalFallSpeed = var1;
	}
	@Nullable()
	public @Nullable() DamageSource getDamageSource(Level var1) {
		if ((this.damageSource) == (null)){
			return null;
		}else{
			return ((DamageSource)(((java.util.function.Function)(this.damageSource)).apply((Object)(var1))));
		}
	}
	public void setDamageSource(Function<Level, DamageSource> var1) {
		this.damageSource = var1;
	}
	public MobEffectInstance getPotionEffect() {
		return ((MobEffectInstance)(this.potionEffect.get()));
	}
	public void setPotionEffect(Supplier<MobEffectInstance> var1) {
		this.potionEffect = var1;
	}
	public boolean isPossibleToRespawnInThis(BlockState var1) {
		return false;
	}
	public double getHorizontalFallSpeed() {
		return this.horizontalFallSpeed;
	}
	public void setHorizontalFallSpeed(double var1) {
		this.horizontalFallSpeed = var1;
	}
}