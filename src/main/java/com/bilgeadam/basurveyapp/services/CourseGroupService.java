package com.bilgeadam.basurveyapp.services;

import com.bilgeadam.basurveyapp.dto.request.CreateCourseGroupRequestDto;
import com.bilgeadam.basurveyapp.dto.response.CourseGroupModelResponse;
import com.bilgeadam.basurveyapp.dto.response.MessageResponseDto;
import com.bilgeadam.basurveyapp.entity.CourseGroup;
import com.bilgeadam.basurveyapp.manager.ICourseGroupManager;
import com.bilgeadam.basurveyapp.mapper.ICourseGroupMapper;
import com.bilgeadam.basurveyapp.repositories.ICourseGroupRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseGroupService{
    private final ICourseGroupManager courseGroupManager;
    private final ICourseGroupRepository courseGroupRepository;

    public CourseGroupService(ICourseGroupManager courseGroupManager, ICourseGroupRepository courseGroupRepository) {
        super();
        this.courseGroupManager = courseGroupManager;
        this.courseGroupRepository = courseGroupRepository;
    }

    public List<CourseGroupModelResponse> getAllDataFromCourseGroup() {
        List<CourseGroupModelResponse> allData = courseGroupManager.findall().getBody();
        if (allData.isEmpty()) {
            throw new RuntimeException("Data boştur");
        }
        for (CourseGroupModelResponse groupCourses: allData){
            createCourseGroup(CreateCourseGroupRequestDto.builder()
                    .apiId("CourseGroup-"+groupCourses.getId())
                    .name(groupCourses.getName())
                    .courseId(groupCourses.getCourseId())
                    .branchId(groupCourses.getBranchId())
                    .trainers(groupCourses.getTrainers())
                    .startDate(groupCourses.getStartDate())
                    .endDate(groupCourses.getEndDate())
                    .build());
        }
        return allData;
    }

    public MessageResponseDto createCourseGroup(CreateCourseGroupRequestDto dto){
        courseGroupRepository.save(ICourseGroupMapper.INSTANCE.toCourseGroup(dto));
        return MessageResponseDto.builder()
                .successMessage(dto.getName()+ "isim sınıf" + dto.getCourseId() + "kurs" + dto.getBranchId()+ "şubesine ait" + dto.getTrainers() + " sahip eğitmenleri" + dto.getStartDate() + "baslangic tarihi" + dto.getEndDate() + "bitis tarihli sınıf eklendi")
                .build();
    }

    public List<CourseGroup> findAllCourseGroup(){
        return courseGroupRepository.findAll();
    }

    public Boolean deleteCourseGroupByOid(Long oid){
        Optional<CourseGroup> optionalCourseGroup = courseGroupRepository.findById(oid);
        if (optionalCourseGroup.isEmpty()){
            throw new RuntimeException("Boyle bir sınıf bulunamamistir");
        }
        try {
            courseGroupRepository.softDeleteById(oid);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public Boolean existsByApiId(String apiId){
        return courseGroupRepository.existsByApiId(apiId);
    }

}