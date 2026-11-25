package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.PartEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.entities.PartEntityType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WyRegistry$Keys {
	// Fields
	public static final ResourceKey<Registry<AbilityCore<? extends IAbility>>> ABILITIES = key("abilities");
	public static final ResourceKey<Registry<AbilityComponentKey<? extends AbilityComponent<?>>>> ABILITY_COMPONENTS = key("ability_components");
	public static final ResourceKey<Registry<JollyRogerElement>> JOLLY_ROGER_ELEMENTS = key("jolly_roger_elements");
	public static final ResourceKey<Registry<ChallengeCore<?>>> CHALLENGES = key("challenges");
	public static final ResourceKey<Registry<MorphInfo>> MORPHS = key("morphs");
	public static final ResourceKey<Registry<ParticleEffect<?>>> PARTICLE_EFFECTS = key("particle_effects");
	public static final ResourceKey<Registry<QuestId<?>>> QUESTS = key("quests");
	public static final ResourceKey<Registry<Faction>> FACTIONS = key("factions");
	public static final ResourceKey<Registry<FightingStyle>> FIGHTING_STYLES = key("fighting_styles");
	public static final ResourceKey<Registry<Race>> RACES = key("races");
	public static final ResourceKey<Registry<PartEntityType<? extends PartEntity<? extends Entity>, ? extends Entity>>> PART_ENTITY_TYPES = key("part_entity_types");

	private static <T> ResourceKey<Registry<T>> key(String var0) {
		return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
}