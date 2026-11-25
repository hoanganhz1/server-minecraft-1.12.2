package xyz.pixelatedw.mineminenomi.containers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.init.ModContainers;

public class WhiteWalkieStorageContainer extends AbstractContainerMenu {
	// Fields
	private Player player;
	private Container storage;
	 WhiteWalkieEntity whiteWalkie;

	public WhiteWalkieStorageContainer(int var1, Inventory var2, FriendlyByteBuf var3) {
		super(((MenuType)(ModContainers.WHITE_WALKIE_STORAGE.get())),var1);
		this.player = var2.player;
		CompoundTag var4 = var3.readNbt();
		int var5 = var4.getInt("entity");
		this.whiteWalkie = ((WhiteWalkieEntity)(this.player.level().getEntity(var5)));
		int var6 = var4.getInt("inventorySize");
		int var7 = var4.getInt("pageSize");
		this.storage = new SimpleContainer(var6);
		this.addSlot((Slot)(new WhiteWalkieStorageContainer$1(this,this.storage,0,8,18)));
		if (this.whiteWalkie.hasChest()){
			int var8 = 0;
			LOOP_1:
			do{
				if ((var8) < (3)){
					int var9 = 0;
					do{
						if ((var9) < (this.whiteWalkie.getInventoryColumns())){
							this.addSlot(new Slot(this.storage,(3) + (((var9) + ((var8) * (this.whiteWalkie.getInventoryColumns()))) + ((this.whiteWalkie.getInventoryPage()) * (var7))),(80) + ((var9) * (18)),(18) + ((var8) * (18))));
							var9++;
							continue;
						}else{
							break;
						}
					} while (true);
					var8++;
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
		}
		int var8 = 0;
		LOOP_2:
		do{
			if ((var8) < (3)){
				int var9 = 0;
				do{
					if ((var9) < (9)){
						this.addSlot(new Slot((Container)(var2),((var9) + ((var8) * (9))) + (9),(8) + ((var9) * (18)),((102) + ((var8) * (18))) + (-18)));
						var9++;
						continue;
					}else{
						break;
					}
				} while (true);
				var8++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		var8 = 0;
		do{
			if ((var8) < (9)){
				this.addSlot(new Slot((Container)(var2),var8,(8) + ((var8) * (18)),142));
				var8++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public WhiteWalkieStorageContainer(int var1, Inventory var2, SimpleContainer var3, WhiteWalkieEntity var4) {
		super(((MenuType)(ModContainers.WHITE_WALKIE_STORAGE.get())),var1);
		this.storage = var3;
		this.whiteWalkie = var4;
		var3.startOpen(var2.player);
		int var5 = this.whiteWalkie.getInventoryPageSize();
		this.addSlot((Slot)(new WhiteWalkieStorageContainer$2(this,this.storage,0,8,18,var4)));
		if (this.whiteWalkie.hasChest()){
			int var6 = 0;
			LOOP_1:
			do{
				if ((var6) < (3)){
					int var7 = 0;
					do{
						if ((var7) < (this.whiteWalkie.getInventoryColumns())){
							this.addSlot(new Slot(this.storage,(3) + (((var7) + ((var6) * (this.whiteWalkie.getInventoryColumns()))) + ((this.whiteWalkie.getInventoryPage()) * (var5))),(80) + ((var7) * (18)),(18) + ((var6) * (18))));
							var7++;
							continue;
						}else{
							break;
						}
					} while (true);
					var6++;
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
		}
		int var6 = 0;
		LOOP_2:
		do{
			if ((var6) < (3)){
				int var7 = 0;
				do{
					if ((var7) < (9)){
						this.addSlot(new Slot((Container)(var2),((var7) + ((var6) * (9))) + (9),(8) + ((var7) * (18)),((102) + ((var6) * (18))) + (-18)));
						var7++;
						continue;
					}else{
						break;
					}
				} while (true);
				var6++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		var6 = 0;
		do{
			if ((var6) < (9)){
				this.addSlot(new Slot((Container)(var2),var6,(8) + ((var6) * (18)),142));
				var6++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public boolean stillValid(Player var1) {
		return (this.storage.stillValid(var1)) && ((this.whiteWalkie.isAlive()) && ((this.whiteWalkie.distanceTo((Entity)(var1))) < (8F)));
	}
	public ItemStack quickMoveStack(Player var1, int var2) {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = ((Slot)(this.slots.get(var2)));
		if (((var4) != (null)) && (var4.hasItem())){
			ItemStack var5 = var4.getItem();
			var3 = var5.copy();
			int var6 = this.storage.getContainerSize();
			if ((var2) < (var6)){
				if (!(this.moveItemStackTo(var5,var6,this.slots.size(),true))){
					return ItemStack.EMPTY;
				}
			}else{
				if ((this.getSlot(1).mayPlace(var5)) && (!(this.getSlot(1).hasItem()))){
					if (!(this.moveItemStackTo(var5,1,2,false))){
						return ItemStack.EMPTY;
					}
				}else{
					if (this.getSlot(0).mayPlace(var5)){
						if (!(this.moveItemStackTo(var5,0,1,false))){
							return ItemStack.EMPTY;
						}
					}else{
						if (((var6) > (2)) && (this.moveItemStackTo(var5,2,var6,false))){
							return null;

						}else{
							int var7 = (var6) + (27);
							int var8 = (var7) + (9);
							if (((var2) >= (var7)) && ((var2) < (var8))){
								if (!(this.moveItemStackTo(var5,var6,var7,false))){
									return ItemStack.EMPTY;
								}
							}else{
								if (((var2) >= (var6)) && ((var2) < (var7))){
									if (!(this.moveItemStackTo(var5,var7,var8,false))){
										return ItemStack.EMPTY;
									}
								}else{
									if (!(this.moveItemStackTo(var5,var7,var7,false))){
										return ItemStack.EMPTY;
									}
								}
							}
							return ItemStack.EMPTY;
						}
					}
				}
			}
			if (var5.isEmpty()){
				var4.set(ItemStack.EMPTY);
			}else{
				var4.setChanged();
			}
		}
		return var3;
	}
	public void removed(Player var1) {
		super.removed(var1);
		this.storage.stopOpen(var1);
	}
	public WhiteWalkieEntity getWhiteWalkie() {
		return this.whiteWalkie;
	}
}