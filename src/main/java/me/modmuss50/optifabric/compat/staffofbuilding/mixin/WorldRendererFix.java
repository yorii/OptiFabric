package me.modmuss50.optifabric.compat.staffofbuilding.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.shape.VoxelShape;

import me.modmuss50.optifabric.compat.DevOnly;

@DevOnly
@Mixin(WorldRenderer.class)
abstract class WorldRendererFix {
	//Work around for https://github.com/SpongePowered/Mixin/issues/431, only needs to be done in dev (as it's a remapping problem)	
	private static void method_3291(MatrixStack matrices, VertexConsumer vertices, VoxelShape shape, double x, double y, double z, float r, float g, float b, float a) {
		drawShapeOutline(matrices, vertices, shape, x, y, z, r, g, b, a);
	}

	@Shadow(remap = false) //Avoid remapping to allow building
	private static native void drawShapeOutline(MatrixStack matrices, VertexConsumer vertices, VoxelShape shape, double x, double y, double z, float r, float g, float b, float a);
}