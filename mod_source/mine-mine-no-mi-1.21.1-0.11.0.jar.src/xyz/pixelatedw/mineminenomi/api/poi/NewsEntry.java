package xyz.pixelatedw.mineminenomi.api.poi;

import java.util.Random;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;

public class NewsEntry {
	// Fields
	private static final String[] PIRATE_NEWS;
	private static final String[] MARINE_NEWS;
	private final TrackedNPC tracked;
	private Component message;

	private NewsEntry(TrackedNPC var1) {
		this.tracked = var1;
	}
	public static NewsEntry createNewsFor(TrackedNPC var0, Level var1) {
		int var5 = 0;
		NewsEntry var2 = new NewsEntry(var0);
		NotoriousEntity var3 = var0.createTrackedMob(var1);
		if (var0.getFaction().equals(ModFactions.PIRATE.get())){
			long var4 = var0.getBounty();
			var5 = (100) + (var0.getRandom().nextInt(255));
			int var6 = (2) + (var0.getRandom().nextInt(10));
			int var7 = var0.getRandom().nextInt(PIRATE_NEWS.length);
			Object[] var8 = new Object[]{var3.getDisplayName().getString()};
			Object[] var9 = var8;
			switch (var7){
			case 0:

			case 2:

			case 6:
				Object[] var10 = new Object[]{var3.getDisplayName().getString(),Integer.valueOf(var6)};
				var9 = var10;
				break;
			case 1:

			case 3:
				Object[] var11 = new Object[]{var3.getDisplayName().getString(),Long.valueOf(var4)};
				var9 = var11;
				break;
			case 5:
				Object[] var12 = new Object[]{var3.getDisplayName().getString(),Integer.valueOf(var5)};
				var9 = var12;
				break;
			case 4:
				break;
			}
			var2.message = Component.translatable(PIRATE_NEWS[var7],var9);
		}else{
			if (var0.getFaction().equals(ModFactions.MARINE.get())){
				int var4_1 = (50) + (var0.getRandom().nextInt(200));
				int var5_1 = (2) + (var0.getRandom().nextInt(10));
				var5 = var0.getRandom().nextInt(MARINE_NEWS.length);
				Object[] var7_1 = new Object[]{var3.getDisplayName().getString()};
				Object[] var8_1 = var7_1;
				switch (var5){
				case 0:

				case 3:

				case 4:
					Object[] var9_1 = new Object[]{var3.getDisplayName().getString(),Integer.valueOf(var5_1)};
					var8_1 = var9_1;
					break;
				case 1:

				case 2:
					Object[] var10_1 = new Object[]{var3.getDisplayName().getString(),Integer.valueOf(var4_1)};
					var8_1 = var10_1;
					break;
				}
				var2.message = Component.translatable(MARINE_NEWS[var5],var8_1);
			}
		}
		return var2;
	}
	public Component getMessage() {
		return this.message;
	}
	static  {
		String[] var0 = new String[]{ModI18nInteractions.PIRATE_NEWS_1_MESSAGE,ModI18nInteractions.PIRATE_NEWS_2_MESSAGE,ModI18nInteractions.PIRATE_NEWS_3_MESSAGE,ModI18nInteractions.PIRATE_NEWS_4_MESSAGE,ModI18nInteractions.PIRATE_NEWS_5_MESSAGE,ModI18nInteractions.PIRATE_NEWS_6_MESSAGE,ModI18nInteractions.PIRATE_NEWS_7_MESSAGE};
		PIRATE_NEWS = var0;
		String[] var1 = new String[]{ModI18nInteractions.MARINE_NEWS_1_MESSAGE,ModI18nInteractions.MARINE_NEWS_2_MESSAGE,ModI18nInteractions.MARINE_NEWS_3_MESSAGE,ModI18nInteractions.MARINE_NEWS_4_MESSAGE,ModI18nInteractions.MARINE_NEWS_5_MESSAGE,ModI18nInteractions.MARINE_NEWS_6_MESSAGE};
		MARINE_NEWS = var1;
	}
}