package model.service.mapper;

import model.dto.request.OrderCreateDto;
import model.dto.request.OrderRequestDto;
import model.entity.Order;
import model.vo.Id;

public class OrderMapper {

    public static Order mapRequestToEntity(OrderCreateDto request, Id userId){
        return Order.createNew(userId, request.productId(), request.deliveryPeriod(), request.deliveryAddress());
    }

    public static Order mapRequestToEntity(OrderRequestDto requestDto){
        return Order.createNew(requestDto.userId(), requestDto.productId(),
                requestDto.deliveryPeriod(), requestDto.deliveryAddress());
    }
}
