package com.samanecorp.secureapp.mapper;

import com.samanecorp.secureapp.dto.AccountUserDto;
import com.samanecorp.secureapp.entities.AccountUserEntity;

public class AccountUserMapper {

	// Convert to Entity
	public static AccountUserEntity toAccountUserEntity(AccountUserDto accountUserDto) {
		if (accountUserDto == null) {
            return null;
        }
		
		AccountUserEntity userEntity = new AccountUserEntity();
		
		userEntity.setId(accountUserDto.getId());
		userEntity.setEmail(accountUserDto.getEmail());
		userEntity.setPassword(accountUserDto.getPassword());
		userEntity.setState(accountUserDto.isState());
		
		return userEntity;
	}

	// Convert to DTO
	public static AccountUserDto toAccountUserDto(AccountUserEntity userEntity) {
		if (userEntity == null) {
            return null;
        }
		
		AccountUserDto accountUserDto = new AccountUserDto();

		accountUserDto.setId(userEntity.getId());
		accountUserDto.setEmail(userEntity.getEmail());
		accountUserDto.setPassword(userEntity.getPassword());
		accountUserDto.setState(userEntity.isState());
		
		return accountUserDto;
	}
}
