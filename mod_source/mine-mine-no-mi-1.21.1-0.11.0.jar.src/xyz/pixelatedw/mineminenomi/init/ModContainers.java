package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.containers.WhiteWalkieStorageContainer;

public class ModContainers {
	// Fields
	public static final DeferredHolder<MenuType<?>, MenuType<WhiteWalkieStorageContainer>> WHITE_WALKIE_STORAGE = ModRegistry.registerContainer("White Walkie Storage",(IContainerFactory)(((net.neoforged.neoforge.network.IContainerFactory)(WhiteWalkieStorageContainer::new))));

	public static void init() {
	}
}