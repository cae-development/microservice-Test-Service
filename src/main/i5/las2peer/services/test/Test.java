package i5.las2peer.services.test;


import java.net.HttpURLConnection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import com.fasterxml.jackson.core.JsonProcessingException;

import i5.las2peer.api.Service;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.restMapper.MediaType;
import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.restMapper.annotations.ContentParam;
import i5.las2peer.restMapper.annotations.Version;
import i5.las2peer.services.test.database.DatabaseManager;
import java.sql.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.Reader;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray; 

/**
 * 
 * Test-Service
 * 
 * This microservice was generated by the CAE (Community Application Editor). If you edit it, please
 * make sure to keep the general structure of the file and only add the body of the methods provided
 * in this main file. Private methods are also allowed, but any "deeper" functionality should be
 * outsourced to (imported) classes.
 * 
 */
@Path("")
@Version("0.1") // this annotation is used by the XML mapper
@Api
@SwaggerDefinition(
    info = @Info(title = "Test-Service", version = "0.1",
        description = "A LAS2peer microservice generated by the CAE.",
        termsOfService = "none",
        contact = @Contact(name = "test", email = "CAEAddress@gmail.com") ,
        license = @License(name = "BSD",
            url = "https://github.com/cae-development/microservice-Test-Service/blob/master/LICENSE.txt") ) )
public class Test extends Service {


  /*
   * Database configuration
   */
  private String jdbcDriverClassName;
  private String jdbcLogin;
  private String jdbcPass;
  private String jdbcUrl;
  private String jdbcSchema;
  private DatabaseManager dbm;



  public Test() {
    // read and set properties values
    setFieldValues();
    // instantiate a database manager to handle database connection pooling and credentials
    dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl, jdbcSchema);
  }

  // //////////////////////////////////////////////////////////////////////////////////////
  // Service methods.
  // //////////////////////////////////////////////////////////////////////////////////////


  /**
   * 
   * test
   * 
   *
   * 
   * @return HttpResponse  
   * 
   */
  @GET
  @Path("/test")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  @ApiResponses(value = {
       @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "testResult")
  })
  @ApiOperation(value = "test", notes = " ")
  public HttpResponse test() {

    // testResult
    boolean testResult_condition = true;
    if(testResult_condition) {
      JSONObject resultJson = new JSONObject(); 
      JSONArray array = new JSONArray();
      Connection conn = null;
      try {
        conn = dbm.getConnection();
        PreparedStatement statement = conn.prepareStatement("Insert into test (number) Values (1);");
        statement.executeUpdate();
        statement = conn.prepareStatement("Select * from test");
        ResultSet result = statement.executeQuery();
        while (result.next()) {
          array.add(result.getInt("number"));
        }
        resultJson.put("numbers", array);
        conn.close();

        HttpResponse testResult = new HttpResponse(resultJson.toJSONString(), HttpURLConnection.HTTP_OK);
        return testResult;
      } catch (Exception e) {
        e.printStackTrace();
        HttpResponse testResult = new HttpResponse("Internal Error", HttpURLConnection.HTTP_INTERNAL_ERROR);
        return testResult;
      }
      HttpResponse testResult = new HttpResponse(resultJson.toJSONString(), HttpURLConnection.HTTP_OK);
      return testResult;
    }
    return null;
  }




  // //////////////////////////////////////////////////////////////////////////////////////
  // Methods required by the LAS2peer framework.
  // //////////////////////////////////////////////////////////////////////////////////////

  
  /**
   * 
   * This method is needed for every RESTful application in LAS2peer. Please don't change.
   * 
   * @return the mapping
   * 
   */
  public String getRESTMapping() {
    String result = "";
    try {
      result = RESTMapper.getMethodsAsXML(this.getClass());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }


}
