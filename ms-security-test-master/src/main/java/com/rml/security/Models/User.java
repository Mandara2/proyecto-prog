package com.rml.security.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Data //para que guarde esa clase en persistencia, se almacena en una base de datos
@Document //de mongo, como quiere lo que se llame la tabla en la base de datos, toma el nombre por defecto del usuario
public class User {
    @Id //a mongo, para decirle que el atributo Id se cree automaticamente
    private String _id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
