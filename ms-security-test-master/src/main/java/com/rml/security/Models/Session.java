package com.rml.security.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Setter
@Getter
@Data
@Document
public class Session {
    @Id
    private String _id;
    private String token;
    private Date expirationDate;
    private String validationCode;
    @DBRef
    private User user; // This is the user that is logged in


    public Session() {
    }

}
