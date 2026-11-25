package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoRocketProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class GomuGomuNoRocketAbility extends Ability {
	// Fields
	private static final Component GOMU_GOMU_NO_DAWN_ROCKET_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"gomu_gomu_no_dawn_rocket","Gomu Gomu no Dawn Rocket"));
	private static final ResourceLocation GOMU_GOMU_NO_ROCKET_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/gomu_gomu_no_rocket.png");
	private static final ResourceLocation GOMU_GOMU_NO_DAWN_ROCKET_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/gomu_gomu_no_dawn_rocket.png");
	private static final int COOLDOWN = 60;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GomuGomuNoRocketAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final SwingTriggerComponent swingTriggerComponent;
	private final ProjectileComponent projectileComponent;
	private final AltModeComponent<GomuHelper$Gears> altModeComponent;
	private int airTime;

	public GomuGomuNoRocketAbility(AbilityCore<GomuGomuNoRocketAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.swingTriggerComponent = new SwingTriggerComponent((IAbility)(this)).addSwingEvent((SwingTriggerComponent$IOnSwingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent)(this::triggerSwingEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<NuProjectileEntity>) (this::createProjectile))))));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),GomuHelper$Gears.class,GomuHelper$Gears.NO_GEAR,true).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::altModeChangeEvent)))));
		this.airTime = 0;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.swingTriggerComponent,this.projectileComponent,this.altModeComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresHands))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.airTime = 0;
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((!(var1.onGround())) && ((this.airTime) < (10))){
			AbilityHelper.slowEntityFall(var1,10);
			this.airTime = (this.airTime) + (1);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,60F);
	}
	private void triggerSwingEvent(LivingEntity var1, IAbility var2) {
		if (!(this.continuousComponent.isContinuous())){
			return;
		}else{
			float var3 = (((GomuHelper$Gears)(this.altModeComponent.getCurrentMode())).equals(GomuHelper$Gears.GEAR_2)) ? (4F) : (3.125F);
			this.projectileComponent.shoot(var1,var3,0F);
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GOMU_SFX.get())),SoundSource.PLAYERS,2F,1F);
			var1.swing(InteractionHand.MAIN_HAND,true);
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.NO_HANDS),10,0));
			this.continuousComponent.stopContinuity(var1);
			return;
		}
	}
	private boolean altModeChangeEvent(LivingEntity var1, IAbility var2, GomuHelper$Gears var3) {
		switch (GomuGomuNoRocketAbility$1.$SwitchMap$xyz$pixelatedw$mineminenomi$abilities$gomu$GomuHelper$Gears[var3.ordinal()]){
		case 1:

		case 2:

		case 3:

		case 4:

		case 5:
			this.setDisplayIcon(GOMU_GOMU_NO_ROCKET_ICON);
			this.setDisplayName(((AbilityCore)(INSTANCE.get())).getLocalizedName());
			break;
		default:
			this.setDisplayIcon(GOMU_GOMU_NO_ROCKET_ICON);
			this.setDisplayName(((AbilityCore)(INSTANCE.get())).getLocalizedName());
			break;
		}
		return true;
	}
	private NuProjectileEntity createProjectile(LivingEntity var1) {
		return new GomuGomuNoRocketProjectile(var1.level(),var1,(Ability)(this));
	}
	public void switchNoGear(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(GomuHelper$Gears.NO_GEAR));
	}
	public void switchFifthGear(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(GomuHelper$Gears.GEAR_5));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Stretches towards a block, then launches the user on an arch depending on where they fist landed."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GomuGomuNoRocketAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).setSourceElement(SourceElement.RUBBER).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gomu_gomu_no_rocket","Gomu Gomu no Rocket",var0));
	}
}