package xyz.pixelatedw.mineminenomi.abilities.magu;

import java.awt.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu.DaiFunkaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class DaiFunkaAbility extends Ability {
	// Fields
	private static final float CHARGE_TIME = 20F;
	private static final float COOLDOWN = 500F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DaiFunkaAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final SkinOverlayComponent skinOverlayComponent;
	private final ProjectileComponent projectileComponent;
	private static final AbilityOverlay OVERLAY;

	public DaiFunkaAbility(AbilityCore<DaiFunkaAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<DaiFunkaProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.skinOverlayComponent,this.projectileComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUse))));
	}
	private void onUse(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,20F);
	}
	private void onChargeStart(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.showAll(var1);
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1,3F,1F);
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.MAGU_SFX.get())),SoundSource.PLAYERS,3F,1F);
		this.skinOverlayComponent.hideAll(var1);
		this.cooldownComponent.startCooldown(var1,500F);
	}
	private DaiFunkaProjectile createProjectile(LivingEntity var1) {
		return new DaiFunkaProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Transforms the user's fist into pure magma before expanding and throwing it forward"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(500F),ChargeComponent.getTooltip(20F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DaiFunkaAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.MAGMA).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("dai_funka","Dai Funka",var0));
		OVERLAY = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.LIMB).setTexture(ModResources.DOKU_COATING).setColor(new Color(160,0,0)).build();
	}
}