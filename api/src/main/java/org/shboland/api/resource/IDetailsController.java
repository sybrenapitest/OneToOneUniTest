package org.shboland.api.resource;

import javax.ws.rs.BeanParam;
import org.shboland.domain.entities.JsonSearchResult;
import org.shboland.domain.entities.JsonDetailsSearchCriteria;
import java.net.URISyntaxException;
import org.shboland.domain.entities.JsonDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/detailss")
public interface IDetailsController {

    // @Input

    @RequestMapping(path = "/{detailsId}/persons", method = RequestMethod.GET)
    ResponseEntity getPerson(@PathVariable long detailsId);

    @RequestMapping(value = "/{detailsId}/persons/{personId}", method = RequestMethod.DELETE)
    ResponseEntity deletePersonWithDetails(@PathVariable("detailsId") long detailsId, @PathVariable("personId") long personId);

    @RequestMapping(value = "/{detailsId}/persons/{personId}", method = RequestMethod.PUT)
    ResponseEntity putPersonWithDetails(@PathVariable("detailsId") long detailsId, @PathVariable("personId") long personId);

    @RequestMapping(path = "/{detailsId}", method = RequestMethod.GET)
    ResponseEntity<JsonDetails> getDetails(@PathVariable long detailsId);
    
    @RequestMapping(path = "", method = RequestMethod.GET)
    ResponseEntity<JsonSearchResult> list(@BeanParam JsonDetailsSearchCriteria searchCriteria);
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity postDetails(@RequestBody JsonDetails details) throws URISyntaxException;
    
    @RequestMapping(value = "/{detailsId}", method = RequestMethod.PUT)
    ResponseEntity putDetails(@PathVariable("detailsId") long detailsId, @RequestBody JsonDetails jsonDetails);
    
    @RequestMapping(value = "/{detailsId}", method = RequestMethod.DELETE)
    ResponseEntity deleteDetails(@PathVariable("detailsId") long detailsId);
    
}