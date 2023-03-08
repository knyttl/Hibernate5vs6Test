# Demo of issue in Hibernate 6

This is a demo repository with issue we are having in Hibernate 6 with complex @OneToMany relationship. 

To run the test with Hibernate 5, which is pre-selected in `pom.xml`:

```bash
 mvn test -Dhibernate.server={my-server} -Dhibernate.database={my-database} -Dhibernate.password={my-password} -Dhibernate.user={db-user}
```

You will see the test is **correctly passing**, logs here: https://gist.github.com/knyttl/ac8010f853bc22b1a0176e856cad0a3e

Now please uncomment Hibernate 6 in `pom.xml` and run the test again.

The test will now fail with this log: https://gist.github.com/knyttl/292aeb613fd10b9c9fbf8d7b5d7e2073
