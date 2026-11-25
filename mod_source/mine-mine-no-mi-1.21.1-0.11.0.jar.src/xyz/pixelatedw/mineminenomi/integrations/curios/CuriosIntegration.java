package xyz.pixelatedw.mineminenomi.integrations.curios;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurio;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.integrations.curios.renderers.ArmorCurioRenderer;
import xyz.pixelatedw.mineminenomi.integrations.curios.renderers.ItemCurioRenderer;
import xyz.pixelatedw.mineminenomi.items.armors.ModArmorItem;

public class CuriosIntegration {
	// Fields
	private static final Map<Item, Function<ItemStack, ICurio>> CURIO_ITEMS = new HashMap();

	public static void setupCurioCapabilities(RegisterCapabilitiesEvent var0) {
		Map.Entry var3 = null;
		Iterator var1 = CURIO_ITEMS.entrySet().iterator();
		do{
			if (var1.hasNext()){
				Map.Entry var2 = ((Map.Entry)(var1.next()));
				var3 = var2;
				final Map.Entry var3_f1 = var3;
				ICapabilityProvider var4 = (l0$sam, l1$sam) -> {
			ItemStack l0 = ((ItemStack)(l0$sam));
			Void l1 = ((Void)(l1$sam));

			return ((ICurio)(((Function)(var3_f1.getValue())).apply(l0)));
		};
				ItemLike[] var5 = new ItemLike[]{((ItemLike)(var2.getKey()))};
				var0.registerItem(CuriosCapability.ITEM,var4,var5);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static void registerCurioItems() {
		Item var0 = ((Item)(ModArmors.SNIPER_GOGGLES.get()));
		Function<ItemStack, ICurio> var1 = SimpleCurioItem::new;
		CURIO_ITEMS.put(var0,var1);
		Item var2 = ((Item)(ModItems.CIGAR.get()));
		Function<ItemStack, ICurio> var3 = SimpleCurioItem::new;
		CURIO_ITEMS.put(var2,var3);
		Item var4 = ((Item)(ModItems.CIGARETTE.get()));
		Function<ItemStack, ICurio> var5 = SimpleCurioItem::new;
		CURIO_ITEMS.put(var4,var5);
		Item var6 = ((Item)(ModItems.SMOKING_PIPE.get()));
		Function<ItemStack, ICurio> var7 = SimpleCurioItem::new;
		CURIO_ITEMS.put(var6,var7);
		Item var8 = ((Item)(ModItems.THREE_CIGARS.get()));
		Function<ItemStack, ICurio> var9 = SimpleCurioItem::new;
		CURIO_ITEMS.put(var8,var9);
		Item var10 = ((Item)(ModArmors.COLA_BACKPACK.get()));
		Function<ItemStack, ICurio> var11 = SimpleCurioItem::new;
		CURIO_ITEMS.put(var10,var11);
		Item var12 = ((Item)(ModArmors.MEDIC_BAG.get()));
		Function<ItemStack, ICurio> var13 = SimpleCurioItem::new;
		CURIO_ITEMS.put(var12,var13);
		Item var14 = ((Item)(ModArmors.TOMOE_DRUMS.get()));
		Function<ItemStack, ICurio> var15 = SimpleCurioItem::new;
		CURIO_ITEMS.put(var14,var15);
		Item var16 = ((Item)(ModItems.COLOR_PALETTE.get()));
		Function<ItemStack, ICurio> var17 = SimpleCurioItem::new;
		CURIO_ITEMS.put(var16,var17);
	}
	public static void registerCurioRenderers() {
		Class var0 = ModArmorItem.class;
		Objects.requireNonNull(var0);
		Consumer<Item> var1 = (l0) -> {
			CuriosRendererRegistry.register(l0,(Supplier)(((Supplier<ICurioRenderer>)(ArmorCurioRenderer::new))));
		};
		((java.util.stream.Stream)(ModRegistry.ITEMS.getEntries().stream().map((Function)(((Function<DeferredHolder, Item>)(DeferredHolder::get)))).filter((Predicate)(((Predicate<Item>)(var0::isInstance)))))).forEach((Consumer)(var1));
		CuriosRendererRegistry.register(((Item)(ModItems.CIGAR.get())),(Supplier)(((Supplier<ICurioRenderer>)(ItemCurioRenderer::new))));
		CuriosRendererRegistry.register(((Item)(ModItems.CIGARETTE.get())),(Supplier)(((Supplier<ICurioRenderer>)(ItemCurioRenderer::new))));
		CuriosRendererRegistry.register(((Item)(ModItems.SMOKING_PIPE.get())),(Supplier)(((Supplier<ICurioRenderer>)(ItemCurioRenderer::new))));
		CuriosRendererRegistry.register(((Item)(ModItems.THREE_CIGARS.get())),(Supplier)(((Supplier<ICurioRenderer>)(ItemCurioRenderer::new))));
	}
}