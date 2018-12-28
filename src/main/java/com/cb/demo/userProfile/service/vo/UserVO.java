package com.cb.demo.userProfile.service.vo;


import com.cb.demo.userProfile.model.AddressEntity;
import com.cb.demo.userProfile.model.PreferenceEntity;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class UserVO implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private Integer tenantId;
    private String countryCode;
    private String username;

    private List<PreferenceEntity> preferences;
    private List<AddressEntity> addresses;
    private List<String> securityRoles;
}
