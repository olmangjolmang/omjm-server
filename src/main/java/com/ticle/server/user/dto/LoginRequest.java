package com.ticle.server.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record LoginRequest(String email,String password) {

}
