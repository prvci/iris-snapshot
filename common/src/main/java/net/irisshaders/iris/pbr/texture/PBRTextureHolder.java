package net.irisshaders.iris.pbr.texture;

import net.irisshaders.iris.gl.texture.AbstractTexture;
import org.jetbrains.annotations.NotNull;

public interface PBRTextureHolder {
	@NotNull
	AbstractTexture normalTexture();

	@NotNull
	AbstractTexture specularTexture();
}
