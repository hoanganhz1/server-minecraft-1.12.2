package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.Iterator;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction8;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public abstract class WallAbility extends Ability {
	// Fields
	protected final ContinuousComponent continuousComponent;
	protected final BlockTrackerComponent blockTrackerComponent;

	public WallAbility(AbilityCore<? extends WallAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(90,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent(90,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.blockTrackerComponent = new BlockTrackerComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.blockTrackerComponent};
		this.addComponents(var2);
		this.addUseEvent(90,(Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,(float)(this.getHoldTime()));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		Direction8 var3 = WyHelper.getLookDirection(var1);
		if ((var3) == (Direction8.SOUTH)){
			this.createWall(var1.level(),var1.blockPosition().offset(0,0,-4),this.getLength(),this.getHeight(),this.getThickness(),this.getWallBlock(),this.getGriefingRule());
		}else{
			if ((var3) == (Direction8.SOUTH_EAST)){
				this.createWall(var1.level(),var1.blockPosition().offset(2,0,-4),this.getLength(),this.getHeight(),this.getThickness(),this.getWallBlock(),this.getGriefingRule());
				this.createWall(var1.level(),var1.blockPosition().offset(4,0,-2),this.getThickness(),this.getHeight(),this.getLength(),this.getWallBlock(),this.getGriefingRule());
			}else{
				if ((var3) == (Direction8.SOUTH_WEST)){
					this.createWall(var1.level(),var1.blockPosition().offset(-2,0,-4),this.getLength(),this.getHeight(),this.getThickness(),this.getWallBlock(),this.getGriefingRule());
					this.createWall(var1.level(),var1.blockPosition().offset(-4,0,-2),this.getThickness(),this.getHeight(),this.getLength(),this.getWallBlock(),this.getGriefingRule());
				}else{
					if ((var3) == (Direction8.NORTH)){
						this.createWall(var1.level(),var1.blockPosition().offset(0,0,4),this.getLength(),this.getHeight(),this.getThickness(),this.getWallBlock(),this.getGriefingRule());
					}else{
						if ((var3) == (Direction8.NORTH_EAST)){
							this.createWall(var1.level(),var1.blockPosition().offset(2,0,4),this.getLength(),this.getHeight(),this.getThickness(),this.getWallBlock(),this.getGriefingRule());
							this.createWall(var1.level(),var1.blockPosition().offset(4,0,2),this.getThickness(),this.getHeight(),this.getLength(),this.getWallBlock(),this.getGriefingRule());
						}else{
							if ((var3) == (Direction8.NORTH_WEST)){
								this.createWall(var1.level(),var1.blockPosition().offset(-2,0,4),this.getLength(),this.getHeight(),this.getThickness(),this.getWallBlock(),this.getGriefingRule());
								this.createWall(var1.level(),var1.blockPosition().offset(-4,0,2),this.getThickness(),this.getHeight(),this.getLength(),this.getWallBlock(),this.getGriefingRule());
							}else{
								if ((var3) == (Direction8.EAST)){
									this.createWall(var1.level(),var1.blockPosition().offset(4,0,0),this.getThickness(),this.getHeight(),this.getLength(),this.getWallBlock(),this.getGriefingRule());
								}else{
									if ((var3) == (Direction8.WEST)){
										this.createWall(var1.level(),var1.blockPosition().offset(-4,0,0),this.getThickness(),this.getHeight(),this.getLength(),this.getWallBlock(),this.getGriefingRule());
									}
								}
							}
						}
					}
				}
			}
		}
		if (this.stopAfterUse()){
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void createWall(Level var1, BlockPos var2, int var3, int var4, int var5, BlockState var6, BlockProtectionRule var7) {
		SimpleBlockPlacer var8 = new SimpleBlockPlacer().setBlock(var6).setRule(var7).setSize(var3,var4,var5);
		var8.generate(var1,var2,BlockGenerators.CUBE);
		this.blockTrackerComponent.addPositions(var8.getPlacedPositions());
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		if (!(this.stopAfterUse())){
			Iterator var3 = this.blockTrackerComponent.getPositions().iterator();
			do{
				if (var3.hasNext()){
					BlockPos var4 = ((BlockPos)(var3.next()));
					if (((java.lang.Object)(var1.level().getBlockState(var4).getBlock())).equals((Object)(this.getWallBlock().getBlock()))){
						var1.level().setBlockAndUpdate(var4,Blocks.AIR.defaultBlockState());
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			this.blockTrackerComponent.clearPositions();
		}
	}
	protected int getHoldTime() {
		return -1;
	}
	public abstract int getThickness();
	public abstract int getHeight();
	public abstract int getLength();
	public abstract BlockState getWallBlock();
	public abstract BlockProtectionRule getGriefingRule();
	public abstract boolean stopAfterUse();
}