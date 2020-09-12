package com.rainston.kafkaDemo.engine;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.rainston.kafkaDemo.models.Person;
import com.rainston.kafkaDemo.models.User;

@Service
public class Consumer {

	private final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	@KafkaListener(topics = "${users.topic.name}", groupId = "users_group")
	public void consume(String message) throws IOException {
		logger.info(String.format("### -> Consumed message -> %s", message));
	}

	@KafkaListener(topics = "${person.topic.name}", containerFactory = "personKafkaListenerContainerFactory")
	public void consume(Person person) throws IOException {
		logger.info("### -> Consumed message -> " + person);
	}

	@KafkaListener(topics = "${user.topic.name}", containerFactory = "userKafkaListenerContainerFactory")
	public void consume(ConsumerRecord<String, User> record) throws IOException {
		logger.info("### -> Consumed message -> " + record.key() + " - " + record.value());
	}
}
