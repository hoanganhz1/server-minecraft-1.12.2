package xyz.pixelatedw.mineminenomi.mixins.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.handlers.ui.CombatModeHandler;

@Mixin(value={Gui.class})
public class GuiMixin {
	@ModifyExpressionValue(method={"renderExperienceLevel"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;guiHeight()I")})
	private int mineminenomi$experienceLevelHeight(int var1) {
		return (var1) - (this.getOffset());
	}
	@ModifyExpressionValue(method={"renderExperienceBar"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;guiHeight()I")})
	private int mineminenomi$experienceBarHeight(int var1) {
		return (var1) - (this.getOffset());
	}
	@ModifyExpressionValue(method={"renderJumpMeter"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphics;guiHeight()I")})
	private int mineminenomi$jumpMeterHeight(int var1) {
		return (var1) - (this.getOffset());
	}
	private int getOffset() {
		if (((Boolean)(EntityStatsData.get((LivingEntity)(Minecraft.getInstance().player)).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isInCombatMode)))).orElse(Boolean.valueOf(false)))).booleanValue()){
			return ((Mth.clamp(ClientConfig.getAbilityBarsOnScreen(),1,CombatModeHandler.abilityBars)) - (1)) * (23);
		}else{
			return 0;
		}
	}
}