package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HonkAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 10F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HonkAbility>> INSTANCE;

	public HonkAbility(AbilityCore<HonkAbility> var1) {
		super((AbilityCore)(var1));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.HONK.get())),SoundSource.PLAYERS,1F,(0.9F) + ((this.random.nextFloat()) / (4F)));
		this.cooldownComponent.startCooldown(var1,10F);
	}
	private static boolean canUnlock(LivingEntity var0) {
		ItemStack var1 = var0.getItemBySlot(EquipmentSlot.HEAD);
		if ((!(var1.isEmpty())) && (((java.lang.Object)(var1.getItem())).equals(ModArmors.BIG_RED_NOSE.get()))){
			return true;
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("§8§ohonk honk§r"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HonkAbility::new)))).addDescriptionLine(lv1_3).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(HonkAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("honk","Honk",var0));
	}
}