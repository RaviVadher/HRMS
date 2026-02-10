package com.roima.hrms.travel.mapper;

import com.roima.hrms.travel.dto.TravelAssignResponseDto;
import com.roima.hrms.travel.dto.TravelResponseDto;
import com.roima.hrms.travel.entity.Travel;
import com.roima.hrms.travel.entity.TravelAssign;
import org.springframework.stereotype.Component;

@Component
public class AssignMapper {

    public static TravelAssignResponseDto toDto(TravelAssign travelAssign)
    {
        TravelAssignResponseDto dto = new TravelAssignResponseDto();
        dto.setAssignedUserId(travelAssign.getUser().getId());
        dto.setTravelId(travelAssign.getTravel().getId());
        return dto;
    }
}
