package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.BuildMode;

public class PatreonHandler {
	// Fields
	private static final Component NO_ACCESS_MESSAGE = Component.literal("§c§lWARNING!§r \n\nYou don't have access to this version yet!");
	private static final Component PUBLIC_TESTING_ENDED_MESSAGE = Component.literal("§c§lWARNING!§r \n\nPublic testing access for this build has ended, please download the actual release!");
	private static final HashMap<UUID, List<PatreonHandler$AccountType>> PATREONS = new HashMap();

	public static boolean isDevBuild() {
		return (BuildMode.MODE) == (BuildMode.DEV);
	}
	public static boolean isEarlyAccessBuild() {
		return (BuildMode.MODE) == (BuildMode.EARLY_ACCESS);
	}
	public static boolean isPublicQABuild() {
		return (BuildMode.MODE) == (BuildMode.PUBLIC_QA);
	}
	public static boolean isReleaseBuild() {
		return (BuildMode.MODE) == (BuildMode.RELEASE);
	}
	public static boolean isQABuild() {
		return (BuildMode.MODE) == (BuildMode.QA);
	}
	public static boolean isPromoBuild() {
		return (BuildMode.MODE) == (BuildMode.PROMO);
	}
	@Nullable()
	private static @Nullable() List<PatreonHandler$AccountType> getPatreonLevel(Player var0) throws IOException {
		String var1 = var0.getUUID().toString();
		String var2 = "/patreon/" + var1;
		String[] var3 = ((String[])(WyHelper.sendGET(var2,String[].class)));
		if (((var3) != (null)) && ((var3.length) > (0))){
			ArrayList var4 = new ArrayList();
			String[] var5 = var3;
			int var6 = var5.length;
			int var7 = 0;
			do{
				if ((var7) < (var6)){
					String var8 = WyHelper.getResourceName(var5[var7]);
					if (var8.equalsIgnoreCase("patreon_rookie")){
						var4.add(PatreonHandler$AccountType.ROOKIE);
					}
					if (var8.equalsIgnoreCase("patreon_supernova")){
						var4.add(PatreonHandler$AccountType.SUPERNOVA);
					}
					if (var8.equalsIgnoreCase("patreon_celestial_dragon")){
						var4.add(PatreonHandler$AccountType.CELESTIAL_DRAGON);
					}
					if (var8.equalsIgnoreCase("mine_mine_no_mi_qateam")){
						var4.add(PatreonHandler$AccountType.TESTER);
					}
					var7++;
					continue;
				}else{
					break;
				}
			} while (true);
			return var4;
		}else{
			return (List<PatreonHandler$AccountType>) (List) (null);
		}
	}
	public static boolean isCelestialDragon(Player var0) {
		if (!(PATREONS.containsKey(var0.getUUID()))){
			return false;
		}else{
			List var1 = ((List)(PATREONS.get(var0.getUUID())));
			return ((var1) != (null)) && (var1.contains(PatreonHandler$AccountType.CELESTIAL_DRAGON));
		}
	}
	public static boolean isSupernova(Player var0) {
		if (!(PATREONS.containsKey(var0.getUUID()))){
			return false;
		}else{
			List var1 = ((List)(PATREONS.get(var0.getUUID())));
			return (((((var1) != (null)) && (var1.contains(PatreonHandler$AccountType.SUPERNOVA))) ? (1) : (0)) != (0)) || (isCelestialDragon(var0));
		}
	}
	public static boolean isTester(Player var0) {
		if (!(PATREONS.containsKey(var0.getUUID()))){
			return false;
		}else{
			List var1 = ((List)(PATREONS.get(var0.getUUID())));
			return ((var1) != (null)) && (var1.contains(PatreonHandler$AccountType.TESTER));
		}
	}
	public static boolean isRookie(Player var0) {
		if (!(PATREONS.containsKey(var0.getUUID()))){
			return false;
		}else{
			List var1 = ((List)(PATREONS.get(var0.getUUID())));
			return (((((var1) != (null)) && (var1.contains(PatreonHandler$AccountType.ROOKIE))) ? (1) : (0)) != (0)) || ((isSupernova(var0)) || (isCelestialDragon(var0)));
		}
	}
	public static boolean hasPatreonAccess(Player var0) {
		if ((isDevBuild()) && (isCelestialDragon(var0))){
			return true;
		}else{
			if ((isQABuild()) && (isTester(var0))){
				return true;
			}else{
				return (isEarlyAccessBuild()) && (isSupernova(var0));
			}
		}
	}
	public static boolean isPublicQAPeriod() {
		Calendar var0 = Calendar.getInstance();
		int var1 = (var0.get(2)) + (1);
		int var2 = var0.get(5);
		return (var1) == (3);
	}
	public static boolean handlePatreonAccess(ServerPlayer var0) {
		if (isPublicQABuild()){
			if (!(isPublicQAPeriod())){
				var0.connection.disconnect(PUBLIC_TESTING_ENDED_MESSAGE);
				return false;
			}else{
				return true;
			}
		}else{
			if ((!(isReleaseBuild())) && (!((isDevBuild()) && (WyDebug.isDebug())))){
				if ((isPromoBuild()) && (var0.getServer().isDedicatedServer())){
					return true;
				}else{
					if (PATREONS.containsKey(var0.getUUID())){
						List var1 = ((List)(PATREONS.get(var0.getUUID())));
						if (((var1) != (null)) && (!(var1.isEmpty()))){
							if (hasPatreonAccess((Player)(var0))){
								return true;
							}
						}else{
							var0.connection.disconnect(NO_ACCESS_MESSAGE);
							return false;
						}
					}
					List var1_1 = null;
					try{
						var1_1 = getPatreonLevel((Player)(var0));
					}catch(IOException var2){
						var2.printStackTrace();
						var0.connection.disconnect(NO_ACCESS_MESSAGE);
						return false;
					}
					PATREONS.put(var0.getUUID(),var1_1);
					if (((var1_1) != (null)) && ((!(var1_1.isEmpty())) && (hasPatreonAccess((Player)(var0))))){
						return true;
					}else{
						var0.connection.disconnect(NO_ACCESS_MESSAGE);
						return false;
					}
				}
			}else{
				return true;
			}
		}
	}
}