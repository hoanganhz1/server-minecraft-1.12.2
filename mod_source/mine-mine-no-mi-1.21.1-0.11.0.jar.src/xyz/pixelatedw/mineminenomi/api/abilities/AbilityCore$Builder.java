package xyz.pixelatedw.mineminenomi.api.abilities;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;

public class AbilityCore$Builder<A extends IAbility> {
	// Fields
	final String name;
	final String id;
	final AbilityCategory category;
	final AbilityType type;
	final AbilityCore$IFactory<A> factory;
	private Set<AbilityDescriptionLine> descriptionLines;
	 String[] descriptionKeys;
	 SourceHakiNature sourceHakiNature;
	private SourceType[] sourceTypes;
	 SourceElement sourceElement;
	 AbilityCore$ICanUnlock unlockCheck;
	private AbilityCore$INodeFactory[] nodeFactories;
	private Set<Consumer<AbilityCore$Builder<A>>> consumerExtensions;
	 ResourceLocation icon;
	 ResourceLocation phantomKey;
	 List<AbilityCore$IAbilityGoalFactory<A>> goalFactories;
	 AbilityCore$IVisibilityPredicate visibilityCheck;

	public AbilityCore$Builder(String var1, String var2, AbilityCategory var3, AbilityCore$IFactory<A> var4) {
		this(var1,var2,var3,AbilityType.ACTION,var4);
	}
	public AbilityCore$Builder(String var0, String var1, AbilityCategory var2, AbilityType var3, AbilityCore$IFactory<A> var4) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public AbilityCore$Builder<A> setHidden(AbilityCore$IVisibilityPredicate var1) {
		this.visibilityCheck = var1;
		return this;
	}
	public AbilityCore$Builder<A> setHidden() { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public AbilityCore$Builder<A> setUnlockCheck(AbilityCore$ICanUnlock var1) {
		this.unlockCheck = var1;
		return this;
	}
	public AbilityCore$Builder<A> setNodeFactories(AbilityCore$INodeFactory... var1) {
		this.nodeFactories = var1;
		return this;
	}
	public AbilityCore$Builder<A> setSourceHakiNature(SourceHakiNature var1) {
		this.sourceHakiNature = var1;
		return this;
	}
	public AbilityCore$Builder<A> setSourceType(SourceType... var1) {
		this.sourceTypes = var1;
		return this;
	}
	public AbilityCore$Builder<A> setSourceElement(SourceElement var1) {
		this.sourceElement = var1;
		return this;
	}
	public AbilityCore$Builder<A> setIcon(ResourceLocation var1) {
		this.icon = var1;
		return this;
	}
	@Deprecated()
	public AbilityCore$Builder<A> addDescriptionLine(String var1, Object... var2) {
		this.descriptionLines.add(AbilityDescriptionLine.of((Component)(Component.translatable(var1,var2)),false));
		return this;
	}
	@Deprecated()
	public AbilityCore$Builder<A> addExtension(Consumer<AbilityCore$Builder<A>> var1) {
		this.consumerExtensions.add(var1);
		return this;
	}
	public AbilityCore$Builder<A> setDescriptionKeys(String... var1) {
		this.descriptionKeys = var1;
		return this;
	}
	public AbilityCore$Builder<A> addDescriptionLine(AbilityDescriptionLine... var1) {
		Collections.addAll((Collection)(this.descriptionLines),var1);
		return this;
	}
	public AbilityCore$Builder<A> addDescriptionLine(AbilityDescriptionLine$IDescriptionLine... var1) {
		AbilityDescriptionLine$IDescriptionLine[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				AbilityDescriptionLine$IDescriptionLine var5 = var2[var4];
				this.descriptionLines.add(AbilityDescriptionLine.of(var5,false));
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return this;
	}
	public AbilityCore$Builder<A> addAdvancedDescriptionLine(AbilityDescriptionLine$IDescriptionLine... var1) {
		AbilityDescriptionLine$IDescriptionLine[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				AbilityDescriptionLine$IDescriptionLine var5 = var2[var4];
				this.descriptionLines.add(AbilityDescriptionLine.of(var5,true));
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return this;
	}
	public AbilityCore$Builder<A> addDescriptionLine(Component... var1) {
		Component[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				Component var5 = var2[var4];
				this.descriptionLines.add(AbilityDescriptionLine.of(var5,false));
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return this;
	}
	public AbilityCore$Builder<A> setPhantomKey(ResourceLocation var1) {
		this.phantomKey = var1;
		return this;
	}
	@SafeVarargs()
	public final AbilityCore$Builder<A> addGoalWrapper(AbilityCore$IAbilityGoalFactory<A>... var1) {
		this.goalFactories.addAll((Collection)(Lists.newArrayList(var1)));
		return this;
	}
	@Deprecated()
	public AbilityCore<A> build() {
		return this.build((String)(null));
	}
	public AbilityCore<A> build(String var1) {
		AbilityCore var2 = new AbilityCore(this.name,this.id,this.category,this.type,this.factory);
		Iterator var3 = this.consumerExtensions.iterator();
		do{
			if (var3.hasNext()){
				((Consumer)(var3.next())).accept(this);
				continue;
			}else{
				break;
			}
		} while (true);
		LinkedHashSet var4 = new LinkedHashSet();
		Iterator var5 = this.descriptionLines.iterator();
		do{
			if (var5.hasNext()){
				((java.util.Set)(var4)).add((Object)(((AbilityDescriptionLine)(var5.next()))));
				continue;
			}else{
				break;
			}
		} while (true);
		var2.descriptionKeys = this.descriptionKeys;
		((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$1(((Set)(var4)));
		((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$9(((AbilityCore$ICanUnlock)(this.unlockCheck)));
		((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$4(((AbilityCore$INodeFactory[])(this.nodeFactories)));
		((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$7(((SourceHakiNature)(this.sourceHakiNature)));
		((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$8(((SourceType[])(this.sourceTypes)));
		((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$6(((SourceElement)(this.sourceElement)));
		((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$0(((List)(this.goalFactories)));
		((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$2(((AbilityCore$IVisibilityPredicate)(this.visibilityCheck)));
		if (((this.icon) == (null)) && ((var1) != (null))){
			String var6 = this.id;
			String var7 = "textures/abilities/" + var6 + ".png";
			((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$3(((ResourceLocation)(ResourceLocation.fromNamespaceAndPath(var1,var7))));
		}else{
			if (((this.icon) == (null)) && ((var1) == (null))){
				((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$3(((ResourceLocation)(AbilityCore.MISSING_TEXTURE)));
			}else{
				((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$3(((ResourceLocation)(this.icon)));
			}
		}
		((AbilityCore)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c697479436f7265$5(((ResourceLocation)(this.phantomKey)));
		return var2;
	}
}