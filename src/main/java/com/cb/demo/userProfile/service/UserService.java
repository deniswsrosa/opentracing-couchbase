package com.cb.demo.userProfile.service;

import com.cb.demo.userProfile.model.UserEntity;
import com.cb.demo.userProfile.service.vo.SimpleUserVO;
import com.cb.demo.userProfile.service.vo.UserVO;

import java.util.List;

public interface UserService {

    UserEntity save(UserEntity user);
    UserVO getUser(String id);

    List<SimpleUserVO> listActiveUsers( boolean enabled, String countryCode,  Integer limit, Integer offset );

    List<SimpleUserVO> ftsListActiveUsers(String firstName,  boolean enabled, String countryCode,  Integer limit, Integer skip );
}
