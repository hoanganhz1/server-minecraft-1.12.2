package xyz.pixelatedw.mineminenomi.init;

import java.awt.Color;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.abilities.marineloyalty.MusterAbility;
import xyz.pixelatedw.mineminenomi.abilities.marineloyalty.SmallMusterAbility;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.api.factions.RevolutionaryRank;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class ModFactions {
	// Fields
	private static int idx;
	private static final int PIRATE_ORDER;
	private static final int MARINE_ORDER;
	private static final int BOUNTY_HUNTER_ORDER;
	private static final int REVOLUTIONARY_ORDER;
	private static final BiPredicate<LivingEntity, LivingEntity> MARINE_HURT_CHECK;
	public static final Supplier<Faction> CIVILIAN;
	public static final Supplier<Faction> PIRATE;
	public static final Supplier<Faction> MARINE;
	public static final Supplier<Faction> BOUNTY_HUNTER;
	public static final Supplier<Faction> REVOLUTIONARY_ARMY;
	public static final Supplier<Faction> BANDIT;
	public static final Supplier<Faction> WORLD_GOVERNMENT;

	public static void init() {
	}
	static  {
		idx = 1;
		PIRATE_ORDER = idx++;
		MARINE_ORDER = idx++;
		BOUNTY_HUNTER_ORDER = idx++;
		REVOLUTIONARY_ORDER = idx++;
		BiPredicate<LivingEntity, LivingEntity> var0 = (l0, l1) -> {
			EntityStatsData lv1_2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(l0))).orElse((Object)(null))));
			EntityStatsData lv1_3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(l1))).orElse((Object)(null))));
			if (((lv1_2) != (null)) && ((lv1_3) != (null))){
				int lv1_4 = ((l1 instanceof AbstractVillager) || ((l1 instanceof IronGolem) || (lv1_3.isCivilian()))) ? (1) : (0);
				int lv1_5 = ((lv1_2.isMarine()) || ((lv1_2.isBountyHunter()) || (lv1_2.isWorldGovernment()))) ? (1) : (0);
				int lv1_6 = ((lv1_3.isMarine()) || ((lv1_3.isBountyHunter()) || (lv1_3.isWorldGovernment()))) ? (1) : (0);
				if (((lv1_5) != (0)) && (!(((lv1_6) == (0)) && ((lv1_4) == (0))))){
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		};
		MARINE_HURT_CHECK = var0;
		CIVILIAN = ModRegistry.registerFaction("civilian","Civilian",(Supplier)(((Supplier<Faction>)(Faction::new))));
		Supplier<Faction> var1 = () -> {
			CharacterCreatorSelectionInfo lv4_0 = new CharacterCreatorSelectionInfo(ModResources.PIRATE_ICON,PIRATE_ORDER);
			Predicate<LivingEntity> lv4_1 = (l2_0) -> {
				return true;
			};
			Predicate<LivingEntity> lv4_2 = lv4_1;
			BiPredicate<LivingEntity, LivingEntity> lv4_3 = (l2_0, l2_1) -> {
				FactionsWorldData lv3_2 = FactionsWorldData.get();
				EntityStatsData lv3_3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(l2_0))).orElse((Object)(null))));
				EntityStatsData lv3_4 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(l2_1))).orElse((Object)(null))));
				if (((lv3_2) != (null)) && (((lv3_3) != (null)) && ((lv3_4) != (null)))){
					if ((lv3_3.isPirate()) && (lv3_4.isPirate())){
						if ((l2_0 instanceof Mob) && (l2_1 instanceof Mob)){
							return false;
						}else{
							Crew lv3_5 = lv3_2.getCrewWithMember(l2_0.getUUID());
							if ((lv3_5) == (null)){
								return true;
							}else{
								Crew lv3_6 = lv3_2.getCrewWithMember(l2_1.getUUID());
								if ((lv3_6) == (null)){
									return true;
								}else{
									return !(((java.lang.Object)(lv3_5)).equals((Object)(lv3_6)));
								}
							}
						}
					}else{
						return true;
					}
				}else{
					return true;
				}
			};
			BiPredicate<LivingEntity, LivingEntity> lv4_4 = lv4_3;
			return new Faction().setBookDetails(lv4_0).setCanReceiveBountyCheck(lv4_2).addCanHurtCheck(lv4_4).setFlagBackgroundColor(Color.BLACK.getRGB());
		};
		PIRATE = ModRegistry.registerFaction("pirate","Pirate",var1);
		Supplier<Faction> var2 = () -> {
			CharacterCreatorSelectionInfo lv6_0 = new CharacterCreatorSelectionInfo(ModResources.MARINE_ICON,MARINE_ORDER);
			Supplier[] lv6_1 = new Supplier[]{SmallMusterAbility.INSTANCE,MusterAbility.INSTANCE,CommandAbility.INSTANCE};
			lv6_0.addTopAbilities(lv6_1);
			Predicate<LivingEntity> lv6_2 = (l2_0) -> {
				return true;
			};
			return new Faction().setBookDetails(lv6_0).setCanReceiveLoyaltyCheck(lv6_2).addCanHurtCheck(MARINE_HURT_CHECK).setRanks(MarineRank.class).setFlagBackgroundColor(15398380);
		};
		MARINE = ModRegistry.registerFaction("marine","Marine",var2);
		Supplier<Faction> var3 = () -> {
			CharacterCreatorSelectionInfo lv8_0 = new CharacterCreatorSelectionInfo(ModResources.BOUNTY_HUNTER_ICON,BOUNTY_HUNTER_ORDER);
			Predicate<LivingEntity> lv8_1 = (l2_0) -> {
				return true;
			};
			return new Faction().setBookDetails(lv8_0).setCanReceiveLoyaltyCheck(lv8_1).addCanHurtCheck(MARINE_HURT_CHECK);
		};
		BOUNTY_HUNTER = ModRegistry.registerFaction("bounty_hunter","Bounty Hunter",var3);
		Supplier<Faction> var4 = () -> {
			CharacterCreatorSelectionInfo lv12_0 = new CharacterCreatorSelectionInfo(ModResources.REVOLUTIONARY_ARMY_ICON,REVOLUTIONARY_ORDER);
			BiPredicate<LivingEntity, LivingEntity> lv12_1 = (l2_0, l2_1) -> {
				EntityStatsData lv9_2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(l2_0))).orElse((Object)(null))));
				EntityStatsData lv9_3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(l2_1))).orElse((Object)(null))));
				if (((lv9_2) != (null)) && ((lv9_3) != (null))){
					int lv9_4 = ((l2_1 instanceof AbstractVillager) || (lv9_3.isCivilian())) ? (1) : (0);
					if ((lv9_2.isRevolutionary()) && (!((!(lv9_3.isRevolutionary())) && ((lv9_4) == (0))))){
						return false;
					}else{
						return true;
					}
				}else{
					return true;
				}
			};
			BiPredicate<LivingEntity, LivingEntity> lv12_2 = lv12_1;
			Predicate<LivingEntity> lv12_3 = (l2_0) -> {
				return true;
			};
			Predicate<LivingEntity> lv12_4 = (l2_0) -> {
				return true;
			};
			return new Faction().setBookDetails(lv12_0).setCanReceiveBountyCheck(lv12_3).setCanReceiveLoyaltyCheck(lv12_4).addCanHurtCheck(lv12_2).setRanks(RevolutionaryRank.class).setFlagBackgroundColor(11403264);
		};
		REVOLUTIONARY_ARMY = ModRegistry.registerFaction("revolutionary_army","Revolutionary Army",var4);
		Supplier<Faction> var5 = () -> {
			BiPredicate<LivingEntity, LivingEntity> lv15_0 = (l2_0, l2_1) -> {
				EntityStatsData lv13_2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(l2_0))).orElse((Object)(null))));
				EntityStatsData lv13_3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(l2_1))).orElse((Object)(null))));
				if (((lv13_2) != (null)) && ((lv13_3) != (null))){
					if ((lv13_2.isBandit()) && (lv13_3.isBandit())){
						return false;
					}else{
						return true;
					}
				}else{
					return true;
				}
			};
			BiPredicate<LivingEntity, LivingEntity> lv15_1 = lv15_0;
			Predicate<LivingEntity> lv15_2 = (l2_0) -> {
				return true;
			};
			return new Faction().addCanHurtCheck(lv15_1).setCanReceiveBountyCheck(lv15_2);
		};
		BANDIT = ModRegistry.registerFaction("bandit","Bandit",var5);
		Supplier<Faction> var6 = () -> {
			return new Faction().setFlagBackgroundColor(15398380);
		};
		WORLD_GOVERNMENT = ModRegistry.registerFaction("world_government","World Government",var6);
	}
}