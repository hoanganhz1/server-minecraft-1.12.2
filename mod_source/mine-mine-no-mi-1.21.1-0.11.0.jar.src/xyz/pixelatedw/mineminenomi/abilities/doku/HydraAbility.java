package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.HydraProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class HydraAbility extends Ability {
	// Fields
	private static final Component VENOM_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"hydra_venom","Venom Hydra"));
	private static final ResourceLocation NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/hydra.png");
	private static final ResourceLocation VENOM_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/hydra_venom.png");
	private static final int COOLDOWN = 100;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HydraAbility>> INSTANCE;
	private final ProjectileComponent projectileComponent;
	private final AltModeComponent<HydraAbility$Mode> altModeComponent;

	public HydraAbility(AbilityCore<HydraAbility> var1) {
		super((AbilityCore)(var1));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<HydraProjectile>) (this::createProjectile))))));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),HydraAbility$Mode.class,HydraAbility$Mode.NORMAL,true).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::onAltModeChange)))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent,this.altModeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1,(((MorphInfo)(ModMorphs.VENOM_DEMON.get())).isActive(var1)) ? (4F) : (2F),0F);
		this.cooldownComponent.startCooldown(var1,100F);
	}
	private HydraProjectile createProjectile(LivingEntity var1) {
		boolean var2 = ((MorphInfo)(ModMorphs.VENOM_DEMON.get())).isActive(var1);
		return new HydraProjectile(var1.level(),var1,(IAbility)(this),var2);
	}
	public void setNormalMode(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(HydraAbility$Mode.NORMAL));
	}
	public void setVenomMode(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(HydraAbility$Mode.VENOM));
	}
	private boolean onAltModeChange(LivingEntity var1, IAbility var2, HydraAbility$Mode var3) {
		if ((var3) == (HydraAbility$Mode.VENOM)){
			this.setDisplayName(VENOM_NAME);
			this.setDisplayIcon(VENOM_ICON);
		}else{
			if ((var3) == (HydraAbility$Mode.NORMAL)){
				this.setDisplayName(((AbilityCore)(INSTANCE.get())).getLocalizedName());
				this.setDisplayIcon(NORMAL_ICON);
			}else{
				return true;
			}
		}
		return true;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Launches a dragon made out of liquid poison at the opponent"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HydraAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.POISON).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("hydra","Hydra",var0));
	}
}