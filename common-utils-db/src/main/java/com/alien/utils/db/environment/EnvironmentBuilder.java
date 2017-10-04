package com.alien.utils.db.environment;

public interface EnvironmentBuilder {
    void build(String hostname, EnvironmentBuildDefinition buildDefinition);
    void tearDown(String hostname, EnvironmentBuildDefinition buildDefinition);
}
