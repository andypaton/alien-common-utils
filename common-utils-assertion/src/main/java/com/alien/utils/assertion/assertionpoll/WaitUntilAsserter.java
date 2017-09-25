package com.alien.utils.assertion.assertionpoll;

import static org.junit.Assert.fail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitUntilAsserter {
    public static final long DEFAULT_TIMEOUT = 30000;

    private static final Logger LOGGER = LoggerFactory.getLogger(WaitUntilAsserter.class);
    private static final long SLEEP_INTERVAL = 1000;

    private final long maxWaitTime;
    private long accumilatedTime;

    private final AssertTask assertTask;

    public WaitUntilAsserter(AssertTask assertTask) {
        this.assertTask = assertTask;
        this.maxWaitTime = DEFAULT_TIMEOUT;
        this.accumilatedTime = 0;
    }

    public WaitUntilAsserter(AssertTask assertTask, long maxWaitTime) {
        this.assertTask = assertTask;
        this.maxWaitTime = maxWaitTime;
        this.accumilatedTime = 0;
    }

    public void assertTaskResult() {
        if(!taskResult()) {
            fail(assertTask.getTaskName()+" Assertion failed! : "+assertTask.getTaskFailureMessage());
        }else {
            LOGGER.debug(assertTask.getTaskName()+" Assertion passed!");
        }
    }
    
    public Boolean taskResult() {
        LOGGER.debug("Max Wait Time : " + maxWaitTime);
        LOGGER.debug("Initial Accumulated Time : "+accumilatedTime);

        boolean success = assertTask.execute();

        LOGGER.debug(assertTask.getTaskName()+" Execution Result : "+success);

        while(!success && accumilatedTime < maxWaitTime) {

            try {
                LOGGER.debug("Sleeping for : "+SLEEP_INTERVAL);
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                throw new IllegalStateException("Shouldn't be interrupted", e);
            }

            success = assertTask.execute();
            accumilatedTime += SLEEP_INTERVAL;

            LOGGER.debug(assertTask.getTaskName()+" Execution Result : "+success);
            LOGGER.debug("New Accumulated Time : "+accumilatedTime);
        }

        return success;
    }
}
