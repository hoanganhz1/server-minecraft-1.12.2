package xyz.pixelatedw.mineminenomi.morphs.hana;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.hana.VeinteFleurCalendulaAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class HanaCalendulaMorphInfo extends MorphInfo {
	@Nullable()
	public @Nullable() ResourceLocation getTexture(LivingEntity var1) {
		if (var1 instanceof AbstractClientPlayer){
			return ((AbstractClientPlayer)(var1)).getSkin().texture();
		}else{
			return null;
		}
	}
	public Component getDisplayName() {
		return ((AbilityCore)(VeinteFleurCalendulaAbility.INSTANCE.get())).getLocalizedName();
	}
	public boolean isPartial() {
		return true;
	}
}