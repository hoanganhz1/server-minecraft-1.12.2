package xyz.pixelatedw.mineminenomi.abilities.baku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.baku.BeroCannonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BakuTsuihoAbility extends Ability {
	// Fields
	private static final float CHARGE_TIME = 60F;
	private static final float COOLDOWN = 160F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BakuTsuihoAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ChargeComponent chargeComponent;
	private final ProjectileComponent projectileComponent;
	private final RepeaterComponent repeaterComponent;
	private int ammo;

	public BakuTsuihoAbility(AbilityCore<BakuTsuihoAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addEndEvent(100,(ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<BeroCannonProjectile>) (this::createProjectile))))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addTriggerEvent(100,(RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::triggerRepeaterEvent)))).addStopEvent(100,(RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::stopRepeaterEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.chargeComponent,this.projectileComponent,this.repeaterComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.repeaterComponent.stop(var1);
			return;
		}else{
			this.chargeComponent.startCharging(var1,60F);
			return;
		}
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		this.ammo = 0;
		List var3 = this.getBlocksInInventory(var1);
		int var4 = 0;
		do{
			if (((this.ammo) < (20)) && ((var4) < (var3.size()))){
				ItemStack var5 = ((ItemStack)(var3.get(var4)));
				ItemStack var6 = var5.copy();
				this.ammo = (this.ammo) + (var6.getCount());
				var5.shrink(this.ammo);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		this.continuousComponent.startContinuity(var1,-1F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.repeaterComponent.start(var1,this.ammo,3);
	}
	private void triggerRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shootWithSpread(var1,3F,4F,1D);
	}
	private void stopRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
		this.cooldownComponent.startCooldown(var1,160F);
	}
	private BeroCannonProjectile createProjectile(LivingEntity var1) {
		return new BeroCannonProjectile(var1.level(),var1,(Ability)(this));
	}
	private List<ItemStack> getBlocksInInventory(LivingEntity var1) {
		ArrayList var2 = new ArrayList();
		Set var3 = DefaultProtectionRules.CORE_FOLIAGE_ORE.getApprovedTags();
		Iterator var4 = ItemsHelper.getInventoryItems(var1).iterator();
		LOOP_1:
		do{
			if (var4.hasNext()){
				ItemStack var5 = ((ItemStack)(var4.next()));
				if (((var5) != (null)) && (!(var5.isEmpty()))){
					Item var6 = var5.getItem();
					if (var6 instanceof BlockItem){
						BlockItem var7 = ((BlockItem)(var6));
						Holder var8 = BuiltInRegistries.BLOCK.wrapAsHolder(var7.getBlock());
						if ((var8) != (null)){
							Iterator var9 = var3.iterator();
							do{
								if (var9.hasNext()){
									if (var8.is(((TagKey)(var9.next())))){
										var2.add(var5);
										continue LOOP_1;
									}else{
										continue;
									}
								}else{
									continue LOOP_1;
								}
							} while (true);
						}else{
							continue;
						}
					}else{
						continue;
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to charge multiple blocks from their inventory in their mouth and shoot them all at the same time."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(160F),ChargeComponent.getTooltip(60F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.BLUNT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BakuTsuihoAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("baku_tsuiho","Baku Tsuiho",var0));
	}
}