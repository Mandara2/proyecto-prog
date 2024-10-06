package com.rml.security.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Data
@Document
public class Permission {
    @Id
    private String _id;
    private String url;
    private String method;

    public Permission(String url, String method) {
        this.url = url;
        this.method = method;
    }

}
