package cn.boyce.entity;

import lombok.*;

import java.util.Collection;

/**
 * @Author: Yuan Baiyu
 * @Date: 2019/11/22
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> {

    public static final int CODE_SUCCESS = 200;

    public static final int CODE_CLIENT_FAIL = 400;

    public static final int CODE_SERVER_FAIL = 500;

    public static final String MESSAGE_SUCCESS = "请求成功！";

    private static final String MESSAGE_FAIL = "请求失败！";
    /**
     * 状态码
     */
    private int code;
    /**
     * 描述信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static final Response SUCCESS = Response.builder()
            .code(CODE_SUCCESS)
            .message(MESSAGE_SUCCESS)
            .build();

    public static final Response CLIENT_FAIL = Response.builder()
            .code(CODE_CLIENT_FAIL)
            .message(MESSAGE_FAIL)
            .build();

    public static final Response SERVER_FAIL = Response.builder()
            .code(CODE_SERVER_FAIL)
            .message(MESSAGE_FAIL)
            .build();

    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .code(CODE_SUCCESS)
                .message(MESSAGE_SUCCESS)
                .data(data)
                .build();
    }

    public static Response successWithoutData(String message) {
        return Response.builder()
                .code(CODE_SUCCESS)
                .message(message)
                .build();
    }

}
