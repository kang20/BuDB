package io.budb.core;

import java.io.IOException;

public interface BudExecutor {
	void put(String key, byte[] value) throws IOException;

	byte[] get(String key) throws IOException;
}
