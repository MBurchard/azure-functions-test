package com.uniper.comstar.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(SecurityConfig.PREFIX)
public class SecurityConfig {
  public static final String PREFIX = "uniper.comstar";
  private String clientId;
  private String tenant;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(final String clientId) {
    this.clientId = clientId;
  }

  public String getTenant() {
    return tenant;
  }

  public void setTenant(final String tenant) {
    this.tenant = tenant;
  }

}
