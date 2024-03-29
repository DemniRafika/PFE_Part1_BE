package net.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;


@Configuration
public class RabbitConfig implements RabbitListenerConfigurer{
	

	public static final String QUEUE_TRADERS = "traders-queue";
    public static final String QUEUE_DEAD_TRADERS = "dead-traders-queue";
    public static final String EXCHANGE_TRADERS = "traders-exchange";
    
    @Bean
    Queue tradersQueue() {
    	
    	 return QueueBuilder.durable(QUEUE_TRADERS)
                 .withArgument("x-dead-letter-exchange", "")
                 .withArgument("x-dead-letter-routing-key",QUEUE_DEAD_TRADERS)
                 .withArgument("x-message-ttl", 5000)
                 .build();
    }
    
    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_TRADERS).build();
    }
    
    @Bean
    Exchange tradersExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TRADERS).build();
    }
    
    @Bean
    Binding binding(Queue tradersQueue, TopicExchange tradersExchange) {
        return BindingBuilder.bind(tradersQueue).to(tradersExchange).with(QUEUE_TRADERS);
    }
    
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
    
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }
    
	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
		
	}
}
