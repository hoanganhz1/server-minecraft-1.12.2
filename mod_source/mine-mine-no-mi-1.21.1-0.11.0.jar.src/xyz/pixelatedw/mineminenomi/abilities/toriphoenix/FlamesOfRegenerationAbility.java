package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ConsumptionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ConsumptionComponent$IOnConsumptionEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FlamesOfRegenerationAbility extends PassiveAbility {
	// Fields
	private static final int MAX_ENERGY = 100;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<FlamesOfRegenerationAbility>> INSTANCE;
	private final DamageTakenComponent damageTakenComponent;
	private final ConsumptionComponent consumptionComponent;
	private static final int MAX_COOLDOWN = 100;
	private static final float REGEN_AMOUNT = 4F;
	private static final double REGEN_ENERGY_COST = 5D;
	private double energy;
	private int cooldown;
	private int invulnerableTime;
	private Interval recuperationInterval;
	private Interval regenerationInterval;

	public FlamesOfRegenerationAbility(AbilityCore<FlamesOfRegenerationAbility> var1) {
		super((AbilityCore)(var1));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.consumptionComponent = new ConsumptionComponent((IAbility)(this)).addConsumptionEvent((ConsumptionComponent$IOnConsumptionEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ConsumptionComponent$IOnConsumptionEvent)(this::onConsumption))));
		this.energy = 100D;
		this.cooldown = 0;
		this.invulnerableTime = 0;
		this.recuperationInterval = new Interval(10);
		this.regenerationInterval = new Interval(40);
		if (super.isClientSide()){
			GaugeComponent var2 = new GaugeComponent((IAbility)(this),(GaugeComponent$IGaugeRenderer)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer)((GaugeComponent$IGaugeRenderer) ((GaugeComponent$IGaugeRenderer<FlamesOfRegenerationAbility>) (this::renderGauge))))));
			AbilityComponent[] var3 = new AbilityComponent[]{var2};
			super.addComponents(var3);
		}
		AbilityComponent[] var2_1 = new AbilityComponent[]{this.damageTakenComponent,this.consumptionComponent};
		super.addComponents(var2_1);
		super.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassive))));
	}
	private void duringPassive(LivingEntity var1) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.invulnerableTime = (this.invulnerableTime) - (1);
			if ((this.regenerationInterval.canTick()) && ((((this.energy) - (5D)) >= (0D)) && ((var1.getHealth()) < (var1.getMaxHealth())))){
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.FLAMES_OF_REGEN.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				float var2 = 1F;
				if (((MorphInfo)(ModMorphs.PHOENIX_FLY.get())).isActive(var1)){
					var2 = 2F;
				}else{
					if (((MorphInfo)(ModMorphs.PHOENIX_ASSAULT.get())).isActive(var1)){
						var2 = 1.6F;
					}
				}
				var1.heal((4F) * (var2));
				this.addEnergy(var1,-5D);
			}
			if (WyHelper.isInCombat(var1)){
				return;
			}else{
				if ((var1.isSleeping()) && (this.recuperationInterval.canTick())){
					this.addEnergy(var1,10D);
				}
				if ((this.cooldown) > (0)){
					this.cooldown = (this.cooldown) - (1);
					return;
				}else{
					if (((this.cooldown) <= (0)) && ((this.energy) <= (0D))){
						this.addEnergy(var1,1D);
					}
					if ((this.energy) <= (0D)){
						this.cooldown = 100;
						return;
					}else{
						this.addEnergy(var1,0.05D);
						return;
					}
				}
			}
		}
	}
	private boolean onConsumption(LivingEntity var1, IAbility var2, int var3, float var4) {
		this.addEnergy(var1,((double)(((float)(var3)) * (var4))) * (2D));
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	private void renderGauge(Player var1, GuiGraphics var2, int var3, int var4, FlamesOfRegenerationAbility var5) {
		Minecraft var6 = Minecraft.getInstance();
		RendererHelper.drawIcon(((AbilityCore)(PhoenixFlyPointAbility.INSTANCE.get())).getIcon(),var2.pose(),(float)(var3),(float)((var4) - (38)),0F,32F,32F);
		String var7 = new DecimalFormat("#0.0").format(var5.energy);
		RendererHelper.drawStringWithBorder(var6.font,var2,var7,((var3) + (15)) - ((var6.font.width(var7)) / (2)),(var4) - (25),Color.WHITE.getRGB());
	}
	public void addEnergy(LivingEntity var1, double var2) {
		this.energy = Mth.clamp((this.energy) + (var2),0D,100D);
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			ModNetwork.sendTo(new SUpdateAbilityNBTPacket(var1,(IAbility)(this)),var3);
		}
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putDouble("energy",this.energy);
	}
	public void loadAdditional(CompoundTag var1) {
		this.energy = var1.getDouble("energy");
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[1];
			Object[] lv3_3 = new Object[]{Integer.valueOf(100)};
			((java.lang.Object[]) (lv3_2))[0] = ImmutablePair.of((Object)("Protects the user and heals them back up when damage is taken, has an initial reserve of §a%s Energy§r which increases with time and decreses with each heal."),lv3_3);
			Component[] lv3_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(FlamesOfRegenerationAbility::new)))).addDescriptionLine(lv3_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("flames_of_regeneration","Flames of Regeneration",var0));
	}
	public float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if (super.isPaused()){
			return var4;
		}else{
			if ((this.invulnerableTime) > (0)){
				return 0F;
			}else{
				Function<EntityStatsData, Boolean> var5 = (l0) -> {
			return Boolean.valueOf(l0.hasShadow());
		};
				boolean var6 = ((Boolean)(((java.util.Optional)(EntityStatsData.get(var1))).map((Function)(var5)).orElse(Boolean.valueOf(false)))).booleanValue();
				IDamageSourceHandler var7 = IDamageSourceHandler.getHandler(var3);
				if ((!(var7.isBypassingLogia())) && (!((var3.is(DamageTypes.ON_FIRE)) && (!(var6))))){
					Function<DevilFruitData, Boolean> var8 = (l0) -> {
			return Boolean.valueOf(l0.hasYamiPower());
		};
					if (((Boolean)(((java.util.Optional)(DevilFruitData.get(var1))).map((Function)(var8)).orElse(Boolean.valueOf(false)))).booleanValue()){
						return var4;
					}else{
						int var9 = 1;
						if ((var3.getDirectEntity()) != (null)){
							LivingEntity var10 = null;
							Entity var11 = var3.getDirectEntity();
							if (var11 instanceof LivingEntity){
								var10 = ((LivingEntity)(var11));
							}else{
								Entity var12 = var3.getDirectEntity();
								if (var12 instanceof Projectile){
									Entity var13 = ((Projectile)(var12)).getOwner();
									if (((var13) != (null)) && (var13 instanceof LivingEntity)){
										var10 = ((LivingEntity)(var13));
										var9 = (var9) & ((!(HakiHelper.hasImbuingActive(((LivingEntity)(var13))))) ? (1) : (0));
									}
								}
							}
							if ((var10) != (null)){
								boolean var12_1 = HakiHelper.hasImbuingActive(var10);
								boolean var13_1 = HakiHelper.hasHardeningActive(var10);
								if (((var7.hasHakiNature(SourceHakiNature.IMBUING)) && (var12_1)) || ((var7.hasHakiNature(SourceHakiNature.HARDENING)) && (var13_1))){
									var9 = 0;
								}else{
									if ((var7.hasHakiNature(SourceHakiNature.SPECIAL)) && (!((!(var12_1)) && (!(var13_1))))){
										var9 = 0;
									}
								}
							}
						}
						if (((var9) != (0)) && (((this.energy) - ((double)(var4))) >= (0D))){
							WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.FLAMES_OF_REGEN.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
							this.addEnergy(var1,(double)(-(var4)));
							this.invulnerableTime = 10;
							return 0F;
						}else{
							this.cooldown = 100;
							return var4;
						}
					}
				}else{
					return var4;
				}
			}
		}
	}
}