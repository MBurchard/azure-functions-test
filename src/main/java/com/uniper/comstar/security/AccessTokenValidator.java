package com.uniper.comstar.security;

import com.auth0.jwt.JWT;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class AccessTokenValidator {
  private static final Logger LOG = getLogger(AccessTokenValidator.class);
  private final SecurityConfig securityConfig;

  public AccessTokenValidator(final SecurityConfig securityConfig) {
    this.securityConfig = securityConfig;
  }

  public boolean isValid(final String accessToken) {
    final var startTS = System.currentTimeMillis();
    try {
      LOG.debug("validate: {}", accessToken);
      final var now = new Date();
      final var jwt = JWT.decode(accessToken);
      if (!"RS256".equals(jwt.getAlgorithm())) {
        LOG.warn("wrong algorithm: {}", jwt.getAlgorithm());
        return false;
      }
      if (!String.format("https://sts.windows.net/%s/", securityConfig.getTenant()).equals(jwt.getIssuer())) {
        LOG.warn("not supported issuer: {}", jwt.getIssuer());
        return false;
      }
      if (!securityConfig.getClientId().equals(jwt.getClaim("appid").asString())) {
        LOG.warn("not supported appid: {}", jwt.getClaim("appid"));
        return false;
      }
      if (now.before(jwt.getNotBefore())) {
        LOG.debug("Token is not valid before {}", jwt.getNotBefore());
        return false;
      }
      if (now.after(jwt.getExpiresAt())) {
        LOG.debug("Token has expired at {}", jwt.getExpiresAt());
        return false;
      }

//      final var client = GraphServiceClient
//          .builder()
//          .authenticationProvider(requestUrl -> CompletableFuture.supplyAsync(() -> accessToken))
//          .buildClient();
//      final var user = client.me().buildRequest().get();
//      if (user != null && Objects.equals(user.id, jwt.getClaim("oid").asString())) {
//        LOG.debug("Valid access token for: {} ({})", user.displayName, user.id);
////        validatedAccessTokens.put(accessToken, jwt.getExpiresAt());
//        return true;
//      }
      LOG.warn("The Access Token seems to be valid, but the signature was not validated");
      return true;
//    } catch (final GraphServiceException ex) {
//      if (ex.getServiceError() != null && "InvalidAuthenticationToken".equals(ex.getServiceError().code)) {
//        LOG.debug(ex.getServiceError().message);
//      } else {
//        LOG.error("", ex);
//      }
    } catch (final Throwable th) {
      LOG.error("unexpected error during MSGraph access", th);
    } finally {
      LOG.debug("access token validation took {}ms", System.currentTimeMillis() - startTS);
    }
    return false;
  }
}
