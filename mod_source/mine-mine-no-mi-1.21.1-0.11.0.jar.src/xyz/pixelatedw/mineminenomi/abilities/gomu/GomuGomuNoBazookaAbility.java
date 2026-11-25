package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoBazookaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoGrizzlyMagnumProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoJetBazookaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoLeoBazookaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class GomuGomuNoBazookaAbility extends Ability {
	// Fields
	private static final Component GOMU_GOMU_NO_JET_BAZOOKA_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"gomu_gomu_no_jet_bazooka","Gomu Gomu no Jet Bazooka"));
	private static final Component GOMU_GOMU_NO_GRIZZLY_MAGNUM_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"gomu_gomu_no_grizzly_magnum","Gomu Gomu no Grizzly Magnum"));
	private static final Component GOMU_GOMU_NO_LEO_BAZOOKA_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"gomu_gomu_no_leo_bazooka","Gomu Gomu no Leo Bazooka"));
	private static final ResourceLocation GOMU_GOMU_NO_BAZOOKA_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/gomu_gomu_no_bazooka.png");
	private static final ResourceLocation GOMU_GOMU_NO_JET_BAZOOKA_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/gomu_gomu_no_jet_bazooka.png");
	private static final ResourceLocation GOMU_GOMU_NO_GRIZZLY_MAGNUM_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/gomu_gomu_no_grizzly_magnum.png");
	private static final ResourceLocation GOMU_GOMU_NO_LEO_BAZOOKA_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/gomu_gomu_no_leo_bazooka.png");
	private static final int NO_GEAR_COOLDOWN = 200;
	private static final int NO_GEAR_CHARGE_TIME = 40;
	private static final int SECOND_GEAR_COOLDOWN = 140;
	private static final int SECOND_GEAR_CHARGE_TIME = 30;
	private static final int THIRD_GEAR_COOLDOWN = 300;
	private static final int THIRD_GEAR_CHARGE_TIME = 60;
	private static final int FOURTH_GEAR_COOLDOWN = 240;
	private static final int FOURTH_GEAR_CHARGE_TIME = 40;
	private static final AbilityDescriptionLine$IDescriptionLine SECOND_GEAR_NAME_DESC = AbilityDescriptionLine$IDescriptionLine.of((Component)(MentionHelper.mentionText(GOMU_GOMU_NO_JET_BAZOOKA_NAME)));
	private static final AbilityDescriptionLine$IDescriptionLine THIRD_GEAR_NAME_DESC = AbilityDescriptionLine$IDescriptionLine.of((Component)(MentionHelper.mentionText(GOMU_GOMU_NO_GRIZZLY_MAGNUM_NAME)));
	private static final AbilityDescriptionLine$IDescriptionLine FOURTH_GEAR_NAME_DESC = AbilityDescriptionLine$IDescriptionLine.of((Component)(MentionHelper.mentionText(GOMU_GOMU_NO_LEO_BAZOOKA_NAME)));
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GomuGomuNoBazookaAbility>> INSTANCE;
	private final AltModeComponent<GomuHelper$Gears> altModeComponent;
	private final ChargeComponent chargeComponent;
	private final ProjectileComponent projectileComponent;
	private final AnimationComponent animationComponent;
	private float cooldown;
	private int chargeTime;
	private float projectileSpeed;
	private float projectileSpread;

	public GomuGomuNoBazookaAbility(AbilityCore<GomuGomuNoBazookaAbility> var1) {
		super((AbilityCore)(var1));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),GomuHelper$Gears.class,GomuHelper$Gears.NO_GEAR,true).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::altModeChangeEvent)))));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<NuProjectileEntity>) (this::createProjectile))))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.cooldown = 200F;
		this.chargeTime = 40;
		this.projectileSpeed = 2F;
		this.projectileSpread = 1F;
		AbilityComponent[] var2 = new AbilityComponent[]{this.altModeComponent,this.chargeComponent,this.projectileComponent,this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresHands))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,(float)(this.chargeTime));
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.GOMU_BAZOOKA);
		if ((((GomuHelper$Gears)(this.altModeComponent.getCurrentMode())).equals(GomuHelper$Gears.GEAR_4)) && ((var1 instanceof Player) && (((Player)(var1)).getAbilities().flying))){
			return;
		}else{
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),this.chargeTime,0));
			return;
		}
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		NuProjectileEntity var3 = this.projectileComponent.getNewProjectile(var1);
		NuProjectileEntity var4 = this.projectileComponent.getNewProjectile(var1);
		Vec3 var5 = Vec3.ZERO;
		Direction var6 = Direction.fromYRot((double)(var1.getYRot()));
		Vec3 var7 = var5.add((double)(Math.abs(var6.getNormal().getX())),(double)(Math.abs(var6.getNormal().getY())),(double)(Math.abs(var6.getNormal().getZ())));
		Vec3 var8 = var7.multiply((double)(this.projectileSpread),1D,(double)(this.projectileSpread));
		var3.moveTo((var1.getX()) + (var8.z),(var1.getEyeY()) - (0.25D),(var1.getZ()) + (var8.x),var1.getYRot(),var1.getXRot());
		var4.moveTo((var1.getX()) - (var8.z),(var1.getEyeY()) - (0.25D),(var1.getZ()) - (var8.x),var1.getYRot(),var1.getXRot());
		this.projectileComponent.shoot(var3,var1,var1.getXRot(),var1.getYRot(),this.projectileSpeed,0F);
		this.projectileComponent.shoot(var4,var1,var1.getXRot(),var1.getYRot(),this.projectileSpeed,0F);
		var1.swing(InteractionHand.MAIN_HAND,true);
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GOMU_SFX.get())),SoundSource.PLAYERS,2F,0.75F);
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.NO_HANDS),10,1));
		this.cooldownComponent.startCooldown(var1,this.cooldown);
	}
	private boolean altModeChangeEvent(LivingEntity var1, IAbility var2, GomuHelper$Gears var3) {
		switch (GomuGomuNoBazookaAbility$1.$SwitchMap$xyz$pixelatedw$mineminenomi$abilities$gomu$GomuHelper$Gears[var3.ordinal()]){
		case 1:
			this.setDisplayName(GOMU_GOMU_NO_JET_BAZOOKA_NAME);
			this.cooldown = 140F;
			this.chargeTime = 30;
			break;
		case 2:
			this.setDisplayName(GOMU_GOMU_NO_GRIZZLY_MAGNUM_NAME);
			this.cooldown = 300F;
			this.chargeTime = 60;
			break;
		case 3:
			this.setDisplayName(GOMU_GOMU_NO_LEO_BAZOOKA_NAME);
			this.cooldown = 240F;
			this.chargeTime = 40;
			break;
		case 4:
			break;
		case 5:

		default:
			this.setDisplayIcon(GOMU_GOMU_NO_BAZOOKA_ICON);
			this.setDisplayName(((AbilityCore)(INSTANCE.get())).getLocalizedName());
			this.cooldown = 200F;
			this.chargeTime = 40;
			break;
		}
		return true;
	}
	private NuProjectileEntity createProjectile(LivingEntity var1) {
		this.projectileSpeed = 2F;
		this.projectileSpread = 1F;
		NuHorizontalLightningEntity var3 = null;
		switch (GomuGomuNoBazookaAbility$1.$SwitchMap$xyz$pixelatedw$mineminenomi$abilities$gomu$GomuHelper$Gears[((GomuHelper$Gears)(this.altModeComponent.getCurrentMode())).ordinal()]){
		case 3:
			var3 = new GomuGomuNoLeoBazookaProjectile(var1.level(),var1,(Ability)(this));
			this.projectileSpeed = 3F;
			this.projectileSpread = 2.5F;
			break;
		case 2:
			var3 = new GomuGomuNoGrizzlyMagnumProjectile(var1.level(),var1,(Ability)(this));
			this.projectileSpeed = 1.8F;
			this.projectileSpread = 2.5F;
			break;
		case 1:
			var3 = new GomuGomuNoJetBazookaProjectile(var1.level(),var1,(Ability)(this));
			this.projectileSpeed = 3F;
			break;
		default:
			var3 = new GomuGomuNoBazookaProjectile(var1.level(),var1,(Ability)(this));
			this.projectileSpread = 0.5F;
			break;
		}
		return var3;
	}
	public void switchNoGear(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(GomuHelper$Gears.NO_GEAR));
	}
	public void switchSecondGear(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(GomuHelper$Gears.GEAR_2));
	}
	public void switchThirdGear(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(GomuHelper$Gears.GEAR_3));
	}
	public void switchFourthGear(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(GomuHelper$Gears.GEAR_4));
	}
	public void switchFifthGear(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(GomuHelper$Gears.GEAR_5));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Hits the enemy with both hands to launch them away."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,AbilityDescriptionLine$IDescriptionLine.of((Component)(Component.literal(l1))),AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ChargeComponent.getTooltip(40F)};
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,SECOND_GEAR_NAME_DESC,GomuHelper.SECOND_GEAR_REQ,AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(140F),ChargeComponent.getTooltip(30F)};
			AbilityDescriptionLine$IDescriptionLine[] lv1_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,THIRD_GEAR_NAME_DESC,GomuHelper.THIRD_GEAR_REQ,AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(300F),ChargeComponent.getTooltip(60F)};
			AbilityDescriptionLine$IDescriptionLine[] lv1_7 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,FOURTH_GEAR_NAME_DESC,GomuHelper.FOURTH_GEAR_REQ,AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F),ChargeComponent.getTooltip(40F)};
			SourceType[] lv1_8 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GomuGomuNoBazookaAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).addAdvancedDescriptionLine(lv1_6).addAdvancedDescriptionLine(lv1_7).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_8).setSourceElement(SourceElement.RUBBER).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gomu_gomu_no_bazooka","Gomu Gomu no Bazooka",var0));
	}
}