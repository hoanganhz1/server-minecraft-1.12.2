package xyz.pixelatedw.mineminenomi.items;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.HaoshokuHakiInfusionAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress1Ability;
import xyz.pixelatedw.mineminenomi.abilities.mandemontactics.DemonicDanceAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.GrandeSablesAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SablesAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SpiralHollowAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class AbilityIconItem extends Item {
	// Fields
	private static int id;
	public static final Map<ResourceLocation, Integer> ICONS;

	private static Map<ResourceLocation, Integer> createMap() {
		HashMap var0 = new HashMap();
		int var1 = 0;
		var0.put(BusoshokuHakiHardeningAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(HaoshokuHakiInfusionAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(ShiShishiSonsonAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(TackleAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(ShakushiAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(StealPunchAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(BellyFlopAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(DemonicDanceAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(PearlFireAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(KarakusagawaraSeikenAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(MizuTaihoAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(SablesAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(GrandeSablesAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(SparClawAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(SpiralHollowAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(DoruDoruArtsMoriAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(CandleChampionAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(KiloPress1Ability.INSTANCE.getId(),Integer.valueOf(++(var1)));
		var0.put(BreezeBreathBombAbility.INSTANCE.getId(),Integer.valueOf(++(var1)));
		return var0;
	}
	public AbilityIconItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public static ItemStack getIcon(DeferredHolder<AbilityCore<?>, ? extends AbilityCore<?>> var0) {
		ItemStack var1 = ((AbilityIconItem)(ModItems.ABILITY_ICON.get())).getDefaultInstance();
		try{
			if (!(ICONS.containsKey(var0.getId()))){
				String var2 = String.valueOf((Object)(var0.getId()));
				String var3 = "Missing AbilityIcon id: " + var2;
				throw new RuntimeException((String)(var3));
			}else{
				var1.set(ModDataComponents.ABILITY_ICON,var0.getId());
			}
		}catch(Exception var2_1){
			var2_1.printStackTrace();
		}
		return var1;
	}
	static  {
		id = 0;
		ICONS = createMap();
	}
}