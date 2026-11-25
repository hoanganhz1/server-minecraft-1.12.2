package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.LevelData;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class WanderingDugongEntity extends AbstractDugongEntity implements IEntityWithComplexSpawn {
	// Fields
	private int headScarId;
	private int chestScarId;
	private int armsScarId;
	private int tailScarId;

	public WanderingDugongEntity(EntityType<? extends WanderingDugongEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.headScarId = 0;
		this.chestScarId = 0;
		this.armsScarId = 0;
		this.tailScarId = 0;
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.headScarId = this.random.nextInt(MobsHelper.DUGONG_HEAD_SCARS_TEXTURES.length);
			this.chestScarId = this.random.nextInt(MobsHelper.DUGONG_CHEST_SCARS_TEXTURES.length);
			this.armsScarId = (this.random.nextBoolean()) ? (this.random.nextInt(MobsHelper.DUGONG_ARMS_SCARS_TEXTURES.length)) : (-1);
			this.tailScarId = (this.random.nextBoolean()) ? (this.random.nextInt(MobsHelper.DUGONG_TAIL_SCARS_TEXTURES.length)) : (-1);
			this.setHealth((float)(this.getAttribute(Attributes.MAX_HEALTH).getValue()));
			WanderingDugongEntity var3 = this;
			Consumer<HakiData> var4 = (l0) -> {
			l0.setBusoshokuHakiExp((float)((45) + (this.getRandom().nextInt(10))));
			l0.setKenbunshokuHakiExp((float)((15) + (this.getRandom().nextInt(10))));
		};
			((java.util.Optional)(HakiData.get((LivingEntity)(this)))).ifPresent((Consumer)(var4));
			this.goalSelector.addGoal(0,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
			this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.2000000476837158D,true)));
			Predicate<LivingEntity> var5 = (l0) -> {
			if (l0 instanceof AbstractDugongEntity){
				if (l0 instanceof WanderingDugongEntity){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		};
			Predicate<LivingEntity> var6 = var5;
			this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Player.class,true,true)));
			this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),OPEntity.class,true,true)));
			this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),PathfinderMob.class,10,true,true,var6)));
			WeightedList var7 = new WeightedList(new Object[0]);
			WanderingDugongEntity var8 = this;
			Supplier<Goal> var9 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get())));
		};
			var7.addEntry(var9,3);
			WanderingDugongEntity var10 = this;
			Supplier<Goal> var11 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(JishinHoAbility.INSTANCE.get())));
		};
			var7.addEntry(var11,2);
			WanderingDugongEntity var12 = this;
			Supplier<Goal> var13 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ChargedPunchAbility.INSTANCE.get())));
		};
			var7.addEntry(var13,2);
			WanderingDugongEntity var14 = this;
			Consumer var15 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			this.goalSelector.addGoal(2,((Goal)(l0.get())));
		}));
			var7.pickN(this.getRandom(),2).forEach(var15);
			WanderingDugongEntity var16 = this;
			Consumer var17 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
			MobsHelper.getBasicHakiAbilities((Mob)(this),80).forEach(var17);
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return AbstractDugongEntity.createAttributes().add(Attributes.ATTACK_DAMAGE,10D).add(Attributes.MOVEMENT_SPEED,0.3D).add(Attributes.MAX_HEALTH,(double)(WyHelper.randomWithRange(60,100))).add(Attributes.ARMOR,(double)(WyHelper.randomWithRange(4,8))).add(ModAttributes.TOUGHNESS,4D);
	}
	public void tame(Player var1) {
		if ((this.random.nextInt(64)) == (0)){
			super.tame(var1);
		}
	}
	public int getHeadScarId() {
		return this.headScarId;
	}
	public int getChestScarId() {
		return this.chestScarId;
	}
	public int getArmsScarId() {
		return this.armsScarId;
	}
	public int getTailScarId() {
		return this.tailScarId;
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		return null;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeInt(this.headScarId);
		var1.writeInt(this.chestScarId);
		var1.writeInt(this.armsScarId);
		var1.writeInt(this.tailScarId);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		this.headScarId = var1.readInt();
		this.chestScarId = var1.readInt();
		this.armsScarId = var1.readInt();
		this.tailScarId = var1.readInt();
	}
	public static boolean checkSpawnRules(EntityType<WanderingDugongEntity> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, RandomSource var4) {
		if (var3.closerThan((Vec3i)(var1.getLevelData().getSpawnPos()),512D)){
			return false;
		}else{
			return Mob.checkMobSpawnRules(var0,var1,var2,var3,var4);
		}
	}
}