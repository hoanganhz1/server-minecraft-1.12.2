package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.WaxCloneEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DoruDoruNoYakataAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 800F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DoruDoruNoYakataAbility>> INSTANCE;

	public DoruDoruNoYakataAbility(AbilityCore<DoruDoruNoYakataAbility> var1) {
		super((AbilityCore)(var1));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::oneUseEvent))));
	}
	private void oneUseEvent(LivingEntity var1, IAbility var2) {
		int var3 = 0;
		do{
			if ((var3) < (7)){
				int var4 = WyHelper.randomWithRange(-2,2);
				int var5 = WyHelper.randomWithRange(-2,2);
				WaxCloneEntity var6 = new WaxCloneEntity((Level)(var1.level()),(LivingEntity)(var1));
				var6.moveTo((var1.getX()) + ((double)(var4)),var1.getY(),(var1.getZ()) + ((double)(var5)),180F,0F);
				var6.setLastHurtByMob(var1);
				if (DoruHelper.hasColorPalette(var1)){
					var6.setUseOwnerTexture();
					var6.copyOwnerEquipment();
				}
				var1.level().addFreshEntity((Entity)(var6));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		this.cooldownComponent.startCooldown(var1,800F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user creates a few wax copies of themselves (Use the Color Palette for color on the clones)"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(800F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DoruDoruNoYakataAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("doru_doru_no_yakata","Doru Doru no Yakata",var0));
	}
}