package hu.evocelot.sample.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.evocelot.sample.dto.SampleEntityWithIdDto;
import hu.evocelot.sample.model.SampleEntity;

/**
 * Converter class that handles conversion between {@link SampleEntity} and
 * {@link SampleEntityWithIdDto}.
 * 
 * @author mark.danisovszky
 */
@Component
public class SampleEntityWithIdConverter implements EntityConverter<SampleEntity, SampleEntityWithIdDto> {

    @Autowired
    private SampleEntityConverter sampleEntityConverter;

    @Override
    public SampleEntityWithIdDto convert(SampleEntity sourceEntity) {
        SampleEntityWithIdDto dto = new SampleEntityWithIdDto();

        convert(sourceEntity, dto);

        return dto;
    }

    @Override
    public void convert(SampleEntity sourceEntity, SampleEntityWithIdDto destinationType) {
        sampleEntityConverter.convert(sourceEntity, destinationType);

        destinationType.setId(sourceEntity.getId());
    }

    @Override
    public SampleEntity convert(SampleEntityWithIdDto sourceType) {
        SampleEntity entity = new SampleEntity();

        convert(sourceType, entity);

        return entity;
    }

    @Override
    public void convert(SampleEntityWithIdDto sourceType, SampleEntity destionationEntity) {
        sampleEntityConverter.convert(sourceType, destionationEntity);

        destionationEntity.setId(sourceType.getId());
    }
}