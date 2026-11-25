package xyz.pixelatedw.mineminenomi.abilities.karu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;

public class KarmaAbility extends PassiveAbility {
	// Fields
	private static final DecimalFormat FORMAT = new DecimalFormat("#0.0");
	public static final float MAX_KARMA = 100F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KarmaAbility>> INSTANCE;
	private final DamageTakenComponent damageTakenComponent;
	private float karma;
	private float prevKarma;

	public KarmaAbility(AbilityCore<KarmaAbility> var1) {
		super((AbilityCore)(var1));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnDamageEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.karma = 0F;
		this.prevKarma = 0F;
		AbilityComponent[] var2 = new AbilityComponent[]{this.damageTakenComponent};
		this.addComponents(var2);
		if (super.isClientSide()){
			GaugeComponent var3 = new GaugeComponent((IAbility)(this),(GaugeComponent$IGaugeRenderer)(((xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent$IGaugeRenderer)((GaugeComponent$IGaugeRenderer) ((GaugeComponent$IGaugeRenderer<KarmaAbility>) (this::renderGauge))))));
			AbilityComponent[] var4 = new AbilityComponent[]{var3};
			super.addComponents(var4);
		}
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((!(var3.is(DamageTypes.MAGIC))) && ((!(var3.is(DamageTypes.INDIRECT_MAGIC))) && (!((this.karma) >= (100F))))){
			if (!(IDamageSourceHandler.getHandler(var3).hasType(SourceType.INTERNAL))){
				this.addKarma(var1,var4);
			}
			return var4;
		}else{
			return var4;
		}
	}
	public void addKarma(LivingEntity var1, float var2) {
		this.prevKarma = this.karma;
		this.karma = Mth.clamp((this.karma) + (var2),0F,100F);
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			ModNetwork.sendTo(new SUpdateAbilityNBTPacket(var1,(IAbility)(this)),var3);
		}
	}
	public float getKarma() {
		return this.karma;
	}
	public void setPrevKarma(float var1) {
		this.prevKarma = var1;
	}
	public float getPrevKarma() {
		return this.prevKarma;
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putFloat("karma",this.karma);
	}
	public void loadAdditional(CompoundTag var1) {
		this.karma = var1.getFloat("karma");
	}
	@OnlyIn(value=Dist.CLIENT)
	public void renderGauge(Player var1, GuiGraphics var2, int var3, int var4, KarmaAbility var5) {
		RenderSystem.enableBlend();
		Minecraft var6 = Minecraft.getInstance();
		RenderSystem.setShaderTexture(0,ModResources.WIDGETS);
		RendererHelper.drawIcon(((AbilityCore)(IngaZarashiAbility.INSTANCE.get())).getIcon(),var2.pose(),(float)(var3),(float)((var4) - (38)),0F,32F,32F);
		String var7 = FORMAT.format((double)(var5.getKarma()));
		RendererHelper.drawStringWithBorder(Minecraft.getInstance().font,var2,var7,((var3) + (16)) - ((var6.font.width(var7)) / (2)),(var4) - (25),Color.WHITE.getRGB());
		RenderSystem.disableBlend();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Converts received damage into karma."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KarmaAbility::new)))).addDescriptionLine(lv1_3).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("karma","Karma",var0));
	}
}