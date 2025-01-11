package hu.evocelot.sample.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * The SampleEntity.
 * 
 * @author mark.danisovszky
 */
@Entity
@Table(name = "SAMPLE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SampleEntity extends AbstractIdentifiedAuditEntity {

    @Column(name = "SAMPLE_VALUE")
    private String sampleValue;

    @Column(name = "SAMPLE_DATE")
    private OffsetDateTime sampleDate;

    public String getSampleValue() {
        return sampleValue;
    }

    public void setSampleValue(String sampleValue) {
        this.sampleValue = sampleValue;
    }

    public OffsetDateTime getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(OffsetDateTime sampleDate) {
        this.sampleDate = sampleDate;
    }
}
