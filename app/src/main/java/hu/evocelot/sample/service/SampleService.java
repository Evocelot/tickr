package hu.evocelot.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import hu.evocelot.sample.model.SampleEntity;
import hu.evocelot.sample.repository.SampleRepository;

/**
 * Sample service for managing the {@link SampleEntity}.
 * 
 * @author mark.danisovszky
 */
@Service
public class SampleService extends AbstractBaseService<SampleEntity> {

    @Autowired
    private SampleRepository sampleRepository;

    @Override
    protected JpaRepository getRepository() {
        return sampleRepository;
    }
}
