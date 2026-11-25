package xyz.pixelatedw.mineminenomi.abilities.gura;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class TenchiMeidoAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 400;
	private static final int CHARGE_TIME = 20;
	private static final int RANGE = 26;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<TenchiMeidoAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final RangeComponent rangeComponent;
	private BreakingBlocksParticleEffect$Details details;

	public TenchiMeidoAbility(AbilityCore<TenchiMeidoAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.details = new BreakingBlocksParticleEffect$Details(40);
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresOnGround))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,20F);
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		if (((this.chargeComponent.getChargeTime()) % (2F)) == (0F)){
			ArrayList var3 = new ArrayList();
			int var4 = (int)(Math.ceil(26D));
			int var5 = -(var4);
			LOOP_1:
			do{
				if ((var5) < (var4)){
					int var6 = -(var4);
					do{
						if ((var6) < (var4)){
							if ((var1.getRandom().nextInt(100)) == (0)){
								double var7 = (var1.getX()) + ((double)(var5));
								double var8 = (var1.getY()) - (1D);
								double var9 = (var1.getZ()) + ((double)(var6));
								var3.add(new Vec3(var7,var8,var9));
							}
							var6++;
							continue;
						}else{
							break;
						}
					} while (true);
					var5++;
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
			if ((((java.util.List)(var3)).size()) > (0)){
				this.details.setVecPositions((List)(var3));
			}
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BREAKING_BLOCKS.get())),(Entity)(var1),0D,0D,0D,(ParticleEffect$Details)(this.details));
		}
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),10,0));
		Iterator var3_1 = this.rangeComponent.getTargetsInArea(var1,26F).iterator();
		do{
			if (var3_1.hasNext()){
				((LivingEntity)(var3_1.next())).addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),10,0,false,false));
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			Iterator var3 = this.rangeComponent.getTargetsInArea(var1,26F).iterator();
			do{
				if (var3.hasNext()){
					LivingEntity var4 = ((LivingEntity)(var3.next()));
					Vec3 var5 = var1.position().subtract(var4.position()).normalize().multiply(5D,1D,5D);
					AbilityHelper.setDeltaMovement((Entity)(var4),-(var5.x),3D,-(var5.z));
					continue;
				}else{
					break;
				}
			} while (true);
			this.cooldownComponent.startCooldown(var1,400F);
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user grabs the air and pulls it downwards, after which all of the opponents are tossed into the air."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(400F),ChargeComponent.getTooltip(20F),RangeComponent.getTooltip(26F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(TenchiMeidoAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("tenchi_meido","Tenchi Meido",var0));
	}
}