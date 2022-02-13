package io.vpplab.flow.module.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping("/event/getEventList")
    public Map<String, Object> getEventList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return eventService.getEventList(paramMap,request);
    }


}
