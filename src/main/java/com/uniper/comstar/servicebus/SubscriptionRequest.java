package com.uniper.comstar.servicebus;

import com.uniper.comstar.security.AuthorizedRequest;

public class SubscriptionRequest extends AuthorizedRequest<Void> {

  public SubscriptionRequest(final String accessToken) {
    super(accessToken, null);
  }
}
