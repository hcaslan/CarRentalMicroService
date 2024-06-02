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
    @Bean(name = "carSaveQueue")
    Queue carSaveQueue(){
        return new Queue("q.car.save");
    }
    @Bean(name = "officeSaveQueue")
    Queue carOfficeQueue(){
        return new Queue("q.office.save");
    }
    @Bean(name = "carSaveDirectExchange")
    DirectExchange carSaveDirectExchange(){
        return new DirectExchange("exchange.direct.carSave");
    }
    @Bean(name = "officeSaveDirectExchange")
    DirectExchange officeSaveDirectExchange(){
        return new DirectExchange("exchange.direct.officeSave");
    }

    @Bean
    Binding bindingCarSave(@Qualifier("carSaveQueue") Queue carSaveQueue,@Qualifier("carSaveDirectExchange") DirectExchange carSaveDirectExchange){
        return BindingBuilder
                .bind(carSaveQueue)
                .to(carSaveDirectExchange)
                .with("Routing.CarSave");
    }

    @Bean
    Binding bindingOfficeSave(@Qualifier("officeSaveQueue") Queue officeSaveQueue,@Qualifier("officeSaveDirectExchange") DirectExchange officeSaveDirectExchange){
        return BindingBuilder
                .bind(officeSaveQueue)
                .to(officeSaveDirectExchange)
                .with("Routing.OfficeSave");
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
