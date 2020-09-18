package com.rainston.kafkaDemo.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.rainston.kafkaDemo.models.Offer;
import com.rainston.kafkaDemo.models.OfferGroup;

@Service
public class Producer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	
	@Value(value = "${message.topic.name}")
	private String messageTopicName;

	@Value(value = "${offer.topic.name}")
	private String offerTopicName;

	@Value(value = "${offergroup.topic.name}")
	private String offerGroupTopicName;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaTemplate<Integer, Offer> kafkaOfferTemplate;

	@Autowired
	private KafkaTemplate<Integer, OfferGroup> kafkaOfferGroupTemplate;
	
	public void sendMessage(String message) {
		logger.info(String.format("### -> Producing message %s", message));
		this.kafkaTemplate.send(messageTopicName, message);
	}

	public void sendOffer(Offer offer) {
		logger.info("### -> Producing Offer " + offer);
		this.kafkaOfferTemplate.send(offerTopicName, offer.getOfferId(), offer);
	}

	public void sendOfferGroup(OfferGroup offerGroup) {
		logger.info("### -> Producing Offer Group " + offerGroup);
		this.kafkaOfferGroupTemplate.send(offerGroupTopicName, offerGroup.getOfferGroupId(), offerGroup);
	}
}
