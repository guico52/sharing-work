package com.guico.sharingwork.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    private int type;

    private String id;

    private String username;

    private String data;


    public static Result success(String id, String username, String data){
        return new Result(1, id, username, data);
    }

    public static Result update(String id, String username, String data){
        return new Result(2, id, username, data);
    }

    public static Result move(String id, String username, String data){
        return new Result(3, id, username, data);
    }

    public static Result bulkUpdate(String id, String username, String data){
        return new Result(4, id, username, data);
    }

}
