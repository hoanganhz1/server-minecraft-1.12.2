package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaBackpackBonusAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.events.stats.BountyEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.stats.BountyEvent$Pre;
import xyz.pixelatedw.mineminenomi.api.events.stats.CurrencyEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.stats.CurrencyEvent$Pre;
import xyz.pixelatedw.mineminenomi.api.events.stats.DorikiEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.stats.DorikiEvent$Pre;
import xyz.pixelatedw.mineminenomi.api.events.stats.LoyaltyEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.stats.LoyaltyEvent$Pre;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.triggers.FreedSlavesTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainBellyTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainBountyTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainDorikiTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.ObtainLoyaltyTrigger;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SUpdateRogueStateEventPacket;

public class EntityStatsData implements INBTSerializable<CompoundTag> {
	// Fields
	public int version;
	private final LivingEntity owner;
	private double doriki;
	private int cola;
	private int ultraCola;
	private double loyalty;
	private int invulnerableTime;
	private Vec3 flyingSpeed;
	private long bounty;
	private long belly;
	private long extol;
	private Optional<Faction> faction;
	private Optional<Race> race;
	private Optional<Race> subRace;
	private Optional<FightingStyle> fightingStyle;
	private boolean hasShadow;
	private boolean hasHeart;
	private boolean inCombatMode;
	private boolean hasStrawDoll;
	private boolean isRogue;
	private double damageMultiplier;
	private int jumpTicks;
	private boolean isJumping;
	private float leftImpulse;
	private float forwardImpulse;
	private boolean hadChiyuEffect;
	private float originalChiyupopoHealth;
	private float allyInflictedInvisibilityCooldown;
	private int freedSlaves;
	private Optional<Integer> skinTint;
	private Deque<ResourceLocation> screenShaders;
	private boolean dyeLayersCheck;
	private final EnumMap<TrainingPointType, Integer> trainingPoints;
	private int spentTrainingPoints;

