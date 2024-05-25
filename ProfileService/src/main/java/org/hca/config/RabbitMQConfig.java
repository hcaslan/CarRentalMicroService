package org.hca.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean(name = "profileUpdateQueue")
    Queue profileUpdateQueue(){
        return new Queue("q.profile.update");
    }
    @Bean(name = "profileUpdateDirectExchange")
    DirectExchange profileUpdateExchange(){
        return new DirectExchange("exchange.direct.profileUpdate");
    }

    @Bean
    Binding bindingProfileUpdate(@Qualifier("profileUpdateQueue") Queue profileUpdateQueue,@Qualifier("profileUpdateDirectExchange") DirectExchange profileUpdateExchange){
        return BindingBuilder
                .bind(profileUpdateQueue)
                .to(profileUpdateExchange)
                .with("Routing.ProfileUpdate");
    }
    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
