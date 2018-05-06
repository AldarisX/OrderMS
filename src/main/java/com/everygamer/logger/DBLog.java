package com.everygamer.logger;

import lombok.Data;

@Data
public class DBLog {
    private int id;
    private String user;
    private String msg;
    private DBLogLevel level;
    private boolean opSucc = true;
    private SystemLogOpType opType;
}