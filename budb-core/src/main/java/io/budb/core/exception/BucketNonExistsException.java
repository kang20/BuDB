package io.budb.core.exception;

public class BucketNonExistsException extends RuntimeException {
	public BucketNonExistsException(String message) {
		super("버킷이 존재하지 않습니다 bucketName: " + message);
	}
}
