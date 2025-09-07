# DAT250-expass-2
Rest API using springboot. 

In this expass I struggled with the test part due to that I had the wrong updated versions of Gradle and Java. I had Gradle 7.4.2 and Java-21, and apparently these two versions are not compatible. Therefor I had to upgrade to Gradle 8.7 to resolve the compatibility problem. 

After fixing the compatibility issue, I encountered several compile-time errors related to package naming and missing or incorrect getter/setter methods in the model classes. Since the getter/setter pattern was new to me, it took some effort to understand and implement it correctly.

Finally, with these corrections the integration test (TestPollManager) now runs succesfully and verifies the functionality of the rest API. 
