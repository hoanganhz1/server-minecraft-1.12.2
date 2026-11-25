package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class CustomSpawnerBlockEntity extends BlockEntity {
	// Fields
	private EntityType<?> entityToSpawn;
	private int spawnLimit;
	private int playerDistance;
	private ArrayList<UUID> spawnedEntities;
	private boolean lock;

	public CustomSpawnerBlockEntity(BlockPos var1, BlockState var2) {
		super(((BlockEntityType)(ModBlockEntities.CUSTOM_SPAWNER.get())),var1,var2);
		this.entityToSpawn = (EntityType) (EntityType.PIG);
		this.spawnLimit = 5;
		this.playerDistance = 100;
		this.spawnedEntities = (ArrayList) (new ArrayList());
	}
	public CustomSpawnerBlockEntity setSpawnerMob(EntityType<?> var1) {
		this.entityToSpawn = var1;
		this.setChanged();
		return this;
	}
	public CustomSpawnerBlockEntity setSpawnerLimit(int var1) {
		this.spawnLimit = var1;
		this.setChanged();
		return this;
	}
	public CustomSpawnerBlockEntity setPlayerDistance(int var1) {
		this.playerDistance = var1;
		this.setChanged();
		return this;
	}
	public void lock() {
		this.lock = true;
	}
	private boolean isActivated() {
		BlockPos var1 = this.getBlockPos();
		return this.getLevel().hasNearbyAlivePlayer(((double)(var1.getX())) + (0.5D),((double)(var1.getY())) + (0.5D),((double)(var1.getZ())) + (0.5D),(double)(this.playerDistance));
	}
	public static void tick(Level var0, BlockPos var1, BlockState var2, CustomSpawnerBlockEntity var3) {
		if (((var0) != (null)) && (!(var3.lock))){
			if ((!(var0.isClientSide)) && (((var0.getGameTime()) % (10L)) == (0L))){
				if (var3.isActivated()){
					if ((var0.getBlockState(var3.worldPosition.below()).getBlock()) == (Blocks.AIR)){
						var0.setBlockAndUpdate(var3.worldPosition,Blocks.AIR.defaultBlockState());
						return;
					}else{
						if ((var3.spawnedEntities.size()) > (0)){
							int var4 = 0;
							Iterator var5 = var3.spawnedEntities.iterator();
							do{
								if (var5.hasNext()){
									UUID var6 = ((UUID)(var5.next()));
									Entity var7 = ((ServerLevel)(var0)).getEntity(var6);
									if (((var7) != (null)) && (var7.isAlive())){
										var4++;
										continue;
									}else{
										continue;
									}
								}else{
									break;
								}
							} while (true);
							if ((ServerConfig.getDestroySpawner()) && ((var4) == (0))){
								var0.setBlockAndUpdate(var3.worldPosition,Blocks.AIR.defaultBlockState());
							}
						}
						if (((var3.entityToSpawn) != (null)) && ((var3.spawnedEntities.size()) < (var3.spawnLimit))){
							LivingEntity var4_1 = ((LivingEntity)(var3.entityToSpawn.spawn(((ServerLevel)(var0)),(Consumer)(null),var3.worldPosition.above(),MobSpawnType.SPAWNER,false,false)));
							if ((var4_1) != (null)){
								int var5_1 = (int)((((var0.random.nextDouble()) - (var0.random.nextDouble())) * (2D)) + (0.5D));
								int var6_1 = (int)((((var0.random.nextDouble()) - (var0.random.nextDouble())) * (2D)) + (0.5D));
								BlockPos var7_1 = var3.getBlockPos().offset(var5_1,0,var6_1);
								var4_1.moveTo((double)(var7_1.getX()),(double)(var7_1.getY()),(double)(var7_1.getZ()),0F,0F);
								var3.spawnedEntities.add(var4_1.getUUID());
								var3.setChanged();
							}else{
								return;
							}
						}else{
							return;
						}
					}
				}else{
					if ((var3.spawnedEntities.size()) > (0)){
						Iterator var4_2 = var3.spawnedEntities.iterator();
						do{
							if (var4_2.hasNext()){
								UUID var5_2 = ((UUID)(var4_2.next()));
								Entity var6_2 = ((ServerLevel)(var0)).getEntity(var5_2);
								if (((var6_2) != (null)) && (var6_2.isAlive())){
									var6_2.remove(Entity.RemovalReason.DISCARDED);
									continue;
								}else{
									continue;
								}
							}else{
								break;
							}
						} while (true);
						var3.spawnedEntities.clear();
						var3.setChanged();
					}else{
						return;
					}
				}
			}
			return;
		}else{
			return;
		}
	}
	public void saveAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.saveAdditional(var1,var2);
		var1.putInt("spawnLimit",this.spawnLimit);
		var1.putInt("playerDistance",this.playerDistance);
		var1.putString("entityToSpawn",EntityType.getKey(this.entityToSpawn).toString());
		ListTag var3 = new ListTag();
		Iterator var4 = this.spawnedEntities.iterator();
		do{
			if (var4.hasNext()){
				UUID var5 = ((UUID)(var4.next()));
				CompoundTag var6 = new CompoundTag();
				var6.putUUID("uuid",var5);
				var3.add(var6);
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("spawns",(Tag)(var3));
	}
	public void loadAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.loadAdditional(var1,var2);
		this.spawnLimit = var1.getInt("spawnLimit");
		this.playerDistance = var1.getInt("playerDistance");
		if ((this.playerDistance) <= (0)){
			this.playerDistance = 100;
		}
		this.entityToSpawn = (EntityType) (((EntityType)(EntityType.byString(var1.getString("entityToSpawn")).orElse(EntityType.PIG))));
		ListTag var3 = var1.getList("spawns",10);
		int var4 = 0;
		do{
			if ((var4) < (var3.size())){
				UUID var5 = var3.getCompound(var4).getUUID("uuid");
				this.spawnedEntities.add(var5);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public CompoundTag getUpdateTag(HolderLookup.Provider var1) {
		CompoundTag var2 = super.getUpdateTag(var1);
		this.saveAdditional(var2,var1);
		return var2;
	}
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create((BlockEntity)(this));
	}
}