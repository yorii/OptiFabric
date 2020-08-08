package me.modmuss50.optifabric.patcher;

import net.fabricmc.tinyremapper.IMappingProvider;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.OutputConsumerPath.Builder;
import net.fabricmc.tinyremapper.TinyRemapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RemapUtils {
	public static void mapJar(Path output, Path input, IMappingProvider mappings, List<Path> libraries) throws IOException {
		Files.deleteIfExists(output);

		TinyRemapper remapper = TinyRemapper.newRemapper().withMappings(mappings).renameInvalidLocals(true).rebuildSourceFilenames(true).build();

		try {
			OutputConsumerPath outputConsumer = new Builder(output).build();
			outputConsumer.addNonClassFiles(input);
			remapper.readInputs(input);

			for (Path path : libraries) {
				remapper.readClassPath(path);
			}

			remapper.apply(outputConsumer);
			outputConsumer.close();
		} catch (Exception e) {
			throw new RuntimeException("Failed to remap jar", e);
		} finally {
			remapper.finish();
		}
	}

}
