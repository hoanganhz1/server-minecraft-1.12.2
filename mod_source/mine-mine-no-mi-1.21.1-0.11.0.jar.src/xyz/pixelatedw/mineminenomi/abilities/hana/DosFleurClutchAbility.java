package xyz.pixelatedw.mineminenomi.abilities.hana;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.HanaHandsEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DosFleurClutchAbility extends Ability {
	// Fields
	private static final int MIL_DISTANCE = 30;
	private static final float DAMAGE_BONUS = 1.5F;
	private static final Component MIL_NAME = Component.translatable(ModRegistry.registerAbilityName("mil_fleur_clutch","Mil Fleur: Clutch"));
	private static final ResourceLocation NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/dos_fleur_clutch.png");
	private static final ResourceLocation MIL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/mil_fleur_clutch.png");
	private static final int COOLDOWN = 120;
	private static final int NORMAL_DAMAGE = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DosFleurClutchAbility>> INSTANCE;
	private final AnimationComponent animationComponent;
	private final AltModeComponent<MilFleurAbility$Mode> altModeComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;

	public DosFleurClutchAbility(AbilityCore<DosFleurClutchAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),MilFleurAbility$Mode.class,MilFleurAbility$Mode.NORMAL,true).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::onAltModeChange)))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent,this.altModeComponent,this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	
	private void clutch(LivingEntity var1, @Nullable() Entity var2) {
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			LivingEntity var3 = ((LivingEntity)(var2));
			var3.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),20,1));
			HanaHandsEntity var4 = new HanaHandsEntity(var1.level(),var1,(IAbility)(this));
			var4.setClutch();
			var4.setWarmup(5);
			var4.setTarget(var3);
			var4.setDamage(10F);
			var1.level().addFreshEntity((Entity)(var4));
			HanaHelper.spawnBlossomEffect(var3);
		}
	}
	private boolean onAltModeChange(LivingEntity var1, IAbility var2, MilFleurAbility$Mode var3) {
		if ((var3) == (MilFleurAbility$Mode.NORMAL)){
			this.setDisplayName(((AbilityCore)(INSTANCE.get())).getLocalizedName());
			this.setDisplayIcon(NORMAL_ICON);
		}else{
			if ((var3) == (MilFleurAbility$Mode.MIL_FLEUR)){
				this.setDisplayName(MIL_NAME);
				this.setDisplayIcon(MIL_ICON);
			}else{
				return true;
			}
		}
		return true;
	}
	public void switchNormalMode(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(MilFleurAbility$Mode.NORMAL));
	}
	public void switchMilMode(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(MilFleurAbility$Mode.MIL_FLEUR));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[2];
			((java.lang.Object[]) (lv2_2))[0] = ImmutablePair.of((Object)("Sprouts two hands to cover the opponent, and then bends them backward with bone-breaking results."),(Object)(null));
			Object[] lv2_3 = new Object[3];
			lv2_3[0] = MilFleurAbility.INSTANCE;
			lv2_3[1] = "§a30§r";
			int lv2_4 = Math.round((Math.abs(-0.5F)) * (100F));
			String lv2_5 = "§a" + lv2_4 + "%§r";
			lv2_3[2] = lv2_5;
			((java.lang.Object[]) (lv2_2))[1] = ImmutablePair.of((Object)("While %s is active it will sprout on every enemy in a %s blocks radius and increase its damage by %s."),lv2_3);
			Component[] lv2_6 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_7 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(120F),DealDamageComponent.getTooltip(10F)};
			SourceType[] lv2_8 = new SourceType[]{SourceType.FIST,SourceType.INDIRECT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DosFleurClutchAbility::new)))).addDescriptionLine(lv2_6).addAdvancedDescriptionLine(lv2_7).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_8).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("dos_fleur_clutch","Dos Fleur: Clutch",var0));
	}
private void useEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.CROSSED_ARMS,7);
		Function<AbilityData, MilFleurAbility> var3 = (l0) -> {
			return ((MilFleurAbility)(l0.getEquippedAbility(((AbilityCore)(MilFleurAbility.INSTANCE.get())))));
		};
		boolean var4 = ((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).map((Function)(((Function<MilFleurAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue();
		this.dealDamageComponent.getBonusManager().removeBonus(HanaHelper.MIL_DAMAGE_BONUS);
		if (var4){
			this.dealDamageComponent.getBonusManager().addBonus(HanaHelper.MIL_DAMAGE_BONUS,"Mil Fleur Damage Bonus",BonusOperation.MUL,1.5F);
			List var5 = this.rangeComponent.getTargetsInArea(var1,30F);
			Iterator var6 = var5.iterator();
			do{
				if (var6.hasNext()){
					this.clutch(var1,(Entity)(((LivingEntity)(var6.next()))));
					continue;
				}else{
					break;
				}
			} while (true);
		}else{
			List var5_1 = this.rangeComponent.getTargetsInLine(var1,30F,2F);
			if ((var5_1.size()) > (0)){
				LivingEntity var6_1 = ((LivingEntity)(var5_1.get(0)));
				if (((var6_1) != (null)) && (var6_1.isAlive())){
					this.clutch(var1,(Entity)(var6_1));
				}
			}
		}
		HanaHelper.spawnBlossomEffect(var1);
		this.cooldownComponent.startCooldown(var1,120F);
	}}