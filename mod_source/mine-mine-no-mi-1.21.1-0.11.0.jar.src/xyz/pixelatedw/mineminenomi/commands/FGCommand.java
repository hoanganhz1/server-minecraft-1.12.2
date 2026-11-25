package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.haki.HaoshokuHakiInfusionAbility;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.commands.FruitArgument;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.CustomSpawnerBlockEntity;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AbilityIconItem;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.HeartItem;
import xyz.pixelatedw.mineminenomi.items.StrawDollItem;
import xyz.pixelatedw.mineminenomi.items.WantedPosterItem;
import xyz.pixelatedw.mineminenomi.world.DynamicDimensionManager;

public class FGCommand {
	// Fields
	public static boolean NO_COOLDOWN;
	public static boolean SHOW_TPS;
	public static boolean IGNORE_STRUCTURE_SIZE;
	public static int ANIM_SPEED;
	public static boolean SHOW_DEBUG_ALL;
	public static boolean SHOW_DEBUG_IN_CHAT;
	public static boolean SHOW_DEBUG_DAMAGE;
	public static boolean SHOW_DEBUG_REWARDS;
	public static boolean SHOW_DEBUG_STATS;
	public static boolean SHOW_DEBUG_FRIENDLY;
	public static boolean SHOW_NPC_ABILITY_LOG;
	public static boolean SHOW_PACKET_LOG;

