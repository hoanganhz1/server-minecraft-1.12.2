package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.sabi.RustSkinAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.GenocideRaidEffectEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GenocideRaidAbility extends Ability {
	// Fields
	public static final int REQUIRED_IRON = 20;
	private static final int COOLDOWN = 200;
	public static final int EFFECT_TIMER = 100;
	private static final int RANGE = 50;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GenocideRaidAbility>> INSTANCE;
	private final RangeComponent rangeComponent;

	public GenocideRaidAbility(AbilityCore<GenocideRaidAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
		this.addCanUseCheck(JikiHelper.getMetalicItemsCheck(20));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		JikiHelper.spawnAttractEffect((Entity)(var1));
		List var3 = JikiHelper.getMagneticItemsNeeded(var1,ItemsHelper.getAllInventoryItems(var1),20F);
		List var4 = this.rangeComponent.getTargetsInLine(var1,50F,3F);
		if (!(var4.isEmpty())){
			Iterator var5 = var4.iterator();
			do{
				if (var5.hasNext()){
					LivingEntity var6 = ((LivingEntity)(var5.next()));
					AbilityData var7 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var6))).orElse((Object)(null))));
					if ((var7) != (null)){
						RustSkinAbility var8 = ((RustSkinAbility)(var7.getPassiveAbility(((AbilityCore)(RustSkinAbility.INSTANCE.get())))));
						if (((var8) != (null)) && (!(var8.isPaused()))){
							break;
						}
					}
					var6.addEffect(new MobEffectInstance((Holder)(ModEffects.GENOCIDE_RAID),100,0));
					JikiHelper.spawnAttractEffect((Entity)(var6));
					GenocideRaidEffectEntity var8_1 = new GenocideRaidEffectEntity(var1.level());
					var8_1.moveTo(var6.getX(),(var6.getY()) + (1D),var6.getZ(),0F,0F);
					var8_1.setTarget(var6);
					var8_1.setOwner(var1);
					var8_1.setItemsUsed(var3);
					var1.level().addFreshEntity((Entity)(var8_1));
					continue;
				}else{
					break;
				}
			} while (true);
		}else{
			BlockHitResult var5_1 = WyHelper.rayTraceBlocks((Entity)(var1),25D);
			JikiHelper.dropComponentItems(var1.level(),var5_1.getLocation(),var3);
		}
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[1];
			Object[] lv1_3 = new Object[]{MentionHelper.mentionText(Integer.valueOf(20))};
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Uses %s magnetic items and sends them towards the target, spiriling around it and dealing damage over time."),lv1_3);
			Component[] lv1_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F)};
			SourceType[] lv1_6 = new SourceType[]{SourceType.BLUNT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GenocideRaidAbility::new)))).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).setSourceType(lv1_6).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("genocide_raid","Genocide Raid",var0));
	}
}