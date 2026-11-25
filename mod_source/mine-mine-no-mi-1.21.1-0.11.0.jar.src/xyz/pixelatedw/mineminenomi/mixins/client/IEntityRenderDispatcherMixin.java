package xyz.pixelatedw.mineminenomi.mixins.client;

import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={EntityRenderDispatcher.class})
public interface IEntityRenderDispatcherMixin {
	@Accessor(value="playerRenderers")
	public abstract Map<PlayerSkin.Model, EntityRenderer<? extends Player>> getPlayerRenderers();
}