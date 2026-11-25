package xyz.pixelatedw.mineminenomi.abilities.baku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.baku.BeroCannonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class BeroCannonAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 40F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BeroCannonAbility>> INSTANCE;
	private final ProjectileComponent projectileComponent;

	public BeroCannonAbility(AbilityCore<BeroCannonAbility> var1) {
		super((AbilityCore)(var1));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<BeroCannonProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canStartAbility))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		List var3 = this.getBlocksInInventory(var1);
		if (!(var3.isEmpty())){
			this.projectileComponent.shoot(var1);
			ItemStack var4 = ((ItemStack)(((java.util.Optional)(var3.stream().findFirst())).orElse((Object)(null))));
			if (((var4) != (null)) && ((var4.getCount()) > (1))){
				var4.shrink(1);
			}
		}
		this.cooldownComponent.startCooldown(var1,40F);
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
	private Result canStartAbility(LivingEntity var1, IAbility var2) {
		if (this.getBlocksInInventory(var1).isEmpty()){
			return Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_BLOCKS);
		}else{
			return Result.success();
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Transforms the user's tongue into a cannon and fires a random block from their inventory."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(40F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.BLUNT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BeroCannonAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("bero_cannon","Bero Cannon",var0));
	}
}