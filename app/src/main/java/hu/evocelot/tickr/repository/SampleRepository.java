package hu.evocelot.tickr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.evocelot.tickr.model.SampleEntity;

/**
 * Sample repository for defining the custom functions for the
 * {@link SampleEntity}.
 * 
 * @author mark.danisovszky
 */
@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, String> {

}
