package com.rainston.kafkaDemo.configs;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.rainston.kafkaDemo.models.Offer;
import com.rainston.kafkaDemo.models.OfferGroup;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

		@Value(value = "${kafka.bootstrapAddress}")
		private String bootstrapAddress;
		
		@Value(value = "${kafka.schema-registry}")
		private String schemaRegistryUrl;

		public ConsumerFactory<String, String> consumerFactory(String groupId) {
			Map<String, Object> props = new HashMap<String, Object>();
			props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
			props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
			props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			return new DefaultKafkaConsumerFactory<>(props);
		}
		
		@Bean
		public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
			ConcurrentKafkaListenerContainerFactory<String, String> factory =
					new ConcurrentKafkaListenerContainerFactory<>();
			factory.setConsumerFactory(consumerFactory("message_group"));
			return factory;
		}
		
		public ConsumerFactory<Integer, Offer> offerConsumerFactory() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
	        //props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "offer_group");
	        return new DefaultKafkaConsumerFactory<>(props);
	    }

	    @Bean
	    public ConcurrentKafkaListenerContainerFactory<Integer, Offer> offerKafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<Integer, Offer> factory = new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(offerConsumerFactory());
	        return factory;
	    }

	    public ConsumerFactory<Integer, OfferGroup> offerGroupConsumerFactory() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
	        //props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "offergroup_group");
	        return new DefaultKafkaConsumerFactory<>(props);
	    }

	    @Bean
	    public ConcurrentKafkaListenerContainerFactory<Integer, OfferGroup> offerGroupKafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<Integer, OfferGroup> factory = new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(offerGroupConsumerFactory());
	        return factory;
	    }
}
