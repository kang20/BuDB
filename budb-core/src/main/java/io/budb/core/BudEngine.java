package io.budb.core;

import static io.budb.utils.State.*;
import static java.nio.file.Files.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.budb.core.exception.BucketConflictException;
import io.budb.core.exception.BucketNonExistsException;
import io.budb.core.util.LockRegistry;

public class BudEngine implements BudExecutor {
	private final LockRegistry locks = new LockRegistry();
	private final Path defaultPath;

	public BudEngine(String dir) throws IOException {
		this.defaultPath = Paths.get(dir);

		if (!exists(defaultPath)) {
			createDirectories(defaultPath);
		}
	}

	@Override
	public boolean isExistBucket(String name) {
		var bucketDir = defaultPath.resolve(name);

		if (exists(bucketDir)) {
			return true;
		}
		return false;
	}

	@Override
	public void createBucket(String name) throws IOException {
		var bucketDir = defaultPath.resolve(name);

		synchronized (locks.getLock(name)) {
			stateThrow(exists(bucketDir), new BucketConflictException());

			createDirectories(bucketDir);

			var segmentFile = bucketDir.resolve("segment-0.log");
			Files.createFile(segmentFile);

			var indexFile = bucketDir.resolve("index-0.idx");
			Files.createFile(indexFile);

			var metadataFile = bucketDir.resolve("metadata.json");
			String metadata = """
				{
				  "createdAt": "%s",
				  "currentSegment": 0
				}
				""".formatted(java.time.Instant.now().toString());
			Files.writeString(metadataFile, metadata, StandardOpenOption.CREATE_NEW);
		}
	}

	@Override
	public void deleteBucket(String name) throws IOException {
		var bucketDir = defaultPath.resolve(name);

		synchronized (locks.getLock(name)) {
			stateThrow(!exists(bucketDir), new BucketNonExistsException(name));
			//TODO : 삭제 전략 다시 고민해보기 아무리 생각해도 그냥 삭제하는 건 오버헤드인 것 같음
			delete(bucketDir);
		}
	}

	// TODO : 세그먼트로 리팩터링
	@Override
	public void put(String key, byte[] value) throws IOException {
		Path filePath = defaultPath.resolve(key);
		write(filePath, value);
	}

	// TODO : 세그먼트로 리팩터링
	@Override
	public byte[] get(String key) throws IOException {
		Path filePath = defaultPath.resolve(key);
		if (exists(filePath)) {
			return readAllBytes(filePath);
		}
		return null;
	}
}
