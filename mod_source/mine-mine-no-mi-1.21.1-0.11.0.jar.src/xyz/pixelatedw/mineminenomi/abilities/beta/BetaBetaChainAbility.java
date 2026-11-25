package xyz.pixelatedw.mineminenomi.abilities.beta;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.beta.BetaBetaChainProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class BetaBetaChainAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 600;
	private static final int COOLDOWN = 120;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BetaBetaChainAbility>> INSTANCE;
	private static final AbilityOverlay OVERLAY;
	private final ContinuousComponent continuousComponent;
	private final ProjectileComponent projectileComponent;
	private final SwingTriggerComponent swingTriggerComponent;
	private final SkinOverlayComponent skinOverlayComponent;

	public BetaBetaChainAbility(AbilityCore<BetaBetaChainAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<BetaBetaChainProjectile>) (this::createProjectile))))));
		this.swingTriggerComponent = new SwingTriggerComponent((IAbility)(this)).addSwingEvent((SwingTriggerComponent$IOnSwingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent)(this::swingEvent))));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.projectileComponent,this.swingTriggerComponent,this.skinOverlayComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,600F);
	}
	private void swingEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.showAll(var1);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.hideAll(var1);
		this.projectileComponent.shoot(var1,4F,1F);
		this.cooldownComponent.startCooldown(var1,120F);
		AbilityHelper.slowEntityFall(var1,2);
	}
	private BetaBetaChainProjectile createProjectile(LivingEntity var1) {
		return new BetaBetaChainProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user shoots a mucus chain which will propel the user towards where it hits."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BetaBetaChainAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.SLIME).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("beta_beta_chain","Beta Beta Chain",var0));
		OVERLAY = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.ARM).setTexture(ModResources.BETA_COATING).setColor(WyHelper.hexToRGB("#FFFFFFA6")).build();
	}
}