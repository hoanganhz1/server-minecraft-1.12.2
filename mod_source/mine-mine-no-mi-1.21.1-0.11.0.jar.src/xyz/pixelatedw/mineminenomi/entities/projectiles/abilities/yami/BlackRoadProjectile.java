package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.abilities.yami.AbsorbedBlocksAbility;
import xyz.pixelatedw.mineminenomi.abilities.yami.LiberationAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IAfterBlockGenerationEvent;
import xyz.pixelatedw.mineminenomi.api.blockgen.IAfterPlaceBlockEvent;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BlackRoadProjectile extends NuProjectileEntity {
	// Fields
	private SimpleBlockPlacer placer;

	public BlackRoadProjectile(EntityType<? extends BlackRoadProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BlackRoadProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.BLACK_ROAD.get())),var1,var2,var3);
		this.setMaxLife(20);
		this.setDamage(10F);
		this.setPassThroughEntities();
		this.setPassThroughBlocks();
		Function<AbilityData, AbsorbedBlocksAbility> var4 = (l0) -> {
			return ((AbsorbedBlocksAbility)(l0.getPassiveAbility(((AbilityCore)(AbsorbedBlocksAbility.INSTANCE.get())))));
		};
		AbsorbedBlocksAbility var5 = ((AbsorbedBlocksAbility)(((java.util.Optional)(AbilityData.get(var2))).map((Function)(var4)).orElse(null)));
		AbsorbedBlocksAbility var6 = var5;
		final AbsorbedBlocksAbility var6_f1 = var6;
		IAfterPlaceBlockEvent var7 = (l0, l1, l2) -> {
			if ((var6_f1) != (null)){
				var6_f1.addMarkedBlock(l1,l0);
			}
		};
		AbsorbedBlocksAbility var8 = var5;
		LivingEntity var9 = var2;
		final AbsorbedBlocksAbility var8_f2 = var8;
		final LivingEntity var9_f3 = var9;
		IAfterBlockGenerationEvent var10 = (l0, l1) -> {
			if (((var8_f2) != (null)) && (var9_f3 instanceof Player)){
				Player lv3_4 = ((Player)(var9_f3));
				ModNetwork.sendTo(new SUpdateAbilityNBTPacket((LivingEntity)(lv3_4),(IAbility)(var8_f2)),lv3_4);
			}
		};
		this.placer = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.DARKNESS.get())).defaultBlockState()).setRule(LiberationAbility.MARK_ABSORB_RULE).setAfterPlaceEvent(var7).setAfterGenerationEvent(var10);
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onTickEvent() {
		BlockPos var1 = null;
		int var2 = 1;
		do{
			if ((var1) == (null)){
				if (!(this.level().getBlockState(this.blockPosition().below(var2)).isAir())){
					var1 = this.blockPosition().below(var2);
					break;
				}else{
					if ((var2) > (2)){
						break;
					}else{
						var2++;
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
		if ((var1) == (null)){
			return;
		}else{
			int var3 = Math.round((2F) + ((4F) * ((1F) - (((float)(this.getLife())) / ((float)(this.getMaxLife()))))));
			if ((this.placer) != (null)){
				this.placer.setSize(var3);
				this.placer.generate(this.level(),var1,BlockGenerators.SPHERE);
			}
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.DARK_MATTER_CHARGING.get())),(Entity)(this),(double)(var1.getX()),(double)(var1.getY()),(double)(var1.getZ()));
			return;
		}
	}
}