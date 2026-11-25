package xyz.pixelatedw.mineminenomi.api.crew;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.data.triggers.JoinCrewTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.LeaveCrewTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SetCrewCaptainTrigger;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;

public class Crew {
	// Fields
	private String name;
	private boolean isTemporary;
	private long creationDate;
	private List<Crew$Member> members;
	private JollyRoger jollyRoger;

	Crew() {
		this.members = new ArrayList();
		this.jollyRoger = new JollyRoger();
	}
	public Crew(String var1, LivingEntity var2) {
		this(var1,var2.getUUID(),var2.getDisplayName().getString(),Instant.now().getEpochSecond());
	}
	public static Crew from(CompoundTag var0) {
		Crew var1 = new Crew();
		var1.read(var0);
		return var1;
	}
	public Crew(String var1, UUID var2, String var3, long var4) {
		this.members = new ArrayList();
		this.jollyRoger = new JollyRoger();
		this.name = var1;
		this.isTemporary = true;
		this.addMember(var2,var3).setIsCaptain(true);
		this.creationDate = var4;
	}
	public void setName(String var1) {
		this.name = var1;
	}
	public String getName() {
		return this.name;
	}
	public boolean isTemporary() {
		return this.isTemporary;
	}
	public Crew$Member addMember(LivingEntity var1, boolean var2) {
		Crew$Member var3 = this.addMember(var1.getUUID(),var1.getDisplayName().getString(),var2);
		if (((var3) != (null)) && (var1 instanceof ServerPlayer)){
			ServerPlayer var4 = ((ServerPlayer)(var1));
			((JoinCrewTrigger)(ModAdvancements.JOIN_CREW.get())).trigger(var4);
		}
		return var3;
	}
	public Crew$Member addMember(UUID var1, String var2) {
		return this.addMember(var1,var2,true);
	}
	public Crew$Member addMember(UUID var1, String var2, boolean var3) {
		Crew$Member var4 = new Crew$Member(var1,var2).setSaved(var3);
		this.members.add(var4);
		return var4;
	}
	public void removeMember(Level var1, UUID var2) {
		Crew$Member var3 = this.getMember(var2);
		if ((var3) == (null)){
			return;
		}else{
			if (var3.isCaptain()){
				Predicate<Crew$Member> var4 = (l0) -> {
			return !(l0.isCaptain());
		};
				Optional var5 = this.getMembers().stream().filter(var4).findFirst();
				if (var5.isPresent()){
					var3.setIsCaptain(false);
					((Crew$Member)(var5.get())).setIsCaptain(true);
					Player var6 = var1.getPlayerByUUID(var3.getUUID());
					if (((var6) != (null)) && (var6 instanceof ServerPlayer)){
						ServerPlayer var7 = ((ServerPlayer)(var6));
						((LeaveCrewTrigger)(ModAdvancements.LEAVE_CREW.get())).trigger(var7);
					}
					Player var7_1 = var1.getPlayerByUUID(((Crew$Member)(var5.get())).getUUID());
					if (((var7_1) != (null)) && (var7_1 instanceof ServerPlayer)){
						ServerPlayer var8 = ((ServerPlayer)(var7_1));
						((SetCrewCaptainTrigger)(ModAdvancements.SET_CREW_CAPTAIN.get())).trigger(var8);
					}
					var3.markedForDeletion = true;
				}else{
					Iterator var6_1 = this.members.iterator();
					do{
						if (var6_1.hasNext()){
							Crew$Member var7_2 = ((Crew$Member)(var6_1.next()));
							var7_2.markedForDeletion = true;
							continue;
						}else{
							break;
						}
					} while (true);
				}
			}else{
				var3.markedForDeletion = true;
			}
			return;
		}
	}
	@Nullable()
	public @Nullable() Crew$Member getMember(UUID var1) {
		UUID var2 = var1;
		final UUID var2_f1 = var2;
		Predicate<Crew$Member> var3 = (l0) -> {
			return l0.getUUID().equals(var2_f1);
		};
		return ((Crew$Member)(((java.util.Optional)(((java.util.stream.Stream)(this.members.stream())).filter((Predicate)(var3)).findFirst())).orElse((Object)(null))));
	}
	public boolean hasMember(UUID var1) {
		return (this.getMember(var1)) != (null);
	}
	public void create(Level var1) {
		this.isTemporary = false;
	}
	@Nullable()
	public @Nullable() Crew$Member getCaptain() {
		return ((Crew$Member)(((java.util.Optional)(((java.util.stream.Stream)(this.members.stream())).filter((Predicate)(((java.util.function.Predicate)(((Predicate<Crew$Member>)(Crew$Member::isCaptain)))))).findFirst())).orElse((Object)(null))));
	}
	public boolean isEmpty() {
		return (this.getMembers().size()) <= (0);
	}
	public List<Crew$Member> getMembers() {
		return this.members;
	}
	public JollyRoger getJollyRoger() {
		return this.jollyRoger;
	}
	public void setJollyRoger(JollyRoger var1) {
		this.jollyRoger = var1;
	}
	public long getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(long var1) {
		this.creationDate = var1;
	}
	public void tick() {
		Iterator var1 = this.members.iterator();
		do{
			if (var1.hasNext()){
				if (((Crew$Member)(var1.next())).markedForDeletion){
					var1.remove();
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public CompoundTag write() {
		CompoundTag var1 = new CompoundTag();
		var1.putString("name",this.getName());
		ListTag var2 = new ListTag();
		Iterator var3 = this.getMembers().iterator();
		do{
			if (var3.hasNext()){
				Crew$Member var4 = ((Crew$Member)(var3.next()));
				if (!(var4.isSaved())){
					continue;
				}else{
					CompoundTag var5 = new CompoundTag();
					var5.putUUID("id",var4.getUUID());
					var5.putString("username",var4.getUsername());
					var5.putBoolean("isCaptain",var4.isCaptain());
					var2.add(var5);
					continue;
				}
			}else{
				break;
			}
		} while (true);
		var1.put("members",(Tag)(var2));
		var1.put("jollyRoger",(Tag)(this.jollyRoger.write()));
		var1.putLong("creationDate",this.creationDate);
		return var1;
	}
	public void read(CompoundTag var1) {
		this.setName(var1.getString("name"));
		ListTag var2 = var1.getList("members",10);
		int var3 = 0;
		do{
			if ((var3) < (var2.size())){
				CompoundTag var4 = var2.getCompound(var3);
				this.addMember(var4.getUUID("id"),var4.getString("username")).setIsCaptain(var4.getBoolean("isCaptain"));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		this.jollyRoger = JollyRoger.from(var1.getCompound("jollyRoger"));
		Long var4_1 = Long.valueOf(var1.getLong("creationDate"));
		if ((var4_1.longValue()) == (0L)){
			this.creationDate = Instant.now().getEpochSecond();
			return;
		}else{
			this.creationDate = var4_1.longValue();
			return;
		}
	}
}