package xyz.pixelatedw.mineminenomi.abilities.doa;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class DoorDoorAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 100;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DoorDoorAbility>> INSTANCE;

	public DoorDoorAbility(AbilityCore<DoorDoorAbility> var1) {
		super((AbilityCore)(var1));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if ((Math.sqrt(var1.distanceToSqr(WyHelper.rayTraceBlocks((Entity)(var1),16D).getLocation()))) > (2.5D)){
			return;
		}else{
			Vec3 var3 = var1.getLookAngle().normalize();
			Vec3 var4 = var1.position();
			BlockPos.MutableBlockPos var5 = new BlockPos.MutableBlockPos();
			var5.set(var4.x,var4.y,var4.z);
			boolean var6 = false;
			int var7 = 0;
			Direction var8 = var1.getDirection().getOpposite();
			int var9 = 0;
			do{
				if ((var9) < (40)){
					BlockState var10 = var1.level().getBlockState((BlockPos)(var5));
					if (((var10.getBlock()) == (Blocks.AIR)) && (!((!(var6)) && ((var7) <= (1))))){
						var1.teleportTo(var4.x(),(var4.y()) + (1D),var4.z());
						break;
					}else{
						var4 = var4.add(var3.x,var3.y,var3.z);
						var5.set(Mth.floor(var4.x),Mth.ceil(var4.y),Mth.floor(var4.z));
						BlockState var11 = var1.level().getBlockState((BlockPos)(var5));
						if (var11.isFaceSturdy((BlockGetter)(var1.level()),(BlockPos)(var5),var8,SupportType.FULL)){
							var6 = true;
						}
						if ((var11.getBlock()) == (Blocks.AIR)){
							var7++;
						}
						var9++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.DOA_IN_SFX.get())),SoundSource.PLAYERS,2F,1F);
			this.cooldownComponent.startCooldown(var1,100F);
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("By making a door, the user transports to the other side of any surface."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DoorDoorAbility::new)))).addDescriptionLine(lv2_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("door_door","Door Door",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(100F);
		};
		CooldownComponentTooltip.generate(var2,var3);
	}
}