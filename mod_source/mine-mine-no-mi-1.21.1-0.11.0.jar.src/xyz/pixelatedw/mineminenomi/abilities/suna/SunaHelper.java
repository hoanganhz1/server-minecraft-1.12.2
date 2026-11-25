package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.Tags;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class SunaHelper {
	// Fields
	public static final UUID DESERT_COOLDOWN_BONUS = UUID.fromString("6d311e6b-84b8-4c9d-b67b-f93116bbc397");
	public static final UUID DESERT_DAMAGE_BONUS = UUID.fromString("9f5c3cf8-d241-4895-a33d-a2f1be34e163");
	public static final UUID DESERT_CHARGE_BONUS = UUID.fromString("415a4bf3-1c81-4b93-a705-5e5661dd0bae");
	public static final UUID DESERT_RANGE_BONUS = UUID.fromString("e5e79903-2ee4-49e5-b462-dfa51a68af0a");

	public static Result requiresInactiveDesertGirasole(LivingEntity var0, IAbility var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return Result.fail(null);
		}else{
			DesertGirasoleAbility var3 = ((DesertGirasoleAbility)(var2.getEquippedAbility(((AbilityCore)(DesertGirasoleAbility.INSTANCE.get())))));
			if (((var3) != (null)) && (var3.isCharging())){
				Object[] var4 = new Object[]{var1.getDisplayName().getString(),var3.getDisplayName().getString()};
				return Result.fail((Component)(Component.translatable(ModI18nAbilities.MESSAGE_CANNOT_USE_TOGETHER,var4)));
			}else{
				return Result.success();
			}
		}
	}
	public static Result requiresInactiveGroundDeath(LivingEntity var0, IAbility var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return Result.fail(null);
		}else{
			GroundDeathAbility var3 = ((GroundDeathAbility)(var2.getEquippedAbility(((AbilityCore)(GroundDeathAbility.INSTANCE.get())))));
			if (((var3) != (null)) && (var3.isCharging())){
				Object[] var4 = new Object[]{var1.getDisplayName().getString(),var3.getDisplayName().getString()};
				return Result.fail((Component)(Component.translatable(ModI18nAbilities.MESSAGE_CANNOT_USE_TOGETHER,var4)));
			}else{
				return Result.success();
			}
		}
	}
	public static boolean isWet(LivingEntity var0) {
		BlockPos var1 = var0.blockPosition();
		int var2 = ((var0.level().isRainingAt(var1)) || (var0.level().isRainingAt(new BlockPos(var1.getX(),(int)(var0.getBoundingBox().maxY),var1.getZ())))) ? (1) : (0);
		int var3 = ((!(var0.getMainHandItem().isEmpty())) && ((var0.getMainHandItem().getItem()) == (ModWeapons.UMBRELLA.get()))) ? (1) : (0);
		int var4 = ((!(var0.getOffhandItem().isEmpty())) && ((var0.getOffhandItem().getItem()) == (ModWeapons.UMBRELLA.get()))) ? (1) : (0);
		int var5 = (((var3) != (0)) || ((var4) != (0))) ? (1) : (0);
		if (((var2) != (0)) && ((var5) != (0))){
			return false;
		}else{
			if (var0.isInWaterOrRain()){
				return true;
			}else{
				return false;
			}
		}
	}
	public static boolean isFruitBoosted(LivingEntity var0) {
		Holder var1 = var0.getCommandSenderWorld().getBiome(var0.blockPosition());
		return (var1.is(Tags.Biomes.IS_DESERT)) || (var1.is(BiomeTags.IS_BEACH));
	}
	public static void drainLiquids(LivingEntity var0, int var1, int var2, int var3) {
		if (((var0) != (null)) && (var0.isAlive())){
			int var4 = 0;
			int var5 = 0;
			int var6 = 0;
			Iterator var7 = new ArrayList(var0.getActiveEffects()).iterator();
			do{
				if (var7.hasNext()){
					MobEffectInstance var8 = ((MobEffectInstance)(var7.next()));
					if (((var8) == (null)) || (!(((var1) > (var6)) && ((((MobEffect)(var8.getEffect().value())).isBeneficial()) && (((1) >= (var8.getAmplifier())) && (!(var8.getEffect() instanceof IColorOverlayEffect))))))){
						continue;
					}else{
						var6++;
						Holder var9 = var8.getEffect();
						var0.removeEffect(var9);
						if (var0 instanceof ServerPlayer){
							((ServerPlayer)(var0)).connection.send((Packet)(new ClientboundRemoveMobEffectPacket(var0.getId(),var9)));
							continue;
						}else{
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			if (var0 instanceof Player){
				Player var8_1 = ((Player)(var0));
				Item[] var9_1 = new Item[]{Items.POTION,Items.LINGERING_POTION,Items.SPLASH_POTION};
				ArrayList var10 = new ArrayList((Collection)(Arrays.asList(var9_1)));
				int var11 = 0;
				do{
					if ((var11) < (var8_1.getInventory().getContainerSize())){
						if ((var10.contains(var8_1.getInventory().getItem(var11).getItem())) && ((var2) > (var4))){
							var8_1.getInventory().setItem(var11,new ItemStack((ItemLike)(Items.AIR)));
							var4++;
						}else{
							if ((var8_1.getInventory().getItem(var11).getItem() instanceof BucketItem) && ((var3) > (var5))){
								BucketItem var12 = ((BucketItem)(var8_1.getInventory().getItem(var11).getItem()));
								if (((var12.content) != (Fluids.EMPTY)) && ((var12.content) != (Fluids.LAVA))){
									var8_1.getInventory().setItem(var11,new ItemStack((ItemLike)(Items.AIR)));
									var5++;
								}else{
									var11++;
									continue;
								}
							}else{
								var11++;
								continue;
							}
						}
						var11++;
						continue;
					}else{
						break;
					}
				} while (true);
				if ((var4) > (0)){
					var8_1.addItem(new ItemStack((ItemLike)(Items.GLASS_BOTTLE),var4));
				}
				if ((var5) > (0)){
					var8_1.addItem(new ItemStack((ItemLike)(Items.BUCKET),var5));
				}else{
					return;
				}
			}
			return;
		}else{
			return;
		}
	}
}