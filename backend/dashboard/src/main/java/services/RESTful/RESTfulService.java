package services.RESTful;
import static spark.Spark.*;

import com.google.gson.Gson;

import services.AufenthaltService;
import services.responses.StandardResponse;
import services.responses.StatusResponse;

/*
public class RESTfulService {
	public static void main(String[] args) {
	 get("/aufenthalte", (request, response) -> {
		 response.type("application/json");
		    return new Gson().toJson(
		      new StandardResponse(StatusResponse.SUCCESS,new Gson()
		        .toJsonTree(AufenthaltService.getAllAufenthalte())));
     });
	}
}
*/