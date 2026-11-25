package xyz.pixelatedw.mineminenomi.abilities.chiyu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.WateringCanItem;

public class TearsAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<TearsAbility>> INSTANCE;
	private final DamageTakenComponent damageTakenComponent;

	public TearsAbility(AbilityCore<TearsAbility> var1) {
		super((AbilityCore)(var1));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnHurtEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.damageTakenComponent};
		super.addComponents(var2);
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if (super.canUse(var1).isFail()){
			return var4;
		}else{
			if (var1 instanceof Player){
				Player var5 = ((Player)(var1));
				ItemStack var6 = null;
				ArrayList var7 = new ArrayList();
				var7.addAll((Collection)(var5.getInventory().items));
				var7.addAll((Collection)(var5.getInventory().offhand));
				Iterator var8 = var7.iterator();
				do{
					if (var8.hasNext()){
						ItemStack var9 = ((ItemStack)(var8.next()));
						if (((var9) != (null)) && ((var9.getItem()) == (ModItems.WATERING_CAN.get()))){
							var6 = var9;
							break;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if ((var6) == (null)){
					return var4;
				}else{
					if ((this.random.nextInt(10)) == (0)){
						WateringCanItem.alterTearAmount(var6,1);
					}
					return var4;
				}
			}else{
				return var4;
			}
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Each time the user is hurt their Watering Can has a 10% chance of being filled with a tear, which can be drank to heal themselves."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(TearsAbility::new)))).addDescriptionLine(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("tears","Tears",var0));
	}
}