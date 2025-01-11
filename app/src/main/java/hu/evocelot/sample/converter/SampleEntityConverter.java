package hu.evocelot.sample.converter;

import org.springframework.stereotype.Component;

import hu.evocelot.sample.dto.SampleEntityDto;
import hu.evocelot.sample.model.SampleEntity;

/**
 * Converter class that handles conversion between {@link SampleEntity} and
 * {@link SampleEntityDto}.
 * 
 * @author mark.danisovszky
 */
@Component
public class SampleEntityConverter implements EntityConverter<SampleEntity, SampleEntityDto> {

    @Override
    public SampleEntity convert(SampleEntityDto sourceType) {
        SampleEntity entity = new SampleEntity();

        convert(sourceType, entity);

        return entity;
    }

    @Override
    public void convert(SampleEntityDto sourceType, SampleEntity destionationEntity) {
        destionationEntity.setSampleValue(sourceType.getSampleValue());
        destionationEntity.setSampleDate(sourceType.getSampleDate());
    }

    @Override
    public SampleEntityDto convert(SampleEntity sourceEntity) {
        SampleEntityDto dto = new SampleEntityDto();

        convert(sourceEntity, dto);

        return dto;
    }

    @Override
    public void convert(SampleEntity sourceEntity, SampleEntityDto destinationType) {
        destinationType.setSampleValue(sourceEntity.getSampleValue());
        destinationType.setSampleDate(sourceEntity.getSampleDate());
    }
}
