package xyz.pixelatedw.mineminenomi.abilities.marineloyalty;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SmallMusterAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 400F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SmallMusterAbility>> INSTANCE;

	public SmallMusterAbility(AbilityCore<SmallMusterAbility> var1) {
		super((AbilityCore)(var1));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		EntityType var3 = ((EntityType)(ModMobs.MARINE_GRUNT.get()));
		Level var4 = var1.level();
		if (var4 instanceof ServerLevel){
			ServerLevel var5 = ((ServerLevel)(var4));
			int var6 = 0;
			do{
				if ((var6) < (WyHelper.randomWithRange(1,5))){
					BlockPos var7 = WyHelper.findOnGroundSpawnLocation(((ServerLevel)(var1.level())),var3,var1.blockPosition(),10);
					if ((var7) != (null)){
						var3.spawn(var5,var7,MobSpawnType.EVENT);
					}
					var6++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		this.cooldownComponent.startCooldown(var1,400F);
	}
	private static boolean canUnlock(LivingEntity var0) {
		EntityStatsData var1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return false;
		}else{
			if (var1.hasRank(MarineRank.LIEUTENANT)){
				return true;
			}else{
				return false;
			}
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user musters a few low level reinforcements."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(400F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.FACTION,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SmallMusterAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(SmallMusterAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("small_muster","Small Muster",var0));
	}
}