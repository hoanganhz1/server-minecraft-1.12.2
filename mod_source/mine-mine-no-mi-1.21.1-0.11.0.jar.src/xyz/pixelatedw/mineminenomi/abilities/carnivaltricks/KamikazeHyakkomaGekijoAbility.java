package xyz.pixelatedw.mineminenomi.abilities.carnivaltricks;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.RepeaterAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.carnivaltricks.TopEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KamikazeHyakkomaGekijoAbility extends RepeaterAbility {
	// Fields
	private static final int COOLDOWN = 100;
	private static final int TRIGGERS = 10;
	private static final int INTERVAL = 2;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KamikazeHyakkomaGekijoAbility>> INSTANCE;

	public KamikazeHyakkomaGekijoAbility(AbilityCore<KamikazeHyakkomaGekijoAbility> var1) {
		super((AbilityCore)(var1));
	}
	public int getMaxTriggers() {
		return 10;
	}
	public int getTriggerInterval() {
		return 2;
	}
	public float getRepeaterCooldown() {
		return 100F;
	}
	public TopEntity getProjectileFactory(LivingEntity var1) {
		return new TopEntity(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user throws several tops at their opponent, some of them exploding on impact."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KamikazeHyakkomaGekijoAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kamikaze_hyakkoma_gekijo","Kamikaze Hyakkoma Gekijo",var0));
	}
}