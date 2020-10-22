package org.manuel.mysportfolio.transformers.users;

import java.util.function.Function;
import org.manuel.mysportfolio.model.dtos.user.AppUserDto;
import org.manuel.mysportfolio.model.entities.user.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserToAppUserDtoTransformer implements Function<AppUser, AppUserDto> {

  @Override
  public AppUserDto apply(final AppUser appUser) {
    return AppUserDto.builder()
        .id(appUser.getId().toString())
        .version(appUser.getVersion())
        //.email(appUser.getEmail())
        .externalId(appUser.getExternalId())
        .fullName(appUser.getFullName())
        .build();
  }

}