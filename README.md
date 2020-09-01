# Build javamop and rv-monitor

```
sudo apt install openjdk-8-jdk
git submodule update --init --recursive
./build.sh
```

# Run tests with monitor

```
mvn clean test -Dtest=concurrent.MultisetTest
```

# Run tests without monitor
```
mvn clean test -Dtest=concurrent.MultiSetTest -P noAspectJ
```

