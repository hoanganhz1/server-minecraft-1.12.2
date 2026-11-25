package xyz.pixelatedw.mineminenomi.api.commands;

import com.google.gson.JsonObject;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

public class CurrencyTypeArgument$Info<T extends Enum<T>> implements ArgumentTypeInfo<CurrencyTypeArgument<T>, CurrencyTypeArgument$Info$Template<T>> {
	public void serializeToNetwork(CurrencyTypeArgument$Info$Template<T> var1, FriendlyByteBuf var2) {
		var2.writeUtf(var1.enumClass.getName());
	}
	public CurrencyTypeArgument$Info$Template<T> deserializeFromNetwork(FriendlyByteBuf var1) {
		try{
			String var2 = var1.readUtf();
			return new CurrencyTypeArgument$Info$Template(this,(Class)(Class.forName(var2)));
		}catch(ClassNotFoundException var2_1){
			return (CurrencyTypeArgument$Info$Template<T>) (CurrencyTypeArgument$Info$Template) (null);
		}
	}
	public void serializeToJson(CurrencyTypeArgument$Info$Template<T> var1, JsonObject var2) {
		var2.addProperty("enum",var1.enumClass.getName());
	}
	public CurrencyTypeArgument$Info$Template<T> unpack(CurrencyTypeArgument<T> var1) {
		return new CurrencyTypeArgument$Info$Template(this,var1.enumClass);
	}
}