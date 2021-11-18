package captcha;

import nl.captcha.text.producer.TextProducer;



public class SetTextProducer implements TextProducer {

    private final String srcStr;

    public SetTextProducer(String answer) {   
        srcStr = answer; 
   }

   

    //@Override
   public String getText() {        
        return srcStr;
   }
}