package cn.ideartefact.data;


import cn.ideartefact.gson.JsonArray;
import lombok.Data;

@Data
public final class DataTable {
    private JsonArray Columns;
    private JsonArray Rows;
}
