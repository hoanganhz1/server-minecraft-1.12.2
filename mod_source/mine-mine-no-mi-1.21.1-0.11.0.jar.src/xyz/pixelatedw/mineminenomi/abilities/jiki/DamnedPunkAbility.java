package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.DamnedPunkProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class DamnedPunkAbility extends Ability {
	// Fields
	private static final int VALUE_PER_PROJECTILE = 15;
	private static final int HOLD_TIME = 100;
	private static final int COOLDOWN = 400;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DamnedPunkAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ProjectileComponent projectileComponent;
	private final MorphComponent morphComponent;
	private final ExplosionComponent explosionComponent;
	private DamnedPunkProjectile proj;

	public DamnedPunkAbility(AbilityCore<DamnedPunkAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<DamnedPunkProjectile>) (this::createProjectile))))));
		this.morphComponent = new MorphComponent((IAbility)(this));
		this.explosionComponent = new ExplosionComponent((IAbility)(this),1508);
		this.proj = null;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.projectileComponent,this.morphComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(JikiHelper::requiresPunkGibson))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canUse))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(JikiHelper::requiresPunkGibson))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,100F);
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		List var3 = ItemsHelper.getAllInventoryItems(var1);
		if (!(JikiHelper.hasEnoughIron(var1,var3,15F))){
			WyHelper.sendMessage(var1,ModI18nAbilities.MESSAGE_NOT_ENOUGH_MATERIALS);
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			if (var1.level().isClientSide){
				return;
			}else{
				if ((this.proj) == (null)){
					this.projectileComponent.shoot(var1);
					JikiHelper.spawnAttractEffect((Entity)(var1));
				}else{
					if ((this.proj.getLife()) <= (0)){
						this.proj.setMaxLife(7);
						List var4 = JikiHelper.getMagneticItemsNeeded(var1,var3,15F);
						this.proj.magneticItems.addAll((Collection)(var4));
					}else{
						return;
					}
				}
				return;
			}
		}
	}
	private DamnedPunkProjectile createProjectile(LivingEntity var1) {
		List var2 = JikiHelper.getMagneticItemsNeeded(var1,ItemsHelper.getAllInventoryItems(var1),15F);
		this.proj = new DamnedPunkProjectile(var1.level(),var1,var2,(IAbility)(this));
		return this.proj;
	}
	private Result canUse(LivingEntity var1, IAbility var2) {
		if (!(JikiHelper.hasEnoughIron(var1,ItemsHelper.getAllInventoryItems(var1),15F))){
			return Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_MATERIALS);
		}else{
			return Result.success();
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[1];
			Object[] lv3_3 = new Object[]{MentionHelper.mentionText(Integer.valueOf(15))};
			((java.lang.Object[]) (lv3_2))[0] = ImmutablePair.of((Object)("Transforms the users arm into a railgun that shoots a projectile with every swing using %s magnetic objects from the users inventory per shoot, dealing massive damage on impact."),lv3_3);
			Component[] lv3_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			AbilityDescriptionLine$IDescriptionLine[] lv3_5 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv3_5[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv3_6 = new Supplier[]{ModMorphs.PUNK_GIBSON};
			lv3_5[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv3_6);
			AbilityDescriptionLine$IDescriptionLine[] lv3_7 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(400F),ContinuousComponent.getTooltip(100F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DamnedPunkAbility::new)))).addDescriptionLine(lv3_4).addDescriptionLine(lv3_5).addAdvancedDescriptionLine(lv3_7).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("damned_punk","Damned Punk",var0));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.proj = null;
		this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.DAMNED_PUNK.get())));
		Function<AbilityData, PunkGibsonAbility> var3 = (l0) -> {
			return ((PunkGibsonAbility)(l0.getEquippedAbility(((AbilityCore)(PunkGibsonAbility.INSTANCE.get())))));
		};
		PunkGibsonAbility var4 = ((PunkGibsonAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).orElse(null)));
		if (((var4) != (null)) && (var4.isContinuous())){
			var4.activateDamnedPunk(var1);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.proj) != (null)){
			this.proj.startFade(7,7);
			this.proj.setMaxLife(7);
		}
		this.morphComponent.stopMorph(var1);
		Function<AbilityData, PunkGibsonAbility> var3 = (l0) -> {
			return ((PunkGibsonAbility)(l0.getEquippedAbility(((AbilityCore)(PunkGibsonAbility.INSTANCE.get())))));
		};
		PunkGibsonAbility var4 = ((PunkGibsonAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).orElse(null)));
		if (((var4) != (null)) && (var4.isContinuous())){
			var4.activatePunkGibson(var1);
		}
		this.cooldownComponent.startCooldown(var1,400F);
	}
}