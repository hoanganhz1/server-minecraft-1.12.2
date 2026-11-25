package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.CoolBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.WeatherBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CoolBallAbility extends WeatherBallAbility {
	// Fields
	private static final int NODE_POINTS_TE = 3;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<CoolBallAbility>> INSTANCE;

	public CoolBallAbility(AbilityCore<CoolBallAbility> var1) {
		super((AbilityCore)(var1));
	}
	public WeatherBallKind getKind() {
		return WeatherBallKind.COOL;
	}
	public ParticleEffect<?> getParticleEffect() {
		return (ParticleEffect<?>) (ParticleEffect) (((ParticleEffect)(ModParticleEffects.COOL_BALL_CHARGE.get())));
	}
	public WeatherBallProjectile getWeatherBallEntity(LivingEntity var1, Vec3 var2) {
		CoolBallProjectile var3 = new CoolBallProjectile(var1.level(),var1,(IAbility)(this));
		var3.moveTo(var2.x(),((var1.getY()) + ((double)(var1.getEyeHeight()))) - (0.5D),var2.z(),var1.getYRot(),var1.getXRot());
		AbilityHelper.setDeltaMovement((Entity)(var3),0D,0.3D,0D);
		return var3;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-12F,2F));
		RequiredStyleUnlockCondition var2 = RequiredStyleUnlockCondition.requires(((FightingStyle)(ModFightingStyles.ART_OF_WEATHER.get())));
		NodeUnlockCondition var3 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).or((NodeUnlockCondition)(TrainingPointsUnlockCondition.technology(3F)));
		var1.setUnlockRule(var3,SubtractPointsAction.technology(3F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).not()).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Launch a Cool Ball from your Clima Tact to use for different Tempos"),(Object)(null)),ImmutablePair.of((Object)("§aSHIFT-USE§r: Loads the ball into the clima tact"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv1_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv1_6 = CoolBallAbility::createNode;
			lv1_5[0] = lv1_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(CoolBallAbility::new)))).addDescriptionLine(lv1_3).setSourceElement(SourceElement.ICE).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_4).setNodeFactories(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("cool_ball","Cool Ball",var0));
	}
}