package xyz.pixelatedw.mineminenomi.api;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;

public class WyDebug {
	// Fields
	public static final Marker ABILITY_LOG = MarkerManager.getMarker("ABILITY_LOG");

	public static boolean isDebug() {
		return Boolean.getBoolean("piwdebug");
	}
	public static void debug(Object var0) {
		debug(null,var0);
	}
	public static void debug(@Nullable() Marker var0, Object var1) {
		if (FGCommand.SHOW_DEBUG_IN_CHAT){
			Minecraft.getInstance().player.sendSystemMessage((Component)(Component.literal(String.valueOf(var1))));
			return;
		}else{
			if ((var0) == (null)){
				LogManager.getLogger(getCallerClassName()).debug(var1);
			}else{
				LogManager.getLogger(getCallerClassName()).debug(var0,var1);
			}
			return;
		}
	}
	public static void info(Object var0) {
		LogManager.getLogger(getCallerClassName()).info(var0);
	}
	public static void warn(Object var0) {
		LogManager.getLogger(getCallerClassName()).warn(var0);
	}
	public static void error(Object var0) {
		LogManager.getLogger(getCallerClassName()).error(var0);
	}
	public static String getCallerClassName() {
		StackTraceElement[] var0 = Thread.currentThread().getStackTrace();
		int var1 = 1;
		do{
			if ((var1) < (var0.length)){
				StackTraceElement var2 = var0[var1];
				if ((!(var2.getClassName().equals(WyDebug.class.getName()))) && ((var2.getClassName().indexOf("java.lang.Thread")) != (0))){
					String var3 = var2.getClassName();
					int var4 = var2.getLineNumber();
					String var5 = var3 + ":" + var4;
					return var5;
				}else{
					var1++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return null;
	}
	public static void printCurrentStackTrace() {
		try{
			throw new RuntimeException();
		}catch(Exception var0){
			var0.printStackTrace();
			return;
		}
	}
}