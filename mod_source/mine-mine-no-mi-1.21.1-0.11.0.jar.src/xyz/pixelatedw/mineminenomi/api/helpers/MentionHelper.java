package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.chat.WyComponent;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class MentionHelper {
	// Fields
	public static final Style MENTION_STYLE = Style.EMPTY.withColor(ChatFormatting.GREEN);

	@Nullable()
	public static @Nullable() MutableComponent tryMention(Object var0) {
		if (var0 instanceof DeferredHolder){
			return WyComponent.reference((Holder)(((DeferredHolder)(var0))));
		}else{
			return parseAndMention(var0);
		}
	}
	@Nullable()
	public static @Nullable() MutableComponent parseAndMention(Object var0) {
		if (var0 instanceof DeferredHolder){
			var0 = ((DeferredHolder)(var0)).get();
		}
		try{
			Integer var3 = null;
			Objects.requireNonNull(var0);
			Object var1 = var0;
			int var2 = 0;
			switch (((var1) == null ? -1 : ((var2) <= 0 && (var1) instanceof AbilityCore) ? 0 : ((var2) <= 1 && (var1) instanceof Item) ? 1 : ((var2) <= 2 && (var1) instanceof MobEffect) ? 2 : ((var2) <= 3 && (var1) instanceof MorphInfo) ? 3 : ((var2) <= 4 && (var1) instanceof String) ? 4 : ((var2) <= 5 && (var1) instanceof Component) ? 5 : ((var2) <= 6 && (var1) instanceof Integer) ? 6 : -1)){
			case 0:
				AbilityCore var4 = ((AbilityCore)(var1));
				break;
			case 1:
				Item var5 = ((Item)(var1));
				break;
			case 2:
				MobEffect var6 = ((MobEffect)(var1));
				break;
			case 3:
				MorphInfo var7 = ((MorphInfo)(var1));
				break;
			case 4:
				String var8 = ((String)(var1));
				break;
			case 5:
				Component var9 = ((Component)(var1));
				break;
			case 6:
				var3 = ((Integer)(var1));
				break;
			default:
				String var10 = String.valueOf(var0);
				String var11 = "Unexpected value: " + var10;
				throw new IllegalArgumentException((String)(var11));
			}
			return mentionText(var3);
		}catch(Exception var1_1){
			var1_1.printStackTrace();
			return null;
		}
	}
	public static MutableComponent mentionAbility(AbilityCore<?> var0) {
		return var0.getLocalizedName().copy().setStyle(MENTION_STYLE);
	}
	public static MutableComponent mentionMorph(MorphInfo var0) {
		return var0.getDisplayName().copy().setStyle(MENTION_STYLE);
	}
	public static MutableComponent mentionItem(Item var0) {
		return Component.translatable(var0.getDescriptionId()).setStyle(MENTION_STYLE);
	}
	public static MutableComponent mentionEffect(MobEffect var0) {
		return var0.getDisplayName().copy().setStyle(MENTION_STYLE);
	}
	public static MutableComponent mentionText(Object var0) {
		if (var0 instanceof Component){
			return ((Component)(var0)).copy().setStyle(MENTION_STYLE);
		}else{
			return Component.literal(var0.toString()).setStyle(MENTION_STYLE);
		}
	}
}