package xyz.pixelatedw.mineminenomi.commands;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContext;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class Requires {
	public static Predicate<CommandSourceStack> hasPermission(PermissionNode<Boolean> var0) {
		PermissionNode<Boolean> var1 = var0;
		final PermissionNode<Boolean> var1_f1 = var1;
		Predicate<CommandSourceStack> var2 = (l0) -> {
			if ((var1_f1) == (null)){
				return false;
			}else{
				CommandSource lv1_2 = l0.source;
				if (lv1_2 instanceof ServerPlayer){
					ServerPlayer lv1_3 = ((ServerPlayer)(lv1_2));
					if (!(ServerConfig.hasPermissionsEnabled())){
						return ((Boolean)(var1_f1.getDefaultResolver().resolve(lv1_3,lv1_3.getUUID(),new PermissionDynamicContext[0]))).booleanValue();
					}else{
						return ((Boolean)(PermissionAPI.getPermission((ServerPlayer)(lv1_3),(PermissionNode)(var1_f1),(PermissionDynamicContext[])(new PermissionDynamicContext[0])))).booleanValue();
					}
				}else{
					if (l0.hasPermission(2)){
						return true;
					}else{
						return false;
					}
				}
			}
		};
		return var2;
	}
	public static Predicate<CommandSourceStack> hasEitherPermission(PermissionNode<Boolean>... var0) {
		PermissionNode<Boolean>[] var1 = var0;
		final PermissionNode<Boolean>[] var1_f1 = var1;
		Predicate<CommandSourceStack> var2 = (l0) -> {
			if ((var1_f1) == (null)){
				return false;
			}else{
				CommandSource lv2_2 = l0.source;
				if (lv2_2 instanceof ServerPlayer){
					int lv2_5 = 0;
					int lv2_6 = 0;
					ServerPlayer lv2_3 = ((ServerPlayer)(lv2_2));
					if (!(ServerConfig.hasPermissionsEnabled())){
						PermissionNode[] lv2_4 = var1_f1;
						lv2_5 = lv2_4.length;
						lv2_6 = 0;
						do{
							if ((lv2_6) < (lv2_5)){
								PermissionNode lv2_7 = lv2_4[lv2_6];
								if (((Boolean)(lv2_7.getDefaultResolver().resolve(lv2_3,lv2_3.getUUID(),new PermissionDynamicContext[0]))).booleanValue()){
									return true;
								}else{
									lv2_6++;
									continue;
								}
							}else{
								break;
							}
						} while (true);
						return false;
					}else{
						PermissionNode[] lv2_4_1 = var1_f1;
						lv2_5 = lv2_4_1.length;
						lv2_6 = 0;
						do{
							if ((lv2_6) < (lv2_5)){
								PermissionNode lv2_7_1 = lv2_4_1[lv2_6];
								if ((((Boolean)(lv2_7_1.getDefaultResolver().resolve(lv2_3,lv2_3.getUUID(),new PermissionDynamicContext[0]))).booleanValue()) && (((Boolean)(PermissionAPI.getPermission(lv2_3,lv2_7_1,new PermissionDynamicContext[0]))).booleanValue())){
									return true;
								}else{
									lv2_6++;
									continue;
								}
							}else{
								break;
							}
						} while (true);
						return false;
					}
				}else{
					if (l0.hasPermission(2)){
						return true;
					}else{
						return false;
					}
				}
			}
		};
		return var2;
	}
}