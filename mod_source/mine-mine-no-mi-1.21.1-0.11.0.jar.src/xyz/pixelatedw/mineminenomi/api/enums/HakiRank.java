package xyz.pixelatedw.mineminenomi.api.enums;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;

public enum HakiRank {
	// Fields
	BEGINNER,
	INITIATE,
	ADEPT,
	PROFICIENT,
	MASTER;

	private HakiRank() {
	}
	public static boolean isAbove(HakiRank var0, HakiType var1, Player var2) {
		return (((java.lang.Enum)(HakiHelper.getHakiRank(var1,(LivingEntity)(var2)))).compareTo((Enum)(var0))) <= (0);
	}
	public static boolean isBelow(HakiRank var0, HakiType var1, Player var2) {
		return (((java.lang.Enum)(HakiHelper.getHakiRank(var1,(LivingEntity)(var2)))).compareTo((Enum)(var0))) > (0);
	}
}