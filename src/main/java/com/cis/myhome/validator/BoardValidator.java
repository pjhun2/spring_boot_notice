package com.cis.myhome.validator;

import com.cis.myhome.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class<?> Clazz) {
        return Board.class.equals(Clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if(StringUtils.isEmpty(b.getContent())){
            errors.rejectValue("content", "key", "내용을 입력하세요.");
        }
    }
}
