package cn.ideartefact.restfulresponsemessage;

import cn.ideartefact.gson.JsonElement;
import lombok.Data;

@Data
public class ResponseMessage {
    private  String code;
    private  String msg;

    private  JsonElement data;
}
