package com.cb.demo.userProfile.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class UserEntity {

    public static final String TYPE = "user";

    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private boolean enabled;
    private Integer tenantId;
    private String countryCode;
    private String username;
    private String password;
    private String socialSecurityNumber;
    @Getter
    private String type = TYPE;
    private List<TelephoneEntity> telephones;
    private List<PreferenceEntity> preferences;
    private List<AddressEntity> addresses;
    private List<String> securityRoles;
}
