package com.alien.utils.db;

import org.junit.Test;

import com.alien.utils.db.environment.EnvironmentBuildDefinition;
import com.alien.utils.db.environment.EnvironmentBuildDefinitionCollector;

import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;


public class EnvironmentBuildDefinitionCollectorIntegrationTest {

    @Test
    public void shouldHaveTwoOrderedEnvironmentBuilders() {
        EnvironmentBuildDefinitionCollector collector = new EnvironmentBuildDefinitionCollector();
        
        Set<EnvironmentBuildDefinition> builders = collector.getBuildDefinitions();

        EnvironmentBuildDefinition firstBuildDefinition = new DefaultLiquibaseBuildDefinition().getDefaultLiquibaseBuildDefinition();
        EnvironmentBuildDefinition lastBuildDefinition = new LastLiquibaseBuilderConfiguration().getLastLiquibaseBuilder();

        System.out.println(firstBuildDefinition.equals(lastBuildDefinition));
        System.out.println(firstBuildDefinition.equals(builders.toArray()[0]));
        System.out.println(firstBuildDefinition.equals(builders.toArray()[1]));

        assertThat(builders, contains(firstBuildDefinition, lastBuildDefinition));
    }
}
