package hu.evocelot.tickr.action.sample;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import hu.evocelot.tickr.converter.SampleEntityWithIdConverter;
import hu.evocelot.tickr.dto.SampleEntityWithIdDto;
import hu.evocelot.tickr.exception.BaseException;
import hu.evocelot.tickr.exception.ExceptionType;
import hu.evocelot.tickr.model.SampleEntity;
import hu.evocelot.tickr.service.SampleService;

/**
 * Sample action class for deleting sample entities.
 * 
 * @author mark.danisovszky
 */
@Component
public class DeleteSampleEntityAction {

    @Autowired
    private SampleEntityWithIdConverter sampleEntityWithIdConverter;

    @Autowired
    private SampleService sampleService;

    /**
     * Deletes a sample entity by ID.
     *
     * @param id - ID of the sample to delete.
     * @return - with {@link ResponseEntity} that contains the
     *         {@link SampleEntityWithIdDto}.
     */
    public ResponseEntity<SampleEntityWithIdDto> deleteSampleEntity(String id) throws BaseException {
        // Find the entity to delete.
        Optional<SampleEntity> optionalSampleEntity = sampleService.findById(id);
        if (optionalSampleEntity.isEmpty()) {
            throw new BaseException(HttpStatus.NOT_FOUND, ExceptionType.SAMPLE_ENTITY_NOT_FOUND,
                    "Cannot find sample entity with id :" + id);
        }

        // Store the details for the response.
        SampleEntity sampleEntity = optionalSampleEntity.get();
        SampleEntityWithIdDto response = sampleEntityWithIdConverter.convert(sampleEntity);

        // Delete the entity.
        sampleService.delete(sampleEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}