package me.modmuss50.optifabric.compat.clothesline.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.RenderLayer;

import me.modmuss50.optifabric.compat.DevOnly;

@DevOnly
@Mixin(BufferBuilderStorage.class)
abstract class BufferBuilderStorageFix {
	//Work around for https://github.com/SpongePowered/Mixin/issues/431, only needs to be done in dev (as it's a remapping problem)
	private static void method_23798(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferBuilder> builderStorage, RenderLayer layer) {
		assignBufferBuilder(builderStorage, layer);
	}

	@Shadow
	private static native void assignBufferBuilder(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferBuilder> builderStorage, RenderLayer layer);
}