package org.example.mcolomerc.monitoringexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

// Spring Cloud stream example app: https://github.com/spring-cloud/spring-cloud-stream-samples
@SpringBootApplication
public class MonitoringExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringExampleApplication.class, args);
	}

	@Bean
	public Supplier<UUID> stringSupplier() {
		return () -> {
			var uuid = UUID.randomUUID();
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
			System.out.println(uuid + " -> batch-in");
			return uuid;
		};
	}

	@Bean
	public Function<List<UUID>, List<Message<String>>> digitRemovingConsumer() {
		return idBatch -> {
			System.out.println("Removed digits from batch of " + idBatch.size());
			return idBatch.stream()
					.map(UUID::toString)
					// Remove all digits from the UUID
					.map(uuid -> uuid.replaceAll("\\d",""))
					.map(noDigitString -> MessageBuilder.withPayload(noDigitString).build())
					.toList();
		};
	}

	@KafkaListener(id = "batch-out", topics = "batch-out")
	public void listen(String in) {
		System.out.println("batch-out -> " + in);
	}
}
