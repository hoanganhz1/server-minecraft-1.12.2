package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent$IOnShootEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.HiNoToriBoshiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KaenBoshiAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 100F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KaenBoshiAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final BowTriggerComponent bowTriggerComponent;
	private final ProjectileComponent projectileComponent;

	public KaenBoshiAbility(AbilityCore<KaenBoshiAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this));
		this.bowTriggerComponent = new BowTriggerComponent((IAbility)(this)).addShootEvent((BowTriggerComponent$IOnShootEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent$IOnShootEvent)(this::shoot))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<HiNoToriBoshiProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.bowTriggerComponent,this.projectileComponent};
		this.addComponents(var2);
		this.setDisplayIcon(((AbilityCore)(HiNoToriBoshiAbility.INSTANCE.get())));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	public void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	public boolean shoot(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			boolean var3 = HissatsuAbility.checkHitScan(var1);
			this.projectileComponent.setHitScan(var3);
			this.projectileComponent.shoot(var1,4F,1F);
			this.continuousComponent.stopContinuity(var1);
			this.cooldownComponent.startCooldown(var1,100F);
			return true;
		}else{
			return false;
		}
	}
	public HiNoToriBoshiProjectile createProjectile(LivingEntity var1) {
		HiNoToriBoshiProjectile var2 = new HiNoToriBoshiProjectile(var1.level(),var1,(IAbility)(this));
		var2.setPos(var1.getX(),(var1.getY()) + ((double)(var1.getEyeHeight())),var1.getZ());
		var2.setDamage(10F);
		return var2;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Fires a flaming pellet that sets the target on fire"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F),ContinuousComponent.getTooltip()};
			SourceType[] lv1_5 = new SourceType[]{SourceType.BULLET};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KaenBoshiAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.FIRE).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kaen_boshi","Kaen Boshi",var0));
	}
}