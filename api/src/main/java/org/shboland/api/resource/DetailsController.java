package org.shboland.api.resource;

import org.shboland.api.convert.PersonConverter;
import org.shboland.core.service.PersonService;
import org.shboland.persistence.db.hibernate.bean.Person;
import org.shboland.persistence.db.hibernate.bean.Details;
import java.util.stream.Collectors;
import javax.ws.rs.BeanParam;
import java.util.List;
import java.util.ArrayList;
import org.shboland.persistence.criteria.DetailsSearchCriteria;
import org.shboland.api.convert.ConvertException;
import org.shboland.domain.entities.JsonSearchResult;
import org.shboland.domain.entities.JsonDetailsSearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.shboland.api.convert.DetailsSearchCriteriaConverter;
import java.net.URISyntaxException;
import org.springframework.http.HttpStatus;
import org.shboland.domain.entities.JsonDetails;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.shboland.core.service.DetailsService;
import org.shboland.api.convert.DetailsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DetailsController implements IDetailsController {

    private final DetailsService detailsService;
    private final DetailsConverter detailsConverter;
    private final DetailsSearchCriteriaConverter detailsSearchCriteriaConverter;
    private final PersonService personService;
    private final PersonConverter personConverter;
    // @FieldInput

    @Autowired
    public DetailsController(PersonConverter personConverter, PersonService personService, DetailsSearchCriteriaConverter detailsSearchCriteriaConverter, DetailsService detailsService, DetailsConverter detailsConverter) {
        this.detailsService = detailsService;
        this.detailsConverter = detailsConverter;
        this.detailsSearchCriteriaConverter = detailsSearchCriteriaConverter;
        this.personService = personService;
        this.personConverter = personConverter;
        // @ConstructorInput
    }
    
    // @Input

    @Override
    public ResponseEntity getPerson(@PathVariable long detailsId) {
        Person person = personService.fetchPersonForDetails(detailsId);

        return person != null ? 
                    ResponseEntity.ok(personConverter.toJson(person)) : 
                    ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity deletePersonWithDetails(@PathVariable long detailsId, @PathVariable long personId) {

        return detailsService.removePerson(detailsId, personId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity putPersonWithDetails(@PathVariable long detailsId, @PathVariable long personId) {

        return detailsService.updateDetailsWithPerson(detailsId, personId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<JsonDetails> getDetails(@PathVariable long detailsId) {
        Optional<Details> detailsOptional = detailsService.fetchDetails(detailsId);

        return detailsOptional.isPresent() ?
                ResponseEntity.ok(detailsConverter.toJson(detailsOptional.get())) :
                ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<JsonSearchResult> list(@BeanParam JsonDetailsSearchCriteria searchCriteria) {

        DetailsSearchCriteria sc;
        try {
            sc = detailsSearchCriteriaConverter.createSearchCriteria(searchCriteria);
        } catch (ConvertException e) {
            log.warn("Conversion failed!", e);
            return ResponseEntity.badRequest().build();
        }

        List<Details> detailsList = new ArrayList<>();
        int numberOfDetails = detailsService.findNumberOfDetails(sc);
        if (numberOfDetails > 0) {
            detailsList = detailsService.findBySearchCriteria(sc);
        }

        JsonSearchResult<JsonDetails> result = JsonSearchResult.<JsonDetails>builder()
                .results(detailsList.stream().map(detailsConverter::toJson).collect(Collectors.toList()))
                .numberOfResults(detailsList.size())
                .grandTotalNumberOfResults(numberOfDetails)
                .build();

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity postDetails(@RequestBody JsonDetails jsonDetails) throws URISyntaxException {
            
        Details newDetails = detailsService.save(detailsConverter.fromJson(jsonDetails));

        return ResponseEntity.status(HttpStatus.CREATED).body(detailsConverter.toJson(newDetails));
    }

    @Override
    public ResponseEntity<JsonDetails> putDetails(@PathVariable long detailsId, @RequestBody JsonDetails jsonDetails) {

        Optional<Details> detailsOptional = detailsService.fetchDetails(detailsId);

        Details savedDetails;
        if (!detailsOptional.isPresent()) {
            savedDetails = detailsService.save(detailsConverter.fromJson(jsonDetails));
        } else {
            savedDetails = detailsService.save(detailsConverter.fromJson(jsonDetails, detailsId));
        }

        return ResponseEntity.ok(detailsConverter.toJson(savedDetails));
    }

    @Override
    public ResponseEntity deleteDetails(@PathVariable long detailsId) {

        return detailsService.deleteDetails(detailsId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }
    
}