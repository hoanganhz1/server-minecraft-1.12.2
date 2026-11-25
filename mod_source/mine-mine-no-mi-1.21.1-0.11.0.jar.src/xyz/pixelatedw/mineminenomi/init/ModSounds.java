package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModSounds {
	// Fields
	public static final Supplier<SoundEvent> EL_THOR_SFX = ModRegistry.registerSound("El Thor");
	public static final Supplier<SoundEvent> MERA_SFX = ModRegistry.registerSound("Mera");
	public static final Supplier<SoundEvent> HAKI_RELEASE_SFX = ModRegistry.registerSound("Haki Release");
	public static final Supplier<SoundEvent> DASH_ABILITY_SWOOSH_SFX = ModRegistry.registerSound("Dash Ability Swoosh");
	public static final Supplier<SoundEvent> NORO_SFX = ModRegistry.registerSound("Noro");
	public static final Supplier<SoundEvent> PIKA_SFX = ModRegistry.registerSound("Pika");
	public static final Supplier<SoundEvent> PIKA_CHARGE_SFX = ModRegistry.registerSound("Pika Charge");
	public static final Supplier<SoundEvent> MAGU_SFX = ModRegistry.registerSound("Magu");
	public static final Supplier<SoundEvent> TELEPORT_SFX = ModRegistry.registerSound("Teleport");
	public static final Supplier<SoundEvent> LONG_ELECTRIC_DISCHARGE_SFX = ModRegistry.registerSound("Long Electric Discharge");
	public static final Supplier<SoundEvent> LIGHTNING_TELEPORT = ModRegistry.registerSound("Lightning Teleport");
	public static final Supplier<SoundEvent> HEART_BEAT_SFX = ModRegistry.registerSound("Heart Beat");
	public static final Supplier<SoundEvent> DEATH_WINK_SFX = ModRegistry.registerSound("Death Wink");
	public static final Supplier<SoundEvent> DOA_IN_SFX = ModRegistry.registerSound("Doa In");
	public static final Supplier<SoundEvent> DOA_OUT_SFX = ModRegistry.registerSound("Doa Out");
	public static final Supplier<SoundEvent> HANA_SFX = ModRegistry.registerSound("Hana");
	public static final Supplier<SoundEvent> SNAP_SFX = ModRegistry.registerSound("Snap");
	public static final Supplier<SoundEvent> GOMU_SFX = ModRegistry.registerSound("Gomu");
	public static final Supplier<SoundEvent> GEAR_SECOND_SFX = ModRegistry.registerSound("Gear Second");
	public static final Supplier<SoundEvent> SPRING_SFX = ModRegistry.registerSound("Spring");
	public static final Supplier<SoundEvent> GEPPO_SFX = ModRegistry.registerSound("Geppo");
	public static final Supplier<SoundEvent> SHIGAN_SFX = ModRegistry.registerSound("Shigan");
	public static final Supplier<SoundEvent> BUSOSHOKU_HAKI_SFX = ModRegistry.registerSound("Busoshoku Haki");
	public static final Supplier<SoundEvent> KENBUNSHOKU_HAKI_ON_SFX = ModRegistry.registerSound("Kenbunshoku Haki On");
	public static final Supplier<SoundEvent> ROOM_CREATE_SFX = ModRegistry.registerSound("Room Create");
	public static final Supplier<SoundEvent> ROOM_CHARGE_SFX = ModRegistry.registerSound("Room Charge");
	public static final Supplier<SoundEvent> ROOM_EXPAND_SFX = ModRegistry.registerSound("Room Expand");
	public static final Supplier<SoundEvent> ROOM_CLOSE_SFX = ModRegistry.registerSound("Room Close");
	public static final Supplier<SoundEvent> GURA_SFX = ModRegistry.registerSound("Gura");
	public static final Supplier<SoundEvent> URSUS_SHOCK_SFX = ModRegistry.registerSound("Ursus Shock");
	public static final Supplier<SoundEvent> ICE_AGE_SFX = ModRegistry.registerSound("Ice Age");
	public static final Supplier<SoundEvent> SABLES_PESADO_SFX = ModRegistry.registerSound("Sables Pesado");
	public static final Supplier<SoundEvent> DESERT_SPADA_SFX = ModRegistry.registerSound("Desert Spada");
	public static final Supplier<SoundEvent> PAD_HO_SFX = ModRegistry.registerSound("Pad Ho");
	public static final Supplier<SoundEvent> MULTIPLE_PAD_HO_SFX = ModRegistry.registerSound("Multiple Pad Ho");
	public static final Supplier<SoundEvent> PRE_CYBORG_BEAM_SFX = ModRegistry.registerSound("Pre Cyborg Beam");
	public static final Supplier<SoundEvent> CHARGE_CYBORG_BEAM_SFX = ModRegistry.registerSound("Charge Cyborg Beam");
	public static final Supplier<SoundEvent> CYBORG_BEAM_SFX = ModRegistry.registerSound("Cyborg Beam");
	public static final Supplier<SoundEvent> DRUM_HIT_SFX = ModRegistry.registerSound("Drum Hit");
	public static final Supplier<SoundEvent> SHORT_DISCHARGE_SFX = ModRegistry.registerSound("Short Discharge");
	public static final Supplier<SoundEvent> MANE_SWITCH = ModRegistry.registerSound("Mane Switch");
	public static final Supplier<SoundEvent> GUARD = ModRegistry.registerSound("Guard");
	public static final Supplier<SoundEvent> HAKI_GUARD = ModRegistry.registerSound("Guard Haki");
	public static final Supplier<SoundEvent> SHAN = ModRegistry.registerSound("Shan");
	public static final Supplier<SoundEvent> DON = ModRegistry.registerSound("Don");
	public static final Supplier<SoundEvent> BON = ModRegistry.registerSound("Bon");
	public static final Supplier<SoundEvent> ROKUOGAN = ModRegistry.registerSound("Rokuogan");
	public static final Supplier<SoundEvent> KENBUNSHOKU_HAKI_OFF = ModRegistry.registerSound("Kenbunshoku Haki Off");
	public static final Supplier<SoundEvent> FUTURE_SIGHT_HIT = ModRegistry.registerSound("Future Sight Hit");
	public static final Supplier<SoundEvent> DODGE_1 = ModRegistry.registerSound("Dodge 1");
	public static final Supplier<SoundEvent> DODGE_2 = ModRegistry.registerSound("Dodge 2");
	public static final Supplier<SoundEvent> BOUNCE_1 = ModRegistry.registerSound("Bounce 1");
	public static final Supplier<SoundEvent> BOUNCE_2 = ModRegistry.registerSound("Bounce 2");
	public static final Supplier<SoundEvent> DRUMS_OF_LIBERATION_1 = ModRegistry.registerSound("Drums of Liberation 1");
	public static final Supplier<SoundEvent> DRUMS_OF_LIBERATION_2 = ModRegistry.registerSound("Drums of Liberation 2");
	public static final Supplier<SoundEvent> FRUIT_REINCARNATION = ModRegistry.registerSound("Fruit Reincarnation");
	public static final Supplier<SoundEvent> BAKURETSU_KAZAN = ModRegistry.registerSound("Bakuretsu Kazan");
	public static final Supplier<SoundEvent> SPIN = ModRegistry.registerSound("Spin");
	public static final Supplier<SoundEvent> GUN_LOAD = ModRegistry.registerSound("Gun Load");
	public static final Supplier<SoundEvent> PISTOL_SHOOT = ModRegistry.registerSound("Pistol Shoot");
	public static final Supplier<SoundEvent> SNIPER_SHOOT = ModRegistry.registerSound("Sniper Shoot");
	public static final Supplier<SoundEvent> GENERIC_EXPLOSION = ModRegistry.registerSound("Generic Explosion");
	public static final Supplier<SoundEvent> GENERIC_EXPLOSION_SHORT = ModRegistry.registerSound("Generic Explosion Short");
	public static final Supplier<SoundEvent> BIG_DUCK_QUACK = ModRegistry.registerSound("Duck Quack");
	public static final Supplier<SoundEvent> BIG_DUCK_HURT = ModRegistry.registerSound("Duck Hurt");
	public static final Supplier<SoundEvent> CROCODILE_LAUGH = ModRegistry.registerSound("Crocodile Laugh");
	public static final Supplier<SoundEvent> HONK = ModRegistry.registerSound("Honk");

	public static void init() {
	}
}