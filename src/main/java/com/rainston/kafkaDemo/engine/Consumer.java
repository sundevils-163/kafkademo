package com.rainston.kafkaDemo.engine;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.rainston.kafkaDemo.models.Offer;
import com.rainston.kafkaDemo.models.OfferGroup;

@Service
public class Consumer {

	private final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	@KafkaListener(topics = "${message.topic.name}", groupId = "message_group")
	public void consume(String message) throws IOException {
		logger.info(String.format("### -> Consumed message -> %s", message));
	}

	@KafkaListener(topics = "${offer.topic.name}", containerFactory = "offerKafkaListenerContainerFactory")
	public void consumeOffer(ConsumerRecord<String, Offer> record) throws IOException {
		logger.info("### -> Consumed Offer -> " + record.value());
	}

	@KafkaListener(topics = "${offergroup.topic.name}", containerFactory = "offerGroupKafkaListenerContainerFactory")
	public void consumeOfferGroup(ConsumerRecord<String, OfferGroup> record) throws IOException {
		logger.info("### -> Consumed OfferGroup -> " + record.value());
	}
}
