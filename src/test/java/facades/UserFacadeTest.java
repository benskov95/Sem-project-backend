package facades;

import dto.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.MissingInput;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Disabled;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private static User user, admin, banned;


    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        user = new User("user", "test123");
        admin = new User("admin", "test123");
        banned = new User("banned", "test123");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Roles.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Meme.deleteAllRows").executeUpdate();
            em.createNamedQuery("Comment.deleteAllRows").executeUpdate();
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            Role bannedRole = new Role("banned");
            user.addRole(userRole);
            admin.addRole(adminRole);
            banned.addRole(bannedRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(banned);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }


    @Test
    public void getAllUsersTest() {
        List<UserDTO> userDTOList = facade.getAllUsers();

        assertTrue(userDTOList.size() == 3);
    }

    @Test
    public void testDeleteUser() {
        String userName = user.getUsername();

        UserDTO userDTO = facade.deleteUser(userName);

        assertTrue(userDTO.getUsername().equals(userName));
    }

    @Test
    public void testAddUser() throws AuthenticationException {
        User newUser = new User("Test", "Testtest");
        UserDTO userDTO = facade.addUser(new UserDTO(newUser));

        List<UserDTO> userDTOList = facade.getAllUsers();

        assertTrue(userDTOList.size() == 4);

        assertTrue(userDTO.getUsername().equals(newUser.getUsername()));
    }
    
    @Test
    public void testEditUser() throws MissingInput, AuthenticationException {
        assertTrue(user.getUsername().equals("user"));
        user.setProfilePicture("test");
        facade.editUser(new UserDTO(user));
        assertTrue(user.getProfilePicture().equals("test"));
    }
    
    @Test
    public void testBanUser() {
        assertTrue(user.getUsername().equals("user"));
        UserDTO userDTO = facade.banUser("user");
        assertTrue(userDTO.getRoles().get(0).equals("banned"));
        
    }
    
    @Test
    public void testUnbanUser() {
        assertTrue(banned.getUsername().equals("banned"));
        UserDTO userDTO = facade.unbanUser("banned");
        assertTrue(userDTO.getRoles().get(0).equals("user"));
    }
}