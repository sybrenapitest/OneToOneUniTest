package org.shboland.api.convert;

import org.shboland.domain.entities.JsonDetailsSearchCriteria;
import org.shboland.persistence.criteria.DetailsSearchCriteria;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("detailsSearchCriteriaConverter")
public class DetailsSearchCriteriaConverter {

    public DetailsSearchCriteria createSearchCriteria(JsonDetailsSearchCriteria jsonDetailsSearchCriteria) {
        DetailsSearchCriteria.DetailsSearchCriteriaBuilder searchCriteriaBuilder = DetailsSearchCriteria.builder();

        searchCriteriaBuilder.start(jsonDetailsSearchCriteria.getStart());
        if (jsonDetailsSearchCriteria.getMaxResults() > 0) {
            searchCriteriaBuilder.maxResults(jsonDetailsSearchCriteria.getMaxResults());
        } else {
            throw new ConvertException("Maximum number of results should be a positive number.");
        }

        Long id = jsonDetailsSearchCriteria.getId();
        searchCriteriaBuilder.id(Optional.ofNullable(id));
        
        Integer age = jsonDetailsSearchCriteria.getAge();
        searchCriteriaBuilder.age(Optional.ofNullable(age));
    
        // @Input

        return searchCriteriaBuilder.build();
    }
    
    // @Function input
}