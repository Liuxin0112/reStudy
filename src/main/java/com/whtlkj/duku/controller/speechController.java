package com.whtlkj.duku.controller;

import com.whtlkj.duku.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class speechController {
    @Autowired
    private SpeechService speechService;

    @PostMapping("speechSynthesis")
    public Map<String,Object> speechSynthesis(@RequestBody Map<String,Object> requestMap){
        Map<String, Object> stringStringMap = null;
        stringStringMap = speechService.speechTTSLocal(requestMap);
        return stringStringMap;
    }


}
