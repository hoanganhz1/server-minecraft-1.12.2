package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.ComponentNotRegisteredException;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;

public class AbilityComponent<A extends IAbility> {
	// Fields
	private int tickRate;
	private long timeToTick;
	private final AbilityComponentKey<?> key;
	private final A ability;
	private boolean isDisabled;
	private boolean isClientSided;
	private List<BonusManager> bonuses;
	private LivingEntity owner;

	public AbilityComponent(AbilityComponentKey<? extends AbilityComponent<A>> var1, A var2) {
		this.tickRate = 0;
		this.isDisabled = false;
		this.isClientSided = false;
		this.bonuses = new ArrayList();
		this.owner = null;
		this.key = (AbilityComponentKey) (var1);
		this.ability = var2;
	}
	public AbilityComponentKey<?> getKey() {
		return (AbilityComponentKey<?>) (AbilityComponentKey) (this.key);
	}
	public A getAbility() {
		return (A) (this.ability);
	}
	public Iterator<BonusManager> getBonusManagers() {
		return (Iterator<BonusManager>) (Iterator) (this.bonuses.iterator());
	}
	public void addBonusManager(BonusManager var1) {
		this.bonuses.add(var1);
	}
	public float getTpsFactor() {
		return TPSDelta.INSTANCE.getDeltaTime();
	}
	public void setTickRate(int var1) {
		this.tickRate = var1;
	}
	public final void tick(LivingEntity var1) {
		this.owner = var1;
		if (this.isDisabled){
			return;
		}else{
			long var2 = (this.timeToTick) - (1L);
			this.timeToTick = var2;
			if ((var2) <= (0L)){
				this.timeToTick = (long)(this.tickRate);
				this.doTick(var1);
			}
			return;
		}
	}
	protected void doTick(LivingEntity var1) {
	}
	public void postInit(IAbility var1) {
	}
	public boolean isDisabled() {
		return this.isDisabled;
	}
	public void setDisabled(boolean var1) {
		this.isDisabled = var1;
	}
	public boolean isClientSided() {
		return this.isClientSided;
	}
	public void setClientSide() {
		this.isClientSided = true;
	}
	public LivingEntity getOwner() {
		return this.owner;
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		if (!(this.bonuses.isEmpty())){
			ListTag var3 = new ListTag();
			Iterator var4 = this.bonuses.iterator();
			LOOP_1:
			do{
				if (var4.hasNext()){
					BonusManager var5 = ((BonusManager)(var4.next()));
					if ((var5.getBonuses().size()) <= (0)){
						continue;
					}else{
						CompoundTag var6 = new CompoundTag();
						var6.putUUID("id",var5.getId());
						ListTag var7 = new ListTag();
						Iterator var8 = var5.getBonuses().iterator();
						do{
							if (var8.hasNext()){
								Map.Entry var9 = ((Map.Entry)(var8.next()));
								CompoundTag var10 = new CompoundTag();
								var10.putUUID("id",((UUID)(var9.getKey())));
								var10.putString("name",((BonusManager$BonusValue)(var9.getValue())).getName());
								var10.putInt("type",((BonusManager$BonusValue)(var9.getValue())).getType().ordinal());
								var10.putFloat("value",((BonusManager$BonusValue)(var9.getValue())).getValue());
								var7.add(var10);
								continue;
							}else{
								break;
							}
						} while (true);
						var6.put("list",(Tag)(var7));
						var3.add(var6);
						continue LOOP_1;
					}
				}else{
					break;
				}
			} while (true);
			if (!(var3.isEmpty())){
				var2.put("bonuses",(Tag)(var3));
			}
		}
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		UUID var7 = null;
		ListTag var3 = var2.getList("bonuses",10);
		int var4 = 0;
		do{
			if ((var4) < (var3.size())){
				CompoundTag var5 = var3.getCompound(var4);
				UUID var6 = var5.getUUID("id");
				var7 = var6;
				final UUID var7_f1 = var7;
				Predicate<BonusManager> var8 = (l0) -> {
			return l0.getId().equals(var7_f1);
		};
				BonusManager var9 = ((BonusManager)(((java.util.Optional)(((java.util.stream.Stream)(this.bonuses.stream())).filter((Predicate)(var8)).findFirst())).orElse((Object)(null))));
				if ((var9) == (null)){

				}else{
					var9.clearBonuses();
					ListTag var10 = var5.getList("list",10);
					int var11 = 0;
					do{
						if ((var11) < (var10.size())){
							CompoundTag var12 = var10.getCompound(var11);
							UUID var13 = var12.getUUID("id");
							String var14 = var12.getString("name");
							BonusOperation var15 = BonusOperation.values()[var12.getInt("type")];
							var9.addBonus(var13,var14,var15,var12.getFloat("value"));
							var11++;
							continue;
						}else{
							break;
						}
					} while (true);
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void ensureIsRegistered() {
		if (!(this.getAbility().hasComponent(this.getKey()))){
			throw new ComponentNotRegisteredException(this);
		}else{
			return;
		}
	}
}