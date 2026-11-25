package xyz.pixelatedw.mineminenomi.api.events.onefruit;

import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

public class DroppedDevilFruitEvent extends EntityEvent {
	// Fields
	private final Item devilFruit;
	private final String reason;
	private final BlockPos location;
	private final ResourceLocation dimensionid;

	public DroppedDevilFruitEvent(@Nullable() LivingEntity var1, @Nonnull() Item var2, @Nullable() ResourceLocation var3, @Nullable() BlockPos var4, String var5) {
		super((Entity)(var1));
		this.devilFruit = var2;
		this.location = var4;
		this.dimensionid = var3;
		this.reason = var5;
	}
	public Item getItem() {
		return this.devilFruit;
	}
	public BlockPos getLocation() {
		return this.location;
	}
	public Level getDimension() {
		if ((this.dimensionid) == (null)){
			return null;
		}else{
			return ServerLifecycleHooks.getCurrentServer().getLevel(ResourceKey.create(Registries.DIMENSION,this.dimensionid));
		}
	}
	public String getReason() {
		return this.reason;
	}
}