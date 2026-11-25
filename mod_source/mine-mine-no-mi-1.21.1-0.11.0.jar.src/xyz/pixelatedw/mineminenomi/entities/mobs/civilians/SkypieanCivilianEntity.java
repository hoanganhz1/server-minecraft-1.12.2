package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class SkypieanCivilianEntity extends OPEntity {
	public SkypieanCivilianEntity(EntityType<? extends SkypieanCivilianEntity> var1, Level var2) {
		super((EntityType)(var1),var2,MobsHelper.SKYPEAN_TEXTURES);
	}
	public void registerGoals() {
		Consumer<EntityStatsData> var1 = (l0) -> {
			l0.setFaction(((Faction)(ModFactions.CIVILIAN.get())));
			l0.setRace(((Race)(ModRaces.SKYPIEAN.get())));
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).ifPresent((Consumer)(var1));
		this.goalSelector.addGoal(1,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(2,(Goal)(new OpenDoorGoal((Mob)(this),false)));
		this.goalSelector.addGoal(3,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.8D)));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(5,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		this.targetSelector.addGoal(0,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,10D).add(Attributes.MOVEMENT_SPEED,0.23D).add(Attributes.ATTACK_DAMAGE,1D).add(Attributes.MAX_HEALTH,20D).add(Attributes.ARMOR,0D);
	}
}