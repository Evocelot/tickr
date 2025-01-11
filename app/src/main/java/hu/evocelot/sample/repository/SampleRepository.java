package hu.evocelot.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.evocelot.sample.model.SampleEntity;

/**
 * Sample repository for defining the custom functions for the
 * {@link SampleEntity}.
 * 
 * @author mark.danisovszky
 */
@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, String> {

}
