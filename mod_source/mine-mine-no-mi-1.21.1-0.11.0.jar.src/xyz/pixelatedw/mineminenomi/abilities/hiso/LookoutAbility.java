package xyz.pixelatedw.mineminenomi.abilities.hiso;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Direction8;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.data.entity.HisoLookoutData;
import xyz.pixelatedw.mineminenomi.data.entity.HisoLookoutData$HisoLookoutTarget;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class LookoutAbility extends Ability {
	// Fields
	private static final Component MESSAGE_RECENTLY_SEEN = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.GUI,"recently_seen","Recently Seen:"));
	private static final String MESSAGE_RECENTLY_SEEN_TARGET;
	private static final int COOLDOWN = 200;
	private static final int RANGE = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<LookoutAbility>> INSTANCE;
	private final RangeComponent rangeComponent;

	public LookoutAbility(AbilityCore<LookoutAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		List var3 = this.rangeComponent.getTargetsInArea(var1,10F,ForewarnAbility.TARGETS_PREDICATE);
		Iterator var4 = var3.iterator();
		LOOP_1:
		do{
			if (var4.hasNext()){
				LivingEntity var5 = ((LivingEntity)(var4.next()));
				if (var5 instanceof Animal){
					HisoLookoutData var6 = ((HisoLookoutData)(((java.util.Optional)(HisoLookoutData.get(((Animal)(var5))))).orElse((Object)(null))));
					if ((var6) == (null)){
						continue;
					}else{
						Collection var7 = var6.getSeenTargets().values();
						if ((var7.size()) > (0)){
							WyHelper.sendMessage(var1,MESSAGE_RECENTLY_SEEN,true);
							Iterator var8 = var7.iterator();
							do{
								if (var8.hasNext()){
									HisoLookoutData$HisoLookoutTarget var9 = ((HisoLookoutData$HisoLookoutTarget)(var8.next()));
									long var10 = (((var1.level().getGameTime()) - (var9.time())) / (20L)) / (60L);
									Object[] var11 = new Object[]{var9.username(),Long.valueOf(var10),WyHelper.getDirectionLocalizedName(var9.direction())};
									WyHelper.sendMessage(var1,(Component)(Component.translatable(MESSAGE_RECENTLY_SEEN_TARGET,var11)),true);
									continue;
								}else{
									continue LOOP_1;
								}
							} while (true);
						}else{
							continue;
						}
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		if ((var3.size()) > (0)){
			LivingEntity var5_1 = ((LivingEntity)(var3.get(0)));
			var5_1.addEffect(new MobEffectInstance((Holder)(ModEffects.ANIMAL_LOOKOUT),9999,0,true,false));
			if (var1 instanceof ServerPlayer){
				((ServerPlayer)(var1)).connection.send((Packet)(new ClientboundUpdateMobEffectPacket(var5_1.getId(),var5_1.getEffect((Holder)(ModEffects.ANIMAL_LOOKOUT)),false)));
			}
		}
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		MESSAGE_RECENTLY_SEEN_TARGET = ModRegistry.registerName(ModRegistry$I18nCategory.GUI,"recently_seen_target","%s was around here %s minutes ago going towards %s");
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to communicate with nearby animals and learn if other players passed near them."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),RangeComponent.getTooltip(10F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(LookoutAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("animal_lookout","Animal Lookout",var0));
	}
}