	public static EntityStatsData createCapability(IAttachmentHolder var0) {
		return new EntityStatsData(((LivingEntity)(var0)));
	}
	public static Optional<EntityStatsData> get(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.STATS))){
			var0.setData(ModAttachments.STATS,new EntityStatsData(var0));
		}
		return (Optional<EntityStatsData>) (Optional) (var0.getExistingData(ModAttachments.STATS));
	}
	public static EntityStatsData getOrCreate(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.STATS))){
			var0.setData(ModAttachments.STATS,new EntityStatsData(var0));
		}
		return ((EntityStatsData)(var0.getData(ModAttachments.STATS)));
	}
	private EntityStatsData(LivingEntity var1) {
		this.version = 0;
		this.cola = 100;
		this.ultraCola = 0;
		this.loyalty = 0D;
		this.invulnerableTime = 0;
		this.flyingSpeed = Vec3.ZERO;
		this.bounty = 0L;
		this.belly = 0L;
		this.extol = 0L;
		this.faction = (Optional) (((java.util.Optional)(Optional.empty())));
		this.race = (Optional) (((java.util.Optional)(Optional.empty())));
		this.subRace = (Optional) (((java.util.Optional)(Optional.empty())));
		this.fightingStyle = (Optional) (((java.util.Optional)(Optional.empty())));
		this.hasShadow = true;
		this.hasHeart = true;
		this.inCombatMode = false;
		this.hasStrawDoll = true;
		this.isRogue = false;
		this.damageMultiplier = 1D;
		this.jumpTicks = 0;
		this.isJumping = false;
		this.leftImpulse = 0F;
		this.forwardImpulse = 0F;
		this.hadChiyuEffect = false;
		this.originalChiyupopoHealth = 0F;
		this.allyInflictedInvisibilityCooldown = 0F;
		this.skinTint = (Optional) (((java.util.Optional)(Optional.empty())));
		this.screenShaders = new ArrayDeque();
		this.dyeLayersCheck = false;
		this.trainingPoints = (EnumMap) (new EnumMap((Class)(TrainingPointType.class)));
		this.spentTrainingPoints = 0;
		this.owner = var1;
	}
	public double getDoriki() {
		return this.doriki;
	}
	public boolean alterDoriki(double var1, StatChangeSource var2) {
		LivingEntity var3 = this.owner;
		if (var3 instanceof Player){
			Player var4 = ((Player)(var3));
			DorikiEvent$Pre var5 = new DorikiEvent$Pre(var4,var1,var2);
			DorikiEvent$Pre var6 = ((DorikiEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(var5))));
			if (var6.isCanceled()){
				return false;
			}else{
				var1 = var6.getDoriki();
			}
		}
		this.doriki = Mth.clamp((this.doriki) + (var1),0D,(double)(ServerConfig.getDorikiLimit()));
		LivingEntity var4_1 = this.owner;
		if (var4_1 instanceof Player){
			Player var5_1 = ((Player)(var4_1));
			DorikiEvent$Post var6_1 = new DorikiEvent$Post(var5_1,var1,var2);
			NeoForge.EVENT_BUS.post((Event)(var6_1));
			LivingEntity var7 = this.owner;
			if (var7 instanceof ServerPlayer){
				ServerPlayer var8 = ((ServerPlayer)(var7));
				((ObtainDorikiTrigger)(ModAdvancements.OBTAIN_DORIKI.get())).trigger(var8,(int)(this.doriki));
			}
		}
		AttributeHelper.updateHPAttribute(this.owner);
		AttributeHelper.updateToughnessAttribute(this.owner);
		return true;
	}
	public void setDoriki(double var1) {
		this.doriki = Mth.clamp(var1,0D,(double)(ServerConfig.getDorikiLimit()));
	}
	public long getBelly() {
		return this.belly;
	}
	public boolean alterBelly(long var1, StatChangeSource var2) {
		LivingEntity var3 = this.owner;
		if (var3 instanceof Player){
			Player var4 = ((Player)(var3));
			CurrencyEvent$Pre var5 = new CurrencyEvent$Pre(var4,var1,Currency.BELLY,var2);
			CurrencyEvent$Pre var6 = ((CurrencyEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(var5))));
			if (var6.isCanceled()){
				return false;
			}else{
				var1 = var6.getAmount();
			}
		}
		this.belly = WyHelper.clamp((this.belly) + (var1),0L,999999999L);
		LivingEntity var4_1 = this.owner;
		if (var4_1 instanceof Player){
			Player var5_1 = ((Player)(var4_1));
			CurrencyEvent$Post var6_1 = new CurrencyEvent$Post(var5_1,var1,Currency.BELLY,var2);
			NeoForge.EVENT_BUS.post((Event)(var6_1));
			if (var5_1 instanceof ServerPlayer){
				ServerPlayer var7 = ((ServerPlayer)(var5_1));
				((ObtainBellyTrigger)(ModAdvancements.OBTAIN_BELLY.get())).trigger(var7,(int)(this.belly),false);
			}else{
				return true;
			}
		}
		return true;
	}
	public void setBelly(long var1) {
		this.belly = var1;
	}
	public long getExtol() {
		return this.extol;
	}
	public boolean alterExtol(long var1, StatChangeSource var2) {
		LivingEntity var3 = this.owner;
		if (var3 instanceof Player){
			Player var4 = ((Player)(var3));
			CurrencyEvent$Pre var5 = new CurrencyEvent$Pre(var4,var1,Currency.EXTOL,var2);
			CurrencyEvent$Pre var6 = ((CurrencyEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(var5))));
			if (var6.isCanceled()){
				return false;
			}else{
				var1 = var6.getAmount();
			}
		}
		this.extol = WyHelper.clamp((this.extol) + (var1),0L,999999999L);
		LivingEntity var4_1 = this.owner;
		if (var4_1 instanceof Player){
			Player var5_1 = ((Player)(var4_1));
			CurrencyEvent$Post var6_1 = new CurrencyEvent$Post(var5_1,var1,Currency.EXTOL,var2);
			NeoForge.EVENT_BUS.post((Event)(var6_1));
		}
		return true;
	}
	public void setExtol(long var1) {
		this.extol = var1;
	}
	public long getBounty() {
		return this.bounty;
	}
	public boolean alterBounty(long var1, StatChangeSource var2) {
		LivingEntity var3 = this.owner;
		if (var3 instanceof Player){
			Player var4 = ((Player)(var3));
			BountyEvent$Pre var5 = new BountyEvent$Pre(var4,var1,var2);
			BountyEvent$Pre var6 = ((BountyEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(var5))));
			if (var6.isCanceled()){
				return false;
			}else{
				var1 = var6.getBounty();
			}
		}
		this.bounty = WyHelper.clamp((this.bounty) + (var1),0L,100000000000L);
		LivingEntity var4_1 = this.owner;
		if (var4_1 instanceof Player){
			Player var5_1 = ((Player)(var4_1));
			BountyEvent$Post var6_1 = new BountyEvent$Post(var5_1,var1,var2);
			NeoForge.EVENT_BUS.post((Event)(var6_1));
			LivingEntity var7 = this.owner;
			if (var7 instanceof ServerPlayer){
				ServerPlayer var8 = ((ServerPlayer)(var7));
				((ObtainBountyTrigger)(ModAdvancements.OBTAIN_BOUNTY.get())).trigger(var8,(int)(this.bounty));
			}else{
				return true;
			}
		}
		return true;
	}
	public void setBounty(long var1) {
		this.bounty = var1;
	}
	public int getCola() {
		return this.cola;
	}
	public void alterCola(int var1) {
		this.cola = Mth.clamp((this.cola) + (var1),0,this.getMaxCola());
	}
	public void setCola(int var1) {
		this.cola = Mth.clamp(var1,0,this.getMaxCola());
	}
	public void updateCola() {
		this.cola = Mth.clamp(this.cola,0,this.getMaxCola());
	}
	public int getMaxCola() {
		int var1 = 100;
		var1 = (var1) + ((this.ultraCola) * (20));
		if ((this.owner) != (null)){
			Function<AbilityData, ColaBackpackBonusAbility> var2 = (l0) -> {
			return ((ColaBackpackBonusAbility)(l0.getPassiveAbility(((AbilityCore)(ColaBackpackBonusAbility.INSTANCE.get())))));
		};
			if (AbilityData.get(this.owner).map(var2).isPresent()){
				var1 = (var1) + (500);
			}
		}
		var1 = Mth.clamp(var1,0,1000);
		return var1;
	}
	public int getUltraCola() {
		return this.ultraCola;
	}
	public void setUltraCola(int var1) {
		this.ultraCola = Mth.clamp(var1,0,20);
	}
	public void addUltraCola(int var1) {
		this.ultraCola = Mth.clamp((this.ultraCola) + (var1),0,20);
	}
	public double getLoyalty() {
		return this.loyalty;
	}
	public boolean alterLoyalty(double var1, StatChangeSource var2) {
		LivingEntity var3 = this.owner;
		if (var3 instanceof Player){
			Player var4 = ((Player)(var3));
			LoyaltyEvent$Pre var5 = new LoyaltyEvent$Pre(var4,var1,var2);
			LoyaltyEvent$Pre var6 = ((LoyaltyEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(var5))));
			if (var6.isCanceled()){
				return false;
			}else{
				var1 = var6.getLoyalty();
			}
		}
		this.loyalty = Mth.clamp((this.loyalty) + (var1),-5D,100D);
		LivingEntity var4_1 = this.owner;
		if (var4_1 instanceof Player){
			Player var5_1 = ((Player)(var4_1));
			LoyaltyEvent$Post var6_1 = new LoyaltyEvent$Post(var5_1,var1,var2);
			NeoForge.EVENT_BUS.post((Event)(var6_1));
			if ((var1) > (0D)){
				var5_1.displayClientMessage(ModI18n.INFO_LOYALTY_INCREASED,true);
				if (var5_1 instanceof ServerPlayer){
					((ObtainLoyaltyTrigger)(ModAdvancements.OBTAIN_LOYALTY.get())).trigger(((ServerPlayer)(this.owner)),(int)(this.loyalty));
				}else{
					return true;
				}
			}else{
				if ((var1) < (0D)){
					if ((this.loyalty) <= (-5D)){
						this.setFaction(((Faction)(ModFactions.PIRATE.get())));
						Object[] var7 = new Object[]{((Faction)(ModFactions.PIRATE.get())).getLabel().getString()};
						WyHelper.sendMessage((LivingEntity)(var5_1),(Component)(Component.translatable(ModI18n.INFO_FACTION_CHANGE,var7)));
					}else{
						WyHelper.sendMessage((LivingEntity)(var5_1),ModI18n.INFO_LOYALTY_DROPPED);
					}
				}else{
					return true;
				}
			}
		}
		return true;
	}
	public void setLoyalty(double var1) {
		this.loyalty = var1;
	}
	public int getInvulnerableTime() {
		return this.invulnerableTime;
	}
	public void alterInvulnerableTime(int var1) {
		this.invulnerableTime = Math.max(0,(this.invulnerableTime) + (var1));
	}
	public void setInvulnerableTime(int var1) {
		this.invulnerableTime = Math.max(var1,0);
	}
	public <E extends Enum<E> & IFactionRank> Optional<E> getRank() {
		if (this.faction.isPresent()){
			Faction var1 = ((Faction)(this.faction.get()));
			if ((var1.getRanks()) == (null)){
				return (Optional<E>) (Optional) (Optional.empty());
			}else{
				Enum[] var2 = var1.getRanks();
				if ((this.loyalty) <= (0D)){
					return (Optional<E>) (Optional) (Optional.of(var2[0]));
				}else{
					Enum[] var3 = var2;
					int var4 = var3.length;
					int var5 = 0;
					do{
						if ((var5) < (var4)){
							Enum var6 = var3[var5];
							if (((IFactionRank)(var6)).isUnlocked(this.owner)){
								return (Optional<E>) (Optional) (Optional.of(var6));
							}else{
								var5++;
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
			}
		}
		return (Optional<E>) (Optional) (Optional.empty());
	}
	public <E extends Enum<E> & IFactionRank> boolean hasRank(E var1) {
		Optional var2 = this.getRank();
		if (var2.isEmpty()){
			return false;
		}else{
			return (((Enum)(var2.get())).ordinal()) >= (var1.ordinal());
		}
	}
	public boolean isPirate() {
		Faction var1 = ((Faction)(ModFactions.PIRATE.get()));
		Objects.requireNonNull(var1);
		return ((Boolean)(this.faction.map((Function)(((Function<Faction, Boolean>)(var1::equals)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public boolean isMarine() {
		Faction var1 = ((Faction)(ModFactions.MARINE.get()));
		Objects.requireNonNull(var1);
		return ((Boolean)(this.faction.map((Function)(((Function<Faction, Boolean>)(var1::equals)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public boolean isBountyHunter() {
		Faction var1 = ((Faction)(ModFactions.BOUNTY_HUNTER.get()));
		Objects.requireNonNull(var1);
		return ((Boolean)(this.faction.map((Function)(((Function<Faction, Boolean>)(var1::equals)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public boolean isRevolutionary() {
		Faction var1 = ((Faction)(ModFactions.REVOLUTIONARY_ARMY.get()));
		Objects.requireNonNull(var1);
		return ((Boolean)(this.faction.map((Function)(((Function<Faction, Boolean>)(var1::equals)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public boolean isBandit() {
		Faction var1 = ((Faction)(ModFactions.BANDIT.get()));
		Objects.requireNonNull(var1);
		return ((Boolean)(this.faction.map((Function)(((Function<Faction, Boolean>)(var1::equals)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public boolean isWorldGovernment() {
		Faction var1 = ((Faction)(ModFactions.WORLD_GOVERNMENT.get()));
		Objects.requireNonNull(var1);
		return ((Boolean)(this.faction.map((Function)(((Function<Faction, Boolean>)(var1::equals)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public boolean isCivilian() {
		Faction var1 = ((Faction)(ModFactions.CIVILIAN.get()));
		Objects.requireNonNull(var1);
		return ((Boolean)(this.faction.map((Function)(((Function<Faction, Boolean>)(var1::equals)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public boolean hasFaction() {
		return this.faction.isPresent();
	}
	public void setFaction(@Nullable() Faction var1) {
		this.faction = (Optional) (Optional.ofNullable(var1));
	}
	public Optional<Faction> getFaction() {
		return this.faction;
	}
	public boolean isHuman() {
		Function<DevilFruitData, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.HITO_HITO_NO_MI));
		};
		boolean var2 = ((Boolean)(((java.util.Optional)(DevilFruitData.get(this.owner))).map((Function)(var1)).orElse(Boolean.valueOf(false)))).booleanValue();
		Function<Race, Boolean> var3 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModRaces.HUMAN.get()));
		};
		boolean var4 = ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.race)).map((Function)(var3)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
		return (var2) || (var4);
	}
	public boolean isFishman() {
		Function<Race, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModRaces.FISHMAN.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.race)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isCyborg() {
		Function<Race, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModRaces.CYBORG.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.race)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isMink() {
		Function<Race, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModRaces.MINK.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.race)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean hasRace() {
		return this.race.isPresent();
	}
	public void setRace(@Nullable() Race var1) {
		this.race = (Optional) (Optional.ofNullable(var1));
	}
	public Optional<Race> getRace() {
		return this.race;
	}
	public boolean isSwordsman() {
		Function<FightingStyle, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModFightingStyles.SWORDSMAN.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.fightingStyle)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isSniper() {
		Function<FightingStyle, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModFightingStyles.SNIPER.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.fightingStyle)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isDoctor() {
		Function<FightingStyle, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModFightingStyles.DOCTOR.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.fightingStyle)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isWeatherWizard() {
		Function<FightingStyle, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModFightingStyles.ART_OF_WEATHER.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.fightingStyle)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isBlackLeg() {
		Function<FightingStyle, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModFightingStyles.BLACK_LEG.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.fightingStyle)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isBrawler() {
		Function<FightingStyle, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModFightingStyles.BRAWLER.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.fightingStyle)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean hasFightingStyle() {
		return this.fightingStyle.isPresent();
	}
	public void setFightingStyle(@Nullable() FightingStyle var1) {
		this.fightingStyle = (Optional) (Optional.ofNullable(var1));
	}
	public Optional<FightingStyle> getFightingStyle() {
		return this.fightingStyle;
	}
	public boolean hasShadow() {
		return this.hasShadow;
	}
	public void setShadow(boolean var1) {
		this.hasShadow = var1;
	}
	public boolean hasHeart() {
		return this.hasHeart;
	}
	public void setHeart(boolean var1) {
		this.hasHeart = var1;
	}
	public boolean isInCombatMode() {
		return this.inCombatMode;
	}
	public void setCombatMode(boolean var1) {
		this.inCombatMode = var1;
	}
	public boolean hasStrawDoll() {
		return this.hasStrawDoll;
	}
	public void setStrawDoll(boolean var1) {
		this.hasStrawDoll = var1;
	}
	public double getDamageMultiplier() {
		return this.damageMultiplier;
	}
	public void setDamageMultiplier(double var1) {
		this.damageMultiplier = var1;
	}
	public boolean isBunnyMink() {
		Function<Race, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModRaces.MINK_BUNNY.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.subRace)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isDogMink() {
		Function<Race, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModRaces.MINK_DOG.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.subRace)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isLionMink() {
		Function<Race, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModRaces.MINK_LION.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.subRace)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public boolean isSawsharkFishman() {
		Function<Race, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(((java.lang.Object)(l0)).equals(ModRaces.FISHMAN_SAWSHARK.get()));
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(this.subRace)).map((Function)(var1)))).orElse((Object)(Boolean.valueOf(false))))).booleanValue();
	}
	public void setSubRace(@Nullable() Race var1) {
		if (((var1) != (null)) && (var1.isSubRace())){

		}else{
			var1 = null;
		}
		this.subRace = (Optional) (Optional.ofNullable(var1));
	}
	public Optional<Race> getSubRace() {
		return this.subRace;
	}
	public void setSkinTint(int var1) {
		this.skinTint = (Optional) (Optional.of(Integer.valueOf((-16777216) | (var1))));
	}
	public Optional<Integer> getSkinTint() {
		return this.skinTint;
	}
	public boolean isRogue() {
		return this.isRogue;
	}
	public void setRogue(boolean var1) {
		this.isRogue = var1;
		LivingEntity var2 = this.owner;
		if (var2 instanceof ServerPlayer){
			ServerPlayer var3 = ((ServerPlayer)(var2));
			ModNetwork.sendTo(new SUpdateRogueStateEventPacket((LivingEntity)(var3)),(Player)(var3));
		}
	}
	public float getLeftImpulse() {
		return this.leftImpulse;
	}
	public void setLeftImpulse(float var1) {
		this.leftImpulse = Mth.clamp(var1,-1F,1F);
	}
	public float getForwardImpulse() {
		return this.forwardImpulse;
	}
	public void setForwardImpulse(float var1) {
		this.forwardImpulse = Mth.clamp(var1,-1F,1F);
	}
	public boolean isJumping() {
		return this.isJumping;
	}
	public void setJumping(boolean var1) {
		this.isJumping = var1;
	}
	public int getJumpTicks() {
		return this.jumpTicks;
	}
	public void setJumpTicks(int var1) {
		this.jumpTicks = var1;
	}
	public void alterJumpTicks(int var1) {
		this.jumpTicks = (this.jumpTicks) + (var1);
	}
	public void addScreenShader(ResourceLocation var1) {
		if (this.screenShaders.contains(var1)){
			return;
		}else{
			this.screenShaders.addLast(var1);
			this.updateScreenShader();
			return;
		}
	}
	public boolean hasScreenShaderActive(ResourceLocation var1) {
		return this.screenShaders.contains(var1);
	}
	public void removeScreenShader(ResourceLocation var1) {
		if (!(this.screenShaders.contains(var1))){
			return;
		}else{
			this.screenShaders.removeLastOccurrence(var1);
			this.updateScreenShader();
			return;
		}
	}
	public void updateScreenShader() {
		GameRenderer var1 = Minecraft.getInstance().gameRenderer;
		ResourceLocation var2 = ((ResourceLocation)(this.screenShaders.peekFirst()));
		if ((var2) != (null)){
			var1.loadEffect(var2);
		}else{
			var1.currentEffect().close();
		}
	}
	public boolean hadChiyuEffect() {
		return this.hadChiyuEffect;
	}
	public void setChiyuEffect(boolean var1) {
		this.hadChiyuEffect = var1;
	}
	public float getOriginalChiyupopoHealth() {
		return this.originalChiyupopoHealth;
	}
	public void setOriginalChiyupopoHealth(float var1) {
		this.originalChiyupopoHealth = var1;
	}
	public void setDyeLayerCheck(boolean var1) {
		this.dyeLayersCheck = var1;
	}
	public boolean isDyeLayerCheck() {
		return this.dyeLayersCheck;
	}
	public int getTrainingPoints(TrainingPointType var1) {
		return ((Integer)(this.trainingPoints.getOrDefault(var1,Integer.valueOf(0)))).intValue();
	}
	public void alterTrainingPoints(TrainingPointType var1, int var2) {
		int var3 = ((Integer)(this.trainingPoints.getOrDefault(var1,Integer.valueOf(0)))).intValue();
		((java.util.EnumMap)(this.trainingPoints)).put((Enum)(var1),(Object)(Integer.valueOf(Math.max((var3) + (var2),0))));
	}
	public int getSpentTrainingPoints() {
		return this.spentTrainingPoints;
	}
	public void alterSpentTrainingPoints(int var1) {
		this.spentTrainingPoints = Mth.clamp((this.spentTrainingPoints) + (var1),0,AbilityHelper.getTrainingPointsLimit(this.owner));
	}
	public int getFreedSlaves() {
		return this.freedSlaves;
	}
	public void addFreedSlaves(int var1) {
		this.freedSlaves = (this.freedSlaves) + (var1);
		LivingEntity var2 = this.owner;
		if (var2 instanceof ServerPlayer){
			ServerPlayer var3 = ((ServerPlayer)(var2));
			((FreedSlavesTrigger)(ModAdvancements.FREED_SLAVES.get())).trigger(var3,this.freedSlaves);
		}
	}
	public void setFreedSlaves(int var1) {
		this.freedSlaves = var1;
	}
	public void setFlyingSpeed(Vec3 var1) {
		this.flyingSpeed = var1;
	}
	public Vec3 getFlyingSpeed() {
		return this.flyingSpeed;
	}
	public float getAllyInflictedInvisibilityCooldown() {
		return this.allyInflictedInvisibilityCooldown;
	}
	public void alterAllyInflictedInvisibilityCooldown(float var1) {
		this.setAllyInflictedInvisibilityCooldown((this.allyInflictedInvisibilityCooldown) + (var1));
	}
	public void setAllyInflictedInvisibilityCooldown(float var1) {
		this.allyInflictedInvisibilityCooldown = Math.max(0F,var1);
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.putInt("version",this.version);
		var2.putDouble("doriki",this.getDoriki());
		var2.putInt("cola",this.getCola());
		var2.putInt("ultraCola",this.getUltraCola());
		var2.putDouble("loyalty",this.getLoyalty());
		var2.putInt("invulnerableTime",this.getInvulnerableTime());
		var2.putDouble("damageMultiplier",this.getDamageMultiplier());
		var2.putLong("bounty",this.getBounty());
		var2.putLong("belly",this.getBelly());
		var2.putLong("extol",this.getExtol());
		CompoundTag var3 = var2;
		final CompoundTag var3_f1 = var3;
		Consumer<? super Faction> var4 = (l0) -> {
			var3_f1.putString("faction",l0.getRegistryName().toString());
		};
		((java.util.Optional)(this.faction)).ifPresent((Consumer)(var4));
		CompoundTag var5 = var2;
		final CompoundTag var5_f2 = var5;
		Consumer<? super Race> var6 = (l0) -> {
			var5_f2.putString("race",l0.getRegistryName().toString());
		};
		((java.util.Optional)(this.race)).ifPresent((Consumer)(var6));
		CompoundTag var7 = var2;
		final CompoundTag var7_f3 = var7;
		Consumer<? super Race> var8 = (l0) -> {
			var7_f3.putString("subRace",l0.getRegistryName().toString());
		};
		((java.util.Optional)(this.subRace)).ifPresent((Consumer)(var8));
		CompoundTag var9 = var2;
		final CompoundTag var9_f4 = var9;
		Consumer<? super FightingStyle> var10 = (l0) -> {
			var9_f4.putString("fightingStyle",l0.getRegistryName().toString());
		};
		((java.util.Optional)(this.fightingStyle)).ifPresent((Consumer)(var10));
		var2.putBoolean("hasShadow",this.hasShadow());
		var2.putBoolean("hasHeart",this.hasHeart());
		var2.putBoolean("hasStrawDoll",this.hasStrawDoll());
		var2.putBoolean("isInCombatMode",this.isInCombatMode());
		var2.putBoolean("isRogue",this.isRogue());
		var2.putBoolean("hadChiyuEffect",this.hadChiyuEffect());
		var2.putFloat("originalChiyupopoHealth",this.getOriginalChiyupopoHealth());
		var2.putFloat("allyInflictedInvisibilityCooldown",this.allyInflictedInvisibilityCooldown);
		var2.putInt("freedSlaves",this.getFreedSlaves());
		CompoundTag var11 = var2;
		final CompoundTag var11_f5 = var11;
		Consumer<? super Integer> var12 = (Integer l0) -> {
			var11_f5.putInt("skinTint",l0.intValue());
		};
		((java.util.Optional)(this.getSkinTint())).ifPresent((Consumer)(var12));
		CompoundTag var13 = new CompoundTag();
		Iterator var14 = this.trainingPoints.entrySet().iterator();
		do{
			if (var14.hasNext()){
				Map.Entry var15 = ((Map.Entry)(var14.next()));
				var13.putInt(((TrainingPointType)(var15.getKey())).name(),((Integer)(var15.getValue())).intValue());
				continue;
			}else{
				break;
			}
		} while (true);
		var2.put("trainingPoints",(Tag)(var13));
		var2.putInt("spentTrainingPoints",this.spentTrainingPoints);
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		this.version = var2.getInt("version");
		this.setDoriki(var2.getDouble("doriki"));
		this.setUltraCola(var2.getInt("ultraCola"));
		this.setCola(var2.getInt("cola"));
		this.setLoyalty((double)(var2.getInt("loyalty")));
		this.setInvulnerableTime(var2.getInt("invulnerableTime"));
		this.setDamageMultiplier(var2.getDouble("damageMultiplier"));
		this.setBelly(var2.getLong("belly"));
		this.setBounty(var2.getLong("bounty"));
		this.setExtol(var2.getLong("extol"));
		if (var2.contains("faction")){
			ResourceLocation var3 = ResourceLocation.parse(var2.getString("faction"));
			this.setFaction(Faction.get(var3));
		}
		if (var2.contains("race")){
			ResourceLocation var3_1 = ResourceLocation.parse(var2.getString("race"));
			this.setRace(Race.get(var3_1));
		}
		if (var2.contains("subRace")){
			ResourceLocation var3_2 = ResourceLocation.parse(var2.getString("subRace"));
			Race var4 = Race.get(var3_2);
			if (((var4) != (null)) && (var4.isSubRace())){
				this.setSubRace(var4);
			}
		}
		if (var2.contains("fightingStyle")){
			ResourceLocation var3_3 = ResourceLocation.parse(var2.getString("fightingStyle"));
			this.setFightingStyle(FightingStyle.get(var3_3));
		}
		this.setShadow(var2.getBoolean("hasShadow"));
		this.setHeart(var2.getBoolean("hasHeart"));
		this.setStrawDoll(var2.getBoolean("hasStrawDoll"));
		this.setCombatMode(var2.getBoolean("isInCombatMode"));
		this.setRogue(var2.getBoolean("isRogue"));
		this.setChiyuEffect(var2.getBoolean("hadChiyuEffect"));
		this.setOriginalChiyupopoHealth(var2.getFloat("originalChiyupopoHealth"));
		if (var2.contains("allyInflictedInvisibilityCooldown")){
			this.allyInflictedInvisibilityCooldown = var2.getFloat("allyInflictedInvisibilityCooldown");
		}
		this.setFreedSlaves(var2.getInt("freedSlaves"));
		if (var2.contains("skinTint")){
			this.skinTint = (Optional) (Optional.of(Integer.valueOf(var2.getInt("skinTint"))));
		}
		this.trainingPoints.clear();
		CompoundTag var3_4 = var2.getCompound("trainingPoints");
		Iterator var4_1 = var3_4.getAllKeys().iterator();
		do{
			if (var4_1.hasNext()){
				String var5 = ((String)(var4_1.next()));
				if (var5.equals("MARKSMANSHIP")){
					var5 = TrainingPointType.ACCURACY.name();
				}
				TrainingPointType var6 = TrainingPointType.valueOf(var5);
				int var7 = var3_4.getInt(var5);
				((java.util.EnumMap)(this.trainingPoints)).put((Enum)(var6),(Object)(Integer.valueOf(var7)));
				continue;
			}else{
				break;
			}
		} while (true);
		this.spentTrainingPoints = var2.getInt("spentTrainingPoints");
	}
	public void port() {
		if ((this.version) == (0)){
			LivingEntity var1 = this.owner;
			if ((var1 instanceof Player) && ((((Player)(var1)).getAttribute(ModAttributes.GCD).getBaseValue()) > (0D))){
				this.owner.getAttribute(ModAttributes.GCD).setBaseValue(0D);
			}
			this.version = 1;
		}
	}
}