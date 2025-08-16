package io.budb.core;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.budb.core.exception.BucketConflictException;
import io.budb.core.exception.BucketNonExistsException;

class BudEngineTest {
	BudExecutor budExecutor;

	@TempDir
	Path tempDir;

	@BeforeEach
	void setUp() throws IOException {
		budExecutor = new BudEngine(tempDir.toString());

	}

	@Test
	void createBucketTest() throws IOException {
		var bucketName = "testBucket";

		assertThat(budExecutor.isExistBucket(bucketName)).isFalse();

		budExecutor.createBucket(bucketName);

		assertThat(budExecutor.isExistBucket(bucketName)).isTrue();
		assertThatThrownBy(() -> budExecutor.createBucket(bucketName))
			.isInstanceOf(BucketConflictException.class);
	}

	@Test
	void deleteBucketTest() throws IOException {
		var bucketName = "testBucket";
		budExecutor.createBucket(bucketName);

		assertThat(budExecutor.isExistBucket(bucketName)).isTrue();

		budExecutor.deleteBucket(bucketName);

		assertThat(budExecutor.isExistBucket(bucketName)).isFalse();

		assertThatThrownBy(() -> budExecutor.deleteBucket(bucketName))
			.isInstanceOf(BucketNonExistsException.class);
	}

	@Test
	void putTest() throws IOException {
		String key = "testKey";
		byte[] value = "testValue".getBytes();

		budExecutor.put(key, value);

		assertThat(budExecutor.get(key)).isEqualTo(value);
	}

}