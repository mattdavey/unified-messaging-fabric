unified-messaging-fabric
========================

PoC

# Setting up development environment

## Build
First use [git](http://git-scm.com/) to clone this repo:

    git clone https://github.com/prystupa/unified-messaging-fabric.git
    cd unified-messagin-fabric

UMF is built with [Maven](http://maven.apache.org/).

    mvn clean install

The above will build the prototype and run all unit tests.

## Run

    mvn exec:exec -Prun-fix-server

