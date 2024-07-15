package com.wsa.mapper;

import com.wsa.model.Authority;
import com.wsa.model.UserReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorityMapper {
    void addAuthority(Authority authority);

    void updateAuthority(Authority authority);

    void deleteAuthority(UserReq request);
}
