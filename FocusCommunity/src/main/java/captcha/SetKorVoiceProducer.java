package captcha;

import java.util.HashMap;
import java.util.Map;

import nl.captcha.audio.Sample;
import nl.captcha.audio.producer.VoiceProducer;
import nl.captcha.util.FileUtil;

public class SetKorVoiceProducer  implements VoiceProducer  {
	
    private static final Map<Integer, String> DEFAULT_VOICES_MAP;

    static {
        DEFAULT_VOICES_MAP = new HashMap<Integer, String>();
        StringBuilder sb;
        
        for (int i = 0; i < 10; i++) {            
        	sb = new StringBuilder("/sounds/ko/numbers/");
            sb.append(i);            
            sb.append(".wav");            
            DEFAULT_VOICES_MAP.put(i, sb.toString());
        }
    }
    
    private final Map<Integer, String> _voices;

    public SetKorVoiceProducer() {
        this(DEFAULT_VOICES_MAP);
    }


	public SetKorVoiceProducer(Map<Integer, String> voices) {
		_voices = voices;
	}

	@Override
	public Sample getVocalization(char num) {
	   try {
            Integer.parseInt(num + "");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Expected <num> to be a number, got '" + num + "' instead.",e);
        }

        int idx = Integer.parseInt(num + "");
        String filename = _voices.get(idx); 
        return FileUtil.readSample(filename);
	}
}