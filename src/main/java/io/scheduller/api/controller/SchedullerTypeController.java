package io.scheduller.api.controller;

import io.scheduller.api.enumerator.SchedullerType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(SchedullerTypeController.SCHEDULLER_TYPE_ENDPOINT)
public class SchedullerTypeController {
    public static final String SCHEDULLER_TYPE_ENDPOINT = "/schedullertype";

    @GetMapping
    List<SchedullerType> getAllSchedullerType(){
        return Arrays.asList(SchedullerType.values());
    }

}
