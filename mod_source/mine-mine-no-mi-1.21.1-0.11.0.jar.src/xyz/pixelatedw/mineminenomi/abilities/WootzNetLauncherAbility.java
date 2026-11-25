package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Iterator;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.NetItem;
import xyz.pixelatedw.mineminenomi.items.armors.WootzArmorItem;

public class WootzNetLauncherAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 600;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<WootzNetLauncherAbility>> INSTANCE;
	private final AnimationComponent animationComponent;
	private ItemStack netStack;

	public WootzNetLauncherAbility(AbilityCore<WootzNetLauncherAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.netStack = ItemStack.EMPTY;
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canUse))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (!(this.netStack.getItem() instanceof NetItem)){
			return;
		}else{
			NetItem var3 = ((NetItem)(this.netStack.getItem()));
			this.animationComponent.start(var1,ModAnimations.MH5_CHARGING,7);
			Vec3 var4 = var1.getLookAngle();
			NetEntity var5 = NetEntity.createFromItem(var1.level(),var1,var3);
			var5.setPos((var1.getX()) + (var4.x),(var1.getEyeY()) + (var4.y),(var1.getZ()) + (var4.z));
			var5.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,1.25F,0F);
			var1.level().addFreshEntity((Entity)(var5));
			this.netStack.shrink(1);
			this.cooldownComponent.startCooldown(var1,600F);
			return;
		}
	}
	private void findNewStack(LivingEntity var1) {
		Iterator var2 = ItemsHelper.getAllInventoryItems(var1).iterator();
		do{
			if (var2.hasNext()){
				ItemStack var3 = ((ItemStack)(var2.next()));
				if (((var3) != (null)) && ((!(var3.isEmpty())) && (var3.getItem() instanceof NetItem))){
					this.netStack = var3;
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
			if (this.netStack.isEmpty()){
				return Result.fail(null);
			}else{
				return Result.success();
			}
		}
	}
	private static boolean canUnlock(LivingEntity var0) {
		if (var0 instanceof Player){
			return false;
		}else{
			return WootzArmorItem.hasArmorEquipped(var0);
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Shoots a net from one of the many guns hidden within the armor."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(600F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.EQUIPMENT,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(WootzNetLauncherAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(WootzNetLauncherAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("wootz_net_launcher","Wootz Net Launcher",var0));
	}
}