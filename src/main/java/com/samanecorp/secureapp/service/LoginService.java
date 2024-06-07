package com.samanecorp.secureapp.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samanecorp.secureapp.dao.AccountUserDao;
import com.samanecorp.secureapp.dao.AccountUserDaoImpl;
import com.samanecorp.secureapp.dto.AccountUserDto;
import com.samanecorp.secureapp.mapper.AccountUserMapper;

public class LoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	private final AccountUserDao accountUserDao = new AccountUserDaoImpl();
	
	
	public Optional<AccountUserDto> login(String email, String password) {
		
		logger.info("\n\n\tTentative de connexion (email): {}...\n", email);
		
		AccountUserDto userDto = AccountUserMapper.toAccountUserDto(accountUserDao.findByEmailAndPassword(email, password));
		
		if (userDto != null) {
			logger.info("\n\n\tTentative de connexion: {} trouvé.\n", email);
			return Optional.of(userDto);
		} else {
            logger.warn("Failed login attempt for email: {}", email);
            return Optional.empty();
        }
	}
	
	
	public int register(AccountUserDto userDTO) {
		return accountUserDao.save(AccountUserMapper.toAccountUserEntity(userDTO));
	}
	
	public Optional<AccountUserDto> getByEmail(String email){
		
		logger.info("\n\n\tCheck if email {} is already registered.\n", email);

		AccountUserDto userDto = AccountUserMapper.toAccountUserDto(accountUserDao.findByEmail(email));
		
		if(userDto != null)
			return Optional.of(userDto);
		else
			return Optional.empty();
	}


	public boolean isEmailExist(String email){

		logger.info("\n\n\tCheck if email {} already existed.\n", email);

		AccountUserDto userDto = AccountUserMapper.toAccountUserDto(accountUserDao.findByEmail(email));

        return userDto != null;
	}

}
