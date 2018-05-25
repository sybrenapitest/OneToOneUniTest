package org.shboland.api.convert;

import org.shboland.domain.entities.JsonPersonSearchCriteria;
import org.shboland.persistence.criteria.PersonSearchCriteria;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("personSearchCriteriaConverter")
public class PersonSearchCriteriaConverter {

    public PersonSearchCriteria createSearchCriteria(JsonPersonSearchCriteria jsonPersonSearchCriteria) {
        PersonSearchCriteria.PersonSearchCriteriaBuilder searchCriteriaBuilder = PersonSearchCriteria.builder();

        searchCriteriaBuilder.start(jsonPersonSearchCriteria.getStart());
        if (jsonPersonSearchCriteria.getMaxResults() > 0) {
            searchCriteriaBuilder.maxResults(jsonPersonSearchCriteria.getMaxResults());
        } else {
            throw new ConvertException("Maximum number of results should be a positive number.");
        }

        Long id = jsonPersonSearchCriteria.getId();
        searchCriteriaBuilder.id(Optional.ofNullable(id));
        
        String name = jsonPersonSearchCriteria.getName();
        searchCriteriaBuilder.name(Optional.ofNullable(name));
    
        // @Input

        return searchCriteriaBuilder.build();
    }
    
    // @Function input
}