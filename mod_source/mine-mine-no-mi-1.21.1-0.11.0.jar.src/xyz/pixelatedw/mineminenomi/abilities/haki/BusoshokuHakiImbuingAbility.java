package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.HakiDamageTooltip;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class BusoshokuHakiImbuingAbility extends Ability {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BusoshokuHakiImbuingAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final PoolComponent poolComponent;

	public BusoshokuHakiImbuingAbility(AbilityCore<BusoshokuHakiImbuingAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent(100,(HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent(100,(HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHitEvent))));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.IMBUING_BUSOSHOKU_HAKI,new AbilityPool[0]);
		this.setOGCD();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTriggerComponent,this.poolComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canEnableHaki))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (((var1.getMainHandItem().getItem()) == (ModWeapons.ENMA.get())) && (this.continuousComponent.isContinuous())){
			return;
		}else{
			this.continuousComponent.triggerContinuity(var1);
			return;
		}
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.BUSOSHOKU_HAKI_SFX.get())),SoundSource.PLAYERS,2F,1F);
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (HakiHelper.checkForHakiOveruse(var1,1)){
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		Iterator var3 = ItemsHelper.getAllInventoryItems(var1).iterator();
		do{
			if (var3.hasNext()){
				ItemStack var4 = ((ItemStack)(var3.next()));
				if ((!(var4.isEmpty())) && ((ItemsHelper.isImbuable(var4)) && (var4.has(ModDataComponents.IMBUING)))){
					var4.remove(ModDataComponents.IMBUING);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (!(this.continuousComponent.isContinuous())){
			return HitTriggerComponent$HitResult.PASS;
		}else{
			return HitTriggerComponent$HitResult.HIT;
		}
	}
	private boolean onHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		IDamageSourceHandler.getHandler(var3).bypassLogia();
		return true;
	}
	private static boolean canUnlock(LivingEntity var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		HakiData var2 = ((HakiData)(((java.util.Optional)(HakiData.get(var0))).orElse((Object)(null))));
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
		if (((var1) != (null)) && (((var2) != (null)) && ((var3) != (null)))){
			boolean var4 = var1.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get())));
			return (((var3.getDoriki()) > (4000D)) && (((double)(var2.getBusoshokuHakiExp())) > (HakiHelper.getBusoshokuHardeningExpNeeded(var0)))) || (var4);
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("Covers the weapon of the user in Armament haki, making their weapon attacks stronger and being able to damage Logia users."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			SourceType[] lv4_4 = new SourceType[]{SourceType.SLASH};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.HAKI,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BusoshokuHakiImbuingAbility::new)))).addDescriptionLine(lv4_3).setSourceType(lv4_4).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(BusoshokuHakiImbuingAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("busoshoku_haki_imbuing","Busoshoku Haki: Imbuing",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
		};
		ContinuousComponentTooltip.generate(var2,var4);
		Player var5 = var1;
		final Player var5_f1 = var5;
		Consumer<HakiDamageTooltip> var6 = (l0) -> {
			l0.damage((float)(HakiHelper.getBasicBusoshokuHakiDamageBoost((LivingEntity)(var5_f1),1F)));
		};
		HakiDamageTooltip.generate(var2,var6);
	}
}