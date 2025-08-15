package io.budb.core;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BudEngineTest {
	BudExecutor budExecutor;

	@TempDir
	Path tempDir;

	@BeforeEach
	void setUp() throws IOException {
		budExecutor = new BudEngine(tempDir.toString());

	}

	@Test
	void putTest() throws IOException {
		String key = "testKey";
		byte[] value = "testValue".getBytes();

		budExecutor.put(key, value);

		assertThat(budExecutor.get(key)).isEqualTo(value);
	}

}