	public static LiteralArgumentBuilder<CommandSourceStack> create(CommandBuildContext var0) {
		Predicate<CommandSourceStack> var1 = (l0) -> {
			return l0.hasPermission(3);
		};
		LiteralArgumentBuilder var2 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("fg"))).requires((Predicate)(var1))));
		Command var3 = (l0) -> {
			return runTest(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		var2.then(Commands.literal("test").executes(var3));
		Command var4 = (l0) -> {
			ServerPlayer lv3_1 = ((CommandSourceStack)(l0.getSource())).getPlayerOrException();
			AbilityData lv3_2 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(lv3_1)))).orElse((Object)(null))));
			if ((lv3_2) == (null)){
				return -1;
			}else{
				Iterator lv3_3 = lv3_2.getNodes().iterator();
				do{
					if (lv3_3.hasNext()){
						((AbilityNode)(lv3_3.next())).lockNode((LivingEntity)(lv3_1));
						continue;
					}else{
						break;
					}
				} while (true);
				return 1;
			}
		};
		LiteralArgumentBuilder var5 = ((LiteralArgumentBuilder)(Commands.literal("reset").then(Commands.literal("nodes").executes(var4))));
		Command var6 = (l0) -> {
			ServerPlayer lv5_1 = ((CommandSourceStack)(l0.getSource())).getPlayerOrException();
			Consumer<AbilityData> lv5_2 = (l2_0) -> {
				Objects.requireNonNull(l2_0);
				l2_0.getUnlockedAbilities().stream().map((Function)(((Function<AbilityCoreUnlockWrapper, AbilityCore>)(AbilityCoreUnlockWrapper::getAbilityCore)))).forEach((Consumer)(((Consumer<AbilityCore>)(l2_0::removeUnlockedAbility))));
			};
			((java.util.Optional)(AbilityData.get((LivingEntity)(lv5_1)))).ifPresent((Consumer)(lv5_2));
			return 1;
		};
		LiteralArgumentBuilder var7 = ((LiteralArgumentBuilder)(var5.then(Commands.literal("abilities").executes(var6))));
		Command var8 = (l0) -> {
			ServerPlayer lv7_1 = ((CommandSourceStack)(l0.getSource())).getPlayerOrException();
			Consumer<EntityStatsData> lv7_2 = (l2_0) -> {
				l2_0.setFaction((Faction)(null));
				l2_0.setRace((Race)(null));
				l2_0.setFightingStyle((FightingStyle)(null));
			};
			((java.util.Optional)(EntityStatsData.get((LivingEntity)(lv7_1)))).ifPresent((Consumer)(lv7_2));
			return 1;
		};
		LiteralArgumentBuilder var9 = ((LiteralArgumentBuilder)(var7.then(Commands.literal("selection").executes(var8))));
		Command var10 = (l0) -> {
			ServerPlayer lv9_1 = ((CommandSourceStack)(l0.getSource())).getPlayerOrException();
			Consumer<EntityStatsData> lv9_2 = (l2_0) -> {
				l2_0.setUltraCola(0);
			};
			((java.util.Optional)(EntityStatsData.get((LivingEntity)(lv9_1)))).ifPresent((Consumer)(lv9_2));
			return 1;
		};
		LiteralArgumentBuilder var11 = ((LiteralArgumentBuilder)(var9.then(Commands.literal("ultra_cola").executes(var10))));
		Command var12 = (l0) -> {
			EventsWorldData.get().wipeAllEvents();
			return 1;
		};
		LiteralArgumentBuilder var13 = ((LiteralArgumentBuilder)(var11.then(Commands.literal("events").executes(var12))));
		Command var14 = (l0) -> {
			ExtendedWorldData.get().resetNewWorld();
			return 1;
		};
		LiteralArgumentBuilder var15 = ((LiteralArgumentBuilder)(var13.then(Commands.literal("new_world").executes(var14))));
		Command var16 = (l0) -> {
			ServerPlayer lv12_1 = ((CommandSourceStack)(l0.getSource())).getPlayerOrException();
			Iterator lv12_2 = ((ChallengeData)(ChallengeData.get((Player)(lv12_1)).get())).getChallenges().iterator();
			do{
				if (lv12_2.hasNext()){
					((Challenge)(lv12_2.next())).setComplete((Player)(lv12_1),false);
					continue;
				}else{
					break;
				}
			} while (true);
			return 1;
		};
		var2.then(var15.then(Commands.literal("challenge_completions").executes(var16)));
		Command var17 = (l0) -> {
			SHOW_DEBUG_IN_CHAT = !(SHOW_DEBUG_IN_CHAT);
			return 1;
		};
		LiteralArgumentBuilder var18 = ((LiteralArgumentBuilder)(Commands.literal("show").then(Commands.literal("debug_in_chat").executes(var17))));
		Command var19 = (l0) -> {
			SHOW_DEBUG_DAMAGE = !(SHOW_DEBUG_DAMAGE);
			return 1;
		};
		LiteralArgumentBuilder var20 = ((LiteralArgumentBuilder)(var18.then(Commands.literal("damage").executes(var19))));
		Command var21 = (l0) -> {
			SHOW_DEBUG_REWARDS = !(SHOW_DEBUG_REWARDS);
			return 1;
		};
		LiteralArgumentBuilder var22 = ((LiteralArgumentBuilder)(var20.then(Commands.literal("rewards").executes(var21))));
		Command var23 = (l0) -> {
			SHOW_DEBUG_STATS = !(SHOW_DEBUG_STATS);
			return 1;
		};
		LiteralArgumentBuilder var24 = ((LiteralArgumentBuilder)(var22.then(Commands.literal("spawn_stats").executes(var23))));
		Command var25 = (l0) -> {
			SHOW_DEBUG_FRIENDLY = !(SHOW_DEBUG_FRIENDLY);
			return 1;
		};
		LiteralArgumentBuilder var26 = ((LiteralArgumentBuilder)(var24.then(Commands.literal("friendly").executes(var25))));
		Command var27 = (l0) -> {
			SHOW_NPC_ABILITY_LOG = !(SHOW_NPC_ABILITY_LOG);
			return 1;
		};
		LiteralArgumentBuilder var28 = ((LiteralArgumentBuilder)(var26.then(Commands.literal("npc_ability_log").executes(var27))));
		Command var29 = (l0) -> {
			SHOW_PACKET_LOG = !(SHOW_PACKET_LOG);
			return 1;
		};
		LiteralArgumentBuilder var30 = ((LiteralArgumentBuilder)(var28.then(Commands.literal("packet_log").executes(var29))));
		Command var31 = (l0) -> {
			SHOW_DEBUG_ALL = !(SHOW_DEBUG_ALL);
			SHOW_DEBUG_IN_CHAT = SHOW_DEBUG_ALL;
			SHOW_DEBUG_DAMAGE = SHOW_DEBUG_ALL;
			SHOW_DEBUG_REWARDS = SHOW_DEBUG_ALL;
			SHOW_DEBUG_STATS = SHOW_DEBUG_ALL;
			SHOW_DEBUG_FRIENDLY = SHOW_DEBUG_ALL;
			SHOW_NPC_ABILITY_LOG = SHOW_DEBUG_ALL;
			SHOW_PACKET_LOG = SHOW_DEBUG_ALL;
			return 1;
		};
		var2.then(var30.then(Commands.literal("all").executes(var31)));
		Command var32 = (l0) -> {
			ServerPlayer lv21_1 = ((CommandSourceStack)(l0.getSource())).getPlayer();
			Holder lv21_2 = lv21_1.level().getBiome(lv21_1.blockPosition());
			Biome lv21_3 = ((Biome)(lv21_2.value()));
			ResourceLocation lv21_4 = ((ResourceLocation)(lv21_2.unwrapKey().map((Function)(((Function<ResourceKey, ResourceLocation>)(ResourceKey::location)))).orElse(null)));
			MobSpawnSettings lv21_5 = lv21_3.getMobSettings();
			StringBuilder lv21_6 = new StringBuilder();
			lv21_6.append((Object)(lv21_4)).append(System.lineSeparator());
			WeightedRandomList lv21_7 = lv21_5.getMobs(MobCategory.CREATURE);
			float lv21_8 = (float)(WeightedRandom.getTotalWeight(lv21_7.unwrap()));
			Iterator lv21_9 = lv21_7.unwrap().iterator();
			do{
				if (lv21_9.hasNext()){
					MobSpawnSettings.SpawnerData lv21_10 = ((MobSpawnSettings.SpawnerData)(lv21_9.next()));
					EntityType lv21_11 = lv21_10.type;
					int lv21_12 = lv21_10.getWeight().asInt();
					String lv21_13 = String.format("%.2f",new Object[]{Double.valueOf(((double)(((float)(lv21_12)) / (lv21_8))) * (100D))});
					String lv21_14 = String.valueOf((Object)(lv21_11));
					int lv21_15 = lv21_12;
					String lv21_16 = lv21_13;
					String lv21_17 = lv21_14 + " | " + lv21_15 + " | " + lv21_16 + "%";
					lv21_6.append(lv21_17).append(System.lineSeparator());
					continue;
				}else{
					break;
				}
			} while (true);
			WyDebug.debug(lv21_6.toString());
			return 1;
		};
		var2.then(Commands.literal("check_spawns").executes(var32));
		Command var33 = (l0) -> {
			Entity lv22_1 = EntityArgument.getEntity(l0,"target");
			if (lv22_1 instanceof LivingEntity){
				LivingEntity lv22_2 = ((LivingEntity)(lv22_1));
				EntityStatsData lv22_3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(lv22_2))).orElse((Object)(null))));
				if ((lv22_3) == (null)){
					return -1;
				}else{
					lv22_3.setHeart(true);
					ItemStack lv22_4 = HeartItem.createHeartStack(lv22_2);
					((CommandSourceStack)(l0.getSource())).getPlayerOrException().addItem(lv22_4);
					lv22_3.setHeart(false);
					lv22_3.setStrawDoll(true);
					ItemStack lv22_5 = StrawDollItem.createDollStack(lv22_2);
					((CommandSourceStack)(l0.getSource())).getPlayerOrException().addItem(lv22_5);
					lv22_3.setStrawDoll(false);
				}
			}
			return 1;
		};
		var2.then(Commands.literal("get_target_soulbounds").then(Commands.argument("target",(ArgumentType)(EntityArgument.entity())).executes(var33)));
		Command var34 = (l0) -> {
			ServerPlayer lv24_1 = ((CommandSourceStack)(l0.getSource())).getPlayerOrException();
			try{
				String lv24_2 = lv24_1.getStringUUID();
				String lv24_3 = "challenges_" + lv24_2;
				ResourceLocation lv24_4 = ResourceLocation.fromNamespaceAndPath("mineminenomi",lv24_3);
				ResourceKey lv24_5 = ResourceKey.create(Registries.DIMENSION,lv24_4);
				ServerPlayer lv24_6 = lv24_1;
				final ServerPlayer lv24_6_f1 = lv24_6;
				Consumer<ServerLevel> lv24_7 = (l2_0) -> {
				lv24_6_f1.teleportTo(l2_0,0D,0D,0D,0F,0F);
			};
				DynamicDimensionManager.INSTANCE.queueLevelForRegistration(lv24_5,lv24_7);
			}catch(Exception lv24_2_1){
				lv24_2_1.printStackTrace();
			}
			return 1;
		};
		var2.then(Commands.literal("tp_to_challenge").executes(var34));
		Command var35 = (l0) -> {
			try{
				int lv26_1 = 0;
				do{
					if ((lv26_1) < (20)){
						UUID lv26_2 = UUID.randomUUID();
						String lv26_3 = String.valueOf((Object)(lv26_2));
						String lv26_4 = "challenges_" + lv26_3;
						ResourceLocation lv26_5 = ResourceLocation.fromNamespaceAndPath("mineminenomi",lv26_4);
						ResourceKey lv26_6 = ResourceKey.create(Registries.DIMENSION,lv26_5);
						Consumer<ServerLevel> lv26_7 = (l2_0) -> {
			};
						DynamicDimensionManager.INSTANCE.queueLevelForRegistration(lv26_6,lv26_7);
						lv26_1++;
						continue;
					}else{
						break;
					}
				} while (true);
			}catch(Exception lv26_1_1){
				lv26_1_1.printStackTrace();
			}
			return 1;
		};
		var2.then(Commands.literal("simulate_challenges").executes(var35));
		Command var36 = (l0) -> {
			NO_COOLDOWN = !(NO_COOLDOWN);
			String lv27_1 = (NO_COOLDOWN) ? ("Enabled") : ("Disabled");
			String lv27_2 = lv27_1 + " No Cooldown Mode";
			((CommandSourceStack)(l0.getSource())).getPlayerOrException().sendSystemMessage((Component)(Component.literal(lv27_2)));
			return 1;
		};
		var2.then(Commands.literal("no_cooldowns").executes(var36));
		Command var37 = (l0) -> {
			IGNORE_STRUCTURE_SIZE = !(IGNORE_STRUCTURE_SIZE);
			String lv28_1 = (IGNORE_STRUCTURE_SIZE) ? ("Enabled") : ("Disabled");
			String lv28_2 = lv28_1 + " Ignore Structure Size";
			((CommandSourceStack)(l0.getSource())).getPlayerOrException().sendSystemMessage((Component)(Component.literal(lv28_2)));
			return 1;
		};
		var2.then(Commands.literal("ignore_structure_size").executes(var37));
		Command var38 = (l0) -> {
			ServerPlayer lv29_1 = ((CommandSourceStack)(l0.getSource())).getPlayerOrException();
			ItemStack lv29_2 = WantedPosterItem.getWantedPosterStack((Player)(lv29_1),100000L);
			lv29_1.level().addFreshEntity((Entity)(new ItemEntity(lv29_1.level(),(double)(lv29_1.blockPosition().getX()),(double)((lv29_1.blockPosition().getY()) + (1)),(double)(lv29_1.blockPosition().getZ()),lv29_2)));
			return 1;
		};
		var2.then(Commands.literal("get_poster").executes(var38));
		Command var39 = (l0) -> {
			return giveRandomClue(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException(),FruitArgument.getFruit(l0,"fruit"));
		};
		var2.then(Commands.literal("clue").then(Commands.argument("fruit",(ArgumentType)(FruitArgument.fruit())).executes(var39)));
		Command var40 = (l0) -> {
			ServerPlayer lv31_1 = ((CommandSourceStack)(l0.getSource())).getPlayerOrException();
			ItemStack lv31_2 = Items.BOOK.getDefaultInstance();
			lv31_2.set(ModDataComponents.IS_ANCIENT_MANUAL,(Object)(Boolean.valueOf(true)));
			lv31_2.set(DataComponents.CUSTOM_NAME,(Object)(ModI18n.ITEM_ANCIENT_MANUAL));
			lv31_1.level().addFreshEntity((Entity)(new ItemEntity(lv31_1.level(),(double)(lv31_1.blockPosition().getX()),(double)((lv31_1.blockPosition().getY()) + (1)),(double)(lv31_1.blockPosition().getZ()),lv31_2)));
			return 1;
		};
		var2.then(Commands.literal("get_ancient_manual").executes(var40));
		Command var41 = (l0) -> {
			ServerLevel lv32_1 = ((CommandSourceStack)(l0.getSource())).getLevel();
			Vec3 lv32_2 = ((CommandSourceStack)(l0.getSource())).getPosition();
			BlockPos lv32_3 = BlockPos.containing(lv32_2.x,lv32_2.y,lv32_2.z);
			lv32_1.setBlock(lv32_3,((Block)(ModBlocks.CUSTOM_SPAWNER.get())).defaultBlockState(),3);
			BlockEntity lv32_4 = lv32_1.getBlockEntity(lv32_3);
			if (((lv32_4) != (null)) && (lv32_4 instanceof CustomSpawnerBlockEntity)){
				CustomSpawnerBlockEntity lv32_5 = ((CustomSpawnerBlockEntity)(lv32_4));
				EntityType lv32_6 = ((EntityType)(ResourceArgument.getSummonableEntityType(l0,"spawn").value()));
				int lv32_7 = IntegerArgumentType.getInteger(l0,"limit");
				lv32_5.setSpawnerMob(lv32_6);
				lv32_5.setSpawnerLimit(lv32_7);
			}
			return 1;
		};
		var2.then(Commands.literal("spawner").then(Commands.argument("spawn",(ArgumentType)(ResourceArgument.resource(var0,Registries.ENTITY_TYPE))).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).then(Commands.argument("limit",(ArgumentType)(IntegerArgumentType.integer())).executes(var41))));
		return var2;
	}
	private static int giveRandomClue(CommandContext<CommandSourceStack> var0, ServerPlayer var1, @Nullable() AkumaNoMiItem var2) {
		AkumaNoMiItem var3 = var2;
		if ((var2) == (null)){
			var3 = ((AkumaNoMiItem)(((java.util.Optional)(WyHelper.shuffle(new ArrayList((Collection)(ModValues.DEVIL_FRUITS))).stream().findFirst())).orElse((Object)(null))));
		}
		if ((var3) == (null)){
			return 1;
		}else{
			DFEncyclopediaEntry var4 = DFEncyclopediaEntry.getClue(var1.level(),var3);
			if ((var4) == (null)){
				return 1;
			}else{
				ItemStack var5 = new ItemStack((ItemLike)(Items.PAPER));
				var5.set(ModDataComponents.FRUIT_CLUE,(Object)(var4));
				var5.set(DataComponents.CUSTOM_NAME,(Object)(ModI18n.ITEM_FRUIT_CLUE));
				var5.setCount(1);
				var1.addItem(var5);
				return -1;
			}
		}
	}
	private static int runTest(CommandContext<CommandSourceStack> var0, ServerPlayer var1) {
		ItemStack var2 = AbilityIconItem.getIcon(HaoshokuHakiInfusionAbility.INSTANCE);
		var1.getInventory().add(var2);
		return 1;
	}
	static  {
		NO_COOLDOWN = false;
		SHOW_TPS = false;
		IGNORE_STRUCTURE_SIZE = false;
		ANIM_SPEED = 8;
		SHOW_DEBUG_ALL = false;
		SHOW_DEBUG_IN_CHAT = false;
		SHOW_DEBUG_DAMAGE = false;
		SHOW_DEBUG_REWARDS = false;
		SHOW_DEBUG_STATS = false;
		SHOW_DEBUG_FRIENDLY = false;
		SHOW_NPC_ABILITY_LOG = false;
		SHOW_PACKET_LOG = false;
	}
}