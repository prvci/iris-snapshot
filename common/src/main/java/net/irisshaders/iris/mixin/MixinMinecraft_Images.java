package net.irisshaders.iris.mixin;

import net.irisshaders.iris.Iris;
import net.irisshaders.iris.platform.IrisPlatformHelpers;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.renderer.texture.TextureResources;
import net.minecraft.resources.Identifier;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * This Mixin is responsible for registering the "widgets" texture used in Iris' GUI's.
 * Normally Fabric API would do this automatically, but we don't use it here, so it must be done manually.
 */
@Mixin(Minecraft.class)
public class MixinMinecraft_Images {
	@Inject(method = "<init>", at = @At("TAIL"))
	private void iris$setupImages(GameConfig arg, CallbackInfo ci) {
		if (!IrisPlatformHelpers.getInstance().isModLoaded("fabric-resource-loader-v0")) {
			try {
				byte[] bytes = IOUtils.toByteArray(Iris.class.getResourceAsStream("/assets/iris/textures/gui/widgets.png"));
				ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length).put(bytes).flip();
				try (NativeImage image = NativeImage.read(buffer)) {
					Minecraft.getInstance().getTextureManager().register(Identifier.fromNamespaceAndPath("iris", "textures/gui/widgets.png"), TextureResources.from2dImage(() -> "Iris widgets", image));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
