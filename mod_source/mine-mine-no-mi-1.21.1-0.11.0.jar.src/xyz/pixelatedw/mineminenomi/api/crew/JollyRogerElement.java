package xyz.pixelatedw.mineminenomi.api.crew;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class JollyRogerElement {
	// Fields
	public static final Codec<JollyRogerElement> CODEC;
	private ResourceLocation key;
	private Component localizedName;
	private ResourceLocation texture;
	private boolean canBeColored;
	private boolean canBeFlippedVertically;
	private boolean canBeFlippedHorizontally;
	private int red;
	private int green;
	private int blue;
	private int color;
	private float inversedRed;
	private float inversedGreen;
	private float inversedBlue;
	private Color fullColor;
	private boolean isFlippedVertically;
	private boolean isFlippedHorizontally;
	private int useLimit;
	private List<JollyRogerElement$ICanUse> canUseChecks;
	private final JollyRogerElement$LayerType type;
	private TexturedRectUI rect;

	public JollyRogerElement(JollyRogerElement$LayerType var1) {
		this.localizedName = ModI18n.GUI_NO_NAME;
		this.canBeColored = false;
		this.canBeFlippedVertically = false;
		this.canBeFlippedHorizontally = false;
		this.red = 255;
		this.green = 255;
		this.blue = 255;
		this.color = 16777215;
		this.fullColor = new Color(this.color);
		this.isFlippedVertically = false;
		this.isFlippedHorizontally = false;
		this.useLimit = 1;
		this.canUseChecks = new ArrayList();
		this.type = var1;
	}
	public JollyRogerElement(JollyRogerElement$LayerType var1, ResourceLocation var2) {
		this.localizedName = ModI18n.GUI_NO_NAME;
		this.canBeColored = false;
		this.canBeFlippedVertically = false;
		this.canBeFlippedHorizontally = false;
		this.red = 255;
		this.green = 255;
		this.blue = 255;
		this.color = 16777215;
		this.fullColor = new Color(this.color);
		this.isFlippedVertically = false;
		this.isFlippedHorizontally = false;
		this.useLimit = 1;
		this.canUseChecks = new ArrayList();
		this.type = var1;
		this.setTexture(var2);
	}
	public static JollyRogerElement of(ResourceLocation var0) {
		return ((JollyRogerElement)(WyRegistry.JOLLY_ROGER_ELEMENTS.get(var0)));
	}
	public boolean canBeColored() {
		return this.canBeColored;
	}
	public JollyRogerElement setCanBeColored() {
		this.canBeColored = true;
		return this;
	}
	public boolean canBeFlippedVertically() {
		return this.canBeFlippedVertically;
	}
	public boolean canBeFlippedHorizontally() {
		return this.canBeFlippedHorizontally;
	}
	public JollyRogerElement setCanBeFlipped(boolean var1, boolean var2) {
		this.canBeFlippedVertically = var1;
		this.canBeFlippedHorizontally = var2;
		return this;
	}
	public int getUseLimit() {
		return this.useLimit;
	}
	public JollyRogerElement setUseLimit(int var1) {
		this.useLimit = var1;
		return this;
	}
	public Color getFullColor() {
		return this.fullColor;
	}
	public int getRed() {
		return this.red;
	}
	public float getRedF() {
		return ((float)(this.red)) / (255F);
	}
	public int getGreen() {
		return this.green;
	}
	public float getGreenF() {
		return ((float)(this.green)) / (255F);
	}
	public int getBlue() {
		return this.blue;
	}
	public float getBlueF() {
		return ((float)(this.blue)) / (255F);
	}
	public Color getColor() {
		return this.fullColor;
	}
	public int getColorI() {
		return this.color;
	}
	public float getInversedRedF() {
		return this.inversedRed;
	}
	public float getInversedGreenF() {
		return this.inversedGreen;
	}
	public float getInversedBlueF() {
		return this.inversedBlue;
	}
	public JollyRogerElement setColor(int var1) {
		int var2 = (-16777216) | (var1);
		int var3 = ((var2) >> (16)) & (255);
		int var4 = ((var2) >> (8)) & (255);
		this.setColor(var3,var4,((var2) >> (0)) & (255));
		return this;
	}
	public JollyRogerElement setColor(int var1, int var2, int var3) {
		this.red = var1;
		this.green = var2;
		this.blue = var3;
		this.color = WyHelper.rgbToInt(var1,var2,var3,255);
		this.fullColor = new Color(this.color);
		this.inversedRed = ((float)((255) - (this.red))) / (255F);
		this.inversedGreen = ((float)((255) - (this.green))) / (255F);
		this.inversedBlue = ((float)((255) - (this.blue))) / (255F);
		return this;
	}
	public boolean isFlippedVertically() {
		return this.isFlippedVertically;
	}
	public JollyRogerElement setFlipVertically(boolean var1) {
		this.isFlippedVertically = var1;
		return this;
	}
	public boolean isFlippedHorizontally() {
		return this.isFlippedHorizontally;
	}
	public JollyRogerElement setFlipHorizontally(boolean var1) {
		this.isFlippedHorizontally = var1;
		return this;
	}
	public TexturedRectUI getTexturedRect() {
		if (((this.rect) != (null)) && (this.rect.getTexture().equals(this.texture))){

		}else{
			this.rect = new TexturedRectUI(this.texture);
		}
		return this.rect;
	}
	public ResourceLocation getTexture() {
		return this.texture;
	}
	public JollyRogerElement setTexture(ResourceLocation var1) {
		this.texture = var1;
		return this;
	}
	public JollyRogerElement setLocalizedName(Component var1) {
		this.localizedName = var1;
		return this;
	}
	public JollyRogerElement addUseCheck(JollyRogerElement$ICanUse var1) {
		this.canUseChecks.add(var1);
		return this;
	}
	public boolean canUse(Player var1, Crew var2) {
		boolean var3 = true;
		Iterator var4 = this.canUseChecks.iterator();
		do{
			if (var4.hasNext()){
				JollyRogerElement$ICanUse var5 = ((JollyRogerElement$ICanUse)(var4.next()));
				if (((var5) != (null)) && (!(var5.canUse(var1,var2)))){
					var3 = false;
					break;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var3;
	}
	public JollyRogerElement$LayerType getLayerType() {
		return this.type;
	}
	public boolean equals(Object var1) {
		if (var1 instanceof JollyRogerElement){
			JollyRogerElement var2 = ((JollyRogerElement)(var1));
			if (((this.getTexture()) != (null)) && ((var2.getTexture()) != (null))){
				return this.getTexture().equals(var2.getTexture());
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	@Nullable()
	public @Nullable() JollyRogerElement create() {
		JollyRogerElement var1 = null;
		try{
			var1 = ((JollyRogerElement)(this.getClass().getConstructor(new Class[0]).newInstance(new Object[0])));
		}catch(Exception var1_1){
			var1_1.printStackTrace();
			return null;
		}
		return var1;
	}
	public void write(CompoundTag var1) {
		var1.putInt("color",this.color);
		var1.putBoolean("isFlippedVertically",this.isFlippedVertically);
		var1.putBoolean("isFlippedHorizontally",this.isFlippedHorizontally);
	}
	public void read(CompoundTag var1) {
		this.setColor(var1.getInt("color"));
		this.isFlippedVertically = var1.getBoolean("isFlippedVertically");
		this.isFlippedHorizontally = var1.getBoolean("isFlippedHorizontally");
	}
	@Nullable()
	public @Nullable() ResourceLocation getRegistryKey() {
		if ((this.key) == (null)){
			Iterator var1 = WyRegistry.JOLLY_ROGER_ELEMENTS.entrySet().iterator();
			do{
				if (var1.hasNext()){
					Map.Entry var2 = ((Map.Entry)(var1.next()));
					if (this.equals(var2.getValue())){
						this.key = ((ResourceKey)(var2.getKey())).location();
						break;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		return this.key;
	}
	public Component getLocalizedName() {
		return this.localizedName;
	}
	public JollyRogerElement copy() {
		JollyRogerElement var1 = new JollyRogerElement(this.getLayerType(),this.getTexture());
		var1.key = this.key;
		var1.localizedName = this.localizedName;
		var1.texture = this.texture;
		var1.canBeColored = this.canBeColored;
		var1.canBeFlippedVertically = this.canBeFlippedVertically;
		var1.canBeFlippedHorizontally = this.canBeFlippedHorizontally;
		var1.useLimit = this.useLimit;
		var1.canUseChecks = (List) (this.canUseChecks);
		return var1;
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return l0.group((App)(ResourceLocation.CODEC.fieldOf("element").forGetter((Function)(((Function<JollyRogerElement, ResourceLocation>)(JollyRogerElement::getRegistryKey)))))).apply((Applicative)(l0),(Function)(((Function<ResourceLocation, JollyRogerElement>)(JollyRogerElement::of))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
}