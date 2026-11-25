package xyz.pixelatedw.mineminenomi.api.commands;

import com.google.gson.JsonObject;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

public class HakiTypeArgument$Info<T extends Enum<T>> implements ArgumentTypeInfo<HakiTypeArgument<T>, HakiTypeArgument$Info$Template<T>> {
	public void serializeToNetwork(HakiTypeArgument$Info$Template<T> var1, FriendlyByteBuf var2) {
		var2.writeUtf(var1.enumClass.getName());
	}
	public HakiTypeArgument$Info$Template<T> deserializeFromNetwork(FriendlyByteBuf var1) {
		try{
			String var2 = var1.readUtf();
			return new HakiTypeArgument$Info$Template(this,(Class)(Class.forName(var2)));
		}catch(ClassNotFoundException var2_1){
			return (HakiTypeArgument$Info$Template<T>) (HakiTypeArgument$Info$Template) (null);
		}
	}
	public void serializeToJson(HakiTypeArgument$Info$Template<T> var1, JsonObject var2) {
		var2.addProperty("enum",var1.enumClass.getName());
	}
	public HakiTypeArgument$Info$Template<T> unpack(HakiTypeArgument<T> var1) {
		return new HakiTypeArgument$Info$Template(this,var1.enumClass);
	}
}