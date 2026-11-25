package xyz.pixelatedw.mineminenomi.mixins.client;

import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.pixelatedw.mineminenomi.api.extensions.IAgeableListModelExtension;

@Mixin(value={AgeableListModel.class})
public abstract class AgeableListModelMixin implements IAgeableListModelExtension {
	@Shadow()
	public abstract Iterable<ModelPart> headParts();
	@Shadow()
	public abstract Iterable<ModelPart> bodyParts();
	public Iterable<ModelPart> getParts() {
		HashSet var1 = new HashSet();
		HashSet var2 = new HashSet();
		Collection var3 = var1;
		final Collection var3_f1 = var3;
		Consumer<? super ModelPart> var4 = (l0) -> {
			Objects.requireNonNull(var3_f1);
			l0.getAllParts().forEach((Consumer)(((Consumer<ModelPart>)(var3_f1::add))));
		};
		((java.lang.Iterable)(this.headParts())).forEach((Consumer)(var4));
		Collection var5 = var2;
		final Collection var5_f2 = var5;
		Consumer<? super ModelPart> var6 = (l0) -> {
			Objects.requireNonNull(var5_f2);
			l0.getAllParts().forEach((Consumer)(((Consumer<ModelPart>)(var5_f2::add))));
		};
		((java.lang.Iterable)(this.bodyParts())).forEach((Consumer)(var6));
		return Iterables.concat((Iterable)(var1),(Iterable)(var2));
	}
}