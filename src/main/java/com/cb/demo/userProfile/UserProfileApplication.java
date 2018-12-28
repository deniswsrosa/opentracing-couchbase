package com.cb.demo.userProfile;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.reporters.RemoteReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.thrift.internal.senders.UdpSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

@SpringBootApplication
@EnableReactiveCouchbaseRepositories
public class UserProfileApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserProfileApplication.class, args);
	}

	@Bean
	public io.opentracing.Tracer jaegerTracer() {
		return new JaegerTracer.Builder("spring-boot")
				.withReporter(new RemoteReporter.Builder()
						.withSender(new UdpSender("127.0.0.1", 6831, 0))
						.build())
				.withSampler(new ConstSampler(true))
				.build();
	}
}
