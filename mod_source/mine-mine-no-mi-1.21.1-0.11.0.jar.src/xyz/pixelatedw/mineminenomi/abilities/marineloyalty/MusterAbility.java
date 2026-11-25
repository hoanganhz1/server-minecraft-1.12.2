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

public class MusterAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 1200F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<MusterAbility>> INSTANCE;

	public MusterAbility(AbilityCore<MusterAbility> var1) {
		super((AbilityCore)(var1));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		EntityType var3 = ((EntityType)(ModMobs.MARINE_GRUNT.get()));
		EntityType var4 = ((EntityType)(ModMobs.MARINE_CAPTAIN.get()));
		Level var5 = var1.level();
		if (var5 instanceof ServerLevel){
			ServerLevel var6 = ((ServerLevel)(var5));
			int var7 = 0;
			do{
				if ((var7) < (WyHelper.randomWithRange(3,10))){
					BlockPos var8 = WyHelper.findOnGroundSpawnLocation(((ServerLevel)(var1.level())),var3,var1.blockPosition(),10);
					if ((var8) != (null)){
						var3.spawn(var6,var8,MobSpawnType.EVENT);
					}
					var7++;
					continue;
				}else{
					break;
				}
			} while (true);
			var7 = 0;
			do{
				if ((var7) < (WyHelper.randomWithRange(1,3))){
					BlockPos var8_1 = WyHelper.findOnGroundSpawnLocation(((ServerLevel)(var1.level())),var4,var1.blockPosition(),10);
					var4.spawn(var6,var8_1,MobSpawnType.EVENT);
					var7++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		this.cooldownComponent.startCooldown(var1,1200F);
	}
	private static boolean canUnlock(LivingEntity var0) {
		EntityStatsData var1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return false;
		}else{
			if (var1.hasRank(MarineRank.CAPTAIN)){
				return true;
			}else{
				return false;
			}
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user musters some reinforcements."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(1200F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.FACTION,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(MusterAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(MusterAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("muster","Muster",var0));
	}
}