package com.cb.demo.userProfile.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController("/events")
public class UserEventController {

//    @Autowired
//    private UserEventService userEventService;
//
//    @PostMapping(value="/add", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<UserEventEntity> save(@Valid @RequestBody List<UserEventEntity> events) {
//        return userEventService.save(events);
//    }
//
//    @GetMapping(value="/findLatest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<UserEventEntity> findLatestUserEvents(@RequestParam("userId") String userId,
//                                                 @RequestParam("eventType") String eventType,
//                                                 @RequestParam("limit") int limit,
//                                                 @RequestParam("offset") int offset) {
//        return userEventService.findLatestUserEvents(userId, eventType, limit, offset);
//    }
}
