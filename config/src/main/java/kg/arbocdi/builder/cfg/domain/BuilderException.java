package kg.arbocdi.builder.cfg.domain;

import lombok.ToString;

@ToString(callSuper = true)
public class BuilderException extends Exception{
    public BuilderException(String message) {
        super(message);
    }
}
