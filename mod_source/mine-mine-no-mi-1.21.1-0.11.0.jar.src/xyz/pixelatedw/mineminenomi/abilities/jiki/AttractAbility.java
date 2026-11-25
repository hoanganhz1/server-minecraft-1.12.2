package xyz.pixelatedw.mineminenomi.abilities.jiki;

import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;

public class AttractAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 60;
	private static final int HOLD_TIME = 100;
	private static final int RADIUS = 40;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<AttractAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;

	public AttractAbility(AbilityCore<AttractAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,100F);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		List var3 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var1.position(),40D,TargetPredicate.DEFAULT_AREA_CHECK,Entity.class);
		((java.util.List)(var3)).addAll((Collection)(TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var1.position(),40D,TargetPredicate.EVERYTHING,ItemEntity.class)));
		Holder.Reference var4 = ((Holder.Reference)(((HolderLookup.RegistryLookup)(var1.registryAccess().lookup(Registries.ENCHANTMENT).get())).get(ModEnchantments.KAIROSEKI).orElse(null)));
		Iterator var5 = var3.iterator();
		LOOP_1:
		do{
			if (var5.hasNext()){
				Entity var6 = ((Entity)(var5.next()));
				if (var6 instanceof ItemEntity){
					ItemEntity var7 = ((ItemEntity)(var6));
					if ((!(var7.getItem().isEmpty())) && (!((!(var7.getItem().is(ModTags$Items.MAGNETIC))) || (((var7.getItem().getEnchantmentLevel((Holder)(var4))) > (0)) || (var7.getItem().has(ModDataComponents.IMBUING)))))){
						Vec3 var8 = var7.position().subtract(var1.position().add(0D,1.5D,0D)).multiply(1.2D,1.2D,1.2D);
						double var9 = 8D;
						double var10 = (-(var8.x)) / (var9);
						double var11 = (-(var8.y)) / (var9);
						AbilityHelper.setDeltaMovement((Entity)(var7),var10,var11,(-(var8.z)) / (var9));
						double var12 = Math.sqrt(var1.blockPosition().distSqr((Vec3i)(var7.blockPosition())));
						if ((var12) > (3D)){
							var7.setPickUpDelay((int)(Math.max(5D,(var12) / (3D))));
						}
						if (((this.continuousComponent.getContinueTime()) % (40F)) == (0F)){
							JikiHelper.spawnAttractEffect((Entity)(var7));
							continue;
						}else{
							continue;
						}
					}else{
						continue;
					}
				}else{
					Iterable var7_1 = null;
					if (var6 instanceof LivingEntity){
						var7_1 = ((LivingEntity)(var6)).getAllSlots();
					}else{
						if (var6 instanceof Player){
							Inventory var8_1 = ((Player)(var6)).getInventory();
							var7_1 = Iterables.concat((Iterable)(var8_1.items),(Iterable)(var8_1.armor),(Iterable)(var8_1.offhand));
						}
					}
					if ((var7_1) == (null)){
						continue;
					}else{
						Iterator var8_2 = var7_1.iterator();
						do{
							if (var8_2.hasNext()){
								ItemStack var9_1 = ((ItemStack)(var8_2.next()));
								if ((!(var9_1.isEmpty())) && (!((!(var9_1.is(ModTags$Items.MAGNETIC))) || (((var9_1.getEnchantmentLevel((Holder)(var4))) > (0)) || (var9_1.has(ModDataComponents.IMBUING)))))){
									if (var6 instanceof Mob){
										Mob var10_1 = ((Mob)(var6));
										EquipmentSlot[] var11_1 = EquipmentSlot.values();
										int var12_1 = var11_1.length;
										int var13 = 0;
										do{
											if ((var13) < (var12_1)){
												EquipmentSlot var14 = var11_1[var13];
												if (((java.lang.Object)(var10_1.getItemBySlot(var14))).equals((Object)(var9_1))){
													var10_1.setItemSlot(var14,ItemStack.EMPTY);
												}
												var13++;
												continue;
											}else{
												break;
											}
										} while (true);
									}
									if (HakiHelper.hasImbuingActive(((LivingEntity)(var6)),false,true)){
										continue;
									}else{
										ItemEntity var10_2 = var6.spawnAtLocation(var9_1.copy(),1F);
										if ((var10_2) != (null)){
											var10_2.setPickUpDelay(30);
										}
										var9_1.shrink(var9_1.getCount());
										continue;
									}
								}else{
									continue;
								}
							}else{
								break;
							}
						} while (true);
						continue LOOP_1;
					}
				}
			}else{
				break;
			}
		} while (true);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,60F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Attracts all nearby magnetic objects from the ground or enemy inventories."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(60F),ContinuousComponent.getTooltip(100F),RangeComponent.getTooltip(40F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(AttractAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("attract","Attract",var0));
	}
}