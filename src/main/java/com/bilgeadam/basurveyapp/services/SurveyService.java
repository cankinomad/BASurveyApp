package com.bilgeadam.basurveyapp.services;

import com.bilgeadam.basurveyapp.repositories.SurveyRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepositoryImpl surveyRepository;

}