package net.irisshaders.iris.pbr.texture;

import net.minecraft.resources.Identifier;

import java.nio.file.Path;
import java.io.IOException;

public interface PBRDumpable {
	void dumpContents(Identifier id, Path path) throws IOException;

	Identifier getDefaultDumpLocation();
}
