package xyz.pixelatedw.mineminenomi.api.crew;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.ModJollyRogers;

public class JollyRoger {
	// Fields
	public static final int BACKGROUND_SLOTS = 2;
	public static final int DETAIL_SLOTS = 5;
	public static final Codec<JollyRoger> CODEC;
	private JollyRogerElement base;
	private JollyRogerElement[] backgrounds;
	private JollyRogerElement[] details;

	public JollyRoger() {
		this.backgrounds = new JollyRogerElement[2];
		this.details = new JollyRogerElement[5];
	}
	public JollyRoger(JollyRogerElement var1, JollyRogerElement[] var2, JollyRogerElement[] var3) {
		this.backgrounds = new JollyRogerElement[2];
		this.details = new JollyRogerElement[5];
		if (((var2.length) <= (2)) && ((var3.length) <= (5))){
			this.base = var1;
			this.backgrounds = var2;
			this.details = var3;
			return;
		}else{
			throw new RuntimeException((String)("Jolly Roger contains more elements that its allowed to !"));
		}
	}
	public CompoundTag write() {
		CompoundTag var1 = new CompoundTag();
		if (this.hasNoElements()){
			this.setBase(((JollyRogerElement)(ModJollyRogers.BASE_SKULL.get())));
		}
		try{
			CompoundTag var7 = null;
			JollyRogerElement var2 = this.getBase();
			CompoundTag var3 = new CompoundTag();
			if ((var2) != (null)){
				var3.putString("id",var2.getRegistryKey().toString());
				var2.write(var3);
			}
			var1.put("base",(Tag)(var3));
			ListTag var4 = new ListTag();
			int var5 = 0;
			do{
				if ((var5) < (this.getBackgrounds().length)){
					JollyRogerElement var6 = this.getBackgrounds()[var5];
					if (((var6) != (null)) && ((var6.getTexture()) != (null))){
						var7 = new CompoundTag();
						var7.putInt("slot",var5);
						var7.putString("id",var6.getRegistryKey().toString());
						var6.write(var7);
						var4.add(var7);
					}else{
						var7 = new CompoundTag();
						var7.putInt("slot",var5);
						var4.add(var7);
					}
					var5++;
					continue;
				}else{
					break;
				}
			} while (true);
			var1.put("backgrounds",(Tag)(var4));
			ListTag var6_1 = new ListTag();
			int var7_1 = 0;
			do{
				if ((var7_1) < (this.getDetails().length)){
					CompoundTag var9 = null;
					JollyRogerElement var8 = this.getDetails()[var7_1];
					if (((var8) != (null)) && ((var8.getTexture()) != (null))){
						var9 = new CompoundTag();
						var9.putInt("slot",var7_1);
						var9.putString("id",var8.getRegistryKey().toString());
						var8.write(var9);
						var6_1.add(var9);
					}else{
						var9 = new CompoundTag();
						var9.putInt("slot",var7_1);
						var6_1.add(var9);
					}
					var7_1++;
					continue;
				}else{
					break;
				}
			} while (true);
			var1.put("details",(Tag)(var6_1));
		}catch(Exception var2_1){
			var2_1.printStackTrace();
		}
		return var1;
	}
	public static JollyRoger from(CompoundTag var0) {
		JollyRoger var1 = new JollyRoger();
		try{
			CompoundTag var2 = var0.getCompound("base");
			if (var2.contains("id")){
				ResourceLocation var3 = ResourceLocation.parse(var2.getString("id"));
				if ((var3) != (null)){
					JollyRogerElement var4 = JollyRogerElement.of(var3).copy();
					if ((var4) != (null)){
						var4.read(var2);
						var1.setBase(var4);
					}
				}else{
					var1.setBase((JollyRogerElement)(null));
				}
			}else{
				var1.setBase((JollyRogerElement)(null));
			}
			ListTag var3_1 = var0.getList("backgrounds",10);
			int var4_1 = 0;
			do{
				if ((var4_1) < (var3_1.size())){
					CompoundTag var5 = var3_1.getCompound(var4_1);
					int var6 = var5.getInt("slot");
					if (var5.contains("id")){
						ResourceLocation var7 = ResourceLocation.parse(var5.getString("id"));
						if ((var7) != (null)){
							JollyRogerElement var8 = JollyRogerElement.of(var7).copy();
							if ((var8) != (null)){
								var8.read(var5);
								var1.setBackground(var6,var8);
							}
						}else{
							var1.setBackground(var6,(JollyRogerElement)(null));
						}
					}else{
						var1.setBackground(var6,(JollyRogerElement)(null));
					}
					var4_1++;
					continue;
				}else{
					break;
				}
			} while (true);
			ListTag var5_1 = var0.getList("details",10);
			int var6_1 = 0;
			do{
				if ((var6_1) < (var5_1.size())){
					CompoundTag var7_1 = var5_1.getCompound(var6_1);
					int var8_1 = var7_1.getInt("slot");
					if (var7_1.contains("id")){
						ResourceLocation var9 = ResourceLocation.parse(var7_1.getString("id"));
						if ((var9) != (null)){
							JollyRogerElement var10 = JollyRogerElement.of(var9).copy();
							if ((var10) != (null)){
								var10.read(var7_1);
								var1.setDetail(var8_1,var10);
							}
						}else{
							var1.setDetail(var8_1,(JollyRogerElement)(null));
						}
					}else{
						var1.setDetail(var8_1,(JollyRogerElement)(null));
					}
					var6_1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}catch(Exception var2_1){
			var2_1.printStackTrace();
		}
		return var1;
	}
	@Nullable()
	public JollyRogerElement getBase() {
		if ((this.base) == (null)){
			this.base = ((JollyRogerElement)(ModJollyRogers.BASE_SKULL.get()));
		}
		return this.base;
	}
	public void setBase(JollyRogerElement var1) {
		this.base = var1;
	}
	public JollyRogerElement[] getBackgrounds() {
		return this.backgrounds;
	}
	public void setBackgrounds(JollyRogerElement[] var1) {
		this.backgrounds = var1;
	}
	public boolean addBackground(JollyRogerElement var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.backgrounds.length)){
				if ((this.backgrounds[var2]) == (null)){
					this.backgrounds[var2] = var1;
					return true;
				}else{
					var2++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public boolean setBackground(int var1, JollyRogerElement var2) {
		if ((!(this.hasBackground(var2))) && ((var1) <= (2))){
			this.backgrounds[var1] = var2;
			return true;
		}else{
			return false;
		}
	}
	public boolean hasBackground(JollyRogerElement var1) {
		if ((var1) == (null)){
			return false;
		}else{
			int var2 = 0;
			JollyRogerElement[] var3 = this.backgrounds;
			int var4 = var3.length;
			int var5 = 0;
			do{
				if ((var5) < (var4)){
					JollyRogerElement var6 = var3[var5];
					if (((var6) != (null)) && (var6.equals(var1))){
						var2++;
					}
					if ((var2) >= (var1.getUseLimit())){
						return true;
					}else{
						var5++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return false;
		}
	}
	public JollyRogerElement[] getDetails() {
		return this.details;
	}
	public void setDetails(JollyRogerElement[] var1) {
		this.details = var1;
	}
	public boolean addDetail(JollyRogerElement var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.details.length)){
				if ((this.details[var2]) == (null)){
					this.details[var2] = var1;
					return true;
				}else{
					var2++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public boolean setDetail(int var1, JollyRogerElement var2) {
		if ((!(this.hasDetail(var2))) && ((var1) <= (5))){
			this.details[var1] = var2;
			return true;
		}else{
			return false;
		}
	}
	public boolean hasDetail(JollyRogerElement var1) {
		if ((var1) == (null)){
			return false;
		}else{
			int var2 = 0;
			JollyRogerElement[] var3 = this.details;
			int var4 = var3.length;
			int var5 = 0;
			do{
				if ((var5) < (var4)){
					JollyRogerElement var6 = var3[var5];
					if (((var6) != (null)) && (var6.equals(var1))){
						var2++;
					}
					if ((var2) >= (var1.getUseLimit())){
						return true;
					}else{
						var5++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return false;
		}
	}
	public boolean hasNoElements() {
		int var1 = ((this.getBase()) == (null)) ? (1) : (0);
		boolean var2 = true;
		JollyRogerElement[] var3 = this.getBackgrounds();
		int var4 = var3.length;
		int var5 = 0;
		do{
			if ((var5) < (var4)){
				if ((var3[var5]) != (null)){
					var2 = false;
					break;
				}else{
					var5++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		boolean var6 = true;
		JollyRogerElement[] var7 = this.getDetails();
		var5 = var7.length;
		int var8 = 0;
		do{
			if ((var8) < (var5)){
				if ((var7[var8]) != (null)){
					var6 = false;
					break;
				}else{
					var8++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return ((var1) != (0)) && ((var2) && (var6));
	}
	public Optional<BufferedImage> getAsBufferedImage() {
		try{
			BufferedImage var1 = new BufferedImage(128,128,2);
			JollyRogerElement[] var2 = this.backgrounds;
			int var3 = var2.length;
			int var4 = 0;
			do{
				if ((var4) < (var3)){
					JollyRogerElement var5 = var2[var4];
					if ((var5) == (null)){

					}else{
						BufferedImage var6 = this.elementToImage(var5);
						var1.getGraphics().drawImage((Image)(var6),0,0,(ImageObserver)(null));
					}
					var4++;
					continue;
				}else{
					break;
				}
			} while (true);
			BufferedImage var5_1 = this.elementToImage(this.getBase());
			var1.getGraphics().drawImage((Image)(var5_1),0,0,(ImageObserver)(null));
			JollyRogerElement[] var6_1 = this.details;
			var4 = var6_1.length;
			int var7 = 0;
			do{
				if ((var7) < (var4)){
					JollyRogerElement var8 = var6_1[var7];
					if ((var8) == (null)){

					}else{
						BufferedImage var9 = this.elementToImage(var8);
						var1.getGraphics().drawImage((Image)(var9),0,0,(ImageObserver)(null));
					}
					var7++;
					continue;
				}else{
					break;
				}
			} while (true);
			return (Optional<BufferedImage>) (Optional) (Optional.of(var1));
		}catch(IOException var1_1){
			MineMineNoMi.LOGGER.error(var1_1.getMessage());
			return (Optional<BufferedImage>) (Optional) (Optional.empty());
		}
	}
	private BufferedImage elementToImage(JollyRogerElement var1) throws IOException {
		String var2 = "assets/mineminenomi/";
		String var3 = var2;
		String var4 = var1.getTexture().getPath();
		String var5 = var3 + var4;
		BufferedImage var6 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(var5));
		if (var1.canBeColored()){
			var6 = this.applyColorToImage(var1.getColor(),var6);
		}
		return var6;
	}
	private BufferedImage applyColorToImage(Color var1, BufferedImage var2) {
		int var3 = 0;
		LOOP_1:
		do{
			if ((var3) < (var2.getWidth())){
				int var4 = 0;
				do{
					if ((var4) < (var2.getHeight())){
						int var5 = var2.getRGB(var3,var4);
						Color var6 = new Color(var5,true);
						if (((var6.getAlpha()) != (0)) && (((var5) & (16777215)) != (0))){
							var2.setRGB(var3,var4,tintABGRPixel(var6.getRGB(),var1).intValue());
						}
						var4++;
						continue;
					}else{
						break;
					}
				} while (true);
				var3++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public static Integer tintABGRPixel(int var0, Color var1) {
		int var2 = ((var0) >> (16)) & (255);
		int var3 = ((var0) >> (8)) & (255);
		int var4 = (((2126) * (var2)) + ((7252) * (var3))) + ((722) * ((var0) & (255)));
		int var5 = (int)((((long)((var1.getBlue()) * (var4))) * (1766117501L)) >> (52));
		int var6 = (int)((((long)((var1.getGreen()) * (var4))) * (1766117501L)) >> (52));
		return Integer.valueOf(((((((var0) >> (24)) & (255)) << (24)) | ((var5) & (255))) | (((var6) & (255)) << (8))) | ((((int)((((long)((var1.getRed()) * (var4))) * (1766117501L)) >> (52))) & (255)) << (16)));
	}
	@OnlyIn(value=Dist.CLIENT)
	public void render(PoseStack var1, int var2, int var3, float var4) {
		JollyRogerElement[] var5 = this.getBackgrounds();
		int var6 = var5.length;
		int var7 = 0;
		do{
			if ((var7) < (var6)){
				JollyRogerElement var8 = var5[var7];
				if (((var8) != (null)) && ((var8.getTexture()) != (null))){
					this.drawElement(var8,var1,var2,var3,var4,true);
				}
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
		if (((this.getBase()) != (null)) && ((this.getBase().getTexture()) != (null))){
			this.drawElement(this.getBase(),var1,var2,var3,var4,true);
		}
		JollyRogerElement[] var8_1 = this.getDetails();
		var6 = var8_1.length;
		var7 = 0;
		do{
			if ((var7) < (var6)){
				JollyRogerElement var9 = var8_1[var7];
				if (((var9) != (null)) && ((var9.getTexture()) != (null))){
					this.drawElement(var9,var1,var2,var3,var4,true);
				}
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	@OnlyIn(value=Dist.CLIENT)
	public void drawElement(JollyRogerElement var1, PoseStack var2, int var3, int var4, float var5, boolean var6) {
		TexturedRectUI var7 = var1.getTexturedRect();
		float var8 = 1F;
		float var9 = 1F;
		float var10 = 1F;
		if (var1.canBeColored()){
			var8 = var1.getRedF();
			var9 = var1.getGreenF();
			var10 = var1.getBlueF();
		}
		var7.setFlipX(var1.isFlippedVertically());
		var7.setFlipY(var1.isFlippedHorizontally());
		var7.setScale(var5);
		if (var6){
			RenderSystem.setShaderColor(var8,var9,var10,1F);
		}
		var7.draw(var2,(float)(var3),(float)(var4));
		RenderSystem.setShaderColor(1F,1F,1F,1F);
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return l0.group((App)(CompoundTag.CODEC.fieldOf("data").forGetter((Function)(((Function<JollyRoger, CompoundTag>)(JollyRoger::write)))))).apply((Applicative)(l0),(Function)(((Function<CompoundTag, JollyRoger>)(JollyRoger::from))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
}