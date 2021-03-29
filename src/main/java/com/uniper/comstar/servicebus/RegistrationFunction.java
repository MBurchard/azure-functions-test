package com.uniper.comstar.servicebus;

import com.uniper.comstar.security.ForbiddenException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;

@Component("registerSB")
public class RegistrationFunction implements Function<SubscriptionRequest, String> {
  private static final Logger LOG = getLogger(RegistrationFunction.class);
  private final SubscriptionService subscriptionService;

  public RegistrationFunction(final SubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
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
