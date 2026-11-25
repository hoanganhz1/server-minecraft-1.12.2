package xyz.pixelatedw.mineminenomi.data.entity;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockChallengeTrigger;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;

public class ChallengeData implements INBTSerializable<CompoundTag> {
	// Fields
	private Player owner;
	private boolean isArenaDirty;
	private ChallengeCore<?> previousChallenge;
	private ArenaStyle previousArenaStyle;
	private String previousArenaClass;
	private UUID groupOwner;
	private List<UUID> group;
	private List<Challenge> challenges;
	private List<ChallengeInvitation> invitations;

	public static ChallengeData createCapability(IAttachmentHolder var0) {
		return new ChallengeData(((Player)(var0)));
	}
	public static Optional<ChallengeData> get(Player var0) {
		if (!(var0.hasData(ModAttachments.CHALLENGE))){
			var0.setData(ModAttachments.CHALLENGE,new ChallengeData(var0));
		}
		return (Optional<ChallengeData>) (Optional) (var0.getExistingData(ModAttachments.CHALLENGE));
	}
	public static ChallengeData getOrCreate(Player var0) {
		if (!(var0.hasData(ModAttachments.CHALLENGE))){
			var0.setData(ModAttachments.CHALLENGE,new ChallengeData(var0));
		}
		return ((ChallengeData)(var0.getData(ModAttachments.CHALLENGE)));
	}
	private ChallengeData(Player var1) {
		this.group = new ArrayList();
		this.challenges = new ArrayList();
		this.invitations = new ArrayList();
		this.owner = var1;
	}
	public boolean isInGroup() {
		return (this.groupOwner) != (null);
	}
	public void setInGroup(@Nullable() UUID var1) {
		this.groupOwner = var1;
	}
	public ImmutableList<UUID> getGroupMembersIds() {
		return (ImmutableList<UUID>) (ImmutableList) (ImmutableList.copyOf((Collection)(this.group)));
	}
	public boolean isGroupMember(UUID var1) {
		return this.group.contains(var1);
	}
	public void removeGroupMember(UUID var1) {
		this.group.remove(var1);
	}
	public void addGroupMember(Player var1) {
		if ((var1) == (null)){
			return;
		}else{
			ChallengeData var2 = this;
			Consumer<ChallengeData> var3 = (l0) -> {
			l0.setInGroup(this.owner.getUUID());
		};
			((java.util.Optional)(get(var1))).ifPresent((Consumer)(var3));
			this.group.add(var1.getUUID());
			return;
		}
	}
	public ImmutableList<ChallengeInvitation> getInvitations() {
		return (ImmutableList<ChallengeInvitation>) (ImmutableList) (ImmutableList.copyOf((Collection)(this.invitations)));
	}
	public Optional<ChallengeInvitation> getInvitationFrom(UUID var1) {
		Iterator var2 = this.invitations.iterator();
		do{
			if (var2.hasNext()){
				ChallengeInvitation var3 = ((ChallengeInvitation)(var2.next()));
				if (var3.getSenderId().equals(var1)){
					return (Optional<ChallengeInvitation>) (Optional) (Optional.ofNullable(var3));
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return (Optional<ChallengeInvitation>) (Optional) (Optional.empty());
	}
	public boolean hasInvitationFrom(UUID var1) {
		Iterator var2 = this.invitations.iterator();
		do{
			if (var2.hasNext()){
				if (((ChallengeInvitation)(var2.next())).getSenderId().equals(var1)){
					return true;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public boolean hasInvitationFrom(Player var1) {
		return this.hasInvitationFrom(var1.getUUID());
	}
	public void addInvitation(ChallengeInvitation var1) {
		if (this.hasInvitationFrom(var1.getSenderId())){
			return;
		}else{
			this.invitations.add(var1);
			return;
		}
	}
	public void removeInvitationFrom(Player var1) {
		Iterator var2 = this.invitations.iterator();
		do{
			if (var2.hasNext()){
				if (((ChallengeInvitation)(var2.next())).getSenderId().equals(var1.getUUID())){
					var2.remove();
					return;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public void tickInvitations() {
		if (this.invitations.isEmpty()){
			return;
		}else{
			Level var1 = this.owner.getCommandSenderWorld();
			long var2 = var1.getGameTime();
			ChallengesWorldData var3 = ChallengesWorldData.get();
			Iterator var4 = this.invitations.iterator();
			do{
				if (var4.hasNext()){
					ChallengeInvitation var5 = ((ChallengeInvitation)(var4.next()));
					Player var6 = var5.getSender(var1);
					if (!(var1.players().contains(var6))){
						WyDebug.debug("Removed invitation due to missing sender");
						var4.remove();
						continue;
					}else{
						if (var5.isExpired(var1)){
							WyDebug.debug("Removed invitation due to timeout");
							var4.remove();
							continue;
						}else{
							if ((var3.getInProgressChallengeFor((LivingEntity)(var6))) != (null)){
								WyDebug.debug("Removed invitation due to it already starting");
								var4.remove();
								continue;
							}else{
								continue;
							}
						}
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public ChallengeCore<?> getPreviousChallenge() {
		return (ChallengeCore<?>) (ChallengeCore) (this.previousChallenge);
	}
	public ArenaStyle getPreviousArenaStyle() {
		return this.previousArenaStyle;
	}
	public String getPreviousArenaClass() {
		return this.previousArenaClass;
	}
	public void setPreviousChallenge(ChallengeCore<?> var1, ArenaStyle var2, String var3) {
		this.previousChallenge = var1;
		this.previousArenaStyle = var2;
		this.previousArenaClass = var3;
	}
	public boolean isArenaDirty() {
		return this.isArenaDirty;
	}
	public void markArenaDirty(boolean var1) {
		this.isArenaDirty = var1;
	}
	public boolean addChallenge(Challenge var1) {
		if (this.hasChallenge(var1.getCore())){
			return false;
		}else{
			this.challenges.add(var1);
			Player var2 = this.owner;
			if (var2 instanceof ServerPlayer){
				ServerPlayer var3 = ((ServerPlayer)(var2));
				((UnlockChallengeTrigger)(ModAdvancements.UNLOCK_CHALLENGE.get())).trigger(var3,var1.getCore());
			}
			return true;
		}
	}
	public boolean addChallenge(ChallengeCore<?> var1) {
		if (this.hasChallenge(var1)){
			return false;
		}else{
			return this.addChallenge(var1.createChallenge());
		}
	}
	public boolean removeChallenge(ChallengeCore<?> var1) {
		if (!(this.hasChallenge(var1))){
			return false;
		}else{
			this.challenges.removeIf((Predicate)(((Predicate<Challenge>)((l0) -> {
			return ((java.lang.Object)(l0.getCore())).equals((Object)(var1));
		}))));
			return true;
		}
	}
	public boolean hasChallenge(ChallengeCore<?> var1) {
		return (this.getChallenge(var1)) != (null);
	}
	public boolean isChallengeCompleted(ChallengeCore<?> var1) {
		Challenge var2 = this.getChallenge(var1);
		if ((var2) == (null)){
			return false;
		}else{
			return var2.isComplete();
		}
	}
	@Nullable()
	public <T extends Challenge> @Nullable() T getChallenge(ChallengeCore<?> var1) {
		ChallengeCore<?> var2 = var1;
		final ChallengeCore<?> var2_f1 = var2;
		Predicate<Challenge> var3 = (l0) -> {
			return ((java.lang.Object)(l0.getCore())).equals((Object)(var2_f1));
		};
		return (T) (((Challenge)(((java.util.Optional)(((java.util.stream.Stream)(this.challenges.stream())).filter((Predicate)(var3)).findFirst())).orElse((Object)(null)))));
	}
	public List<Challenge> getChallenges() {
		Predicate<Challenge> var1 = (l0) -> {
			return (l0) != (null);
		};
		return ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.challenges.stream())).filter((Predicate)(var1)))).collect((Collector)(Collectors.toList()))));
	}
	public int countChallenges() {
		this.challenges.removeIf((Predicate)(((Predicate<Challenge>)((l0) -> {
			return (l0) == (null);
		}))));
		return this.challenges.size();
	}
	public void clearChallenges() {
		this.challenges.clear();
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		var2.putBoolean("isArenaDirty",this.isArenaDirty());
		if ((this.getPreviousChallenge()) != (null)){
			var2.putString("previousChallenge",this.getPreviousChallenge().getRegistryKey().toString());
			var2.putString("previousArenaStyle",this.getPreviousArenaStyle().name());
			var2.putString("previousArenaClass",this.getPreviousArenaClass());
		}
		ListTag var3 = new ListTag();
		int var4 = 0;
		do{
			if ((var4) < (this.getChallenges().size())){
				Challenge var5 = ((Challenge)(this.getChallenges().get(var4)));
				CompoundTag var6 = new CompoundTag();
				var6.putString("id",var5.getCore().getRegistryKey().toString());
				var5.save(var6);
				var3.add(var6);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		var2.put("challenges",(Tag)(var3));
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		this.clearChallenges();
		try{
			this.markArenaDirty(var2.getBoolean("isArenaDirty"));
			ChallengeCore var3 = ((ChallengeCore)(WyRegistry.CHALLENGES.get(ResourceLocation.parse(var2.getString("previousChallenge")))));
			ArenaStyle var4 = ArenaStyle.valueOf(var2.getString("previousArenaStyle"));
			this.setPreviousChallenge(var3,var4,var2.getString("previousArenaClass"));
		}catch(Exception var3_1){

		}
		ListTag var3_2 = var2.getList("challenges",10);
		int var4_1 = 0;
		do{
			if ((var4_1) < (var3_2.size())){
				try{
					CompoundTag var5 = var3_2.getCompound(var4_1);
					ChallengeCore var6 = ((ChallengeCore)(WyRegistry.CHALLENGES.get(ResourceLocation.parse(var5.getString("id")))));
					if ((var6) == (null)){

					}else{
						Challenge var7 = var6.createChallenge();
						var7.load(var5);
						this.addChallenge(var7);
					}
				}catch(Exception var6_1){

				}
				var4_1++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}