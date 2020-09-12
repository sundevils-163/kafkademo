package com.rainston.kafkaDemo.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.rainston.kafkaDemo.models.Person;
import com.rainston.kafkaDemo.models.User;

@Service
public class Producer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	
	@Value(value = "${users.topic.name}")
	private String userTopicName;

	@Value(value = "${user.topic.name}")
	private String user1TopicName;

	@Value(value = "${person.topic.name}")
	private String personTopicName;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaTemplate<String, Person> kafkaPersonTemplate;

	@Autowired
	private KafkaTemplate<String, User> kafkaUserTemplate;
	
	public void sendMessage(String message) {
		logger.info(String.format("### -> Producing message %s", message));
		this.kafkaTemplate.send(userTopicName, message);
	}

	public void sendPerson(Person person) {
		logger.info("### -> Producing message " + person);
		this.kafkaPersonTemplate.send(personTopicName, person.getName(), person);
	}

	public void sendUser(User user) {
		logger.info("### -> Producing message " + user);
		this.kafkaUserTemplate.send(user1TopicName, user.getName(), user);
	}
}
