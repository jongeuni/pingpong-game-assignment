package com.prography.pingpong.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fakerApiClient", url = "https://fakerapi.it/api/v1")
public interface FakerApiClient {

    @GetMapping("/users")
    FakerApiRs fetchUsers(@RequestParam("_seed") int seed,
                                @RequestParam("_quantity") int quantity,
                                @RequestParam("_locale") String locale);
}
