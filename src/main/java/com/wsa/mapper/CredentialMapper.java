package com.wsa.mapper;

import com.wsa.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM credentials WHERE volunteerId = #{volunteerId}")
    List<Credential> findCredentialsByVolunteerId(@Param("volunteerId") Long volunteerId);

    @Delete("DELETE FROM credentials WHERE id = #{id}")
    void deleteCredentialById(@Param("id") Long id);

    @Update("UPDATE credentials SET credentialName = #{credentialName}, credentialUrl = #{credentialUrl} WHERE id = #{id}")
    void updateCredential(Credential credential);
}
