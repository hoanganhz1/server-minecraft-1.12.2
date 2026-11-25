package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.blocks.FlagBlock;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.items.FlagItem;

public class FlagBlockEntity extends BlockEntity {
	// Fields
	private UUID ownerUUID;
	private Optional<Faction> faction;
	private boolean isOnFire;
	private int fireTick;
	@Nullable() private Entity lastAttacker;
	private BlockPos masterPos;
	private FlagBlockEntity master;
	private boolean isSub;
	private List<BlockPos> subsList;
	private Optional<JollyRoger> jollyRoger;

	public FlagBlockEntity(BlockPos var1, BlockState var2) {
		super(((BlockEntityType)(ModBlockEntities.FLAG.get())),var1,var2);
		this.ownerUUID = Util.NIL_UUID;
		this.faction = (Optional) (((java.util.Optional)(Optional.empty())));
		this.fireTick = 200;
		this.subsList = new ArrayList();
		this.jollyRoger = (Optional) (((java.util.Optional)(Optional.empty())));
	}
	public void setJollyRoger(JollyRoger var1) {
		this.jollyRoger = (Optional) (Optional.ofNullable(var1));
	}
	@Nullable()
	public Optional<JollyRoger> getJollyRoger() {
		return this.jollyRoger;
	}
	public void setOnFire(boolean var1) {
		this.isOnFire = var1;
		if (var1){
			this.fireTick = 200;
		}
		this.setChanged();
	}
	public boolean isOnFire() {
		return this.isOnFire;
	}
	public void setLastAttacker(Entity var1) {
		this.lastAttacker = var1;
	}
	@Nullable()
	public Entity getLastAttacker() {
		return this.lastAttacker;
	}
	public void setFaction(Faction var1) {
		this.faction = (Optional) (Optional.ofNullable(var1));
		this.setChanged();
	}
	public void setOwner(@Nullable() LivingEntity var1) {
		if ((var1) != (null)){
			this.ownerUUID = var1.getUUID();
			this.faction = (Optional) (((Optional)(((java.util.Optional)(EntityStatsData.get(var1).map((Function)(((Function<EntityStatsData, Optional>)(EntityStatsData::getFaction)))))).orElse((Object)(Optional.empty())))));
			this.setChanged();
		}
	}
	public Optional<Faction> getFaction() {
		return this.faction;
	}
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	public boolean isMaster() {
		return !(this.isSub());
	}
	public boolean isSub() {
		return this.isSub;
	}
	@Nullable()
	public FlagBlockEntity getMaster() {
		if (!(this.isSub)){
			return this;
		}else{
			if (((this.level) != (null)) && (((this.masterPos) != (null)) && ((this.master) == (null)))){
				BlockEntity var1 = this.level.getBlockEntity(this.masterPos);
				if (var1 instanceof FlagBlockEntity){
					this.master = ((FlagBlockEntity)(var1));
				}
			}
			return this.master;
		}
	}
	public void setMaster(FlagBlockEntity var1) {
		this.masterPos = var1.getBlockPos();
		this.master = var1;
		this.isSub = true;
	}
	public void addSub(BlockPos var1) {
		this.subsList.add(var1);
	}
	public static void tick(Level var0, BlockPos var1, BlockState var2, FlagBlockEntity var3) {
		if ((var3.level) == (null)){
			return;
		}else{
			if (var3.isOnFire()){
				if (var3.isSub){
					if (var3.level.isRaining()){
						var3.master.setOnFire(false);
						return;
					}else{
						var3.master.setOnFire(true);
					}
				}else{
					if (var3.level.isRaining()){
						var3.setOnFire(false);
						return;
					}else{
						int var4 = (var3.fireTick) - (1);
						var3.fireTick = var4;
						if ((var4) <= (0)){
							var3.breakAllBlocks(false);
						}else{
							return;
						}
					}
				}
			}
			return;
		}
	}
	public void breakAllBlocks(boolean var1) {
		if (this.isSub()){
			return;
		}else{
			int var2 = (!(this.isOnFire)) ? (1) : (0);
			if (var1){
				var2 = 0;
			}
			if ((this.getLevel()) != (null)){
				if ((var2) != (0)){
					BlockState var3 = this.level.getBlockState(this.getBlockPos());
					if (!(var3.isAir())){
						ItemStack var4 = new ItemStack(((ItemLike)(ModBlocks.FLAG.get())));
						if (var3.hasProperty((Property)(FlagBlock.SIZE))){
							FlagItem.setCanvasSize(var4,((CanvasSize)(var3.getValue((Property)(FlagBlock.SIZE)))));
						}
						ItemEntity var5 = new ItemEntity(this.level,(double)(this.getBlockPos().getX()),(double)(this.getBlockPos().getY()),(double)(this.getBlockPos().getZ()),var4);
						this.level.addFreshEntity((Entity)(var5));
					}
				}
				this.getLevel().destroyBlock(this.getBlockPos(),false);
				Iterator var3_1 = this.subsList.iterator();
				do{
					if (var3_1.hasNext()){
						BlockPos var4_1 = ((BlockPos)(var3_1.next()));
						this.getLevel().destroyBlock(var4_1,false);
						continue;
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
	public void saveAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.saveAdditional(var1,var2);
		var1.putUUID("ownerUUID",this.ownerUUID);
		CompoundTag var3 = var1;
		final CompoundTag var3_f1 = var3;
		Consumer<? super Faction> var4 = (l0) -> {
			var3_f1.putString("faction",l0.getRegistryName().toString());
		};
		((java.util.Optional)(this.faction)).ifPresent((Consumer)(var4));
		var1.putBoolean("isOnFire",this.isOnFire);
		var1.putInt("fireTicks",this.fireTick);
		var1.putBoolean("isSub",this.isSub);
		if (this.isSub){
			var1.putLong("masterPos",this.masterPos.asLong());
		}else{
			LongArrayTag var5 = new LongArrayTag((List)(new ArrayList()));
			Iterator var6 = this.subsList.iterator();
			do{
				if (var6.hasNext()){
					var5.add(LongTag.valueOf(((BlockPos)(var6.next())).asLong()));
					continue;
				}else{
					break;
				}
			} while (true);
			var1.put("subs",(Tag)(var5));
		}
		CompoundTag var5_1 = var1;
		final CompoundTag var5_1_f2 = var5_1;
		Consumer<? super JollyRoger> var6_1 = (l0) -> {
			var5_1_f2.put("JollyRoger",(Tag)(l0.write()));
		};
		((java.util.Optional)(this.jollyRoger)).ifPresent((Consumer)(var6_1));
	}
	public void loadAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.loadAdditional(var1,var2);
		this.ownerUUID = var1.getUUID("ownerUUID");
		if (var1.contains("faction")){
			Faction var3 = ((Faction)(WyRegistry.FACTIONS.get(ResourceLocation.parse(var1.getString("faction")))));
			this.faction = (Optional) (Optional.ofNullable(var3));
		}
		this.isOnFire = var1.getBoolean("isOnFire");
		this.fireTick = var1.getInt("fireTicks");
		this.isSub = var1.getBoolean("isSub");
		if (this.isSub){
			this.masterPos = BlockPos.of(var1.getLong("masterPos"));
		}else{
			long[] var3_1 = var1.getLongArray("subs");
			int var4 = var3_1.length;
			int var5 = 0;
			do{
				if ((var5) < (var4)){
					BlockPos var6 = BlockPos.of(var3_1[var5]);
					this.subsList.add(var6);
					var5++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		if (var1.contains("JollyRoger",10)){
			this.setJollyRoger(JollyRoger.from(var1.getCompound("JollyRoger")));
		}
		this.setChanged();
	}
	public CompoundTag getUpdateTag(HolderLookup.Provider var1) {
		CompoundTag var2 = super.getUpdateTag(var1);
		this.saveAdditional(var2,var1);
		return var2;
	}
	@Nullable()
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create((BlockEntity)(this));
	}
}