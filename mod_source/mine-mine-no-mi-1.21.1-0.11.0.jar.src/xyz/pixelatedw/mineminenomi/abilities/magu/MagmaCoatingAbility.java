package xyz.pixelatedw.mineminenomi.abilities.magu;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class MagmaCoatingAbility extends PunchAbility {
	// Fields
	private static final float THRESHOLD = 600F;
	private static final float COOLDOWN = 200F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<MagmaCoatingAbility>> INSTANCE;
	private final SkinOverlayComponent skinOverlayComponent;
	private static final AbilityOverlay OVERLAY;

	public MagmaCoatingAbility(AbilityCore<MagmaCoatingAbility> var1) {
		super((AbilityCore)(var1));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		this.continuousComponent.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.skinOverlayComponent};
		this.addComponents(var2);
		this.clearUseChecks();
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.showAll(var1);
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.consumeUseAndStop();
		this.skinOverlayComponent.hideAll(var1);
	}
	public float getPunchDamage() {
		return 25F;
	}
	public float getPunchCooldown() {
		return 200F;
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		AbilityHelper.setSecondsOnFireBy((Entity)(var2),5,var1);
		return true;
	}
	public float getPunchHoldTime() {
		return 600F;
	}
	public int getUseLimit() {
		return -1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user coats their arm with magma"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(MagmaCoatingAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(lv1_4).setSourceElement(SourceElement.MAGMA).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("magma_coating","Magma Coating",var0));
		OVERLAY = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.LIMB).setTexture(ModResources.DOKU_COATING).setColor(new Color(160,0,0)).build();
	}
}