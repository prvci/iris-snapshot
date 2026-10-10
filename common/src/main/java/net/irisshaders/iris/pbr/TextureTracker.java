package net.irisshaders.iris.pbr;

import com.mojang.renderpearl.api.textures.GpuTextureView;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.gl.IrisRenderSystem;
import net.irisshaders.iris.gl.state.StateUpdateNotifiers;
import net.irisshaders.iris.gl.texture.TextureType;
import net.irisshaders.iris.pipeline.WorldRenderingPipeline;
import net.irisshaders.iris.gl.texture.AbstractTexture;
import org.jetbrains.annotations.Nullable;

public class TextureTracker {
	public static final TextureTracker INSTANCE = new TextureTracker();

	private static Runnable bindTextureListener;

	static {
		StateUpdateNotifiers.bindTextureNotifier = listener -> bindTextureListener = listener;
	}

	private final Int2ObjectMap<Object> textures = new Int2ObjectOpenHashMap<>();

	private boolean lockBindCallback;

	private TextureTracker() {
	}

	public void trackTexture(int id, Object texture) {
		textures.put(id, texture);
	}

	@Nullable
	public Object getTexture(int id) {
		return textures.get(id);
	}

	public void onDeleteTexture(int id) {
		textures.remove(id);
	}
}
