package thelazycoder.blog_app.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import thelazycoder.blog_app.exception.InvalidInputException;
import thelazycoder.blog_app.exception.response.ExceptionResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class GenericFieldValidator {

//    Email matcher checker
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
//  Generic Object
    public <T> T validate(T object) throws InvalidInputException {
        if (object == null) {
            return null;
        }
        List<ExceptionResponse>exceptionResponseList=new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                String fieldName = field.getName();
//                For string
                if(value == null || value instanceof String && ((String)value).isEmpty()){
                    exceptionResponseList.add(new ExceptionResponse("Field cannot be empty", fieldName));
                } else if ("email".equalsIgnoreCase(fieldName)
                        && value instanceof String
                        && !EMAIL_PATTERN.matcher((String) value).matches()) {
                    exceptionResponseList.add(new ExceptionResponse("Field cannot be empty",fieldName));
                }
            }catch (IllegalAccessException e) {
                System.out.println("IllegalAccessException "+ field.getName());
                throw new RuntimeException("error during validation", e);
            }
        }

        if (!exceptionResponseList.isEmpty()){
            throw new InvalidInputException("Invalid Input", exceptionResponseList);
        }
        return object;
    }
}
