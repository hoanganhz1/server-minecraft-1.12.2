package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Iterator;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class StealPunchAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 160F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<StealPunchAbility>> INSTANCE;

	public StealPunchAbility(AbilityCore<StealPunchAbility> var1) {
		super((AbilityCore)(var1));
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		ItemStack var4 = ItemStack.EMPTY;
		if (!(var2.getMainHandItem().isEmpty())){
			var4 = var2.getMainHandItem();
		}else{
			if (!(var2.getOffhandItem().isEmpty())){
				var4 = var2.getOffhandItem();
			}else{
				if ((var2.getArmorCoverPercentage()) > (0F)){
					Iterator var5 = var2.getArmorSlots().iterator();
					do{
						if (var5.hasNext()){
							ItemStack var6 = ((ItemStack)(var5.next()));
							if (((var6) != (null)) && ((!(var6.isEmpty())) && ((var2.getRandom().nextDouble()) < (0.35D)))){
								var4 = var6;
								break;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
			}
		}
		if (((var4) != (null)) && (!(var4.isEmpty()))){
			Vec3 var5_1 = var2.getLookAngle();
			var1.setPos((var2.getX()) + (var5_1.x),var2.getY(),(var2.getZ()) + (var5_1.z));
			double var6_1 = (var2.getEyeY()) - (0.30000001192092896D);
			ItemEntity var7 = new ItemEntity(var2.level(),var2.getX(),var6_1,var2.getZ(),var4.copy());
			var7.setPickUpDelay(40);
			var7.setThrower((Entity)(var2));
			float var8 = (var2.getRandom().nextFloat()) * (1.5F);
			float var9 = (var2.getRandom().nextFloat()) * (6.2831855F);
			AbilityHelper.setDeltaMovement((Entity)(var7),(double)((-(Mth.sin(var9))) * (var8)),0.75D,(double)((Mth.cos(var9)) * (var8)));
			var2.level().addFreshEntity((Entity)(var7));
			var4.shrink(var4.getCount());
		}
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public boolean isParallel() {
		return true;
	}
	public float getPunchCooldown() {
		return 160F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Hits the enemy and throws their held item away from them."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(StealPunchAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("steal_punch","Steal Punch",var0));
	}
}