package com.alien.utils.db.environment;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.alien.utils.db")
public class EnvironmentBuildDefinitionCollector_old {

    protected static final boolean ALLOW_EAGER_INIT = true;
    protected static final boolean INCLUDE_NON_SINGLETONS = true;

    public Set<EnvironmentBuildDefinition> getBuildDefinitions() {

        Set<EnvironmentBuildDefinition> buildDefinitions = new TreeSet<EnvironmentBuildDefinition>(new EnvironmentBuildDefinitionComparator());
        
        ApplicationContext context = new AnnotationConfigApplicationContext(EnvironmentBuildDefinitionCollector.class);
        
        Map<String, EnvironmentBuildDefinition> beansOfType = context.getBeansOfType(EnvironmentBuildDefinition.class, INCLUDE_NON_SINGLETONS, ALLOW_EAGER_INIT);

        for (String key: beansOfType.keySet()) {
            EnvironmentBuildDefinition buildDefinition = beansOfType.get(key);

            buildDefinitions.add(buildDefinition);
        }

        return buildDefinitions;
    }
}
