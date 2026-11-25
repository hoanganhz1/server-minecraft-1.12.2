package xyz.pixelatedw.mineminenomi.api.entities;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public abstract class NuVerticalLightningEntity extends NuLightningEntity {
	// Fields
	public final TargetPredicate verticalLightningTargetsCheck;

	public NuVerticalLightningEntity(EntityType<? extends NuVerticalLightningEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
		NuVerticalLightningEntity var3 = this;
		Predicate<Entity> var4 = (l0) -> {
			if (((l0) != (this)) && ((l0) != (this.getOwner()))){
				if ((!(l0 instanceof ExperienceOrb)) && (!(l0 instanceof ItemEntity))){
					if (l0 instanceof Projectile){
						Projectile lv1_2 = ((Projectile)(l0));
						if (((lv1_2.getOwner()) != (null)) && ((lv1_2.getOwner()) == (this.getOwner()))){
							return false;
						}else{
							return true;
						}
					}else{
						return true;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		};
		this.verticalLightningTargetsCheck = new TargetPredicate().selector(ModEntityPredicates.IS_ALIVE_AND_SURVIVAL.and(var4));
	}
	public NuVerticalLightningEntity(EntityType<? extends NuVerticalLightningEntity> var1, LivingEntity var2, double var3, double var4, double var5, float var6, float var7, IAbility var8) {
		this(var1,var2,var3,var4,var5,var6,var7,var8,var8.getCore().getSourceElement(),var8.getCore().getSourceHakiNature(),var8.getCore().getSourceTypesArray());
	}
	public NuVerticalLightningEntity(EntityType<? extends NuVerticalLightningEntity> var1, LivingEntity var2, double var3, double var4, double var5, float var6, float var7, @Nullable() IAbility var8, SourceElement var9, SourceHakiNature var10, SourceType... var11) {
		super((EntityType)(var1),var2,var3,var4,var5,0F,90F,var6,var7,var8,var9,var10,var11);
		NuVerticalLightningEntity var12 = this;
		Predicate<Entity> var13 = (l0) -> {
			if (((l0) != (this)) && ((l0) != (this.getOwner()))){
				if ((!(l0 instanceof ExperienceOrb)) && (!(l0 instanceof ItemEntity))){
					if (l0 instanceof Projectile){
						Projectile lv1_2 = ((Projectile)(l0));
						if (((lv1_2.getOwner()) != (null)) && ((lv1_2.getOwner()) == (this.getOwner()))){
							return false;
						}else{
							return true;
						}
					}else{
						return true;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		};
		this.verticalLightningTargetsCheck = new TargetPredicate().selector(ModEntityPredicates.IS_ALIVE_AND_SURVIVAL.and(var13));
	}
	public TargetPredicate getTargetCheck() {
		return this.verticalLightningTargetsCheck;
	}
}