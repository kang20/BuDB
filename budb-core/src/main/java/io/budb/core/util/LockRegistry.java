package io.budb.core.util;

import java.util.concurrent.ConcurrentHashMap;

public class LockRegistry {
	private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

	public Object getLock(String key) {
		return locks.computeIfAbsent(key, k -> new Object());
	}
}
