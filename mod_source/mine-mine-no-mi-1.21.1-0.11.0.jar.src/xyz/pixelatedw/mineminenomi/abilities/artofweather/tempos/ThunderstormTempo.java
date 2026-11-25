package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.TempoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.TempoTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderstormProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class ThunderstormTempo extends TempoAbility {
	// Fields
	private static final float RANGE = 25F;
	private static final int NODE_POINTS_TE = 3;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ThunderstormTempo>> INSTANCE;
	private final RangeComponent rangeComponent;

	public ThunderstormTempo(AbilityCore<ThunderstormTempo> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
	}
	public void useTempo(LivingEntity var1, WeatherCloudEntity var2) {
		Vec3 var3 = WyHelper.getFloorLevel(var2);
		List var4 = this.rangeComponent.getTargetsInArea(var1,BlockPos.containing((Position)(var3)),25F,TargetPredicate.DEFAULT_AREA_CHECK);
		Collections.shuffle(var4);
		Iterator var5 = var4.iterator();
		do{
			if (var5.hasNext()){
				LivingEntity var6 = ((LivingEntity)(var5.next()));
				if (!(this.isUnderWeatherCloud(var2,var6))){
					continue;
				}else{
					float var7 = (float)(Math.sqrt(var2.position().distanceToSqr(var6.getX(),var6.getY(),var6.getZ())));
					ThunderstormProjectile var8 = new ThunderstormProjectile(var1.level(),var1,var6.getX(),var6.getY(),var6.getZ(),var7,(IAbility)(this));
					var8.moveTo(var6.getX(),(var6.getY()) + ((double)(var7)),var6.getZ(),0F,90F);
					var8.setMaxTravelDistance(var7);
					var1.level().addFreshEntity((Entity)(var8));
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-18F,5F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.technology(3F);
		NodeUnlockAction var3 = SubtractPointsAction.technology(3F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(ThunderboltTempo.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("Hits multiple enemies below the cloud with multiple lightning bolts"),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			SourceType[] lv3_4 = new SourceType[]{SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv3_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv3_6 = ThunderstormTempo::createNode;
			lv3_5[0] = lv3_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ThunderstormTempo::new)))).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(lv3_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.LIGHTNING).setSourceType(lv3_4).setNodeFactories(lv3_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("thunderstorm_tempo","Thunderstorm Tempo",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<TempoTooltip> var3 = (l0) -> {
			l0.requiresCloud().requiredBalls(5,WeatherBallKind.THUNDER);
		};
		TempoTooltip.generate(var2,var3);
		Consumer<RangeComponentTooltip> var4 = (l0) -> {
			l0.range(25F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var4);
	}
}