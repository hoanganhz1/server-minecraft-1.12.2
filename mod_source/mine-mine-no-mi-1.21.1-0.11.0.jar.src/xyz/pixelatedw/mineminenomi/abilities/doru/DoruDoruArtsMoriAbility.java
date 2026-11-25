package xyz.pixelatedw.mineminenomi.abilities.doru;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru.ChampFightProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru.DoruDoruArtsMoriProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DoruDoruArtsMoriAbility extends Ability {
	// Fields
	private static final Component CHAMP_FIGHT_NAME = Component.translatable(ModRegistry.registerAbilityName("champ_fight","Champ Fight"));
	private static final ResourceLocation CHAMP_FIGHT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/champ_fight.png");
	private static final float COOLDOWN = 80F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DoruDoruArtsMoriAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RepeaterComponent repeaterComponent;
	private final AltModeComponent<DoruDoruArtsMoriAbility$Mode> altModeComponent;
	private final ProjectileComponent projectileComponent;
	private final AnimationComponent animationComponent;

	public DoruDoruArtsMoriAbility(AbilityCore<DoruDoruArtsMoriAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addTriggerEvent(100,(RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::repeaterTriggerEvent)))).addStopEvent(100,(RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::repeaterStopEvent))));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),DoruDoruArtsMoriAbility$Mode.class,DoruDoruArtsMoriAbility$Mode.MORI).addChangeModeEvent(100,(AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::changeModeEvent)))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<NuProjectileEntity>) (this::createProjectile))))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.repeaterComponent,this.altModeComponent,this.projectileComponent,this.animationComponent};
		this.addComponents(var2);
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
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (DoruDoruArtsMoriAbility$Mode.CHAMP_FIGHT)){
				this.repeaterComponent.start(var1,25,1);
				this.animationComponent.start(var1,ModAnimations.AIM_SNIPER);
			}else{
				if ((this.altModeComponent.getCurrentMode()) == (DoruDoruArtsMoriAbility$Mode.MORI)){
					this.repeaterComponent.start(var1,1,0);
					this.animationComponent.start(var1,ModAnimations.AIM_SNIPER,7);
				}else{
					return;
				}
			}
			return;
		}
	}
	private void repeaterTriggerEvent(LivingEntity var1, IAbility var2) {
		if (((DoruDoruArtsMoriAbility$Mode)(this.altModeComponent.getCurrentMode())).equals(DoruDoruArtsMoriAbility$Mode.CHAMP_FIGHT)){
			this.projectileComponent.shootWithSpread(var1,2F,1F,2D);
		}else{
			this.projectileComponent.shoot(var1,3F,0F);
		}
	}
	private void repeaterStopEvent(LivingEntity var1, IAbility var2) {
		if ((this.altModeComponent.getCurrentMode()) == (DoruDoruArtsMoriAbility$Mode.CHAMP_FIGHT)){
			this.animationComponent.stop(var1);
		}
		this.continuousComponent.stopContinuity(var1);
		this.cooldownComponent.startCooldown(var1,80F);
	}
	private boolean changeModeEvent(LivingEntity var1, IAbility var2, DoruDoruArtsMoriAbility$Mode var3) {
		if (var3.equals(DoruDoruArtsMoriAbility$Mode.CHAMP_FIGHT)){
			this.setDisplayIcon(CHAMP_FIGHT_ICON);
			this.setDisplayName(CHAMP_FIGHT_NAME);
		}else{
			this.setDisplayIcon(((AbilityCore)(INSTANCE.get())));
			this.setDisplayName(((AbilityCore)(INSTANCE.get())).getLocalizedName());
		}
		return true;
	}
	public void changeToChampFight(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(DoruDoruArtsMoriAbility$Mode.CHAMP_FIGHT));
	}
	public void changeToDefault(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(DoruDoruArtsMoriAbility$Mode.MORI));
	}
	private NuProjectileEntity createProjectile(LivingEntity var1) {
		if (((DoruDoruArtsMoriAbility$Mode)(this.altModeComponent.getCurrentMode())).equals(DoruDoruArtsMoriAbility$Mode.CHAMP_FIGHT)){
			return new ChampFightProjectile(var1.level(),var1,(IAbility)(this));
		}else{
			return new DoruDoruArtsMoriProjectile(var1.level(),var1,(IAbility)(this));
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user fires a harpoon made of wax at the opponent."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(80F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DoruDoruArtsMoriAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.WAX).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("doru_doru_arts_mori","Doru Doru Arts: Mori",var0));
	}
}