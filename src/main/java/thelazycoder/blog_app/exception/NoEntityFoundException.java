package thelazycoder.blog_app.exception;

public class NoEntityFoundException extends RuntimeException{

    public NoEntityFoundException(String message){
        super(message);
    }
}
