package com.uniper.comstar.servicebus;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.uniper.comstar.security.ForbiddenException;
import org.slf4j.Logger;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class RegistrationHandler extends FunctionInvoker<SubscriptionRequest, String> {
  private static final Logger LOG = getLogger(RegistrationHandler.class);

  @FunctionName("registerSB")
  @Nonnull
  public HttpResponseMessage run(
      @Nonnull @HttpTrigger(name = "request", methods = {HttpMethod.GET, HttpMethod.POST},
          authLevel = AuthorizationLevel.ANONYMOUS) final HttpRequestMessage<Optional<String>> request,
      @Nonnull final ExecutionContext context) {
    LOG.debug("run function {}", context.getFunctionName());
    final String auth = request.getHeaders().get(HttpHeaders.AUTHORIZATION.toLowerCase());
    if (StringUtils.hasLength(auth) && auth.startsWith("Bearer ")) {
      try {
      return request.createResponseBuilder(HttpStatus.OK)
          .body(handleRequest(new SubscriptionRequest(auth.replace("Bearer ", "")), context))
          .header("Content-Type", "application/json")
          .build();
      } catch (final ForbiddenException ex) {
        return request.createResponseBuilder(HttpStatus.FORBIDDEN).build();
      } catch (final Throwable th) {
        LOG.error("", th);
        return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }
    return request.createResponseBuilder(HttpStatus.UNAUTHORIZED).build();
  }
}
