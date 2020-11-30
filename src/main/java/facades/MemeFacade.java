package facades;

import dto.CommentDTO;
import entities.Comment;
import entities.Meme;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MemeFacade {
    
    private static EntityManagerFactory emf;
    private static MemeFacade instance;
    
    public static MemeFacade getMemeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MemeFacade();
        }
        return instance;
    }


    public void addComment(CommentDTO commentDTO) {

        EntityManager em = emf.createEntityManager();

        Meme meme = em.find(Meme.class, commentDTO.getMeme_id());
        User user = em.find(User.class, commentDTO.getUsername());
        Comment comment = new Comment(commentDTO.getComment(), user);
        meme.getComments().add(comment);
        comment.setMeme(meme);

        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }finally {
            em.close();
        }

    }

    
}
