package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;

@Mixin(value={StructureBlockEntity.class})
public class StructureBlockEntityMixin {
	@ModifyVariable(method={"loadStructure(Lnet/minecraft/server/level/ServerLevel;ZLnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;)Z"}, at=@At(value="STORE"), ordinal=1)
	public boolean mineminenomi$sizeDiffersCheck(boolean var1) {
		if (FGCommand.IGNORE_STRUCTURE_SIZE){
			return true;
		}else{
			return var1;
		}
	}
}