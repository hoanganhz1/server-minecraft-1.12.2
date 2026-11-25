package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.CoolBallAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.HeatBallAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.TempoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class WeatherCloudTempo extends TempoAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<WeatherCloudTempo>> INSTANCE;

	public WeatherCloudTempo(AbilityCore<WeatherCloudTempo> var1) {
		super((AbilityCore)(var1));
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-12F,4F));
		UnlockAbilityAction var2 = UnlockAbilityAction.unlock((Supplier)(INSTANCE));
		AbilityNode[] var3 = new AbilityNode[2];
		var3[0] = ((AbilityCore)(HeatBallAbility.INSTANCE.get())).getNode(var0);
		var3[1] = ((AbilityCore)(CoolBallAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var3);
		var1.setUnlockRule((NodeUnlockCondition)(null),var2);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates a cloud that can further be used by other Tempos"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv1_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv1_6 = WeatherCloudTempo::createNode;
			lv1_5[0] = lv1_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(WeatherCloudTempo::new)))).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(lv1_3).setSourceType(lv1_4).setNodeFactories(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("weather_cloud_tempo","Weather Cloud Tempo",var0));
	}
}