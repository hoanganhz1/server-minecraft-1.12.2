package xyz.pixelatedw.mineminenomi.api;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.CodecHelpers;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class DFEncyclopediaEntry {
	// Fields
	public static final Codec<DFEncyclopediaEntry> CODEC;
	private final Optional<Color> baseColor;
	private final Optional<Color> stemColor;
	private final Optional<Integer> shape;
	private AkumaNoMiItem devilFruit;

	public static DFEncyclopediaEntry empty() {
		return new DFEncyclopediaEntry(Optional.empty(),Optional.empty(),Optional.empty());
	}
	public static DFEncyclopediaEntry of(Optional<Integer> var0, Optional<Color> var1, Optional<Color> var2) {
		return new DFEncyclopediaEntry(var0,var1,var2);
	}
	private DFEncyclopediaEntry(Optional<Integer> var1, Optional<Color> var2, Optional<Color> var3) {
		this.shape = var1;
		this.baseColor = var2;
		this.stemColor = var3;
	}
	public Optional<Color> getBaseColor() {
		return this.baseColor;
	}
	public Optional<Color> getStemColor() {
		return this.stemColor;
	}
	public Optional<Integer> getShape() {
		return this.shape;
	}
	public AkumaNoMiItem getDevilFruit() {
		return this.devilFruit;
	}
	public void setDevilFruit(AkumaNoMiItem var1) {
		this.devilFruit = var1;
	}
	public int getCompletion() {
		int var1 = 0;
		if (this.getShape().isPresent()){
			var1++;
		}
		if ((this.getBaseColor().isPresent()) && ((((Color)(this.getBaseColor().get())).getRGB()) != (Color.BLACK.getRGB()))){
			var1++;
		}
		if ((this.getStemColor().isPresent()) && ((((Color)(this.getStemColor().get())).getRGB()) != (Color.BLACK.getRGB()))){
			var1++;
		}
		return var1;
	}
	public boolean isComplete() {
		return (this.getCompletion()) >= (3);
	}
	@Nullable()
	public static @Nullable() DFEncyclopediaEntry getClue(Level var0) {
		AkumaNoMiItem var1 = ((AkumaNoMiItem)(((java.util.Optional)(WyHelper.shuffle(new ArrayList((Collection)(ModValues.DEVIL_FRUITS))).stream().findFirst())).orElse((Object)(null))));
		if ((var1) == (null)){
			return null;
		}else{
			return getClue(var0,var1);
		}
	}
	@Nullable()
	public static @Nullable() DFEncyclopediaEntry getClue(Level var0, AkumaNoMiItem var1) {
		RandomSource var2 = var0.random;
		boolean var3 = ExtendedWorldData.get().isNewWorldActive();
		int var4 = (var3) ? (96) : (128);
		float var5 = (var3) ? (0.4F) : (0.25F);
		DFEncyclopediaEntry var6 = var1.getRandomElements((LevelAccessor)(var0));
		Optional var7 = Optional.empty();
		Optional var8 = Optional.empty();
		Optional var9 = Optional.empty();
		boolean var10 = false;
		if ((var2.nextInt(var4)) == (0)){
			var10 = true;
		}
		int var11 = 0;
		if (var10){
			var7 = var6.getShape();
			var8 = var6.getBaseColor();
			var9 = var6.getStemColor();
		}else{
			if ((var2.nextFloat()) < (var5)){
				var7 = var6.getShape();
				var11++;
			}
			if ((var2.nextFloat()) < (var5)){
				var8 = var6.getBaseColor();
				var11++;
			}
			if ((var2.nextFloat()) < (var5)){
				var9 = var6.getStemColor();
				var11++;
			}
			if ((var11) == (0)){
				float var12 = var2.nextFloat();
				if ((var12) < (0.5F)){
					var8 = var6.getBaseColor();
				}else{
					if ((var12) >= (0.5F)){
						var9 = var6.getStemColor();
					}
				}
			}
		}
		if ((!(var7.isPresent())) && ((!(var8.isPresent())) && (!(var9.isPresent())))){
			var9 = var6.getStemColor();
		}
		DFEncyclopediaEntry var12_1 = of(var7,var8,var9);
		var12_1.devilFruit = var1;
		return var12_1;
	}
	public String toString() {
		StringBuilder var1 = new StringBuilder();
		var1.append("[ ");
		if (this.getShape().isPresent()){
			String var2 = String.valueOf(this.getShape().get());
			String var3 = "Shape: " + var2 + " ";
			var1.append(var3);
		}
		if (this.getBaseColor().isPresent()){
			String var2_1 = ((Color)(this.getBaseColor().get())).toString();
			String var3_1 = "Base Color: " + var2_1 + " ";
			var1.append(var3_1);
		}
		if (this.getStemColor().isPresent()){
			String var2_2 = ((Color)(this.getStemColor().get())).toString();
			String var3_2 = "Stem Color: " + var2_2 + " ";
			var1.append(var3_2);
		}
		int var2_3 = this.getCompletion();
		String var3_3 = "Completion: " + var2_3;
		var1.append(var3_3);
		var1.append(" ]");
		return var1.toString();
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function lv4_1 = (Function) ((Function<DFEncyclopediaEntry, Optional>) ((l2_0) -> {
				return l2_0.shape;
			}));
			Function lv4_2 = (Function) ((Function<DFEncyclopediaEntry, Optional>) ((l2_0) -> {
				return l2_0.baseColor;
			}));
			Function lv4_3 = (Function) ((Function<DFEncyclopediaEntry, Optional>) ((l2_0) -> {
				return l2_0.stemColor;
			}));
			return l0.group((App)(Codec.INT.optionalFieldOf("shape").forGetter(lv4_1)),(App)(CodecHelpers.COLOR.optionalFieldOf("baseColor").forGetter(lv4_2)),(App)(CodecHelpers.COLOR.optionalFieldOf("stemColor").forGetter(lv4_3))).apply((Applicative)(l0),(Function3)(((com.mojang.datafixers.util.Function3)(DFEncyclopediaEntry::new))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
}