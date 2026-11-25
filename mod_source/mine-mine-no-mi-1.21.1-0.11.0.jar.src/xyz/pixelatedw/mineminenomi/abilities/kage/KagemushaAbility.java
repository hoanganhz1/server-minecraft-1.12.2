package xyz.pixelatedw.mineminenomi.abilities.kage;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KagemushaAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 100F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KagemushaAbility>> INSTANCE;

	public KagemushaAbility(AbilityCore<KagemushaAbility> var1) {
		super((AbilityCore)(var1));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to change its position with that of the Doppelman."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KagemushaAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kagemusha","Kagemusha",var0));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		Function<AbilityData, DoppelmanAbility> var3 = (l0) -> {
			return ((DoppelmanAbility)(l0.getEquippedAbility(((AbilityCore)(DoppelmanAbility.INSTANCE.get())))));
		};
		DoppelmanAbility var4 = ((DoppelmanAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).orElse(null)));
		if (((var4) != (null)) && ((var4.isContinuous()) && (((var4.getDoppelman()) != (null)) && (var4.getDoppelman().isAlive())))){
			DoppelmanEntity var5 = var4.getDoppelman();
			BlockPos var6 = var1.blockPosition();
			var1.stopRiding();
			var1.teleportTo(var5.getX(),var5.getY(),var5.getZ());
			var5.stopRiding();
			var5.teleportTo((double)(var6.getX()),(double)(var6.getY()),(double)(var6.getZ()));
		}
		this.cooldownComponent.startCooldown(var1,100F);
	}
}