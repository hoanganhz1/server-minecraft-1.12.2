package xyz.pixelatedw.mineminenomi.api.events.stats;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public class HakiExpEvent extends StatEvent {
	// Fields
	private float hakiExp;
	private HakiType hakiType;

	public HakiExpEvent(Player var1, float var2, HakiType var3, StatChangeSource var4) {
		super(var1,var4);
		this.hakiExp = var2;
		this.hakiType = var3;
	}
	public HakiType getHakiType() {
		return this.hakiType;
	}
	public float getHakiExp() {
		return this.hakiExp;
	}
	public void setHakiExp(float var1) {
		this.hakiExp = var1;
	}
}