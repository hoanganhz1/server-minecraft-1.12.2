package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public abstract class PassiveAbility implements IAbility, IDescriptiveAbility {
	// Fields
	private final AbilityCore<? extends PassiveAbility> core;
	private Component displayName;
	private ResourceLocation displayIcon;
	private ResourceLocation cachedIcon;
	private float tickRate;
	private float currentTick;
	protected final DisableComponent disableComponent;
	protected final CooldownComponent cooldownComponent;
	protected final PauseTickComponent pauseTickComponent;
	private PriorityEventPool<Ability$ICanUseEvent> onCanUseEvents;
	private PriorityEventPool<PassiveAbility$IDuringPassiveEvent> duringPassiveEvents;
	private PriorityEventPool<PassiveAbility$IOnEquipEvent> onEquipEvents;
	private PriorityEventPool<PassiveAbility$IOnRemoveEvent> onRemoveEvents;
	private Map<AbilityComponentKey<?>, AbilityComponent<?>> components;
	protected final Random random;

	public PassiveAbility(AbilityCore<? extends PassiveAbility> var1) {
		this.tickRate = 1F;
		this.currentTick = 0F;
		this.disableComponent = new DisableComponent((IAbility)(this));
		this.cooldownComponent = new CooldownComponent((IAbility)(this));
		this.pauseTickComponent = new PauseTickComponent((IAbility)(this));
		this.onCanUseEvents = (PriorityEventPool) (new PriorityEventPool());
		this.duringPassiveEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onEquipEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onRemoveEvents = (PriorityEventPool) (new PriorityEventPool());
		this.components = new LinkedHashMap();
		this.random = new Random();
		this.core = var1;
		AbilityComponent[] var2 = new AbilityComponent[]{this.cooldownComponent,this.disableComponent,this.pauseTickComponent};
		this.addComponents(var2);
	}
	public void onEquip(LivingEntity var1) {
		PassiveAbility var2 = this;
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		Consumer<PassiveAbility$IOnEquipEvent> var4 = (l0) -> {
			l0.onEquip(var3_f1,this);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onEquipEvents)).dispatch((Consumer)(var4));
	}
	public void onRemove(LivingEntity var1) {
		LivingEntity var2 = var1;
		final LivingEntity var2_f1 = var2;
		Consumer<ChangeStatsComponent> var3 = (l0) -> {
			l0.removeModifiers(var2_f1);
		};
		((java.util.Optional)(this.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHANGE_STATS.get()))))).ifPresent((Consumer)(var3));
		PassiveAbility var4 = this;
		LivingEntity var5 = var1;
		final LivingEntity var5_f2 = var5;
		Consumer<PassiveAbility$IOnRemoveEvent> var6 = (l0) -> {
			l0.onRemove(var5_f2,this);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onRemoveEvents)).dispatch((Consumer)(var6));
	}
	public void use(LivingEntity var1) {
	}
	public void tick(LivingEntity var1) {
		LivingEntity var5 = null;
		LivingEntity var2 = var1;
		final LivingEntity var2_f1 = var2;
		Consumer<? super AbilityComponent<?>> var3 = (Consumer) ((Consumer<AbilityComponent>) ((l0) -> {
			l0.tick(var2_f1);
		}));
		((java.lang.Iterable)(this.components.values())).forEach((Consumer)(var3));
		if (this.canUse(var1).isFail()){
			return;
		}else{
			if (!(var1.level().isClientSide)){
				Optional var4 = ProtectedAreasData.get(((ServerLevel)(var1.level()))).getProtectedArea(var1.getBlockX(),var1.getBlockY(),var1.getBlockZ());
				if ((var4.isPresent()) && (!(((ProtectedArea)(var4.get())).canUseAbility(this.getCore())))){
					return;
				}
			}
			float var4_1 = (this.currentTick) - (1F);
			this.currentTick = var4_1;
			if ((var4_1) > (0F)){
				return;
			}else{
				this.currentTick = this.tickRate;
				var5 = var1;
				final LivingEntity var5_f2 = var5;
				Consumer<PassiveAbility$IDuringPassiveEvent> var6 = (l0) -> {
			l0.duringPassive(var5_f2);
		};
				((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.duringPassiveEvents)).dispatch((Consumer)(var6));
				return;
			}
		}
	}
	public Result canUse(LivingEntity var1) {
		LivingEntity var3 = null;
		if ((!(this.disableComponent.isDisabled())) && ((!(this.pauseTickComponent.isPaused())) && (!(this.cooldownComponent.isOnCooldown())))){
			if ((this.onCanUseEvents.countEventsInPool()) > (0L)){
				PassiveAbility var2 = this;
				var3 = var1;
				final LivingEntity var3_f1 = var3;
				Function<Ability$ICanUseEvent, Result> var4 = (l0) -> {
			return l0.canUse(var3_f1,this);
		};
				Optional var5 = this.onCanUseEvents.getEventsStream().map(var4).filter((Predicate)(((Predicate<Result>)(Result::isFail)))).findFirst();
				if (var5.isPresent()){
					return ((Result)(var5.get()));
				}
			}
			return Result.success();
		}else{
			return Result.fail(null);
		}
	}
	public void addEquipEvent(PassiveAbility$IOnEquipEvent var1) {
		this.onEquipEvents.addEvent(var1);
	}
	public void addEquipEvent(int var1, PassiveAbility$IOnEquipEvent var2) {
		this.onEquipEvents.addEvent(var1,var2);
	}
	public void addRemoveEvent(PassiveAbility$IOnRemoveEvent var1) {
		this.onRemoveEvents.addEvent(var1);
	}
	public void addRemoveEvent(int var1, PassiveAbility$IOnRemoveEvent var2) {
		this.onRemoveEvents.addEvent(var1,var2);
	}
	public void addCanUseCheck(Ability$ICanUseEvent var1) {
		this.onCanUseEvents.addEvent(var1);
	}
	public void addCanUseCheck(int var1, Ability$ICanUseEvent var2) {
		this.onCanUseEvents.addEvent(var1,var2);
	}
	public void addDuringPassiveEvent(PassiveAbility$IDuringPassiveEvent var1) {
		this.duringPassiveEvents.addEvent(var1);
	}
	public void addDuringPassiveEvent(int var1, PassiveAbility$IDuringPassiveEvent var2) {
		this.duringPassiveEvents.addEvent(var1,var2);
	}
	public void setTickRate(float var1) {
		this.tickRate = var1;
		this.currentTick = var1;
	}
	public AbilityCore<?> getCore() {
		return (AbilityCore<?>) (AbilityCore) (this.core);
	}
	public boolean hasCustomName() {
		return (this.displayName) != (null);
	}
	public void setDisplayName(Component var1) {
		this.displayName = var1;
	}
	public Component getDisplayName() {
		if (this.hasCustomName()){
			return this.displayName;
		}else{
			return this.getCore().getLocalizedName();
		}
	}
	public boolean hasCustomIcon() {
		return (this.displayIcon) != (null);
	}
	public void setDisplayIcon(ResourceLocation var1) {
		this.displayIcon = var1;
	}
	public ResourceLocation getIcon(LivingEntity var1) {
		if (((this.core.getIcon()) == (null)) && ((this.cachedIcon) == (null))){
			ResourceLocation var2 = this.core.getRegistryKey();
			String var3 = var2.getPath();
			String var4 = "textures/abilities/" + var3 + ".png";
			this.cachedIcon = ResourceLocation.fromNamespaceAndPath(var2.getNamespace(),var4);
			return this.cachedIcon;
		}else{
			if (this.hasCustomIcon()){
				return this.displayIcon;
			}else{
				if ((this.getCore().getIcon()) != (null)){
					return this.getCore().getIcon();
				}else{
					return this.cachedIcon;
				}
			}
		}
	}
	public boolean isPaused() {
		return this.pauseTickComponent.isPaused();
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
			PassiveAbility var5_1 = this;
			Consumer var6 = (Consumer) ((Consumer<AbilityComponent>) ((l0) -> {
			l0.postInit(this);
		}));
			this.components.values().stream().forEach(var6);
		}else{
			Predicate var5_2 = (Predicate) ((Predicate<AbilityComponent>) ((l0) -> {
			return !(l0.isClientSided());
		}));
			PassiveAbility var6_1 = this;
			Consumer var7 = (Consumer) ((Consumer<AbilityComponent>) ((l0) -> {
			l0.postInit(this);
		}));
			this.components.values().stream().filter(var5_2).forEach(var7);
		}
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
	public void appendCoreDescription(Player var1, AbilityTooltip var2) {
		PassiveAbility var3 = this;
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
	}
	public boolean hasComponent(AbilityComponentKey<?> var1) {
		return this.components.containsKey(var1);
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
	public boolean isClientSide() {
		return FMLEnvironment.dist.isClient();
	}
	public boolean isOGCD() {
		return true;
	}
}