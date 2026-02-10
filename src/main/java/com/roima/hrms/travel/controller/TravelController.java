package com.roima.hrms.travel.controller;

import com.roima.hrms.travel.dto.TravelAssignResponseDto;
import com.roima.hrms.travel.dto.TravelAssingnRequestDto;
import com.roima.hrms.travel.dto.TravelCreateRequestDto;
import com.roima.hrms.travel.dto.TravelResponseDto;
import com.roima.hrms.travel.entity.TravelAssign;
import com.roima.hrms.travel.service.TravelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    private final TravelService travelService;

    public TravelController( TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping("/getAll")
    public List<TravelResponseDto> findAllTravels(){
        return travelService.findAllTravels();
    }

    //Hr Create traveling plan
    @PostMapping
    @PreAuthorize("hasRole('Hr')")
    public TravelResponseDto createTravel(@RequestBody TravelCreateRequestDto dto){
        return travelService.createTravel(dto);
    }


    //Hr assign travel plan to specific employee
    @PostMapping("/{travelId}/assign")
    @PreAuthorize("hasRole('Hr')")
    public TravelAssign assignTravel(@PathVariable Long travelId, @RequestBody TravelAssingnRequestDto dto)
    {
        Long userId = dto.getFkUserId();
        return travelService.assignTravel(travelId,userId);
    }


    //Hr can see all travel assigns
    @GetMapping("/assigns/getAll")
    public List<TravelAssignResponseDto> findAllTravelsAssign(){
         return travelService.findAllTravelsAssign();
    }
}
