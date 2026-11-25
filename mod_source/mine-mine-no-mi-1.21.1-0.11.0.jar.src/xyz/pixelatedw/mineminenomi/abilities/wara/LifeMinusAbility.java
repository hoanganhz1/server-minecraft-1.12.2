package xyz.pixelatedw.mineminenomi.abilities.wara;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.StrawDollItem;

public class LifeMinusAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<LifeMinusAbility>> INSTANCE;
	private final DealDamageComponent dealDamageComponent;
	private final DamageTakenComponent damageTakenComponent;

	public LifeMinusAbility(AbilityCore<LifeMinusAbility> var1) {
		super((AbilityCore)(var1));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.dealDamageComponent,this.damageTakenComponent};
		this.addComponents(var2);
	}
	private void spawnParticles(ServerLevel var1, double var2, double var3, double var4) {
		int var5 = 0;
		do{
			if ((var5) < (5)){
				double var6 = (WyHelper.randomDouble()) / (2D);
				double var7 = (WyHelper.randomDouble()) / (2D);
				double var8 = (WyHelper.randomDouble()) / (2D);
				var1.sendParticles((ParticleOptions)(ParticleTypes.DRAGON_BREATH),(var2) + (var6),(var3) + (var7),(var4) + (var8),25,0D,0D,0D,0.1D);
				var5++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Transfers the damage taken to a Straw Doll in your inventory."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			SourceType[] lv2_4 = new SourceType[]{SourceType.FRIENDLY,SourceType.INTERNAL};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(LifeMinusAbility::new)))).addDescriptionLine(lv2_3).setSourceType(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("life_minus","Life Minus",var0));
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if (super.canUse(var1).isFail()){
			return var4;
		}else{
			if (var1 instanceof Player){
				Player var5 = ((Player)(var1));
				int var6 = 0;
				do{
					if ((var6) < (var5.getInventory().items.size())){
						ItemStack var7 = var5.getInventory().getItem(var6);
						if ((var7.getItem()) == (ModItems.STRAW_DOLL.get())){
							Pair var8 = SoulboundItemHelper.getOwner(var5.level(),var7);
							StrawDollItem.alterStrawDollHealth(var7,-(var4));
							if ((var5) == (var8.getValue())){
								return var4;
							}else{
								if ((var8.getValue()) == (null)){

								}else{
									AbilityDamageSource var9 = this.dealDamageComponent.getDamageSource(var1);
									IDamageSourceHandler.getHandler(var9).bypassFriendly();
									if (((this.dealDamageComponent.hurtTarget(var1,((LivingEntity)(var8.getValue())),var4,var9)) && ((((LivingEntity)(var8.getValue())).getHealth()) <= (0F))) || ((StrawDollItem.getStrawDollHealth(var7)) <= (0F))){
										this.spawnParticles(((ServerLevel)(var5.level())),var5.getX(),var5.getY(),var5.getZ());
										this.spawnParticles(((ServerLevel)(((LivingEntity)(var8.getValue())).level())),((LivingEntity)(var8.getValue())).getX(),((LivingEntity)(var8.getValue())).getY(),((LivingEntity)(var8.getValue())).getZ());
										Consumer<EntityStatsData> var10 = (l0) -> {
			l0.setStrawDoll(true);
		};
										((java.util.Optional)(EntityStatsData.get(((LivingEntity)(var8.getValue()))))).ifPresent((Consumer)(var10));
										var5.getInventory().removeItem(var7);
									}
									return 0F;
								}
							}
						}
						var6++;
						continue;
					}else{
						break;
					}
				} while (true);
				return var4;
			}else{
				return var4;
			}
		}
	}
}