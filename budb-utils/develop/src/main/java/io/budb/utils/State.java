package io.budb.utils;

public class State {
	public static void stateThrow(boolean condition, RuntimeException e) {
		if (condition) {
			throw e;
		}
	}
}
