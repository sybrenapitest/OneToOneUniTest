package org.shboland.api.convert;

import org.shboland.persistence.db.hibernate.bean.Person;
import org.shboland.domain.entities.JsonPerson;
import org.shboland.api.resource.PersonController;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
public class PersonConverter {
    
    public JsonPerson toJson(Person person) {
        JsonPerson jsonPerson = JsonPerson.builder()
                .name(person.getName())
                // @InputJsonField
                .build();
        
        jsonPerson.add(linkTo(PersonController.class).slash(person.getId()).withSelfRel());
        // @InputLink
        
        jsonPerson.add(linkTo(PersonController.class).slash(person.getId()).slash("/detailss").withRel("detailss"));

        return jsonPerson;
    }
    
    public Person fromJson(JsonPerson jsonPerson) {
        return personBuilder(jsonPerson).build();
    }

    public Person fromJson(JsonPerson jsonPerson, long personId) {
        return personBuilder(jsonPerson)
                .id(personId)
                .build();
    }

    private Person.PersonBuilder personBuilder(JsonPerson jsonPerson) {

        return Person.builder()
                .name(jsonPerson.getName())
                // @InputBeanField
        ;
    }
}