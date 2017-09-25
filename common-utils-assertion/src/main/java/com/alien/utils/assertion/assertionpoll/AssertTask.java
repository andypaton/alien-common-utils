package com.alien.utils.assertion.assertionpoll;

public interface AssertTask {

    boolean execute();

    String getTaskName();

    String getTaskFailureMessage();
}
