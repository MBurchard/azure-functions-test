package com.uniper.comstar.security;

public class ForbiddenException extends RuntimeException {

  public ForbiddenException(final String message) {
    super(message);
  }
}
