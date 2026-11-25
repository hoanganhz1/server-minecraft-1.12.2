package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraBaraHoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BaraBaraHoAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 80;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BaraBaraHoAbility>> INSTANCE;
	private final ProjectileComponent projectileComponent;
	private final MorphComponent morphComponent;
	private int morphTick;

	public BaraBaraHoAbility(AbilityCore<BaraBaraHoAbility> var1) {
		super((AbilityCore)(var1));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<BaraBaraHoProjectile>) (this::createProjectile))))));
		this.morphComponent = new MorphComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent,this.morphComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(BaraHelper::hasLimbs))));
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::tickEvent))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void tickEvent(LivingEntity var1, IAbility var2) {
		if ((!(var1.level().isClientSide)) && (this.morphComponent.isMorphed())){
			if ((this.morphTick) > (0)){
				this.morphTick = (this.morphTick) - (1);
			}else{
				this.morphComponent.stopMorph(var1);
			}
		}
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1,2F,1F);
		int var3 = this.projectileComponent.getShotProjectile().getLife();
		this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.BARA_HO.get())));
		this.morphTick = var3;
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.NO_HANDS),var3,0));
		this.cooldownComponent.startCooldown(var1,80F);
	}
	private BaraBaraHoProjectile createProjectile(LivingEntity var1) {
		BaraBaraHoProjectile var2 = new BaraBaraHoProjectile(var1.level(),var1,(IAbility)(this));
		ItemStack var3 = var1.getMainHandItem();
		float var4 = 0F;
		if (ItemsHelper.isSword(var3)){
			var4 = ItemsHelper.getItemDamage(var3);
		}
		var2.setDamage((var2.getDamage()) + (var4));
		return var2;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Launches the user's fist towards the enemy, if the user holds a weapon in hand this will increase the fist's damage as well."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(80F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BaraBaraHoAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("bara_bara_ho","Bara Bara Ho",var0));
	}
}