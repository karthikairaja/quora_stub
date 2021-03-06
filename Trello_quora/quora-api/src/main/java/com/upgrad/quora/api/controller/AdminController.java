package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.service.business.AuthenticationService;
import com.upgrad.quora.service.business.UserService;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AdminController {

  @Autowired
  private UserService userService;

  @Autowired
  private AuthenticationService authenticationService;

  // Delete a user.

  @RequestMapping(method = RequestMethod.DELETE, path = "/admin/user/{userId}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<UserDeleteResponse> userDelete(@PathVariable("userId") final String userId,
      @RequestHeader("authorization") final String authorization)
      throws UserNotFoundException, AuthorizationFailedException, AuthenticationFailedException {


    String accessToken = authenticationService.getBearerAccessToken(authorization);

    UserAuthEntity userAuthEntity = authenticationService
        .validateBearerAuthentication(accessToken, "to get user details");

    UserEntity userEntity = userService.deleteUser(userId, userAuthEntity);

    UserDeleteResponse userDeleteResponse = new UserDeleteResponse().id(userEntity.getUuid())
        .status("USER SUCCESSFULLY DELETED");
    return new ResponseEntity<UserDeleteResponse>(userDeleteResponse, HttpStatus.OK);
  }

}
