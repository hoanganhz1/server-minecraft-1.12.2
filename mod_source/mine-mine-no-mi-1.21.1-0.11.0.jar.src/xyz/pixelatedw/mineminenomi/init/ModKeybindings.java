package xyz.pixelatedw.mineminenomi.init;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nKeybinds;

@EventBusSubscriber(modid="mineminenomi", value={Dist.CLIENT})
public class ModKeybindings {
	// Fields
	public static final KeyMapping TEST = new KeyMapping("Test Key",(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,80,ModI18nKeybinds.CATEGORY_GENERAL);
	public static final KeyMapping OPEN_PLAYER_MENU = new KeyMapping(ModI18nKeybinds.KEY_PLAYER,(IKeyConflictContext)(KeyConflictContext.UNIVERSAL),InputConstants.Type.KEYSYM,82,ModI18nKeybinds.CATEGORY_GENERAL);
	public static final KeyMapping OPEN_ABILITIES_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_ABILITIES,(IKeyConflictContext)(KeyConflictContext.UNIVERSAL),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_GENERAL);
	public static final KeyMapping OPEN_CHALLENGES_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_CHALLENGES,(IKeyConflictContext)(KeyConflictContext.UNIVERSAL),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_GENERAL);
	public static final KeyMapping OPEN_QUESTS_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_QUESTS,(IKeyConflictContext)(KeyConflictContext.UNIVERSAL),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_GENERAL);
	public static final KeyMapping OPEN_CREW_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_CREW,(IKeyConflictContext)(KeyConflictContext.UNIVERSAL),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_GENERAL);
	public static final KeyMapping OPEN_ABILITY_TREE_MENU = new KeyMapping(ModI18nKeybinds.KEY_OPEN_ABILITY_TREE,(IKeyConflictContext)(KeyConflictContext.UNIVERSAL),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_GENERAL);
	public static final KeyMapping VEHICLE_ALT_MODE = new KeyMapping(ModI18nKeybinds.KEY_VEHICLE_ALT_MODE,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,96,ModI18nKeybinds.CATEGORY_GENERAL);
	public static final KeyMapping COMBAT_MODE = new KeyMapping(ModI18nKeybinds.KEY_COMBATMODE,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,342,ModI18nKeybinds.CATEGORY_COMBATBAR);
	public static final KeyMapping NEXT_COMBAT_BAR = new KeyMapping(ModI18nKeybinds.KEY_NEXT_COMBAT_BAR,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,93,ModI18nKeybinds.CATEGORY_COMBATBAR);
	public static final KeyMapping PREV_COMBAT_BAR = new KeyMapping(ModI18nKeybinds.KEY_PREV_COMBAT_BAR,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,91,ModI18nKeybinds.CATEGORY_COMBATBAR);
	public static final KeyMapping LAST_COMBAT_BAR = new KeyMapping(ModI18nKeybinds.KEY_LAST_COMBAT_BAR,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
	public static final KeyMapping ABILITY_ALT_MODE = new KeyMapping(ModI18nKeybinds.KEY_CHANGE_ABILITY_MODE,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,96,ModI18nKeybinds.CATEGORY_COMBATBAR);
	public static final ArrayList<KeyMapping> COMBAT_BAR_KEYS = new ArrayList();
	public static final KeyMapping[] COMBAT_BAR_SHORTCUTS = new KeyMapping[10];
	public static final int[] PREVIOUS_INVENTORY_KEYBINDS;
	public static int serverMaxBars;

	@SubscribeEvent()
	public static void registerKeyMappings(RegisterKeyMappingsEvent var0) {
		var0.register(TEST);
		var0.register(OPEN_PLAYER_MENU);
		var0.register(OPEN_ABILITIES_MENU);
		var0.register(OPEN_CHALLENGES_MENU);
		var0.register(OPEN_QUESTS_MENU);
		var0.register(OPEN_CREW_MENU);
		var0.register(OPEN_ABILITY_TREE_MENU);
		var0.register(VEHICLE_ALT_MODE);
		var0.register(COMBAT_MODE);
		var0.register(NEXT_COMBAT_BAR);
		var0.register(PREV_COMBAT_BAR);
		var0.register(LAST_COMBAT_BAR);
		var0.register(ABILITY_ALT_MODE);
		Iterator var1 = COMBAT_BAR_KEYS.iterator();
		do{
			if (var1.hasNext()){
				var0.register(((KeyMapping)(var1.next())));
				continue;
			}else{
				break;
			}
		} while (true);
		KeyMapping[] var2 = COMBAT_BAR_SHORTCUTS;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				var0.register(var2[var4]);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	static  {
		int[] var0 = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		PREVIOUS_INVENTORY_KEYBINDS = var0;
		serverMaxBars = 2;
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT1,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,49,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT2,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,50,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT3,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,51,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT4,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,52,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT5,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,53,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT6,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,54,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT7,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,55,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR1_COMBATSLOT8,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,56,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT1,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT2,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT3,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT4,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT5,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT6,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT7,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR2_COMBATSLOT8,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT1,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT2,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT3,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT4,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT5,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT6,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT7,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_KEYS.add(new KeyMapping(ModI18nKeybinds.KEY_BAR3_COMBATSLOT8,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR));
		COMBAT_BAR_SHORTCUTS[0] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT1,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
		COMBAT_BAR_SHORTCUTS[1] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT2,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
		COMBAT_BAR_SHORTCUTS[2] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT3,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
		COMBAT_BAR_SHORTCUTS[3] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT4,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
		COMBAT_BAR_SHORTCUTS[4] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT5,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
		COMBAT_BAR_SHORTCUTS[5] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT6,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
		COMBAT_BAR_SHORTCUTS[6] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT7,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
		COMBAT_BAR_SHORTCUTS[7] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT8,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
		COMBAT_BAR_SHORTCUTS[8] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT9,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
		COMBAT_BAR_SHORTCUTS[9] = new KeyMapping(ModI18nKeybinds.KEY_BAR_SHORTCUT10,(IKeyConflictContext)(KeyConflictContext.IN_GAME),InputConstants.Type.KEYSYM,-1,ModI18nKeybinds.CATEGORY_COMBATBAR);
	}
}