package xyz.pixelatedw.mineminenomi.datagen.advancements;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.PearlFireAbility;
import xyz.pixelatedw.mineminenomi.abilities.ShakushiAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.BreezeBreathBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleChampionAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsMoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuTaihoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress1Ability;
import xyz.pixelatedw.mineminenomi.abilities.mandemontactics.DemonicDanceAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.GrandeSablesAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SablesAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SpiralHollowAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.data.triggers.CompleteChallengeTrigger$TriggerInstance;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockChallengeTrigger$TriggerInstance;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAdvancements;
import xyz.pixelatedw.mineminenomi.items.AbilityIconItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class ChallengeAdvancements implements AdvancementProvider.AdvancementGenerator {
	// Fields
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("textures/gui/advancements/backgrounds/adventure.png");
	private ExistingFileHelper fileHelper;

	public ChallengeAdvancements(ExistingFileHelper var1) {
		this.fileHelper = var1;
	}
	public void generate(HolderLookup.Provider var1, Consumer<AdvancementHolder> var2, ExistingFileHelper var3) {
		AdvancementHolder var4 = Advancement.Builder.advancement().display(((ItemLike)(ModBlocks.PONEGLYPH.get())),ModI18nAdvancements.CHALLENGES_ROOT.title(),ModI18nAdvancements.CHALLENGES_ROOT.description(),BACKGROUND,AdvancementType.TASK,false,false,false).addCriterion("unlock_challenge",UnlockChallengeTrigger$TriggerInstance.unlockAnyChallenge()).save(var2,ResourceLocation.fromNamespaceAndPath("mineminenomi","challenges/root"),this.fileHelper);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.HIGUMA.get())),((ModSwordItem)(ModWeapons.CUTLASS.get())).getDefaultInstance(),var2),((ChallengeCore)(ModChallenges.HIGUMA_HARD.get())),ShiShishiSonsonAbility.INSTANCE,var2);
		ItemStack var5 = new ItemStack(((ItemLike)(ModWeapons.AXE.get())));
		int[] var6 = new int[]{33980};
		var5.set(ModDataComponents.MULTI_CHANNEL_DYES,new MultiChannelItemColor(var6));
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.MORGAN.get())),var5,var2),((ChallengeCore)(ModChallenges.MORGAN_HARD.get())),TackleAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.KURO.get())),((Item)(ModArmors.KURO_CHEST.get())).getDefaultInstance(),var2),((ChallengeCore)(ModChallenges.KURO_HARD.get())),ShakushiAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.NYANBAN_BROTHERS.get())),StealPunchAbility.INSTANCE,var2),((ChallengeCore)(ModChallenges.NYANBAN_BROTHERS_HARD.get())),BellyFlopAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.DON_KRIEG.get())),((Item)(ModArmors.MH5_GAS_MASK.get())).getDefaultInstance(),var2),((ChallengeCore)(ModChallenges.DON_KRIEG_HARD.get())),((ModSwordItem)(ModWeapons.DAISENSO.get())).getDefaultInstance(),var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.GIN.get())),((ModSwordItem)(ModWeapons.TONFA.get())).getDefaultInstance(),var2),((ChallengeCore)(ModChallenges.GIN_HARD.get())),DemonicDanceAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.PEARL.get())),((Item)(ModArmors.PEARL_HAT.get())).getDefaultInstance(),var2),((ChallengeCore)(ModChallenges.PEARL_HARD.get())),PearlFireAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.ARLONG.get())),((Item)(ModArmors.ARLONG_CHEST.get())).getDefaultInstance(),var2),((ChallengeCore)(ModChallenges.ARLONG_HARD.get())),((ModSwordItem)(ModWeapons.KIRIBACHI.get())).getDefaultInstance(),var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.KUROOBI.get())),((Item)(ModArmors.KUROOBI_CHEST.get())).getDefaultInstance(),var2),((ChallengeCore)(ModChallenges.KUROOBI_HARD.get())),KarakusagawaraSeikenAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.CHEW.get())),((Item)(ModArmors.CHEW_CHEST.get())).getDefaultInstance(),var2),((ChallengeCore)(ModChallenges.CHEW_HARD.get())),MizuTaihoAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.MR_0.get())),SablesAbility.INSTANCE,var2),((ChallengeCore)(ModChallenges.MR_0_HARD.get())),GrandeSablesAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.MR_1.get())),SparClawAbility.INSTANCE,var2),((ChallengeCore)(ModChallenges.MR_1_HARD.get())),SpiralHollowAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.MR_3.get())),DoruDoruArtsMoriAbility.INSTANCE,var2),((ChallengeCore)(ModChallenges.MR_3_HARD.get())),CandleChampionAbility.INSTANCE,var2);
		this.hard(this.standard(var4,((ChallengeCore)(ModChallenges.MR_5_AND_MISS_VALENTINE.get())),KiloPress1Ability.INSTANCE,var2),((ChallengeCore)(ModChallenges.MR_5_AND_MISS_VALENTINE_HARD.get())),BreezeBreathBombAbility.INSTANCE,var2);
	}
	private AdvancementHolder standard(AdvancementHolder var1, ChallengeCore<?> var2, ItemStack var3, Consumer<AdvancementHolder> var4) {
		return this.challenge(var1,var2,new DisplayInfo(var3,var2.getLocalizedTitle(),var2.getLocalizedObjective(),Optional.empty(),AdvancementType.TASK,true,false,true),var4);
	}
	private AdvancementHolder standard(AdvancementHolder var1, ChallengeCore<?> var2, DeferredHolder<AbilityCore<?>, ? extends AbilityCore<?>> var3, Consumer<AdvancementHolder> var4) {
		ItemStack var5 = AbilityIconItem.getIcon(var3);
		return this.challenge(var1,var2,new DisplayInfo(var5,var2.getLocalizedTitle(),var2.getLocalizedObjective(),Optional.empty(),AdvancementType.TASK,true,false,true),var4);
	}
	private AdvancementHolder hard(AdvancementHolder var1, ChallengeCore<?> var2, ItemStack var3, Consumer<AdvancementHolder> var4) {
		return this.challenge(var1,var2,new DisplayInfo(var3.copy(),var2.getLocalizedTitle(),var2.getLocalizedObjective(),Optional.empty(),AdvancementType.CHALLENGE,true,true,false),var4);
	}
	private AdvancementHolder hard(AdvancementHolder var1, ChallengeCore<?> var2, DeferredHolder<AbilityCore<?>, ? extends AbilityCore<?>> var3, Consumer<AdvancementHolder> var4) {
		ItemStack var5 = AbilityIconItem.getIcon(var3);
		return this.challenge(var1,var2,new DisplayInfo(var5,var2.getLocalizedTitle(),var2.getLocalizedObjective(),Optional.empty(),AdvancementType.CHALLENGE,true,true,false),var4);
	}
	private AdvancementHolder challenge(AdvancementHolder var1, ChallengeCore<?> var2, DisplayInfo var3, Consumer<AdvancementHolder> var4) {
		String var5 = var2.getId();
		String var6 = "challenges/" + var5;
		return Advancement.Builder.advancement().parent(var1).display(var3).addCriterion("complete_challenge",CompleteChallengeTrigger$TriggerInstance.completeChallenge(var2)).save(var4,ResourceLocation.fromNamespaceAndPath("mineminenomi",var6),this.fileHelper);
	}
}