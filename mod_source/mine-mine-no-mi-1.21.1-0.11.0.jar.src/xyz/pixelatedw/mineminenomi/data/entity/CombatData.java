package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.entities.ClientBossExtraEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.UpdateCombatStateEvent;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SUpdateCombatStatePacket;

public class CombatData implements INBTSerializable<CompoundTag> {
	// Fields
	private LivingEntity owner;
	private @Nullable() LivingEntity lastAttacker;
	private long lastAttackTime;
	private float storedDamage;
	private Map<UUID, ClientBossExtraEvent> extraInfos;
	private Set<HitTriggerComponent> hitTriggers;

	public static CombatData createCapability(IAttachmentHolder var0) {
		return new CombatData(((LivingEntity)(var0)));
	}
	public static Optional<CombatData> get(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.COMBAT))){
			var0.setData(ModAttachments.COMBAT,new CombatData(var0));
		}
		return (Optional<CombatData>) (Optional) (var0.getExistingData(ModAttachments.COMBAT));
	}
	public static CombatData getOrCreate(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.COMBAT))){
			var0.setData(ModAttachments.COMBAT,new CombatData(var0));
		}
		return ((CombatData)(var0.getData(ModAttachments.COMBAT)));
	}
	private CombatData(LivingEntity var1) {
		this.extraInfos = new HashMap();
		this.hitTriggers = new HashSet();
		this.owner = var1;
	}
	public void setInCombatCache(@Nullable() LivingEntity var1) {
		if ((var1) != (null)){
			this.lastAttackTime = 300L;
		}else{
			this.lastAttackTime = 0L;
		}
		if ((this.lastAttacker) != (var1)){
			this.lastAttacker = var1;
			int var2 = ((this.lastAttacker) != (null)) ? (1) : (0);
			UpdateCombatStateEvent var3 = new UpdateCombatStateEvent(this.owner,this.lastAttacker,(var2) != (0));
			NeoForge.EVENT_BUS.post((Event)(var3));
			LivingEntity var4 = this.owner;
			if (var4 instanceof ServerPlayer){
				ServerPlayer var5 = ((ServerPlayer)(var4));
				ModNetwork.sendTo(new SUpdateCombatStatePacket((LivingEntity)(this.lastAttacker)),(Player)(var5));
			}else{
				return;
			}
		}
	}
	public boolean isInCombatCache() {
		return (this.lastAttackTime) > (0L);
	}
	@Nullable()
	public @Nullable() LivingEntity getLastAttacker() {
		return this.lastAttacker;
	}
	public long getLastAttackTime() {
		return this.lastAttackTime;
	}
	public void tick() {
		if ((this.owner) == (null)){
			return;
		}else{
			int var1 = (((this.lastAttacker) != (null)) && (this.lastAttacker.isAlive())) ? (1) : (0);
			int var2 = (((var1) != (0)) && ((Math.abs(this.lastAttacker.distanceToSqr((Entity)(this.owner)))) <= (10000D))) ? (1) : (0);
			int var3 = (((var1) != (0)) && ((this.lastAttacker instanceof Mob) && ((((Mob)(this.lastAttacker)).getTarget()) == (this.owner)))) ? (1) : (0);
			if ((((((var1) == (0)) || (((var2) == (0)) || ((var3) == (0)))) ? (1) : (0)) != (0)) && ((this.lastAttackTime) > (0L))){
				this.lastAttackTime = (this.lastAttackTime) - (1L);
			}else{
				if (((this.lastAttackTime) <= (0L)) && ((this.lastAttacker) != (null))){
					this.setInCombatCache((LivingEntity)(null));
				}else{
					return;
				}
			}
			return;
		}
	}
	public Map<UUID, ClientBossExtraEvent> getExtraBossInfo() {
		return this.extraInfos;
	}
	public void addExtraBossInfo(UUID var1, ClientBossExtraEvent var2) {
		this.extraInfos.put(var1,var2);
	}
	public void setStoredDamage(float var1) {
		this.storedDamage = var1;
	}
	public float getStoredDamage() {
		return this.storedDamage;
	}
	public void setHitTriggers(Set<HitTriggerComponent> var1) {
		this.hitTriggers = var1;
	}
	public Set<HitTriggerComponent> getHitTriggers() {
		return this.hitTriggers;
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		return new CompoundTag();
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
	}
}