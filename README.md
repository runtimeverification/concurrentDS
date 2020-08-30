# Run tests with monitor

```
mvn clean test -Dtest=concurrent.MultisetTest
```

# Run tests without monitor
```
mvn clean test -Dtest=concurrent.MultiSetTest -P noAspectJ
```

