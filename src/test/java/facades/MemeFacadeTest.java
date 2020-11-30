package facades;

import entities.Comment;
import entities.Meme;
import entities.Role;
import entities.User;
import dto.MemeDTO;
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
public class MemeFacadeTest {

    private static EntityManagerFactory emf;
    private static MemeFacade facade;
    private static User user, admin, banned;
    private static Meme meme1, meme2;
    private static Comment comment1, comment2, comment3;

    public MemeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = MemeFacade.getMemeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        setupTestData(em);
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }
    
    @Test
    public void getAllDownvotedMemesTest() {
        List<MemeDTO> memeDTOsList = facade.getAllDownvotedMemes();
        assertTrue(memeDTOsList.size() == 1);
    }
    
    @Test
    public void getAllUpvotedMemesTest() {
        List<MemeDTO> memeDTOsList = facade.getAllUpvotedMemes();
        assertTrue(memeDTOsList.size() == 2);
    }
    
    public void setupTestData(EntityManager em) {
        user = new User("user", "test123");
        admin = new User("admin", "test123");
        banned = new User("banned", "test123");
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
            Role bannedRole = new Role("banned");
            user.addRole(userRole);
            admin.addRole(adminRole);
            banned.addRole(bannedRole);
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
            em.persist(banned);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}