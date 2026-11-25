package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Client;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.CRequestSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilitiesListScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilityTreeScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenChallengesScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.ui.widgets.PlankButton;

public class PlayerStatsScreen extends Screen {
	// Fields
	private static final ItemStack YAMI_STACK = new ItemStack(((ItemLike)(ModFruits.YAMI_YAMI_NO_MI.get())));
	private final AbstractClientPlayer player;
	private EntityStatsData entityStatsProps;
	private DevilFruitData devilFruitProps;
	private ChallengeData challengesProps;
	private QuestData questProps;
	private AbilityData abilityProps;
	private final double doriki;
	private final boolean hasQuestsConfigEnabled;
	private final int questAmount;
	private final boolean hasChallengesConfigEnabled;
	private final int challengeAmount;
	private final boolean isInCombat;
	private final boolean isInChallengeDimension;
	private final int invites;
	private final boolean hasCrew;
	private Component colaLabel;
	private Component dorikiLabel;
	private Component factionLabel;
	private Component raceLabel;
	private Component styleLabel;
	private Component bellyLabel;
	private Component extolLabel;

	public PlayerStatsScreen(double var1, boolean var2, int var3, boolean var4, int var5, boolean var6, boolean var7, int var8, boolean var9) {
		super((Component)(Component.empty()));
		this.player = Minecraft.getInstance().player;
		this.doriki = var1;
		this.hasQuestsConfigEnabled = var2;
		this.questAmount = var3;
		this.hasChallengesConfigEnabled = var4;
		this.challengeAmount = var5;
		this.isInCombat = var6;
		this.isInChallengeDimension = var7;
		this.invites = var8;
		this.hasCrew = var9;
		this.entityStatsProps = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(this.player)))).orElse((Object)(null))));
		this.devilFruitProps = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(this.player)))).orElse((Object)(null))));
		this.challengesProps = ((ChallengeData)(((java.util.Optional)(ChallengeData.get((Player)(this.player)))).orElse((Object)(null))));
		this.questProps = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(this.player)))).orElse((Object)(null))));
		AbilityData var10 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(this.player)))).orElse((Object)(null))));
		this.abilityProps = var10;
		try{
			this.initLabels();
		}catch(Exception var11){
			var11.printStackTrace();
		}
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
		int var5 = ((this.width) - (256)) / (2);
		int var6 = ((this.height) - (256)) / (2);
		if (this.entityStatsProps.isCyborg()){
			RendererHelper.drawStringWithBorder(this.font,var1,this.colaLabel,(var5) - (50),(var6) + (30),-1);
		}
		RendererHelper.drawStringWithBorder(this.font,var1,this.dorikiLabel,(var5) - (50),(var6) + (50),-1);
		RendererHelper.drawStringWithBorder(this.font,var1,this.factionLabel,(var5) - (50),(var6) + (70),-1);
		RendererHelper.drawStringWithBorder(this.font,var1,this.raceLabel,(var5) - (50),(var6) + (90),-1);
		RendererHelper.drawStringWithBorder(this.font,var1,this.styleLabel,(var5) - (50),(var6) + (110),-1);
		if ((this.entityStatsProps.getBelly()) > (0L)){
			RendererHelper.drawStringWithBorder(this.font,var1,this.bellyLabel,(var5) + (215),(var6) + (52),-1);
			var1.blit(ModResources.CURRENCIES,(var5) + (190),(var6) + (40),0,32,32,64);
		}
		if ((this.entityStatsProps.getExtol()) > (0L)){
			RendererHelper.drawStringWithBorder(this.font,var1,this.extolLabel,(var5) + (215),(var6) + (82),-1);
			var1.blit(ModResources.CURRENCIES,(var5) + (190),(var6) + (69),34,32,64,86);
		}
		if (this.devilFruitProps.hasAnyDevilFruit()){
			Item var7 = this.devilFruitProps.getDevilFruitItem();
			if (var7 instanceof AkumaNoMiItem){
				AkumaNoMiItem var8 = ((AkumaNoMiItem)(var7));
				ItemStack var9 = new ItemStack((ItemLike)((RandomizedFruitsHandler$Client.HAS_RANDOMIZED_FRUIT) ? (var8.getReverseShiftedFruit()) : (var8)));
				int var10 = (((this.devilFruitProps.getDevilFruitItem()) != (ModFruits.YAMI_YAMI_NO_MI.get())) && (this.devilFruitProps.hasYamiPower())) ? (1) : (0);
				if (!(var9.isEmpty())){
					String var11 = var8.getDevilFruitName().getString();
					int var12 = -1;
					if (this.devilFruitProps.hasAwakenedFruit()){
						var12 = 15509033;
					}
					StringBuilder var13 = new StringBuilder();
					var13.append(var11);
					if ((var10) != (0)){
						String var14 = ((AkumaNoMiItem)(ModFruits.YAMI_YAMI_NO_MI.get())).getDevilFruitName().getString();
						String var15 = " + " + var14;
						var13.append(var15);
						var1.renderItem(YAMI_STACK,(var5) - (56),(var6) + (147));
					}
					String var14_1 = String.valueOf((Object)(ChatFormatting.BOLD));
					String var15_1 = var13.toString();
					String var16 = var14_1 + var15_1;
					RendererHelper.drawStringWithBorder(this.font,var1,var16,(var5) - (28),(var6) + (154),var12);
					var1.renderItem(var9,(var5) - (50),(var6) + (150));
				}else{
					RendererHelper.drawStringWithBorder(this.font,var1,"§4§lUnknown Fruit§r",(var5) - (28),(var6) + (154),-1);
				}
			}else{
				return;
			}
		}
	}
	public void init() {
		this.initLabels();
		LinkedHashSet var1 = new LinkedHashSet();
		Button.OnPress var2 = (l0) -> {
			ModNetwork.sendToServer(new COpenAbilitiesListScreenPacket());
		};
		PlankButton var3 = new PlankButton(0,0,80,26,ModI18n.GUI_ABILITIES,var2);
		if ((((this.abilityProps.countUnlockedAbilities()) > (0)) ? (1) : (0)) == (0)){
			var3.active = false;
		}
		((java.util.Set)(var1)).add((Object)(var3));
		Tooltip var5 = null;
		if (this.hasQuestsConfigEnabled){
			int var4 = ((this.questAmount) > (0)) ? (1) : (0);
			var5 = null;
			if ((this.questAmount) <= (0)){
				var5 = Tooltip.create(ModI18n.GUI_NO_QUESTS_AVAILABLE);
			}
			Button.OnPress var6 = (l0) -> {
			ModNetwork.sendToServer(new CRequestSyncQuestDataPacket(true));
		};
			PlankButton var7 = new PlankButton(0,0,80,26,ModI18n.GUI_QUESTS,var6);
			var7.setTooltip(var5);
			if ((var4) == (0)){
				var7.active = false;
			}
			((java.util.Set)(var1)).add((Object)(var7));
		}
		Button.OnPress var4_1 = (l0) -> {
			ModNetwork.sendToServer(new COpenAbilityTreeScreenPacket());
		};
		((java.util.Set)(var1)).add((Object)(new PlankButton(0,0,80,26,ModI18n.GUI_ABILITY_TREE,var4_1)));
		if (this.entityStatsProps.isPirate()){
			Button.OnPress var5_1 = (l0) -> {
			ModNetwork.sendToServer(new COpenCrewScreenPacket());
		};
			PlankButton var6_1 = new PlankButton(0,0,80,26,ModI18n.GUI_CREW,var5_1);
			if (!(this.hasCrew)){
				var6_1.active = false;
			}
			((java.util.Set)(var1)).add((Object)(var6_1));
		}
		if (this.hasChallengesConfigEnabled){
			int var5_1_1 = ((this.challengeAmount) > (0)) ? (1) : (0);
			Tooltip var6_2 = null;
			if ((var5_1_1) == (0)){
				var6_2 = Tooltip.create(ModI18n.GUI_NO_CHALLENGES_AVAILABLE);
			}else{
				if (this.isInChallengeDimension){
					var6_2 = Tooltip.create(ModI18n.GUI_CHALLENGE_DIM_ACCESS_MENU);
				}else{
					if (this.isInCombat){
						var6_2 = Tooltip.create(ModI18n.GUI_COMBAT_ACCESS_MENU);
					}
				}
			}
			Button.OnPress var7_1 = (l0) -> {
			ModNetwork.sendToServer(new COpenChallengesScreenPacket());
		};
			PlankButton var8 = new PlankButton(0,0,80,26,ModI18n.GUI_CHALLENGES,var7_1);
			var8.setTooltip(var6_2);
			if (((var5_1_1) != (0)) && ((!(this.isInCombat)) && (!(this.isInChallengeDimension)))){

			}else{
				var8.active = false;
			}
			((java.util.Set)(var1)).add((Object)(var8));
		}
		int var5_1_1 = ((this.width) / (2)) - (190);
		int var6_3 = ((this.height) - ((this.height) / (4))) - (10);
		int var7_2 = 0;
		Iterator var8_1 = var1.iterator();
		do{
			if (var8_1.hasNext()){
				Button var9 = ((Button)(var8_1.next()));
				var9.setX((var5_1_1) + ((var7_2) * (60)));
				var9.setY((var6_3) + (((var7_2) % (2)) * (30)));
				this.addRenderableWidget((GuiEventListener)(var9));
				var7_2++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public boolean isPauseScreen() {
		return false;
	}
	private void initLabels() {
		int var1 = this.entityStatsProps.getCola();
		int var2 = this.entityStatsProps.getMaxCola();
		String var3 = ": " + var1 + " / " + var2;
		MutableComponent var4 = Component.literal(var3).setStyle(Style.EMPTY.withBold(Boolean.valueOf(false)));
		this.colaLabel = ModI18n.GUI_COLA.copy().setStyle(Style.EMPTY.withBold(Boolean.valueOf(true))).append((Component)(var4));
		long var5 = Math.round(this.doriki);
		String var6 = ": " + var5;
		MutableComponent var7 = Component.literal(var6).setStyle(Style.EMPTY.withBold(Boolean.valueOf(false)));
		this.dorikiLabel = ModI18n.GUI_DORIKI.copy().setStyle(Style.EMPTY.withBold(Boolean.valueOf(true))).append((Component)(var7));
		Function var8 = (Function) ((Function<Enum, String>) ((l0) -> {
			String lv6_1 = ((IFactionRank)(l0)).getLocalizedName().getString();
			String lv6_2 = " - " + lv6_1;
			return lv6_2;
		}));
		String var9 = ((String)(this.entityStatsProps.getRank().map(var8).orElse("")));
		String var10 = ((Component)(this.entityStatsProps.getFaction().map((Function)(((Function<Faction, Component>)(Faction::getLabel)))).orElse(ModI18n.FACTION_EMPTY))).getString();
		String var11 = var9;
		String var12 = var10 + var11;
		String var13 = var12;
		String var14 = ((Component)(this.entityStatsProps.getRace().map((Function)(((Function<Race, Component>)(Race::getLabel)))).orElse(ModI18n.FACTION_EMPTY))).getString();
		String var15 = ((Component)(this.entityStatsProps.getFightingStyle().map((Function)(((Function<FightingStyle, Component>)(FightingStyle::getLabel)))).orElse(ModI18n.FACTION_EMPTY))).getString();
		String var16 = String.valueOf((Object)(ChatFormatting.BOLD));
		String var17 = ModI18n.GUI_FACTION.getString();
		String var18 = String.valueOf((Object)(ChatFormatting.RESET));
		String var19 = var13;
		String var20 = var16 + var17 + ": " + var18 + var19;
		this.factionLabel = Component.literal(var20);
		String var21 = String.valueOf((Object)(ChatFormatting.BOLD));
		String var22 = ModI18n.GUI_RACE.getString();
		String var23 = String.valueOf((Object)(ChatFormatting.RESET));
		String var24 = var14;
		String var25 = var21 + var22 + ": " + var23 + var24;
		this.raceLabel = Component.literal(var25);
		String var26 = String.valueOf((Object)(ChatFormatting.BOLD));
		String var27 = ModI18n.GUI_STYLE.getString();
		String var28 = String.valueOf((Object)(ChatFormatting.RESET));
		String var29 = var15;
		String var30 = var26 + var27 + ": " + var28 + var29;
		this.styleLabel = Component.literal(var30);
		long var31 = this.entityStatsProps.getBelly();
		String var32 = "" + var31;
		this.bellyLabel = Component.literal(var32);
		long var33 = this.entityStatsProps.getExtol();
		String var34 = "" + var33;
		this.extolLabel = Component.literal(var34);
	}
	private void drawItemStack(PoseStack var1, ItemStack var2, int var3, int var4, String var5) {
	}
	public static void open(double var0, boolean var1, int var2, boolean var3, int var4, boolean var5, boolean var6, int var7, boolean var8) {
		Minecraft.getInstance().setScreen((Screen)(new PlayerStatsScreen(var0,var1,var2,var3,var4,var5,var6,var7,var8)));
	}
}