package me.modmuss50.optifabric.compat.carpet.mixin;

import java.util.concurrent.CompletableFuture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.util.Either;

import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ChunkHolder.Unloaded;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.world.chunk.Chunk;

import me.modmuss50.optifabric.compat.InterceptingMixin;
import me.modmuss50.optifabric.compat.Shim;

@Mixin(ThreadedAnvilChunkStorage.class)
@InterceptingMixin("carpet/mixins/ThreadedAnvilChunkStorage_scarpetChunkCreationMixin")
abstract class ThreadedAnvilChunkStorageMixin {
	@Inject(method = "lambda$null$17", remap = false,
			at = @At(value = "INVOKE", remap = true,
						target = "Lnet/minecraft/server/world/ThreadedAnvilChunkStorage;convertToFullChunk(Lnet/minecraft/server/world/ChunkHolder;)Ljava/util/concurrent/CompletableFuture;",
						shift = Shift.AFTER))
	private void doOnChunkGenerated(ChunkHolder chunkHolder, Chunk chunk, CallbackInfoReturnable<CompletableFuture<Either<Chunk, Unloaded>>> call) {
		onChunkGenerated(chunkHolder, chunk, call);
	}

	@Shim
	private native void onChunkGenerated(ChunkHolder chunkHolder, Chunk chunk, CallbackInfoReturnable<CompletableFuture<Either<Chunk, Unloaded>>> call);
}