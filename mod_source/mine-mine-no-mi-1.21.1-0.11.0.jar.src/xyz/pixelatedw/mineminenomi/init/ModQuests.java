package xyz.pixelatedw.mineminenomi.init;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.Test2Quest;
import xyz.pixelatedw.mineminenomi.quests.TestQuest;
import xyz.pixelatedw.mineminenomi.quests.accuracy.AdvancedBallisticsQuest;
import xyz.pixelatedw.mineminenomi.quests.accuracy.ControlledFireQuest;
import xyz.pixelatedw.mineminenomi.quests.accuracy.PrecisionPursuitQuest;
import xyz.pixelatedw.mineminenomi.quests.accuracy.SteadyAimQuest;
import xyz.pixelatedw.mineminenomi.quests.accuracy.TargetTransitionDrillQuest;
import xyz.pixelatedw.mineminenomi.quests.martialarts.AerialTechniqueQuest;
import xyz.pixelatedw.mineminenomi.quests.martialarts.CloseQuartersDrillQuest;
import xyz.pixelatedw.mineminenomi.quests.martialarts.FinisherDrillQuest;
import xyz.pixelatedw.mineminenomi.quests.martialarts.PressureTrainingQuest;
import xyz.pixelatedw.mineminenomi.quests.martialarts.UntouchableFormQuest;
import xyz.pixelatedw.mineminenomi.quests.technology.AppliedKnowledgeQuest;
import xyz.pixelatedw.mineminenomi.quests.technology.EfficientDeploymentQuest;
import xyz.pixelatedw.mineminenomi.quests.technology.SpecialistControlQuest;
import xyz.pixelatedw.mineminenomi.quests.technology.TechnicalMasteryQuest;
import xyz.pixelatedw.mineminenomi.quests.technology.ToolFamiliarityQuest;
import xyz.pixelatedw.mineminenomi.quests.weaponmastery.MixedArsenalQuest;
import xyz.pixelatedw.mineminenomi.quests.weaponmastery.ProperFormQuest;
import xyz.pixelatedw.mineminenomi.quests.weaponmastery.WeaponDisciplineQuest;
import xyz.pixelatedw.mineminenomi.quests.weaponmastery.WeaponVersatilityQuest;
import xyz.pixelatedw.mineminenomi.quests.weaponmastery.WeightedStrikesQuest;

public class ModQuests {
	// Fields
	public static final DeferredHolder<QuestId<?>, QuestId<TestQuest>> TEST;
	public static final DeferredHolder<QuestId<?>, QuestId<Test2Quest>> TEST2;
	public static final List<DeferredHolder<QuestId<?>, ? extends QuestId<? extends Quest>>> ACCURACY_QUESTS;
	public static final List<DeferredHolder<QuestId<?>, ? extends QuestId<? extends Quest>>> MARTIAL_ARTS_QUESTS;
	public static final List<DeferredHolder<QuestId<?>, ? extends QuestId<? extends Quest>>> TECHNOLOGY_QUESTS;
	public static final List<DeferredHolder<QuestId<?>, ? extends QuestId<? extends Quest>>> WEAPON_MASTERY_QUESTS;

	public static void init() {
	}
	static  {
		Supplier var0 = (Supplier) ((Supplier<QuestId>) (() -> {
			return TestQuest.INSTANCE;
		}));
		TEST = (DeferredHolder) (ModRegistry.registerQuest("test","Testin",var0));
		Supplier var1 = (Supplier) ((Supplier<QuestId>) (() -> {
			return Test2Quest.INSTANCE;
		}));
		TEST2 = (DeferredHolder) (ModRegistry.registerQuest("test2","Testin2",var1));
		DeferredHolder[] var2 = new DeferredHolder[]{SteadyAimQuest.INSTANCE,ControlledFireQuest.INSTANCE,TargetTransitionDrillQuest.INSTANCE,AdvancedBallisticsQuest.INSTANCE,PrecisionPursuitQuest.INSTANCE};
		ACCURACY_QUESTS = (List) (Arrays.asList(var2));
		DeferredHolder[] var3 = new DeferredHolder[]{CloseQuartersDrillQuest.INSTANCE,PressureTrainingQuest.INSTANCE,UntouchableFormQuest.INSTANCE,AerialTechniqueQuest.INSTANCE,FinisherDrillQuest.INSTANCE};
		MARTIAL_ARTS_QUESTS = (List) (Arrays.asList(var3));
		DeferredHolder[] var4 = new DeferredHolder[]{ToolFamiliarityQuest.INSTANCE,AppliedKnowledgeQuest.INSTANCE,EfficientDeploymentQuest.INSTANCE,SpecialistControlQuest.INSTANCE,TechnicalMasteryQuest.INSTANCE};
		TECHNOLOGY_QUESTS = (List) (Arrays.asList(var4));
		DeferredHolder[] var5 = new DeferredHolder[]{ProperFormQuest.INSTANCE,WeaponDisciplineQuest.INSTANCE,WeightedStrikesQuest.INSTANCE,MixedArsenalQuest.INSTANCE,WeaponVersatilityQuest.INSTANCE};
		WEAPON_MASTERY_QUESTS = (List) (Arrays.asList(var5));
	}
}