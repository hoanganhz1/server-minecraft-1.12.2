package xyz.pixelatedw.mineminenomi.abilities.karu;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class IngaZarashiAbility extends MorphAbility {
	// Fields
	private static final int MIN_COOLDOWN = 20;
	private static final int MAX_COOLDOWN = 240;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<IngaZarashiAbility>> INSTANCE;
	private static final ResourceLocation ARMOR_MODIFIER_ID;
	private static final ResourceLocation ATTACK_MODIFIER_ID;
	private static final ResourceLocation REACH_MODIFIER_ID;
	private Optional<KarmaAbility> karmaAbility;

	public IngaZarashiAbility(AbilityCore<IngaZarashiAbility> var1) {
		super((AbilityCore)(var1));
		this.karmaAbility = (Optional) (((java.util.Optional)(Optional.empty())));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canUse))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canUse))));
		this.continuousComponent.addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuousEvent))));
		this.continuousComponent.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuousEvent))));
	}
	private void duringContinuousEvent(LivingEntity var1, IAbility var2) {
		if ((((this.continuousComponent.getContinueTime()) % (20F)) == (0F)) && (this.karmaAbility.isPresent())){
			this.updateKarma(var1);
		}
	}
	private void endContinuousEvent(LivingEntity var1, IAbility var2) {
		float var3 = Mth.clamp(this.continuousComponent.getContinueTime(),20F,240F);
		this.cooldownComponent.startCooldown(var1,var3);
	}
	private Result canUse(LivingEntity var1, IAbility var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return Result.fail(null);
		}else{
			KarmaAbility var4 = ((KarmaAbility)(var3.getPassiveAbility(((AbilityCore)(KarmaAbility.INSTANCE.get())))));
			if ((var4) == (null)){
				return Result.fail(null);
			}else{
				this.karmaAbility = (Optional) (Optional.ofNullable(var4));
				return Result.success();
			}
		}
	}
	private void updateKarma(LivingEntity var1) {
		if ((((KarmaAbility)(this.karmaAbility.get())).getPrevKarma()) != (((KarmaAbility)(this.karmaAbility.get())).getKarma())){
			this.statsComponent.removeModifiers(var1);
			Iterator var2 = this.getAttributes().entries().iterator();
			do{
				if (var2.hasNext()){
					Map.Entry var3 = ((Map.Entry)(var2.next()));
					this.statsComponent.removeAttributeModifier(((Holder)(var3.getKey())));
					this.statsComponent.addAttributeModifier(((Holder)(var3.getKey())),((AttributeModifier)(var3.getValue())));
					continue;
				}else{
					break;
				}
			} while (true);
			this.statsComponent.applyModifiers(var1);
			this.morphComponent.updateMorphSize(var1);
			((KarmaAbility)(this.karmaAbility.get())).setPrevKarma(((KarmaAbility)(this.karmaAbility.get())).getKarma());
		}
		((KarmaAbility)(this.karmaAbility.get())).addKarma(var1,-((((KarmaAbility)(this.karmaAbility.get())).getKarma()) / (100F)));
	}
	private Multimap<Holder<Attribute>, AttributeModifier> getAttributes() {
		HashMultimap var1 = HashMultimap.create();
		float var2 = ((KarmaAbility)(this.karmaAbility.get())).getKarma();
		double var3 = (double)(Math.min(((var2) / (100F)) * (8F),8F));
		double var4 = (double)(Math.min(((var2) / (100F)) * (10F),10F));
		double var5 = Math.min(((double)((var2) / (100F))) * (2.5D),2.5D);
		var1.put(Attributes.ARMOR,new AttributeModifier(ARMOR_MODIFIER_ID,var3,AttributeModifier.Operation.ADD_VALUE));
		var1.put(ModAttributes.PUNCH_DAMAGE,new AttributeModifier(ATTACK_MODIFIER_ID,var4,AttributeModifier.Operation.ADD_VALUE));
		AttributeModifier var6 = new AttributeModifier(REACH_MODIFIER_ID,var5,AttributeModifier.Operation.ADD_VALUE);
		var1.put(Attributes.ENTITY_INTERACTION_RANGE,var6);
		var1.put(Attributes.BLOCK_INTERACTION_RANGE,var6);
		return var1;
	}
	public MorphInfo getTransformation() {
		return ((MorphInfo)(ModMorphs.INGA_ZARASHI.get()));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[1];
			Object[] lv1_3 = new Object[]{MentionHelper.mentionText(ModI18n.GUI_KARMA)};
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Increases your physical prowess depending on how much damage you have in your %s counter"),lv1_3);
			Component[] lv1_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(20F,240F),ContinuousComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(IngaZarashiAbility::new)))).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("inga_zarashi","Inga Zarashi",var0));
		ARMOR_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.inga_zarashi.armor");
		ATTACK_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.inga_zarashi.attack");
		REACH_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.inga_zarashi.reach");
	}
}