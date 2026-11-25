package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class CharacterCreatorSelectionInfo {
	// Fields
	private final ResourceLocation icon;
	private final int order;
	private List<Supplier<? extends AbilityCore<?>>> topAbilities;
	private List<Supplier<? extends AbilityCore<?>>> bottomAbilities;

	public CharacterCreatorSelectionInfo(ResourceLocation var1, int var2) {
		this.topAbilities = new ArrayList();
		this.bottomAbilities = new ArrayList();
		this.icon = var1;
		this.order = var2;
	}
	public void addTopAbilities(Supplier<? extends AbilityCore<?>>... var1) {
		this.topAbilities.addAll((Collection)(Lists.newArrayList(var1)));
	}
	public void addBottomAbilities(Supplier<? extends AbilityCore<?>>... var1) {
		this.bottomAbilities.addAll((Collection)(Lists.newArrayList(var1)));
	}
	public ResourceLocation getIcon() {
		return this.icon;
	}
	public int getOrder() {
		return this.order;
	}
	public List<Supplier<? extends AbilityCore<?>>> getTopAbilities() {
		return this.topAbilities;
	}
	public List<Supplier<? extends AbilityCore<?>>> getBottomAbilities() {
		return this.bottomAbilities;
	}
}