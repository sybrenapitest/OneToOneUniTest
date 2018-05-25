package org.shboland.api.convert;

import org.shboland.persistence.db.hibernate.bean.Details;
import org.shboland.domain.entities.JsonDetails;
import org.shboland.api.resource.DetailsController;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
public class DetailsConverter {
    
    public JsonDetails toJson(Details details) {
        JsonDetails jsonDetails = JsonDetails.builder()
                .age(details.getAge())
                // @InputJsonField
                .build();
        
        jsonDetails.add(linkTo(DetailsController.class).slash(details.getId()).withSelfRel());
        jsonDetails.add(linkTo(DetailsController.class).slash(details.getId()).slash("/persons").withRel("person"));
            // @InputLink

        return jsonDetails;
    }
    
    public Details fromJson(JsonDetails jsonDetails) {
        return detailsBuilder(jsonDetails).build();
    }

    public Details fromJson(JsonDetails jsonDetails, long detailsId) {
        return detailsBuilder(jsonDetails)
                .id(detailsId)
                .build();
    }

    private Details.DetailsBuilder detailsBuilder(JsonDetails jsonDetails) {

        return Details.builder()
                .age(jsonDetails.getAge())
                // @InputBeanField
        ;
    }
}