package rest;

import dto.MemeDTO;
import entities.Comment;
import entities.Meme;
import entities.Role;
import entities.User;
import utils.EMF_Creator;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;

import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MemeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static User user, admin, both;
    private static Meme meme1, meme2;
    private static Comment comment1, comment2, comment3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        setupTestData(em);
    }
    
    private static String securityToken;

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    @Test
    public void testServerIsUp() {
        given().when().get("memes/funny").then().statusCode(200);
    }

    @Test
    public void testGetFunny() {
        given()
                .contentType("application/json")
                .get("memes/funny")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(5));
    }

    @Test
    public void testGetCat() {
        given()
                .contentType("application/json")
                .get("memes/cat")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(5));
    }

    @Test
    public void testGetYesOrNo() {
        given()
                .contentType("application/json")
                .get("memes/yesorno")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(5));
    }

    @Test
    public void testGetDog() {
        given()
                .contentType("application/json")
                .get("memes/dog")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(5));
    }

    @Test
    public void testUpvoteMeme() {
        login("user", "test123");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(new MemeDTO(meme2))
                .post("/memes/upvote/{username}", user.getUsername())
                .then().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("currentUpvotes", equalTo(2));
    }
    
    @Test
    public void testDownvoteMeme() {
        login("admin", "test123");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(new MemeDTO(meme2))
                .post("/memes/downvote/{username}", admin.getUsername())
                .then().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("currentDownvotes", equalTo(1));
    }
    
    @Test
    public void testUndoUpvote() {
        login("user", "test123");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(new MemeDTO(meme1))
                .post("/memes/upvote/{username}", user.getUsername())
                .then().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("currentUpvotes", equalTo(0));
    }
    
    @Test
    public void testUndoDownvote() {
        login("admin", "test123");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(new MemeDTO(meme1))
                .post("/memes/downvote/{username}", admin.getUsername())
                .then().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("currentDownvotes", equalTo(0));
    }
    
    @Test
    public void testGetColdList() {
        given()
                .contentType("application/json")
                .get("memes/cold")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(1));
    }

    @Test
    public void testGetHotList() {
        given()
                .contentType("application/json")
                .get("memes/hot")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(2));
    }

    public void setupTestData(EntityManager em) {
        user = new User("user", "test123");
        admin = new User("admin", "test123");
        both = new User("user_admin", "test123");
        meme1 = new Meme("fatcat.jpg", "Random cat");
        meme2 = new Meme("yomama.jpg", "Offensive joke");
        comment1 = new Comment("Jeg synes den er sjov", user);
        comment2 = new Comment("Jeg synes den er fed", user);
        comment3 = new Comment("Jeg synes den er nederen", admin);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Roles.deleteAllRows").executeUpdate();
            em.createNamedQuery("Comment.deleteAllRows").executeUpdate();
            em.createNamedQuery("Meme.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            meme1.getComments().add(comment1);
            meme1.getComments().add(comment2);
            meme2.getComments().add(comment3);
            meme1.getUpvoters().add(user);
            meme1.getDownvoters().add(admin);
            meme2.getUpvoters().add(admin);
            comment1.setMeme(meme1);
            comment2.setMeme(meme1);
            comment3.setMeme(meme2);
            user.getUpvotedMemes().add(meme1);
            admin.getUpvotedMemes().add(meme2);
            admin.getDownvotedMemes().add(meme1);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
