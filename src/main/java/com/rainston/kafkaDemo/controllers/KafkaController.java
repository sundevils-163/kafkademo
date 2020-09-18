package com.rainston.kafkaDemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainston.kafkaDemo.engine.Producer;
import com.rainston.kafkaDemo.models.Offer;
import com.rainston.kafkaDemo.models.OfferGroup;
import com.rainston.kafkaDemo.models.OfferGroup_POJO;
import com.rainston.kafkaDemo.models.Offer_POJO;
import com.rainston.kafkaDemo.models.offer_group_status_enum;
import com.rainston.kafkaDemo.models.offer_status_enum;
import com.rainston.kafkaDemo.models.offer_type_enum;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

	@Autowired
	private Producer producer;
	
	@PostMapping(value = "/publishMsg")
	public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
		this.producer.sendMessage(message);
	}
	
	@PostMapping(value = "/publishOffer")
	public void sendOfferToKafkaTopic(@RequestBody Offer_POJO offer_pojo) {
		Offer offer = Offer.newBuilder()
				.setOfferId(offer_pojo.getOffer_id())
				.setOfferGroupId(offer_pojo.getOffer_group_id())
				.setOfferStatus(offer_status_enum.valueOf(offer_pojo.getOffer_status()))
				.setComments(offer_pojo.getComments())
				.setLoanAmount(offer_pojo.getLoan_amount())
				.build();
		this.producer.sendOffer(offer);
	}

	@PostMapping(value = "/publishOfferGroup")
	public void sendOfferGroupToKafkaTopic(@RequestBody OfferGroup_POJO offerGroup_pojo) {
		OfferGroup offerGroup = OfferGroup.newBuilder()
				.setOfferGroupId(offerGroup_pojo.getOffer_group_id())
				.setOfferType(offer_type_enum.valueOf(offerGroup_pojo.getOffer_type()))
				.setOfferGroupStatus(offer_group_status_enum.valueOf(offerGroup_pojo.getOffer_group_status()))
				.setFileName(offerGroup_pojo.getFile_name())
				.build();

		this.producer.sendOfferGroup(offerGroup);
	}
}
