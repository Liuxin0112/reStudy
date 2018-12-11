package com.whtlkj.duku.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface SpeechService {
    Map<String, Object> speechTTS(Map<String, Object> requestMap) throws UnsupportedEncodingException;
    Map<String, Object> speechTTSLocal(Map<String, Object> requestMap);
}
