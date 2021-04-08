package com.uniper.comstar.servicebus;

import java.util.function.Function;

import com.uniper.comstar.security.ForbiddenException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class RegistrationFunction implements Function<SubscriptionRequest, String> {
  private static final Logger LOG = getLogger(RegistrationFunction.class);
  private final SubscriptionService subscriptionService;

  public RegistrationFunction(final SubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
    LOG.debug("initialize");
  }

  @Override
  public String apply(final SubscriptionRequest request) {
    try {
      return subscriptionService.subscribe(request);
    } catch (final ForbiddenException ex) {
      throw ex;
    } catch (final Throwable th) {
      LOG.error("error calling subscription service", th);
      throw new RuntimeException("error calling subscription service");
    }
  }
}
