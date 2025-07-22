package com.anouar.runnerz;

import com.anouar.runnerz.user.UserHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	UserHttpClient userHttpClient(WebClient.Builder builder) {
		WebClient webClient = builder
				.baseUrl("https://jsonplaceholder.typicode.com")
				.build();

		var factory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webClient))
				.build();

		return factory.createClient(UserHttpClient.class);
	}
}
