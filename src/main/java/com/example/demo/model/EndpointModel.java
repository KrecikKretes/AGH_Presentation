package com.example.demo.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EndpointModel {

    private Long id;

    private String template;

    private String emailOrigin;

    private String emailDestination;

    public EndpointEntity toEntity() {
        var entity = new EndpointEntity();
        entity.setId(id);
        entity.setTemplate(template);
        entity.setEmailOrigin(emailOrigin);
        entity.setEmailDestination(emailDestination);
        return entity;
    }

    public static EndpointModel toModel(EndpointEntity endpointEntity) {
        return new EndpointModel()
                .setId(endpointEntity.getId())
                .setTemplate(endpointEntity.getTemplate())
                .setEmailOrigin(endpointEntity.getEmailOrigin())
                .setEmailDestination(endpointEntity.getEmailDestination());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final EndpointModel other = (EndpointModel) obj;

        return this.id.equals(other.id) &&
                this.template.equals(other.template) &&
                this.emailOrigin.equals(other.emailOrigin) &&
                this.emailDestination.equals(other.emailDestination);
    }
}
