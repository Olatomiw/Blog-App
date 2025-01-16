package thelazycoder.blog_app.utils;

import thelazycoder.blog_app.dto.response.ApiResponse;

public class ResponseUtil {

    public static <T> ApiResponse<T> success(T data, String message ){
        return new ApiResponse<>(
                "Success",
                message,
                data
        );
    }
    public static <T> ApiResponse<T> error(T data, String message ){
        return new ApiResponse<>(
                "Error",
                message,
                data

        );
    }
}