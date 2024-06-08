package com.samanecorp.secureapp.service;

import com.samanecorp.secureapp.dao.AccountUserDao;
import com.samanecorp.secureapp.dao.AccountUserDaoImpl;
import com.samanecorp.secureapp.dto.AccountUserDto;
import com.samanecorp.secureapp.mapper.AccountUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountUserService {
	private static final Logger logger = LoggerFactory.getLogger(AccountUserService.class);
	private final AccountUserDao accountUserDao;

	public AccountUserService() {
		accountUserDao = new AccountUserDaoImpl();
	}

	public Optional<List<AccountUserDto>> getAllUsers() {
		List<AccountUserDto> usersDTO = accountUserDao.findAll()
				.stream()
				.map(AccountUserMapper::toAccountUserDto)
				.collect(Collectors.toList());

		if (usersDTO.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(usersDTO);
		}
	}

	public Optional<AccountUserDto> getUserById(long id) {
		AccountUserDto userDTO = AccountUserMapper.toAccountUserDto(accountUserDao.findById(id));

		if (userDTO != null) {
			return Optional.of(userDTO);
		} else {
			return Optional.empty();
		}
	}

	public int deleteUser(long id){
		AccountUserDto userDto = getUserById(id).get();
		int result = 0;
		if (userDto != null){
			result = accountUserDao.delete(AccountUserMapper.toAccountUserEntity(userDto));
        }
		return result;
	}
}
