package facades;

import dto.CommentDTO;
import entities.Comment;
import entities.Meme;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

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

    public List<CommentDTO> getAllCommentsById (int id) {

        EntityManager em = emf.createEntityManager();

        TypedQuery<Comment> query = em.createQuery("SELECT c From Comment c join c.meme m where m.id = :id", Comment.class);
        query.setParameter("id", id);

        List<Comment> commentList = query.getResultList();
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for (Comment comment:commentList) {
            commentDTOList.add(new CommentDTO(comment));
        }
        return commentDTOList;
    }

    
}
