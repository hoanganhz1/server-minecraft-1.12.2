package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SlotDecorationComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityTickEvent;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityUseEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityUseEvent$Pre;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SStartGCDPacket;

public abstract class Ability implements IAbility, IDescriptiveAbility {
	// Fields
	public static final float RACIAL_NODE_MOD = 1.5F;
	private Component displayName;
	private ResourceLocation displayIcon;
	private AbilityUnlock unlock;
	private final AbilityCore<? extends IAbility> core;
	private long lastUseTime;
	private boolean isOGCD;
	protected final DisableComponent disableComponent;
	protected final CooldownComponent cooldownComponent;
	private Map<AbilityComponentKey<?>, AbilityComponent<?>> components;
	protected final Random random;
	private final PriorityEventPool<Ability$ICanUseEvent> onCanUseEvents;
	private final PriorityEventPool<Ability$ICanUseEvent> onContinueUseEvents;
	private final PriorityEventPool<Ability$IOnUseEvent> onUseEvents;
	private final PriorityEventPool<Ability$IOnTickEvent> onTickEvents;
	private final PriorityEventPool<Ability$IOnEquipEvent> onEquipEvents;
	private final PriorityEventPool<Ability$IOnRemoveEvent> onRemoveEvents;
	private Interval continueUseCheckInterval;

