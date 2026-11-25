package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.ArrayList;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.BlackLegPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ExtraHachisAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
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
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TrainerEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class BlackLegTrainerEntity extends TrainerEntity {
	public BlackLegTrainerEntity(EntityType<BlackLegTrainerEntity> var1, Level var2) {
		super((EntityType)(var1),var2,MobsHelper.BLACK_LEG_TRAINERS_TEXTURES);
	}
	public void registerGoals() {
		super.registerGoals();
		Consumer<EntityStatsData> var1 = (l0) -> {
			l0.setFaction(((Faction)(ModFactions.CIVILIAN.get())));
			l0.setRace(((Race)(ModRaces.HUMAN.get())));
			l0.setFightingStyle(((FightingStyle)(ModFightingStyles.BLACK_LEG.get())));
			l0.setDoriki((double)((4000) + (WyHelper.randomWithRange(0,1000))));
			l0.setBelly((long)((500) + (Math.round((float)(WyHelper.randomWithRange(0,500))))));
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).ifPresent((Consumer)(var1));
		Consumer<HakiData> var2 = (l0) -> {
			l0.setBusoshokuHakiExp(80F);
			l0.setKenbunshokuHakiExp(80F);
		};
		((java.util.Optional)(HakiData.get((LivingEntity)(this)))).ifPresent((Consumer)(var2));
		if ((this.random.nextDouble()) < (0.4D)){
			this.setItemSlot(EquipmentSlot.HEAD,new ItemStack(((ItemLike)(ModItems.CIGAR.get()))));
		}
		this.targetSelector.addGoal(2,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Monster.class,true,true)));
		this.goalSelector.addGoal(0,(Goal)(new AlwaysActiveAbilityWrapperGoal((Mob)(this),((AbilityCore)(BlackLegPassiveBonusesAbility.INSTANCE.get())))));
		this.goalSelector.addGoal(0,(Goal)(new SprintTowardsTargetGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.3D,true)));
		WeightedList var3 = new WeightedList(new Object[0]);
		BlackLegTrainerEntity var4 = this;
		Supplier<Goal> var5 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(PartyTableKickCourseAbility.INSTANCE.get())));
		};
		var3.addEntry(var5,3);
		BlackLegTrainerEntity var6 = this;
		Supplier<Goal> var7 = () -> {
			return new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(AntiMannerKickCourseAbility.INSTANCE.get())));
		};
		var3.addEntry(var7,3);
		BlackLegTrainerEntity var8 = this;
		Supplier<Goal> var9 = () -> {
			return new JumpAbilityWrapperGoal((Mob)(this),((AbilityCore)(ConcasseAbility.INSTANCE.get())));
		};
		var3.addEntry(var9,3);
		BlackLegTrainerEntity var10 = this;
		Supplier<Goal> var11 = () -> {
			return new RepeaterAbilityWrapperGoal((Mob)(this),((AbilityCore)(ExtraHachisAbility.INSTANCE.get())));
		};
		var3.addEntry(var11,3);
		BlackLegTrainerEntity var12 = this;
		Consumer var13 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			this.goalSelector.addGoal(2,((Goal)(l0.get())));
		}));
		var3.pickN(this.getRandom(),5).forEach(var13);
		BlackLegTrainerEntity var14 = this;
		Consumer var15 = (Consumer) ((Consumer<AbilityCore>) ((l0) -> {
			this.goalSelector.addGoal(0,(Goal)(new HakiAbilityWrapperGoal((Mob)(this),l0)));
		}));
		MobsHelper.getBasicHakiAbilities((Mob)(this),100).forEach(var15);
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.3100000023841858D).add(Attributes.ATTACK_DAMAGE,6D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,15D).add(ModAttributes.TOUGHNESS,12D);
	}
	public List<QuestId<?>> getAvailableQuests(Player var1) {
		if ((((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))))) == (null)){
			return EMPTY;
		}else{
			return new ArrayList(((Collection)(((java.util.stream.Stream)(ModQuests.MARTIAL_ARTS_QUESTS.stream().map((Function)(((Function<DeferredHolder, QuestId>)(DeferredHolder::get)))))).collect((Collector)(Collectors.toList())))));
		}
	}
}