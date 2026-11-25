package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.server.commands.FillCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={FillCommand.class})
public class FillCommandMixin {
	@ModifyVariable(method={"fillBlocks"}, at=@At(value="STORE"), ordinal=0)
	private static int mineminenomi$areaSize(int var0) {
		return 0;
	}
}