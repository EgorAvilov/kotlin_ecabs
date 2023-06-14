package com.example.consumer.mapper

import com.example.consumer.data.TripWayPoint
import com.example.consumer.dto.TripWayPointDto
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface TripWayPointMapper {
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