	public Ability(AbilityCore<? extends IAbility> var1) {
		this.unlock = AbilityUnlock.PROGRESSION;
		this.disableComponent = new DisableComponent((IAbility)(this));
		this.cooldownComponent = new CooldownComponent((IAbility)(this));
		this.components = new LinkedHashMap();
		this.random = new Random();
		this.onCanUseEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onContinueUseEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onUseEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onTickEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onEquipEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onRemoveEvents = (PriorityEventPool) (new PriorityEventPool());
		this.continueUseCheckInterval = new Interval(20);
		this.core = var1;
		if (this.isClientSide()){
			SlotDecorationComponent var2 = new SlotDecorationComponent((IAbility)(this));
			AbilityComponent[] var3 = new AbilityComponent[]{var2};
			this.addComponents(var3);
		}
		AbilityComponent[] var2_1 = new AbilityComponent[]{this.cooldownComponent,this.disableComponent};
		this.addComponents(var2_1);
	}
	public final void use(LivingEntity var1) {
		LivingEntity var4 = null;
		if ((!(this.isOGCD)) && (((Boolean)(AbilityData.get(var1).map((Function)(((Function<AbilityData, Boolean>)(AbilityData::isOnGCD)))).orElse(Boolean.valueOf(false)))).booleanValue())){
			return;
		}else{
			Result var2 = this.canUse(var1);
			if (var2.isFail()){
				if ((var2.getMessage()) != (null)){
					WyHelper.sendMessage(var1,var2.getMessage());
				}
				return;
			}else{
				if (((AbilityUseEvent$Pre)(NeoForge.EVENT_BUS.post((Event)(new AbilityUseEvent$Pre(var1,(IAbility)(this)))))).isCanceled()){
					return;
				}else{
					this.dispatchUseEvents(var1);
					this.lastUseTime = var1.level().getGameTime();
					NeoForge.EVENT_BUS.post((Event)(new AbilityUseEvent$Post(var1,(IAbility)(this))));
					Ability var3 = this;
					var4 = var1;
					final LivingEntity var4_f1 = var4;
					Consumer<AbilityData> var5 = (l0) -> {
			l0.setPreviouslyUsedAbility((IAbility)(this));
			if (!(this.isOGCD())){
				l0.startGCD();
				if (var4_f1 instanceof Player){
					Player lv1_3 = ((Player)(var4_f1));
					ModNetwork.sendTo(new SStartGCDPacket(var4_f1.getId()),lv1_3);
				}else{
					return;
				}
			}
		};
					((java.util.Optional)(AbilityData.get(var1))).ifPresent((Consumer)(var5));
					return;
				}
			}
		}
	}
	public void onEquip(LivingEntity var1) {
		Ability var2 = this;
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		Consumer<Ability$IOnEquipEvent> var4 = (l0) -> {
			l0.onEquip(var3_f1,this);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onEquipEvents)).dispatch((Consumer)(var4));
	}
	public void onRemove(LivingEntity var1) {
		LivingEntity var2 = var1;
		final LivingEntity var2_f1 = var2;
		Consumer<ContinuousComponent> var3 = (l0) -> {
			l0.stopContinuity(var2_f1);
		};
		((java.util.Optional)(this.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var3));
		LivingEntity var4 = var1;
		final LivingEntity var4_f2 = var4;
		Consumer<MorphComponent> var5 = (l0) -> {
			l0.stopMorph(var4_f2);
		};
		((java.util.Optional)(this.getComponent(((AbilityComponentKey)(ModAbilityComponents.MORPH.get()))))).ifPresent((Consumer)(var5));
		LivingEntity var6 = var1;
		final LivingEntity var6_f3 = var6;
		Consumer<ChangeStatsComponent> var7 = (l0) -> {
			l0.removeModifiers(var6_f3);
		};
		((java.util.Optional)(this.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHANGE_STATS.get()))))).ifPresent((Consumer)(var7));
		Ability var8 = this;
		LivingEntity var9 = var1;
		final LivingEntity var9_f4 = var9;
		Consumer<Ability$IOnRemoveEvent> var10 = (l0) -> {
			l0.onRemove(var9_f4,this);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onRemoveEvents)).dispatch((Consumer)(var10));
	}
	public void addEquipEvent(Ability$IOnEquipEvent var1) {
		this.addEquipEvent(100,var1);
	}
	public void addEquipEvent(int var1, Ability$IOnEquipEvent var2) {
		this.onEquipEvents.addEvent(var1,var2);
	}
	public void addRemoveEvent(Ability$IOnRemoveEvent var1) {
		this.addRemoveEvent(100,var1);
	}
	public void addRemoveEvent(int var1, Ability$IOnRemoveEvent var2) {
		this.onRemoveEvents.addEvent(var1,var2);
	}
	public void addUseEvent(Ability$IOnUseEvent var1) {
		this.addUseEvent(100,var1);
	}
	public void addUseEvent(int var1, Ability$IOnUseEvent var2) {
		this.onUseEvents.addEvent(var1,var2);
	}
	public boolean hasUseEvent(Ability$IOnUseEvent var1) {
		return this.onUseEvents.getEventsStream().anyMatch((Predicate)(((Predicate<Ability$IOnUseEvent>)((l0) -> {
			return l0.equals(var1);
		}))));
	}
	public void removeUseEvent(Ability$IOnUseEvent var1) {
		this.onUseEvents.removeEvent(var1);
	}
	public void dispatchUseEvents(LivingEntity var1) {
		Ability var2 = this;
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		Consumer<Ability$IOnUseEvent> var4 = (l0) -> {
			l0.onUse(var3_f1,this);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onUseEvents)).dispatch((Consumer)(var4));
	}
	public void addTickEvent(Ability$IOnTickEvent var1) {
		this.addTickEvent(100,var1);
	}
	public void addTickEvent(int var1, Ability$IOnTickEvent var2) {
		this.onTickEvents.addEvent(var1,var2);
	}
	public void clearUseChecks() {
		this.onCanUseEvents.clearEvents();
	}
	public void addCanUseCheck(Ability$ICanUseEvent var1) {
		this.addCanUseCheck(100,var1);
	}
	public void addCanUseCheck(int var1, Ability$ICanUseEvent var2) {
		this.onCanUseEvents.addEvent(var1,var2);
	}
	public Stream<Ability$ICanUseEvent> getCanUseChecksStream() {
		return (Stream<Ability$ICanUseEvent>) (Stream) (this.onCanUseEvents.getEventsStream());
	}
	public void clearContinueChecks() {
		this.onContinueUseEvents.clearEvents();
	}
	public void addContinueUseCheck(Ability$ICanUseEvent var1) {
		this.addContinueUseCheck(100,var1);
	}
	public void addContinueUseCheck(int var1, Ability$ICanUseEvent var2) {
		this.onContinueUseEvents.addEvent(var1,var2);
	}
	public Stream<Ability$ICanUseEvent> getContinueUseChecksStream() {
		return (Stream<Ability$ICanUseEvent>) (Stream) (this.onContinueUseEvents.getEventsStream());
	}
	public boolean canContinueToUse(LivingEntity var1) {
		return this.getContinueUseChecksStream().allMatch((Predicate)(((Predicate<Ability$ICanUseEvent>)((l0) -> {
			return l0.canUse(var1,this).isSuccess();
		}))));
	}
	public boolean isOnCooldown() {
		Optional var1 = this.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())));
		return (var1.isPresent()) && (((CooldownComponent)(var1.get())).isOnCooldown());
	}
	public boolean isContinuous() {
		Optional var1 = this.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())));
		return (var1.isPresent()) && (((ContinuousComponent)(var1.get())).isContinuous());
	}
	public boolean isCharging() {
		Optional var1 = this.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get())));
		return (var1.isPresent()) && (((ChargeComponent)(var1.get())).isCharging());
	}
	public boolean isDisabled() {
		Optional var1 = this.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())));
		return (var1.isPresent()) && (((DisableComponent)(var1.get())).isDisabled());
	}
	public String getDisplayNameOld() {
		return ((this.displayName) != (null)) ? (this.displayName.getString()) : (this.getCore().getLocalizedName().getString());
	}
	public boolean hasCustomName() {
		return (this.displayName) != (null);
	}
	public Component getDisplayName() {
		if ((this.displayName) != (null)){
			return this.displayName;
		}else{
			return this.getCore().getLocalizedName();
		}
	}
	public void setDisplayName(Component var1) {
		this.displayName = var1;
	}
	public boolean hasCustomIcon() {
		return (this.displayIcon) != (null);
	}
	public ResourceLocation getIcon(LivingEntity var1) {
		if (this.hasCustomIcon()){
			return this.displayIcon;
		}else{
			return this.getCore().getIcon();
		}
	}
	public void setDisplayIcon(ResourceLocation var1) {
		this.displayIcon = var1;
	}
	public void setDisplayIcon(AbilityCore<?> var1) {
		this.displayIcon = var1.getIcon();
	}
	public AbilityCategory getCategory() {
		return this.getCore().getCategory();
	}
	public SourceHakiNature getSourceHakiNature() {
		return this.getCore().getSourceHakiNature();
	}
	public ArrayList<SourceType> getSourceTypes() {
		return (ArrayList<SourceType>) (ArrayList) (this.getCore().getSourceTypes());
	}
	public boolean hasSourceTypes(SourceType var1) {
		return this.getCore().hasType(var1);
	}
	public SourceElement getSourceElement() {
		return this.getCore().getSourceElement();
	}
	public long getLastUseGametime() {
		return this.lastUseTime;
	}
	public AbilityCore<? extends IAbility> getCore() {
		return (AbilityCore<? extends IAbility>) (AbilityCore) (this.core);
	}
	public void setOGCD() {
		this.isOGCD = true;
	}
	public boolean isOGCD() {
		return this.isOGCD;
	}
	public final void tick(LivingEntity var1) {
		if (!(var1.level().isClientSide)){
			int var2 = 0;
			if (this.continueUseCheckInterval.canTick()){
				var2 = (!(this.canContinueToUse(var1))) ? (1) : (0);
			}
			if ((!(this.isDisabled())) && ((var2) == (0))){

			}else{
				AbilityHelper.emergencyStopAbility(var1,this);
			}
		}
		Ability var2_1 = this;
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		Consumer<Ability$IOnTickEvent> var4 = (l0) -> {
			l0.tick(var3_f1,this);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onTickEvents)).dispatch((Consumer)(var4));
		LivingEntity var5 = var1;
		final LivingEntity var5_f2 = var5;
		Consumer<? super AbilityComponent<?>> var6 = (Consumer) ((Consumer<AbilityComponent>) ((l0) -> {
			l0.tick(var5_f2);
		}));
		((java.lang.Iterable)(this.components.values())).forEach((Consumer)(var6));
		AbilityTickEvent var7 = new AbilityTickEvent(var1,(IAbility)(this));
		NeoForge.EVENT_BUS.post((Event)(var7));
	}
	public boolean equals(Object var1) {
		if ((var1 instanceof Ability) && (((this.getCore()) != (null)) && ((((Ability)(var1)).getCore()) != (null)))){
			if (this.getCore().equals(((Ability)(var1)).getCore())){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public final void save(HolderLookup.Provider var1, CompoundTag var2) {
		IAbility.super.save(var1,var2);
		if (this.hasCustomName()){
			var2.putString("displayName",Component.Serializer.toJson(this.getDisplayName(),var1));
		}
		if (this.hasCustomIcon()){
			var2.putString("displayIcon",this.displayIcon.toString());
		}
		this.saveAdditional(var2);
	}
	public final void load(HolderLookup.Provider var1, CompoundTag var2) {
		IAbility.super.load(var1,var2);
		if (var2.contains("displayName")){
			this.setDisplayName((Component)(Component.Serializer.fromJson(var2.getString("displayName"),var1)));
		}
		if (var2.contains("displayIcon")){
			this.setDisplayIcon(ResourceLocation.parse(var2.getString("displayIcon")));
		}
		this.loadAdditional(var2);
	}
	public void saveAdditional(CompoundTag var1) {
	}
	public void loadAdditional(CompoundTag var1) {
	}
	public void addComponents(AbilityComponent<?>... var1) {
		AbilityComponent<?>[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				AbilityComponent<?> var5 = var2[var4];
				this.components.put(var5.getKey(),var5);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		if (FMLEnvironment.dist.isClient()){
			Ability var5_1 = this;
			Consumer var6 = (Consumer) ((Consumer<AbilityComponent>) ((l0) -> {
			l0.postInit(this);
		}));
			this.components.values().stream().forEach(var6);
		}else{
			Predicate var5_2 = (Predicate) ((Predicate<AbilityComponent>) ((l0) -> {
			return !(l0.isClientSided());
		}));
			Ability var6_1 = this;
			Consumer var7 = (Consumer) ((Consumer<AbilityComponent>) ((l0) -> {
			l0.postInit(this);
		}));
			this.components.values().stream().filter(var5_2).forEach(var7);
		}
	}
	public boolean hasComponent(AbilityComponentKey<?> var1) {
		return this.components.containsKey(var1);
	}
	public <C extends AbilityComponent<?>> Optional<C> getComponent(AbilityComponentKey<C> var1) {
		if (!(this.hasComponent(var1))){
			return (Optional<C>) (Optional) (Optional.empty());
		}else{
			AbilityComponent var2 = ((AbilityComponent)(((java.util.Map)(this.components)).get((Object)(var1))));
			if ((var2.isClientSided()) && (!(FMLEnvironment.dist.isClient()))){
				return (Optional<C>) (Optional) (Optional.empty());
			}else{
				return (Optional<C>) (Optional) (Optional.of(var2));
			}
		}
	}
	public Map<AbilityComponentKey<?>, AbilityComponent<?>> getComponents() {
		return this.components;
	}
	public final Result canUse(LivingEntity var1) {
		LivingEntity var7_1 = null;
		if (var1.hasEffect((Holder)(ModEffects.IN_EVENT))){
			return Result.fail(null);
		}else{
			AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
			if (((var2) != (null)) && ((!(this.disableComponent.isDisabled())) && (!(this.cooldownComponent.isOnCooldown())))){
				if (!(var1.level().isClientSide)){
					Optional var3 = ProtectedAreasData.get(((ServerLevel)(var1.level()))).getProtectedArea(var1.blockPosition().getX(),var1.blockPosition().getY(),var1.blockPosition().getZ());
					if ((var3.isPresent()) && (!(((ProtectedArea)(var3.get())).canUseAbility(this.getCore())))){
						return Result.fail(ModI18nAbilities.MESSAGE_CANNOT_USE_HERE);
					}
				}
				Optional var3_1 = this.getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get())));
				if (var3_1.isPresent()){
					if (((PoolComponent)(var3_1.get())).getPools().stream().anyMatch((Predicate)(((Predicate<AbilityPool>)((l0) -> {
			Supplier<Boolean> lv16_1 = () -> {
				return Boolean.valueOf(false);
			};
			return ((Boolean)(l0.getFlagValue((String)("switchContinuous"),(Supplier)(lv16_1)))).booleanValue();
		}))))){
						Predicate<IAbility> var4 = (l0) -> {
			return (l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())))) && (((ContinuousComponent)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).get())).isContinuous());
		};
						Predicate<IAbility> var5 = (l0) -> {
			return (l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get())))) && (((PoolComponent)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get()))).get())).getPools().stream().anyMatch((Predicate)(((Predicate<AbilityPool>)((l2_0) -> {
				Supplier<Boolean> lv19_1 = () -> {
					return Boolean.valueOf(false);
				};
				return ((Boolean)(l2_0.getFlagValue((String)("switchContinuous"),(Supplier)(lv19_1)))).booleanValue();
			})))));
		};
						Optional var6 = var2.getEquippedAbilities().stream().filter(var4).filter(var5).findFirst();
						if (var6.isPresent()){
							((ContinuousComponent)(((IAbility)(var6.get())).getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).get())).stopContinuity(var1);
							((PoolComponent)(((IAbility)(var6.get())).getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get()))).get())).stopPoolInUse(var1);
						}
					}
					if (!(((PoolComponent)(var3_1.get())).canUsePool(var1))){
						return Result.fail(ModI18nAbilities.MESSAGE_POOL_ALREADY_IN_USE);
					}
				}
				Optional var4_1 = this.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())));
				if ((var1 instanceof Player) && ((var4_1.isPresent()) && ((!(((ContinuousComponent)(var4_1.get())).isContinuous())) && (!(((ContinuousComponent)(var4_1.get())).isParallel()))))){
					Iterator var5_1 = var2.getEquippedAbilities().iterator();
					do{
						if (var5_1.hasNext()){
							IAbility var6_1 = ((IAbility)(var5_1.next()));
							if (var6_1.equals(this)){
								continue;
							}else{
								Optional var7 = var6_1.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())));
								if ((var7.isPresent()) && ((((ContinuousComponent)(var7.get())).isContinuous()) && (!(((ContinuousComponent)(var7.get())).isParallel())))){
									if ((ServerConfig.getStopContinuousAbility()) && (this.hasComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))){
										((ContinuousComponent)(var7.get())).stopContinuity(var1);
										continue;
									}else{
										return Result.fail(null);
									}
								}else{
									continue;
								}
							}
						}else{
							break;
						}
					} while (true);
				}
				if ((var4_1.isPresent()) && (((ContinuousComponent)(var4_1.get())).isContinuous())){
					return Result.success();
				}else{
					Optional var5_2 = this.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get())));
					if ((var5_2.isPresent()) && (((ChargeComponent)(var5_2.get())).isCharging())){
						return Result.success();
					}else{
						Ability var6_2 = this;
						var7_1 = var1;
						final LivingEntity var7_1_f1 = var7_1;
						Function<Ability$ICanUseEvent, Result> var8 = (l0) -> {
			return l0.canUse(var7_1_f1,this);
		};
						Optional var9 = this.onCanUseEvents.getEventsStream().map(var8).filter((Predicate)(((Predicate<Result>)(Result::isFail)))).findFirst();
						if (var9.isPresent()){
							return ((Result)(var9.get()));
						}else{
							return Result.success();
						}
					}
				}
			}else{
				return Result.fail(null);
			}
		}
	}
	public void appendCoreDescription(Player var1, AbilityTooltip var2) {
		Ability var3 = this;
		AbilityTooltip var4 = var2;
		Player var5 = var1;
		final AbilityTooltip var4_f1 = var4;
		final Player var5_f2 = var5;
		Consumer<? super AbilityDescriptionLine> var6 = (l0) -> {
			var4_f1.add(l0.getTextComponent((LivingEntity)(var5_f2),this));
		};
		((java.lang.Iterable)(this.core.getDescription())).forEach((Consumer)(var6));
		var2.add(AbilityTooltipsHelper.NEW_LINE);
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		AttributeInstance var3 = var1.getAttribute(ModAttributes.GCD);
		boolean var4 = true;
		if (((var3) != (null)) && (!((var3.getValue()) <= (0D)))){

		}else{
			var4 = false;
		}
		if ((var2.getAbility().isOGCD()) && (var4)){
			var2.add(1050,ModI18nAbilities.DESCRIPTION_STAT_NAME_OGCD);
		}
	}
	public boolean isClientSide() {
		return FMLEnvironment.dist.isClient();
	}
}