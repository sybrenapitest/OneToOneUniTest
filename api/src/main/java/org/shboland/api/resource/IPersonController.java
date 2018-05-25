package org.shboland.api.resource;

import javax.ws.rs.BeanParam;
import org.shboland.domain.entities.JsonSearchResult;
import org.shboland.domain.entities.JsonPersonSearchCriteria;
import java.net.URISyntaxException;
import org.shboland.domain.entities.JsonPerson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/persons")
public interface IPersonController {

    // @Input

    @RequestMapping(path = "/{personId}/detailss", method = RequestMethod.GET)
    ResponseEntity getDetailss(@PathVariable long personId);

    @RequestMapping(path = "/{personId}", method = RequestMethod.GET)
    ResponseEntity<JsonPerson> getPerson(@PathVariable long personId);
    
    @RequestMapping(path = "", method = RequestMethod.GET)
    ResponseEntity<JsonSearchResult> list(@BeanParam JsonPersonSearchCriteria searchCriteria);
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity postPerson(@RequestBody JsonPerson person) throws URISyntaxException;
    
    @RequestMapping(value = "/{personId}", method = RequestMethod.PUT)
    ResponseEntity putPerson(@PathVariable("personId") long personId, @RequestBody JsonPerson jsonPerson);
    
    @RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
    ResponseEntity deletePerson(@PathVariable("personId") long personId);
    
}