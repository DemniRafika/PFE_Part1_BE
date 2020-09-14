package net.model;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import net.config.RabbitConfig;
@Service
public class TradeMessageSender {
	    private final RabbitTemplate rabbitTemplate;
	    private final ObjectMapper objectMapper;
	    
	    @Autowired
	    public TradeMessageSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
	        this.rabbitTemplate = rabbitTemplate;
	        this.objectMapper = objectMapper;
	    }
	    
	    public void sendOrder(Trade trade) {
	      //  this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_TRADERS, trade);
	        
	      try {
	            String tradeJson = objectMapper.writeValueAsString(trade);
	            Message message = MessageBuilder
	                                .withBody(tradeJson.getBytes())
	                                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
	                                .build();
	            this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_TRADERS, message);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        } 
	        
	        
	    }
}
