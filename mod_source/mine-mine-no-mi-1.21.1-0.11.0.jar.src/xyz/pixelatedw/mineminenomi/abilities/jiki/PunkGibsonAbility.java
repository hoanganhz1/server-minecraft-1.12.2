package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PunkGibsonAbility extends Ability {
	// Fields
	private static final int REQUIRED_IRON = 100;
	private static final int HOLD_TIME = 2400;
	private static final int MIN_COOLDOWN = 60;
	private static final int MAX_COOLDOWN = 600;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PunkGibsonAbility>> INSTANCE;
	private static final AttributeModifier PUNCH_DAMAGE_MODIFIER;
	private static final AttributeModifier REACH_MODIFIER;
	private final ContinuousComponent continuousComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final ChangeStatsComponent statsComponent;
	private final MorphComponent morphComponent;
	private List<ItemStack> magneticItems;
	private boolean dropItems;

	public PunkGibsonAbility(AbilityCore<PunkGibsonAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent((HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::hitEvent))));
		this.statsComponent = new ChangeStatsComponent((IAbility)(this));
		this.morphComponent = new MorphComponent((IAbility)(this));
		this.magneticItems = new ArrayList();
		this.dropItems = true;
		AbilityComponent[] var2 = new AbilityComponent[]{this.hitTriggerComponent,this.continuousComponent,this.statsComponent,this.morphComponent};
		this.addComponents(var2);
		this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE,PUNCH_DAMAGE_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,REACH_MODIFIER);
		this.statsComponent.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE,REACH_MODIFIER);
		this.addCanUseCheck(JikiHelper.getMetalicItemsCheck(100));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,2400F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.PUNK_GIBSON.get())));
		this.statsComponent.applyModifiers(var1);
		if (!(this.magneticItems.isEmpty())){
			this.magneticItems.clear();
		}
		List var3 = ItemsHelper.getAllInventoryItems(var1);
		this.magneticItems = (List) (JikiHelper.getMagneticItemsNeeded(var1,var3,100F));
		this.dropItems = true;
		JikiHelper.spawnAttractEffect((Entity)(var1));
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.morphComponent.stopMorph(var1);
		this.statsComponent.removeModifiers(var1);
		if ((this.dropItems) && ((this.magneticItems.size()) > (0))){
			ItemsHelper.itemBreakParticles(var1,100,((ItemStack)(this.magneticItems.get(0))));
			var1.level().playSound((Player)(null),var1.blockPosition(),SoundEvents.ITEM_BREAK,var1.getSoundSource(),0.5F,1F);
			JikiHelper.dropComponentItems(var1.level(),var1.position(),this.magneticItems);
		}
		float var3 = Mth.clamp(this.continuousComponent.getContinueTime(),60F,600F);
		this.cooldownComponent.startCooldown(var1,var3);
	}
	private HitTriggerComponent$HitResult hitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		return HitTriggerComponent$HitResult.PASS;
	}
	public void activateDamnedPunk(LivingEntity var1) {
		this.statsComponent.removeModifiers(var1);
	}
	public void activatePunkGibson(LivingEntity var1) {
		this.statsComponent.applyModifiers(var1);
	}
	public void stopItemDrops() {
		this.dropItems = false;
	}
	public List<ItemStack> getMagneticItems() {
		return this.magneticItems;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[1];
			Object[] lv1_3 = new Object[]{"§a100§r"};
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Uses %s magnetic items from the user's inventory to create a large arm increasing their punch power and reach."),lv1_3);
			Component[] lv1_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(60F,600F),ContinuousComponent.getTooltip(2400F),ChangeStatsComponent.getTooltip()};
			SourceType[] lv1_6 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PunkGibsonAbility::new)))).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).setSourceType(lv1_6).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("punk_gibson","Punk Gibson",var0));
		PUNCH_DAMAGE_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.punk_gibson.punch_damage"),6D,AttributeModifier.Operation.ADD_VALUE);
		REACH_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.punk_gibson.reach"),2D,AttributeModifier.Operation.ADD_VALUE);
	}
}