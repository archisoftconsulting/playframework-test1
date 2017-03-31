package controllers; 

import javax.persistence.EntityManager; 
import javax.persistence.EntityManagerFactory; 
import javax.persistence.Persistence; 
import model.User; 
import util.Util;
import play.Play; 
import play.mvc.Controller; 
import play.mvc.Result; 
import play.mvc.Http.RequestBody;
import play.libs.Json;

import java.util.Collection;
import java.util.List; 
import java.util.Map; 
import javax.persistence.Query;

import static play.libs.Json.toJson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserController extends Controller 
{ 
	EntityManagerFactory emf; 
    
    public Result persist() 
    { 
        EntityManager em = getEmf().createEntityManager(); 
        User user = new User(); 
        user.setUserId("0001"); 
        user.setFirstName("Roslan"); 
        user.setLastName("Lee"); 
        user.setCity("Cyberjaya");  
        em.persist(user); 
        em.close(); 
        return ok("User 0001 record persisted for persistence unit cassandra_pu"); 
    } 
    
    public Result find() 
    {  
        EntityManager em = getEmf().createEntityManager(); 
        User user = em.find(User.class, "0001"); 
        
        ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonData = mapper.convertValue(user, JsonNode.class);
		
		ObjectNode result = Json.newObject();
		result.put("body", (JsonNode) jsonData);

		em.close(); 
		return ok(result); 
    } 
    
    public Result all() 
    {  
        EntityManager em = getEmf().createEntityManager(); 
        
        
        List<User> users = em.createQuery("SELECT e FROM User e").getResultList();
        
        
        ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonData = mapper.convertValue(users, JsonNode.class);
		
		ObjectNode result = Json.newObject();
		result.put("body", (JsonNode) jsonData);

		em.close(); 
		return ok(result); 
    } 
    
    public Result add() 
    {  
    	/*String userId = request().body().asFormUrlEncoded().get("userId")[0];
    	String firstName = request().body().asFormUrlEncoded().get("firstName")[0];
    	String lastName = request().body().asFormUrlEncoded().get("lastName")[0];
    	String city = request().body().asFormUrlEncoded().get("city")[0];
    	
    	if (userId == null)
    	{
    		return badRequest("Expceting some userId data");
    	}
    	else 
    	{
    		 String result = "";
    		 result += "\n" + userId;
             return ok(result);
    	}*/
    	
    	
    	/*String userId = request().body().asFormUrlEncoded().get("userId")[0]; 
    	if (userId == null)
    	{
    		return badRequest("Expceting some userId data");
    	}
    	else 
    	{
    		 String result = "";
    		 result += "\n" + userId;
             return ok(result);
    	}*/
    	
    	
    	Map<String,String[]> data = request().body().asFormUrlEncoded();
    	String userId = request().body().asFormUrlEncoded().get("userId")[0]; 
    	String firstName = request().body().asFormUrlEncoded().get("firstName")[0]; 
    	String lastName = request().body().asFormUrlEncoded().get("lastName")[0]; 
    	String city = request().body().asFormUrlEncoded().get("city")[0]; 
    	
    	EntityManager em = getEmf().createEntityManager(); 
    	//em.getTransaction().begin();
    	User user = new User(); 
        user.setUserId(userId); 
        user.setFirstName(firstName); 
        user.setLastName(lastName); 
        user.setCity(city);  
        em.persist(user);
        //em.getTransaction().commit();
        em.close(); 
    	//return ok(userId);
        /*if(data == null){
            return badRequest("Expecting some userId data");
        } else {
            String result = "";
            for(Map.Entry value : data.entrySet()){
            	result += "\n" + value.getValue();
            }
            return ok(result);
        }*/
        
        String result = "User ID : " + userId + ", First Name : " + firstName + ", Last Name : " + lastName + ", City : " + city;
        
        return ok(result);
    } 
    
    public Result update() 
    { 
        EntityManager em = getEmf().createEntityManager(); 
        User user = em.find(User.class, "0001");
        user.setCity("New York"); 
        em.merge(user); 
        user = em.find(User.class, "0001"); 
        return ok("Record updated:" + printUser(user));
    } 
        
    public Result delete() 
    { 
        EntityManager em = getEmf().createEntityManager(); 
        User user = em.find(User.class, "0001"); 
        em.remove(user); 
        user = em.find(User.class, "0001"); 
        return ok("Record deleted:" + printUser(user)); 
    } 
    
    private EntityManagerFactory getEmf() 
    { 
        if (emf == null) 
        { 
            emf = Persistence.createEntityManagerFactory("cassandrapu"); 
        } 
        return emf; 
    } 
    
    private String printUser(User user) 
    { 
        if (user == null) 
            return "Record not found"; 
        return "n--------------------------------------------------" + "nuserId:" + user.getUserId() + "nfirstName:" + user.getFirstName() + "nlastName:" + user.getLastName() + "ncity:" + user.getCity(); 
    } 
}