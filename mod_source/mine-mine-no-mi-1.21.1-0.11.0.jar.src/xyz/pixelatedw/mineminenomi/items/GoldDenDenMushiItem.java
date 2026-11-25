package xyz.pixelatedw.mineminenomi.items;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.api.items.components.BusterCallData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class GoldDenDenMushiItem extends Item {
	// Fields
	private static final int CANNON_SHOTS_TIMER = 200;
	private static final int BUSTER_CALL_COUNTDOWN = 1200;

	public GoldDenDenMushiItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		Player var9 = null;
		ItemStack var4 = var2.getItemInHand(var3);
		BusterCallData var5 = ((BusterCallData)(var4.getOrDefault(ModDataComponents.BUSTER_CALL,new BusterCallData(false,0,BlockPos.ZERO))));
		if (((var5) != (null)) && ((!(var5.inUse())) && (!(var1.isClientSide)))){
			if (NuWorld.isChallengeDimension(var1)){
				return InteractionResultHolder.fail(var4);
			}else{
				EntityStatsData var6 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
				if ((var6) == (null)){
					return InteractionResultHolder.fail(var4);
				}else{
					boolean var7 = ModPermissions.hasPermission(var2,ModPermissions.BUSTER_CALL_ITEM);
					if ((!(var6.hasRank(MarineRank.ADMIRAL))) && (!(var7))){
						WyHelper.sendMessage((LivingEntity)(var2),ModI18n.ITEM_BUSTER_CALL_REQUIREMENT);
						return InteractionResultHolder.fail(var4);
					}else{
						BusterCallData.setInUse(var4,1200,var2.blockPosition());
						Predicate<Player> var8 = (l0) -> {
			return ((Boolean)(EntityStatsData.get((LivingEntity)(l0)).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isMarine)))).orElse(Boolean.valueOf(false)))).booleanValue();
		};
						var9 = var2;
						final Player var9_f1 = var9;
						Consumer<Player> var10 = (l0) -> {
			Object[] lv2_2 = new Object[]{Long.valueOf(Math.round(var9_f1.getX())),Long.valueOf(Math.round(var9_f1.getZ()))};
			WyHelper.sendMessage((LivingEntity)(l0),(Component)(Component.translatable(ModI18n.ITEM_BUSTER_CALL_LAUNCHED,lv2_2)));
		};
						((java.util.stream.Stream)(((java.util.stream.Stream)(var1.players().stream())).filter((Predicate)(var8)))).forEach((Consumer)(var10));
					}
				}
			}
		}
		return InteractionResultHolder.success(var4);
	}
	public void inventoryTick(ItemStack var1, Level var2, Entity var3, int var4, boolean var5) {
		super.inventoryTick(var1,var2,var3,var4,var5);
		BusterCallData var6 = ((BusterCallData)(var1.getOrDefault(ModDataComponents.BUSTER_CALL,new BusterCallData(false,0,BlockPos.ZERO))));
		if (((var6) != (null)) && ((var6.inUse()) && (!(var2.isClientSide)))){
			var6.setCooldown(var1,(this.getCountdown(var1)) - (1));
			int var7 = this.getCountdown(var1);
			int var8 = Math.max(((var7) - (200)) / (20),0);
			if (var3 instanceof Player){
				Player var9 = ((Player)(var3));
				Object[] var10 = new Object[]{Integer.valueOf(var8)};
				WyHelper.sendMessage((LivingEntity)(var9),(Component)(Component.translatable(ModI18n.ITEM_BUSTER_CALL_TIMER,var10)));
			}
			BlockPos var9_1 = var6.pos();
			if (((var7) > (40)) && (((var7) < (200)) && (((var7) % (5)) == (0)))){
				int var10_1 = 0;
				do{
					if ((var10_1) < (20)){
						CannonBallProjectile var11 = new CannonBallProjectile((Level)(var2),((LivingEntity)(var3)));
						var11.setPos((double)((var9_1.getX()) + (WyHelper.randomWithRange(-50,50))),(double)((var9_1.getY()) + (100)),(double)((var9_1.getZ()) + (WyHelper.randomWithRange(-50,50))));
						var11.setDamage(50F);
						var11.setMaxLife(60);
						var2.addFreshEntity((Entity)(var11));
						var11.shootFromRotation(var3,90F,0F,0F,3F,0F);
						var10_1++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
			if ((var7) == (0)){
				var1.shrink(1);
				EntityType var10_2 = ((EntityType)(ModMobs.MARINE_CAPTAIN.get()));
				EntityType var11_1 = ((EntityType)(ModMobs.MARINE_GRUNT.get()));
				EntityType var12 = ((EntityType)(ModMobs.PACIFISTA.get()));
				int var13 = WyHelper.randomWithRange(1,5);
				int var14 = WyHelper.randomWithRange(10,20);
				int var15 = WyHelper.randomWithRange(100,200);
				int var16 = 0;
				do{
					if ((var16) < (var13)){
						BlockPos var17 = WyHelper.findOnGroundSpawnLocation(((ServerLevel)(var2)),var10_2,var9_1,50);
						if ((var17) != (null)){
							var12.spawn(((ServerLevel)(var2)),(ItemStack)(null),(Player)(null),var17,MobSpawnType.EVENT,false,false);
						}
						var16++;
						continue;
					}else{
						break;
					}
				} while (true);
				var16 = 0;
				do{
					if ((var16) < (var14)){
						BlockPos var17_1 = WyHelper.findOnGroundSpawnLocation(((ServerLevel)(var2)),var10_2,var9_1,50);
						if ((var17_1) != (null)){
							var10_2.spawn(((ServerLevel)(var2)),(ItemStack)(null),(Player)(null),var17_1,MobSpawnType.EVENT,false,false);
						}
						var16++;
						continue;
					}else{
						break;
					}
				} while (true);
				var16 = 0;
				do{
					if ((var16) < (var15)){
						BlockPos var17_2 = WyHelper.findOnGroundSpawnLocation(((ServerLevel)(var2)),var11_1,var9_1,50);
						if ((var17_2) != (null)){
							var11_1.spawn(((ServerLevel)(var2)),(ItemStack)(null),(Player)(null),var17_2,MobSpawnType.EVENT,false,false);
						}
						var16++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
			return;
		}else{
			return;
		}
	}
	private int getCountdown(ItemStack var1) {
		return ((BusterCallData)(var1.getOrDefault(ModDataComponents.BUSTER_CALL,new BusterCallData(false,-1,BlockPos.ZERO)))).cooldown();
	}
	public void appendHoverText(ItemStack var1, Item.TooltipContext var2, List<Component> var3, TooltipFlag var4) {
		super.appendHoverText(var1,var2,var3,var4);
		var3.add(ModI18n.ITEM_BUSTER_CALL_INFO);
		int var5 = this.getCountdown(var1);
		if ((var5) > (0)){
			int var6 = Math.max(((var5) - (200)) / (20),0);
			Object[] var7 = new Object[]{Integer.valueOf(var6)};
			((java.util.List)(var3)).add((Object)(Component.translatable(ModI18n.ITEM_BUSTER_CALL_TIMER,var7)));
		}
	}
}