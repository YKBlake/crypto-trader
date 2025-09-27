package com.ykb.app.cryptotrader.web.api.rest;

import com.ykb.app.cryptotrader.auth.dto.BaseDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

sealed interface CrudApi permits UserApi {

    ResponseEntity<String> create(BaseDto dto);
    ResponseEntity<BaseDto> read(Map<String,String> params);
    ResponseEntity<String> update(BaseDto dto);
    ResponseEntity<String> delete(String id);

}
