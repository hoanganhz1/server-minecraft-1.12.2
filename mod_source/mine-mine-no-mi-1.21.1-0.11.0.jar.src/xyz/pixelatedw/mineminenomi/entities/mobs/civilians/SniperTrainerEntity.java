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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KaenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.NemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.RenpatsuNamariBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TetsuBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal$IStyleSwitchEvent;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class SniperTrainerEntity extends TrainerEntity implements RangedAttackMob {
	// Fields
	private static final int SWITCH_RANGE = 5;
	private NPCPhaseManager phaseManager;
	private NPCPhase<SniperTrainerEntity> meleePhase;
	private NPCPhase<SniperTrainerEntity> rangePhase;
	private ItemStack heldBow;

	public SniperTrainerEntity(EntityType<SniperTrainerEntity> var1, Level var2) {
		super((EntityType)(var1),var2,MobsHelper.SNIPER_TRAINER_TEXTURES);
		this.heldBow = ItemStack.EMPTY;
	}
	public void registerGoals() {
		super.registerGoals();
		this.phaseManager = new NPCPhaseManager((Mob)(this));
		this.meleePhase = new SimplePhase("Melee Phase",this);
		this.rangePhase = new SimplePhase("Range Phase",this);
		SniperTrainerEntity var1 = this;
		Consumer<EntityStatsData> var2 = (l0) -> {
			l0.setFaction(((Faction)(ModFactions.CIVILIAN.get())));
			l0.setRace(((Race)(ModRaces.HUMAN.get())));
			l0.setFightingStyle(((FightingStyle)(ModFightingStyles.SNIPER.get())));
			l0.setDoriki((double)((4000) + (this.getRandom().nextInt(1000))));
			l0.setBelly((long)((500) + (Math.round((float)(this.getRandom().nextInt(500))))));
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).ifPresent((Consumer)(var2));
		SniperTrainerEntity var3 = this;
		Consumer<HakiData> var4 = (l0) -> {
			l0.setBusoshokuHakiExp((float)((60) + (this.getRandom().nextInt(20))));
			l0.setKenbunshokuHakiExp((float)((60) + (this.getRandom().nextInt(20))));
		};
		((java.util.Optional)(HakiData.get((LivingEntity)(this)))).ifPresent((Consumer)(var4));
		this.setItemSlot(EquipmentSlot.MAINHAND,Items.BOW.getDefaultInstance());
		this.targetSelector.addGoal(2,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
		this.targetSelector.addGoal(2,(Goal)(new NearestAttackableTargetGoal((Mob)(this),Monster.class,true,true)));
		this.goalSelector.addGoal(0,(Goal)(new StyleSwitchGoal((Mob)(this),(StyleSwitchGoal$IStyleSwitchEvent)(((xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal$IStyleSwitchEvent)(this::onStyleSwitched))))));
		if ((this.getRandom().nextInt(3)) == (0)){
			this.meleePhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()))));
		}else{
			this.meleePhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get()))));
		}
		this.meleePhase.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1.15D,true)));
		this.rangePhase.addGoal(0,new HakiAbilityWrapperGoal((Mob)(this),((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get()))));
		this.rangePhase.addGoal(1,(Goal)(new RangedAttackGoal((RangedAttackMob)(this),1D,60,120F)));
		WeightedList var5 = new WeightedList(new Object[0]);
		SniperTrainerEntity var6 = this;
		Supplier<Goal> var7 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(KaenBoshiAbility.INSTANCE.get())));
		};
		var5.addEntry(var7,3);
		SniperTrainerEntity var8 = this;
		Supplier<Goal> var9 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(SakuretsuSabotenBoshiAbility.INSTANCE.get())));
		};
		var5.addEntry(var9,3);
		SniperTrainerEntity var10 = this;
		Supplier<Goal> var11 = () -> {
			ProjectileAbilityWrapperGoal lv5_1 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(TokuyoAburaBoshiAbility.INSTANCE.get())));
			((TokuyoAburaBoshiAbility)(lv5_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv5_1;
		};
		var5.addEntry(var11,3);
		SniperTrainerEntity var12 = this;
		Supplier<Goal> var13 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(TetsuBoshiAbility.INSTANCE.get())));
		};
		var5.addEntry(var13,2);
		SniperTrainerEntity var14 = this;
		Supplier<Goal> var15 = () -> {
			ProjectileAbilityWrapperGoal lv7_1 = new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(RenpatsuNamariBoshiAbility.INSTANCE.get())));
			((RenpatsuNamariBoshiAbility)(lv7_1.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
			return lv7_1;
		};
		var5.addEntry(var15,2);
		SniperTrainerEntity var16 = this;
		Supplier<Goal> var17 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(KemuriBoshiAbility.INSTANCE.get())));
		};
		var5.addEntry(var17,1);
		SniperTrainerEntity var18 = this;
		Supplier<Goal> var19 = () -> {
			return new ProjectileAbilityWrapperGoal((Mob)(this),((AbilityCore)(NemuriBoshiAbility.INSTANCE.get())));
		};
		var5.addEntry(var19,1);
		SniperTrainerEntity var20 = this;
		Consumer var21 = (Consumer) ((Consumer<Supplier>) ((l0) -> {
			this.rangePhase.addGoal(2,((Goal)(l0.get())));
		}));
		var5.pickN(this.getRandom(),5).forEach(var21);
		this.meleePhase.addGoal(2,new CloseMeleeAbilityWrapperGoal((Mob)(this),((AbilityCore)(ChargedPunchAbility.INSTANCE.get()))));
		GrabAbilityWrapperGoal var22 = new GrabAbilityWrapperGoal((Mob)(this),((AbilityCore)(SuplexAbility.INSTANCE.get())));
		((SuplexAbility)(var22.getAbility())).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
		this.meleePhase.addGoal(2,var22);
		this.phaseManager.setPhase(this.rangePhase);
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.2800000011920929D).add(Attributes.ATTACK_DAMAGE,5D).add(Attributes.MAX_HEALTH,200D).add(Attributes.ARMOR,15D).add(ModAttributes.TOUGHNESS,12D);
	}
	private FightingStyle onStyleSwitched(Mob var1, LivingEntity var2, FightingStyle var3) {
		boolean var4 = false;
		if (((java.lang.Object)(var3)).equals(ModFightingStyles.SNIPER.get())){
			var4 = GoalHelper.hasEnoughTargetsAround(var1,2,5F);
			boolean var5 = GoalHelper.isWithinDistance((LivingEntity)(var1),var2,5D);
			if ((!(var4)) && (!(var5))){

			}else{
				this.phaseManager.setPhase(this.meleePhase);
				this.heldBow = this.getMainHandItem();
				this.setItemSlot(EquipmentSlot.MAINHAND,ItemStack.EMPTY);
				return ((FightingStyle)(ModFightingStyles.BRAWLER.get()));
			}
		}else{
			if (this.heldBow.isEmpty()){
				return var3;
			}else{
				var4 = GoalHelper.isOutsideDistance((LivingEntity)(var1),var2,5D);
				if (var4){
					this.phaseManager.setPhase(this.rangePhase);
					this.setItemSlot(EquipmentSlot.MAINHAND,this.heldBow);
					return ((FightingStyle)(ModFightingStyles.SNIPER.get()));
				}
			}
		}
		return var3;
	}
	public void customServerAiStep() {
		this.phaseManager.tick();
	}
	public void performRangedAttack(LivingEntity var1, float var2) {
		if ((!(this.isUsingItem())) && ((!(this.getMainHandItem().isEmpty())) && (((java.lang.Object)(this.getMainHandItem().getItem())).equals((Object)(Items.BOW))))){
			NuProjectileEntity var3 = new NormalBulletProjectile((Level)(this.level()),(LivingEntity)(this));
			if (this.isAboveNormalDifficulty()){
				var3 = new KairosekiBulletProjectile((Level)(this.level()),(LivingEntity)(this));
			}
			double var4 = (var1.getX()) - (this.getX());
			double var5 = (var1.getEyeY()) - (var3.getY());
			var3.shoot(var4,var5,(var1.getZ()) - (this.getZ()),2.5F,0F);
			this.level().addFreshEntity((Entity)(var3));
			return;
		}else{
			return;
		}
	}
	public List<QuestId<?>> getAvailableQuests(Player var1) {
		if ((((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))))) == (null)){
			return EMPTY;
		}else{
			return new ArrayList(((Collection)(((java.util.stream.Stream)(ModQuests.ACCURACY_QUESTS.stream().map((Function)(((Function<DeferredHolder, QuestId>)(DeferredHolder::get)))))).collect((Collector)(Collectors.toList())))));
		}
	}
}