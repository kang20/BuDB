package io.budb.core.port;

import java.io.IOException;

public interface BudProvider {
	void save(String key, byte[] value) throws IOException;

	byte[] get(String key) throws IOException;
}
