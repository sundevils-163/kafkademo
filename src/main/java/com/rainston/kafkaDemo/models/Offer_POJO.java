package com.rainston.kafkaDemo.models;

import lombok.Data;

@Data
public class Offer_POJO {

	private int offer_id;
	private int offer_group_id;
	private String offer_status;
	private String comments;
	private double loan_amount;
}
