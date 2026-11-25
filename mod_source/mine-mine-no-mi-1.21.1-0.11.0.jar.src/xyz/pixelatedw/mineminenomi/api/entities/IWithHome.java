package xyz.pixelatedw.mineminenomi.api.entities;

import java.util.Optional;
import net.minecraft.world.phys.Vec3;

public interface IWithHome {
	public abstract Optional<Vec3> getHomePosition();
}