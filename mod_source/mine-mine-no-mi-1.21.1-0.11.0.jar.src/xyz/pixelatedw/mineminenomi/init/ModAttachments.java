package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Function;
import java.util.function.Supplier;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationData;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.data.entity.CarryData;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.CombatData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.entity.HisoLookoutData;
import xyz.pixelatedw.mineminenomi.data.entity.KairosekiCoatingData;
import xyz.pixelatedw.mineminenomi.data.entity.NBTGoalsData;
import xyz.pixelatedw.mineminenomi.data.entity.ProjectileData;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;

public class ModAttachments {
	// Fields
	public static final Supplier<AttachmentType<EntityStatsData>> STATS;
	public static final Supplier<AttachmentType<AbilityData>> ABILITY;
	public static final Supplier<AttachmentType<DevilFruitData>> DEVIL_FRUIT;
	public static final Supplier<AttachmentType<CameraLockData>> CAMERA_LOCK;
	public static final Supplier<AttachmentType<AnimationData>> ANIMATION;
	public static final Supplier<AttachmentType<HakiData>> HAKI;
	public static final Supplier<AttachmentType<ProjectileData>> PROJECTILE;
	public static final Supplier<AttachmentType<ChallengeData>> CHALLENGE;
	public static final Supplier<AttachmentType<CombatData>> COMBAT;
	public static final Supplier<AttachmentType<CarryData>> CARRY;
	public static final Supplier<AttachmentType<KairosekiCoatingData>> KAIROSEKI_COATING;
	public static final Supplier<AttachmentType<NBTGoalsData>> NBT_GOALS;
	public static final Supplier<AttachmentType<QuestData>> QUEST;
	public static final Supplier<AttachmentType<HisoLookoutData>> HISO_LOOKOUT;

	public static void init() {
	}
	static  {
		Supplier<AttachmentType> var0 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, EntityStatsData>)(EntityStatsData::createCapability)))).copyOnDeath().build();
		};
		STATS = ModRegistry.registerAttachment("stats",(Supplier)(var0));
		Supplier<AttachmentType> var1 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, AbilityData>)(AbilityData::createCapability)))).copyOnDeath().build();
		};
		ABILITY = ModRegistry.registerAttachment("ability",(Supplier)(var1));
		Supplier<AttachmentType> var2 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, DevilFruitData>)(DevilFruitData::createCapability)))).copyOnDeath().build();
		};
		DEVIL_FRUIT = ModRegistry.registerAttachment("devil_fruit",(Supplier)(var2));
		Supplier<AttachmentType> var3 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, CameraLockData>)(CameraLockData::createCapability)))).build();
		};
		CAMERA_LOCK = ModRegistry.registerAttachment("camera_lock",(Supplier)(var3));
		Supplier<AttachmentType> var4 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, AnimationData>)(AnimationData::createCapability)))).build();
		};
		ANIMATION = ModRegistry.registerAttachment("animation",(Supplier)(var4));
		Supplier<AttachmentType> var5 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, HakiData>)(HakiData::createCapability)))).copyOnDeath().build();
		};
		HAKI = ModRegistry.registerAttachment("haki",(Supplier)(var5));
		Supplier<AttachmentType> var6 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, ProjectileData>)(ProjectileData::createCapability)))).build();
		};
		PROJECTILE = ModRegistry.registerAttachment("projectile",(Supplier)(var6));
		Supplier<AttachmentType> var7 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, ChallengeData>)(ChallengeData::createCapability)))).copyOnDeath().build();
		};
		CHALLENGE = ModRegistry.registerAttachment("challenges",(Supplier)(var7));
		Supplier<AttachmentType> var8 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, CombatData>)(CombatData::createCapability)))).build();
		};
		COMBAT = ModRegistry.registerAttachment("combat",(Supplier)(var8));
		Supplier<AttachmentType> var9 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, CarryData>)(CarryData::createCapability)))).build();
		};
		CARRY = ModRegistry.registerAttachment("carry",(Supplier)(var9));
		Supplier<AttachmentType> var10 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, KairosekiCoatingData>)(KairosekiCoatingData::createCapability)))).build();
		};
		KAIROSEKI_COATING = ModRegistry.registerAttachment("kairoseki_coating",(Supplier)(var10));
		Supplier<AttachmentType> var11 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, NBTGoalsData>)(NBTGoalsData::createCapability)))).build();
		};
		NBT_GOALS = ModRegistry.registerAttachment("nbt_goals",(Supplier)(var11));
		Supplier<AttachmentType> var12 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, QuestData>)(QuestData::createCapability)))).copyOnDeath().build();
		};
		QUEST = ModRegistry.registerAttachment("quests",(Supplier)(var12));
		Supplier<AttachmentType> var13 = () -> {
			return AttachmentType.serializable((Function)(((Function<IAttachmentHolder, HisoLookoutData>)(HisoLookoutData::createCapability)))).copyOnDeath().build();
		};
		HISO_LOOKOUT = ModRegistry.registerAttachment("hiso_lookout",(Supplier)(var13));
	}
}