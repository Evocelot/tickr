package hu.evocelot.tickr.action.sample;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import hu.evocelot.tickr.converter.SampleEntityConverter;
import hu.evocelot.tickr.converter.SampleEntityWithIdConverter;
import hu.evocelot.tickr.dto.SampleEntityDto;
import hu.evocelot.tickr.dto.SampleEntityWithIdDto;
import hu.evocelot.tickr.exception.BaseException;
import hu.evocelot.tickr.exception.ExceptionType;
import hu.evocelot.tickr.model.SampleEntity;
import hu.evocelot.tickr.service.SampleService;

/**
 * Sample action class for updating sample entities.
 * 
 * @author mark.danisovszky
 */
@Component
public class UpdateSampleEntityAction {

    @Autowired
    private SampleEntityConverter sampleEntityConverter;

    @Autowired
    private SampleEntityWithIdConverter sampleEntityWithIdConverter;

    @Autowired
    private SampleService sampleService;

    /**
     * Updates a sample entity.
     *
     * @param id              - the id of the entity to update.
     * @param sampleEntityDto - the updated details of the entity.
     * @return - with {@link ResponseEntity} that contains the
     *         {@link SampleEntityWithIdDto}.
     * @throws BaseException - when the entity not found.
     */
    public ResponseEntity<SampleEntityWithIdDto> updateSampleEntity(String id,
            SampleEntityDto sampleEntityDto) throws BaseException {
        // Find the entity to update.
        Optional<SampleEntity> optionalSampleEntity = sampleService.findById(id);
        if (optionalSampleEntity.isEmpty()) {
            throw new BaseException(HttpStatus.NOT_FOUND, ExceptionType.SAMPLE_ENTITY_NOT_FOUND,
                    "Cannot find sample entity with id :" + id);
        }

        // Update the entity details.
        SampleEntity sampleEntity = optionalSampleEntity.get();
        sampleEntityConverter.convert(sampleEntityDto, sampleEntity);
        sampleEntity = sampleService.save(sampleEntity);

        // Create the response DTO.
        SampleEntityWithIdDto response = sampleEntityWithIdConverter.convert(sampleEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
