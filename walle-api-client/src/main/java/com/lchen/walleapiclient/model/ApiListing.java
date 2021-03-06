package com.lchen.walleapiclient.model;

import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : lchen
 * @date : 2019/6/20
 */
public class ApiListing {
    private final String apiVersion;
    private final String basePath;
    private final String resourcePath;
    private final Set<String> produces;
    private final Set<String> consumes;
    private final String host;
    private final Set<String> protocols;
    private final List<SecurityReference> securityReferences;
    private final List<ApiDescription> apis;
    private final Map<String, Model> models;
    private final String description;
    private final int position;
    private final Set<Tag> tags;

    public ApiListing(
            String apiVersion,
            String basePath,
            String resourcePath,
            Set<String> produces,
            Set<String> consumes,
            String host,
            Set<String> protocols,
            List<SecurityReference> securityReferences,
            List<ApiDescription> apis,
            Map<String, Model> models,
            String description,
            int position,
            Set<Tag> tags) {

        this.apiVersion = apiVersion;
        this.basePath = basePath;
        this.resourcePath = resourcePath;
        this.produces = produces;
        this.consumes = consumes;
        this.host = host;
        this.protocols = protocols;
        this.securityReferences = securityReferences;
        this.apis = apis;
        this.models = models;
        this.description = description;
        this.position = position;
        this.tags = tags;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public Set<String> getProduces() {
        return produces;
    }

    public Set<String> getConsumes() {
        return consumes;
    }

    public String getHost() {
        return host;
    }

    public Set<String> getProtocols() {
        return protocols;
    }

    public List<SecurityReference> getSecurityReferences() {
        return securityReferences;
    }

    public List<ApiDescription> getApis() {
        return apis;
    }

    public Map<String, Model> getModels() {
        return models;
    }

    public String getDescription() {
        return description;
    }

    public int getPosition() {
        return position;
    }

    public Set<Tag> getTags() {
        return tags;
    }

}

