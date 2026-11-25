package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticle$Factory;

public class ModParticleTypes$Client {
	public static void registerParticleFactories(RegisterParticleProvidersEvent var0) {
		var0.registerSpecial(((ParticleType)(ModParticleTypes.YUKI.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.YUKI)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.YUKI2.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.YUKI2)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.YUKI3.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.YUKI3)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.PIKA.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.PIKA)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.MERA.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.MERA,8)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.MERA2.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.MERA2)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.MOKU.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.MOKU)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.MOKU2.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.MOKU2)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.SUNA.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.SUNA)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.SUNA2.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.SUNA2)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GASU.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GASU)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GASU2.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GASU2)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.BLUE_FLAME.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.BLUE_FLAME,8)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.DARKNESS.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.DARKNESS,8)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.DARKNESS_STATIC.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.DARKNESS_STATIC)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.KUROUZU.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.KUROUZU)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GORO.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GORO)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GORO2.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GORO2)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GORO3.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GORO3)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.MAGU.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.MAGU)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.DOKU.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.DOKU,6)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.DOKU_PINK.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.DOKU_PINK)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.ITO.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.ITO)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GURA.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GURA,4)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GURA2.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GURA2)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.HIE.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.HIE,8)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.HIE_FLAKES.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.HIE_FLAKES)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.MERO.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.MERO)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.HORU.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.HORU)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.CHIYU.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.CHIYU)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.RUST.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.RUST)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.AWA.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.AWA)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.AWA2.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.AWA2)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.AWA3.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.AWA3)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.AWA_FOAM.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.AWA_FOAM)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.BETA.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.BETA)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.NETSU.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.NETSU)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.NETSU2.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.NETSU2)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GRILL.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GRILL)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.MEDIC.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.MEDIC)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GORO_YELLOW.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GORO_YELLOW)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GORO2_YELLOW.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GORO2_YELLOW)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.GORO3_YELLOW.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.GORO3_YELLOW)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.HANA.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.HANA)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.MAGNET.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.MAGNET)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.SIMPLE_CIRCLE.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.SIMPLE_CIRCLE)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.DOUBLE_CIRCLE.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.DOUBLE_CIRCLE)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.HOLE.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.HOLE)));
		var0.registerSpecial(((ParticleType)(ModParticleTypes.COMMAND_MARK.get())),(ParticleProvider)(new SimpleParticle$Factory(ModResources.COMMAND_MARK)));
	}
}