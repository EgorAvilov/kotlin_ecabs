package com.example.consumer.mapper

import com.example.consumer.data.Booking
import com.example.consumer.data.TripWayPoint
import com.example.consumer.dto.BookingDto
import com.example.consumer.dto.TripWayPointDto
import org.mapstruct.BeanMapping
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface BookingMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun update(booking: Booking, @MappingTarget persistBooking: Booking)

    @Mapping(source = "id", target = "id")
    @Mapping(source = "passengerName", target = "passengerName")
    @Mapping(source = "passengerContactNumber", target = "passengerContactNumber")
    @Mapping(source = "fromDate", target = "fromDate")
    @Mapping(source = "toDate", target = "toDate")
    @Mapping(source = "numberOfPassengers", target = "numberOfPassengers")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "tripWayPoints", target = "tripWayPoints")
    fun map(booking: Booking): BookingDto


    @Mapping(source = "id", target = "id")
    @Mapping(source = "passengerName", target = "passengerName")
    @Mapping(source = "passengerContactNumber", target = "passengerContactNumber")
    @Mapping(source = "fromDate", target = "fromDate")
    @Mapping(source = "toDate", target = "toDate")
    @Mapping(source = "numberOfPassengers", target = "numberOfPassengers")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "tripWayPoints", target = "tripWayPoints")
    fun map(bookingDto: BookingDto): Booking


    @Mapping(source = "id", target = "id")
    @Mapping(source = "locality", target = "locality")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    fun map(tripWayPoint: TripWayPoint): TripWayPointDto


    @Mapping(source = "id", target = "id")
    @Mapping(source = "locality", target = "locality")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    fun map(tripWayPointDto: TripWayPointDto): TripWayPoint


}