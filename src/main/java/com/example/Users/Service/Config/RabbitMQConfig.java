package com.example.Users.Service.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@EnableRabbit
@Configuration
public class RabbitMQConfig {
    
    public static final String EXCHANGE_NAME ="user.joined.exchange";
    public static final String QUEUE_NAME = "user.joined.queue";
    public static final String ROUTING_KEY = "user.joined.routingKey";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public MessageConverter JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
