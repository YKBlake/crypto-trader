package com.ykb.app.cryptotrader.web.api.rest;

import com.ykb.app.cryptotrader.auth.service.UserOperationsService;
import com.ykb.app.cryptotrader.web.dto.BaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public final class UserApi implements CrudApi {

    private final UserOperationsService userService;

    @Autowired
    public UserApi(UserOperationsService userService) {
        this.userService=userService;
    }

    @Override
    @PostMapping
    public ResponseEntity<String> create(BaseDto dto) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<BaseDto> read(Map<String, String> params) {
        return null;
    }

    @Override
    @PutMapping
    public ResponseEntity<String> update(BaseDto dto) {
        return null;
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> delete(String id) {
        return null;
    }

}
