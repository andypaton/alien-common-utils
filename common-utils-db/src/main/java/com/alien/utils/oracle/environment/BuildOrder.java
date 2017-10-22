package com.alien.utils.oracle.environment;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BuildOrder implements Comparable<BuildOrder> {

    public static final BuildOrder DEFAULT  = new BuildOrder(5);   
    public static final BuildOrder FIRST    = new BuildOrder(1);
    public static final BuildOrder LAST     = new BuildOrder(9);
    
    private static final boolean TEST_TRANSIENTS = false;
    
    private final int order;

    public BuildOrder(int order) {
        this.order = order;
    }

    private int getOrder() {
        return order;
    }

    @Override
    public int compareTo(BuildOrder buildOrder) {

        return new CompareToBuilder()
        .append(this.getOrder(), buildOrder.getOrder())
        .toComparison();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, TEST_TRANSIENTS);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, TEST_TRANSIENTS);
    }

    @Override
    public String toString() {
        return Integer.toString(order);
    }
}
