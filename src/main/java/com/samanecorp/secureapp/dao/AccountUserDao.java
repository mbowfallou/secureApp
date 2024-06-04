package com.samanecorp.secureapp.dao;

import java.util.List;

import com.samanecorp.secureapp.entities.AccountUserEntity;

public interface AccountUserDao {
    int save(AccountUserEntity user);
    void update(AccountUserEntity user);
    void delete(AccountUserEntity user);
//    AccountUserEntity findById(long id);
    AccountUserEntity findByEmail(String email);
    AccountUserEntity findByEmailAndPassword(String email, String password);
    List<AccountUserEntity> findAll();
}
