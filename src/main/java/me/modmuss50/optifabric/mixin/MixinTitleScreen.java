package me.modmuss50.optifabric.mixin;

import me.modmuss50.optifabric.mod.OptifabricError;
import me.modmuss50.optifabric.mod.OptifineVersion;

import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class MixinTitleScreen extends Screen {

	@Shadow
	@Final
	private boolean doBackgroundFade;

	@Shadow
	private long backgroundFadeStart;

	protected MixinTitleScreen(Text component_1) {
		super(component_1);
	}

	@Inject(method = "init", at = @At("RETURN"))
	private void init(CallbackInfo info) {
		if (OptifabricError.hasError()) {
			client.openScreen(new ConfirmScreen(yes -> {
				if (yes) {
					Util.getOperatingSystem().open(OptifabricError.getErrorURL());
				} else {
					client.scheduleStop();
				}
			}, new LiteralText("There was an error loading OptiFabric!").formatted(Formatting.RED), new LiteralText(OptifabricError.getError()),
					new LiteralText(OptifabricError.getHelpButtonText()).formatted(Formatting.GREEN), new LiteralText("Close Game").formatted(Formatting.RED)));
		}
	}

	@Inject(method = "render", at = @At("RETURN"))
	private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		if (!OptifabricError.hasError()) {
			float fadeTime = this.doBackgroundFade ? (float) (Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0F : 1.0F;
			float fadeColor = this.doBackgroundFade ? MathHelper.clamp(fadeTime - 1.0F, 0.0F, 1.0F) : 1.0F;

			int alpha = MathHelper.ceil(fadeColor * 255.0F) << 24;
			if ((alpha & 0xFC000000) != 0) {
				textRenderer.drawWithShadow(matrices, OptifineVersion.version, 2, this.height - 20, 0xFFFFFF | alpha);
			}
		}
	}

}
