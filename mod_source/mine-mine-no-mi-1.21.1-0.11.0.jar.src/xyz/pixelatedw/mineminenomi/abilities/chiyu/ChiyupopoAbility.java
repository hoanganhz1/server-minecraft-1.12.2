package xyz.pixelatedw.mineminenomi.abilities.chiyu;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChiyupopoAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 16800F;
	private static final float RANGE = 15F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ChiyupopoAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private TargetPredicate predicate;

	public ChiyupopoAbility(AbilityCore<ChiyupopoAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
		super.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("Releases dandelions made of tears that temporarily increase the healing rate of those around the user. This can only be applied once per person."),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			AbilityDescriptionLine$IDescriptionLine[] lv3_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(16800F),RangeComponent.getTooltip(15F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ChiyupopoAbility::new)))).addDescriptionLine(lv3_3).addAdvancedDescriptionLine(lv3_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("chiyupopo","Chiyupopo",var0));
	}

	private void onUseEvent(LivingEntity var1, IAbility var2) {
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			var3.setChiyuEffect(false);
			if ((this.predicate) == (null)){
				Predicate<Entity> var4 = (l0) -> {
			int lv1_1 = 0;
			if (l0 instanceof LivingEntity){
				LivingEntity lv1_2 = ((LivingEntity)(l0));
				if (!(((Boolean)(EntityStatsData.get(lv1_2).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::hadChiyuEffect)))).orElse(Boolean.valueOf(false)))).booleanValue())){
					lv1_1 = 1;
				}else{
					lv1_1 = 0;
				}
			}else{
				lv1_1 = 0;
			}
			return (((lv1_1) & 1) != 0);
		};
				this.predicate = new TargetPredicate().testFriendlyFaction().selector(var4);
			}
			List var4_1 = this.rangeComponent.getTargetsInArea(var1,15F,this.predicate);
			if ((((java.util.function.Predicate)(ModEntityPredicates.getFriendlyFactions(var1))).test((Object)(var1))) && (!(var3.hadChiyuEffect()))){
				var4_1.add(var1);
			}
			Iterator var5 = var4_1.iterator();
			do{
				if (var5.hasNext()){
					LivingEntity var6 = ((LivingEntity)(var5.next()));
					if (var6.addEffect(new MobEffectInstance((Holder)(ModEffects.CHIYUPOPO),4800,5))){
						Consumer<EntityStatsData> var7 = (l0) -> {
			l0.setChiyuEffect(true);
		};
						((java.util.Optional)(EntityStatsData.get(var6))).ifPresent((Consumer)(var7));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CHIYUPOPO.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			this.cooldownComponent.startCooldown(var1,16800F);
			return;
		}
	}}