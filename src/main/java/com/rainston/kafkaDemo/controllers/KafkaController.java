package com.rainston.kafkaDemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainston.kafkaDemo.engine.Producer;
import com.rainston.kafkaDemo.models.User;
import com.rainston.kafkaDemo.models.Person;
import com.rainston.kafkaDemo.models.User_POJO;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

	@Autowired
	private Producer producer;
	
	@PostMapping(value = "/publishMsg")
	public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
		this.producer.sendMessage(message);
	}
	
	@PostMapping(value = "/publishPerson")
	public void sendPersonToKafkaTopic(@RequestBody Person person) {
		this.producer.sendPerson(person);
	}

	@PostMapping(value = "/publishUser")
	public void sendUserToKafkaTopic(@RequestBody User_POJO user_pojo) {
		User user = User.newBuilder()
				.setName(user_pojo.getName())
				.setAge(user_pojo.getAge())
				.build();

		this.producer.sendUser(user);
	}
}
