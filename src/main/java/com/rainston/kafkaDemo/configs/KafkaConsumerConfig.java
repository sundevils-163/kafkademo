package com.rainston.kafkaDemo.configs;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.rainston.kafkaDemo.models.Person;
import com.rainston.kafkaDemo.models.User;

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
			factory.setConsumerFactory(consumerFactory("users_group"));
			return factory;
		}
		
		public ConsumerFactory<String, User> userConsumerFactory() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
	        //props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user_group");
	        return new DefaultKafkaConsumerFactory<>(props);
	    }

	    @Bean
	    public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(userConsumerFactory());
	        return factory;
	    }

	    public ConsumerFactory<String, Person> personConsumerFactory() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
	        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.rainston.kafkaDemo.models");
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "person_group");
	        return new DefaultKafkaConsumerFactory<>(props);
	    }

	    @Bean
	    public ConcurrentKafkaListenerContainerFactory<String, Person> personKafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<String, Person> factory = new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(personConsumerFactory());
	        return factory;
	    }
}
