package xyz.pixelatedw.mineminenomi.api.events.onefruit;

import javax.annotation.Nonnull;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.EntityEvent;
import org.jetbrains.annotations.Nullable;

public class InventoryDevilFruitEvent extends EntityEvent {
	// Fields
	private final Item devilFruit;
	private final String reason;

	public InventoryDevilFruitEvent(@Nullable() LivingEntity var1, @Nonnull() Item var2, String var3) {
		super((Entity)(var1));
		this.devilFruit = var2;
		this.reason = var3;
	}
	public Item getItem() {
		return this.devilFruit;
	}
	public String getReason() {
		return this.reason;
	}
}