package com.bilgeadam.basurveyapp.services;

import com.bilgeadam.basurveyapp.dto.request.TrainerUpdateDto;
import com.bilgeadam.basurveyapp.dto.response.AssistantTrainerResponseDto;
import com.bilgeadam.basurveyapp.dto.response.MasterTrainerResponseDto;
import com.bilgeadam.basurveyapp.dto.response.TrainerResponseDto;
import com.bilgeadam.basurveyapp.dto.response.TrainerResponseListTagsDto;
import com.bilgeadam.basurveyapp.entity.Student;
import com.bilgeadam.basurveyapp.entity.Trainer;
import com.bilgeadam.basurveyapp.entity.TrainerExTags;
import com.bilgeadam.basurveyapp.entity.User;
import com.bilgeadam.basurveyapp.entity.enums.State;
import com.bilgeadam.basurveyapp.entity.tags.TrainerTag;
import com.bilgeadam.basurveyapp.exceptions.custom.ResourceNotFoundException;
import com.bilgeadam.basurveyapp.exceptions.custom.TrainerNotFoundException;
import com.bilgeadam.basurveyapp.exceptions.custom.TrainerTagNotFoundException;
import com.bilgeadam.basurveyapp.mapper.TrainerMapper;
import com.bilgeadam.basurveyapp.repositories.TrainerRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainerTagService trainerTagService;
    private final TrainerExTagsService trainerExTagsService;

    public TrainerService(TrainerRepository trainerRepository, @Lazy TrainerTagService trainerTagService, @Lazy TrainerExTagsService trainerExTagsService) {
        this.trainerRepository = trainerRepository;
        this.trainerTagService = trainerTagService;
        this.trainerExTagsService = trainerExTagsService;
    }

    public Boolean createTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
        return true;
    }
    public TrainerResponseDto updateTrainer (TrainerUpdateDto dto){
        Optional<Trainer> trainer = trainerRepository.findActiveById(dto.getTrainerOid());
        Optional<TrainerTag> trainerTag = trainerTagService.findOptionalTrainerTagById(dto.getTrainerTagOid());
        if (trainerTag.isEmpty()) {
            throw new TrainerTagNotFoundException("Trainer tag is not found");
        }
        if (trainer.isEmpty()) {
            throw new TrainerNotFoundException("Trainer is not found");
        }

        List<Long> traineroids =trainerTagService.findTrainerOidByTrainerTagOid(trainerTag.get().getOid());
        List<Optional<Trainer>> trainerList =traineroids.stream().map(trainerRepository::findTrainerByTrainerOid).toList();
        List<Trainer> assistantTrainer = trainerList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(t -> !t.isMasterTrainer())
                .toList();
        List<Trainer> masterTrainer = trainerList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(t -> t.isMasterTrainer())
                .toList();

        if(trainer.get().isMasterTrainer() && !masterTrainer.isEmpty()){
            saveExTrainerTag(trainer, trainerTag, masterTrainer);

        }
        if(!trainer.get().isMasterTrainer() &&!assistantTrainer.isEmpty()){
            saveExTrainerTag(trainer, trainerTag, assistantTrainer);
        }
        else {
            trainer.get().getTrainerTags().add(trainerTag.get());
            trainerTag.get().getTargetEntities().add(trainer.get());
            trainerRepository.save(trainer.get());
            trainerTagService.save(trainerTag.get());
        }
      return TrainerMapper.INSTANCE.toTrainerResponseDto(trainer.get());
    }

    private void saveExTrainerTag(Optional<Trainer> trainer, Optional<TrainerTag> trainerTag, List<Trainer> trainers) {
        trainerExTagsService.saveExTrainerTag(TrainerExTags.builder()
                        .trainer(trainers.get(0))
                        .trainerTag(trainerTag.get())
                .build());
        trainerTag.get().getTargetEntities().remove(trainers.get(0));
        trainer.get().getTrainerTags().add(trainerTag.get());
        trainerTag.get().getTargetEntities().add(trainer.get());
        trainerRepository.save(trainer.get());
        trainerTagService.save(trainerTag.get());
    }

    public Optional<Trainer> findTrainerByUserOid(Long oid) {

        return trainerRepository.findTrainerByUserOid(oid);
    }

    public Optional<Trainer> findActiveById(Long trainerOid) {

        return trainerRepository.findActiveById(trainerOid);
    }

    public List<MasterTrainerResponseDto> getMasterTrainerList() {

        List<Trainer> masterTrainers = trainerRepository.findAllMasterTrainers();

        return TrainerMapper.INSTANCE.toMasterTrainerResponseDtoList(masterTrainers);
    }


    public List<AssistantTrainerResponseDto> getAssistantTrainerList() {

        List<Trainer> assistantTrainers = trainerRepository.findAllAssistantTrainers();

        return TrainerMapper.INSTANCE.toAssistantTrainerResponseDtos(assistantTrainers);
    }

    public List<TrainerResponseListTagsDto> getTrainerList() {

        List<Trainer> findAllTrainers = trainerRepository.findAllActive();

        return TrainerMapper.INSTANCE.toTrainerResponseListTagsDtos(findAllTrainers);
    }

    public Optional<Trainer> findActiveByEmail(String email) {
        return trainerRepository.findActiveByEmail(email);
    }


    // Kullanılan ilk metod değiştirildi. findTrainerByUserOid > findActiveTrainerByUserOid
    public Boolean deleteByTrainerOid(Long oid) {
      Optional<Trainer> trainer =  trainerRepository.findActiveTrainerByUserOid(oid);
        Trainer userOfTrainer = findActiveById(trainer.get().getOid()).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        userOfTrainer.getUser().setState(State.DELETED);
        trainerRepository.save(userOfTrainer);
      return trainerRepository.softDeleteById(trainer.get().getOid());
    }

    //deleteByTrainerOid metodu beklendiği gibi çalışmadığı için yeni metod eklendi.
    public void deleteTrainerByUser(User user){
        Optional<Trainer> trainer = trainerRepository.findByUser(user);
        if (trainer.isPresent()){
            trainer.get().setState(State.DELETED);
            trainerRepository.save(trainer.get());
        }
    }
    public User findUserByTrainerOid(Long oid) {

        Optional<Trainer> trainer = trainerRepository.findTrainerByUserOid(oid);
        if (trainer.isEmpty()) {
            throw new ResourceNotFoundException("student id bulunamadı.");
        }
        return trainer.get().getUser();
    }

    /**
     * API işlemleri için hazırlandı.
     * @param User Databasede kayıt olan kullanıcı.
     * @return User'ı barındıran trainer nesnesi
     */
    public Optional<Trainer> findByUser(User user){
       return trainerRepository.findByUser(user);
    }

}
