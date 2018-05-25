package org.shboland.persistence.db.repo;

import org.shboland.persistence.db.hibernate.bean.Details;
import org.shboland.persistence.criteria.DetailsSearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DetailsRepositoryImpl extends AbstractHibernateRepository<Details> implements DetailsRepositoryCustom {

    private static final String ID_PROPERTY = "id";
    private static final String AGE_PROPERTY = "age";
    private static final String PERSON_PROPERTY = "person";
    // @Property input

    @Override
    protected Class<Details> getDomainClass() {
        return Details.class;
    }

    @Override
    public int findNumberOfDetailsBySearchCriteria(DetailsSearchCriteria sc) {
        CriteriaBuilder criteria = getDefaultCriteria();
        CriteriaQuery<Long> criteriaQuery = criteria.createQuery(Long.class);
        Root<Details> root = criteriaQuery.from(getDomainClass());

        List<Predicate> predicates = createPredicates(sc, criteria, root);

        criteriaQuery.select(criteria.count(root)).distinct(true)
                .where(predicates.toArray(new Predicate[predicates.size()]));

        return getEntityManager()
                .createQuery(criteriaQuery)
                .getSingleResult()
                .intValue();
    }

    @Override
    public List<Details> findBySearchCriteria(DetailsSearchCriteria sc) {
        CriteriaBuilder criteria = getDefaultCriteria();
        CriteriaQuery<Details> criteriaQuery = criteria.createQuery(getDomainClass());
        Root<Details> root = criteriaQuery.from(getDomainClass());

        List<Predicate> predicates = createPredicates(sc, criteria, root);

        criteriaQuery.select(root).distinct(true)
                .where(predicates.toArray(new Predicate[predicates.size()]));

        return getEntityManager()
                .createQuery(criteriaQuery)
                .setFirstResult(sc.getStart())
                .setMaxResults(sc.getMaxResults())
                .getResultList();
    }

    private List<Predicate> createPredicates(DetailsSearchCriteria sc, CriteriaBuilder criteria, Root<Details> root) {

        List<Predicate> predicates = new ArrayList<>();

        sc.getId().ifPresent(id -> predicates.add(criteria.equal(root.get(ID_PROPERTY), id)));
        
        sc.getAge().ifPresent(age -> predicates.add(criteria.equal(root.get(AGE_PROPERTY), age)));
    
        sc.getPersonId().ifPresent(personId -> predicates.add(criteria.equal(root.get(PERSON_PROPERTY).get(ID_PROPERTY), personId)));
    
        // @Predicate input

        return predicates;
    }
}
