package xyz.pixelatedw.mineminenomi.abilities.oto;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BonAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 60F;
	private static final float VOLUME = 3.5F;
	private static final int DISTANCE = 24;
	private static final float WIDTH = 3F;
	private static final float DAMAGE = 25F;
	private static final int ANIMATION_TICKS = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BonAbility>> INSTANCE;
	private final AnimationComponent animationComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;

	public BonAbility(AbilityCore<BonAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent,this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.BON.get())),SoundSource.PLAYERS,3.5F,(0.2F) + (var1.getRandom().nextFloat()));
		List var3 = this.rangeComponent.getTargetsInLine(var1,24F,3F);
		this.animationComponent.start(var1,ModAnimations.BON,10);
		Iterator var4 = var3.iterator();
		LOOP_1:
		do{
			if (var4.hasNext()){
				LivingEntity var5 = ((LivingEntity)(var4.next()));
				if (var5.hasEffect((Holder)(ModEffects.SILENT))){
					continue;
				}else{
					AbilityDamageSource var6 = this.dealDamageComponent.getDamageSource(var1);
					IDamageSourceHandler var7 = IDamageSourceHandler.getHandler(var6);
					var7.bypassLogia();
					var7.addAbilityPiercing(1F,this.getCore());
					if (this.dealDamageComponent.hurtTarget(var1,var5,25F,var6)){
						Vec3 var8 = var5.position().subtract(var1.position()).add(0D,-1D,0D).normalize();
						double var9 = 4.5D;
						double var10 = (-(var8.x)) * (var9);
						AbilityHelper.setDeltaMovement((Entity)(var5),-(var10),0.1D,-((-(var8.z)) * (var9)));
						int var11 = 0;
						do{
							if ((var11) < (5)){
								double var12 = (var5.getRandom().nextDouble()) / (2D);
								double var13 = (var5.getRandom().nextDouble()) / (2D);
								WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.SWEEP_ATTACK),((ServerLevel)(var1.level())),(var5.getX()) + (var12),((var5.getY()) + ((double)(var5.getEyeHeight()))) + (var12),(var5.getZ()) + (var13));
								var11++;
								continue;
							}else{
								break;
							}
						} while (true);
						WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.EXPLOSION),((ServerLevel)(var1.level())),var5.getX(),(var5.getY()) + ((double)(var5.getEyeHeight())),var5.getZ());
						break LOOP_1;
					}else{
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
		this.cooldownComponent.startCooldown(var1,60F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user plucks a string created from their arm, creating a sound wave that internally damages all who hear it"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(60F),DealDamageComponent.getTooltip(25F),RangeComponent.getTooltip(24F,RangeComponent$RangeType.CONE)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.INTERNAL,SourceType.SLASH,SourceType.INDIRECT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BonAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(lv1_5).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("bon","Bon",var0));
	}
}