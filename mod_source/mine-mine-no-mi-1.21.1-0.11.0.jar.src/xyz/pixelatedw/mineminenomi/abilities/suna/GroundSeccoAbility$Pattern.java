package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public enum GroundSeccoAbility$Pattern {
	// Fields
	CIRCLE((GroundSeccoAbility$IPatternFunction)(((xyz.pixelatedw.mineminenomi.abilities.suna.GroundSeccoAbility$IPatternFunction)(GroundSeccoAbility::setupCirclePattern)))),
	CROSS((GroundSeccoAbility$IPatternFunction)(((xyz.pixelatedw.mineminenomi.abilities.suna.GroundSeccoAbility$IPatternFunction)(GroundSeccoAbility::setupCrossPattern)))),
	CORNERS((GroundSeccoAbility$IPatternFunction)(((xyz.pixelatedw.mineminenomi.abilities.suna.GroundSeccoAbility$IPatternFunction)(GroundSeccoAbility::setupCornersPattern)))),
	CENTER((GroundSeccoAbility$IPatternFunction)(((xyz.pixelatedw.mineminenomi.abilities.suna.GroundSeccoAbility$IPatternFunction)(GroundSeccoAbility::setupCenterPattern))));
	private GroundSeccoAbility$IPatternFunction func;

	private GroundSeccoAbility$Pattern(GroundSeccoAbility$IPatternFunction var3) {
		this.func = var3;
	}
	public void create(GroundSeccoAbility var1, Level var2, BlockPos var3) {
		this.func.create(var1,var2,var3);
	}
}