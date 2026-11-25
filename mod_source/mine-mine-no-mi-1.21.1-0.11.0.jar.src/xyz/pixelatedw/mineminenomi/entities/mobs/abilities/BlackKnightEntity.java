package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.ito.KumoNoSugakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.OverheatAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.TamaitoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class BlackKnightEntity extends CloneEntity {
	// Fields
	private static final UUID OVERHEAT_COOLDOWN_MODIFIER_UUID = UUID.fromString("1c155dbe-ddc4-4a0c-a189-eb617e0ad55b");
	private static final UUID TAMAITO_COOLDOWN_MODIFIER_UUID = UUID.fromString("01afb328-256f-46d6-a240-ddb1112466a5");

	public BlackKnightEntity(EntityType<BlackKnightEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BlackKnightEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModMobs.BLACK_KNIGHT.get())),var1,var2);
		if (((var1) != (null)) && (!(var1.isClientSide))){
			this.copyOwnerEquipment();
			this.setUseOwnerTexture();
			this.setSprinting(false);
			this.setMaxAliveTicks(-1);
			this.disablePermaPanic();
			this.getAttribute(ModAttributes.TOUGHNESS).setBaseValue(4D);
			this.getAttribute(Attributes.ARMOR).setBaseValue(10D);
			this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8D);
			this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(2D);
			((GroundPathNavigation)(this.getNavigation())).setCanOpenDoors(true);
			ProjectileAbilityWrapperGoal var3 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(OverheatAbility.INSTANCE.get())));
			((CooldownComponent)(((OverheatAbility)(var3.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).get())).getBonusManager().addBonus(OVERHEAT_COOLDOWN_MODIFIER_UUID,"Overheat Cooldown Modifier",BonusOperation.MUL,2F);
			ProjectileAbilityWrapperGoal var4 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(TamaitoAbility.INSTANCE.get())));
			((CooldownComponent)(((TamaitoAbility)(var4.getAbility())).getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).get())).getBonusManager().addBonus(TAMAITO_COOLDOWN_MODIFIER_UUID,"Tamaito Cooldown Modifier",BonusOperation.MUL,2F);
			this.goalSelector.addGoal(1,(Goal)(new ReactiveGuardAbilityWrapperGoal((Mob)(this),((AbilityCore)(KumoNoSugakiAbility.INSTANCE.get())))));
			this.goalSelector.addGoal(2,(Goal)(var3));
			this.goalSelector.addGoal(2,(Goal)(var4));
			AbilityHelper.forceCooldownOnAllAbilities((LivingEntity)(this),40);
		}
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(0,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new OpenDoorGoal((Mob)(this),false)));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.goalSelector.addGoal(3,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.8D)));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(5,(Goal)(new RandomLookAroundGoal((Mob)(this))));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.2800000011920929D).add(Attributes.ATTACK_DAMAGE,10D).add(Attributes.MAX_HEALTH,200D).add(Attributes.KNOCKBACK_RESISTANCE,1D).add(Attributes.ARMOR,2D);
	}
	public void tick() {
		super.tick();
		Level var1 = super.level();
		if (((var1) != (null)) && (!(var1.isClientSide))){
			if (((this.tickCount) % (20)) == (0)){
				this.copyOwnerEquipment();
			}
			return;
		}else{
			return;
		}
	}
	public void setOwner(LivingEntity var1) {
		super.setOwner(var1);
		this.unlockHakiOfOwner(var1);
	}
}