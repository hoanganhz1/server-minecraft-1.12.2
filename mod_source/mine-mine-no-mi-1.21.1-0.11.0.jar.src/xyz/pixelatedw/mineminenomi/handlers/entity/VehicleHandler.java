package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;

public class VehicleHandler {
	public static double getStrikerCameraZoom(Player var0) {
		if (((var0.getVehicle()) != (null)) && (var0.getVehicle() instanceof StrikerEntity)){
			return 7D;
		}else{
			return 0D;
		}
	}
}