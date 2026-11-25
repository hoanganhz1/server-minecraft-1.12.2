package xyz.pixelatedw.mineminenomi.abilities.zou;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GreatStompAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 240;
	private static final int RANGE = 10;
	private static final float DAMAGE = 15F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GreatStompAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;

	public GreatStompAbility(AbilityCore<GreatStompAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(ZouHelper::requiresGuardPoint))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		Iterator var3 = this.rangeComponent.getTargetsInArea(var1,10F).iterator();
		do{
			if (var3.hasNext()){
				LivingEntity var4 = ((LivingEntity)(var3.next()));
				if (this.dealDamageComponent.hurtTarget(var1,var4,15F)){
					var4.setPos(var4.getX(),(var4.getY()) + (3D),var4.getZ());
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GREAT_STOMP.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		this.cooldownComponent.startCooldown(var1,240F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("By stomping the ground as a full-form elephant, the user creates a shockwave."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv1_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv1_5 = new Supplier[]{ModMorphs.ZOU_GUARD};
			lv1_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv1_5);
			AbilityDescriptionLine$IDescriptionLine[] lv1_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F)};
			SourceType[] lv1_7 = new SourceType[]{SourceType.INDIRECT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GreatStompAbility::new)))).addDescriptionLine(lv1_3).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_6).setSourceType(lv1_7).setSourceElement(SourceElement.SHOCKWAVE).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("great_stomp","Great Stomp",var0));
	}
}