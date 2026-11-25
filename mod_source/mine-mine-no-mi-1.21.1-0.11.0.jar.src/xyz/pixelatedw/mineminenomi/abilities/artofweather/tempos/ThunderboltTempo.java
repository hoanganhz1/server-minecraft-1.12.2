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
import xyz.pixelatedw.mineminenomi.abilities.artofweather.ThunderBallAbility;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.TempoTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderboltProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class ThunderboltTempo extends TempoAbility {
	// Fields
	private static final float RANGE = 25F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ThunderboltTempo>> INSTANCE;
	private final RangeComponent rangeComponent;
	private final ProjectileComponent projectileComponent;

	public ThunderboltTempo(AbilityCore<ThunderboltTempo> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<ThunderboltProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.projectileComponent};
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
				if ((!(this.isUnderWeatherCloud(var2,var6))) || (!(var1.hasLineOfSight((Entity)(var6))))){
					continue;
				}else{
					float var7 = (float)(Math.sqrt(var2.position().distanceToSqr(var6.getX(),var6.getY(),var6.getZ())));
					ThunderboltProjectile var8 = ((ThunderboltProjectile)(this.projectileComponent.getNewProjectile(var1)));
					var8.moveTo(var6.getX(),(var6.getY()) + ((double)(var7)),var6.getZ(),0F,90F);
					var8.setMaxTravelDistance(var7);
					this.projectileComponent.shoot(var8,var1,0F,90F,1F,1F);
					break;
				}
			}else{
				break;
			}
		} while (true);
	}
	public ThunderboltProjectile createProjectile(LivingEntity var1) {
		return new ThunderboltProjectile(var1.level(),var1,var1.getX(),var1.getY(),var1.getZ(),0F,(IAbility)(this));
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-16F,4F));
		UnlockAbilityAction var2 = UnlockAbilityAction.unlock((Supplier)(INSTANCE));
		AbilityNode[] var3 = new AbilityNode[1];
		var3[0] = ((AbilityCore)(ThunderBallAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var3);
		var1.setUnlockRule((NodeUnlockCondition)(null),var2);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("Hits a single enemy below the cloud with lightning bolt"),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			SourceType[] lv4_4 = new SourceType[]{SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv4_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_6 = ThunderboltTempo::createNode;
			lv4_5[0] = lv4_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ThunderboltTempo::new)))).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(lv4_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.LIGHTNING).setSourceType(lv4_4).setNodeFactories(lv4_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("thunderbolt_tempo","Thunderbolt Tempo",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<RangeComponentTooltip> var3 = (l0) -> {
			l0.range(25F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var3);
		Consumer<ProjectileComponentTooltip> var4 = (l0) -> {
			l0.damage(40F);
		};
		ProjectileComponentTooltip.generate(var2,var4);
		Consumer<TempoTooltip> var5 = (l0) -> {
			l0.requiresCloud().requiredBalls(1,WeatherBallKind.THUNDER);
		};
		TempoTooltip.generate(var2,var5);
	}
}