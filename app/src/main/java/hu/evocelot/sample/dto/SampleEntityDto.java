package hu.evocelot.sample.dto;

import java.time.OffsetDateTime;

import hu.evocelot.sample.model.SampleEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO class for {@link SampleEntity} base details.
 * 
 * @author mark.danisovszky
 */
public class SampleEntityDto {

    @Schema(description = "The sample value", required = true)
    private String sampleValue;

    @Schema(description = "The sample date in UTC", example = "2025-01-01T10:00:00+00:00", required = true)
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
