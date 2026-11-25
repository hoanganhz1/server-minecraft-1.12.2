package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
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
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PunkCrossAbility extends Ability {
	// Fields
	private static final int REQUIRED_IRON = 50;
	private static final int COOLDOWN = 200;
	private static final int EFFECT_TIMER = 80;
	private static final int RANGE = 80;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PunkCrossAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RangeComponent rangeComponent;
	private LivingEntity target;
	private boolean hasTarget;
	private Vec3 lastKnownTargetPos;
	private List<ItemStack> magneticItems;

	public PunkCrossAbility(AbilityCore<PunkCrossAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.magneticItems = new ArrayList();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addCanUseCheck(JikiHelper.getMetalicItemsCheck(50));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::tickEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,80F);
	}
	private void tickEvent(LivingEntity var1, IAbility var2) {
		if ((this.hasTarget) && (!(((this.target) != (null)) && ((this.target.isAlive()) && (this.target.hasEffect((Holder)(ModEffects.PUNK_CROSS))))))){
			JikiHelper.dropComponentItems(var1.level(),this.lastKnownTargetPos,this.magneticItems);
			this.target = null;
			this.hasTarget = false;
		}
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.target = null;
		this.hasTarget = false;
		List var3 = ItemsHelper.getAllInventoryItems(var1);
		this.magneticItems = (List) (JikiHelper.getMagneticItemsNeeded(var1,var3,50F));
		List var4 = this.rangeComponent.getTargetsInLine(var1,80F,3F);
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
					var6.addEffect(new MobEffectInstance((Holder)(ModEffects.PUNK_CROSS),80,1));
					JikiHelper.spawnAttractEffect((Entity)(var6));
					this.hasTarget = true;
					this.target = var6;
					this.lastKnownTargetPos = var6.position();
					continue;
				}else{
					break;
				}
			} while (true);
		}else{
			BlockHitResult var5_1 = WyHelper.rayTraceBlocks((Entity)(var1),40D);
			JikiHelper.dropComponentItems(var1.level(),var5_1.getLocation(),this.magneticItems);
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.target) != (null)){
			this.lastKnownTargetPos = this.target.position();
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.target) != (null)){
			this.target.removeEffect((Holder)(ModEffects.PUNK_CROSS));
		}
		if ((this.target) != (null)){
			JikiHelper.dropComponentItems(var1.level(),this.lastKnownTargetPos,this.magneticItems);
		}
		this.hasTarget = false;
		this.target = null;
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[1];
			Object[] lv1_3 = new Object[]{MentionHelper.mentionText(Integer.valueOf(50))};
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Uses %s magnetic items and stuns the target in a cross-shaped structure."),lv1_3);
			Component[] lv1_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ContinuousComponent.getTooltip(80F)};
			SourceType[] lv1_6 = new SourceType[]{SourceType.BLUNT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PunkCrossAbility::new)))).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).setSourceType(lv1_6).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("punk_cross","Punk Cross",var0));
	}
}