package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.BarizodonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BarizodonAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 140;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BarizodonAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RepeaterComponent repeaterComponent;
	private final ProjectileComponent projectileComponent;
	private final ExplosionComponent explosionComponent;

	public BarizodonAbility(AbilityCore<BarizodonAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addTriggerEvent((RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::onRepeaterTrigger)))).addStopEvent((RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::onRepeaterStop))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<BarizodonProjectile>) (this::createProjectile))))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.repeaterComponent,this.projectileComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RyuPteraHelper::requiresEitherPoint))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RyuPteraHelper::requiresEitherPoint))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.repeaterComponent.stop(var1);
			return;
		}else{
			this.continuousComponent.triggerContinuity(var1);
			return;
		}
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		Level var3 = var1.level();
		if (((var3) != (null)) && (!(var3.isClientSide))){
			this.repeaterComponent.start(var1,8,2);
			return;
		}else{
			return;
		}
	}
	private void onRepeaterTrigger(LivingEntity var1, IAbility var2) {
		if (this.canUse(var1).isFail()){
			this.repeaterComponent.stop(var1);
			return;
		}else{
			int var3 = 0;
			do{
				if ((var3) < (WyHelper.randomWithRange(1,4))){
					this.projectileComponent.shoot(var1,3F,10F);
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.DASH_ABILITY_SWOOSH_SFX.get())),SoundSource.PLAYERS,2F,1F);
			return;
		}
	}
	private void onRepeaterStop(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
		this.cooldownComponent.startCooldown(var1,140F);
	}
	private BarizodonProjectile createProjectile(LivingEntity var1) {
		return new BarizodonProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Shoots out a barrage of elliptic air projectiles using the user's wings."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv1_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv1_5 = new Supplier[]{ModMorphs.PTERA_ASSAULT,ModMorphs.PTERA_FLY};
			lv1_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv1_5);
			AbilityDescriptionLine$IDescriptionLine[] lv1_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(140F),ContinuousComponent.getTooltip()};
			SourceType[] lv1_7 = new SourceType[]{SourceType.SLASH};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BarizodonAbility::new)))).addDescriptionLine(lv1_3).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_6).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_7).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("barizodon","Barizodon",var0));
	}
}