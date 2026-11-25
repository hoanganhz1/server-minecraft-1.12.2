package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.abilities.brawler.DamageAbsorptionAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.PunchRushAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class BoxingDugongEntity extends AbstractDugongEntity {
	public BoxingDugongEntity(EntityType<BoxingDugongEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
		if (((var2) != (null)) && (!(var2.isClientSide))){
			BoxingDugongEntity var3 = this;
			Consumer<HakiData> var4 = (l0) -> {
			l0.setBusoshokuHakiExp((float)((25) + (this.getRandom().nextInt(10))));
			l0.setKenbunshokuHakiExp((float)((15) + (this.getRandom().nextInt(10))));
		};
			((java.util.Optional)(HakiData.get((LivingEntity)(this)))).ifPresent((Consumer)(var4));
			this.goalSelector.addGoal(0,(Goal)(new BreedGoal((Animal)(this),1D)));
			this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.2000000476837158D,true)));
			this.goalSelector.addGoal(2,(Goal)(new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(DamageAbsorptionAbility.INSTANCE.get())))));
			WeightedList var5 = new WeightedList(new Object[0]);
			BoxingDugongEntity var6 = this;
			Supplier<Goal> var7 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(PunchRushAbility.INSTANCE.get())));
		};
			var5.addEntry(var7,1);
			BoxingDugongEntity var8 = this;
			Supplier<Goal> var9 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(HakaiHoAbility.INSTANCE.get())));
		};
			var5.addEntry(var9,1);
			BoxingDugongEntity var10 = this;
			Consumer var11 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			this.goalSelector.addGoal(2,((Goal)(l0.get())));
		}));
			var5.pickN(this.getRandom(),2).forEach(var11);
			BoxingDugongEntity var12 = this;
			Consumer var13 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
			MobsHelper.getBasicHakiAbilities((Mob)(this),10).forEach(var13);
			this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Monster.class,true,true)));
			this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),WrestlingDugongEntity.class,true,false)));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return AbstractDugongEntity.createAttributes().add(ModAttributes.TOUGHNESS,2D);
	}
	@Nullable()
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		if ((var4) == (null)){
			var4 = new AgeableMob.AgeableMobGroupData(0.2F);
		}
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomHealth());
		this.getAttribute(Attributes.ARMOR).setBaseValue(this.generateRandomArmor());
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.generateRandomAttack());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
		this.setHealth((float)(this.getAttribute(Attributes.MAX_HEALTH).getValue()));
		return super.finalizeSpawn(var1,var2,var3,var4);
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		BoxingDugongEntity var3 = new BoxingDugongEntity(((EntityType)(ModMobs.BOXING_DUGONG.get())),(Level)(var1));
		this.setOffspringAttributes(var2,var3);
		if ((this.hasHakiLearned()) && ((var2 instanceof AbstractDugongEntity) && (((AbstractDugongEntity)(var2)).hasHakiLearned()))){
			BoxingDugongEntity var4 = this;
			Consumer var5 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
			MobsHelper.getBasicHakiAbilities((Mob)(var3),80).forEach(var5);
		}
		return var3;
	}
	protected void setOffspringAttributes(AgeableMob var1, BoxingDugongEntity var2) {
		double var3 = ((this.getAttributeBaseValue(Attributes.MAX_HEALTH)) + (var1.getAttributeBaseValue(Attributes.MAX_HEALTH))) + (this.generateRandomHealth());
		var2.getAttribute(Attributes.MAX_HEALTH).setBaseValue((var3) / (3D));
		double var4 = ((this.getAttributeBaseValue(Attributes.ARMOR)) + (var1.getAttributeBaseValue(Attributes.ARMOR))) + (this.generateRandomArmor());
		var2.getAttribute(Attributes.ARMOR).setBaseValue((var4) / (3D));
		double var5 = ((this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED)) + (var1.getAttributeBaseValue(Attributes.MOVEMENT_SPEED))) + (this.generateRandomSpeed());
		var2.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((var5) / (3D));
		double var6 = ((this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE)) + (var1.getAttributeBaseValue(Attributes.ATTACK_DAMAGE))) + (this.generateRandomAttack());
		var2.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((var6) / (3D));
	}
	protected double generateRandomHealth() {
		return (60D) + ((this.random.nextDouble()) * (20D));
	}
	protected double generateRandomArmor() {
		return (2D) + ((this.random.nextDouble()) * (4D));
	}
	protected double generateRandomSpeed() {
		return (0.28D) + ((this.random.nextDouble()) * (0.06D));
	}
	protected double generateRandomAttack() {
		return (7D) + ((this.random.nextDouble()) * (3D));
	}
}