# Setting up development environment

## Clone
First use [git](http://git-scm.com/) to clone this repo:

    git clone https://github.com/prystupa/unified-messaging-fabric.git
    cd unified-messaging-fabric

## Build
UMF is built with [Maven](http://maven.apache.org/).

    mvn clean install

The above will build the prototype and run all unit tests.

## Run
    cd test
    mvn exec:java

## Explore
Navigate to UMF Dashboard in a browser

    http://localhost:8080
