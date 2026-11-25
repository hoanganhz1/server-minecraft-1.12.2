package xyz.pixelatedw.mineminenomi.abilities.wara;

import java.util.Optional;
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
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.StrawDollItem;

public class StrawManAbility extends PunchAbility {
	// Fields
	private static final int COOLDOWN = 600;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<StrawManAbility>> INSTANCE;

	public StrawManAbility(AbilityCore<StrawManAbility> var1) {
		super((AbilityCore)(var1));
	}
	public float getPunchDamage() {
		return 1F;
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		if (var1 instanceof Player){
			Player var4 = ((Player)(var1));
			EntityStatsData var5 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var2))).orElse((Object)(null))));
			if ((var5) == (null)){
				return false;
			}else{
				if ((var4.getInventory().countItem(((Item)(ModItems.STRAW_DOLL.get())))) < (10)){
					if ((!(CombatHelper.isTargetBlocking((LivingEntity)(var4),var2))) && (!(CombatHelper.isHakiBlocking((LivingEntity)(var4),var2)))){
						if (var5.hasStrawDoll()){
							ItemStack var6 = StrawDollItem.createDollStack(var2);
							var4.getInventory().add(var6);
							var5.setStrawDoll(false);
						}else{
							return true;
						}
					}else{
						return true;
					}
				}else{
					return true;
				}
			}
		}
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchCooldown() {
		return 600F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates a doll that representing the entity you attacked, any damage will get redirected to it."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST,SourceType.FRIENDLY};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(StrawManAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("straw_man","Straw Man",var0));
	}
}