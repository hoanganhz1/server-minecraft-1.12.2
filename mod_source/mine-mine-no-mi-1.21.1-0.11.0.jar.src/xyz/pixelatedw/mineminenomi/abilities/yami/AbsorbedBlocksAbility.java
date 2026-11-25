package xyz.pixelatedw.mineminenomi.abilities.yami;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.NBTHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class AbsorbedBlocksAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<AbsorbedBlocksAbility>> INSTANCE;
	private List<AbsorbedBlocksAbility$MarkedBlock> markedBlocks;
	private List<BlockState> absorbedBlocks;
	private int absorbedBlocksAmount;
	private int markedBlocksAmount;

	public AbsorbedBlocksAbility(AbilityCore<AbsorbedBlocksAbility> var1) {
		super((AbilityCore)(var1));
		this.markedBlocks = new ArrayList();
		this.absorbedBlocks = new ArrayList();
		this.absorbedBlocksAmount = 0;
		this.markedBlocksAmount = 0;
		if (super.isClientSide()){
			GaugeComponent var2 = new GaugeComponent((IAbility)(this),(GaugeComponent$IGaugeRenderer)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer)((GaugeComponent$IGaugeRenderer) ((GaugeComponent$IGaugeRenderer<AbsorbedBlocksAbility>) (this::renderGauge))))));
			AbilityComponent[] var3 = new AbilityComponent[]{var2};
			this.addComponents(var3);
		}
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::onDuringPassive))));
	}
	public void onDuringPassive(LivingEntity var1) {
	}
	@OnlyIn(value=Dist.CLIENT)
	private void renderGauge(Player var1, GuiGraphics var2, int var3, int var4, AbsorbedBlocksAbility var5) {
		Minecraft var6 = Minecraft.getInstance();
		RenderSystem.setShaderTexture(0,ModResources.WIDGETS);
		RendererHelper.drawIcon(((AbilityCore)(BlackHoleAbility.INSTANCE.get())).getIcon(),var2.pose(),(float)(var3),(float)((var4) - (38)),0F,32F,32F);
		int var7 = 25;
		if ((var5.markedBlocksAmount) > (0)){
			int var8 = var5.markedBlocksAmount;
			String var9 = "(" + var8 + ")";
			String var10 = var9;
			RendererHelper.drawStringWithBorder(var6.font,var2,var10,((var3) + (15)) - ((var6.font.width(var10)) / (2)),(var4) - (20),Color.WHITE.getRGB());
			var7 = 32;
		}
		String var8_1 = Integer.toString(var5.absorbedBlocksAmount);
		RendererHelper.drawStringWithBorder(var6.font,var2,var8_1,((var3) + (15)) - ((var6.font.width(var8_1)) / (2)),(var4) - (var7),Color.WHITE.getRGB());
	}
	public List<AbsorbedBlocksAbility$MarkedBlock> getMarkedBlocks() {
		return this.markedBlocks;
	}
	public List<BlockState> getAbsorbedBlocks() {
		return this.absorbedBlocks;
	}
	public void addMarkedBlock(BlockState var1, BlockPos var2) {
		this.markedBlocks.add(new AbsorbedBlocksAbility$MarkedBlock(var1,var2));
	}
	public void removeMarkedBlock(AbsorbedBlocksAbility$MarkedBlock var1) {
		this.markedBlocks.remove(var1);
	}
	
	public void addAbsorbedBlock(BlockState var1) {
		this.absorbedBlocks.add(var1);
	}
	public void removeAbsorbedBlock(BlockState var1) {
		this.absorbedBlocks.remove(var1);
	}
	public void clearMarkedBlocks() {
		this.markedBlocks.clear();
		this.markedBlocksAmount = 0;
	}
	
	public void loadAdditional(CompoundTag var1) {
		this.markedBlocksAmount = var1.getInt("markedBlocksAmount");
		this.absorbedBlocks.clear();
		NBTHelper.loadBlockCollection(var1,(Collection)(this.absorbedBlocks));
		this.absorbedBlocksAmount = this.absorbedBlocks.size();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("Displays the total amount of blocks the user has absorbed."),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(AbsorbedBlocksAbility::new)))).addDescriptionLine(lv3_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("absorbed_blocks","Absorbed Blocks",var0));
	}
public void removeMarkedBlock(BlockState var1, BlockPos var2) {
		this.markedBlocks.removeIf((Predicate)(((Predicate<AbsorbedBlocksAbility$MarkedBlock>)((l0) -> {
			return (((java.lang.Object)(l0.blockState)).equals((Object)(var1))) && (l0.blockPos.equals(var2));
		}))));
	}public void saveAdditional(CompoundTag var1) {
		Predicate<AbsorbedBlocksAbility$MarkedBlock> var2 = (l0) -> {
			return LiberationAbility.MARK_ABSORB_RULE.isApproved(l0.blockState);
		};
		var1.putInt("markedBlocksAmount",((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.markedBlocks.stream().distinct())).filter((Predicate)(var2)))).collect((Collector)(Collectors.toList())))).size());
		NBTHelper.saveBlockCollection(var1,(Collection)(this.absorbedBlocks));
	}}