package integration;
         
import org.shboland.persistence.db.repo.DetailsRepository;
import org.shboland.persistence.db.hibernate.bean.Details;
import org.shboland.domain.entities.JsonDetails;
import org.shboland.persistence.db.repo.PersonRepository;
import org.shboland.persistence.db.hibernate.bean.Person;
import org.shboland.domain.entities.JsonPerson;

public class IntegrationTestFactory {

    // @Input

   public static Details givenADetailsWithPerson(DetailsRepository detailsRepository, PersonRepository personRepository) {
        Person person = personRepository.save(Person.builder().build());

        return detailsRepository.save(Details.builder()
                .person(person)
                // @FieldInput
                .build());
    }

    public static Details givenADetails(DetailsRepository detailsRepository) {
        return givenADetails(Details.builder()
                 .age(3)
                // @FieldInputDetailsBean
                .build(),
                detailsRepository);
    }
    
    public static Details givenADetails(Details details, DetailsRepository detailsRepository) {
        return detailsRepository.save(details);
    }
    
    public static JsonDetails givenAJsonDetails() {
        return JsonDetails.builder()
                 .age(4)
                // @FieldInputJsonDetails
                .build();
    }
        

    public static Person givenAPerson(PersonRepository personRepository) {
        return givenAPerson(Person.builder()
                 .name("string")
                // @FieldInputPersonBean
                .build(),
                personRepository);
    }
    
    public static Person givenAPerson(Person person, PersonRepository personRepository) {
        return personRepository.save(person);
    }
    
    public static JsonPerson givenAJsonPerson() {
        return JsonPerson.builder()
                 .name("different string")
                // @FieldInputJsonPerson
                .build();
    }
        
}
