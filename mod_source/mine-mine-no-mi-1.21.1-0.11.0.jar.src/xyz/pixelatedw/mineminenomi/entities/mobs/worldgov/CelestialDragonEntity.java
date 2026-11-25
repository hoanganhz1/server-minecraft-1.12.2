package xyz.pixelatedw.mineminenomi.entities.mobs.worldgov;

import java.awt.Color;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DespawnAfterTimeGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunTowardsLocationGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFactions;

public class CelestialDragonEntity extends OPEntity {
	// Fields
	private static final byte MOUNT_EVENT = 100;
	private static final byte UNMOUNT_EVENT = 101;
	private Color hairColor;
	private boolean ridingSlave;
	private boolean secondChance;
	private boolean gotHitOnce;

	public CelestialDragonEntity(EntityType<CelestialDragonEntity> var1, Level var2) {
		super((EntityType)(var1),var2,MobsHelper.CELESTIAL_DRAGONS_TEXTURES);
		this.hairColor = Color.BLACK;
		this.ridingSlave = true;
		this.secondChance = false;
		this.gotHitOnce = false;
	}
	public void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(3,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.6D)));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Villager.class,8F)));
		this.goalSelector.addGoal(5,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		Consumer<EntityStatsData> var1 = (l0) -> {
			l0.setFaction(((Faction)(ModFactions.WORLD_GOVERNMENT.get())));
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).ifPresent((Consumer)(var1));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,30D).add(Attributes.MOVEMENT_SPEED,0.20000000298023224D).add(Attributes.ATTACK_DAMAGE,0.1D).add(Attributes.MAX_HEALTH,20D).add(Attributes.ARMOR,1D);
	}
	@Nullable()
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		var4 = super.finalizeSpawn(var1,var2,var3,var4);
		float var5 = this.getRandom().nextFloat();
		float var6 = this.getRandom().nextFloat();
		float var7 = this.getRandom().nextFloat();
		Color var8 = new Color(var5,var6,var7);
		this.hairColor = var8;
		ItemStack var9 = ((Item)(ModArmors.CELESTIAL_DRAGON_BUBBLE.get())).getDefaultInstance();
		int[] var10 = new int[]{this.hairColor.getRGB()};
		var9.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var10));
		this.setItemSlot(EquipmentSlot.HEAD,var9);
		this.mountSlave();
		return var4;
	}
	public boolean hurt(DamageSource var1, float var2) {
		if (super.hurt(var1,var2)){
			Entity var3 = var1.getDirectEntity();
			if (((var3) != (null)) && (var3 instanceof Player)){
				Player var4 = ((Player)(var3));
				EntityStatsData var5 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var4)))).orElse((Object)(null))));
				if ((var5) == (null)){
					return true;
				}else{
					if (!(this.gotHitOnce)){
						var5.alterBounty(10000L,StatChangeSource.KILL_NPC);
						this.gotHitOnce = true;
						if ((!(var5.isMarine())) && (!(var5.isBountyHunter()))){

						}else{
							var5.alterLoyalty(-100D,StatChangeSource.KILL_NPC);
						}
						if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(var4)){
							EventsWorldData.get().addSpecialBusterCall(((ServerLevel)(this.level())),(LivingEntity)(var4));
						}
					}
					double var6 = 0.5D;
					double var7 = 0D;
					if (HakiHelper.hasHardeningActive((LivingEntity)(var4),false,false)){
						var6 = (var6) + (0.2D);
					}
					if (HakiHelper.hasAdvancedBusoActive((LivingEntity)(var4))){
						var6 = (var6) + (1D);
					}
					if (HakiHelper.hasInfusionActive((LivingEntity)(var4))){
						var6 = (var6) + (1D);
					}
					if ((var6) > (1D)){
						var7 = 0.5D;
						this.unmountSlave((LivingEntity)(var4));
						var4.addEffect(new MobEffectInstance((Holder)(ModEffects.IMPACT_FRAME),2,0));
					}
					AbilityHelper.setDeltaMovement((Entity)(this),var4.getLookAngle().add(0D,var7,0D).multiply(var6,1D,var6));
				}
			}
			return true;
		}else{
			return false;
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void handleEntityEvent(byte var1) {
		switch (var1){
		case 100:
			this.ridingSlave = true;
			break;
		case 101:
			this.deathTime = 0;
			this.ridingSlave = false;
			break;
		}
		super.handleEntityEvent(var1);
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putBoolean("hitOnce",this.gotHitOnce);
		var1.putBoolean("ridingSlave",this.ridingSlave);
		var1.putInt("hairColor",this.hairColor.getRGB());
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.gotHitOnce = var1.getBoolean("hitOnce");
		this.ridingSlave = var1.getBoolean("ridingSlave");
		this.hairColor = new Color(var1.getInt("hairColor"));
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		super.writeSpawnData(var1);
		var1.writeInt(this.hairColor.getRGB());
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		this.hairColor = new Color(var1.readInt());
	}
	public void startSeenByPlayer(ServerPlayer var1) {
		super.startSeenByPlayer(var1);
		if (!(this.ridingSlave)){
			this.level().broadcastEntityEvent((Entity)(this),(byte)(101));
		}
	}
	public void die(DamageSource var1) {
		LivingEntity var2 = this.getKillCredit();
		if (((var2) != (null)) && ((var2.isAlive()) && (this.ridingSlave))){
			this.unmountSlave(var2);
		}
		if ((!(this.secondChance)) && ((this.getHealth()) <= (0F))){
			this.setHealth(1F);
			this.deathTime = 0;
			this.secondChance = true;
			return;
		}else{
			super.die(var1);
			return;
		}
	}
	public void mountSlave() {
		this.ridingSlave = true;
		this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
	}
	public void unmountSlave(LivingEntity var1) {
		if (!(this.ridingSlave)){
			return;
		}else{
			this.ridingSlave = false;
			this.level().broadcastEntityEvent((Entity)(this),(byte)(101));
			this.spawnFreedSlave(var1);
			return;
		}
	}
	private void spawnFreedSlave(LivingEntity var1) {
		Villager var2 = new Villager(EntityType.VILLAGER,this.level());
		var2.moveTo(this.position());
		var2.goalSelector.getAvailableGoals().clear();
		int var3 = (50) + (this.random.nextInt(50));
		if (this.random.nextBoolean()){
			var3 = (var3) * (-1);
		}
		int var4 = (50) + (this.random.nextInt(50));
		if (this.random.nextBoolean()){
			var4 = (var4) * (-1);
		}
		Vec3 var5 = this.position().add((double)(var3),0D,(double)(var4));
		var2.goalSelector.addGoal(0,(Goal)(new RunTowardsLocationGoal(var2,1.5D,var5)));
		var2.goalSelector.addGoal(0,(Goal)(new DespawnAfterTimeGoal((Mob)(var2),40)));
		this.level().addFreshEntity((Entity)(var2));
		EntityStatsData var6 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
		if ((var6) != (null)){
			var6.addFreedSlaves(1);
			if (var6.isRevolutionary()){
				var6.alterLoyalty(0.5D,StatChangeSource.KILL_NPC);
			}else{
				return;
			}
		}
	}
	public boolean isRidingSlave() {
		return this.ridingSlave;
	}
	public Color getHairColor() {
		return this.hairColor;
	}
	public boolean removeWhenFarAway(double var1) {
		return (var1) > (1024D);
	}
}