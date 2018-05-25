package org.shboland.persistence.db.repo;

import org.shboland.persistence.db.hibernate.bean.Person;
import org.shboland.persistence.criteria.PersonSearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepositoryImpl extends AbstractHibernateRepository<Person> implements PersonRepositoryCustom {

    private static final String ID_PROPERTY = "id";
    private static final String NAME_PROPERTY = "name";
    // @Property input

    @Override
    protected Class<Person> getDomainClass() {
        return Person.class;
    }

    @Override
    public int findNumberOfPersonBySearchCriteria(PersonSearchCriteria sc) {
        CriteriaBuilder criteria = getDefaultCriteria();
        CriteriaQuery<Long> criteriaQuery = criteria.createQuery(Long.class);
        Root<Person> root = criteriaQuery.from(getDomainClass());

        List<Predicate> predicates = createPredicates(sc, criteria, root);

        criteriaQuery.select(criteria.count(root)).distinct(true)
                .where(predicates.toArray(new Predicate[predicates.size()]));

        return getEntityManager()
                .createQuery(criteriaQuery)
                .getSingleResult()
                .intValue();
    }

    @Override
    public List<Person> findBySearchCriteria(PersonSearchCriteria sc) {
        CriteriaBuilder criteria = getDefaultCriteria();
        CriteriaQuery<Person> criteriaQuery = criteria.createQuery(getDomainClass());
        Root<Person> root = criteriaQuery.from(getDomainClass());

        List<Predicate> predicates = createPredicates(sc, criteria, root);

        criteriaQuery.select(root).distinct(true)
                .where(predicates.toArray(new Predicate[predicates.size()]));

        return getEntityManager()
                .createQuery(criteriaQuery)
                .setFirstResult(sc.getStart())
                .setMaxResults(sc.getMaxResults())
                .getResultList();
    }

    private List<Predicate> createPredicates(PersonSearchCriteria sc, CriteriaBuilder criteria, Root<Person> root) {

        List<Predicate> predicates = new ArrayList<>();

        sc.getId().ifPresent(id -> predicates.add(criteria.equal(root.get(ID_PROPERTY), id)));
        
        sc.getName().ifPresent(name -> predicates.add(criteria.equal(root.get(NAME_PROPERTY), name)));
    
        // @Predicate input

        return predicates;
    }
}
