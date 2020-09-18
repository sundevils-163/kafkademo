package com.rainston.kafkaDemo.configs;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;
	
	@Value(value = "${message.topic.name}")
	private String messageTopicName;

	@Value(value = "${offer.topic.name}")
	private String offerTopicName;
	
	@Value(value = "${offergroup.topic.name}")
	private String offerGroupTopicName;
	
	@Value("${topic.partitions-num}")
	private Integer partitions;
	
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		
		return new KafkaAdmin(configs);
	}
	
	@Bean
	public NewTopic topicUsers() {
		return new NewTopic(messageTopicName, partitions, (short)1);
	}

	@Bean
	public NewTopic topicOffer() {
		return new NewTopic(offerTopicName, partitions, (short)1);
	}

	@Bean
	public NewTopic topicOfferGroup() {
		return new NewTopic(offerGroupTopicName, partitions, (short)1);
	}
}
