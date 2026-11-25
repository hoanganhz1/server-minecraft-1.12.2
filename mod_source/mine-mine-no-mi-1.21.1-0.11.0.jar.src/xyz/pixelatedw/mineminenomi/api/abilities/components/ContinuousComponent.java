package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetContinuityThresholdPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartContinuityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopContinuityPacket;

public class ContinuousComponent extends AbilityComponent<IAbility> {
	// Fields
	private boolean isContinuous;
	private float thresholdTime;
	private float continueTime;
	private boolean isInfinite;
	private final boolean isParallel;
	private final Predicate<ContinuousComponent> isParallelTest;
	private final PriorityEventPool<ContinuousComponent$IStartContinuousEvent> startContinuousEvents;
	private final PriorityEventPool<ContinuousComponent$IDuringContinuousEvent> tickContinuousEvents;
	private final PriorityEventPool<ContinuousComponent$IEndContinuousEvent> stopContinuousEvents;

	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip() {
		return getTooltip(-1F);
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(float var0) {
		return getTooltip(var0,var0);
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(float var0, float var1) {
		float var2 = var0;
		float var3 = var1;
		final float var2_f1 = var2;
		final float var3_f2 = var3;
		AbilityDescriptionLine$IDescriptionLine var4 = (l0, l1) -> {
			float lv1_6 = 0.0F;
			float lv1_7 = 0.0F;
			if (((var2_f1) == (var3_f2)) && ((var2_f1) == (-1F))){
				lv1_6 = (1.0F/0.0F);
				lv1_7 = (1.0F/0.0F);
			}else{
				lv1_6 = (float)(Math.round((var2_f1) / (20F)));
				lv1_7 = (float)(Math.round((var3_f2) / (20F)));
			}
			return new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_CONTINUE),lv1_6,lv1_7).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS).build().getStatDescription();
		};
		return var4;
	}
	public ContinuousComponent(IAbility var1) {
		this((IAbility)(var1),false);
	}
	public ContinuousComponent(IAbility var1, boolean var2) {
		super(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())),var1);
		this.startContinuousEvents = (PriorityEventPool) (new PriorityEventPool());
		this.tickContinuousEvents = (PriorityEventPool) (new PriorityEventPool());
		this.stopContinuousEvents = (PriorityEventPool) (new PriorityEventPool());
		this.isParallel = var2;
		Predicate<ContinuousComponent> var3 = (l0) -> {
			return true;
		};
		this.isParallelTest = var3;
	}
	public ContinuousComponent(IAbility var1, Predicate<ContinuousComponent> var2) {
		super(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())),var1);
		this.startContinuousEvents = (PriorityEventPool) (new PriorityEventPool());
		this.tickContinuousEvents = (PriorityEventPool) (new PriorityEventPool());
		this.stopContinuousEvents = (PriorityEventPool) (new PriorityEventPool());
		this.isParallel = true;
		this.isParallelTest = var2;
	}
	public void postInit(IAbility var1) {
		ContinuousComponent var2 = this;
		Consumer<SlotDecorationComponent> var3 = (l0) -> {
			ContinuousComponent lv4_2 = this;
			SlotDecorationComponent lv4_3 = l0;
			final SlotDecorationComponent lv4_3_f1 = lv4_3;
			SlotDecorationComponent$IPreRenderEvent lv4_4 = (l2_0, l2_1, l2_2, l2_3, l2_4, l2_5, l2_6, l2_7) -> {
				if (this.isContinuous()){
					lv4_3_f1.setMaxValue(this.thresholdTime);
					lv4_3_f1.setCurrentValue((this.thresholdTime) - (this.continueTime));
					if (ClientConfig.isDisplayInPercentage()){
						float lv3_10 = (((this.thresholdTime) - (this.continueTime)) / (this.thresholdTime)) * (100F);
						lv3_10 = Mth.clamp(lv3_10,0F,100F);
						String lv3_11 = String.format("%.0f",new Object[]{Float.valueOf(lv3_10)});
						String lv3_12 = lv3_11 + "%";
						lv4_3_f1.setDisplayText(lv3_12);
					}
					lv4_3_f1.setSlotColor(0F,0F,1F);
				}
			};
			l0.addPreRenderEvent(980,lv4_4);
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))))).ifPresent((Consumer)(var3));
	}
	protected void doTick(LivingEntity var1) {
		LivingEntity var9 = null;
		if ((this.getAbility().hasComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())))) && (((DisableComponent)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get())).isDisabled())){
			return;
		}else{
			ContinuousComponent var2 = this;
			Consumer var3 = (Consumer) ((Consumer<AltModeComponent>) ((l0) -> {
			l0.setDisabled(this.isContinuous());
		}));
			this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.ALT_MODE.get()))).ifPresent(var3);
			if (this.isContinuous()){
				if (var1.hasEffect((Holder)(ModEffects.IN_EVENT))){
					this.stopContinuity(var1);
					return;
				}else{
					if (!(var1.level().isClientSide)){
						Optional var4 = ProtectedAreasData.get(((ServerLevel)(var1.level()))).getProtectedArea(var1.blockPosition().getX(),var1.blockPosition().getY(),var1.blockPosition().getZ());
						if ((var4.isPresent()) && (!(((ProtectedArea)(var4.get())).canUseAbility(this.getAbility().getCore())))){
							this.stopContinuity(var1);
							return;
						}
					}
					if ((!(this.isInfinite)) && ((this.continueTime) >= (this.thresholdTime))){
						this.stopContinuity(var1);
						return;
					}else{
						AttributeInstance var4_1 = var1.getAttribute(ModAttributes.TIME_PROGRESSION);
						double var5 = 1D;
						if ((var4_1) != (null)){
							var5 = var4_1.getValue();
						}
						this.continueTime = (float)(((double)(this.continueTime)) + (((double)(this.getTpsFactor())) * (var5)));
						int var6 = Math.max(1,(int)(this.getTpsFactor()));
						int var7 = 0;
						do{
							if ((var7) < (var6)){
								ContinuousComponent var8 = this;
								var9 = var1;
								final LivingEntity var9_f1 = var9;
								Consumer<ContinuousComponent$IDuringContinuousEvent> var10 = (l0) -> {
			l0.duringContinuous(var9_f1,this.getAbility());
		};
								((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.tickContinuousEvents)).dispatch((Consumer)(var10));
								var7++;
								continue;
							}else{
								break;
							}
						} while (true);
					}
				}
			}
			return;
		}
	}
	public <T extends LivingEntity, A extends IAbility> ContinuousComponent addStartEvent(ContinuousComponent$IStartContinuousEvent var1) {
		this.startContinuousEvents.addEvent(var1);
		return this;
	}
	public <T extends LivingEntity> ContinuousComponent addStartEvent(int var1, ContinuousComponent$IStartContinuousEvent var2) {
		this.startContinuousEvents.addEvent(var1,var2);
		return this;
	}
	public <T extends LivingEntity> ContinuousComponent addTickEvent(ContinuousComponent$IDuringContinuousEvent var1) {
		this.tickContinuousEvents.addEvent(var1);
		return this;
	}
	public <T extends LivingEntity> ContinuousComponent addTickEvent(int var1, ContinuousComponent$IDuringContinuousEvent var2) {
		this.tickContinuousEvents.addEvent(var1,var2);
		return this;
	}
	public <T extends LivingEntity> ContinuousComponent addEndEvent(ContinuousComponent$IEndContinuousEvent var1) {
		this.stopContinuousEvents.addEvent(var1);
		return this;
	}
	public <T extends LivingEntity> ContinuousComponent addEndEvent(int var1, ContinuousComponent$IEndContinuousEvent var2) {
		this.stopContinuousEvents.addEvent(var1,var2);
		return this;
	}
	public void triggerContinuity(LivingEntity var1) {
		this.triggerContinuity(var1,-1F);
	}
	public void triggerContinuity(LivingEntity var1, float var2) {
		if (this.isContinuous()){
			this.stopContinuity(var1);
		}else{
			this.startContinuity(var1,var2);
		}
	}
	public void startContinuity(LivingEntity var1) {
		this.startContinuity(var1,-1F);
	}
	public void startContinuity(LivingEntity var1, float var2) {
		LivingEntity var6 = null;
		LivingEntity var7_1 = null;
		this.ensureIsRegistered();
		if (this.isContinuous()){
			return;
		}else{
			Optional var3 = this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())));
			if ((var3.isPresent()) && (((CooldownComponent)(var3.get())).isOnCooldown())){
				return;
			}else{
				Optional var4 = this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())));
				if ((var4.isPresent()) && (((DisableComponent)(var4.get())).isDisabled())){
					return;
				}else{
					Optional var5 = this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get())));
					if ((var5.isPresent()) && (!(((PoolComponent)(var5.get())).canUsePool(var1)))){
						return;
					}else{
						if ((var2) < (0F)){
							this.isInfinite = true;
						}else{
							this.isInfinite = false;
						}
						this.thresholdTime = var2;
						this.continueTime = 0F;
						this.isContinuous = true;
						if (!(var1.level().isClientSide)){
							var6 = var1;
							final LivingEntity var6_f1 = var6;
							Consumer<PoolComponent> var7 = (l0) -> {
			l0.startPoolInUse(var6_f1);
		};
							((java.util.Optional)(var5)).ifPresent((Consumer)(var7));
						}
						ContinuousComponent var6_1 = this;
						var7_1 = var1;
						final LivingEntity var7_1_f2 = var7_1;
						Consumer<ContinuousComponent$IStartContinuousEvent> var8 = (l0) -> {
			l0.startContinuous(var7_1_f2,this.getAbility());
		};
						((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.startContinuousEvents)).dispatch((Consumer)(var8));
						if (!(var1.level().isClientSide)){
							ModNetwork.sendToAllTrackingAndSelf(new SStartContinuityPacket((LivingEntity)(var1),(IAbility)(this.getAbility()),var2),var1);
						}
						return;
					}
				}
			}
		}
	}
	public void stopContinuity(LivingEntity var1) {
		LivingEntity var2 = null;
		if (!(this.isContinuous)){
			return;
		}else{
			if (!(var1.level().isClientSide)){
				var2 = var1;
				final LivingEntity var2_f1 = var2;
				Consumer<PoolComponent> var3 = (l0) -> {
			l0.stopPoolInUse(var2_f1);
		};
				((java.util.Optional)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get()))))).ifPresent((Consumer)(var3));
			}
			ContinuousComponent var2_1 = this;
			LivingEntity var3_1 = var1;
			final LivingEntity var3_1_f1 = var3_1;
			Consumer<ContinuousComponent$IEndContinuousEvent> var4 = (l0) -> {
			l0.endContinuous(var3_1_f1,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.stopContinuousEvents)).dispatch((Consumer)(var4));
			this.isContinuous = false;
			this.continueTime = 0F;
			this.thresholdTime = 0F;
			this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))).ifPresent((Consumer)(((Consumer<SlotDecorationComponent>)(SlotDecorationComponent::resetDecoration))));
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SStopContinuityPacket((LivingEntity)(var1),(IAbility)(this.getAbility())),var1);
			}
			return;
		}
	}
	public boolean isContinuous() {
		return this.isContinuous;
	}
	public float getThresholdTime() {
		return this.thresholdTime;
	}
	public void setThresholdTime(LivingEntity var1, float var2) {
		if ((var2) < (0F)){
			this.isInfinite = true;
		}else{
			this.isInfinite = false;
		}
		this.thresholdTime = var2;
		this.continueTime = 0F;
		if (!(var1.level().isClientSide)){
			ModNetwork.sendToAllTrackingAndSelf(new SSetContinuityThresholdPacket((LivingEntity)(var1),(IAbility)(this.getAbility()),this.thresholdTime),var1);
		}
	}
	public float getContinueTime() {
		return this.continueTime;
	}
	public void increaseContinuityTime(float var1) {
		this.continueTime = (this.continueTime) + (var1);
		this.continueTime = Mth.clamp(this.continueTime,0F,this.thresholdTime);
	}
	public void decreaseContinuityTime(float var1) {
		this.continueTime = (this.continueTime) - (var1);
		this.continueTime = Mth.clamp(this.continueTime,0F,this.thresholdTime);
	}
	public boolean isInfinite() {
		return this.isInfinite;
	}
	public boolean isParallel() {
		return (this.isParallel) && (this.isParallelTest.test(this));
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		var2.putFloat("continueTime",this.getContinueTime());
		var2.putFloat("thresholdTime",this.getThresholdTime());
		var2.putBoolean("isInfinite",this.isInfinite());
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		this.continueTime = var2.getFloat("continueTime");
		this.thresholdTime = var2.getFloat("thresholdTime");
		this.isInfinite = var2.getBoolean("isInfinite");
		if ((this.continueTime) > (0F)){
			this.isContinuous = true;
		}
	}
}