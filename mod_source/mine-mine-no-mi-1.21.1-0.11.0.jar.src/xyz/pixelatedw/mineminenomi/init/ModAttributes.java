package xyz.pixelatedw.mineminenomi.init;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.AttributeRegistryEntry;

public class ModAttributes {
	// Fields
	public static final Holder<Attribute> FALL_RESISTANCE;
	public static final Holder<Attribute> JUMP_HEIGHT;
	public static final Holder<Attribute> REGEN_RATE;
	public static final Holder<Attribute> PUNCH_DAMAGE;
	public static final Holder<Attribute> TIME_PROGRESSION;
	public static final Holder<Attribute> FLOATING_TIME;
	public static final Holder<Attribute> TOUGHNESS;
	public static final Holder<Attribute> GCD;
	public static final Holder<Attribute> MINING_SPEED;
	@Deprecated() public static final Holder<Attribute> FAUX_PROTECTION;
	public static final Holder<Attribute> FRICTION;

	public static void init() {
	}
	public static void changeAttributes(EntityAttributeModificationEvent var0) {
		Iterator var1 = var0.getTypes().iterator();
		do{
			if (var1.hasNext()){
				EntityType var2 = ((EntityType)(var1.next()));
				var0.add(var2,FALL_RESISTANCE);
				var0.add(var2,JUMP_HEIGHT);
				var0.add(var2,REGEN_RATE);
				var0.add(var2,PUNCH_DAMAGE);
				var0.add(var2,TIME_PROGRESSION);
				var0.add(var2,TOUGHNESS);
				var0.add(var2,FRICTION);
				if ((var2) == (EntityType.PLAYER)){
					var0.add(var2,MINING_SPEED);
					var0.add(var2,GCD,0D);
				}else{
					var0.add(var2,GCD,40D);
				}
				if ((var2) == (ModMobs.BIG_DUCK.get())){
					var0.add(var2,FLOATING_TIME);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	static  {
		AttributeRegistryEntry var0 = (l0) -> {
			return new RangedAttribute(l0,0D,-256D,256D);
		};
		FALL_RESISTANCE = ModRegistry.registerAttribute("Fall Resistance",var0);
		AttributeRegistryEntry var1 = (l0) -> {
			return new RangedAttribute(l0,1D,-256D,256D).setSyncable(true);
		};
		JUMP_HEIGHT = ModRegistry.registerAttribute("Jump Height",var1);
		AttributeRegistryEntry var2 = (l0) -> {
			return new RangedAttribute(l0,1D,0D,32D).setSyncable(true);
		};
		REGEN_RATE = ModRegistry.registerAttribute("Regen Rate",var2);
		AttributeRegistryEntry var3 = (l0) -> {
			return new RangedAttribute(l0,0D,-1024D,1024D).setSyncable(true);
		};
		PUNCH_DAMAGE = ModRegistry.registerAttribute("Punch Damage",var3);
		AttributeRegistryEntry var4 = (l0) -> {
			return new RangedAttribute(l0,1D,0D,1024D).setSyncable(true);
		};
		TIME_PROGRESSION = ModRegistry.registerAttribute("Time Progression",var4);
		AttributeRegistryEntry var5 = (l0) -> {
			return new RangedAttribute(l0,100D,0D,2048D).setSyncable(true);
		};
		FLOATING_TIME = ModRegistry.registerAttribute("Floating Time",var5);
		AttributeRegistryEntry var6 = (l0) -> {
			return new RangedAttribute(l0,0D,0D,20D).setSyncable(true);
		};
		TOUGHNESS = ModRegistry.registerAttribute("Toughness",var6);
		AttributeRegistryEntry var7 = (l0) -> {
			return new RangedAttribute(l0,40D,0D,72000D).setSyncable(true);
		};
		GCD = ModRegistry.registerAttribute("GCD",var7);
		AttributeRegistryEntry var8 = (l0) -> {
			return new RangedAttribute(l0,1D,-128D,128D).setSyncable(true);
		};
		MINING_SPEED = ModRegistry.registerAttribute("Mining Speed",var8);
		AttributeRegistryEntry var9 = (l0) -> {
			return new RangedAttribute(l0,0D,0D,20D).setSyncable(true);
		};
		FAUX_PROTECTION = ModRegistry.registerAttribute("Faux Protection",var9);
		AttributeRegistryEntry var10 = (l0) -> {
			return new RangedAttribute(l0,0D,0D,0.99D).setSyncable(true);
		};
		FRICTION = ModRegistry.registerAttribute("Friction",var10);
	}
}