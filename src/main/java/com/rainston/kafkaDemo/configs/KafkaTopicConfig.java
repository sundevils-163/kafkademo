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
	
	@Value(value = "${users.topic.name}")
	private String userTopicName;

	@Value(value = "${user.topic.name}")
	private String user1TopicName;
	
	@Value(value = "${person.topic.name}")
	private String personTopicName;
	
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
		return new NewTopic(userTopicName, partitions, (short)1);
	}

	@Bean
	public NewTopic topicPerson() {
		return new NewTopic(personTopicName, partitions, (short)1);
	}

	@Bean
	public NewTopic topicUser() {
		return new NewTopic(user1TopicName, partitions, (short)1);
	}
}
