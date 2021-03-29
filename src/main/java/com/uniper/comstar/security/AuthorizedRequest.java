package com.uniper.comstar.security;

public class AuthorizedRequest<T> {
  private String accessToken;
  private T payload;

  public AuthorizedRequest(final String accessToken, final T payload) {
    this.accessToken = accessToken;
    this.payload = payload;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(final String accessToken) {
    this.accessToken = accessToken;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(final T payload) {
    this.payload = payload;
  }

}
