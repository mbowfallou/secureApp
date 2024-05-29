package com.samanecorp.secureapp.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samanecorp.secureapp.dao.AccountUserDao;
import com.samanecorp.secureapp.dao.AccountUserDaoImpl;
import com.samanecorp.secureapp.dto.AccountUserDto;
import com.samanecorp.secureapp.entities.AccountUserEntity;
import com.samanecorp.secureapp.mapper.AccountUserMapper;

public class LoginService {
	
	private static Logger logger = LoggerFactory.getLogger(AccountUserService.class);
	private final AccountUserDao accountUserDao = new AccountUserDaoImpl();
	
	
	public Optional<AccountUserDto> login(String email, String password) {
		
		logger.info("\n\n\tTentattive de connexion (email): {}...\n", email);
		
		AccountUserEntity userEntity = accountUserDao.findByEmailAndPassword(email, password);
		
		if (userEntity != null) {
			logger.info("\n\n\tTentattive de connexion: {} trouvé.\n", email);
			return Optional.of(AccountUserMapper.toAccountUserDto(userEntity));
		} else {
            logger.warn("Failed login attempt for email: {}", email);
            return Optional.empty();
        }
	}
	
	
	public int register(AccountUserDto userDTO) {
		return accountUserDao.save(AccountUserMapper.toAccountUserEntity(userDTO));
	}
}
