package com.prography.pingpong.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userReadApiClient", url = "https://fakerapi.it/api/v1")
public interface UserReadApiClient {

    @GetMapping("/users")
    UserReadApiRs fetchUsers(@RequestParam("_seed") int seed,
                                @RequestParam("_quantity") int quantity,
                                @RequestParam("_locale") String locale);
}
