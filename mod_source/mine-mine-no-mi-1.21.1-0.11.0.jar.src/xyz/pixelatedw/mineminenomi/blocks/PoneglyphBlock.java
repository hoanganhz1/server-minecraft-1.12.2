package xyz.pixelatedw.mineminenomi.blocks;

import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.slf4j.Logger;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.PoneglyphBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.PoneglyphBlockEntity$Type;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncChallengeDataPacket;

public class PoneglyphBlock extends Block implements EntityBlock {
	// Fields
	public static final IntegerProperty TEXTURE = IntegerProperty.create("texture",0,2);

	public PoneglyphBlock() {
		super(BlockBehaviour.Properties.of().strength(3.4028235e+38F).noLootTable());
		this.registerDefaultState(((BlockState)(((BlockState)(this.stateDefinition.any())).setValue((Property)(TEXTURE),(Comparable)(Integer.valueOf(0))))));
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	public void initPoneglyph(LevelAccessor var1, BlockPos var2) {
		ChallengeCore var3 = null;
		Iterator var4 = WyHelper.getNearbyBlocks(var2,var1,2).iterator();
		do{
			if (var4.hasNext()){
				BlockPos var5 = ((BlockPos)(var4.next()));
				BlockEntity var6 = var1.getBlockEntity(var5);
				if ((var5.equals(var2)) || (!(var6 instanceof PoneglyphBlockEntity))){
					continue;
				}else{
					PoneglyphBlockEntity var7 = ((PoneglyphBlockEntity)(var6));
					if ((var7.getChallenge()) == (null)){
						continue;
					}else{
						var3 = var7.getChallenge();
						break;
					}
				}
			}else{
				break;
			}
		} while (true);
		if ((var3) == (null)){
			Predicate var5_1 = (Predicate) ((Predicate<ChallengeCore>) ((l0) -> {
			return (l0.getDifficulty()) == (ChallengeDifficulty.STANDARD);
		}));
			List var6_1 = ((List)(((java.util.stream.Stream)(WyRegistry.CHALLENGES.stream().filter(var5_1))).collect((Collector)(Collectors.toList()))));
			if (var6_1.isEmpty()){
				LogUtils.getLogger().warn("Challenges registry is empty!");
				return;
			}else{
				Collections.shuffle(var6_1);
				ChallengeCore var7_1 = ((ChallengeCore)(var6_1.get(0)));
				var3 = ((var7_1) != (null)) ? (var7_1) : ((ChallengeCore)(((ChallengeCore)(ModChallenges.MORGAN.get()))));
			}
		}
		((PoneglyphBlockEntity)(var1.getBlockEntity(var2))).setChallenge(var3);
	}
	@Nullable()
	public BlockState getStateForPlacement(BlockPlaceContext var1) {
		BlockState var2 = this.defaultBlockState();
		BlockState var3 = ((BlockState)(var2.setValue((Property)(TEXTURE),(Comparable)(Integer.valueOf(var1.getLevel().getRandom().nextInt(3))))));
		return var3;
	}
	public InteractionResult useWithoutItem(BlockState var1, Level var2, BlockPos var3, Player var4, BlockHitResult var5) {
		PoneglyphBlockEntity var6 = ((PoneglyphBlockEntity)(var2.getBlockEntity(var3)));
		if (NuWorld.isChallengeDimension(var2)){
			return InteractionResult.PASS;
		}else{
			if (var2.isClientSide){
				return InteractionResult.SUCCESS;
			}else{
				if ((var6.getChallenge()) == (null)){
					this.initPoneglyph((LevelAccessor)(var2),var3);
				}
				if ((var6.getEntryType()) == (PoneglyphBlockEntity$Type.CHALLENGE)){
					ChallengeData var7 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get(var4))).orElse((Object)(null))));
					if ((var7) == (null)){
						return InteractionResult.FAIL;
					}else{
						ChallengeCore var8 = var6.getChallenge();
						if ((var8) == (null)){
							LogUtils.getLogger().warn("Poneglyph has no challenge!");
							return InteractionResult.FAIL;
						}else{
							boolean var9 = false;
							ItemStack var10 = var4.getMainHandItem();
							int var11 = ((!(var10.isEmpty())) && (((java.lang.Object)(var10.getItem())).equals((Object)(Items.PAPER)))) ? (1) : (0);
							int var12 = ((!(var10.isEmpty())) && ((((java.lang.Object)(var10.getItem())).equals((Object)(Items.BOOK))) && (var10.has(ModDataComponents.IS_ANCIENT_MANUAL)))) ? (1) : (0);
							if ((var11) != (0)){
								if (var7.hasChallenge(var8)){
									Object[] var13 = new Object[]{var8.getLocalizedTitle()};
									var4.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_ALREADY_UNLOCKED,var13)));
									return InteractionResult.FAIL;
								}else{
									var7.addChallenge(var8);
									Object[] var13_1 = new Object[]{var8.getLocalizedTitle()};
									var4.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_UNLOCKED,var13_1)));
									var10.shrink(1);
									var9 = true;
								}
							}else{
								if ((var12) != (0)){
									ChallengeCore var13_2 = var8.getHardVersion();
									if ((var13_2) == (null)){
										return InteractionResult.FAIL;
									}else{
										Challenge var14 = var7.getChallenge(var13_2);
										if ((var14) == (null)){
											Object[] var15 = new Object[]{var13_2.getLocalizedTitle()};
											var4.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_NOT_UNLOCKED,var15)));
											return InteractionResult.FAIL;
										}else{
											if (!(var14.increaseBonus())){
												Object[] var15_1 = new Object[]{var13_2.getLocalizedTitle()};
												var4.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_BONUS_MAXED,var15_1)));
												return InteractionResult.FAIL;
											}else{
												Object[] var15_2 = new Object[]{var13_2.getLocalizedTitle()};
												var4.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_BONUS_INCREASED,var15_2)));
												var10.shrink(1);
												var9 = true;
											}
										}
									}
								}
							}
							if (var9){
								ModNetwork.sendTo(new SSyncChallengeDataPacket(var4),var4);
								return InteractionResult.SUCCESS;
							}else{
								var4.sendSystemMessage(ModI18nChallenges.MESSAGE_INSCRIPTION_NO_PAPER);
							}
						}
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
		Property[] var2 = new Property[]{TEXTURE};
		var1.add(var2);
	}
	public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
		return new PoneglyphBlockEntity(var1,var2);
	}
}