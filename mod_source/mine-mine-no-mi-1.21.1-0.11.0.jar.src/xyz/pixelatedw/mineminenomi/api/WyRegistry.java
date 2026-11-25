package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.registries.RegistryBuilder;
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

public class WyRegistry {
	// Fields
	public static final Registry<AbilityCore<? extends IAbility>> ABILITIES = new RegistryBuilder(WyRegistry$Keys.ABILITIES).create();
	public static final Registry<AbilityComponentKey<? extends AbilityComponent<?>>> ABILITY_COMPONENTS = new RegistryBuilder(WyRegistry$Keys.ABILITY_COMPONENTS).create();
	public static final Registry<JollyRogerElement> JOLLY_ROGER_ELEMENTS = new RegistryBuilder(WyRegistry$Keys.JOLLY_ROGER_ELEMENTS).create();
	public static final Registry<ChallengeCore<?>> CHALLENGES = new RegistryBuilder(WyRegistry$Keys.CHALLENGES).create();
	public static final Registry<MorphInfo> MORPHS = new RegistryBuilder(WyRegistry$Keys.MORPHS).create();
	public static final Registry<ParticleEffect<?>> PARTICLE_EFFECTS = new RegistryBuilder(WyRegistry$Keys.PARTICLE_EFFECTS).create();
	public static final Registry<QuestId<?>> QUESTS = new RegistryBuilder(WyRegistry$Keys.QUESTS).create();
	public static final Registry<Faction> FACTIONS = new RegistryBuilder(WyRegistry$Keys.FACTIONS).create();
	public static final Registry<FightingStyle> FIGHTING_STYLES = new RegistryBuilder(WyRegistry$Keys.FIGHTING_STYLES).create();
	public static final Registry<Race> RACES = new RegistryBuilder(WyRegistry$Keys.RACES).create();
	public static final Registry<PartEntityType<? extends PartEntity<? extends Entity>, ? extends Entity>> PART_ENTITY_TYPES = new RegistryBuilder(WyRegistry$Keys.PART_ENTITY_TYPES).create();
}