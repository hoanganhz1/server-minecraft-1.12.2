package xyz.pixelatedw.mineminenomi.items;

import com.google.common.collect.Sets;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry$Status;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.enums.FruitType;
import xyz.pixelatedw.mineminenomi.api.events.onefruit.EatDevilFruitEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.onefruit.EatDevilFruitEvent$Pre;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.RandomizedFruit;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.triggers.ConsumeDevilFruitTrigger;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.entities.AkumaNoMiEntity;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Client;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Common;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class AkumaNoMiItem extends Item {
	// Fields
	private ResourceLocation key;
	private int tier;
	public FruitType type;
	private Supplier<? extends AbilityCore<?>>[] abilities;
	public static final int GENERIC_FRUIT_VARIATIONS = 10;
	private static final Color[] STEM_COLORS;

	public AkumaNoMiItem(int var1, FruitType var2, Supplier<? extends AbilityCore<?>>... var3) {
		super(new Item.Properties().stacksTo(1).food(Foods.APPLE));
		this.type = var2;
		this.abilities = var3;
		this.tier = var1;
		if ((this.type) == (FruitType.LOGIA)){
			ModValues.LOGIA_FRUITS.add(this);
		}
		ModValues.DEVIL_FRUITS.add(this);
	}
	public void setAbilities(Supplier<? extends AbilityCore<?>>... var1) {
		this.abilities = var1;
	}
	public Component getName(ItemStack var1) {
		if (ServerConfig.getRandomizedFruits()){
			if (((Boolean)(var1.getOrDefault(ModDataComponents.FRUIT_DECIPHERED,Boolean.valueOf(false)))).booleanValue()){
				return this.getShiftedFruit().getDevilFruitName();
			}else{
				return ModI18n.ITEM_GENERIC_FRUIT;
			}
		}else{
			return super.getName(var1);
		}
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		var2.startUsingItem(var3);
		return InteractionResultHolder.success(var4);
	}
	public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
		if (var3 instanceof Player){
			Player var4 = ((Player)(var3));
			if (((EatDevilFruitEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(new EatDevilFruitEvent$Pre(var4,var1))))).isCanceled()){
				return var1;
			}else{
				if (!(var4.level().isClientSide)){
					DevilFruitData var5 = DevilFruitData.getOrCreate((LivingEntity)(var4));
					AbilityData var6 = AbilityData.getOrCreate((LivingEntity)(var4));
					OneFruitWorldData var7 = OneFruitWorldData.get();
					AkumaNoMiItem var8 = ((AkumaNoMiItem)(var1.getItem()));
					if (ServerConfig.getRandomizedFruits()){
						var8 = var8.getShiftedFruit();
					}
					ResourceLocation var9 = BuiltInRegistries.ITEM.getKey(var8);
					boolean var10 = var5.hasAnyDevilFruit();
					boolean var11 = var5.hasDevilFruit(ModFruits.YAMI_YAMI_NO_MI);
					boolean var12 = var7.isFruitInUse(var9);
					var12 = (var12) | (!(var7.updateOneFruit(var9,var4.getUUID(),OneFruitEntry$Status.IN_USE)));
					if ((ServerConfig.hasOneFruitPerWorldSimpleLogic()) && (var12)){
						var4.displayClientMessage(ModI18n.ITEM_MESSAGE_FRUIT_ALREADY_USED,false);
						var1.shrink(1);
						return var1;
					}else{
						if ((!(ServerConfig.isYamiPowerEnabled())) && (var10)){
							this.applyCurseDeath(var4);
							var1.shrink(1);
							var7.lostOneFruit(var9,(LivingEntity)(var4),"Devil Fruits Curse");
							return var1;
						}else{
							if (ServerConfig.isYamiPowerEnabled()){
								if ((var10) && (((var8) != (ModFruits.YAMI_YAMI_NO_MI.get())) && (!(var11)))){
									this.applyCurseDeath(var4);
									var1.shrink(1);
									var7.lostOneFruit(var9,(LivingEntity)(var4),"Devil Fruits Curse");
									return var1;
								}else{
									if (((var8) == (ModFruits.YAMI_YAMI_NO_MI.get())) && (var11)){
										this.applyCurseDeath(var4);
										var1.shrink(1);
										var7.lostOneFruit(var9,(LivingEntity)(var4),"Devil Fruits Curse");
										return var1;
									}else{
										if ((var10) && ((!(var5.hasDevilFruit(ModFruits.YAMI_YAMI_NO_MI))) && (var5.hasYamiPower()))){
											this.applyCurseDeath(var4);
											var1.shrink(1);
											var7.lostOneFruit(var9,(LivingEntity)(var4),"Devil Fruits Curse");
											return var1;
										}
									}
								}
							}
							if (((var8) == (ModFruits.YAMI_YAMI_NO_MI.get())) && (ServerConfig.isYamiPowerEnabled())){
								var5.setYamiPower(true);
							}
							boolean var13 = false;
							if ((var10) && (!((var11) && (ServerConfig.isYamiPowerEnabled())))){

							}else{
								DFEncyclopediaItem.updateEncyclopediae(var4,var9,var8.getRandomElements((LevelAccessor)(var2)));
								var5.setDevilFruit(var8);
								var13 = true;
							}
							if (var4 instanceof ServerPlayer){
								ServerPlayer var14 = ((ServerPlayer)(var4));
								((ConsumeDevilFruitTrigger)(ModAdvancements.CONSUME_DEVIL_FRUIT.get())).trigger(var14,this.getRegistryKey());
							}
							if ((var8) != (ModFruits.YOMI_YOMI_NO_MI.get())){
								Supplier[] var14_1 = var8.abilities;
								int var15 = var14_1.length;
								int var16 = 0;
								do{
									if ((var16) < (var15)){
										Supplier var17 = var14_1[var16];
										if ((!(AbilityHelper.verifyIfAbilityIsBanned(((AbilityCore)(var17.get()))))) && (!(var6.hasUnlockedAbility(((AbilityCore)(var17.get())))))){
											AbilityHelper.checkAndUnlockAbility(var3,((AbilityCore)(var17.get())));
										}
										var16++;
										continue;
									}else{
										break;
									}
								} while (true);
								if (((var8) != (ModFruits.KAGE_KAGE_NO_MI.get())) && ((var8) != (ModFruits.ITO_ITO_NO_MI.get()))){

								}else{
									AbilityHelper.checkAndUnlockAbility(var3,((AbilityCore)(CommandAbility.INSTANCE.get())));
								}
								var13 = true;
								ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var4)),var4);
							}
							if (var13){
								ModNetwork.sendTo(new SSyncDevilFruitPacket((LivingEntity)(var4)),var4);
							}
							if ((var8) == (ModFruits.HITO_HITO_NO_MI.get())){
								var4.displayClientMessage(ModI18n.ITEM_MESSAGE_GAINED_ENLIGHTENMENT,false);
								ProgressionHandler.checkForRacialUnlocks(var4);
							}
							var4.eat(var2,var1);
						}
					}
				}
				EatDevilFruitEvent$Post var5_1 = new EatDevilFruitEvent$Post(var4,var1);
				NeoForge.EVENT_BUS.post((Event)(var5_1));
				var1.shrink(1);
				return var1;
			}
		}else{
			return var1;
		}
	}
	public void appendHoverText(ItemStack var1, Item.TooltipContext var2, List<Component> var3, TooltipFlag var4) {
		if (!(RandomizedFruitsHandler$Client.HAS_RANDOMIZED_FRUIT)){
			if (((ModFruits.MOCHI_MOCHI_NO_MI) != (null)) && ((this) == (ModFruits.MOCHI_MOCHI_NO_MI.get()))){
				String var5 = String.valueOf((Object)(ChatFormatting.GRAY));
				String var6 = var5 + "Yanagi Mochi";
				((java.util.List)(var3)).add((Object)(Component.literal(var6)));
				String var7 = String.valueOf((Object)(ChatFormatting.GRAY));
				String var8 = var7 + "Mochi Tsuki";
				((java.util.List)(var3)).add((Object)(Component.literal(var8)));
				String var9 = String.valueOf((Object)(ChatFormatting.GRAY));
				String var10 = var9 + "Kaku Mochi";
				((java.util.List)(var3)).add((Object)(Component.literal(var10)));
				String var11 = String.valueOf((Object)(ChatFormatting.GRAY));
				String var12 = var11 + "Yaki Mochi";
				((java.util.List)(var3)).add((Object)(Component.literal(var12)));
				String var13 = String.valueOf((Object)(ChatFormatting.GRAY));
				String var14 = var13 + "Zan Giri Mochi";
				((java.util.List)(var3)).add((Object)(Component.literal(var14)));
				String var15 = String.valueOf((Object)(ChatFormatting.GRAY));
				String var16 = var15 + "Nagare Mochi";
				((java.util.List)(var3)).add((Object)(Component.literal(var16)));
				String var17 = String.valueOf((Object)(ChatFormatting.GRAY));
				String var18 = var17 + "Muso Donuts";
				((java.util.List)(var3)).add((Object)(Component.literal(var18)));
				String var19 = String.valueOf((Object)(ChatFormatting.GRAY));
				String var20 = var19 + "Kuzu Mochi";
				((java.util.List)(var3)).add((Object)(Component.literal(var20)));
				String var21 = String.valueOf((Object)(ChatFormatting.GRAY));
				String var22 = var21 + "Mochi Invulnerability";
				((java.util.List)(var3)).add((Object)(Component.literal(var22)));
			}else{
				Supplier[] var5_1 = this.abilities;
				int var6_1 = var5_1.length;
				int var7_1 = 0;
				do{
					if ((var7_1) < (var6_1)){
						AbilityCore var8_1 = ((AbilityCore)(var5_1[var7_1].get()));
						if (((var8_1) != (null)) && ((!(AbilityHelper.verifyIfAbilityIsBanned(var8_1))) && ((var8_1.isVisible()) && (var8_1.isVisibleInTooltips())))){
							((java.util.List)(var3)).add((Object)(var8_1.getLocalizedName().copy().withStyle(ChatFormatting.GRAY)));
						}
						var7_1++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
			var3.add(CommonComponents.EMPTY);
			String var5_2 = String.valueOf((Object)(this.type.getColor()));
			String var6_2 = this.type.getName();
			String var7_2 = var5_2 + var6_2;
			((java.util.List)(var3)).add((Object)(Component.literal(var7_2)));
		}else{
			var3.clear();
		}
	}
	public boolean hasCustomEntity(ItemStack var1) {
		return true;
	}
	public Entity createEntity(Level var1, Entity var2, ItemStack var3) {
		if (!(ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			return null;
		}else{
			this.applyRandomness(var1,var3);
			AkumaNoMiEntity var4 = new AkumaNoMiEntity(var1,var2.position().x,var2.position().y,var2.position().z,var3);
			Entity var5 = ((ItemEntity)(var2)).getOwner();
			if ((var5) != (null)){
				var4.setThrower(var5);
			}
			var4.setPickUpDelay(40);
			AbilityHelper.setDeltaMovement((Entity)(var4),var2.getDeltaMovement());
			return var4;
		}
	}
	public boolean onEntityItemUpdate(ItemStack var1, ItemEntity var2) {
		if (var2.level().isClientSide){
			return false;
		}else{
			OneFruitWorldData var3 = OneFruitWorldData.get();
			ResourceLocation var4 = this.getRegistryKey();
			boolean var5 = false;
			String var6 = null;
			Iterator var7 = WyHelper.getNearbyTileEntities(var2.blockPosition(),var2.level(),2).iterator();
			do{
				if (var7.hasNext()){
					BlockPos var8 = ((BlockPos)(var7.next()));
					if (var2.level().getBlockEntity(var8) instanceof HopperBlockEntity){
						var5 = true;
						var6 = "Thrown into hopper";
						break;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if ((TargetHelper.getEntitiesInArea((LevelAccessor)(var2.level()),(LivingEntity)(null),var2.blockPosition(),0.5D,TargetPredicate.EVERYTHING,MinecartHopper.class).size()) > (0)){
				var5 = true;
				var6 = "Thrown into Hopper Minecart";
			}
			if ((TargetHelper.getEntitiesInArea((LevelAccessor)(var2.level()),(LivingEntity)(null),var2.blockPosition(),1.5D,TargetPredicate.EVERYTHING,Fox.class).size()) > (0)){
				var5 = true;
				var6 = "Fox took fruit";
			}
			if (var5){
				var2.remove(Entity.RemovalReason.DISCARDED);
				if ((var2.getOwner()) != (null)){
					Player var8_1 = var2.level().getPlayerByUUID(var2.getOwner().getUUID());
					if ((var8_1) != (null)){
						var8_1.getInventory().add(var1);
					}else{
						var3.lostOneFruit(var4,(LivingEntity)(null),var6);
					}
				}else{
					var3.lostOneFruit(var4,(LivingEntity)(null),var6);
				}
			}
			return false;
		}
	}
	public void applyRandomness(@Nullable() Level var1, ItemStack var2) {
		if (RandomizedFruitsHandler$Client.HAS_RANDOMIZED_FRUIT){
			RandomizedFruitsHandler$Client.DIRTY = true;
			if ((RandomizedFruit.hasBaseColor(var2)) && ((RandomizedFruit.hasStemColor(var2)) && (!(RandomizedFruitsHandler$Client.DIRTY)))){
				return;
			}else{
				var2.remove(ModDataComponents.RANDOMIZED_FRUIT);
				DFEncyclopediaEntry var3 = this.getShiftedFruit().getRandomElements((LevelAccessor)(var1));
				if ((var3) == (null)){
					return;
				}else{
					int var4 = ((Integer)(var3.getShape().get())).intValue();
					int var5 = FastColor.ARGB32.opaque(((Color)(var3.getBaseColor().get())).getRGB());
					int var6 = FastColor.ARGB32.opaque(((Color)(var3.getStemColor().get())).getRGB());
					var2.set(ModDataComponents.RANDOMIZED_FRUIT,new RandomizedFruit(var4,var5,var6));
					RandomizedFruitsHandler$Client.DIRTY = false;
				}
			}
		}
	}
	public DFEncyclopediaEntry getRandomElements(@Nullable() LevelAccessor var1) {
		ResourceLocation var2 = BuiltInRegistries.ITEM.getKey(this);
		if ((var2) == (null)){
			return null;
		}else{
			long var3 = (RandomizedFruitsHandler$Common.SEED) + ((long)(var2.hashCode()));
			if (((var1) != (null)) && (!(var1.isClientSide()))){

			}else{
				var3 = ((Long)(RandomizedFruitsHandler$Client.FRUIT_SEEDS.get(Integer.valueOf(var2.hashCode())))).longValue();
			}
			Random var4 = new Random(var3);
			float var5 = var4.nextFloat();
			float var6 = var4.nextFloat();
			float var7 = var4.nextFloat();
			Optional var8 = Optional.of(Integer.valueOf((var4.nextInt(10)) + (1)));
			Optional var9 = Optional.of(new Color(var5,var6,var7));
			return DFEncyclopediaEntry.of(var8,var9,Optional.of(STEM_COLORS[var4.nextInt(STEM_COLORS.length)]));
		}
	}
	public AkumaNoMiItem getShiftedFruit() {
		int var1 = Math.max(1,(int)((RandomizedFruitsHandler$Common.SEED) % (10L)));
		int var2 = ((ModValues.DEVIL_FRUITS.indexOf(this)) + (var1)) % (ModValues.DEVIL_FRUITS.size());
		return ((AkumaNoMiItem)(ModValues.DEVIL_FRUITS.get(var2)));
	}
	@Nullable()
	public @Nullable() AkumaNoMiItem getReverseShiftedFruit() {
		Iterator var1 = ModValues.DEVIL_FRUITS.iterator();
		do{
			if (var1.hasNext()){
				AkumaNoMiItem var2 = ((AkumaNoMiItem)(var1.next()));
				if ((var2.getShiftedFruit()) == (this)){
					return var2;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return null;
	}
	private void applyCurseDeath(Player var1) {
		var1.hurt(ModDamageSources.getInstance((LevelAccessor)(var1.level())).devilsCurse(),3.4028235e+38F);
	}
	public ResourceLocation getRegistryKey() {
		if ((this.key) == (null)){
			this.key = BuiltInRegistries.ITEM.getKey(this);
		}
		return this.key;
	}
	public Supplier<? extends AbilityCore<?>>[] getRawRegistryAbilities() {
		return this.abilities;
	}
	public HashSet<Supplier<? extends AbilityCore<?>>> getRegistryAbilities() {
		return Sets.newHashSet(this.abilities);
	}
	public List<? extends AbilityCore<? extends IAbility>> getAbilities() {
		return Arrays.stream(this.abilities).map((Function)(((Function<Supplier<AbilityCore>, AbilityCore>)(Supplier::get)))).toList();
	}
	public int getTier() {
		return this.tier;
	}
	public FruitType getType() {
		return this.type;
	}
	public Component getDevilFruitName() {
		return this.getDescription();
	}
	static  {
		Color[] var0 = new Color[]{WyHelper.hexToRGB("#ccc774"),WyHelper.hexToRGB("#8a5216"),WyHelper.hexToRGB("#025f00"),WyHelper.hexToRGB("#aecd6d"),WyHelper.hexToRGB("#f56fe3"),WyHelper.hexToRGB("#f93434")};
		STEM_COLORS = var0;
	}
}