package xyz.pixelatedw.mineminenomi.entities.mobs;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SelfHealEatGoal;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenTraderScreenPacket;

public abstract class TraderEntity extends OPEntity {
	// Fields
	private static final float SPAWNER_DESPAWN_DISTANCE = 40000F;
	protected List<TradeEntry> tradeEntries;
	private boolean isTrading;
	private boolean canBuy;
	private int customerCheckTick;

	public TraderEntity(EntityType<? extends TraderEntity> var1, Level var2) {
		this(var1,var2,null);
	}
	public TraderEntity(EntityType<? extends TraderEntity> var1, Level var2, ResourceLocation[] var3) {
		super((EntityType)(var1),var2,var3);
		this.tradeEntries = new ArrayList();
		this.isTrading = false;
		this.customerCheckTick = 40;
	}
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new PanicGoal((PathfinderMob)(this),2D)));
		this.goalSelector.addGoal(3,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.8D)));
		this.goalSelector.addGoal(4,(Goal)(new SelfHealEatGoal((Mob)(this))));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Mob.class,8F)));
		this.goalSelector.addGoal(5,(Goal)(new RandomLookAroundGoal((Mob)(this))));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,25D).add(Attributes.MOVEMENT_SPEED,0.20000000298023224D).add(Attributes.ATTACK_DAMAGE,1D).add(Attributes.MAX_HEALTH,20D).add(Attributes.ARMOR,2D);
	}
	public boolean removeWhenFarAway(double var1) {
		if ((this.isSpawnedViaSpawner()) && ((var1) < (40000D))){
			return false;
		}else{
			if ((this.hasCustomName()) && (!(ServerConfig.getDespawnWithNametag()))){
				return false;
			}else{
				return true;
			}
		}
	}
	public abstract boolean canTrade(Player var0);
	public abstract Component getTradeFailMessage();
	public abstract ResourceKey<LootTable> getTradeTable();
	public abstract Currency getCurrency();
	public void tick() {
		super.tick();
		int var1 = (this.customerCheckTick) - (1);
		this.customerCheckTick = var1;
		if ((var1) <= (0)){
			List var2 = TargetHelper.getNearbyPlayers((LevelAccessor)(this.level()),this.position(),3D,(Predicate)(null));
			if ((var2.size()) > (0)){
				this.lookAt(EntityAnchorArgument.Anchor.EYES,((Player)(var2.get(0))).getEyePosition(0F));
				this.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),40,0,false,false));
			}
			this.customerCheckTick = 40;
		}
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		ListTag var2 = new ListTag();
		Iterator var3 = this.tradeEntries.iterator();
		do{
			if (var3.hasNext()){
				TradeEntry var4 = ((TradeEntry)(var3.next()));
				CompoundTag var5 = new CompoundTag();
				CompoundTag var6 = ((CompoundTag)(var4.itemStack().save((HolderLookup.Provider)(this.registryAccess()),(Tag)(var5))));
				var2.add(var6);
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("Items",(Tag)(var2));
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		ListTag var2 = var1.getList("Items",10);
		int var3 = 0;
		do{
			if ((var3) < (var2.size())){
				CompoundTag var4 = var2.getCompound(var3);
				ItemStack var5 = ((ItemStack)(ItemStack.parse((HolderLookup.Provider)(this.registryAccess()),(Tag)(var4)).orElse(ItemStack.EMPTY)));
				this.tradeEntries.add(new TradeEntry(var5));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void setStacksFromNBT(CompoundTag var1) {
		ListTag var2 = var1.getList("Items",10);
		int var3 = 0;
		do{
			if ((var3) < (var2.size())){
				CompoundTag var4 = var2.getCompound(var3);
				ItemStack var5 = ((ItemStack)(ItemStack.parse((HolderLookup.Provider)(this.registryAccess()),(Tag)(var4)).orElse(ItemStack.EMPTY)));
				this.tradeEntries.add(new TradeEntry(var5));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	@Nullable()
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		var4 = super.finalizeSpawn(var1,var2,var3,var4);
		LootTable var5 = this.level().getServer().reloadableRegistries().getLootTable(this.getTradeTable());
		ObjectArrayList var6 = var5.getRandomItems(new LootParams.Builder(((ServerLevel)(this.level()))).create(LootContextParamSets.EMPTY));
		this.tradeEntries = (List) (((List)(((java.util.stream.Stream)(var6.stream().map((Function)(((Function<ItemStack, TradeEntry>)(TradeEntry::new)))))).collect((Collector)(Collectors.toList())))));
		return var4;
	}
	public List<TradeEntry> getTradingItems() {
		return this.tradeEntries;
	}
	public void setIsTrading(boolean var1) {
		this.isTrading = var1;
	}
	public void setTradingItems(List<TradeEntry> var1) {
		this.tradeEntries = var1;
	}
	public boolean canBuyFromPlayers() {
		return this.canBuy;
	}
	public void setCanBuyFromPlayers() {
		this.canBuy = true;
	}
	protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
		if (((var2) == (InteractionHand.MAIN_HAND)) && (!(var1.level().isClientSide))){
			ModNetwork.sendTo(new SOpenTraderScreenPacket(this.getId(),this.tradeEntries),var1);
			return InteractionResult.SUCCESS;
		}else{
			return InteractionResult.PASS;
		}
	}
}