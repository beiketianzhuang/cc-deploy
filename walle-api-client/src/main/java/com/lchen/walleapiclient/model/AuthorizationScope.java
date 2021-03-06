package com.lchen.walleapiclient.model;


/**
 * @author : lchen
 * @date : 2019/6/20
 */
public class AuthorizationScope {
  private final String scope;
  private final String description;

  public AuthorizationScope(String scope, String description) {
    this.description = description;
    this.scope = scope;
  }

  public String getScope() {
    return scope;
  }

  public String getDescription() {
    return description;
  }
}
