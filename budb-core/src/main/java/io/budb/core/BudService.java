package io.budb.core;

import static java.util.Objects.*;

import java.io.IOException;

import io.budb.core.port.BudProvider;

public class BudService implements BudProvider {
	private final BudExecutor executor;

	public BudService(BudExecutor executor) {
		this.executor = requireNonNull(executor);
	}

	@Override
	public void createBucket(String name) throws IOException {
		executor.createBucket(name);
	}

	@Override
	public void deleteBucket(String name) {

	}

	public void save(String key, byte[] value) throws IOException {
		executor.put(key, value);
	}

	public byte[] get(String key) throws IOException {
		return executor.get(key);
	}

}
