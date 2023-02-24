package com.bilgeadam.basurveyapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * @author Eralp Nitelik
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrainerClassroomSurveyResponseDto {
    String firstName;
    String lastName;
    String email;
    Set<String> roles;
    List<ClassroomWithSurveysResponseDto> classroomSurveys;
}
