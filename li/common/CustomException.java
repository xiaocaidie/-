package li.common;

import javax.xml.transform.sax.SAXResult;

public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
