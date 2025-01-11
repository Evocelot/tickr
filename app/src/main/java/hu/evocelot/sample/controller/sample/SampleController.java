package hu.evocelot.sample.controller.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.evocelot.sample.action.sample.CreateSampleEntityAction;
import hu.evocelot.sample.action.sample.DeleteSampleEntityAction;
import hu.evocelot.sample.action.sample.GetSampleEntityAction;
import hu.evocelot.sample.action.sample.UpdateSampleEntityAction;
import hu.evocelot.sample.dto.SampleEntityDto;
import hu.evocelot.sample.dto.SampleEntityWithIdDto;
import io.swagger.v3.oas.annotations.Operation;

/**
 * Sample RestController.
 * 
 * @author mark.danisovszky
 */
@RestController
@RequestMapping("/sample")
public class SampleController {

	@Autowired
	private GetSampleEntityAction getSampleEntityAction;

	@Autowired
	private CreateSampleEntityAction createSampleEntityAction;

	@Autowired
	private UpdateSampleEntityAction updateSampleEntityAction;

	@Autowired
	private DeleteSampleEntityAction deleteSampleEntityAction;

	/**
	 * Get a sample entity by ID.
	 *
	 * @param sampleEntityId - ID of the sample entity to get.
	 * @return - with {@link ResponseEntity} that contains the
	 *         {@link SampleEntityWithIdDto}.
	 * @throws Exception - when the sample entity not found.
	 */
	@GetMapping("/{id}")
	@Operation(summary = SampleControllerInformation.GET_SAMPLE_SUMMARY, description = SampleControllerInformation.GET_SAMPLE_DESCRIPTION)
	public ResponseEntity<SampleEntityWithIdDto> getSampleEntity(@Param(value = "id") String id)
			throws Exception {
		return getSampleEntityAction.getSampleEntity(id);
	}

	/**
	 * Creates a new sample entity.
	 *
	 * @param sampleEntityDto - Data for the new sample.
	 * @return - with {@link ResponseEntity} that contains the
	 *         {@link SampleEntityWithIdDto}.
	 */
	@PostMapping
	@Operation(summary = SampleControllerInformation.CREATE_SAMPLE_SUMMARY, description = SampleControllerInformation.CREATE_SAMPLE_DESCRIPTION)
	public ResponseEntity<SampleEntityWithIdDto> createSampleEntity(
			@RequestBody SampleEntityDto sampleEntityDto) {
		return createSampleEntityAction.createSampleEntity(sampleEntityDto);
	}

	/**
	 * Updates an existing sample entity.
	 *
	 * @param id              - ID of the sample to update.
	 * @param sampleEntityDto - Updated data for the sample.
	 * @return - Updated SampleDto.
	 * @throws Exception - when the entity not found.
	 */
	@PutMapping("/{id}")
	@Operation(summary = SampleControllerInformation.UPDATE_SAMPLE_SUMMARY, description = SampleControllerInformation.UPDATE_SAMPLE_DESCRIPTION)
	public ResponseEntity<SampleEntityWithIdDto> updateSampleEntity(@PathVariable String id,
			@RequestBody SampleEntityDto sampleEntityDto) throws Exception {
		return updateSampleEntityAction.updateSampleEntity(id, sampleEntityDto);
	}

	/**
	 * Deletes a sample entity by ID.
	 *
	 * @param id - ID of the sample to delete.
	 * @return - with {@link ResponseEntity} that contains the
	 *         {@link SampleEntityWithIdDto}.
	 * @throws Exception - when the entity not found.
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = SampleControllerInformation.DELETE_SAMPLE_SUMMARY, description = SampleControllerInformation.DELETE_SAMPLE_DESCRIPTION)
	public ResponseEntity<SampleEntityWithIdDto> deleteSampleEntity(@PathVariable String id) throws Exception {
		return deleteSampleEntityAction.deleteSampleEntity(id);
	}
}
