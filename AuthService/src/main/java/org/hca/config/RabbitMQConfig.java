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
    @Bean(name = "mailQueue")
    Queue mailQueue() {
        return new Queue("q.mail");
    }
    @Bean(name = "createProfileQueue")
    Queue createProfileQueue() {
        return new Queue("q.profile.create");
    }
    @Bean(name = "updateStatusQueue")
    Queue updateStatusQueue() {
        return new Queue("q.status.update");
    }

    @Bean(name = "mailDirectExchange")
    DirectExchange mailExchange() {
        return new DirectExchange("exchange.direct.mail");
    }
    @Bean(name = "createProfileDirectExchange")
    DirectExchange createProfileDirectExchange() {
        return new DirectExchange("exchange.direct.createProfile");
    }
    @Bean(name = "updateStatusDirectExchange")
    DirectExchange updateStatusDirectExchange() {
        return new DirectExchange("exchange.direct.updateStatus");
    }

    @Bean
    Binding bindingMail(@Qualifier("mailQueue") Queue mailQueue, @Qualifier("mailDirectExchange") DirectExchange mailExchange) {
        return BindingBuilder
                .bind(mailQueue)
                .to(mailExchange)
                .with("Routing.Mail");
    }
    @Bean
    Binding bindingCreateProfile(@Qualifier("createProfileQueue") Queue createProfileQueue, @Qualifier("createProfileDirectExchange") DirectExchange createProfileDirectExchange) {
        return BindingBuilder
                .bind(createProfileQueue)
                .to(createProfileDirectExchange)
                .with("Routing.createProfile");
    }
    @Bean
    Binding bindingUpdateStatus(@Qualifier("updateStatusQueue") Queue updateStatusQueue, @Qualifier("updateStatusDirectExchange") DirectExchange updateStatusDirectExchange) {
        return BindingBuilder
                .bind(updateStatusQueue)
                .to(updateStatusDirectExchange)
                .with("Routing.updateStatus");
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
