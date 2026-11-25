package xyz.pixelatedw.mineminenomi.api.abilities;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NoAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class AbilityCore<A extends IAbility> implements Comparable<AbilityCore<?>> {
	// Fields
	public static final Codec<Holder<AbilityCore<?>>> CODEC = WyRegistry.ABILITIES.holderByNameCodec();
	public static final ResourceLocation MISSING_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/missing.png");
	private ResourceLocation key;
	final String name;
	final String id;
	final AbilityCategory category;
	final AbilityType type;
	final AbilityCore$IFactory<A> factory;
	 ResourceLocation icon;
	private Set<AbilityDescriptionLine> description;
	 String[] descriptionKeys;
	 SourceHakiNature sourceHakiNature;
	private ArrayList<SourceType> sourceTypes;
	 SourceElement sourceElement;
	 AbilityCore$ICanUnlock unlockCheck;
	private List<AbilityCore$INodeFactory> nodeFactories;
	private @Nullable() String i18nId;
	 ResourceLocation phantomKey;
	private Component localizedDisplayName;
	 List<AbilityCore$IAbilityGoalFactory<A>> goalFactories;
	 AbilityCore$IVisibilityPredicate visibilityCheck;

	protected AbilityCore(String var1, String var2, AbilityCategory var3, AbilityType var4, AbilityCore$IFactory<A> var5) {
		this.nodeFactories = new ArrayList();
		this.name = var1;
		this.id = var2;
		this.category = var3;
		this.type = var4;
		this.factory = var5;
	}
	public String getUnlocalizedName() {
		return this.name;
	}
	public String getId() {
		return this.id;
	}
	public Component getLocalizedName() {
		String var1 = this.getLocalizationId();
		if (Strings.isNullOrEmpty(var1)){
			return Component.literal(this.getUnlocalizedName());
		}else{
			return Component.translatable(var1);
		}
	}
	public String getLocalizationId() {
		if ((this.i18nId) == (null)){
			ResourceLocation var1 = WyRegistry.ABILITIES.getKey(this);
			if (((var1) == (null)) && ((this.phantomKey) != (null))){
				var1 = this.phantomKey;
			}
			if ((var1) != (null)){
				this.i18nId = Util.makeDescriptionId("ability",var1);
			}
		}
		return this.i18nId;
	}
	public AbilityCategory getCategory() {
		return this.category;
	}
	public AbilityType getType() {
		return this.type;
	}
	public AbilityCore$IFactory<? extends A> getFactory() {
		return (AbilityCore$IFactory<? extends A>) (AbilityCore$IFactory) (this.factory);
	}
	private void setUnlockCheck(AbilityCore$ICanUnlock var1) {
		this.unlockCheck = var1;
	}
	public boolean canUnlock(LivingEntity var1) {
		if ((this.unlockCheck) == (null)){
			return false;
		}else{
			return this.unlockCheck.canUnlock(var1);
		}
	}
	public boolean hasUnlockCheck() {
		return (this.unlockCheck) != (null);
	}
	private void setNodeFactories(AbilityCore$INodeFactory... var1) {
		this.nodeFactories = (List) (List.of(var1));
	}
	public int getNodeCount() {
		return this.nodeFactories.size();
	}
	@Nullable()
	public @Nullable() AbilityNode getNode(LivingEntity var1) {
		return this.getNode(var1,0);
	}
	@Nullable()
	public @Nullable() AbilityNode getNode(LivingEntity var1, int var2) {
		if ((this.nodeFactories.size()) <= (var2)){
			return null;
		}else{
			AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
			if ((var3) == (null)){
				return null;
			}else{
				AbilityNode var4 = var3.getNode(this,var2);
				if ((var4) == (null)){
					var4 = ((AbilityCore$INodeFactory)(this.nodeFactories.get(var2))).create(var1);
					var3.addNode(this,var2,var4);
				}
				return var4;
			}
		}
	}
	private void setSourceHakiNature(SourceHakiNature var1) {
		this.sourceHakiNature = var1;
	}
	public SourceHakiNature getSourceHakiNature() {
		return this.sourceHakiNature;
	}
	private void setSourceTypes(SourceType... var1) {
		this.sourceTypes = (ArrayList) (new ArrayList((Collection)(Arrays.asList(var1))));
		if ((this.sourceTypes.contains(SourceType.FIST)) && (!(this.sourceTypes.contains(SourceType.PHYSICAL)))){
			this.sourceTypes.add(SourceType.PHYSICAL);
		}
	}
	public ArrayList<SourceType> getSourceTypes() {
		return new ArrayList((Collection)(this.sourceTypes));
	}
	public SourceType[] getSourceTypesArray() {
		return ((SourceType[])(this.sourceTypes.toArray(new SourceType[0])));
	}
	public boolean hasType(SourceType var1) {
		boolean var2 = false;
		Iterator var3 = this.sourceTypes.iterator();
		do{
			if (var3.hasNext()){
				if (((SourceType)(var3.next())).equals(var1)){
					var2 = true;
					break;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	private void setSourceElement(SourceElement var1) {
		this.sourceElement = var1;
	}
	public SourceElement getSourceElement() {
		return this.sourceElement;
	}
	private void setHidden(AbilityCore$IVisibilityPredicate var1) {
		this.visibilityCheck = var1;
	}
	public boolean isVisibleInTooltips() {
		return this.visibilityCheck.isVisible();
	}
	private void setDescription(Set<AbilityDescriptionLine> var1) {
		this.description = var1;
	}
	@Nullable()
	public @Nullable() Set<AbilityDescriptionLine> getDescription() {
		return this.description;
	}
	@Nullable()
	public @Nullable() String[] getDescriptionKeys() {
		if ((this.descriptionKeys) == (null)){
			this.descriptionKeys = new String[0];
		}
		return this.descriptionKeys;
	}
	private void setIcon(ResourceLocation var1) {
		this.icon = var1;
	}
	public ResourceLocation getIcon() {
		return this.icon;
	}
	private void addGoalWrappers(List<AbilityCore$IAbilityGoalFactory<A>> var1) {
		this.goalFactories = var1;
	}
	public boolean hasGoalWrapper() {
		return ((this.goalFactories) != (null)) && ((this.goalFactories.size()) > (0));
	}
	public List<AbilityWrapperGoal<Mob, ? extends IAbility>> getGoalWrappers(Mob var1) {
		if (!(this.hasGoalWrapper())){
			AbilityCore$IAbilityGoalFactory[] var2 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory var3 = NoAbilityWrapperGoal::new;
			var2[0] = var3;
			this.goalFactories = Lists.newArrayList(var2);
		}
		ArrayList var2_1 = Lists.newArrayList();
		Iterator var3_1 = this.goalFactories.iterator();
		do{
			if (var3_1.hasNext()){
				AbilityWrapperGoal var4 = ((AbilityCore$IAbilityGoalFactory)(var3_1.next())).create(var1,this);
				if (var4 instanceof NoAbilityWrapperGoal){
					String var5 = this.getUnlocalizedName();
					String var6 = "No Ability Wrapper Goal set for " + var5;
					WyDebug.error(var6);
					continue;
				}else{
					var2_1.add(var4);
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var2_1;
	}
	public boolean isHidden() {
		return !(this.visibilityCheck.isVisible());
	}
	public boolean isVisible() {
		return !(this.isHidden());
	}
	private void setPhantomKey(ResourceLocation var1) {
		this.phantomKey = var1;
	}
	public A createAbility() {
		return (A) (((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(this.factory)).create((AbilityCore)(this)));
	}
	@Nullable()
	public static <A extends IAbility> @Nullable() AbilityCore<?> get(ResourceLocation var0) {
		return (AbilityCore<?>) (AbilityCore) (((AbilityCore)(WyRegistry.ABILITIES.get(var0))));
	}
	@Nullable()
	public @Nullable() ResourceLocation getRegistryKey() {
		if ((this.key) == (null)){
			this.key = WyRegistry.ABILITIES.getKey(this);
		}
		return this.key;
	}
	public String toString() {
		return this.getUnlocalizedName();
	}
	public boolean equals(Object var1) {
		if (var1 instanceof AbilityCore){
			AbilityCore var2 = ((AbilityCore)(var1));
			if (((this.getRegistryKey()) != (null)) && ((var2.getRegistryKey()) != (null))){
				if (this.getRegistryKey().equals(var2.getRegistryKey())){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public int compareTo(AbilityCore<?> var1) {
		if ((var1) == (null)){
			return 1;
		}else{
			String var2 = this.getLocalizedName().getString();
			return var2.compareToIgnoreCase(var1.getLocalizedName().getString());
		}
	}
	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$0(List arg0) { ((AbilityCore)this).addGoalWrappers(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$1(Set arg0) { ((AbilityCore)this).setDescription(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$2(AbilityCore$IVisibilityPredicate arg0) { ((AbilityCore)this).setHidden(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$3(ResourceLocation arg0) { ((AbilityCore)this).setIcon(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$4(AbilityCore$INodeFactory[] arg0) { ((AbilityCore)this).setNodeFactories(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$5(ResourceLocation arg0) { ((AbilityCore)this).setPhantomKey(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$6(SourceElement arg0) { ((AbilityCore)this).setSourceElement(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$7(SourceHakiNature arg0) { ((AbilityCore)this).setSourceHakiNature(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$8(SourceType[] arg0) { ((AbilityCore)this).setSourceTypes(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$9(AbilityCore$ICanUnlock arg0) { ((AbilityCore)this).setUnlockCheck(arg0); }

}