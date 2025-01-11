package hu.evocelot.sample.service;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import hu.evocelot.sample.model.AbstractIdentifiedAuditEntity;

/**
 * Base class for all services that provide common functionality
 * such as save, saveAll, findById, and delete operations.
 * It is intended to be extended by concrete service classes to manage entities
 * that extend {@link AbstractIdentifiedAuditEntity}.
 * <p>
 * Provides audit field management for entities and logging.
 * </p>
 * 
 * @param <T> the type of entity this service handles, extending
 *            {@link AbstractIdentifiedAuditEntity}
 * @author mark.danisovszky
 */
public abstract class AbstractBaseService<T extends AbstractIdentifiedAuditEntity> {

    private static final Logger LOG = LogManager.getLogger(AbstractBaseService.class);

    /**
     * Provides the JpaRepository that will be used for managing entities.
     * <p>
     * Concrete implementations should return the relevant repository for their
     * entity type.
     * </p>
     * 
     * @return the {@link JpaRepository} to use
     */
    protected abstract JpaRepository getRepository();

    /**
     * Saves or updates an entity. Manages audit fields such as version,
     * modification date, and modification user.
     * <p>
     * This method increments the version, updates the modification date, and sets
     * the modification user before saving the entity.
     * </p>
     * 
     * @param entity the entity to save or update
     * @return the updated entity
     */
    public T save(T entity) {
        entity.setVersion(entity.getVersion().add(BigInteger.valueOf(1)));
        entity.setModDate(OffsetDateTime.now());
        entity.setModUser("unknown"); // TODO: Get current user.
        T savedEntity = (T) getRepository().save(entity);

        LOG.debug(MessageFormat.format("Saved {0} entity with id {1}.", savedEntity.getClass(), savedEntity.getId()));

        return savedEntity;
    }

    /**
     * Saves all the given entities in a single batch operation.
     * 
     * This method delegates to the underlying repository's {@code saveAll} method,
     * ensuring efficient batch processing for the provided collection of entities.
     * Each entity in the collection will be persisted or updated in the database.
     * 
     * @param entities the collection of entities to save; must not be {@code null}
     *                 or empty
     * @return a list of the saved entities, including any updates made during the
     *         save operation
     *         (e.g., generated IDs or default values applied by the persistence
     *         layer)
     * @throws IllegalArgumentException if the given collection is {@code null}
     */
    public List<T> saveAll(Collection<T> entities) {
        List<T> savedEntities = getRepository().saveAll(entities);

        LOG.debug(MessageFormat.format("Saved {0} entity in batch operation.", savedEntities.size()));

        return savedEntities;
    }

    /**
     * Finds an entity by its ID.
     * <p>
     * This method returns an {@link Optional} to allow handling cases where the
     * entity is not found.
     * </p>
     * 
     * @param id the ID of the entity to find
     * @return an {@link Optional} containing the entity if found, or an empty
     *         {@link Optional} if not
     */
    public Optional<T> findById(String id) {
        return getRepository().findById(id);
    }

    /**
     * Deletes an entity by its ID.
     * <p>
     * If the entity does not exist, a {@link EmptyResultDataAccessException} will
     * be thrown.
     * </p>
     * 
     * @param id the ID of the entity to delete
     * @throws EmptyResultDataAccessException if no entity with the given ID exists
     */
    public void deleteById(String id) {
        try {
            getRepository().deleteById(id);
            LOG.debug("Deleted entity with ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            LOG.warn("Attempted to delete non-existent entity with ID: " + id);
        } catch (DataAccessException e) {
            LOG.error("Error deleting entity with ID: " + id, e);
            throw e; // Re-throw exception to allow caller to handle it
        }
    }

    /**
     * Deletes the given entity.
     * <p>
     * If the entity does not exist, a {@link DataAccessException} will be thrown.
     * </p>
     * 
     * @param entity the entity to delete
     * @throws DataAccessException if the entity cannot be deleted due to
     *                             persistence errors
     */
    public void delete(T entity) {
        try {
            getRepository().delete(entity);
            LOG.debug("Deleted entity: " + entity);
        } catch (DataAccessException e) {
            LOG.error("Error deleting entity: " + entity, e);
            throw e; // Re-throw exception to allow caller to handle it
        }
    }
}
