package io.budb.core.exception;

public class BucketConflictException extends RuntimeException {
	public BucketConflictException() {
		super("중복된 버킷 생성 예외");
	}
}
