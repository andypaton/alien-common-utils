package com.alien.utils.db;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.alien.utils.db.environment.BuildOrder;
import com.alien.utils.db.environment.EnvironmentBuildDefinition;
import com.alien.utils.db.environment.EnvironmentBuildDefinitionComparator_old;

public class EnvironmentBuildDefinitionComparatorTest_old {

    @Test
    public void shouldSortBuildersAlphabetically() {

        EnvironmentBuildDefinition buildDefinition1 = mock(EnvironmentBuildDefinition.class);
        when(buildDefinition1.getName()).thenReturn("B");
        
        EnvironmentBuildDefinition buildDefinition2 = mock(EnvironmentBuildDefinition.class);
        when(buildDefinition2.getName()).thenReturn("A");
        
        List<EnvironmentBuildDefinition> buildDefinitions = new ArrayList<EnvironmentBuildDefinition>();
        buildDefinitions.add(buildDefinition1);
        buildDefinitions.add(buildDefinition2);

        Collections.sort(buildDefinitions, new EnvironmentBuildDefinitionComparator_old());

        assertThat(buildDefinitions, contains(buildDefinition2, buildDefinition1));
    }

    @Test
    public void shouldSortBuildersByOrder() {

        EnvironmentBuildDefinition buildDefinition1 = mock(EnvironmentBuildDefinition.class);
        when(buildDefinition1.getName()).thenReturn("A");
        when(buildDefinition1.getOrder()).thenReturn(BuildOrder.LAST);
        
        EnvironmentBuildDefinition buildDefinition2 = mock(EnvironmentBuildDefinition.class);
        when(buildDefinition2.getName()).thenReturn("A");
        when(buildDefinition2.getOrder()).thenReturn(BuildOrder.FIRST);
        
        EnvironmentBuildDefinition buildDefinition3 = mock(EnvironmentBuildDefinition.class);
        when(buildDefinition3.getName()).thenReturn("A");
        when(buildDefinition3.getOrder()).thenReturn(BuildOrder.DEFAULT);
        
        List<EnvironmentBuildDefinition> buildDefinitions = new ArrayList<EnvironmentBuildDefinition>();
        buildDefinitions.add(buildDefinition1);
        buildDefinitions.add(buildDefinition2);
        buildDefinitions.add(buildDefinition3);

        Collections.sort(buildDefinitions, new EnvironmentBuildDefinitionComparator_old());

//        printBuildDefintions(buildDefinitions);
        
        assertThat(buildDefinitions, contains(buildDefinition2, buildDefinition3, buildDefinition1));
    }

    @Test
    public void shouldSortBuildersByOrderThenAlphabetically() {

        EnvironmentBuildDefinition buildDefinition1 = mock(EnvironmentBuildDefinition.class);
        when(buildDefinition1.getName()).thenReturn("A");
        when(buildDefinition1.getOrder()).thenReturn(BuildOrder.LAST);
        
        EnvironmentBuildDefinition buildDefinition2 = mock(EnvironmentBuildDefinition.class);
        when(buildDefinition2.getName()).thenReturn("Z");
        when(buildDefinition2.getOrder()).thenReturn(BuildOrder.FIRST);
        
        EnvironmentBuildDefinition buildDefinition3 = mock(EnvironmentBuildDefinition.class);
        when(buildDefinition3.getName()).thenReturn("B");
        when(buildDefinition3.getOrder()).thenReturn(BuildOrder.DEFAULT);
        
        EnvironmentBuildDefinition buildDefinition4 = mock(EnvironmentBuildDefinition.class);
        when(buildDefinition4.getName()).thenReturn("C");
        when(buildDefinition4.getOrder()).thenReturn(BuildOrder.DEFAULT);
        
        List<EnvironmentBuildDefinition> buildDefinitions = new ArrayList<EnvironmentBuildDefinition>();
        buildDefinitions.add(buildDefinition1);
        buildDefinitions.add(buildDefinition2);
        buildDefinitions.add(buildDefinition3);
        buildDefinitions.add(buildDefinition4);

        Collections.sort(buildDefinitions, new EnvironmentBuildDefinitionComparator_old());

//        printBuildDefintions(buildDefinitions);

        assertThat(buildDefinitions, contains(buildDefinition2, buildDefinition3, buildDefinition4, buildDefinition1));
    }

    private void printBuildDefintions(List<EnvironmentBuildDefinition> buildDefinitions) {
        for (EnvironmentBuildDefinition environmentBuildDefinition : buildDefinitions) {
            System.out.println(environmentBuildDefinition.getName() + "-" + environmentBuildDefinition.getOrder());
        }
    }
}
