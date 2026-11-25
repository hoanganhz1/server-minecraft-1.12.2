package xyz.pixelatedw.mineminenomi.entities.mobs;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.entities.GoalMemories;
import xyz.pixelatedw.mineminenomi.api.entities.IGoalMemoriesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.IWithHome;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.items.weapons.ModGunItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public abstract class OPEntity extends PathfinderMob implements IEntityWithComplexSpawn, IGoalMemoriesEntity, RangedAttackMob, IWithHome {
	// Fields
	private static final float SPAWNER_DESPAWN_DISTANCE = 40000F;
	private final GoalMemories goalMemories;
	protected ResourceLocation[] textures;
	private EntityStatsData entityStatData;
	private ResourceLocation currentTexture;
	private boolean isSpawnerChild;
	private Optional<Vec3> homePosition;
	private IMobDetails<OPEntity> factionInit;
	private boolean isInitialized;

	public OPEntity(EntityType<? extends OPEntity> var1, Level var2) {
		this(var1,var2,null);
	}
	public OPEntity(EntityType<? extends OPEntity> var1, Level var2, ResourceLocation[] var3) {
		super(var1,var2);
		this.goalMemories = new GoalMemories();
		this.currentTexture = null;
		this.isSpawnerChild = false;
		this.homePosition = (Optional) (((java.util.Optional)(Optional.empty())));
		this.textures = var3;
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.chooseTexture();
			this.entityStatData = ((EntityStatsData)(EntityStatsData.get((LivingEntity)(this)).orElseThrow()));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ENTITY_INTERACTION_RANGE).add(Attributes.ATTACK_DAMAGE).add(Attributes.ATTACK_SPEED);
	}
	@Nullable()
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		var4 = super.finalizeSpawn(var1,var2,var3,var4);
		this.setLeftHanded(false);
		if ((this.getEntityStats().getDoriki()) > (0D)){
			this.xpReward = (int)((this.getEntityStats().getDoriki()) / (100D));
		}
		if (MobSpawnType.isSpawner(var3)){
			this.isSpawnerChild = true;
		}
		this.homePosition = (Optional) (Optional.of(this.position()));
		return var4;
	}
	public void initFaction() {
		if ((this.factionInit) == (null)){
			return;
		}else{
			this.factionInit.init(this);
			return;
		}
	}
	public void setFactionInit(IMobDetails<OPEntity> var1) {
		this.factionInit = var1;
	}
	public boolean removeWhenFarAway(double var1) {
		if ((this.isSpawnedViaSpawner()) && ((var1) > (40000D))){
			return true;
		}else{
			return false;
		}
	}
	public static boolean checkSpawnRules(EntityType<? extends OPEntity> var0, ServerLevelAccessor var1, MobSpawnType var2, BlockPos var3, RandomSource var4) {
		if ((var2) == (MobSpawnType.SPAWNER)){
			return true;
		}else{
			if ((var1.getDifficulty()) == (Difficulty.PEACEFUL)){
				return false;
			}else{
				if (((var1.getTimeOfDay(0F)) > (0.25F)) && ((var1.getTimeOfDay(0F)) < (0.8F))){
					return false;
				}else{
					if (!(var1.canSeeSky(var3))){
						return false;
					}else{
						if ((var3.getY()) > (150)){
							return false;
						}else{
							if (!(var1.getBlockState(var3.below()).isValidSpawn((BlockGetter)(var1),var3.below(),var0))){
								return false;
							}else{
								return true;
							}
						}
					}
				}
			}
		}
	}
	public void initEntity() {
	}
	public void tick() {
		if (!(this.level().isClientSide)){
			if (!(this.isInitialized)){
				this.initEntity();
				this.setHealth(this.getMaxHealth());
				this.isInitialized = true;
			}
			if ((this.getVehicle() instanceof Boat) && ((this.getTarget()) != (null))){
				this.stopRiding();
			}
			if (((this.tickCount) % (20)) == (0)){
				if (GoalHelper.hasAliveTarget((Mob)(this))){
					ItemStack var1 = this.getMainHandItem();
					Function<AnimationData, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(l0.isAnimationPlaying(ModAnimations.AIM_SNIPER));
		};
					if ((!(((Boolean)(((java.util.Optional)(AnimationData.get((LivingEntity)(this)))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue())) && (var1.is(ModTags$Items.GUNS))){
						ModNetwork.sendToAllTracking(SToggleAnimationPacket.playAnimation((LivingEntity)(this),ModAnimations.AIM_SNIPER,20,true),(Entity)(this));
					}
				}else{
					ModNetwork.sendToAllTracking(SToggleAnimationPacket.stopAnimation((LivingEntity)(this),ModAnimations.AIM_SNIPER),(Entity)(this));
				}
			}
		}
		super.tick();
	}
	public void aiStep() {
		this.updateSwingTime();
		super.aiStep();
	}
	public boolean canDrownInFluidType(FluidType var1) {
		if (((var1) == (NeoForgeMod.WATER_TYPE.value())) && (this.getEntityStats().isFishman())){
			return false;
		}else{
			return true;
		}
	}
	public void setTextures(ResourceLocation[] var1) {
		this.textures = var1;
	}
	public void chooseTexture() {
		this.chooseTexture(this.random);
	}
	public void chooseTexture(RandomSource var1) {
		if (((this.textures) != (null)) && ((this.textures.length) > (0))){
			int var2 = var1.nextInt(this.textures.length);
			this.setTexture(this.textures[var2]);
		}
	}
	public void customServerAiStep() {
		this.goalMemories.tick();
	}
	protected Vec3 getPassengerAttachmentPoint(Entity var1, EntityDimensions var2, float var3) {
		return new Vec3(0D,-0.35D,0D);
	}
	public ResourceLocation getCurrentTexture() {
		return this.currentTexture;
	}
	protected void setTexture(ResourceLocation var1) {
		this.currentTexture = var1;
	}
	public boolean isSpawnedViaSpawner() {
		return this.isSpawnerChild;
	}
	public Optional<Vec3> getHomePosition() {
		return this.homePosition;
	}
	public GoalMemories getGoalMemories() {
		return this.goalMemories;
	}
	public void performRangedAttack(LivingEntity var1, float var2) {
		if ((!(this.isUsingItem())) && (this.canPerformRangedAttack(var1))){
			Projectile var3 = this.getRangedProjectile(var1);
			if ((var3) == (null)){
				return;
			}else{
				float var4 = Math.max(0F,(6F) - (((float)(this.level().getDifficulty().getId())) * (2.5F)));
				double var5 = (var1.getX()) - (this.getX());
				double var6 = (var1.getY(0.3D)) - (var3.getY());
				var3.shoot(var5,var6,(var1.getZ()) - (this.getZ()),2.5F,var4);
				this.level().addFreshEntity((Entity)(var3));
				ModGunItem.playShootSound(this.getMainHandItem(),(LivingEntity)(this));
				return;
			}
		}else{
			return;
		}
	}
	@Nullable()
	public Projectile getRangedProjectile(LivingEntity var1) {
		return null;
	}
	public boolean canPerformRangedAttack(LivingEntity var1) {
		return false;
	}
	public ItemStack getRandomSword(List<Supplier<? extends Item>> var1) {
		return this.getRandomSword(this.random,var1);
	}
	public ItemStack getRandomSword(RandomSource var1, List<Supplier<? extends Item>> var2) {
		return new ItemStack(((ItemLike)(((Supplier)(var2.get(var1.nextInt(var2.size())))).get())));
	}
	public boolean isAboveNormalDifficulty() {
		return (this.level().getDifficulty().getId()) > (Difficulty.NORMAL.getId());
	}
	public EntityStatsData getEntityStats() {
		if ((this.entityStatData) == (null)){
			this.entityStatData = ((EntityStatsData)(EntityStatsData.get((LivingEntity)(this)).orElseThrow()));
		}
		return this.entityStatData;
	}
	public double getRandomDorikiBonus(int var1, int var2) {
		if (this.isAboveNormalDifficulty()){
			var1 = (var1) + (500);
			var2 = (var2) + (500);
		}
		if (ExtendedWorldData.get().isNewWorldActive(this.blockPosition())){
			var1 = (var1) + (250);
		}
		return (double)(WyHelper.randomWithRange(var1,var2));
	}
	public float getRandomHakiBonus(int var1, int var2) {
		if (this.isAboveNormalDifficulty()){
			var1 = (var1) + (5);
			var2 = (var2) + (10);
		}
		if (ExtendedWorldData.get().isNewWorldActive(this.blockPosition())){
			var2 = (var2) + (10);
		}
		return (float)(WyHelper.randomWithRange(var1,var2));
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		if ((this.getCurrentTexture()) != (null)){
			var1.putString("texture",this.getCurrentTexture().toString());
		}
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		if (var1.contains("texture")){
			this.setTexture(ResourceLocation.parse(var1.getString("texture")));
		}
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeBoolean((this.currentTexture) != (null));
		if ((this.currentTexture) != (null)){
			var1.writeResourceLocation(this.currentTexture);
		}
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		if (var1.readBoolean()){
			this.currentTexture = var1.readResourceLocation();
		}
	}
}