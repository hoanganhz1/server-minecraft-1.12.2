package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IPauseEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public abstract class LogiaBlockBypassingAbility extends PassiveAbility {
	// Fields
	public static final Component[] DESCRIPTION;
	private boolean isInBlock;

	public LogiaBlockBypassingAbility(AbilityCore<? extends LogiaBlockBypassingAbility> var1) {
		super((AbilityCore)(var1));
		this.isInBlock = false;
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassive))));
		this.pauseTickComponent.addPauseEvent(110,(PauseTickComponent$IPauseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IPauseEvent)(this::onPauseEvent))));
	}
	private void duringPassive(LivingEntity var1) {
		if (var1 instanceof Player){
			Player var2 = ((Player)(var1));
			Abilities var3 = var2.getAbilities();
			if (this.canGoThrough(var1.level().getBlockState(var1.blockPosition()))){
				if (!(var3.flying)){
					var3.flying = true;
					var2.onUpdateAbilities();
				}
				if (!(this.isInBlock)){
					Consumer<DevilFruitData> var4 = (l0) -> {
			l0.setInsideLogiaBlock(true);
		};
					((java.util.Optional)(DevilFruitData.get((LivingEntity)(var2)))).ifPresent((Consumer)(var4));
				}
				this.isInBlock = true;
				this.spawnParticles(var1);
			}else{
				if (this.isInBlock){
					this.stopBlockTravel(var2,var3);
				}else{
					return;
				}
			}
		}
	}
	private void onPauseEvent(LivingEntity var1, IAbility var2) {
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			if (this.isInBlock){
				this.stopBlockTravel(var3,var3.getAbilities());
			}else{
				return;
			}
		}
	}
	private void stopBlockTravel(Player var1, Abilities var2) {
		var2.flying = false;
		var1.onUpdateAbilities();
		this.isInBlock = false;
		Consumer<DevilFruitData> var3 = (l0) -> {
			l0.setInsideLogiaBlock(false);
		};
		((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1)))).ifPresent((Consumer)(var3));
	}
	public abstract void spawnParticles(LivingEntity var0);
	public abstract boolean canGoThrough(BlockState var0);
	public static boolean isAllowedToGoThrough(LivingEntity var0, BlockState var1) {
		if ((var0) == (null)){
			return false;
		}else{
			if (!(var1.is(ModTags$Blocks.LOGIA_BLOCK_PASS_ALL))){
				return false;
			}else{
				boolean var2 = ((Boolean)(DevilFruitData.get(var0).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::isInsideLogiaBlock)))).orElse(Boolean.valueOf(false)))).booleanValue();
				boolean var3 = var0.isSteppingCarefully();
				if ((!(var2)) && (!(var3))){
					return false;
				}else{
					AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
					if ((var4) == (null)){
						return false;
					}else{
						Class var5 = LogiaBlockBypassingAbility.class;
						Objects.requireNonNull(var5);
						Class var6 = LogiaBlockBypassingAbility.class;
						Objects.requireNonNull(var6);
						return var4.getPassiveAbilities().stream().filter((Predicate)(((Predicate<IAbility>)(var5::isInstance)))).map((Function)(((Function<IAbility, LogiaBlockBypassingAbility>)(var6::cast)))).anyMatch((Predicate)(((Predicate<LogiaBlockBypassingAbility>)((l0) -> {
			return (l0.canGoThrough(var1)) && (!(l0.isPaused()));
		}))));
					}
				}
			}
		}
	}
	static  {
		Pair[] var0 = new Pair[]{ImmutablePair.of((Object)("Allows the user to move through specific blocks based on their element"),(Object)(null))};
		DESCRIPTION = AbilityHelper.registerDescriptionText("mineminenomi","logia_block_bypassing",var0);
	}
}