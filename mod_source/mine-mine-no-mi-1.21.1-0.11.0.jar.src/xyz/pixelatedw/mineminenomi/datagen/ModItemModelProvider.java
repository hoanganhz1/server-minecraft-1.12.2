package xyz.pixelatedw.mineminenomi.datagen;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModModelOverrides;
import xyz.pixelatedw.mineminenomi.items.AbilityIconItem;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput var1, ExistingFileHelper var2) {
		super(var1,"mineminenomi",var2);
	}
	protected void registerModels() {
		Supplier[] var1 = new Supplier[]{ModItems.SHADOW};
		this.generateBasicItems(var1);
		this.generateAbilityIcons();
	}
	private void generateAbilityIcons() {
		Iterator var1 = AbilityIconItem.ICONS.entrySet().iterator();
		do{
			if (var1.hasNext()){
				Map.Entry var2 = ((Map.Entry)(var1.next()));
				String var3 = ((ResourceLocation)(var2.getKey())).getPath();
				String var4 = "item/ability_icons/" + var3;
				ItemModelBuilder var5 = ((ItemModelBuilder)(((ItemModelBuilder)(this.getBuilder(ResourceLocation.fromNamespaceAndPath("mineminenomi",var4).toString()))).parent((ModelFile)(new ModelFile.UncheckedModelFile("item/generated")))));
				String var6 = ((ResourceLocation)(var2.getKey())).getPath();
				String var7 = "abilities/" + var6;
				var5.texture("layer0",ResourceLocation.fromNamespaceAndPath("mineminenomi",var7));
				continue;
			}else{
				break;
			}
		} while (true);
		ResourceLocation var2_1 = ((ResourceLocation)(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(((Item)(ModItems.ABILITY_ICON.get()))))));
		ItemModelBuilder var3_1 = ((ItemModelBuilder)(((ItemModelBuilder)(((ItemModelBuilder)(this.getBuilder(var2_1.toString()))).parent((ModelFile)(new ModelFile.UncheckedModelFile("item/generated"))))).texture("layer0",ResourceLocation.fromNamespaceAndPath(var2_1.getNamespace(),"items/key"))));
		Comparator var4_1 = (Comparator) ((Comparator<Map.Entry>) ((l0, l1) -> {
			return (((Integer)(l0.getValue())).intValue()) - (((Integer)(l1.getValue())).intValue());
		}));
		Iterator var5_1 = AbilityIconItem.ICONS.entrySet().stream().sorted(var4_1).toList().iterator();
		do{
			if (var5_1.hasNext()){
				Map.Entry var6_1 = ((Map.Entry)(var5_1.next()));
				String var7_1 = ((ResourceLocation)(var6_1.getKey())).getPath();
				String var8 = "item/ability_icons/" + var7_1;
				var3_1 = var3_1.override().predicate(ModModelOverrides.ABILITY_ICON_PROPERTY,(float)(((Integer)(var6_1.getValue())).intValue())).model((ModelFile)(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath("mineminenomi",var8)))).end();
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void generateBasicItems(Supplier<Item>... var1) {
		Supplier<Item>[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				Supplier<Item> var5 = var2[var4];
				ResourceLocation var6 = ((ResourceLocation)(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(((Item)(var5.get()))))));
				ItemModelBuilder var7 = ((ItemModelBuilder)(((ItemModelBuilder)(this.getBuilder(var6.toString()))).parent((ModelFile)(new ModelFile.UncheckedModelFile("item/generated")))));
				String var8 = var6.getPath();
				String var9 = "items/" + var8;
				var7.texture("layer0",ResourceLocation.fromNamespaceAndPath(var6.getNamespace(),var9));
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}