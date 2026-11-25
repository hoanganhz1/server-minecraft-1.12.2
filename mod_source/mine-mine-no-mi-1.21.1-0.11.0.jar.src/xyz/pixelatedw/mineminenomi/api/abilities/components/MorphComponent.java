package xyz.pixelatedw.mineminenomi.api.abilities.components;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.IPartType;
import xyz.pixelatedw.mineminenomi.api.entities.PartEntityType;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeMorphPacket;

public class MorphComponent extends AbilityComponent<IAbility> {
	// Fields
	private MorphInfo morphInfo;
	private boolean isMorphed;
	private final HashMap<String, PartEntity<?>> parts;

	public MorphComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.MORPH.get())),var1);
		this.parts = (HashMap) (new HashMap());
	}
	public void startMorph(LivingEntity var1, MorphInfo var2) {
		if (this.isMorphed()){
			return;
		}else{
			this.ensureIsRegistered();
			this.morphInfo = var2;
			MorphInfo var3 = var2;
			final MorphInfo var3_f1 = var3;
			Consumer<DevilFruitData> var4 = (l0) -> {
			l0.addMorph(var3_f1);
		};
			((java.util.Optional)(DevilFruitData.get(var1))).ifPresent((Consumer)(var4));
			this.isMorphed = true;
			this.updateMorphSize(var1);
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SChangeMorphPacket((LivingEntity)(var1),(IAbility)(this.getAbility()),(MorphInfo)(this.morphInfo),true),var1);
			}
			return;
		}
	}
	public void updateMorphSize(LivingEntity var1) {
		var1.ejectPassengers();
		var1.refreshDimensions();
		if (!(var1.level().isClientSide)){
			((ServerLevel)(var1.level())).getChunkSource().broadcastAndSend((Entity)(var1),(Packet)(new ClientboundSetPassengersPacket((Entity)(var1))));
		}
	}
	public void stopMorph(LivingEntity var1) {
		if (!(this.isMorphed())){
			return;
		}else{
			MorphComponent var2 = this;
			Consumer<DevilFruitData> var3 = (l0) -> {
			l0.removeMorph(this.morphInfo);
		};
			((java.util.Optional)(DevilFruitData.get(var1))).ifPresent((Consumer)(var3));
			this.isMorphed = false;
			this.updateMorphSize(var1);
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SChangeMorphPacket((LivingEntity)(var1),(IAbility)(this.getAbility()),(MorphInfo)(this.morphInfo),false),var1);
			}
			this.morphInfo = null;
			this.removePartsFromWorld();
			this.parts.clear();
			return;
		}
	}
	@Nullable()
	public @Nullable() MorphInfo getMorphInfo() {
		return this.morphInfo;
	}
	public boolean isMorphed() {
		return this.isMorphed;
	}
	public HashMap<String, PartEntity<?>> getParts() {
		return new HashMap((Map)(this.parts));
	}
	public void addPart(String var1, PartEntity<? extends Entity> var2) {
		this.parts.put(var1,(PartEntity)(var2));
		Level var3 = var2.level();
		if (var3.isClientSide){
			Int2ObjectMap var4 = ((Int2ObjectMap)(WyHelper.getPrivateValue(ClientLevel.class,((ClientLevel)(var3)),"partEntities")));
			var4.put(var2.getId(),var2);
		}else{
			Int2ObjectMap var4_1 = ((Int2ObjectMap)(WyHelper.getPrivateValue(ServerLevel.class,((ServerLevel)(var3)),"dragonParts")));
			var4_1.put(var2.getId(),var2);
		}
	}
	private void removePartsFromWorld() {
		Iterator var1 = this.getParts().values().iterator();
		do{
			if (var1.hasNext()){
				PartEntity var2 = ((PartEntity)(var1.next()));
				Level var3 = var2.level();
				if (var3.isClientSide){
					Int2ObjectMap var4 = ((Int2ObjectMap)(WyHelper.getPrivateValue(ClientLevel.class,((ClientLevel)(var3)),"partEntities")));
					var4.remove(var2.getId());
					continue;
				}else{
					Int2ObjectMap var4_1 = ((Int2ObjectMap)(WyHelper.getPrivateValue(ServerLevel.class,((ServerLevel)(var3)),"dragonParts")));
					var4_1.remove(var2.getId());
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		if ((this.morphInfo) != (null)){
			ResourceLocation var3 = this.morphInfo.getKey();
			if ((var3) != (null)){
				var2.putString("morph",var3.toString());
			}
		}
		var2.putBoolean("isMorphed",this.isMorphed);
		CompoundTag var3_1 = new CompoundTag();
		Iterator var4 = this.parts.entrySet().iterator();
		do{
			if (var4.hasNext()){
				Map.Entry var5 = ((Map.Entry)(var4.next()));
				CompoundTag var6 = new CompoundTag();
				String var7 = ((String)(var5.getKey()));
				PartEntity var8 = ((PartEntity)(var5.getValue()));
				if (((var8) != (null)) && ((var8 instanceof IPartType) && ((((IPartType)(var8)).getPartType()) != (null)))){
					var6.putInt("parentId",var8.getParent().getId());
					var6.putString("partType",((IPartType)(var8)).getPartType().getKey().toString());
					var6.putInt("partId",var8.getId());
					var6.put("data",(Tag)(var8.serializeNBT(var1)));
					var3_1.put(var7,(Tag)(var6));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		var2.put("parts",(Tag)(var3_1));
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		if (var2.contains("morph")){
			ResourceLocation var3 = ResourceLocation.parse(var2.getString("morph"));
			this.morphInfo = ((MorphInfo)(WyRegistry.MORPHS.get(var3)));
		}
		this.isMorphed = var2.getBoolean("isMorphed");
		this.parts.clear();
		if (var2.contains("parts",10)){
			CompoundTag var3_1 = var2.getCompound("parts");
			Iterator var4 = var3_1.getAllKeys().iterator();
			do{
				if (var4.hasNext()){
					String var5 = ((String)(var4.next()));
					CompoundTag var6 = var3_1.getCompound(var5);
					int var7 = var6.getInt("parentId");
					Entity var8 = this.getOwner().level().getEntity(var7);
					if ((var8) == (null)){
						continue;
					}else{
						ResourceLocation var9 = ResourceLocation.parse(var6.getString("partType"));
						PartEntityType var10 = ((PartEntityType)(WyRegistry.PART_ENTITY_TYPES.get(var9)));
						if ((var10) == (null)){
							continue;
						}else{
							PartEntity var11 = var10.create(var8);
							int var12 = var6.getInt("partId");
							CompoundTag var13 = var6.getCompound("data");
							var11.setId(var12);
							var11.deserializeNBT(var1,var13);
							this.addPart(var5,var11);
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
		}
	}
}