package hu.evocelot.sample.action.sample;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import hu.evocelot.sample.converter.SampleEntityWithIdConverter;
import hu.evocelot.sample.dto.SampleEntityWithIdDto;
import hu.evocelot.sample.exception.BaseException;
import hu.evocelot.sample.exception.ExceptionType;
import hu.evocelot.sample.model.SampleEntity;
import hu.evocelot.sample.service.SampleService;

/**
 * Sample action class for getting sample entities.
 * 
 * @author mark.danisovszky
 */
@Component
public class GetSampleEntityAction {

    @Autowired
    private SampleEntityWithIdConverter sampleEntityWithIdConverter;

    @Autowired
    private SampleService sampleService;

    /**
     * Get a sample entity by ID.
     *
     * @param id - ID of the sample to get.
     * @return - with {@link ResponseEntity} that contains the
     *         {@link SampleEntityWithIdDto}.
     * @throws BaseException - when sample entity not found.
     */
    public ResponseEntity<SampleEntityWithIdDto> getSampleEntity(String id) throws BaseException {
        // Find the entity based on the id.
        Optional<SampleEntity> optionalSampleEntity = sampleService.findById(id);
        if (optionalSampleEntity.isEmpty()) {
            throw new BaseException(HttpStatus.NOT_FOUND, ExceptionType.SAMPLE_ENTITY_NOT_FOUND,
                    "Cannot find sample entity with id :" + id);
        }

        // Create the response dto.
        SampleEntityWithIdDto response = sampleEntityWithIdConverter.convert(optionalSampleEntity.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
