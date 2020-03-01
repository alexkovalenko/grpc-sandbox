package org.github.alexkovalenko.grpc.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class HelloWorldClient {
    private final ManagedChannel managedChannel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public HelloWorldClient() {
        managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 50021).usePlaintext().build();
        blockingStub = GreeterGrpc.newBlockingStub(managedChannel);
    }

    public void greet(String name) {
        HelloResponse helloResponse = blockingStub.sayHello(HelloRequest.newBuilder().setName(name).build());
        System.out.println("Response: " + helloResponse.getMessage());
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown(). awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClient client = new HelloWorldClient();
        client.greet("Petya");
        client.shutdown();
    }
}
