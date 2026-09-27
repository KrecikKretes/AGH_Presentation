package com.zawisza.email.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EmailModel {

    private Long id;

    private String template;

    private String emailOrigin;

    private String emailDestination;

    public EmailEntity toEntity() {
        var entity = new EmailEntity();
        entity.setId(id);
        entity.setTemplate(template);
        entity.setEmailOrigin(emailOrigin);
        entity.setEmailDestination(emailDestination);
        return entity;
    }

    public static EmailModel toModel(EmailEntity emailEntity) {
        return new EmailModel()
                .setId(emailEntity.getId())
                .setTemplate(emailEntity.getTemplate())
                .setEmailOrigin(emailEntity.getEmailOrigin())
                .setEmailDestination(emailEntity.getEmailDestination());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final EmailModel other = (EmailModel) obj;

        return this.id.equals(other.id) &&
                this.template.equals(other.template) &&
                this.emailOrigin.equals(other.emailOrigin) &&
                this.emailDestination.equals(other.emailDestination);
    }
}
