# Simple beanstalkd client for Java
[![Build Status](https://travis-ci.org/binarti/jbeanstalkc.svg?branch=master)](https://travis-ci.org/binarti/jbeanstalkc)

This project is intended to help developers to use the [beanstalkd](http://kr.github.io/beanstalkd/) queue service for Java platform.<br>

See [beanstalkd protocol](https://github.com/kr/beanstalkd/blob/master/doc/protocol.txt) document for you better comprehension about beanstalkd operations.

<sub>Excuse me for my terrible English</sub>

## How to use
To use this library, you need to install the package in your maven local repository:
```
mvn clean install -Dmaven.test.skip=true
```

If you want to run unit tests, you need to install on your computer beanstalkd service. After, run this command:
```
mvn clean install
```

By default jbenastalkc connect to beanstalkd service on port 11300. If your beanstalkd service is running over other port, use the variable jbeanstalkc.beanstalkd.url to specify the host and port where server is running:
```
mvn clean install -Djbeanstalkc.beanstalkd.url=beanstalkd://beanstalk-server:15000
```

And add this dependency in your pom.xml

```xml
<dependency>
    <groupId>br.com.binarti</groupId>
    <artifactId>jbeanstalkc</artifactId>
    <version>1.0</version> <!-- or the last version -->
</dependency>
```

This library is not available yet in maven central repository, it is coming soon.<br/>
If you not use maven, you can also build the jar and add it in classpath of the your Java app. You also need to add the log4j version 1.2.17 in classpath of your app<br/>
To build jar package, you need to install maven and execute the command:
```
mvn clean package
```
The jar package is available in target/ directory.

<strong>Java 1.8 is required</strong>

## Quick start
For create a client instance, before you need to know the purpose of your request.<br>
- If you need to producer data to a queue (tube), you need to create a producer.<br>
- If you need to consumer (worker) data from a queue (tube), you need to create a consumer.<br>
- If you need to producer, consumer and access admistrative functions from beanstalkd you need to create a client.<br>

We provide a simple factory to help you to instantiating producer, consumer and full client.<br>

### Why three kinds of client?
Our intention is help the developer to see the separated options about queue requests. Given this situation, we created three kind of clients interfaces, namely:
- BeanstalkProducer
- BeanstalkConsumer
- BeanstalkClient

Each interface should be used for a specific purpose.
Producer, to producer data; consumer, to consumer queue data and client to both and administrative operations.

### Use
For create an instance of the client, you need to use the factory.<br>
The factory class provide methods to create producer, consumer and client instances.<br>
The factory require the host and port to connect to beanstalkd service, look like this:<br>
```java
import br.com.binarti.jbeanstalkc.BeanstalkClientFactory;
...
BeanstalkClientFactory factory = new BeanstalkClientFactory("beanstalkd://localhost:11300");
...or...
BeanstalkClientFactory factory = new BeanstalkClientFactory("localhost", 11300);
```
Each instance produced by this factory, is connected to beanstalkd service in host 'localhost' on port '11300'.

#### Producer
```java
import br.com.binarti.jbeanstalkc.BeanstalkClientFactory;
import br.com.binarti.jbeanstalkc.BeanstalkProducer;

public class BeanstalkProducerTest {

  public static void main(String...args) {
      BeanstalkClientFactory factory = new BeanstalkClientFactory("beanstalkd://localhost:11300");
        //Create producer instance to produce jobs in tube 'mytube'
        try (BeanstalkProducer producer = factory.createProducer("mytube")) {
          //Puts data in tube
          producer.put("my job body");
        }
    }

}
```

#### Consumer
```java
import br.com.binarti.jbeanstalkc.BeanstalkClientFactory;
import br.com.binarti.jbeanstalkc.BeanstalkConsumer;
import br.com.binarti.jbeanstalkc.BeanstalkJob;

public class BeanstalkConsumerWorkerTest {

  public static void main(String...args) {
      BeanstalkClientFactory factory = new BeanstalkClientFactory("beanstalkd://localhost:11300");
        //Create a consumer to consume jobs from tube 'mytube'
        try (BeanstalkConsumer consumer = factory.createConsumer("mytube")) {
          while (true) {
              //This operation, block the current thread until receive a job from beanstalkd service.
              BeanstalkJob job = consumer.reserve();
              execute(job);
              //After successfully execution, you need to delete the job from queue.
              job.delete();
            }
        }
    }

}
```

#### Consumer
```java
import br.com.binarti.jbeanstalkc.BeanstalkClientFactory;
import br.com.binarti.jbeanstalkc.BeanstalkClient;
import br.com.binarti.jbeanstalkc.BeanstalkJob;
import br.com.binarti.jbeanstalkc.protocol.TubeStats;

public class BeanstalkClientTest {

  public static void main(String...args) {
      BeanstalkClientFactory factory = new BeanstalkClientFactory("beanstalkd://localhost:11300");
        try (BeanstalkClient beanstalkClient = factory.createClient()) {
          //Said to beanstalk to produce data in tube 'mytube'
          beanstalkClient.useTube("mytube");
          //Put data in tube 'mytube'
          beanstalkClient.put("my job data");
          
          //Said to beanstalk that you need to receive all data posted in tube 'mytube'
          beanstalkClient.watch("mytube");

          //This operation, block the current thread by 5 seconds waiting for a job. If no job is posted in 'mytube' tube, after this time a null job will be returned.
          BeanstalkJob job = beanstalkClient.reserve(5);
          if (job != null) {
            execute(job);
            //After successfully execution, you need to delete the job from queue.
            job.delete();
          }
          
          //Get statistical information for current tube
          //The BeanstalkClient interface provides many methods to get statistical information and execute administrative operations
          TubeStats tubeStats = beanstalkClient.currentTubeStats();
          //Get number of jobs in ready queue
          System.out.println(tubeStats.getCurrentJobsReady());
        }
    }

}
```