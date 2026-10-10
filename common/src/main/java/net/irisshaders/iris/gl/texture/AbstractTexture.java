package net.irisshaders.iris.gl.texture;

import com.mojang.renderpearl.api.textures.GpuSampler;
import com.mojang.renderpearl.api.textures.GpuTexture;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import net.minecraft.client.renderer.texture.TextureResources;

public abstract class AbstractTexture implements AutoCloseable {
	protected GpuTexture texture;
	protected GpuTextureView textureView;
	protected GpuSampler sampler;
	private TextureResources resources;

	protected void setResources(TextureResources resources) {
		this.resources = resources;
		this.texture = resources.texture();
		this.textureView = resources.textureView();
		this.sampler = resources.sampler();
	}

	public GpuTexture getTexture() {
		return texture;
	}

	public GpuTextureView getTextureView() {
		return textureView;
	}

	public TextureResources getResources() {
		return resources != null ? resources : new TextureResources(texture, textureView, sampler);
	}

	public void releaseTextures() {
		if (resources != null) {
			resources.close();
			resources = null;
		} else {
			if (textureView != null) textureView.close();
			if (texture != null) texture.close();
		}
		texture = null;
		textureView = null;
	}

	@Override
	public void close() {
		releaseTextures();
	}
}
