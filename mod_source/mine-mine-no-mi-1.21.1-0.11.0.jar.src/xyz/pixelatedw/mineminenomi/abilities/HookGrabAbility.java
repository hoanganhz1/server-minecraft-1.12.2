package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.HookEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class HookGrabAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 240;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HookGrabAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ProjectileComponent projectileComponent;
	private LivingEntity grabbedEntity;
	private ItemStack hookStack;
	private InteractionHand hookHand;
	private HookEntity proj;

	public HookGrabAbility(AbilityCore<HookGrabAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<HookEntity>) (this::createProjectile))))));
		this.grabbedEntity = null;
		this.hookStack = ItemStack.EMPTY;
		this.hookHand = InteractionHand.MAIN_HAND;
		this.proj = null;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.projectileComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresDryUser))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canUseCheck))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((((this.proj) != (null)) && (this.proj.isAlive())) || ((this.grabbedEntity) != (null))){
				if (((this.grabbedEntity) != (null)) && (!((this.grabbedEntity.isAlive()) && ((!(CombatHelper.isGuardBlocking(this.grabbedEntity))) && (var1.hasLineOfSight((Entity)(this.grabbedEntity))))))){
					this.continuousComponent.stopContinuity(var1);
					return;
				}else{
					if ((this.grabbedEntity) != (null)){
						if ((this.grabbedEntity.distanceTo((Entity)(var1))) < (2F)){
							AbilityHelper.setDeltaMovement((Entity)(this.grabbedEntity),0D,0D,0D);
							this.continuousComponent.stopContinuity(var1);
							return;
						}else{
							if (((this.continuousComponent.getContinueTime()) % (2F)) == (0F)){
								Vec3 var3 = this.grabbedEntity.position().subtract(var1.position()).normalize();
								AbilityHelper.setDeltaMovement((Entity)(this.grabbedEntity),-(var3.x),-(var3.y),-(var3.z));
								this.grabbedEntity.fallDistance = 0F;
							}else{
								return;
							}
						}
					}
					return;
				}
			}else{
				this.continuousComponent.stopContinuity(var1);
				return;
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.proj = null;
		this.grabbedEntity = null;
		var1.setItemInHand(this.hookHand,this.hookStack);
		this.cooldownComponent.startCooldown(var1,240F);
	}
	private HookEntity createProjectile(LivingEntity var1) {
		return new HookEntity(var1.level(),var1,(IAbility)(this));
	}
	private Result canUseCheck(LivingEntity var1, IAbility var2) {
		ItemStack var3 = var1.getMainHandItem();
		ItemStack var4 = var1.getMainHandItem();
		int var5 = ((!(var3.isEmpty())) && ((var3.getItem()) == (ModWeapons.HOOK.get()))) ? (1) : (0);
		int var6 = ((!(var4.isEmpty())) && ((var4.getItem()) == (ModWeapons.HOOK.get()))) ? (1) : (0);
		if (((var5) == (0)) && ((var6) == (0))){
			return Result.fail(null);
		}else{
			return Result.success();
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Shoot's the equipped Hook catching the enemy and dragging them towards the user."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F)};
			SourceType[] lv2_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.EQUIPMENT,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HookGrabAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("hook_grab","Hook Grab",var0));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.grabbedEntity = null;
		this.proj = ((HookEntity)(this.projectileComponent.getNewProjectile(var1)));
		HookGrabAbility var3 = this;
		NuProjectileEntity$IOnHitEntityEvent var4 = (l0) -> {
			Entity lv1_2 = l0.getEntity();
			if (lv1_2 instanceof LivingEntity){
				LivingEntity lv1_3 = ((LivingEntity)(lv1_2));
				this.grabbedEntity = lv1_3;
			}
		};
		this.proj.addEntityHitEvent(100,var4);
		ItemStack var5 = var1.getMainHandItem();
		ItemStack var6 = var1.getMainHandItem();
		int var7 = ((!(var5.isEmpty())) && ((var5.getItem()) == (ModWeapons.HOOK.get()))) ? (1) : (0);
		int var8 = ((!(var6.isEmpty())) && ((var6.getItem()) == (ModWeapons.HOOK.get()))) ? (1) : (0);
		if ((var7) != (0)){
			this.hookStack = var1.getMainHandItem();
			this.hookHand = InteractionHand.MAIN_HAND;
		}else{
			if ((var8) != (0)){
				this.hookStack = var1.getMainHandItem();
				this.hookHand = InteractionHand.OFF_HAND;
			}
		}
		var1.setItemInHand(this.hookHand,ItemStack.EMPTY);
		var1.level().addFreshEntity((Entity)(this.proj));
		this.proj.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,4F,0F);
	}
}