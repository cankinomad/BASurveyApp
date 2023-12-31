package com.bilgeadam.basurveyapp.exceptions;

import com.bilgeadam.basurveyapp.exceptions.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static com.bilgeadam.basurveyapp.exceptions.ExceptionType.*;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, HttpServletRequest request) {
        log.error(messageSource.getMessage("exception.UNEXPECTED_ERROR", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(UNEXPECTED_ERROR, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException exception, HttpServletRequest request) {
        log.error(messageSource.getMessage("exception.RUNTIME_ERROR", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(RUNTIME_EXCEPTION, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(UsernameNotFoundException exception, HttpServletRequest request) {
        String errorMessage = messageSource.getMessage("exception.LOGIN_ERROR_USERNAME_DOES_NOT_EXIST", null, Locale.getDefault());
        log.error(errorMessage, exception);
        return createExceptionInfoResponse(LOGIN_ERROR_USERNAME_DOES_NOT_EXIST, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.LOGIN_ERROR_WRONG_PASSWORD", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(LOGIN_ERROR_WRONG_PASSWORD, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.REGISTER_ERROR_DATA_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(REGISTER_ERROR_DATA_EXISTS, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.RESOURCE_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(RESOURCE_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.DATA_NOT_VALID", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(DATA_NOT_VALID, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.ACCESS_DENIED", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(ACCESS_DENIED, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionNotFoundException(QuestionNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.QUESTION_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(QUESTION_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(QuestionAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionAlreadyExistsException(QuestionAlreadyExistsException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.QUESTION_ALREADY_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(QUESTION_ALREADY_EXISTS, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(QuestionTypeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionTypeNotFoundException(QuestionTypeNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.QUESTION_TYPE_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(QUESTION_TYPE_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(AlreadyAnsweredSurveyException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyAnsweredSurveyException(AlreadyAnsweredSurveyException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.SURVEY_ALREADY_ANSWERED", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(SURVEY_ALREADY_ANSWERED, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(QuestionsAndResponsesDoesNotMatchException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionsAndResponsesDoesNotMatchException(QuestionsAndResponsesDoesNotMatchException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.QUESTIONS_AND_RESPONSES_DOES_NOT_MATCH", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(QUESTIONS_AND_RESPONSES_DOES_NOT_MATCH, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(UserInsufficientAnswerException.class)
    public ResponseEntity<ExceptionResponse> handleUserInsufficientAnswerException(UserInsufficientAnswerException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.USER_INSUFFICIENT_ANSWER", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(USER_INSUFFICIENT_ANSWER, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(UserDoesNotExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserDoesNotExistsException(UserDoesNotExistsException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.USER_DOES_NOT_EXIST", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(USER_DOES_NOT_EXIST, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(SurveyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyNotFoundException(SurveyNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.SURVEY_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(SURVEY_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(ClassroomNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleClassroomNotFoundException(ClassroomNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.CLASSROOM_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(CLASSROOM_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(ClassroomExistException.class)
    public ResponseEntity<ExceptionResponse> handleClassroomExistException(ClassroomExistException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.CLASSROOM_ALREADY_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(CLASSROOM_ALREADY_EXISTS, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(StudentTagExistException.class)
    public ResponseEntity<ExceptionResponse> handleStudentTagExistException(StudentTagExistException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.STUDENT_TAG_ALREADY_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(STUDENT_TAG_ALREADY_EXISTS, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(QuestionTagExistException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionTagExistException(QuestionTagExistException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.QUESTION_TAG_ALREADY_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(QUESTION_TAG_ALREADY_EXISTS, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(TrainerTagExistException.class)
    public ResponseEntity<ExceptionResponse> handleTrainerTagExistException(TrainerTagExistException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.TRAINER_TAG_ALREADY_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(TRAINER_TAG_ALREADY_EXISTS, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(SurveyTagExistException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyTagExistException(SurveyTagExistException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.SURVEY_TAG_ALREADY_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(SURVEY_TAG_ALREADY_EXISTS, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(RoleAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleRoleAlradyExistException(RoleAlreadyExistException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.ROLE_ALREADY_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(ROLE_ALREADY_EXISTS, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRoleNotFoundException(RoleNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.ROLE_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(ROLE_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTagNotFoundException(TagNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.TAG_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(TAG_NOT_FOUND, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(TagAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleTagAlreadyExistsException(TagAlreadyExistsException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.TAG_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(TAG_ALREADY_EXIST, exception, request);
    }


    private ResponseEntity<ExceptionResponse> createExceptionInfoResponse(ExceptionType exceptionType, Exception exception, HttpServletRequest request) {
        String exceptionName = exceptionType.name();
        Locale locale;
        String acceptLanguage = request.getHeader("Accept-Language");
        if (acceptLanguage != null && acceptLanguage.toLowerCase().startsWith("tr")) {
            locale = Locale.getDefault();
        } else if (acceptLanguage != null && acceptLanguage.toLowerCase().startsWith("nl")){
            Locale nlLocale = new Locale("nl");
            locale = nlLocale;
        } else {
            locale = Locale.US;
        }
        String localizedMessage = messageSource.getMessage("exception." + exceptionName, null, locale);
        return new ResponseEntity<>(ExceptionResponse.builder()
                .exceptionCode(exceptionType.getCode())
                .customMessage(localizedMessage)
                .exceptionMessage(exception.getMessage())
                .httpStatus(exceptionType.getHttpStatus().value())
                .build(), exceptionType.getHttpStatus());
    }

    @ResponseBody
    @ExceptionHandler(TrainerTagNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTrainerTagNotFoundException(TrainerTagNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.TRAINER_TAG_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(TRAINER_TAG_NOT_FOUND, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTrainerNotFoundException(TrainerNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.TRAINER_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(TRAINER_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(SurveyTagNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyTagNotFoundException(SurveyTagNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.SURVEY_TAG_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(SURVEY_TAG_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(QuestionTagNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionTagNotFoundException(QuestionTagNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.QUESTION_TAG_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(QUESTION_TAG_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(SurveyTitleAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyTitleAlreadyExistException(SurveyTitleAlreadyExistException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.SURVEY_TITLE_ALREADY_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(SURVEY_TITLE_ALREADY_EXISTS, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(SurveyHasNotAssignedInToClassroomException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyHasNotAssignedInToClassroomException(SurveyHasNotAssignedInToClassroomException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.SURVEY_HAS_NOT_ASSIGNED_CLASSROOM", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(SURVEY_HAS_NOT_ASSIGNED_CLASSROOM, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(SurveyExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyExpiredException(SurveyExpiredException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.SURVEY_EXPIRED", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(SURVEY_EXPIRED, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(SurveyNotInitiatedException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyNotInitiatedException(SurveyNotInitiatedException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.SURVEY_NOT_INITIATED", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(SURVEY_NOT_INITIATED, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(UndefinedTokenException.class)
    public ResponseEntity<ExceptionResponse> handleUndefinedTokenException(UndefinedTokenException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.UNDEFINED_TOKEN", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(UNDEFINED_TOKEN, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(StudentTagNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleStudentTagNotFoundException(StudentTagNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.STUDENT_TAG_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(STUDENT_TAG_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(TagAlreadyAssignedException.class)
    public ResponseEntity<ExceptionResponse> handleTagAlreadyAssignedException(TagAlreadyAssignedException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.TAG_ALREADY_ASSIGNED", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(TAG_ALREADY_ASSIGNED, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(SurveyAlreadyAssignToClassException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyAlreadyAssingToClassException(SurveyAlreadyAssignToClassException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.SURVEY_ALREADY_ASSIGN_TO_CLASS", null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(SURVEY_ALREADY_ASSIGN_TO_CLASS,exception,request);
    }

    @ResponseBody
    @ExceptionHandler(ResponseNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResponseNotFoundException(ResponseNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.RESPONSE_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(RESPONSE_NOT_FOUND, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleStudentNotFoundException(StudentNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.STUDENT_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(STUDENT_NOT_FOUND, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(SurveyAssignInvalidDateException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyAssignInvalidDateException(SurveyAssignInvalidDateException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.SURVEY_ASSIGN_INVALID_DATE",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(SURVEY_ASSIGN_INVALID_DATE,exception,request);
    }
    @ResponseBody
    @ExceptionHandler(SurveyAnsweredException.class)
    public ResponseEntity<ExceptionResponse> handleSurveyAnsweredException(SurveyAnsweredException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.SURVEY_ANSWERED",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(SURVEY_ANSWERED,exception,request);
    }
    @ResponseBody
    @ExceptionHandler(QuestionAnsweredException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionAnsweredException(QuestionAnsweredException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.QUESTION_ANSWERED",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(QUESTION_ANSWERED,exception,request);
    }
    @ResponseBody
    @ExceptionHandler(SurveyInUseException.class)
    public ResponseEntity<ExceptionResponse> surveyInUseException(SurveyInUseException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.SURVEY_IN_USE",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(SURVEY_IN_USE,exception,request);
    }

    @ResponseBody
    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<ExceptionResponse> branchNotFoundException(BranchNotFoundException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.BRANCH_NOT_FOUND",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(BRANCH_NOT_FOUND,exception,request);
    }

    @ResponseBody
    @ExceptionHandler(BranchAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> branchAlreadyExistException(BranchAlreadyExistException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.BRANCH_ALREADY_EXIST",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(BRANCH_ALREADY_EXIST,exception,request);
    }
    @ResponseBody
    @ExceptionHandler(BranchIsUpToDateException.class)
    public ResponseEntity<ExceptionResponse> branchIsUpToDateException(BranchIsUpToDateException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.BRANCH_IS_UP_TO_DATE",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(BRANCH_IS_UP_TO_DATE,exception,request);
    }
    @ResponseBody
    @ExceptionHandler(CourseGroupNotFoundException.class)
    public ResponseEntity<ExceptionResponse> courseGroupExistException(CourseGroupNotFoundException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.COURSE_GROUP_NOT_FOUND",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(COURSE_GROUP_NOT_FOUND,exception,request);
    }
    @ResponseBody
    @ExceptionHandler(CourseAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> courseAlreadyExistException(CourseAlreadyExistException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.COURSE_ALREADY_EXIST",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(COURSE_ALREADY_EXIST,exception,request);
    }

    @ResponseBody
    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ExceptionResponse> courseNotFoundException(CourseAlreadyExistException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.COURSE_NOT_FOUND",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(COURSE_NOT_FOUND,exception,request);
    }

    @ResponseBody
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ExceptionResponse> invalidFormatException(InvalidFormatException exception, HttpServletRequest request){
        log.warn(messageSource.getMessage("exception.DATA_NOT_VALID",null,Locale.getDefault()),exception);
        return createExceptionInfoResponse(DATA_NOT_VALID, exception, request);
    }
}