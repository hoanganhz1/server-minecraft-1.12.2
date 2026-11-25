package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TrainerEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ArtOfWeatherTrainerEntity extends TrainerEntity {
	// Fields
	protected static final List<Supplier<? extends Item>> TACTS;

	public ArtOfWeatherTrainerEntity(EntityType<ArtOfWeatherTrainerEntity> var1, Level var2) {
		super((EntityType)(var1),var2,MobsHelper.WEATHER_TRAINER_TEXTURES);
	}
	public void registerGoals() {
		super.registerGoals();
		Consumer<EntityStatsData> var1 = (l0) -> {
			l0.setFaction(((Faction)(ModFactions.CIVILIAN.get())));
			l0.setRace(((Race)(ModRaces.HUMAN.get())));
			l0.setFightingStyle(((FightingStyle)(ModFightingStyles.ART_OF_WEATHER.get())));
			l0.setDoriki(100D);
			l0.setBelly((long)((10) + (Math.round((float)(WyHelper.randomWithRange(0,10))))));
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).ifPresent((Consumer)(var1));
		if ((this.random.nextDouble()) < (0.4D)){
			ItemStack var2 = new ItemStack(((ItemLike)(((Supplier)(TACTS.get(this.random.nextInt(TACTS.size())))).get())));
			this.setItemSlot(EquipmentSlot.MAINHAND,var2);
		}
		this.setItemSlot(EquipmentSlot.HEAD,((Item)(ModArmors.WIZARD_HAT.get())).getDefaultInstance());
		this.targetSelector.addGoal(2,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,35D).add(Attributes.MOVEMENT_SPEED,0.2199999988079071D).add(Attributes.ATTACK_DAMAGE,2D).add(Attributes.MAX_HEALTH,50D).add(Attributes.ARMOR,2D).add(ModAttributes.TOUGHNESS,12D);
	}
	public List<QuestId<?>> getAvailableQuests(Player var1) {
		if ((((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))))) == (null)){
			return EMPTY;
		}else{
			return new ArrayList(((Collection)(((java.util.stream.Stream)(ModQuests.TECHNOLOGY_QUESTS.stream().map((Function)(((Function<DeferredHolder, QuestId>)(DeferredHolder::get)))))).collect((Collector)(Collectors.toList())))));
		}
	}
	static  {
		Supplier[] var0 = new Supplier[]{ModWeapons.CLIMA_TACT,ModWeapons.SORCERY_CLIMA_TACT,ModWeapons.PERFECT_CLIMA_TACT};
		TACTS = (List) (Arrays.asList(var0));
	}
}