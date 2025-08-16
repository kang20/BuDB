package io.budb.core;

import java.io.IOException;

public interface BudExecutor {
	boolean isExistBucket(String name);

	void createBucket(String name) throws IOException;

	void deleteBucket(String name) throws IOException;

	void put(String key, byte[] value) throws IOException;

	byte[] get(String key) throws IOException;
}
