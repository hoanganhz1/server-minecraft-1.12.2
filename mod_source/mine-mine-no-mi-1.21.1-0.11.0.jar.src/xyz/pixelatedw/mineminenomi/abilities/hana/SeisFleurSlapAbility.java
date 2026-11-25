package xyz.pixelatedw.mineminenomi.abilities.hana;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SeisFleurSlapAbility extends Ability {
	// Fields
	private static final int MIL_DISTANCE = 20;
	private static final float DAMAGE_BONUS = 1.25F;
	private static final Component MIL_NAME = Component.translatable(ModRegistry.registerAbilityName("mil_fleur_slap","Mil Fleur: Slap"));
	private static final ResourceLocation NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/seis_fleur_slap.png");
	private static final ResourceLocation MIL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/mil_fleur_slap.png");
	private static final int COOLDOWN = 100;
	private static final int NORMAL_DAMAGE = 6;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SeisFleurSlapAbility>> INSTANCE;
	private final AnimationComponent animationComponent;
	private final AltModeComponent<MilFleurAbility$Mode> altModeComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;

	public SeisFleurSlapAbility(AbilityCore<SeisFleurSlapAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),MilFleurAbility$Mode.class,MilFleurAbility$Mode.NORMAL,true).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::onAltModeChange)))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent,this.altModeComponent,this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	
	private void slap(LivingEntity var1, @Nullable() Entity var2) {
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			LivingEntity var3 = ((LivingEntity)(var2));
			HanaHandsEntity var4 = new HanaHandsEntity(var1.level(),var1,(IAbility)(this));
			var4.setWarmup(0);
			var4.setTarget(var3);
			var4.setDamage(6F);
			var4.setSlap();
			var1.level().addFreshEntity((Entity)(var4));
			Vec3 var5 = var1.position().subtract(var3.position()).normalize();
			AbilityHelper.setDeltaMovement((Entity)(var3),(-(var5.x)) * (3D),(var1.getDeltaMovement().y()) + (0.1D),(-(var5.z)) * (3D));
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
			((java.lang.Object[]) (lv2_2))[0] = ImmutablePair.of((Object)("Slaps the enemy pushing them few blocks back and dealing some damage."),(Object)(null));
			Object[] lv2_3 = new Object[3];
			lv2_3[0] = MilFleurAbility.INSTANCE;
			lv2_3[1] = "§a20§r";
			int lv2_4 = Math.round((Math.abs(-0.25F)) * (100F));
			String lv2_5 = "§a" + lv2_4 + "%§r";
			lv2_3[2] = lv2_5;
			((java.lang.Object[]) (lv2_2))[1] = ImmutablePair.of((Object)("While %s is active it will sprout on every enemy in a %s blocks radius and increase its damage by %s."),lv2_3);
			Component[] lv2_6 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_7 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F),DealDamageComponent.getTooltip(6F)};
			SourceType[] lv2_8 = new SourceType[]{SourceType.FIST,SourceType.INDIRECT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SeisFleurSlapAbility::new)))).addDescriptionLine(lv2_6).addAdvancedDescriptionLine(lv2_7).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_8).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("seis_fleur_slap","Seis Fleur: Slap",var0));
	}
private void useEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.CROSSED_ARMS,7);
		Function<AbilityData, MilFleurAbility> var3 = (l0) -> {
			return ((MilFleurAbility)(l0.getEquippedAbility(((AbilityCore)(MilFleurAbility.INSTANCE.get())))));
		};
		boolean var4 = ((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).map((Function)(((Function<MilFleurAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue();
		this.dealDamageComponent.getBonusManager().removeBonus(HanaHelper.MIL_DAMAGE_BONUS);
		if (var4){
			this.dealDamageComponent.getBonusManager().addBonus(HanaHelper.MIL_DAMAGE_BONUS,"Mil Fleur Damage Bonus",BonusOperation.MUL,1.25F);
			List var5 = this.rangeComponent.getTargetsInArea(var1,20F);
			Iterator var6 = var5.iterator();
			do{
				if (var6.hasNext()){
					this.slap(var1,(Entity)(((LivingEntity)(var6.next()))));
					continue;
				}else{
					break;
				}
			} while (true);
		}else{
			List var5_1 = this.rangeComponent.getTargetsInLine(var1,20F,2F);
			if ((var5_1.size()) > (0)){
				LivingEntity var6_1 = ((LivingEntity)(var5_1.get(0)));
				if (((var6_1) != (null)) && (var6_1.isAlive())){
					this.slap(var1,(Entity)(var6_1));
				}
			}
		}
		HanaHelper.spawnBlossomEffect(var1);
		this.cooldownComponent.startCooldown(var1,100F);
	}}