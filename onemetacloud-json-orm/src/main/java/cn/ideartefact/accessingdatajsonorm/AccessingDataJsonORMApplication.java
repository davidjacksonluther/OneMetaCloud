package cn.ideartefact.accessingdatajsonorm;

import cn.ideartefact.gson.Gson;
import cn.ideartefact.gson.JsonObject;
import cn.ideartefact.gson.JsonPrimitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class AccessingDataJsonORMApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataJsonORMApplication.class, args);
	}

	@Autowired
	private MySQLConnector QueryTool;

	@RequestMapping(value = "/GetSQLQuery",method = RequestMethod.GET,produces ="application/json")
	public JsonObject GetSQL(@RequestParam String sql){
		JsonPrimitive result = QueryTool.QueryJsonPrimitive(sql);
		JsonObject ret = new JsonObject();
		ret.add("page",new JsonPrimitive("HomePage"));
		ret.add("result",result);

		return  ret;
		// return String.format( """
		// 		{
		// 		 	'page':'HomePage',
		// 		 	'result':'%s'
		//
		// 		 }
		// 		""",result);
	}

	@RequestMapping(value = "/PostSQLQuery",method = RequestMethod.POST,produces ="application/json")
	public String PostSQL(@RequestBody String Body) throws JsonObject.JsonObjectValueTypeError {
		JsonObject  bodyJson = new Gson().fromJson(Body,JsonObject.class);

		String sql = bodyJson.getString("members[0].sql");
		String result = QueryTool.Query(sql);


		return String.format( """
				{
				 	'page':'HomePage',
				 	'result':'%s'
				 
				 }
				""",result);
	}

}
