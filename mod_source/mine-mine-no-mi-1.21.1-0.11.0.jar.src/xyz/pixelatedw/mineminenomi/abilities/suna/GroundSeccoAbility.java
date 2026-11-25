package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GroundSeccoAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.8F;
	private static final int COOLDOWN = 300;
	private static final int HOLD_TIME = 200;
	private static final int CHARGE_TIME = 60;
	private static final int DISTANCE = 20;
	private static final int SIZE = 64;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GroundSeccoAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private final AltModeComponent<GroundSeccoAbility$Pattern> altModeComponent;
	private final BlockTrackerComponent blockTrackerComponent;
	private Set<SimpleBlockPlacer> placers;
	private int distance;
	private int size;
	private float sizeMod;

	public GroundSeccoAbility(AbilityCore<GroundSeccoAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),GroundSeccoAbility$Pattern.class,GroundSeccoAbility$Pattern.CENTER));
		this.blockTrackerComponent = new BlockTrackerComponent((IAbility)(this));
		this.placers = new HashSet();
		this.distance = 20;
		this.size = 64;
		this.sizeMod = 1F;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.altModeComponent,this.chargeComponent,this.blockTrackerComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresDryUser))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresOnGround))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(SunaHelper::requiresInactiveDesertGirasole))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			if (this.chargeComponent.isCharging()){
				return;
			}else{
				int var3 = 500;
				this.blockTrackerComponent.initQueue(var1.level(),var3);
				BlockPos var4 = var1.blockPosition();
				InProgressChallenge var5 = ChallengesWorldData.get().getInProgressChallengeFor(((ServerLevel)(var1.level())));
				if ((var5) != (null)){
					var4 = new BlockPos(var5.getArenaPos().getX(),var1.blockPosition().getY(),var5.getArenaPos().getZ());
					this.distance = 30;
				}
				((GroundSeccoAbility$Pattern)(this.altModeComponent.getCurrentMode())).create(this,var1.level(),var4);
				int var6 = (this.blockTrackerComponent.getQueue().getQueueSize()) / (var3);
				this.chargeComponent.startCharging(var1,(float)(var6));
				return;
			}
		}
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			return;
		}
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1));
			this.blockTrackerComponent.tickBlockQueue(var1.level());
			return;
		}
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.startContinuity(var1,200F);
		Iterator var3 = this.placers.iterator();
		do{
			if (var3.hasNext()){
				SimpleBlockPlacer var4 = ((SimpleBlockPlacer)(var3.next()));
				this.blockTrackerComponent.addPositions(var4.getPlacedPositions());
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		Iterator var3 = this.blockTrackerComponent.getPositions().iterator();
		do{
			if (var3.hasNext()){
				BlockPos var4 = ((BlockPos)(var3.next()));
				var1.level().setBlock(var4,Blocks.SAND.defaultBlockState(),2);
				continue;
			}else{
				break;
			}
		} while (true);
		this.placers.clear();
		this.cooldownComponent.startCooldown(var1,300F);
	}
	static void setupCornersPattern(GroundSeccoAbility var0, Level var1, BlockPos var2) {
		createCircle(var0,var1,var2.east(var0.distance).north(var0.distance),var0.size);
		createCircle(var0,var1,var2.west(var0.distance).north(var0.distance),var0.size);
		createCircle(var0,var1,var2.east(var0.distance).south(var0.distance),var0.size);
		createCircle(var0,var1,var2.west(var0.distance).south(var0.distance),var0.size);
	}
	static void setupCirclePattern(GroundSeccoAbility var0, Level var1, BlockPos var2) {
		createCircle(var0,var1,var2.north(var0.distance),var0.size);
		createCircle(var0,var1,var2.north(var0.distance).east(var0.distance),var0.size);
		createCircle(var0,var1,var2.east(var0.distance),var0.size);
		createCircle(var0,var1,var2.south(var0.distance).east(var0.distance),var0.size);
		createCircle(var0,var1,var2.south(var0.distance),var0.size);
		createCircle(var0,var1,var2.south(var0.distance).west(var0.distance),var0.size);
		createCircle(var0,var1,var2.west(var0.distance),var0.size);
		createCircle(var0,var1,var2.north(var0.distance).west(var0.distance),var0.size);
	}
	static void setupCenterPattern(GroundSeccoAbility var0, Level var1, BlockPos var2) {
		createCircle(var0,var1,var2,(var0.size) * (2));
	}
	static void setupCrossPattern(GroundSeccoAbility var0, Level var1, BlockPos var2) {
		createCircle(var0,var1,var2,var0.size);
		createCircle(var0,var1,var2.north(var0.distance),var0.size);
		createCircle(var0,var1,var2.south(var0.distance),var0.size);
		createCircle(var0,var1,var2.east(var0.distance),var0.size);
		createCircle(var0,var1,var2.west(var0.distance),var0.size);
	}
	private static void createCircle(GroundSeccoAbility var0, Level var1, BlockPos var2, int var3) {
		int var4 = Math.round(((float)(var3)) * (var0.sizeMod));
		SimpleBlockPlacer var5 = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.SUNA_SAND.get())).defaultBlockState()).setSize(var4,4).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID).setBlockQueue(var0.blockTrackerComponent.getQueue());
		var5.generate(var1,var2,BlockGenerators.SPHERE);
		var0.placers.add(var5);
	}
	public void changePattern(LivingEntity var1, GroundSeccoAbility$Pattern var2) {
		this.altModeComponent.setMode(var1,(Enum)(var2));
	}
	public void changeRandomPattern(LivingEntity var1) {
		GroundSeccoAbility$Pattern var2 = GroundSeccoAbility$Pattern.values()[var1.getRandom().nextInt((GroundSeccoAbility$Pattern.values().length) - (1))];
		this.altModeComponent.setMode(var1,(Enum)(var2));
	}
	public void setSizeModifier(float var1) {
		this.sizeMod = var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[2];
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Dries out the area around the user."),(Object)(null));
			Object[] lv1_3 = new Object[1];
			int lv1_4 = Math.round(19.999998F);
			String lv1_5 = "§a" + lv1_4 + "%§r";
			lv1_3[0] = lv1_5;
			((java.lang.Object[]) (lv1_2))[1] = ImmutablePair.of((Object)("While in a desert the cooldown of this ability is reduced by %s."),lv1_3);
			Component[] lv1_6 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_7 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(300F),ContinuousComponent.getTooltip(200F),ChargeComponent.getTooltip(60F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GroundSeccoAbility::new)))).addDescriptionLine(lv1_6).addAdvancedDescriptionLine(lv1_7).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("ground_secco","Ground Secco",var0));
	}
}