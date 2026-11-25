package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.IgnoreWaterloggingStructureProcessor;

@Mixin(value={StructureTemplate.class})
public class StructureTemplateMixin {
	@Inject(method={"placeInWorld"}, at={@At(value="HEAD")})
	public void mineminenomi$placeInWorld(ServerLevelAccessor var1, BlockPos var2, BlockPos var3, StructurePlaceSettings var4, RandomSource var5, int var6, CallbackInfoReturnable<Boolean> var7) {
		Iterator var8 = var4.getProcessors().iterator();
		do{
			if (var8.hasNext()){
				if (((StructureProcessor)(var8.next())) instanceof IgnoreWaterloggingStructureProcessor){
					var4.setLiquidSettings(LiquidSettings.IGNORE_WATERLOGGING);
					break;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
}