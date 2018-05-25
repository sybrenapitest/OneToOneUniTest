package org.shboland.core.service;

import org.shboland.persistence.db.repo.PersonRepository;
import org.shboland.persistence.db.hibernate.bean.Person;
import java.util.List;
import org.shboland.persistence.criteria.DetailsSearchCriteria;
import java.util.Optional;
import org.shboland.persistence.db.hibernate.bean.Details;
import org.shboland.persistence.db.repo.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DetailsService {

    private final DetailsRepository detailsRepository;
    private final PersonRepository personRepository;
    // @FieldInput

    @Autowired
    public DetailsService(PersonRepository personRepository, DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
        this.personRepository = personRepository;
        // @ConstructorInput
    }
    
    // @Input

    public List<Details> fetchDetailssForPerson(long personId) {
        DetailsSearchCriteria detailsSearchCriteria =  DetailsSearchCriteria.builder()
                .personId(Optional.of(personId))
                .build();

        return detailsRepository.findBySearchCriteria(detailsSearchCriteria);
    }

    public boolean removePerson(long detailsId, long personId) {
        Optional<Details> detailsOptional = detailsRepository.findById(detailsId);
        if (detailsOptional.isPresent()) {
            Details details = detailsOptional.get();
         
            if (details.getPerson() != null) {

                Optional<Person> personOptional = personRepository.findById(personId);
                if (personOptional.isPresent() && personOptional.get().getId().equals(details.getPerson().getId())) {
    
                    Details newDetails = details.toBuilder()
                            .person(null)
                            .build();
                    detailsRepository.save(newDetails);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean updateDetailsWithPerson(long detailsId, long personId) {
        Optional<Details> detailsOptional = detailsRepository.findById(detailsId);
        if (detailsOptional.isPresent()) {

            Optional<Person> personOptional = personRepository.findById(personId);
            if (personOptional.isPresent()) {

                Details newDetails = detailsOptional.get().toBuilder()
                        .person(personOptional.get())
                        .build();
                detailsRepository.save(newDetails);
                return true;
            }
        }

        return false;
    }
  
    public int findNumberOfDetails(DetailsSearchCriteria sc) {
        return detailsRepository.findNumberOfDetailsBySearchCriteria(sc);
    }
    

    public List<Details> findBySearchCriteria(DetailsSearchCriteria sc) {
        return detailsRepository.findBySearchCriteria(sc);
    }

    public Details save(Details details) {
        return detailsRepository.save(details);
    }

    public Optional<Details> fetchDetails(long detailsId) {
        return detailsRepository.findById(detailsId);
    }

    public boolean deleteDetails(long detailsId) {
        Optional<Details> details = detailsRepository.findById(detailsId);

        if (details.isPresent()) {
            detailsRepository.delete(details.get());
            return true;
        } else {
            return false;
        }
    }
    
}