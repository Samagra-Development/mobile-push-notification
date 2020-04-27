package com.samagra.odktest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//@RunWith(Suite.class)
@RunWith(StopOnFailureSuite.class)
@Suite.SuiteClasses({
        ForgotPasswordTest.class,
        LoginActivityTest.class,
        HomeActivityTest.class,

})
public class JunitTestSuite {}