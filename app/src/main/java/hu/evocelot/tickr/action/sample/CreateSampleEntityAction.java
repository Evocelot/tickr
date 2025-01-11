package hu.evocelot.tickr.action.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import hu.evocelot.tickr.converter.SampleEntityConverter;
import hu.evocelot.tickr.converter.SampleEntityWithIdConverter;
import hu.evocelot.tickr.dto.SampleEntityDto;
import hu.evocelot.tickr.dto.SampleEntityWithIdDto;
import hu.evocelot.tickr.model.SampleEntity;
import hu.evocelot.tickr.service.SampleService;

/**
 * Sample action class for creating sample entities.
 * 
 * @author mark.danisovszky
 */
@Component
public class CreateSampleEntityAction {

    @Autowired
    private SampleEntityConverter sampleEntityConverter;

    @Autowired
    private SampleEntityWithIdConverter sampleEntityWithIdConverter;

    @Autowired
    private SampleService sampleService;

    /**
     * Creates a new sample entity.
     *
     * @param sampleEntityDto - Data for the new sample.
     * @return - with {@link ResponseEntity} that contains the
     *         {@link SampleEntityWithIdDto}.
     */
    public ResponseEntity<SampleEntityWithIdDto> createSampleEntity(SampleEntityDto sampleEntityDto) {
        // Create entity based on the DTO.
        SampleEntity sampleEntity = sampleEntityConverter.convert(sampleEntityDto);
        sampleEntity = sampleService.save(sampleEntity);

        // Create the response DTO.
        SampleEntityWithIdDto response = sampleEntityWithIdConverter.convert(sampleEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
