package xyz.pixelatedw.mineminenomi.abilities.hiso;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.triggers.MooteorologistTrigger;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.mixins.IServerLevelMixin;

public class ForewarnAbility extends Ability {
	// Fields
	public static final TargetPredicate TARGETS_PREDICATE;
	private static final Component MESSAGE_UNCHANGING_WEATHER;
	private static final String MESSAGE_NEXT_RAIN;
	private static final String MESSAGE_NEXT_CLEAR;
	private static final int COOLDOWN = 200;
	private static final int RANGE = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ForewarnAbility>> INSTANCE;
	private final RangeComponent rangeComponent;

	public ForewarnAbility(AbilityCore<ForewarnAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		List var3 = this.rangeComponent.getTargetsInArea(var1,10F,TARGETS_PREDICATE);
		if ((var3.size()) <= (0)){
			this.cooldownComponent.startCooldown(var1,200F);
			return;
		}else{
			if (((var3.size()) > (0)) && (!(var1.level().isClientSide))){
				LivingEntity var4 = ((LivingEntity)(((java.util.Optional)(var3.stream().findFirst())).orElse((Object)(null))));
				if ((var4) == (null)){
					this.cooldownComponent.startCooldown(var1,200F);
					return;
				}else{
					Object var5 = Component.empty();
					if (var4 instanceof Cow){
						if (var1 instanceof ServerPlayer){
							ServerPlayer var6 = ((ServerPlayer)(var1));
							((MooteorologistTrigger)(ModAdvancements.MOOTEOROLOGIST.get())).trigger(var6);
						}
						var5 = ModI18n.MESSAGE_MOO;
					}
					int var6_1 = ((IServerLevelMixin)(var1.level())).getServerLevelData().getRainTime();
					int var7 = ((IServerLevelMixin)(var1.level())).getServerLevelData().getClearWeatherTime();
					if (((!(var1.getServer().getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE))) ? (1) : (0)) != (0)){
						String var8 = MESSAGE_UNCHANGING_WEATHER.getString();
						String var9 = ((Component)(var5)).getString();
						String var10 = var8 + " " + var9;
						WyHelper.sendMessage(var1,(Component)(Component.literal(var10)),true);
						return;
					}else{
						if ((var7) == (0)){
							Object[] var8_1 = new Object[]{Integer.valueOf((var6_1) / (1200))};
							MutableComponent var9_1 = Component.translatable(MESSAGE_NEXT_RAIN,var8_1);
							String var10_1 = var9_1.getString();
							String var11 = ((Component)(var5)).getString();
							String var12 = var10_1 + " " + var11;
							WyHelper.sendMessage(var1,(Component)(Component.literal(var12)),true);
						}else{
							if ((var6_1) == (0)){
								Object[] var8_2 = new Object[]{Integer.valueOf((var7) / (1200))};
								MutableComponent var9_2 = Component.translatable(MESSAGE_NEXT_CLEAR,var8_2);
								String var10_2 = var9_2.getString();
								String var11_1 = ((Component)(var5)).getString();
								String var12_1 = var10_2 + " " + var11_1;
								WyHelper.sendMessage(var1,(Component)(Component.literal(var12_1)),true);
							}
						}
					}
				}
			}
			this.cooldownComponent.startCooldown(var1,200F);
			return;
		}
	}
	static  {
		Class var0 = Animal.class;
		Objects.requireNonNull(var0);
		TARGETS_PREDICATE = new TargetPredicate().selector((Predicate)(((Predicate<Entity>)(var0::isInstance))));
		MESSAGE_UNCHANGING_WEATHER = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.GUI,"unchanging_weather","The weather won't ever change it seems."));
		MESSAGE_NEXT_RAIN = ModRegistry.registerName(ModRegistry$I18nCategory.GUI,"next_rain","Next rain will happen in %s minutes.");
		MESSAGE_NEXT_CLEAR = ModRegistry.registerName(ModRegistry$I18nCategory.GUI,"next_clear","The rain should stop in %s minutes.");
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to communicate with nearby animals and learn when the next rain or clear weathers will come."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),RangeComponent.getTooltip(10F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ForewarnAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("animal_forewarning","Animal Forewarning",var1));
	}
}