package xyz.pixelatedw.mineminenomi.api.helpers;

import com.google.common.collect.Iterables;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.abilities.ZoomAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.enums.ItemWeight;
import xyz.pixelatedw.mineminenomi.api.enums.WeaponFamily;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModModelOverrides;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ItemsHelper {
	// Fields
	private static final String[] WANTED_POSTER_BACKGROUNDS;
	public static final Color MARINE_HANDLE_COLOR;
	public static final Color GENERIC_HANDLE_COLOR;
	public static final int DEFAULT_ARMOR_COLOR = -3092272;
	private static final float RAINBOW_UPDATE_MS = 1000F;
	public static final ChatFormatting[] RAINBOW_COLORS;

	public static final ChatFormatting getRainbowFormatting() {
		int var0 = (Math.round((((float)(Util.getMillis())) % (1000F)) / ((1000F) / ((float)(RAINBOW_COLORS.length))))) % (RAINBOW_COLORS.length);
		return RAINBOW_COLORS[var0];
	}
	public static void itemBreakParticles(Level var0, Vec3 var1, int var2, ItemStack var3) {
		int var4 = 0;
		do{
			if ((var4) < (var2)){
				Vector3d var5 = new Vector3d((((double)(var0.getRandom().nextFloat())) - (0.5D)) * (0.1D),((Math.random()) * (0.1D)) + (0.1D),0D);
				double var6 = (((double)(-(var0.getRandom().nextFloat()))) * (0.6D)) - (0.3D);
				Vector3d var7 = new Vector3d((((double)(var0.getRandom().nextFloat())) - (0.5D)) * (0.3D),var6,0.6D);
				if (var0 instanceof ServerLevel){
					((ServerLevel)(var0)).sendParticles((ParticleOptions)(new ItemParticleOption(ParticleTypes.ITEM,var3)),var7.x,var7.y,var7.z,1,var5.x,(var5.y) + (0.05D),var5.z,0.2D);
				}else{
					var0.addParticle((ParticleOptions)(new ItemParticleOption(ParticleTypes.ITEM,var3)),var7.x,var7.y,var7.z,var5.x,(var5.y) + (0.05D),var5.z);
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static void itemBreakParticles(LivingEntity var0, int var1, ItemStack var2) {
		int var3 = 0;
		do{
			if ((var3) < (var1)){
				Vec3 var4 = new Vec3((((double)(var0.getRandom().nextFloat())) - (0.5D)) * (0.1D),((Math.random()) * (0.1D)) + (0.1D),0D);
				Vec3 var5 = var4.xRot((-(var0.getXRot())) * (0.017453292F));
				Vec3 var6 = var5.yRot((-(var0.getYRot())) * (0.017453292F));
				double var7 = (((double)(-(var0.getRandom().nextFloat()))) * (0.6D)) - (0.3D);
				Vec3 var8 = new Vec3((((double)(var0.getRandom().nextFloat())) - (0.5D)) * (0.3D),var7,0.6D);
				Vec3 var9 = var8.xRot((-(var0.getXRot())) * (0.017453292F));
				Vec3 var10 = var9.yRot((-(var0.getYRot())) * (0.017453292F));
				Vec3 var11 = var10.add(var0.getX(),var0.getEyeY(),var0.getZ());
				Level var12 = var0.level();
				if (var12 instanceof ServerLevel){
					((ServerLevel)(var12)).sendParticles((ParticleOptions)(new ItemParticleOption(ParticleTypes.ITEM,var2)),var11.x,var11.y,var11.z,1,var6.x,(var6.y) + (0.05D),var6.z,0.2D);
				}else{
					var0.level().addParticle((ParticleOptions)(new ItemParticleOption(ParticleTypes.ITEM,var2)),var11.x,var11.y,var11.z,var6.x,(var6.y) + (0.05D),var6.z);
				}
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static Optional<ItemStack> findItemInSlot(LivingEntity var0, EquipmentSlot var1, Item var2) {
		Item var4 = null;
		ItemStack var3 = var0.getItemBySlot(var1);
		if ((!(var3.isEmpty())) && ((var3.getItem()) == (var2))){
			return (Optional<ItemStack>) (Optional) (Optional.ofNullable(var3));
		}else{
			if (MineMineNoMi.hasCuriosInstalled()){
				var4 = var2;
				final Item var4_f1 = var4;
				Function var5 = (Function) ((Function<ICuriosItemHandler, List>) ((l0) -> {
			return l0.findCurios(var4_f1);
		}));
				List var6 = ((List)(CuriosApi.getCuriosInventory(var0).map(var5).orElse(null)));
				if ((var6) != (null)){
					Iterator var7 = var6.iterator();
					do{
						if (var7.hasNext()){
							SlotResult var8 = ((SlotResult)(var7.next()));
							if ((var8.stack().getItem()) == (var2)){
								return Optional.ofNullable(var8.stack());
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
			}
			return (Optional<ItemStack>) (Optional) (Optional.empty());
		}
	}
	public static boolean hasItemInSlot(LivingEntity var0, EquipmentSlot var1, Item var2) {
		Item var4 = null;
		ItemStack var3 = var0.getItemBySlot(var1);
		if ((!(var3.isEmpty())) && ((var3.getItem()) == (var2))){
			return true;
		}else{
			if (MineMineNoMi.hasCuriosInstalled()){
				var4 = var2;
				final Item var4_f1 = var4;
				Function var5 = (Function) ((Function<ICuriosItemHandler, List>) ((l0) -> {
			return l0.findCurios(var4_f1);
		}));
				List var6 = ((List)(CuriosApi.getCuriosInventory(var0).map(var5).orElse(null)));
				if ((var6) != (null)){
					Iterator var7 = var6.iterator();
					do{
						if (var7.hasNext()){
							if ((((SlotResult)(var7.next())).stack().getItem()) == (var2)){
								return true;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}else{
					return false;
				}
			}
			return false;
		}
	}
	public static void stopShieldAndStartCooldown(LivingEntity var0, int var1) {
		if (CombatHelper.isShieldBlocking(var0)){
			var0.stopUsingItem();
			if (var0 instanceof Player){
				((Player)(var0)).getCooldowns().addCooldown(Items.SHIELD,var1);
			}else{
				return;
			}
		}
	}
	public static boolean giveItemStackToEntity(LivingEntity var0, ItemStack var1, EquipmentSlot var2) {
		if (var0 instanceof Player){
			((Player)(var0)).getInventory().add(var1);
			return true;
		}else{
			if (!(var0.hasItemInSlot(var2))){
				var0.setItemSlot(var2,var1);
				return true;
			}else{
				return false;
			}
		}
	}
	public static void removeItemStackFromInventory(LivingEntity var0, ItemStack var1) {
		if (var0 instanceof Player){
			((Player)(var0)).getInventory().removeItem(var1);
		}else{
			Iterator var2 = var0.getAllSlots().iterator();
			do{
				if (var2.hasNext()){
					ItemStack var3 = ((ItemStack)(var2.next()));
					if (((java.lang.Object)(var3)).equals((Object)(var1))){
						var3.shrink(var3.getCount());
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	public static boolean hasItemStackInInventory(LivingEntity var0, ItemStack var1) {
		return getAllInventoryItems(var0).contains(var1);
	}
	public static int countItemInInventory(LivingEntity var0, Item var1) {
		Item var2 = var1;
		final Item var2_f1 = var2;
		Predicate<ItemStack> var3 = (l0) -> {
			return ((java.lang.Object)(l0.getItem())).equals((Object)(var2_f1));
		};
		return ((Integer)(((java.util.stream.Stream)(getAllInventoryItems(var0).stream())).filter((Predicate)(var3)).map((Function)(((Function<ItemStack, Integer>)(ItemStack::getCount)))).reduce(Integer.valueOf(0),(BinaryOperator)(((BinaryOperator<Integer>)(Integer::sum)))))).intValue();
	}
	public static int getFreeOrSameSlot(Player var0, ItemStack var1) {
		int var2 = (var0.getInventory().items.size()) - (1);
		do{
			if ((var2) > (0)){
				ItemStack var3 = ((ItemStack)(var0.getInventory().items.get(var2)));
				if (((var2) != (var0.getInventory().selected)) && ((var3.isStackable()) && ((((var3.getCount()) + (var1.getCount())) <= (var3.getMaxStackSize())) && (matchItemStacks(var3,var1))))){
					return var2;
				}else{
					var2--;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		var2 = (var0.getInventory().items.size()) - (1);
		do{
			if ((var2) > (0)){
				ItemStack var3_1 = ((ItemStack)(var0.getInventory().items.get(var2)));
				if (((var2) != (var0.getInventory().selected)) && (var3_1.isEmpty())){
					return var2;
				}else{
					var2--;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return -1;
	}
	private static boolean matchItemStacks(ItemStack var0, ItemStack var1) {
		return (ItemStack.matches(var0,var1)) || (ItemStack.isSameItemSameComponents(var0,var1));
	}
	public static int getFreeSlotReversed(Player var0) {
		int var1 = (var0.getInventory().items.size()) - (1);
		do{
			if ((var1) > (0)){
				if (((ItemStack)(var0.getInventory().items.get(var1))).isEmpty()){
					return var1;
				}else{
					var1--;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return -1;
	}
	public static boolean hasInventoryFull(LivingEntity var0) {
		return !(getInventoryItems(var0).stream().anyMatch((Predicate)(((Predicate<ItemStack>)(ItemStack::isEmpty)))));
	}
	public static long getFreeSlotsCount(LivingEntity var0) {
		return getInventoryItems(var0).stream().filter((Predicate)(((Predicate<ItemStack>)(ItemStack::isEmpty)))).count();
	}
	public static List<ItemStack> getInventoryItems(LivingEntity var0) {
		ArrayList var1 = new ArrayList();
		if (var0 instanceof Player){
			var1.addAll((Collection)(((Player)(var0)).getInventory().items));
		}else{
			Objects.requireNonNull(var1);
			var0.getHandSlots().forEach((Consumer)(((Consumer<ItemStack>)(var1::add))));
		}
		return var1;
	}
	public static List<ItemStack> getAllInventoryItems(LivingEntity var0) {
		ArrayList var1 = new ArrayList();
		if (var0 instanceof Player){
			Player var2 = ((Player)(var0));
			var1.addAll((Collection)(var2.getInventory().items));
			var1.addAll((Collection)(var2.getInventory().offhand));
			var1.addAll((Collection)(var2.getInventory().armor));
		}else{
			Objects.requireNonNull(var1);
			var0.getAllSlots().forEach((Consumer)(((Consumer<ItemStack>)(var1::add))));
		}
		if (MineMineNoMi.hasCuriosInstalled()){
			Function var2_1 = (Function) ((Function<ICuriosItemHandler, List>) ((l0) -> {
			Predicate<ItemStack> lv5_1 = (l2_0) -> {
				return true;
			};
			return l0.findCurios(lv5_1);
		}));
			Collection var3 = ((Collection)(CuriosApi.getCuriosInventory(var0).map(var2_1).orElse(null)));
			if ((var3) != (null)){
				Iterator var4 = var3.iterator();
				do{
					if (var4.hasNext()){
						var1.add(((SlotResult)(var4.next())).stack());
						continue;
					}else{
						break;
					}
				} while (true);
			}
		}
		var1.removeIf((Predicate)(((Predicate<ItemStack>)((l0) -> {
			return ((l0) == (null)) || (l0.isEmpty());
		}))));
		return var1;
	}
	public static boolean hasKairosekiItem(LivingEntity var0) {
		Iterable var1 = var0.getAllSlots();
		if (var0 instanceof Player){
			var1 = Iterables.concat((Iterable)(((Player)(var0)).getInventory().items),var0.getAllSlots());
		}
		Iterator var2 = var1.iterator();
		do{
			if (var2.hasNext()){
				ItemStack var3 = ((ItemStack)(var2.next()));
				if ((!(var3.isEmpty())) && (var3.is(ModTags$Items.KAIROSEKI))){
					return true;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public static boolean isKairosekiWeapon(LevelReader var0, ItemStack var1) {
		if (((var1) != (null)) && (!(var1.isEmpty()))){
			if ((!(var1.is(ModTags$Items.KAIROSEKI))) && ((var1.getItem()) != (ModWeapons.JITTE.get()))){
				if ((var1.isEnchanted()) && ((var1.getEnchantmentLevel((Holder)(var0.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.KAIROSEKI)))) > (0))){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	public static float getSniperInaccuracy(float var0, LivingEntity var1) {
		if (((Boolean)(EntityStatsData.get(var1).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isSniper)))).orElse(Boolean.valueOf(false)))).booleanValue()){
			var0 = (var0) / (2F);
		}
		if ((var1.getItemBySlot(EquipmentSlot.HEAD).getItem()) == (ModArmors.SNIPER_GOGGLES.get())){
			Function<AbilityData, ZoomAbility> var2 = (l0) -> {
			return ((ZoomAbility)(l0.getEquippedAbility(((AbilityCore)(ZoomAbility.INSTANCE.get())))));
		};
			if (((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var2)).map((Function)(((Function<ZoomAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()){
				var0 = (var0) / (4F);
			}
		}
		return var0;
	}
	public static float getItemSpeed(ItemStack var0) {
		Iterator var1 = var0.getAttributeModifiers().modifiers().iterator();
		do{
			if (var1.hasNext()){
				AttributeModifier var2 = ((ItemAttributeModifiers.Entry)(var1.next())).modifier();
				if (var2.id().equals(Item.BASE_ATTACK_SPEED_ID)){
					return (float)(var2.amount());
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return -1F;
	}
	public static float getItemDamage(ItemStack var0) {
		Iterator var1 = var0.getAttributeModifiers().modifiers().iterator();
		do{
			if (var1.hasNext()){
				AttributeModifier var2 = ((ItemAttributeModifiers.Entry)(var1.next())).modifier();
				if (var2.id().equals(Item.BASE_ATTACK_DAMAGE_ID)){
					return (float)(var2.amount());
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return 0F;
	}
	public static ItemWeight getItemWeight(ItemStack var0) {
		if (var0.isEmpty()){
			return ItemWeight.UNKNOWN;
		}else{
			if (var0.is(ModTags$Items.ITEM_WEIGHT_LIGHT)){
				return ItemWeight.LIGHT;
			}else{
				if (var0.is(ModTags$Items.ITEM_WEIGHT_MEDIUM)){
					return ItemWeight.MEDIUM;
				}else{
					if (var0.is(ModTags$Items.ITEM_WEIGHT_HEAVY)){
						return ItemWeight.HEAVY;
					}else{
						Item var1 = var0.getItem();
						if ((!(var1 instanceof BowItem)) && ((!(var1 instanceof CrossbowItem)) && (!(var1 instanceof ProjectileWeaponItem)))){
							double var2 = (double)(getItemSpeed(var0));
							double var3 = (double)(getItemDamage(var0));
							if (((var2) == (0D)) && ((var3) == (0D))){
								return ItemWeight.UNKNOWN;
							}else{
								if ((!((var2) <= (-3D))) && (!((var3) >= (8D)))){
									if ((!((var2) < (-2D))) && (!((var3) > (5D)))){
										if (((var2) >= (-2D)) && ((var3) <= (5D))){
											return ItemWeight.LIGHT;
										}else{
											return ItemWeight.UNKNOWN;
										}
									}else{
										return ItemWeight.MEDIUM;
									}
								}else{
									return ItemWeight.HEAVY;
								}
							}
						}else{
							return ItemWeight.LIGHT;
						}
					}
				}
			}
		}
	}
	public static boolean isWeapon(ItemStack var0) {
		if (var0.isEmpty()){
			return false;
		}else{
			if (var0.is(ModTags$Items.WEAPONS)){
				return true;
			}else{
				Item var1 = var0.getItem();
				if ((!(var1 instanceof SwordItem)) && ((!(var1 instanceof AxeItem)) && ((!(var1 instanceof ProjectileWeaponItem)) && (!(var1 instanceof TridentItem))))){
					return (getItemDamage(var0)) > (0F);
				}else{
					return true;
				}
			}
		}
	}
	public static boolean isSword(ItemStack var0) {
		if (var0.isEmpty()){
			return false;
		}else{
			return var0.is(ItemTags.SWORDS);
		}
	}
	public static boolean isPolearm(ItemStack var0) {
		if (var0.isEmpty()){
			return false;
		}else{
			return var0.is(ModTags$Items.POLEARMS);
		}
	}
	public static boolean isBlunt(ItemStack var0) {
		if (var0.isEmpty()){
			return false;
		}else{
			return var0.is(ModTags$Items.BLUNTS);
		}
	}
	public static boolean isBowOrGun(ItemStack var0) {
		if (var0.isEmpty()){
			return false;
		}else{
			return (var0.is(Tags.Items.TOOLS_BOW)) || ((var0.is(Tags.Items.TOOLS_CROSSBOW)) || (var0.is(ModTags$Items.GUNS)));
		}
	}
	public static boolean isClimaTact(ItemStack var0) {
		if (((var0) != (null)) && (!(var0.isEmpty()))){
			return var0.is(ModTags$Items.CLIMA_TACTS);
		}else{
			return false;
		}
	}
	public static boolean hasImbuingOverride(@Nullable() ItemStack var0) {
		if (((var0) != (null)) && (!(var0.isEmpty()))){
			return (ItemProperties.getProperty(var0,ModModelOverrides.HAKI_PROPERTY)) != (null);
		}else{
			return false;
		}
	}
	public static boolean isImbuable(@Nullable() ItemStack var0) {
		if (((var0) != (null)) && (!(var0.isEmpty()))){
			if (var0.is(ModTags$Items.IMBUING_DAMAGE_BONUS)){
				return true;
			}else{
				Predicate<ItemAttributeModifiers.Entry> var1 = (l0) -> {
			return l0.attribute().equals(Attributes.ATTACK_DAMAGE);
		};
				Function<ItemAttributeModifiers.Entry, Double> var2 = (l0) -> {
			return Double.valueOf(l0.modifier().amount());
		};
				return (((Double)(((java.util.stream.Stream)(((java.util.stream.Stream)(((ItemAttributeModifiers)(var0.get(DataComponents.ATTRIBUTE_MODIFIERS))).modifiers().stream())).filter((Predicate)(var1)))).map((Function)(var2)).reduce(Double.valueOf(0D),(BinaryOperator)(((BinaryOperator<Double>)(Double::sum)))))).doubleValue()) > (0D);
			}
		}else{
			return false;
		}
	}
	public static boolean isImmuneToRust(@Nullable() LivingEntity var0, ItemStack var1) {
		if ((var0) != (null)){
			Function<AbilityData, BusoshokuHakiImbuingAbility> var2 = (l0) -> {
			return ((BusoshokuHakiImbuingAbility)(l0.getEquippedAbility(((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
		};
			if (((Boolean)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var2)).map((Function)(((Function<BusoshokuHakiImbuingAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()){
				return true;
			}
		}
		if (var1.is(ModTags$Items.RUST_IMMUNITY)){
			return true;
		}else{
			return false;
		}
	}
	public static Set<WeaponFamily> getWeaponFamily(ItemStack var0) {
		if ((!(var0.isEmpty())) && (var0.is(ModTags$Items.WEAPONS))){
			HashSet var1 = new HashSet();
			if (var0.is(ItemTags.SWORDS)){
				var1.add(WeaponFamily.SWORD);
			}
			if (var0.is(ItemTags.AXES)){
				var1.add(WeaponFamily.AXE);
			}
			if (var0.is(Tags.Items.TOOLS_SPEAR)){
				var1.add(WeaponFamily.TRIDENT);
			}
			if (var0.is(Tags.Items.TOOLS_BOW)){
				var1.add(WeaponFamily.BOW);
			}
			if (var0.is(Tags.Items.TOOLS_CROSSBOW)){
				var1.add(WeaponFamily.CROSSBOW);
			}
			if (var0.is(ModTags$Items.BLUNTS)){
				var1.add(WeaponFamily.BLUNT);
			}
			if (var0.is(ModTags$Items.GUNS)){
				var1.add(WeaponFamily.GUN);
			}
			if (var0.is(ModTags$Items.POLEARMS)){
				var1.add(WeaponFamily.POLEARM);
			}
			return var1;
		}else{
			return (Set<WeaponFamily>) (Set) (Set.of((Object)(WeaponFamily.UNKNOWN)));
		}
	}
	static  {
		String[] var0 = new String[]{"forest1","forest2","jungle1","jungle2","hills1","hills2","hills3","plains1","plains2","plains3","taiga1","taiga2"};
		WANTED_POSTER_BACKGROUNDS = var0;
		MARINE_HANDLE_COLOR = FactionHelper.MARINE_COLOR;
		GENERIC_HANDLE_COLOR = new Color(7817288);
		ChatFormatting[] var1 = new ChatFormatting[]{ChatFormatting.RED,ChatFormatting.GOLD,ChatFormatting.YELLOW,ChatFormatting.GREEN,ChatFormatting.AQUA,ChatFormatting.BLUE,ChatFormatting.LIGHT_PURPLE};
		RAINBOW_COLORS = var1;
	}
}