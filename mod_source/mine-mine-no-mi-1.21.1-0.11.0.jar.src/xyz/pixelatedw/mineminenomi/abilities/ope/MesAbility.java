package xyz.pixelatedw.mineminenomi.abilities.ope;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MesAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 600F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<MesAbility>> INSTANCE;

	public MesAbility(AbilityCore<MesAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent.addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RoomAbility::hasRoomActive))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RoomAbility::hasRoomActive))));
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if ((!(var1.level().isClientSide)) && (RoomAbility.hasRoomActive(var1,this).isFail())){
			this.continuousComponent.stopContinuity(var1);
		}
	}
	public float getPunchCooldown() {
		return 600F;
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		DevilFruitData var4 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var2))).orElse((Object)(null))));
		EntityStatsData var5 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var2))).orElse((Object)(null))));
		if (((var5) != (null)) && ((var4) != (null))){
			if (var2 instanceof Player){
				Player var6 = ((Player)(var2));
				if (var4.hasDevilFruit(ModFruits.WARA_WARA_NO_MI)){
					Iterator var7 = var6.getInventory().items.iterator();
					do{
						if (var7.hasNext()){
							if ((((ItemStack)(var7.next())).getItem()) == (ModItems.STRAW_DOLL.get())){
								return true;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
			}
			if ((!(CombatHelper.isTargetBlocking(var1,var2))) && (!(CombatHelper.isHakiBlocking(var1,var2)))){

			}else{
				if (((((var1.getHealth()) > (var2.getHealth())) && (((double)(var2.getHealth())) < (WyHelper.percentage(15D,(double)(var2.getMaxHealth()))))) ? (1) : (0)) == (0)){
					return true;
				}
			}
			int var6_1 = (((var4.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI)) || (var2.getType().is(EntityTypeTags.UNDEAD))) ? 1 : 0);
			if ((var5.hasHeart()) && ((var6_1) == (0))){
				ItemStack var7_1 = new ItemStack(((ItemLike)(ModItems.HEART.get())));
				SoulboundItemHelper.setOwner(var7_1,var2);
				String var8 = var2.getDisplayName().getString();
				String var9 = var8 + "'s Heart";
				var7_1.set(DataComponents.CUSTOM_NAME,Component.literal(var9));
				if (var1 instanceof Player){
					((Player)(var1)).getInventory().add(var7_1);
				}
				var5.setHeart(false);
			}
			return true;
		}else{
			return false;
		}
	}
	public int getUseLimit() {
		return 1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Removes the heart of the user's target which they can then damage to hurt the opponent"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(MesAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("mes","MES",var0));
	}
}