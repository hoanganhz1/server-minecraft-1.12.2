package xyz.pixelatedw.mineminenomi.api.crew;

import java.io.Serializable;
import net.minecraft.world.entity.player.Player;

public interface JollyRogerElement$ICanUse extends Serializable {
	public abstract boolean canUse(Player var0, Crew var1);
}