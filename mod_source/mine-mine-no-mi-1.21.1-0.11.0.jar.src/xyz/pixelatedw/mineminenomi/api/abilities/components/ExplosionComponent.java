package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.ComponentNotRegisteredException;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion$IOnBlockDestroyed;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion$IOnEntityHit;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class ExplosionComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final int DEFAULT_LIMIT = 4096;
	private int blocksAffectedLimit;
	private int blocksAffected;
	private boolean hasBlockDestructionLimit;
	private boolean removeOnBlockLimit;
	private Set<Entity> hits;
	private final PriorityEventPool<ExplosionComponent$ICheckLimitEvent> checkLimitEvents;
	private Entity explosionSource;

	public ExplosionComponent(IAbility var1) {
		this(var1,4096);
	}
	public ExplosionComponent(IAbility var1, int var2) {
		super(((AbilityComponentKey)(ModAbilityComponents.EXPLOSION.get())),var1);
		this.blocksAffected = 0;
		this.hasBlockDestructionLimit = true;
		this.removeOnBlockLimit = true;
		this.hits = new HashSet();
		this.checkLimitEvents = (PriorityEventPool) (new PriorityEventPool());
		this.blocksAffectedLimit = var2;
	}
	public ExplosionComponent addLimitCheckEvent(int var1, ExplosionComponent$ICheckLimitEvent var2) {
		this.checkLimitEvents.addEvent(var1,var2);
		return this;
	}
	public static void createExplosion(NuProjectileEntity var0, Consumer<? super ExplosionComponent> var1) {
		if ((var0) == (null)){
			return;
		}else{
			createExplosion(((IAbility)(((java.util.Optional)(var0.getParent())).orElse((Object)(null)))),var1);
			return;
		}
	}
	public static void createExplosion(@Nullable() IAbility var0, Consumer<? super ExplosionComponent> var1) {
		if ((var0) == (null)){
			return;
		}else{
			try{
				if (!(var0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.EXPLOSION.get()))))){
					throw new ComponentNotRegisteredException(((AbilityComponentKey)(ModAbilityComponents.EXPLOSION.get())));
				}
			}catch(Exception var2){
				var2.printStackTrace();
				return;
			}
			ExplosionComponent var2_1 = ((ExplosionComponent)(var0.getComponent(((AbilityComponentKey)(ModAbilityComponents.EXPLOSION.get()))).get()));
			if (!(var2_1.hasReachedLimit())){
				((java.util.function.Consumer)(var1)).accept((Object)(var2_1));
			}
			return;
		}
	}
	protected void doTick(LivingEntity var1) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (((this.explosionSource) != (null)) && (this.explosionSource.isAlive())){
				if (this.hasReachedLimit()){
					if (((!(this.checkLimitEvents.dispatchCancelable((Predicate)(((Predicate<ExplosionComponent$ICheckLimitEvent>)((l0) -> {
			return l0.checkLimit(var1,this.getAbility());
		})))))) ? (1) : (0)) != (0)){
						this.tryRemoveSource();
					}else{
						return;
					}
				}
				return;
			}else{
				if (((this.blocksAffected) <= (0)) && (this.hits.isEmpty())){

				}else{
					this.tryRemoveSource();
				}
				return;
			}
		}
	}
	public AbilityExplosion createExplosion(Entity var1, double var2, double var3, double var4, float var5) {
		return this.createExplosion(var1,(LivingEntity)(null),var2,var3,var4,var5);
	}
	public AbilityExplosion createExplosion(Entity var1, @Nullable() LivingEntity var2, double var3, double var4, double var5, float var6) {
		this.ensureIsRegistered();
		Level var7 = var1.level();
		this.hits.clear();
		this.explosionSource = var1;
		AbilityExplosion var8 = new AbilityExplosion(var1,this.getAbility(),var3,var4,var5,var6);
		var8.setSpawner(var2);
		ExplosionComponent var9 = this;
		Level var10 = var7;
		AbilityExplosion var11 = var8;
		final Level var10_f1 = var10;
		final AbilityExplosion var11_f2 = var11;
		AbilityExplosion$IOnBlockDestroyed var12 = (l0) -> {
			this.increaseBlocksAffected(Math.max((int)(var10_f1.getBlockState(l0).getExplosionResistance((BlockGetter)(var10_f1),l0,(Explosion)(var11_f2))),1));
		};
		var8.onBlockDestroyedEvent = var12;
		ExplosionComponent var13 = this;
		LivingEntity var14 = var2;
		Entity var15 = var1;
		final LivingEntity var14_f3 = var14;
		final Entity var15_f4 = var15;
		AbilityExplosion$IOnEntityHit var16 = (l0) -> {
			if (((var14_f3) != (null)) && (((Entity)(l0)).equals(var14_f3))){
				return false;
			}else{
				if (((Entity)(l0)).equals(var15_f4)){
					return false;
				}else{
					if (this.canHit((Entity)(l0))){
						return true;
					}else{
						return false;
					}
				}
			}
		};
		var8.onEntityHitEvent = var16;
		return var8;
	}
	public boolean canHit(Entity var1) {
		this.ensureIsRegistered();
		if (this.hits.contains(var1)){
			return false;
		}else{
			this.hits.add(var1);
			return true;
		}
	}
	public void increaseBlocksAffected(int var1) {
		this.blocksAffected = Mth.clamp((this.blocksAffected) + (var1),0,this.blocksAffectedLimit);
	}
	public int getBlocksAffected() {
		return this.blocksAffected;
	}
	public void setBlocksAffectedLimit(int var1) {
		this.blocksAffectedLimit = var1;
	}
	public int getBlocksAffectedLimit() {
		return this.blocksAffectedLimit;
	}
	public boolean hasReachedLimit() {
		if ((this.blocksAffectedLimit) == (0)){
			this.blocksAffectedLimit = 4096;
		}
		return (this.hasBlockDestructionLimit) && ((this.blocksAffected) >= (this.blocksAffectedLimit));
	}
	public void setKillOnBlockLimit(boolean var1) {
		this.removeOnBlockLimit = var1;
	}
	public void tryRemoveSource() {
		if (((this.explosionSource) != (null)) && ((!(this.explosionSource instanceof Player)) && (this.removeOnBlockLimit))){
			this.explosionSource.remove(Entity.RemovalReason.DISCARDED);
		}
		this.blocksAffected = 0;
		this.hits.clear();
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		var2.putInt("blocksAffected",this.blocksAffected);
		var2.putInt("blocksAffectedLimit",this.blocksAffectedLimit);
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		this.blocksAffected = var2.getInt("blocksAffected");
		this.blocksAffectedLimit = var2.getInt("blocksAffectedLimit");
	}
}