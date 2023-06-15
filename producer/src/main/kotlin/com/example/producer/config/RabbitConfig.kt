package com.example.producer.config

import org.springframework.amqp.core.*
import org.springframework.amqp.core.BindingBuilder.bind
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    @Bean
    fun connectionFactory(): CachingConnectionFactory = CachingConnectionFactory(CONNECTION_NAME)

    @Bean
    fun amqpAdmin(): AmqpAdmin = RabbitAdmin(connectionFactory())

    @Bean
    fun rabbitTemplate(): RabbitTemplate = RabbitTemplate(connectionFactory())

    @Bean
    fun bookingCreateUpdateQueue(): Queue = Queue(BOOKING_CREATE_UPDATE_QUEUE_NAME)

    @Bean
    fun bookingFindAllQueue(): Queue = Queue(BOOKING_FIND_ALL_QUEUE_NAME)

    @Bean
    fun messageAuditQueue(): Queue = Queue(MESSAGE_AUDIT_QUEUE_NAME)

    @Bean
    fun messageExchange(): FanoutExchange = FanoutExchange(MESSAGE_EXCHANGE)

    @Bean
    fun bookingExchange(): DirectExchange = DirectExchange(BOOKING_EXCHANGE)

    @Bean
    fun bookingExchangeToMessageExchange(): Binding = bind(bookingExchange()).to(messageExchange())

    @Bean
    fun bookingCreateUpdateQueueToBookingExchange(): Binding =
        bind(bookingCreateUpdateQueue()).to(bookingExchange()).with(CREATE_UPDATE_BOOKING_KEY)

    @Bean
    fun bookingFindAllQueueToBookingExchange(): Binding =
        bind(bookingFindAllQueue()).to(bookingExchange()).with(FIND_ALL_BOOKING_KEY)

}

const val CONNECTION_NAME: String = "localhost"
const val BOOKING_CREATE_UPDATE_QUEUE_NAME: String = "BookingCreateUpdateQueue"
const val BOOKING_FIND_ALL_QUEUE_NAME: String = "BookingFindAllQueue"
const val MESSAGE_AUDIT_QUEUE_NAME: String = "MessageAuditQueue"
const val MESSAGE_EXCHANGE: String = "MessageExchange"
const val BOOKING_EXCHANGE: String = "BookingExchange"
const val CREATE_UPDATE_BOOKING_KEY: String = "create_update_booking"
const val FIND_ALL_BOOKING_KEY: String = "find_all_booking"