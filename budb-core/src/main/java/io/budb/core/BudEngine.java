package io.budb.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BudEngine implements BudExecutor {
	private final Path storagePath;

	public BudEngine(String dir) throws IOException {
		this.storagePath = Paths.get(dir);
		if (!Files.exists(storagePath)) {
			Files.createDirectories(storagePath);
		}
	}

	@Override
	public void put(String key, byte[] value) throws IOException {
		Path filePath = storagePath.resolve(key);
		Files.write(filePath, value);
	}

	@Override
	public byte[] get(String key) throws IOException {
		Path filePath = storagePath.resolve(key);
		if (Files.exists(filePath)) {
			return Files.readAllBytes(filePath);
		}
		return null;
	}
}
