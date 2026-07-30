package de.rathfelix.exceptions;

public class ChunkStorageException extends RuntimeException {
  public ChunkStorageException(String message, Throwable cause) {
    super(message, cause);
  }

  public ChunkStorageException(String message) {
    super(message);
  }
}
