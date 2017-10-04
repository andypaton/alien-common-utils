package com.alien.utils.db.environment;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class EnvironmentBuildDefinitionComparator_old implements Comparator<EnvironmentBuildDefinition> {

    @Override
    public int compare(EnvironmentBuildDefinition buildDefinition1, EnvironmentBuildDefinition buildDefinition2) {
        
        return new CompareToBuilder()
                .append(buildDefinition1.getOrder(), buildDefinition2.getOrder())
                .append(buildDefinition1.getName(), buildDefinition2.getName())
                .toComparison();
    }
}
