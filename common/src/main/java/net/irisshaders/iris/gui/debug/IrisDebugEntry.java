package net.irisshaders.iris.gui.debug;

import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.gui.option.IrisVideoSettings;
import net.irisshaders.iris.shadows.ShadowRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.gui.components.debug.DebugGroup;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

public class IrisDebugEntry implements DebugScreenEntry {
	private static final DebugGroup GROUP = DebugGroup.Builder.titled("Iris").build();

	@Override
	public void display(DebugScreenDisplayer debugScreenDisplayer, @Nullable Level level, @Nullable LevelChunk levelChunk, @Nullable LevelChunk levelChunk2) {
		debugScreenDisplayer.addToGroup(GROUP, "[" + Iris.MODNAME + "] Version: " + Iris.getFormattedVersion());

		if (Iris.getIrisConfig().areShadersEnabled()) {
			debugScreenDisplayer.addToGroup(GROUP, "[" + Iris.MODNAME + "] Shaderpack: " + Iris.getCurrentPackName() + (Iris.isFallback() ? " (fallback)" : ""));
			Iris.getCurrentPack().ifPresent(pack -> debugScreenDisplayer.addToGroup(GROUP, "[" + Iris.MODNAME + "] " + pack.getProfileInfo()));
			debugScreenDisplayer.addToGroup(GROUP, "[" + Iris.MODNAME + "] Color space: " + IrisVideoSettings.colorSpace.name());
			ShadowRenderer.ACTIVE = true;
			if (level != null) {
				debugScreenDisplayer.addToGroup(GROUP, "[" + Iris.MODNAME + "] Shadows: " + SodiumWorldRenderer.instance().getChunksDebugString());
			}
			ShadowRenderer.ACTIVE = false;
		} else {
			debugScreenDisplayer.addToGroup(GROUP, "[" + Iris.MODNAME + "] Shaders are disabled");
		}

		//messages.add(3, "Direct Buffers: +" + iris$humanReadableByteCountBin(iris$directPool.getMemoryUsed()));

	}
}
