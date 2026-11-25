package xyz.pixelatedw.mineminenomi.api.chat;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

@Deprecated()
public interface WyComponent extends Component {
	@Deprecated()
	public static MutableComponent reference(Holder<?> var0) {
		return MutableComponent.create((ComponentContents)(new ReferenceContents(var0.getKey().registry(),var0.getKey().location())));
	}
}