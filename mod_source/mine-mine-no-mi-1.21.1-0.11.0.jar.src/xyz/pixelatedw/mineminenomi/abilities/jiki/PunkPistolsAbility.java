package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.List;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.RepeaterAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.PunkPistolProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class PunkPistolsAbility extends RepeaterAbility {
	// Fields
	private static final int VALUE_PER_SPEAR = 5;
	private static final int SPEARS = 6;
	private static final int INTERVAL = 4;
	private static final int COOLDOWN = 240;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PunkPistolsAbility>> INSTANCE;
	private final AnimationComponent animationComponent;

	public PunkPistolsAbility(AbilityCore<PunkPistolsAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.repeaterComponent.addTriggerEvent((RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::triggerRepeaterEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.AIM_SNIPER);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
	}
	private void triggerRepeaterEvent(LivingEntity var1, IAbility var2) {
		if (!(JikiHelper.hasEnoughIron(var1,ItemsHelper.getAllInventoryItems(var1),5F))){
			WyHelper.sendMessage(var1,ModI18nAbilities.MESSAGE_NOT_ENOUGH_MATERIALS);
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			JikiHelper.spawnAttractEffect((Entity)(var1));
			return;
		}
	}
	public int getMaxTriggers() {
		return 6;
	}
	public int getTriggerInterval() {
		return 4;
	}
	public float getRepeaterCooldown() {
		return 240F;
	}
	public PunkPistolProjectile getProjectileFactory(LivingEntity var1) {
		List var2 = JikiHelper.getMagneticItemsNeeded(var1,ItemsHelper.getAllInventoryItems(var1),5F);
		return new PunkPistolProjectile(var1.level(),var1,var2,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[1];
			Object[] lv1_3 = new Object[]{MentionHelper.mentionText(Integer.valueOf(30)),MentionHelper.mentionText(Integer.valueOf(6))};
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Uses %s magnetic objects from the user's inventory to form %s spears and shoots them at enemies."),lv1_3);
			Component[] lv1_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PunkPistolsAbility::new)))).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("punk_pistols","Punk Pistols",var0));
	}
}