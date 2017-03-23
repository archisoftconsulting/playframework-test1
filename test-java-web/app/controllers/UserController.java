package controllers; 

import javax.persistence.EntityManager; 
import javax.persistence.EntityManagerFactory; 
import javax.persistence.Persistence; 
import model.User; 
import util.Util;
import play.Play; 
import play.mvc.Controller; 
import play.mvc.Result; 
import play.libs.Json;

import java.util.Collection;
import java.util.List; 
import javax.persistence.Query;

import static play.libs.Json.toJson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserController extends Controller 
{ 
	//test
    EntityManagerFactory emf; 
    //EntityManager emgr;
    
   // public UserController(EntityManager emg) {
    //    this.emgr = emg; 
  //}
  
    public Result persist() { 
        
        //Map<String, String> propertyMap = new HashMap<String, String>();
        //propertyMap.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandrapu", propertyMap);
        //EntityManager em = emf.createEntityManager();
    
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
    
    public Result find() {  
        EntityManager em = getEmf().createEntityManager(); 
        User user = em.find(User.class, "0001"); 
        //em.close(); 
        
        //UserController service = new UserController(em);
        //Collection<User> emps = service.findAllUsers(); 
        
        
        //Query query = em.createQuery("SELECT a FROM users a");
        //Collection<User> emps =  query.getResultList(); 
        ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonData = mapper.convertValue(user, JsonNode.class);
		//JsonNode jsonData = mapper.createObjectNode();
		
        //List<User> user = findAll();
        //Set<User> user = em.find(User.class);
        //em.close(); 
        //Set<Student> result = StudentStore.getInstance().getAllStudents();
		
		
		ObjectNode result = Json.newObject();
		result.put("body", (JsonNode) jsonData);

		 
		
		
		
		em.close(); 
		return ok(result); 
		//return ok(Util.createResponse(user, true)); 
		
        //return ok("Found User in database with the following details:" + printUser(user)); 
    } 
    
    //public List<User> findAll() {
        
    //    try {
    //        String queryString = "select * from users model"; 
    //        Query query = getEntityManager().createQuery(queryString);
   //         return query.getResultList();
   //     } catch (RuntimeException re) {
   //         throw re;
   //     }
   // }
    
    //public Collection<User> findAllUsers() {
        //Query query = em.createQuery("SELECT e FROM users e");
        //return (Collection<User>) query.getResultList();
     // }
    
    public Result update() { 
        EntityManager em = getEmf().createEntityManager(); 
        User user = em.find(User.class, "0001");
        user.setCity("New York"); 
        em.merge(user); 
        user = em.find(User.class, "0001"); 
        return ok("Record updated:" + printUser(user));
    } 
        
    public Result delete() { 
        EntityManager em = getEmf().createEntityManager(); 
        User user = em.find(User.class, "0001"); 
        em.remove(user); 
        user = em.find(User.class, "0001"); 
        return ok("Record deleted:" + printUser(user)); 
    } 
    
    private EntityManagerFactory getEmf() { 
        if (emf == null) 
        { 
            emf = Persistence.createEntityManagerFactory("cassandrapu"); 
        } 
        return emf; 
    } 
    
    private String printUser(User user) { 
        if (user == null) 
            return "Record not found"; 
        return "n--------------------------------------------------" + "nuserId:" + user.getUserId() + "nfirstName:" + user.getFirstName() + "nlastName:" + user.getLastName() + "ncity:" + user.getCity(); 
    } 
}