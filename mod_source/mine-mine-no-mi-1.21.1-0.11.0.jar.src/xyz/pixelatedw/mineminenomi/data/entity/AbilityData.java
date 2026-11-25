package xyz.pixelatedw.mineminenomi.data.entity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent$IEndCooldownEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent$IStartCooldownEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SlotDecorationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.events.ability.EquipAbilityEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockAbilityTrigger;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CSetCombatBarPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SStopGCDPacket;
import xyz.pixelatedw.mineminenomi.renderers.layers.AuraLayer;

public class AbilityData implements INBTSerializable<CompoundTag> {
	// Fields
	public static final int VERSION = 1;
	private static final int DEFAULT_GCD = 20;
	private LivingEntity owner;
	private Set<AbilityCoreUnlockWrapper<?>> unlockedAbilities;
	private Set<IAbility> passiveAbilities;
	private IAbility[] activeAbilities;
	private IAbility previouslyUsedAbility;
	private int currentCombatBarSet;
	private int lastCombatBarSet;
	private int previousGCD;
	private int gcd;
	private final Map<Pair<AbilityCore<? extends IAbility>, Integer>, AbilityNode> nodes;

	public static AbilityData createCapability(IAttachmentHolder var0) {
		return new AbilityData(((LivingEntity)(var0)));
	}
	public static Optional<AbilityData> get(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.ABILITY))){
			var0.setData(ModAttachments.ABILITY,new AbilityData(var0));
		}
		return (Optional<AbilityData>) (Optional) (var0.getExistingData(ModAttachments.ABILITY));
	}
	public static AbilityData getOrCreate(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.ABILITY))){
			var0.setData(ModAttachments.ABILITY,new AbilityData(var0));
		}
		return ((AbilityData)(var0.getData(ModAttachments.ABILITY)));
	}
	private AbilityData(LivingEntity var1) {
		this.unlockedAbilities = new LinkedHashSet();
		this.passiveAbilities = new LinkedHashSet();
		this.activeAbilities = new IAbility[80];
		this.currentCombatBarSet = 0;
		this.lastCombatBarSet = 0;
		this.previousGCD = 20;
		this.gcd = 20;
		this.nodes = new HashMap();
		this.owner = var1;
	}
	public <A extends IAbility> boolean addUnlockedAbility(AbilityCore<A> var1, AbilityUnlock var2) {
		if (((var1) != (null)) && (!(this.hasUnlockedAbility(var1)))){
			this.addUnlockedAbility(new AbilityCoreUnlockWrapper(this.owner,var1,var2));
			if ((var1.getType()) == (AbilityType.PASSIVE)){
				this.addPassiveAbility(var1.createAbility());
			}
			return true;
		}else{
			return false;
		}
	}
	public <A extends IAbility> boolean addUnlockedAbility(AbilityCoreUnlockWrapper<A> var1) {
		AbilityCore var2 = var1.getAbilityCore();
		if (((var2) != (null)) && (!(this.hasUnlockedAbility(var2)))){
			this.unlockedAbilities.add((AbilityCoreUnlockWrapper)(var1));
			if ((this.owner) != (null)){
				LivingEntity var3 = this.owner;
				if (var3 instanceof ServerPlayer){
					ServerPlayer var4 = ((ServerPlayer)(var3));
					((UnlockAbilityTrigger)(ModAdvancements.UNLOCK_ABILITY.get())).trigger(var4,var2);
				}else{
					return true;
				}
			}
			return true;
		}else{
			return false;
		}
	}
	public AbilityUnlock getUnlockTypeForAbility(AbilityCore<?> var1) {
		AbilityCore<?> var2 = var1;
		final AbilityCore<?> var2_f1 = var2;
		Predicate var3 = (Predicate) ((Predicate<AbilityCoreUnlockWrapper>) ((l0) -> {
			return l0.getAbilityCore().equals(var2_f1);
		}));
		return ((AbilityUnlock)(((java.util.Optional)(((java.util.stream.Stream)(this.unlockedAbilities.stream())).filter((Predicate)(var3)).map((Function)(((Function<AbilityCoreUnlockWrapper, AbilityUnlock>)(AbilityCoreUnlockWrapper::getUnlockType)))).findFirst())).orElse((Object)(AbilityUnlock.NONE))));
	}
	public boolean removeUnlockedAbility(AbilityCore<?> var1) {
		if (!(this.hasUnlockedAbility(var1))){
			return false;
		}else{
			int var2 = 0;
			var2 = (var2) | (((this.removeEquippedAbility(var1)) ? 1 : 0));
			var2 = (var2) | (((this.removePassiveAbility(var1)) ? 1 : 0));
			if ((var2) != (0)){

			}
			this.unlockedAbilities.removeIf((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<AbilityCoreUnlockWrapper>) ((l0) -> {
			return l0.getAbilityCore().equals(var1);
		}))))));
			return true;
		}
	}
	public boolean hasUnlockedAbility(AbilityCore<?> var1) {
		return this.getUnlockedAbilities().stream().anyMatch((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<AbilityCoreUnlockWrapper>) ((l0) -> {
			return l0.getAbilityCore().equals(var1);
		}))))));
	}
	public Set<AbilityCoreUnlockWrapper<? extends IAbility>> getUnlockedAbilities() {
		return ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.unlockedAbilities.stream())).filter((Predicate)(((java.util.function.Predicate)(((Predicate<AbilityCoreUnlockWrapper>)(Objects::nonNull)))))))).collect((Collector)(Collectors.toSet()))));
	}
	public Set<AbilityCoreUnlockWrapper<? extends IAbility>> getUnlockedAbilities(Predicate<AbilityCore<?>> var1) {
		Predicate<AbilityCore<?>> var2 = var1;
		final Predicate<AbilityCore<?>> var2_f1 = var2;
		Predicate var3 = (Predicate) ((Predicate<AbilityCoreUnlockWrapper>) ((l0) -> {
			return var2_f1.test(l0.getAbilityCore());
		}));
		return ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.getUnlockedAbilities().stream())).filter((Predicate)(var3)))).collect((Collector)(Collectors.toSet()))));
	}
	public Stream<AbilityCoreUnlockWrapper<? extends IAbility>> getUnlockedAbilitiesStream() {
		return (Stream<AbilityCoreUnlockWrapper<? extends IAbility>>) (Stream) (((java.util.stream.Stream)(this.unlockedAbilities.stream())).filter((Predicate)(((java.util.function.Predicate)(((Predicate<AbilityCoreUnlockWrapper>)(Objects::nonNull)))))));
	}
	public Set<AbilityCore<?>> clearUnlockedAbilities() {
		Set var1 = ((Set)(((java.util.stream.Stream)(this.unlockedAbilities.stream().map((Function)(((Function<AbilityCoreUnlockWrapper, AbilityCore>)(AbilityCoreUnlockWrapper::getAbilityCore)))))).collect((Collector)(Collectors.toSet()))));
		var1.forEach((Consumer)(((Consumer<AbilityCore>)(this::removeUnlockedAbility))));
		this.unlockedAbilities.clear();
		return var1;
	}
	public Set<AbilityCore<?>> clearUnlockedAbilities(Predicate<AbilityCore<?>> var1) {
		Predicate<AbilityCore<?>> var2 = var1;
		final Predicate<AbilityCore<?>> var2_f1 = var2;
		Predicate var3 = (Predicate) ((Predicate<AbilityCoreUnlockWrapper>) ((l0) -> {
			return var2_f1.test(l0.getAbilityCore());
		}));
		Set var4 = ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.unlockedAbilities.stream())).filter((Predicate)(var3)))).collect((Collector)(Collectors.toSet()))));
		AbilityData var5 = this;
		Consumer var6 = (Consumer) ((Consumer<AbilityCoreUnlockWrapper>) ((l0) -> {
			this.removeUnlockedAbility(l0.getAbilityCore());
		}));
		var4.forEach(var6);
		this.unlockedAbilities.removeAll((Collection)(var4));
		return ((Set)(((java.util.stream.Stream)(var4.stream().map((Function)(((Function<AbilityCoreUnlockWrapper, AbilityCore>)(AbilityCoreUnlockWrapper::getAbilityCore)))))).collect((Collector)(Collectors.toSet()))));
	}
	public int countUnlockedAbilities() {
		this.unlockedAbilities.removeIf((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<AbilityCoreUnlockWrapper>) ((l0) -> {
			return (l0) == (null);
		}))))));
		return this.unlockedAbilities.size();
	}
	public int countUnlockedAbilities(Predicate<AbilityCore<?>> var1) {
		Predicate<AbilityCore<?>> var2 = var1;
		final Predicate<AbilityCore<?>> var2_f1 = var2;
		Predicate<AbilityCore<?>> var3 = (Predicate) ((Predicate<AbilityCore>) ((l0) -> {
			return var2_f1.test(l0);
		}));
		ToIntFunction var4 = (ToIntFunction) ((ToIntFunction<AbilityCoreUnlockWrapper>) ((l0) -> {
			return 1;
		}));
		return this.getUnlockedAbilities(var3).stream().mapToInt(var4).sum();
	}
	public boolean addPassiveAbility(IAbility var1) {
		if (this.hasPassiveAbility(var1)){
			return false;
		}else{
			if (!(this.hasUnlockedAbility(var1.getCore()))){
				return false;
			}else{
				var1.onEquip(this.owner);
				this.passiveAbilities.add(var1);
				return true;
			}
		}
	}
	public boolean removePassiveAbility(IAbility var1) {
		return this.removePassiveAbility(var1.getCore());
	}
	public boolean removePassiveAbility(AbilityCore<?> var1) {
		return this.passiveAbilities.removeIf((Predicate)(((Predicate<IAbility>)((l0) -> {
			if (l0.getCore().equals(var1)){
				l0.onRemove(this.owner);
				return true;
			}else{
				return false;
			}
		}))));
	}
	public boolean hasPassiveAbility(IAbility var1) {
		return this.hasPassiveAbility(var1.getCore());
	}
	public boolean hasPassiveAbility(AbilityCore<?> var1) {
		return this.getPassiveAbilities().stream().anyMatch((Predicate)(((Predicate<IAbility>)((l0) -> {
			return l0.getCore().equals(var1);
		}))));
	}
	@Nullable()
	public <T extends IAbility> @Nullable() T getPassiveAbility(AbilityCore<T> var1) {
		AbilityCore<T> var2 = var1;
		final AbilityCore<T> var2_f1 = var2;
		Predicate<IAbility> var3 = (l0) -> {
			return l0.getCore().equals(var2_f1);
		};
		return (T) (((IAbility)(((java.util.Optional)(((java.util.stream.Stream)(this.getPassiveAbilities().stream())).filter((Predicate)(var3)).findFirst())).orElse((Object)(null)))));
	}
	public Set<IAbility> getPassiveAbilities() {
		return ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.passiveAbilities.stream())).filter((Predicate)(((java.util.function.Predicate)(((Predicate<IAbility>)(Objects::nonNull)))))))).collect((Collector)(Collectors.toSet()))));
	}
	public Set<IAbility> getPassiveAbilities(Predicate<IAbility> var1) {
		Predicate<IAbility> var2 = var1;
		final Predicate<IAbility> var2_f1 = var2;
		Predicate<IAbility> var3 = (l0) -> {
			return (var2_f1.test(l0)) && (var2_f1.test(l0));
		};
		return ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.getPassiveAbilities().stream())).filter((Predicate)(var3)))).collect((Collector)(Collectors.toSet()))));
	}
	public void clearPassiveAbilities() {
		Iterator var1 = this.passiveAbilities.iterator();
		do{
			if (var1.hasNext()){
				((IAbility)(var1.next())).onRemove(this.owner);
				continue;
			}else{
				break;
			}
		} while (true);
		this.passiveAbilities.clear();
	}
	public void clearPassiveAbilities(Predicate<IAbility> var1) {
		Iterator var2 = this.passiveAbilities.iterator();
		do{
			if (var2.hasNext()){
				IAbility var3 = ((IAbility)(var2.next()));
				if (var1.test(var3)){
					var3.onRemove(this.owner);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		this.passiveAbilities.removeIf((Predicate)(((Predicate<IAbility>)((l0) -> {
			return var1.test(l0);
		}))));
	}
	public int countPassiveAbilities() {
		return this.getPassiveAbilities().size();
	}
	public int countPassiveAbilities(Predicate<IAbility> var1) {
		Predicate<IAbility> var2 = var1;
		final Predicate<IAbility> var2_f1 = var2;
		Predicate<IAbility> var3 = (l0) -> {
			return var2_f1.test(l0);
		};
		ToIntFunction<IAbility> var4 = (l0) -> {
			return 1;
		};
		return this.getPassiveAbilities().stream().filter(var3).mapToInt(var4).sum();
	}
	public boolean setEquippedAbility(int var1, @Nullable() IAbility var2) {
		if ((var1) < (0)){
			return false;
		}else{
			if ((var2) != (null)){
				if (this.hasEquippedAbility(var2)){
					return false;
				}else{
					EquipAbilityEvent var3 = ((EquipAbilityEvent)(NeoForge.EVENT_BUS.post((Event)(new EquipAbilityEvent(this.owner,var2)))));
					if (var3.isCanceled()){
						return false;
					}else{
						IAbility var4 = var3.getAbility();
						if (this.owner.level().isClientSide){
							Consumer<CooldownComponent> var5 = (l0) -> {
			l0.addStartEvent((CooldownComponent$IStartCooldownEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent$IStartCooldownEvent)(AuraLayer::addAbilityIcon))));
			l0.addEndEvent((CooldownComponent$IEndCooldownEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent$IEndCooldownEvent)(AuraLayer::addAbilityIcon))));
		};
							((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var5));
							Consumer<ChargeComponent> var6 = (l0) -> {
			l0.addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(AuraLayer::addAbilityIcon))));
			l0.addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(AuraLayer::addAbilityIcon))));
		};
							((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))))).ifPresent((Consumer)(var6));
							Consumer<ContinuousComponent> var7 = (l0) -> {
			l0.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(AuraLayer::addAbilityIcon))));
			l0.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(AuraLayer::addAbilityIcon))));
		};
							((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var7));
						}
						var4.onEquip(this.owner);
					}
				}
			}else{
				if ((this.activeAbilities[var1]) != (null)){
					this.activeAbilities[var1].onRemove(this.owner);
				}
			}
			this.activeAbilities[var1] = var2;
			return true;
		}
	}
	public boolean removeEquippedAbility(IAbility var1) {
		if ((var1) == (null)){
			return false;
		}else{
			return this.removeEquippedAbility(var1.getCore());
		}
	}
	public boolean removeEquippedAbility(AbilityCore<?> var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.activeAbilities.length)){
				IAbility var3 = this.activeAbilities[var2];
				if (((var3) != (null)) && (var3.getCore().equals(var1))){
					var3.onRemove(this.owner);
					this.activeAbilities[var2] = null;
					return true;
				}else{
					var2++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public boolean hasEquippedAbility(IAbility var1) {
		if ((var1) == (null)){
			return false;
		}else{
			return this.hasEquippedAbility(var1.getCore());
		}
	}
	public boolean hasEquippedAbility(AbilityCore<?> var1) {
		Predicate<? super IAbility> var2 = (l0) -> {
			return (l0) != (null);
		};
		return Arrays.stream(this.activeAbilities).filter(var2).anyMatch((Predicate)(((Predicate<IAbility>)((l0) -> {
			return l0.getCore().equals(var1);
		}))));
	}
	public int getEquippedAbilitySlot(IAbility var1) {
		if ((var1) == (null)){
			return -1;
		}else{
			return this.getEquippedAbilitySlot(var1.getCore());
		}
	}
	public int getEquippedAbilitySlot(AbilityCore<?> var1) {
		if ((var1) == (null)){
			return -1;
		}else{
			int var2 = 0;
			do{
				if ((var2) < (this.activeAbilities.length)){
					IAbility var3 = this.activeAbilities[var2];
					if (((var3) != (null)) && (var3.getCore().equals(var1))){
						return var2;
					}else{
						var2++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return -1;
		}
	}
	@Deprecated()		
@Nullable()
	public static <T extends IAbility> @Nullable() T getEquippedAbility(LivingEntity var0, AbilityCore<T> var1) {
		AbilityCore<T> var2 = var1;
		final AbilityCore<T> var2_f1 = var2;
		Function<AbilityData, IAbility> var3 = (l0) -> {
			return l0.getEquippedAbility(var2_f1);
		};
		return (T) (((IAbility)(((java.util.Optional)(get(var0))).map((Function)(var3)).orElse(null))));
	}
	@Nullable()
	public <T extends IAbility> @Nullable() T getEquippedAbility(T var1) {
		T var2 = var1;
		final T var2_f1 = var2;
		Predicate<? super IAbility> var3 = (l0) -> {
			return ((l0) != (null)) && (l0.equals(var2_f1));
		};
		return (T) (((IAbility)(((java.util.Optional)(((java.util.stream.Stream)(Arrays.stream(this.activeAbilities))).filter((Predicate)(var3)).findFirst())).orElse((Object)(null)))));
	}
	@Nullable()
	public <T extends IAbility> @Nullable() T getEquippedAbility(AbilityCore<T> var1) {
		AbilityCore<T> var2 = var1;
		final AbilityCore<T> var2_f1 = var2;
		Predicate<? super IAbility> var3 = (l0) -> {
			return ((l0) != (null)) && (l0.getCore().equals(var2_f1));
		};
		return (T) (((IAbility)(((java.util.Optional)(((java.util.stream.Stream)(Arrays.stream(this.activeAbilities))).filter((Predicate)(var3)).findFirst())).orElse((Object)(null)))));
	}
	@Nullable()
	public <T extends IAbility> @Nullable() T getEquippedAbility(int var1) {
		if ((var1) < (0)){
			return null;
		}else{
			if ((this.activeAbilities.length) < (var1)){
				return null;
			}else{
				return (T) (this.activeAbilities[var1]);
			}
		}
	}
	public List<IAbility> getRawEquippedAbilities() {
		LinkedList var1 = Lists.newLinkedList();
		Collections.addAll((Collection)(var1),this.activeAbilities);
		return var1;
	}
	public <T extends IAbility> Set<T> getEquippedAbilities() {
		return ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(Arrays.stream(this.activeAbilities))).filter((Predicate)(((java.util.function.Predicate)(((Predicate<IAbility>)(Objects::nonNull)))))))).collect((Collector)(Collectors.toSet()))));
	}
	public <T extends IAbility> Set<T> getEquippedAbilities(Predicate<IAbility> var1) {
		Predicate<IAbility> var2 = var1;
		final Predicate<IAbility> var2_f1 = var2;
		Predicate<? super IAbility> var3 = (l0) -> {
			return ((l0) != (null)) && (var2_f1.test(l0));
		};
		return ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(Arrays.stream(this.activeAbilities))).filter((Predicate)(var3)))).collect((Collector)(Collectors.toSet()))));
	}
	public <T extends IAbility> Set<T> getEquippedAbilitiesWith(AbilityComponentKey<?>... var1) {
		AbilityComponentKey<?>[] var2 = var1;
		Predicate<? super IAbility> var3 = (l0) -> {
			return ((l0) != (null)) && (Arrays.stream(var2).allMatch((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<AbilityComponentKey>) ((l2_0) -> {
				return l0.hasComponent(l2_0);
			})))))));
		};
		return ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(Arrays.stream(this.activeAbilities))).filter((Predicate)(var3)))).collect((Collector)(Collectors.toSet()))));
	}
	public Stream<IAbility> getEquippedAbilitiesInPool(AbilityPool var1) {
		AbilityPool var2 = var1;
		final AbilityPool var2_f1 = var2;
		Predicate<IAbility> var3 = (l0) -> {
			return (l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get())))) && (((PoolComponent)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get()))).get())).containsPool(var2_f1));
		};
		return (Stream<IAbility>) (Stream) (((java.util.stream.Stream)(this.getEquippedAndPassiveAbilities().stream())).filter((Predicate)(var3)));
	}
	public void clearEquippedAbilities() {
		int var1 = 0;
		do{
			if ((var1) < (this.activeAbilities.length)){
				IAbility var2 = this.activeAbilities[var1];
				if ((var2) != (null)){
					var2.onRemove(this.owner);
				}
				this.activeAbilities[var1] = null;
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void clearEquippedAbilities(Predicate<IAbility> var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.activeAbilities.length)){
				IAbility var3 = this.activeAbilities[var2];
				if (((var3) != (null)) && (var1.test(var3))){
					var3.onRemove(this.owner);
					this.activeAbilities[var2] = null;
				}
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public int countEquippedAbilities() {
		Predicate<? super IAbility> var1 = (l0) -> {
			return (l0) != (null);
		};
		ToIntFunction<IAbility> var2 = (l0) -> {
			return 1;
		};
		return Arrays.stream(this.activeAbilities).filter(var1).mapToInt(var2).sum();
	}
	public int countEquippedAbilities(Predicate<IAbility> var1) {
		Predicate<IAbility> var2 = var1;
		final Predicate<IAbility> var2_f1 = var2;
		Predicate<? super IAbility> var3 = (l0) -> {
			return ((l0) != (null)) && (var2_f1.test(l0));
		};
		ToIntFunction<IAbility> var4 = (l0) -> {
			return 1;
		};
		return Arrays.stream(this.activeAbilities).filter(var3).mapToInt(var4).sum();
	}
	@Nullable()
	public <T extends IAbility> @Nullable() T getEquippedOrPassiveAbility(AbilityCore<T> var1) {
		AbilityCore<T> var2 = var1;
		final AbilityCore<T> var2_f1 = var2;
		Predicate<IAbility> var3 = (l0) -> {
			return l0.getCore().equals(var2_f1);
		};
		return (T) (((IAbility)(((java.util.Optional)(((java.util.stream.Stream)(this.getEquippedAndPassiveAbilities().stream())).filter((Predicate)(var3)).findFirst())).orElse((Object)(null)))));
	}
	public Set<IAbility> getEquippedAndPassiveAbilities() {
		HashSet var1 = new HashSet((Collection)(this.getPassiveAbilities()));
		((java.util.Set)(var1)).addAll((Collection)(this.getEquippedAbilities()));
		return var1;
	}
	public Set<IAbility> getEquippedAndPassiveAbilities(Predicate<IAbility> var1) {
		HashSet var2 = new HashSet((Collection)(this.getPassiveAbilities(var1)));
		((java.util.Set)(var2)).addAll((Collection)(((xyz.pixelatedw.mineminenomi.data.entity.AbilityData)(this)).getEquippedAbilities((Predicate)(var1))));
		return var2;
	}
	public <T extends IAbility> T getPreviouslyUsedAbility() {
		return (T) (this.previouslyUsedAbility);
	}
	public void setPreviouslyUsedAbility(IAbility var1) {
		this.previouslyUsedAbility = var1;
	}
	public int getCombatBarSet() {
		return Math.max(0,this.currentCombatBarSet);
	}
	public void nextCombatBarSet(int var1) {
		this.lastCombatBarSet = this.currentCombatBarSet;
		this.currentCombatBarSet = Mth.clamp((this.currentCombatBarSet) + (var1),0,(ServerConfig.getAbilityBars()) - (1));
		if (((this.owner) != (null)) && (this.owner.level().isClientSide)){
			ModNetwork.sendToServer(new CSetCombatBarPacket(this.getCombatBarSet()));
		}
	}
	public void prevCombatBarSet(int var1) {
		this.lastCombatBarSet = this.currentCombatBarSet;
		this.currentCombatBarSet = Mth.clamp((this.currentCombatBarSet) - (var1),0,(ServerConfig.getAbilityBars()) - (1));
		if (((this.owner) != (null)) && (this.owner.level().isClientSide)){
			ModNetwork.sendToServer(new CSetCombatBarPacket(this.getCombatBarSet()));
		}
	}
	public int getLastCombatBarSet() {
		return this.lastCombatBarSet;
	}
	public void setCombatBarSet(int var1) {
		this.lastCombatBarSet = this.currentCombatBarSet;
		this.currentCombatBarSet = var1;
		if (((this.owner) != (null)) && (this.owner.level().isClientSide)){
			ModNetwork.sendToServer(new CSetCombatBarPacket(this.getCombatBarSet()));
		}
	}
	@Nullable()
	public @Nullable() Pair<AbilityCore<? extends IAbility>, Integer> getCoreIndexPair(AbilityNode var1) {
		Iterator var2 = this.nodes.entrySet().iterator();
		do{
			if (var2.hasNext()){
				Map.Entry var3 = ((Map.Entry)(var2.next()));
				if ((var1) == (var3.getValue())){
					return ((Pair)(var3.getKey()));
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return (Pair<AbilityCore<? extends IAbility>, Integer>) (Pair) (null);
	}
	public Set<AbilityNode> getNodes() {
		return (Set<AbilityNode>) (Set) (Set.copyOf((Collection)(this.nodes.values())));
	}
	public AbilityNode getNode(AbilityCore<? extends IAbility> var1, int var2) {
		return ((AbilityNode)(((java.util.Map)(this.nodes)).get((Object)(Pair.of((Object)(var1),(Object)(Integer.valueOf(var2)))))));
	}
	public int getUnlockedNodes() {
		AbilityData var1 = this;
		Predicate<AbilityNode> var2 = (l0) -> {
			return l0.isUnlocked(this.owner);
		};
		return (int)(this.nodes.values().stream().filter(var2).count());
	}
	public void addNode(AbilityCore<? extends IAbility> var1, int var2, AbilityNode var3) {
		this.nodes.put(Pair.of(var1,Integer.valueOf(var2)),var3);
	}
	public void startGCD() {
		if (this.isOnGCD()){
			return;
		}else{
			int var1 = this.getDefaultGCD();
			if ((var1) <= (0)){
				return;
			}else{
				if ((this.previousGCD) != (var1)){
					this.previousGCD = var1;
				}
				this.gcd = var1;
				return;
			}
		}
	}
	public void stopGCD() {
		Iterator var1 = this.getEquippedAndPassiveAbilities().iterator();
		do{
			if (var1.hasNext()){
				((IAbility)(var1.next())).getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))).ifPresent((Consumer)(((Consumer<SlotDecorationComponent>)(SlotDecorationComponent::resetDecoration))));
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public boolean isOnGCD() {
		return (this.gcd) > (0);
	}
	public void tickAbilities() {
		Iterator var1 = this.getEquippedAndPassiveAbilities().iterator();
		do{
			if (var1.hasNext()){
				IAbility var2 = ((IAbility)(var1.next()));
				try{
					var2.tick(this.owner);
				}catch(Exception var3){
					var3.printStackTrace();
				}
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void tickGCD() {
		if ((this.gcd) <= (0)){
			return;
		}else{
			this.gcd = (this.gcd) - (1);
			if ((this.gcd) <= (0)){
				this.stopGCD();
				LivingEntity var1 = this.owner;
				if (var1 instanceof Player){
					Player var2 = ((Player)(var1));
					ModNetwork.sendTo(new SStopGCDPacket(var2.getId()),var2);
				}else{
					return;
				}
			}
			return;
		}
	}
	public int getCurrentGCD() {
		return this.gcd;
	}
	public int getDefaultGCD() {
		try{
			AttributeInstance var1 = this.owner.getAttribute(ModAttributes.GCD);
			if ((var1) == (null)){
				return 20;
			}else{
				return (int)(Math.round(var1.getValue()));
			}
		}catch(Exception var1_1){
			return 20;
		}
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		try{
			CompoundTag var11 = null;
			ListTag var3 = new ListTag();
			Iterator var4 = this.getUnlockedAbilities().iterator();
			do{
				if (var4.hasNext()){
					var3.add(((AbilityCoreUnlockWrapper)(var4.next())).save());
					continue;
				}else{
					break;
				}
			} while (true);
			var2.put("unlocked_abilities",(Tag)(var3));
			ListTag var5 = new ListTag();
			Iterator var6 = this.getPassiveAbilities().iterator();
			do{
				if (var6.hasNext()){
					IAbility var7 = ((IAbility)(var6.next()));
					CompoundTag var8 = new CompoundTag();
					var8.putString("id",var7.getCore().getRegistryKey().toString());
					var7.save(var1,var8);
					var5.add(var8);
					continue;
				}else{
					break;
				}
			} while (true);
			var2.put("passive_abilities",(Tag)(var5));
			ListTag var7_1 = new ListTag();
			int var8_1 = 0;
			Iterator var9 = this.getRawEquippedAbilities().iterator();
			do{
				if (var9.hasNext()){
					IAbility var10 = ((IAbility)(var9.next()));
					if ((var10) == (null)){
						var11 = new CompoundTag();
						var11.putInt("slot",var8_1);
						var7_1.add(var11);
					}else{
						var11 = new CompoundTag();
						var11.putInt("slot",var8_1);
						var11.putString("id",var10.getCore().getRegistryKey().toString());
						var10.save(var1,var11);
						var7_1.add(var11);
					}
					var8_1++;
					continue;
				}else{
					break;
				}
			} while (true);
			var2.put("equipped_abilities",(Tag)(var7_1));
			var2.putInt("combat_bar_set",this.getCombatBarSet());
			ListTag var10_1 = new ListTag();
			Iterator var11_1 = this.nodes.entrySet().iterator();
			do{
				if (var11_1.hasNext()){
					Map.Entry var12 = ((Map.Entry)(var11_1.next()));
					AbilityCore var13 = ((AbilityCore)(((Pair)(var12.getKey())).getKey()));
					if ((var13) == (null)){
						continue;
					}else{
						int var14 = ((Integer)(((Pair)(var12.getKey())).getValue())).intValue();
						AbilityNode var15 = ((AbilityNode)(var12.getValue()));
						if ((var15) == (null)){
							continue;
						}else{
							CompoundTag var16 = var15.save();
							var16.putString("id",var13.getRegistryKey().toString());
							var16.putInt("index",var14);
							var10_1.add(var16);
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			var2.put("nodes",(Tag)(var10_1));
		}catch(Exception var3_1){
			var3_1.printStackTrace();
		}
		var2.putInt("gcd",this.gcd);
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		try{
			this.unlockedAbilities.clear();
			this.nodes.clear();
			ListTag var3 = var2.getList("unlocked_abilities",10);
			int var4 = 0;
			do{
				if ((var4) < (var3.size())){
					this.addUnlockedAbility(AbilityCoreUnlockWrapper.of(var3.getCompound(var4)));
					var4++;
					continue;
				}else{
					break;
				}
			} while (true);
			HashSet var5 = new HashSet();
			ListTag var6 = var2.getList("passive_abilities",10);
			int var7 = 0;
			do{
				if ((var7) < (var6.size())){
					CompoundTag var8 = var6.getCompound(var7);
					AbilityCore var9 = AbilityCore.get(ResourceLocation.parse(var8.getString("id")));
					if ((var9) == (null)){
						var7++;
						continue;
					}else{
						IAbility var10 = this.getPassiveAbility(var9);
						if ((var10) != (null)){
							if (!(this.hasUnlockedAbility(var10.getCore()))){
								this.removePassiveAbility(var9);
							}else{
								var10.load(var1,var8);
								var5.add(var9);
							}
						}else{
							IAbility var11_1 = var9.createAbility();
							if ((var11_1) == (null)){
								var7++;
								continue;
							}else{
								var11_1.load(var1,var8);
								var5.add(var9);
								this.addPassiveAbility(var11_1);
							}
						}
						var7++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			Iterator var8_1 = this.passiveAbilities.iterator();
			do{
				if (var8_1.hasNext()){
					if (((java.util.Set)(var5)).contains((Object)(((IAbility)(var8_1.next())).getCore()))){
						continue;
					}else{
						var8_1.remove();
						continue;
					}
				}else{
					break;
				}
			} while (true);
			ListTag var9_1 = var2.getList("equipped_abilities",10);
			int var10_1 = 0;
			do{
				if ((var10_1) < (var9_1.size())){
					CompoundTag var11 = var9_1.getCompound(var10_1);
					int var12 = var11.getInt("slot");
					IAbility var11_1 = this.getEquippedAbility(var12);
					if ((var11_1) != (null)){
						if (!(this.hasUnlockedAbility(var11_1.getCore()))){
							this.setEquippedAbility(var12,(IAbility)(null));
						}else{
							var11_1.load(var1,var11);
						}
					}else{
						if (var11.contains("id")){
							AbilityCore var14_1 = AbilityCore.get(ResourceLocation.parse(var11.getString("id")));
							if ((var14_1) == (null)){

							}else{
								IAbility var15 = var14_1.createAbility();
								var15.load(var1,var11);
								this.setEquippedAbility(var12,var15);
							}
						}else{
							this.setEquippedAbility(var12,(IAbility)(null));
						}
					}
					var10_1++;
					continue;
				}else{
					break;
				}
			} while (true);
			this.setCombatBarSet(var2.getInt("combat_bar_set"));
			ListTag var11_2 = var2.getList("nodes",10);
			int var12_1 = 0;
			do{
				if ((var12_1) < (var11_2.size())){
					CompoundTag var13 = var11_2.getCompound(var12_1);
					String var14 = var13.getString("id");
					int var15_1 = var13.getInt("index");
					AbilityCore var14_1 = AbilityCore.get(ResourceLocation.parse(var14));
					if ((var14_1) == (null)){
						var12_1++;
						continue;
					}else{
						AbilityNode var17 = var14_1.getNode(this.owner,var15_1);
						if ((var17) == (null)){
							var12_1++;
							continue;
						}else{
							var17.load(var13);
							this.addNode(var14_1,var15_1,var17);
							var12_1++;
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			Iterator var13_1 = WyRegistry.ABILITIES.iterator();
			LOOP_1:
			do{
				if (var13_1.hasNext()){
					AbilityCore var14_2 = ((AbilityCore)(var13_1.next()));
					int var15_2 = 0;
					do{
						if ((var15_2) < (var14_2.getNodeCount())){
							if ((this.getNode(var14_2,var15_2)) != (null)){
								var15_2++;
								continue;
							}else{
								AbilityNode var16 = var14_2.getNode(this.owner,var15_2);
								if ((var16) == (null)){
									var15_2++;
									continue;
								}else{
									this.addNode(var14_2,var15_2,var16);
									var15_2++;
									continue;
								}
							}
						}else{
							continue LOOP_1;
						}
					} while (true);
				}else{
					break;
				}
			} while (true);
		}catch(Exception var3_1){
			var3_1.printStackTrace();
		}
		this.gcd = var2.getInt("gcd");
	}
}