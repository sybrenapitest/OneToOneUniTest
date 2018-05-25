package org.shboland.persistence.db.repo;

import org.shboland.persistence.db.hibernate.bean.Details;
import org.shboland.persistence.criteria.DetailsSearchCriteria;

import java.util.List;

public interface DetailsRepositoryCustom {

    int findNumberOfDetailsBySearchCriteria(DetailsSearchCriteria sc);

    List<Details> findBySearchCriteria(DetailsSearchCriteria sc);
}
