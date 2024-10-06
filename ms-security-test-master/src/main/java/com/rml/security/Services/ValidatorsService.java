package com.rml.security.Services;


import com.jcg.security.Models.*;
import com.rml.security.Models.*;
import com.rml.security.Repositories.PermissionRepository;
import com.rml.security.Repositories.RolePermissionRepository;
import com.rml.security.Repositories.UserRepository;
import com.rml.security.Repositories.UserRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidatorsService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PermissionRepository thePermissionRepository;
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private RolePermissionRepository theRolePermissionRepository;
    @Autowired
    private UserRoleRepository theUserRoleRepository;

    private static final String BEARER_PREFIX = "Bearer ";
    public boolean validationRolePermission(HttpServletRequest request,
                                            String url,
                                            String method){
        boolean success = false;

        User theUser = this.getUser(request);

        if(theUser != null){
            url = url.replaceAll("[0-9a-fA-F]{24}|\\d+", "?"); // Reemplaza el Mongo id o los números en la URL por "?"
            Permission thePermission = this.thePermissionRepository.getPermission(url, method); // Obtiene el permiso de la URL y el método

            List<UserRole> roles = this.theUserRoleRepository.getRolesByUserId(theUser.get_id());
            int i = 0;
            while(i < roles.size() && !success){
                UserRole actual = roles.get(i);
                Role theRole = actual.getRole();

                if(theRole!=null && thePermission!=null){
                    RolePermission theRolePermission = this.theRolePermissionRepository.getRolePermission(theRole.get_id(),thePermission.get_id());
                    if (theRolePermission!=null){
                        success=true;
                    }
                }
                i+=1;
            }

        }
        return success;
    }
    public User getUser(final HttpServletRequest request) {
        User theUser = null;

        String authorizationHeader = request.getHeader("Authorization"); // Obtiene el encabezado de autorización de la solicitud

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.substring(BEARER_PREFIX.length()); // Extrae el token de autorización de la solicitud
            User theUserFromToken = jwtService.getUserFromToken(token);// Obtiene el usuario del token

            if(theUserFromToken != null) {
                theUser = this.theUserRepository.findById(theUserFromToken.get_id()).orElse(null); // Obtiene el usuario de la base de datos
            }
        }
        return theUser;
    }
}
