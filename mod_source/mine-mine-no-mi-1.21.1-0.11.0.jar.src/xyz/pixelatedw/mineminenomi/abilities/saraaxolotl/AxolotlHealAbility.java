package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AxolotlHealAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 300;
	private static final float RANGE = 15F;
	private static final TargetPredicate TARGET_PREDICATE;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<AxolotlHealAbility>> INSTANCE;
	private final RangeComponent rangeComponent;

	public AxolotlHealAbility(AbilityCore<AxolotlHealAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(SaraAxolotlHelper::requiresEitherPoint))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		List var3 = this.rangeComponent.getTargetsInArea(var1,15F,TARGET_PREDICATE);
		int var4 = var3.size();
		var4 = Math.min(var4,3);
		int var5 = (100) + (Math.min((var4) * (100),300));
		var3.add(var1);
		Iterator var6 = var3.iterator();
		do{
			if (var6.hasNext()){
				((LivingEntity)(var6.next())).addEffect(new MobEffectInstance(MobEffects.REGENERATION,var5,var4));
				continue;
			}else{
				break;
			}
		} while (true);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CHIYUPOPO.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		this.cooldownComponent.startCooldown(var1,300F);
	}
	static  {
		Predicate<Entity> var0 = (l0) -> {
			if (l0 instanceof Player){
				Player lv2_1 = ((Player)(l0));
				Function<DevilFruitData, Boolean> lv2_2 = (l2_0) -> {
				return Boolean.valueOf(l2_0.hasDevilFruit(ModFruits.SARA_SARA_NO_MI_AXOLOTL));
			};
				if (((Boolean)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(lv2_1)))).map((Function)(lv2_2)).orElse(Boolean.valueOf(false)))).booleanValue()){
					return true;
				}
			}
			if (l0 instanceof Axolotl){
				return true;
			}else{
				return false;
			}
		};
		TARGET_PREDICATE = new TargetPredicate().testFriendlyFaction().selector(var0);
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("Regenerates all friendlies nearby based on how many other Axolotls are around."),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			AbilityDescriptionLine$IDescriptionLine[] lv3_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv3_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv3_5 = new Supplier[]{ModMorphs.AXOLOTL_HEAVY,ModMorphs.AXOLOTL_WALK};
			lv3_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv3_5);
			AbilityDescriptionLine$IDescriptionLine[] lv3_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(300F),RangeComponent.getTooltip(15F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(AxolotlHealAbility::new)))).addDescriptionLine(lv3_3).addDescriptionLine(lv3_4).addAdvancedDescriptionLine(lv3_6).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("axolotl_heal","Axolotl Heal",var1));
	}
}