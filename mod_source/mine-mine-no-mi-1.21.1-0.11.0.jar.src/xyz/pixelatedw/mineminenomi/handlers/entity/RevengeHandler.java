package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeEntity;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.ITickRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;

public class RevengeHandler {
	public static void checkTicks(LivingEntity var0) {
		if (var0 instanceof IRevengeEntity){
			IRevengeEntity var1 = ((IRevengeEntity)(var0));
			if (((var0.level().getGameTime()) % (60L)) != (0L)){
				return;
			}else{
				Iterator var2 = var1.getRevengeMeters().iterator();
				LOOP_1:
				do{
					if (var2.hasNext()){
						RevengeMeter var3 = ((RevengeMeter)(var2.next()));
						Iterator var4 = var3.getChecks().iterator();
						do{
							if (var4.hasNext()){
								IRevengeCheck var5 = ((IRevengeCheck)(var4.next()));
								if (var5 instanceof ITickRevengeCheck){
									ITickRevengeCheck var6 = ((ITickRevengeCheck)(var5));
									if (var6.check(var0)){
										var3.addRevengeValueFrom(var6);
										continue;
									}else{
										continue;
									}
								}else{
									continue;
								}
							}else{
								continue LOOP_1;
							}
						} while (true);
					}else{
						break;
					}
				} while (true);
				return;
			}
		}else{
			return;
		}
	}
	public static void checkHits(LivingEntity var0, DamageSource var1) {
		if (var0 instanceof IRevengeEntity){
			IRevengeEntity var2 = ((IRevengeEntity)(var0));
			Iterator var3 = var2.getRevengeMeters().iterator();
			LOOP_1:
			do{
				if (var3.hasNext()){
					RevengeMeter var4 = ((RevengeMeter)(var3.next()));
					Iterator var5 = var4.getChecks().iterator();
					do{
						if (var5.hasNext()){
							IRevengeCheck var6 = ((IRevengeCheck)(var5.next()));
							if (var6 instanceof IHitRevengeCheck){
								IHitRevengeCheck var7 = ((IHitRevengeCheck)(var6));
								if (var7.check(var0,var1)){
									var4.addRevengeValueFrom(var7);
									continue;
								}else{
									continue;
								}
							}else{
								continue;
							}
						}else{
							continue LOOP_1;
						}
					} while (true);
				}else{
					break;
				}
			} while (true);
			return;
		}else{
			return;
		}
	}
}