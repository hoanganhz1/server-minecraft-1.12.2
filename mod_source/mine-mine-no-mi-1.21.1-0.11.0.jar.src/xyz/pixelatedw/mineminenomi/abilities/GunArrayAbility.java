package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Iterator;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.armors.WootzArmorItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModGunItem;

public class GunArrayAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 40;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GunArrayAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RepeaterComponent repeaterComponent;
	private final ProjectileComponent projectileComponent;
	private ItemStack bulletStack;

	public GunArrayAbility(AbilityCore<GunArrayAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addTriggerEvent((RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::triggerRepeaterEvent)))).addStopEvent((RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::stopRepeaterEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<NuProjectileEntity>) (this::createProjectile))))));
		this.bulletStack = ItemStack.EMPTY;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.repeaterComponent,this.projectileComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canUse))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.repeaterComponent.start(var1,20,20);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (this.bulletStack.isEmpty()){
			this.findNewStack(var1);
			if (this.bulletStack.isEmpty()){
				this.continuousComponent.stopContinuity(var1);
			}else{
				return;
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.repeaterComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,40F);
	}
	private void triggerRepeaterEvent(LivingEntity var1, IAbility var2) {
		float var3 = 3F;
		if (var1.isSprinting()){
			var3 = (var3) * (2F);
		}
		if (var1.isCrouching()){
			var3 = 0F;
		}
		this.projectileComponent.shoot(var1,3F,var3);
		this.bulletStack.shrink(1);
	}
	private void stopRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
	}
	private NuProjectileEntity createProjectile(LivingEntity var1) {
		NuProjectileEntity var3 = null;
		if ((this.bulletStack.getItem()) == (ModItems.KAIROSEKI_BULLET.get())){
			var3 = new KairosekiBulletProjectile((Level)(var1.level()),(LivingEntity)(var1));
		}else{
			var3 = new NormalBulletProjectile((Level)(var1.level()),(LivingEntity)(var1));
		}
		return var3;
	}
	private void findNewStack(LivingEntity var1) {
		Iterator var2 = ItemsHelper.getAllInventoryItems(var1).iterator();
		do{
			if (var2.hasNext()){
				ItemStack var3 = ((ItemStack)(var2.next()));
				if (((var3) != (null)) && ((!(var3.isEmpty())) && ((ModGunItem.GUN_AMMO.test(var3)) && ((var3.getCount()) >= (4))))){
					this.bulletStack = var3;
					break;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private Result canUse(LivingEntity var1, IAbility var2) {
		Result var3 = AbilityUseConditions.requiresWootzArmor(var1,var2);
		if (var3.isFail()){
			return var3;
		}else{
			this.findNewStack(var1);
			if (this.bulletStack.isEmpty()){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		}
	}
	private static boolean canUnlock(LivingEntity var0) {
		return WootzArmorItem.hasArmorEquipped(var0);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("While active it automatically fires bullets from the inventory."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.EQUIPMENT,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GunArrayAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.IMBUING).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(GunArrayAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gun_array","Gun Array",var0));
	}
}