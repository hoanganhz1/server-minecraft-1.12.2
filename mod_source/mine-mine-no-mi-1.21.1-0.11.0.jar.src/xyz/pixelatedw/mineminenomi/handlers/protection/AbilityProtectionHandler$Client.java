package xyz.pixelatedw.mineminenomi.handlers.protection;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;

public class AbilityProtectionHandler$Client {
	// Fields
	public static final Map<String, ProtectedArea> CLIENT_AREAS = new HashMap();

	public static void renderAbilityProtections(PoseStack var0, Camera var1) {
		if ((Minecraft.getInstance().player) == (null)){
			return;
		}else{
			BufferBuilder var2 = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX_COLOR);
			Iterator var3 = CLIENT_AREAS.values().iterator();
			do{
				if (var3.hasNext()){
					ProtectedArea var4 = ((ProtectedArea)(var3.next()));
					RendererHelper.renderAbilityProtectionArea(var0,var1,var2,(double)(var4.getCenter().getX()),(double)(var4.getCenter().getY()),(double)(var4.getCenter().getZ()),(float)(var4.getSize()),(float)(var4.getSize()),(float)(var4.getSize()),var4.isDisabled());
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}