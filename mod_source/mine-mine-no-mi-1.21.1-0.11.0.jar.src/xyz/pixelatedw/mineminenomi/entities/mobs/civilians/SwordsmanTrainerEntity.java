package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TrainerEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class SwordsmanTrainerEntity extends TrainerEntity {
	// Fields
	protected static final List<Supplier<? extends Item>> SWORDS;

	public SwordsmanTrainerEntity(EntityType<SwordsmanTrainerEntity> var1, Level var2) {
		super((EntityType)(var1),var2,MobsHelper.SWORDSMAN_TRAINER_TEXTURES);
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.ATTACK_DAMAGE,6D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,15D).add(ModAttributes.TOUGHNESS,12D);
	}
	public List<QuestId<?>> getAvailableQuests(Player var1) {
		if ((((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))))) == (null)){
			return EMPTY;
		}else{
			return new ArrayList(((Collection)(((java.util.stream.Stream)(ModQuests.WEAPON_MASTERY_QUESTS.stream().map((Function)(((Function<DeferredHolder, QuestId>)(DeferredHolder::get)))))).collect((Collector)(Collectors.toList())))));
		}
	}
	static  {
		Supplier[] var0 = new Supplier[9];
		((java.lang.Object[]) (var0))[0] = ModWeapons.SANDAI_KITETSU;
		((java.lang.Object[]) (var0))[1] = ModWeapons.NIDAI_KITETSU;
		((java.lang.Object[]) (var0))[2] = ModWeapons.WADO_ICHIMONJI;
		((java.lang.Object[]) (var0))[3] = ModWeapons.KIKOKU;
		((java.lang.Object[]) (var0))[4] = ModWeapons.GRYPHON;
		((java.lang.Object[]) (var0))[5] = ModWeapons.AME_NO_HABAKIRI;
		((java.lang.Object[]) (var0))[6] = ModWeapons.ENMA;
		Supplier<Item> var1 = () -> {
			return Items.DIAMOND_SWORD;
		};
		var0[7] = var1;
		Supplier<Item> var2 = () -> {
			return Items.NETHERITE_SWORD;
		};
		var0[8] = var2;
		SWORDS = (List) (Arrays.asList(var0));
	}
public void registerGoals() {
		super.registerGoals();
		Consumer<EntityStatsData> var1 = (l0) -> {
			l0.setFaction(((Faction)(ModFactions.CIVILIAN.get())));
			l0.setRace(((Race)(ModRaces.HUMAN.get())));
			l0.setFightingStyle(((FightingStyle)(ModFightingStyles.SWORDSMAN.get())));
			l0.setDoriki((double)((4000) + (WyHelper.randomWithRange(0,1000))));
			l0.setBelly((long)((500) + (Math.round((float)(WyHelper.randomWithRange(0,500))))));
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).ifPresent((Consumer)(var1));
		Consumer<HakiData> var2 = (l0) -> {
			l0.setBusoshokuHakiExp(80F);
			l0.setKenbunshokuHakiExp(80F);
		};
		((java.util.Optional)(HakiData.get((LivingEntity)(this)))).ifPresent((Consumer)(var2));
		ItemStack var3 = new ItemStack(((ItemLike)(((Supplier)(SWORDS.get(this.random.nextInt(SWORDS.size())))).get())));
		var3.set(ModDataComponents.IS_CLONE,Boolean.valueOf(true));
		this.setItemSlot(EquipmentSlot.MAINHAND,var3);
		this.targetSelector.addGoal(2,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Monster.class,true,true)));
		this.goalSelector.addGoal(0,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.2D,true)));
		WeightedList var4 = new WeightedList(new Object[0]);
		SwordsmanTrainerEntity var5 = this;
		Supplier<Goal> var6 = () -> {
			return new DashAbilityWrapperGoal((Mob)(this),((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get())));
		};
		var4.addEntry(var6,3);
		SwordsmanTrainerEntity var7 = this;
		Supplier<Goal> var8 = () -> {
			ProjectileAbilityWrapperGoal lv4_1 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(SanbyakurokujuPoundHoAbility.INSTANCE.get())));
			((SanbyakurokujuPoundHoAbility)(lv4_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv4_1;
		};
		var4.addEntry(var8,3);
		SwordsmanTrainerEntity var9 = this;
		Supplier<Goal> var10 = () -> {
			return new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(HiryuKaenAbility.INSTANCE.get())));
		};
		var4.addEntry(var10,3);
		SwordsmanTrainerEntity var11 = this;
		Supplier<Goal> var12 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(OTatsumakiAbility.INSTANCE.get())));
		};
		var4.addEntry(var12,2);
		SwordsmanTrainerEntity var13 = this;
		Supplier<Goal> var14 = () -> {
			ProjectileAbilityWrapperGoal lv7_1 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(YakkodoriAbility.INSTANCE.get())));
			((YakkodoriAbility)(lv7_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv7_1;
		};
		var4.addEntry(var14,2);
		SwordsmanTrainerEntity var15 = this;
		Consumer var16 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			this.goalSelector.addGoal(2,((Goal)(l0.get())));
		}));
		var4.pickN(this.getRandom(),5).forEach(var16);
		SwordsmanTrainerEntity var17 = this;
		Consumer var18 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
		MobsHelper.getBasicHakiAbilities((Mob)(this),100).forEach(var18);
	}}