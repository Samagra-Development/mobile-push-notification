package com.samagra.odktest;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(StopOnFailureSuite.class)
@Suite.SuiteClasses({
        LoginActivityTest.class,
        StudentAddressBookTest.class,
})

public class StudentJUnnitTestSuite {
}
