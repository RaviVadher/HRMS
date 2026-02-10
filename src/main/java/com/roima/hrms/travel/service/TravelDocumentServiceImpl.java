package com.roima.hrms.travel.service;
import com.roima.hrms.travel.dto.RequiredTravelDocumentRequestDto;
import com.roima.hrms.travel.entity.RequiredDocument;
import com.roima.hrms.travel.entity.SubmittedTravelDocs;
import com.roima.hrms.travel.entity.Travel;
import com.roima.hrms.travel.entity.TravelAssign;
import com.roima.hrms.travel.repository.RequiredDocumentRepository;
import com.roima.hrms.travel.repository.SubmittedTravelDocumentRepository;
import com.roima.hrms.travel.repository.TravelAssignRepository;
import com.roima.hrms.travel.repository.TravelRepository;
import com.roima.hrms.user.entity.User;
import com.roima.hrms.user.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TravelDocumentServiceImpl implements TravelDocumentService {

    private final SubmittedTravelDocumentRepository submittedTravelDocumentRepository;
    private final TravelAssignRepository travelAssignRepository;
    private final RequiredDocumentRepository requiredDocumentRepository;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;

    public TravelDocumentServiceImpl(FileStorageService fileStorageService,UserRepository userRepository,
                                     TravelAssignRepository travelAssignRepository,
                                     RequiredDocumentRepository requiredDocumentRepository,
                                     SubmittedTravelDocumentRepository submittedTravelDocumentRepository,
                                     TravelRepository travelRepository)
    {
        this.fileStorageService = fileStorageService;
        this.travelAssignRepository = travelAssignRepository;
        this.requiredDocumentRepository = requiredDocumentRepository;
        this.submittedTravelDocumentRepository = submittedTravelDocumentRepository;
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
    }


    @Override
    public void uploadDocument(Long travelId, Long requiredDocId, MultipartFile file,Long userId,String filetype) {
//        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        TravelAssign travelAssign = travelAssignRepository.findByUser_idAndTravel_id(userId, travelId).orElseThrow(() -> new RuntimeException("travel not assigned"));
        RequiredDocument requiredDocument = requiredDocumentRepository.findById(requiredDocId).orElseThrow(() -> new RuntimeException("required document not found"));

        String path = fileStorageService.store(
                file,
                travelId,
                travelAssign.getUser().getId(),
                requiredDocument.getDoc_name()
        );

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        SubmittedTravelDocs doc = new SubmittedTravelDocs();
        doc.setTravelAssign(travelAssign);
        doc.setRequiredDocument(requiredDocument);
        doc.setFilepath(path);
        doc.setUser(user);
        doc.setFiletype(filetype);
        submittedTravelDocumentRepository.save(doc);
    }


        @Override
        public Resource downloadDocument(Long id)
        {
            SubmittedTravelDocs doc =submittedTravelDocumentRepository.findById(id).orElseThrow(() -> new RuntimeException("document not found"));

//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            boolean isOwner = doc.getTravelAssign().getUser().getId().equals(user.getId());
//            boolean isHr = user.getRole().equals("Hr");
//
//            if(!isOwner && !isHr){
//                throw new RuntimeException("document not found");
//            }

            return fileStorageService.load(doc.getFilepath());

        }


        public RequiredDocument postDocument(Long travelId, RequiredTravelDocumentRequestDto dto) {

               Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new RuntimeException("travel not found"));
               RequiredDocument doc = new RequiredDocument();
               doc.setDoc_name(dto.getFilename());
               doc.setTravel(travel);
              return requiredDocumentRepository.save(doc);
        }


    @Override
    public List<RequiredDocument> getDocument(Long travelId) {
        List<RequiredDocument> list = requiredDocumentRepository.findByTravel_Id(travelId);
        return list;
    }
}
