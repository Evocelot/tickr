package hu.evocelot.sample.dto;

import hu.evocelot.sample.model.SampleEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO class for {@link SampleEntity} base details with id.
 * 
 * @author mark.danisovszky
 */
public class SampleEntityWithIdDto extends SampleEntityDto {

    @Schema(description = "Unique identifier of the sample entity", required = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
