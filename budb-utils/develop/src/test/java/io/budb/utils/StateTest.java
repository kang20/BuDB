package io.budb.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StateTest {

	@Test
	void stateThrowTest() {
		assertThatThrownBy(() ->
			State.stateThrow(true, new IllegalArgumentException())
		).isInstanceOf(IllegalArgumentException.class);
	}

}