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
* Navigate to UMF Dashboard in a browser: [http://localhost:8080](http://localhost:8080)
* Browser the services registered with UMF through its discovery mechanism
* Explore service details (list of topics published) and subscribe to interesting topics
* Monitor the subcriptions
