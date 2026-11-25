package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class ItemSpawnComponent extends AbilityComponent<IAbility> {
	// Fields
	private List<ItemStack> trackedStacks;
	private boolean isActive;
	private final PriorityEventPool<ItemSpawnComponent$IMissingItemEvent> missingItemEvents;

	public ItemSpawnComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.ITEM_SPAWN.get())),var1);
		this.trackedStacks = new ArrayList();
		this.missingItemEvents = (PriorityEventPool) (new PriorityEventPool());
		this.setTickRate(5);
	}
	public ItemSpawnComponent addMissingItemEvent(int var1, ItemSpawnComponent$IMissingItemEvent var2) {
		this.missingItemEvents.addEvent(var1,var2);
		return this;
	}
	public void doTick(LivingEntity var1) {
		LivingEntity var4 = null;
		if ((!(var1.level().isClientSide)) && ((this.isActive) && (((this.missingItemEvents.countEventsInPool()) > (0L)) && (!(this.trackedStacks.isEmpty()))))){
			Iterator var2 = this.trackedStacks.iterator();
			do{
				if (var2.hasNext()){
					if (((ItemStack)(var2.next())).isEmpty()){
						ItemSpawnComponent var3 = this;
						var4 = var1;
						final LivingEntity var4_f1 = var4;
						Consumer<ItemSpawnComponent$IMissingItemEvent> var5 = (l0) -> {
			l0.missingItem(var4_f1,this.getAbility());
		};
						((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.missingItemEvents)).dispatch((Consumer)(var5));
						return;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	private List<ItemStack> getTrackedItems() {
		return this.trackedStacks;
	}
	public void spawnItem(LivingEntity var1, ItemStack var2) {
		this.spawnItem(var1,var2,EquipmentSlot.MAINHAND,false);
	}
	public void spawnItem(LivingEntity var1, ItemStack var2, @Nullable() EquipmentSlot var3, boolean var4) {
		this.ensureIsRegistered();
		this.isActive = true;
		if (var1.getItemBySlot(var3).isEmpty()){
			var1.setItemSlot(var3,var2);
			this.trackedStacks.add(var2);
			return;
		}else{
			if (var1 instanceof Player){
				((Player)(var1)).addItem(var2);
				this.trackedStacks.add(var2);
			}
			return;
		}
	}
	public void despawnItems(LivingEntity var1) {
		this.isActive = false;
		if ((this.trackedStacks.size()) > (0)){
			Iterator var2 = this.trackedStacks.iterator();
			do{
				if (var2.hasNext()){
					ItemsHelper.removeItemStackFromInventory(var1,((ItemStack)(var2.next())));
					continue;
				}else{
					break;
				}
			} while (true);
		}
		this.trackedStacks.clear();
	}
	public boolean isActive() {
		return this.isActive;
	}
}