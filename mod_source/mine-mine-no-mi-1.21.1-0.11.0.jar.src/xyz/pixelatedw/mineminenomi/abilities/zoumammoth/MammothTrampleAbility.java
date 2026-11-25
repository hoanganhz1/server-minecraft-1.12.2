package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class MammothTrampleAbility extends PassiveAbility {
	// Fields
	private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/trample.png");
	private static final float TRAMPLE_AREA = 3.5F;
	private static final int BLOCK_BREAKING_AREA = 5;
	private static final float DAMAGE = 6F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<MammothTrampleAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private BreakingBlocksParticleEffect$Details details;
	public float speed;

	public MammothTrampleAbility(AbilityCore<MammothTrampleAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.details = new BreakingBlocksParticleEffect$Details(100);
		this.speed = 0F;
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(ZouMammothHelper::requiresGuardPoint))));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassiveEvent))));
	}
	
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Running speed increases with acceleration trampling any nearby entity."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv2_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv2_5 = new Supplier[]{ModMorphs.MAMMOTH_GUARD};
			lv2_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv2_5);
			AbilityDescriptionLine$IDescriptionLine[] lv2_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			SourceType[] lv2_7 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(MammothTrampleAbility::new)))).setIcon(ICON).addDescriptionLine(lv2_3).addDescriptionLine(lv2_4).addAdvancedDescriptionLine(lv2_6).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_7).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("mammoth_trample","Trample",var0));
	}
public void duringPassiveEvent(LivingEntity var1) {
		if ((var1 instanceof Player) && (((Player)(var1)).getAbilities().flying)){
			return;
		}else{
			if (!(((MorphInfo)(ModMorphs.MAMMOTH_GUARD.get())).isActive(var1))){
				return;
			}else{
				if (!(var1.isSprinting())){
					this.speed = 0F;
				}else{
					List var2 = this.rangeComponent.getTargetsInArea(var1,var1.blockPosition(),3.5F);
					AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
					if ((var3) == (null)){
						return;
					}else{
						AncientStompAbility var4 = ((AncientStompAbility)(var3.getEquippedAbility(((AbilityCore)(AncientStompAbility.INSTANCE.get())))));
						float var5 = ((var4) != (null)) ? ((var4.isContinuous()) ? (0.1F) : (0.45F)) : (0.45F);
						float var6 = ((var4) != (null)) ? ((var4.isContinuous()) ? (0.002F) : (0.006F)) : (0.006F);
						float var7 = (var6) * (((this.speed) > (0F)) ? ((1F) - ((this.speed) / (var5))) : (1F));
						if (((var1.zza) > (0F)) && (!(var1.horizontalCollision))){

						}else{
							var7 = (-(var5)) / (10F);
						}
						this.speed = Mth.clamp((this.speed) + (var7),0.2F,var5);
						int var8 = ((var1.zza) > (0F)) ? (1) : (0);
						Vec3 var9 = var1.getLookAngle();
						double var10 = ((var9.x) * ((double)(this.speed))) * ((double)(var8));
						double var11 = ((var9.z) * ((double)(this.speed))) * ((double)(var8));
						AbilityHelper.setDeltaMovement((Entity)(var1),var10,var1.getDeltaMovement().y,var11);
						if (!(var1.level().isClientSide)){
							Predicate<BlockState> var12 = (l0) -> {
			return (!(l0.isAir())) && (l0.is(ModTags$Blocks.BLOCK_PROT_FOLIAGE));
		};
							List var13 = WyHelper.getNearbyBlocks(var1.blockPosition(),(LevelAccessor)(var1.level()),5,5,5,var12);
							ArrayList var14 = new ArrayList();
							Iterator var15 = var13.iterator();
							do{
								if (var15.hasNext()){
									BlockPos var16 = ((BlockPos)(var15.next()));
									if (NuWorld.setBlockState((Entity)(var1),var16,Blocks.AIR.defaultBlockState(),3,DefaultProtectionRules.FOLIAGE)){
										var14.add(var16);
										continue;
									}else{
										continue;
									}
								}else{
									break;
								}
							} while (true);
							if ((((java.util.List)(var14)).size()) > (0)){
								this.details.setPositions((List)(var14));
								WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BREAKING_BLOCKS.get())),(Entity)(var1),0D,0D,0D,(ParticleEffect$Details)(this.details));
							}
							Iterator var16_1 = var2.iterator();
							do{
								if (var16_1.hasNext()){
									LivingEntity var17 = ((LivingEntity)(var16_1.next()));
									if (this.dealDamageComponent.hurtTarget(var1,var17,6F)){
										Vec3 var18 = var1.getLookAngle().normalize().multiply(2D,1D,2D);
										AbilityHelper.setDeltaMovement((Entity)(var17),var18.x,0.2D,var18.z);
										continue;
									}else{
										continue;
									}
								}else{
									break;
								}
							} while (true);
						}else{
							return;
						}
					}
				}
				return;
			}
		}
	}}