package com.bilgeadam.basurveyapp.exceptions;

import com.bilgeadam.basurveyapp.exceptions.custom.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.bilgeadam.basurveyapp.exceptions.ExceptionType.*;

/**
 * @author Eralp Nitelik
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception) {
        log.error("Unhandled error occurred!", exception);
        return createExceptionInfoResponse(UNEXPECTED_ERROR, exception);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException exception) {
        log.error("Unhandled runtime error occurred!", exception);
        return createExceptionInfoResponse(UNEXPECTED_ERROR, exception);
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(UsernameNotFoundException exception) {
        log.warn("Auth does not exist or deleted.", exception);
        return createExceptionInfoResponse(LOGIN_ERROR_USERNAME_DOES_NOT_EXIST, exception);
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException exception) {
        log.warn("Authentication information does not match. {}", exception.getMessage());
        return createExceptionInfoResponse(LOGIN_ERROR_WRONG_PASSWORD, exception);
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        log.warn("Unique key already exists on database. {}", exception.getMessage());
        return createExceptionInfoResponse(REGISTER_ERROR_DATA_EXISTS, exception);
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.warn("Unique key already exists on database. {}", exception.getMessage());
        return createExceptionInfoResponse(RESOURCE_NOT_FOUND, exception);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn("Incoming data validation failed. {}", exception.getMessage());
        return createExceptionInfoResponse(DATA_NOT_VALID, exception);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException exception) {
        log.warn("Access denied, user role unauthorized. {}", exception.getMessage());
        return createExceptionInfoResponse(ACCESS_DENIED, exception);
    }
    @ResponseBody
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionNotFoundException(QuestionNotFoundException exception) {
        log.warn("Question is not found. {}", exception.getMessage());
        return createExceptionInfoResponse(QUESTION_NOT_FOUND, exception);
    }
    @ResponseBody
    @ExceptionHandler(QuestionTypeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionTypeNotFoundException(QuestionTypeNotFoundException exception) {
        log.warn("Question type is not found. {}", exception.getMessage());
        return createExceptionInfoResponse(QUESTION_TYPE_NOT_FOUND, exception);
    }
    @ResponseBody
    @ExceptionHandler(AlreadyAnsweredSurveyException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyAnsweredSurveyException(AlreadyAnsweredSurveyException exception) {
        log.warn("User has already answered. {}", exception.getMessage());
        return createExceptionInfoResponse(SURVEY_ALREADY_ANSWERED, exception);
    }
    @ResponseBody
    @ExceptionHandler(QuestionsAndResponsesDoesNotMatchException.class)
    public ResponseEntity<ExceptionResponse> handleQuestionsAndResponsesDoesNotMatchException(QuestionsAndResponsesDoesNotMatchException exception) {
        log.warn("User has already answered. {}", exception.getMessage());
        return createExceptionInfoResponse(QUESTIONS_AND_RESPONSES_DOES_NOT_MATCH, exception);
    }
    @ResponseBody
    @ExceptionHandler(UserInsufficientAnswerException.class)
    public ResponseEntity<ExceptionResponse> handleUserInsufficientanswerException(UserInsufficientAnswerException exception) {
        log.warn("User must answer all the questions. {}", exception.getMessage());
        return createExceptionInfoResponse(USER_INSUFFICIENT_ANSWER, exception);
    }
    @ResponseBody
    @ExceptionHandler(UserDoesNotExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserDoesNotExistsException(UserDoesNotExistsException exception) {
        log.warn("User deleted or doesnt exist. {}", exception.getMessage());
        return createExceptionInfoResponse(USER_DOES_NOT_EXIST, exception);
    }

    @ResponseBody
    @ExceptionHandler(ClassroomNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleClassroomNotFoundException(ClassroomNotFoundException exception) {
        log.warn("Classroom is not found. {}", exception.getMessage());
        return createExceptionInfoResponse(CLASSROOM_NOT_FOUND, exception);
    }

    @ResponseBody
    @ExceptionHandler(ClassroomExistException.class)
    public ResponseEntity<ExceptionResponse> handleClassroomExistException(ClassroomExistException exception) {
        log.warn("Classroom is already exist. {}", exception.getMessage());
        return createExceptionInfoResponse(CLASSROOM_ALREADY_EXISTS, exception);
    }

    private ResponseEntity<ExceptionResponse> createExceptionInfoResponse(ExceptionType exceptionType, Exception exception) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .exceptionCode(exceptionType.getCode())
                .customMessage(exceptionType.getMessage())
                .exceptionMessage(exception.getMessage())
                .httpStatus(exceptionType.getHttpStatus().value())
                .build(), exceptionType.getHttpStatus());
    }
}
