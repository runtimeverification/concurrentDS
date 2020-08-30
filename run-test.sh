#!/usr/bin/env bash

round="$1"

for i in $(seq 1 $round)
do
    mvn clean test -Dtest=concurrent.MultisetTest | grep ERROR
done
