package net.irisshaders.iris.pbr.loader;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.textures.FilterMode;
import com.mojang.renderpearl.api.textures.GpuSampler;
import net.irisshaders.iris.gl.texture.AbstractTexture;
import net.irisshaders.iris.gl.texture.DynamicTexture;
import net.irisshaders.iris.pbr.texture.PBRType;
import net.irisshaders.iris.vertices.ImmediateState;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class SimplePBRLoader implements PBRTextureLoader<Identifier> {
	@Override
	public void load(Identifier location, ResourceManager resourceManager, PBRTextureConsumer consumer) {
		AbstractTexture normal = createPBRTexture(location, resourceManager, PBRType.NORMAL);
		AbstractTexture specular = createPBRTexture(location, resourceManager, PBRType.SPECULAR);
		if (normal != null) consumer.acceptNormalTexture(normal);
		if (specular != null) consumer.acceptSpecularTexture(specular);
	}

	@Nullable
	private AbstractTexture createPBRTexture(Identifier location, ResourceManager resourceManager, PBRType type) {
		Identifier pbrLocation = location.withPath(type::appendSuffix);
		ImmediateState.temporarilyIgnorePass = true;
		try {
			TextureContents contents = TextureContents.load(resourceManager, pbrLocation);
			GpuSampler sampler = contents.clamp()
				? RenderSystem.getSamplerCache().getClampToEdge(contents.blur() ? FilterMode.LINEAR : FilterMode.NEAREST)
				: RenderSystem.getSamplerCache().getRepeat(contents.blur() ? FilterMode.LINEAR : FilterMode.NEAREST);
			return new DynamicTexture(pbrLocation::toString, contents.image(), sampler);
		} catch (IOException e) {
			return null;
		} finally {
			ImmediateState.temporarilyIgnorePass = false;
		}
	}
}
