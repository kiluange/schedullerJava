package io.scheduller.api.controller;

import io.scheduller.api.dto.SchedullerDTO;
import io.scheduller.api.service.SchedullerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SchedullerController.SCHEDULLER_ENDPOINT)
@Api(value = "Greeting", description = "Greeting people")
public class SchedullerController {
    public static final String SCHEDULLER_ENDPOINT = "/scheduller";

    @Autowired
    SchedullerService service;

    @PostMapping
    SchedullerDTO createScheduller(@RequestBody SchedullerDTO schedullerDTO){
        return service.runScheduler(schedullerDTO);
    }

}
