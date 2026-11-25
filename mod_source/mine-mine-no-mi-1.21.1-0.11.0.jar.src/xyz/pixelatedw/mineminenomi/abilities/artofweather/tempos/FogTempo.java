package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.clouds.MirageTempoCloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class FogTempo extends ChargedTempoAbility {
	// Fields
	private static final float RANGE = 10F;
	private static final int EFFECT_TIME = 200;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<FogTempo>> INSTANCE;
	private final RangeComponent rangeComponent;

	public FogTempo(AbilityCore<FogTempo> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
	}
	public WeatherBallKind[] getTempoOrder() {
		WeatherBallKind[] var1 = new WeatherBallKind[]{WeatherBallKind.HEAT,WeatherBallKind.HEAT,WeatherBallKind.COOL};
		return var1;
	}
	public void useTempo(LivingEntity var1) {
		MirageTempoCloudEntity var2 = new MirageTempoCloudEntity((Level)(var1.level()),(LivingEntity)(var1));
		var2.setLife(300);
		var2.moveTo(var1.getX(),(var1.getY()) + (1D),var1.getZ(),0F,0F);
		AbilityHelper.setDeltaMovement(var2,0D,0D,0D);
		var1.level().addFreshEntity((Entity)(var2));
		Iterator var3 = this.rangeComponent.getTargetsInArea(var1,var1.blockPosition(),10F,TargetPredicate.DEFAULT_AREA_CHECK).iterator();
		do{
			if (var3.hasNext()){
				((LivingEntity)(var3.next())).addEffect(new MobEffectInstance(MobEffects.BLINDNESS,200,1));
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-10F,2F));
		UnlockAbilityAction var2 = UnlockAbilityAction.unlock((Supplier)(INSTANCE));
		AbilityNode[] var3 = new AbilityNode[1];
		var3[0] = ((AbilityCore)(WeatherCloudTempo.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var3);
		var1.setUnlockRule((NodeUnlockCondition)(null),var2);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Creates a thick fog at the user's position, blinding all nearby enemies as well"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			SourceType[] lv2_4 = new SourceType[]{SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv2_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv2_6 = FogTempo::createNode;
			lv2_5[0] = lv2_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(FogTempo::new)))).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(lv2_3).setSourceType(lv2_4).setNodeFactories(lv2_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("fog_tempo","Fog Tempo",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<RangeComponentTooltip> var3 = (l0) -> {
			l0.range(10F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var3);
	}
}