package com.uniper.comstar.servicebus;

import javax.annotation.Nonnull;

import com.uniper.comstar.security.AccessTokenValidator;
import com.uniper.comstar.security.ForbiddenException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class SubscriptionService {
  private static final Logger LOG = getLogger(SubscriptionService.class);
  private final AccessTokenValidator accessTokenValidator;

  public SubscriptionService(final AccessTokenValidator accessTokenValidator) {
    this.accessTokenValidator = accessTokenValidator;
  }

  @Nonnull
  public String subscribe(@Nonnull final SubscriptionRequest request) {
    LOG.debug("subscribe");
    if (accessTokenValidator.isValid(request.getAccessToken())) {
      return "Response from SubscriptionService";
    }
    throw new ForbiddenException("authorization failed");
  }
}
