package hu.evocelot.sample.converter;

import hu.evocelot.sample.model.AbstractIdentifiedAuditEntity;

/**
 * Base converter interface for converting between data types.
 * 
 * @author mark.danisovszky
 */
public interface EntityConverter<A extends AbstractIdentifiedAuditEntity, B> {

    /**
     * Creates {@link B} object based on the @param sourceEntity.
     * 
     * @param sourceEntity - the source.
     * @return - with the created {@link B}
     */
    public B convert(A sourceEntity);

    /**
     * Updates the {@link B} object fields based on the {@link A} object.
     * 
     * @param sourceEntity    - the source.
     * @param destinationType - the target.
     */
    public void convert(A sourceEntity, B destinationType);

    /**
     * Creates {@link A} object based on the @param sourceType.
     * 
     * @param sourceType - the source.
     * @return - with the created {@link A}
     */
    public A convert(B sourceType);

    /**
     * Updates the {@link A} object fields based on the {@link B} object.
     * 
     * @param sourceEntity    - the source.
     * @param destinationType - the target.
     */
    public void convert(B sourceType, A destionationEntity);
}
