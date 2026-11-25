package xyz.pixelatedw.mineminenomi.api.challenges;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.items.components.RewardData;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.triggers.CompleteChallengeTrigger;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public abstract class Challenge {
	// Fields
	private static final int MAX_BONUS_POINTS = 3;
	private static float[] TOTAl_SCALE;
	private final ChallengeCore<?> core;
	private int completions;
	private int bestTime;
	private int bonusPoints;

	private static void calcTotalScale() {
		ChallengeDifficulty var3 = null;
		int var0 = ChallengeDifficulty.values().length;
		TOTAl_SCALE = new float[var0];
		int var1 = 0;
		do{
			if ((var1) < (var0)){
				ChallengeDifficulty var2 = ChallengeDifficulty.values()[var1];
				var3 = var2;
				final ChallengeDifficulty var3_f1 = var3;
				Predicate var4 = (Predicate) ((Predicate<ChallengeCore>) ((l0) -> {
			return l0.getDifficulty().equals(var3_f1);
		}));
				BinaryOperator<Float> var5 = (Float l0, Float l1) -> {
			return Float.valueOf((l0.floatValue()) + (l1.floatValue()));
		};
				TOTAl_SCALE[var1] = ((Float)(((java.util.stream.Stream)(WyRegistry.CHALLENGES.stream().filter(var4).map((Function)(((Function<ChallengeCore, Float>)(ChallengeCore::getRewardsFactor)))))).reduce((Object)(Float.valueOf(0F)),(BinaryOperator)(var5)))).floatValue();
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static float getTotalScale(ChallengeDifficulty var0) {
		if ((TOTAl_SCALE) == (null)){
			calcTotalScale();
		}
		return TOTAl_SCALE[var0.ordinal()];
	}
	public Challenge(ChallengeCore<?> var1) {
		this.core = var1;
	}
	public String getCategory() {
		return this.core.getCategory();
	}
	public void resetBestTime() {
		this.bestTime = 0;
	}
	public void tryUpdateBestTime(int var1) {
		if ((this.getBestTimeTick()) == (0)){
			this.bestTime = var1;
			return;
		}else{
			if (this.isPersonalBest(var1)){
				this.bestTime = var1;
			}
			return;
		}
	}
	public boolean isPersonalBest(int var1) {
		int var2 = this.getBestTimeTick();
		if (((var2) > (0)) && ((var1) < (var2))){
			return true;
		}else{
			return false;
		}
	}
	public int getBestTimeTick() {
		return this.bestTime;
	}
	public String getFormattedBestTime() {
		return WyHelper.formatTimeMMSS((long)(this.bestTime));
	}
	public int getBonusPoints() {
		if (this.core.getDifficulty().equals(ChallengeDifficulty.STANDARD)){
			return 0;
		}else{
			return this.bonusPoints;
		}
	}
	public float getBonusMultiplier() {
		if (this.core.getDifficulty().equals(ChallengeDifficulty.STANDARD)){
			return 0F;
		}else{
			switch (this.bonusPoints){
			case 1:

			case 2:

			case 3:

			default:
				return 0F;
			}
		}
	}
	public boolean increaseBonus() {
		if ((this.bonusPoints) >= (3)){
			return false;
		}else{
			this.bonusPoints = (this.bonusPoints) + (1);
			return true;
		}
	}
	public void resetBonus() {
		this.bonusPoints = 0;
	}
	public void setComplete(Player var1, boolean var2) {
		if (var2){
			this.complete(var1);
		}else{
			this.completions = 0;
		}
		this.resetBonus();
	}
	public void complete(Player var1) {
		this.completions = (this.completions) + (1);
		if ((this.isFirstCompletion()) && (var1 instanceof ServerPlayer)){
			ServerPlayer var2 = ((ServerPlayer)(var1));
			((CompleteChallengeTrigger)(ModAdvancements.COMPLETE_CHALLENGE.get())).trigger(var2,this.core);
		}
	}
	public boolean isFirstCompletion() {
		return (this.completions) == (1);
	}
	public boolean isComplete() {
		return (this.completions) > (0);
	}
	public int getCompletions() {
		return this.completions;
	}
	public ChallengeCore<?> getCore() {
		return (ChallengeCore<?>) (ChallengeCore) (this.core);
	}
	@Nullable()
	public String getRewards(Player var1) {
		EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		HakiData var3 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if (((var2) != (null)) && ((var3) != (null))){
			int var11 = 0;
			int var12 = 0;
			ArrayList var4 = Lists.newArrayList();
			if ((this.core.getRewards()) != (null)){
				LootTable var5 = var1.level().getServer().reloadableRegistries().getLootTable(this.core.getRewards());
				if ((var5) == (LootTable.EMPTY)){
					String var6 = String.valueOf((Object)(this.core.getRewards()));
					String var7 = var6 + " reward could not be found.";
					MineMineNoMi.LOGGER.warn(var7);
					return null;
				}else{
					var4.addAll((Collection)(var5.getRandomItems(new LootParams.Builder(((ServerLevel)(var1.level()))).withParameter(LootContextParams.THIS_ENTITY,var1).withParameter(ModLootTypes.COMPLETED_CHALLENGE,this.core).withLuck((1F) + ((float)(this.bonusPoints))).create(LootContextParamSets.EMPTY))));
				}
			}
			StringBuilder var5_1 = new StringBuilder();
			var5_1.append("\n§aRewards");
			if (((float)(this.bonusPoints)) > (0F)){
				int var6_1 = this.bonusPoints;
				String var7_1 = " (+" + var6_1 + ")";
				var5_1.append(var7_1);
			}
			var5_1.append("§r\n");
			boolean var6_2 = false;
			if (((this.completions) != (0)) && ((this.core.getDifficulty()) != (ChallengeDifficulty.HARD))){

			}else{
				var6_2 = true;
			}
			float var13 = 0.0F;
			if (var6_2){
				float var7_2 = (this.core.getRewardsFactor()) / (getTotalScale(this.core.getDifficulty()));
				float var8 = (var7_2) * ((float)(ServerConfig.getDorikiRewardPoolForDifficulty(this.core.getDifficulty())));
				float var9 = (var7_2) * ((float)(ServerConfig.getBellyRewardPoolForDifficulty(this.core.getDifficulty())));
				float var10 = (var7_2) * ((float)(ServerConfig.getHakiRewardPoolForDifficulty(this.core.getDifficulty())));
				var11 = Math.round((float)((this.scaleValueWithRounding(var8)) + (this.roundValue((var8) * (this.getBonusMultiplier())))));
				var12 = Math.round((float)((this.scaleValueWithRounding(var9)) + (this.roundValue((var9) * (this.getBonusMultiplier())))));
				var13 = (this.scaleValueFromCompletions(var10)) + ((var10) * (this.getBonusMultiplier()));
				var13 = ((float)(Math.round((var13) * (100F)))) / (100F);
				if ((var11) > (0)){
					var2.alterDoriki((double)(var11),StatChangeSource.CHALLENGE);
					int var14 = var11;
					String var15 = "  " + var14 + " Doriki\n";
					var5_1.append(var15);
				}
				if ((var12) > (0)){
					var2.alterBelly((long)(var12),StatChangeSource.CHALLENGE);
					int var14_1 = var12;
					String var15_1 = "  " + var14_1 + " Belly\n";
					var5_1.append(var15_1);
				}
				if ((var13) > (0F)){
					if (var1.getRandom().nextBoolean()){
						var3.alterBusoshokuHakiExp(var13,StatChangeSource.CHALLENGE);
						float var14_2 = var13;
						String var15_2 = "  " + var14_2 + " Busoshoku Haki Experience\n";
						var5_1.append(var15_2);
					}else{
						var3.alterKenbunshokuHakiExp(var13,StatChangeSource.CHALLENGE);
						float var14_3 = var13;
						String var15_3 = "  " + var14_3 + " Kenbunshoku Haki Experience\n";
						var5_1.append(var15_3);
					}
				}
			}
			Iterator var7_3 = var4.iterator();
			LOOP_1:
			do{
				if (var7_3.hasNext()){
					ItemStack var8_1 = ((ItemStack)(var7_3.next()));
					RewardData var9_1 = ((RewardData)(var8_1.get(ModDataComponents.REWARDS)));
					if ((var9_1) != (null)){
						int var10_1 = var9_1.doriki();
						var11 = var9_1.belly();
						var12 = var9_1.extol();
						int var13_1 = var9_1.bounty();
						int var14_4 = var9_1.busoExp();
						int var15_4 = var9_1.kenExp();
						if ((var10_1) > (0)){
							int var16 = var10_1;
							String var17 = "  " + var16 + " Doriki\n";
							var5_1.append(var17);
						}
						if ((var11) > (0)){
							int var16_1 = var11;
							String var17_1 = "  " + var16_1 + " Belly\n";
							var5_1.append(var17_1);
						}
						if ((var12) > (0)){
							int var16_2 = var12;
							String var17_2 = "  " + var16_2 + " Extol\n";
							var5_1.append(var17_2);
						}
						if ((var13_1) > (0)){
							int var16_3 = var13_1;
							String var17_3 = "  " + var16_3 + " Bounty\n";
							var5_1.append(var17_3);
						}
						if ((var13_1) > (0)){
							int var16_4 = var13_1;
							String var17_4 = "  " + var16_4 + " Bounty\n";
							var5_1.append(var17_4);
						}
						if ((var14_4) > (0)){
							int var16_5 = var14_4;
							String var17_5 = "  " + var16_5 + " Busoshoku Haki Experience\n";
							var5_1.append(var17_5);
						}
						if ((var15_4) > (0)){
							int var16_6 = var15_4;
							String var17_6 = "  " + var16_6 + " Kenbunshoku Haki Experience\n";
							var5_1.append(var17_6);
						}
						Iterator var16_7 = var9_1.unlockedChallenges().iterator();
						do{
							if (var16_7.hasNext()){
								String var17_7 = ((String)(var16_7.next()));
								String var18 = var17_7;
								String var19 = "  New Challenge unlocked " + var18 + "\n";
								var5_1.append(var19);
								continue;
							}else{
								continue LOOP_1;
							}
						} while (true);
					}else{
						ItemStack var10_2 = var8_1.copy();
						int var11_1 = var10_2.getCount();
						String var12_1 = "" + var11_1 + " ";
						do{
							String var13_1_1 = ((var10_2.getCount()) > (1)) ? (var12_1) : ("");
							String var14_5 = var10_2.getHoverName().getString();
							String var15_5 = "  " + var13_1_1 + var14_5 + "\n";
							var5_1.append(var15_5);
							var1.addItem(var10_2);
							continue LOOP_1;
						} while (true);
					}
				}else{
					break;
				}
			} while (true);
			ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var1),(EntityStatsData)(var2)),var1);
			var5_1.append("\n");
			return var5_1.toString();
		}else{
			return null;
		}
	}
	public int roundValue(float var1) {
		if ((((var1) % (10F)) != (0F)) && ((var1) > (10F))){
			return WyHelper.roundToNiceNumber(var1);
		}else{
			return (int)(var1);
		}
	}
	public int scaleValueWithRounding(float var1) {
		var1 = this.scaleValueFromCompletions(var1);
		var1 = (float)(this.roundValue(var1));
		return (int)(var1);
	}
	public float scaleValueFromCompletions(float var1) {
		int var2 = this.getCompletions();
		var2 = Math.min(var2,10);
		if ((var2) > (0)){
			var1 = (var1) / (((float)(var2)) * (1.5F));
		}
		return var1;
	}
	public CompoundTag save(CompoundTag var1) {
		var1.putInt("completions",this.completions);
		var1.putInt("bestTime",this.bestTime);
		var1.putInt("bonusPoints",this.bonusPoints);
		return var1;
	}
	public void load(CompoundTag var1) {
		this.completions = var1.getInt("completions");
		this.bestTime = var1.getInt("bestTime");
		this.bonusPoints = var1.getInt("bonusPoints");
	}
	public boolean equals(Object var1) {
		if ((var1 instanceof ChallengeCore) && (((java.lang.Object)(((ChallengeCore)(var1)))).equals((Object)(this.getCore())))){
			return true;
		}else{
			if (var1 instanceof Challenge){
				Challenge var2 = ((Challenge)(var1));
				if (((this.getCore()) != (null)) && ((var2.getCore()) != (null))){
					if (((java.lang.Object)(this.getCore())).equals((Object)(var2.getCore()))){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
	}
	static  {
		TOTAl_SCALE = null;
	}
}