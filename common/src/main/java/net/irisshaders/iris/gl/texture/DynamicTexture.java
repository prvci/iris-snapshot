package net.irisshaders.iris.gl.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.textures.GpuSampler;
import net.minecraft.client.renderer.texture.TextureResources;

import java.util.function.Supplier;

public class DynamicTexture extends AbstractTexture {
	private final NativeImage pixels;

	public DynamicTexture(Supplier<String> name, NativeImage pixels) {
		this.pixels = pixels;
		setResources(TextureResources.from2dImage(name, pixels));
	}

	public DynamicTexture(Supplier<String> name, NativeImage pixels, GpuSampler sampler) {
		this.pixels = pixels;
		setResources(TextureResources.from2dImage(name, pixels, sampler));
	}

	public NativeImage getPixels() {
		return pixels;
	}

	public void upload() {
		pixels.writeToGpuTexture(RenderSystem.getDevice().createCommandEncoder(), texture);
	}

	@Override
	public void close() {
		super.close();
		pixels.close();
	}
}
