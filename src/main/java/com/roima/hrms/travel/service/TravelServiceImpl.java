package com.roima.hrms.travel.service;

import com.roima.hrms.mail.EmailService;
import com.roima.hrms.mail.EmailTemplate;
import com.roima.hrms.travel.dto.TravelAssignResponseDto;
import com.roima.hrms.travel.dto.TravelCreateRequestDto;
import com.roima.hrms.travel.dto.TravelResponseDto;
import com.roima.hrms.travel.entity.Travel;
import com.roima.hrms.travel.entity.TravelAssign;
import com.roima.hrms.travel.mapper.AssignMapper;
import com.roima.hrms.travel.mapper.TravelMapper;
import com.roima.hrms.travel.repository.TravelAssignRepository;
import com.roima.hrms.travel.repository.TravelRepository;
import com.roima.hrms.user.entity.User;
import com.roima.hrms.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {

    private final TravelRepository travelRepository;
    private final TravelMapper travelMapper;
    private final UserRepository userRepository;
    private final TravelAssignRepository travelAssignRepository;
    private final EmailService emailService;


    public TravelServiceImpl(TravelRepository travelRepository, TravelMapper travelMapper, UserRepository userRepository
    , TravelAssignRepository travelAssignRepository, EmailService emailService) {
        this.travelRepository = travelRepository;
        this.travelMapper = travelMapper;
        this.userRepository = userRepository;
        this.travelAssignRepository = travelAssignRepository;
        this.emailService = emailService;
    }


    @Override
    public TravelResponseDto createTravel(TravelCreateRequestDto dto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user  =userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        Travel travel = travelMapper.toEntity(dto);
        travel.setUser(user);
        travel.setCreated_date(LocalDate.now());
        return travelMapper.toDto(travelRepository.save(travel));
    }

    @Override
    public List<TravelResponseDto> findAllTravels()
    {
        return travelRepository.findAll()
                .stream()
                .map(travelMapper::toDto)
                .toList();
    }

    public TravelAssign assignTravel(Long travelId,Long userId)
    {
        User user  =userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        Travel travel = travelRepository.findById(travelId).orElseThrow(()-> new RuntimeException("Travel not found"));

        TravelAssign travelAssign = new TravelAssign();
        travelAssign.setTravel(travel);
        travelAssign.setUser(user);

        //sending mail to assigned User
        emailService.sendEmail(user.getEmail(),"Travel Assigned -"+ travel.getTravel_title(),EmailTemplate.travelAssigned(user.getName(),travel.getTravel_title(),travel.getStart_date(),travel.getEnd_date()));
        return travelAssignRepository.save(travelAssign);
    }

    @Override
    public  List<TravelAssignResponseDto> findAllTravelsAssign()
    {
        return travelAssignRepository.findAll()
                .stream()
                .map(AssignMapper::toDto)
                .toList();
    }
}
