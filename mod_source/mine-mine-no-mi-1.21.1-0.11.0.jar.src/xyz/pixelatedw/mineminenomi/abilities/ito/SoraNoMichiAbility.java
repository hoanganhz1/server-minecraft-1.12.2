package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class SoraNoMichiAbility extends Ability {
	// Fields
	private static final int JUMPS = 12;
	private static final float SHORT_COOLDOWN_PER_STACK = 10F;
	private static final float LONG_COOLDOWN_PER_STACK = 40F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SoraNoMichiAbility>> INSTANCE;
	private final PoolComponent poolComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final StackComponent stackComponent;
	private boolean hasFallDamage;

	public SoraNoMichiAbility(AbilityCore<SoraNoMichiAbility> var1) {
		super((AbilityCore)(var1));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GEPPO_LIKE,new AbilityPool[0]);
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.stackComponent = new StackComponent((IAbility)(this));
		this.hasFallDamage = true;
		AbilityComponent[] var2 = new AbilityComponent[]{this.poolComponent,this.damageTakenComponent,this.stackComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canUse))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.stackComponent.setDefaultStacks(this.getMaxJumps(var1));
		if (!(var1.level().isClientSide())){
			var1.swing(InteractionHand.MAIN_HAND,true);
		}
		int var3 = 1;
		if (var1.onGround()){
			Vec3 var4 = var1.getLookAngle().normalize().multiply(1.1D,1D,1.1D);
			AbilityHelper.setDeltaMovement((Entity)(var1),var4.x,2.4D,var4.z);
		}else{
			Vec3 var4_1 = var1.getLookAngle().normalize().multiply(2.5D,1D,2.5D);
			AbilityHelper.setDeltaMovement((Entity)(var1),var4_1.x,0.8D,var4_1.z);
		}
		this.stackComponent.addStacks(var1,this,-(var3));
		this.hasFallDamage = false;
		if ((this.stackComponent.getStacks()) <= (0)){
			this.cooldownComponent.startCooldown(var1,this.getCooldownTicks());
			this.stackComponent.setStacks(var1,this,this.getMaxJumps(var1));
		}else{
			this.cooldownComponent.startCooldown(var1,10F);
		}
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((!(this.hasFallDamage)) && (var3.is(DamageTypes.FALL))){
			this.resetStacks(var1);
			return 0F;
		}else{
			return var4;
		}
	}
	private void resetStacks(LivingEntity var1) {
		if ((this.stackComponent.getStacks()) != (this.stackComponent.getDefaultStacks())){
			this.cooldownComponent.stopCooldown(var1);
			this.cooldownComponent.startCooldown(var1,this.getCooldownTicks());
		}
		this.stackComponent.setStacks(var1,this,this.getMaxJumps(var1));
		this.stackComponent.setDefaultStacks(this.getMaxJumps(var1));
		this.hasFallDamage = true;
	}
	private int getMaxJumps(LivingEntity var1) {
		return 12;
	}
	private float getCooldownTicks() {
		return ((float)((this.stackComponent.getDefaultStacks()) - (this.stackComponent.getStacks()))) * (40F);
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putBoolean("hasFallDamage",this.hasFallDamage);
	}
	public void loadAdditional(CompoundTag var1) {
		this.hasFallDamage = var1.getBoolean("hasFallDamage");
	}
	private Result canUse(LivingEntity var1, IAbility var2) {
		if ((!((var1.getY()) > (192D))) && ((!(((java.lang.Object)(var1.level().dimension())).equals((Object)(Level.NETHER)))) && (!(((java.lang.Object)(var1.level().dimension())).equals((Object)(Level.END)))))){
			return Result.success();
		}else{
			return Result.fail(ModI18nAbilities.MESSAGE_NEED_CLODUS);
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user attaches the strings to clouds, allowing them to move through the air"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,StackComponent.getTooltip(12)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SoraNoMichiAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("sora_no_michi","Sora no Michi",var0));
	}
}