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
import dto.MemeDTO;
import entities.Meme;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;


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


    public CommentDTO addComment(CommentDTO commentDTO) {

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
        return new CommentDTO(comment);
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


    public MemeDTO upvoteMeme(String username, MemeDTO memeDTO) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, username);
        Meme meme = checkIfMemeExists(memeDTO, em);
        
        if (!checkHasUpvoted(meme, user, em)) {
            try {
                em.getTransaction().begin();
                if (user.getDownvotedMemes().contains(meme)) {
                    meme.getDownvoters().remove(user);
                    user.getDownvotedMemes().remove(meme);
                }
                meme.getUpvoters().add(user);
                user.getUpvotedMemes().add(meme);
                em.getTransaction().commit();
                return new MemeDTO(meme);
            } finally {
                em.close();
            }
        }
        return new MemeDTO(meme);
    }
    
    public MemeDTO downvoteMeme(String username, MemeDTO memeDTO) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, username);
        Meme meme = checkIfMemeExists(memeDTO, em);
        
        if (!checkHasDownvoted(meme, user, em)) {
            try {
                em.getTransaction().begin();
                 if (user.getUpvotedMemes().contains(meme)) {
                    meme.getUpvoters().remove(user);
                    user.getUpvotedMemes().remove(meme);
                }
                meme.getDownvoters().add(user);
                user.getDownvotedMemes().add(meme);
                em.getTransaction().commit();
                return new MemeDTO(meme);
            } finally {
                em.close();
            }
        }
        return new MemeDTO(meme);
    }
    
    public Meme checkIfMemeExists(MemeDTO memeDTO, EntityManager em) {
        Query q = em.createQuery("SELECT m FROM Meme m WHERE m.imageUrl = :url");
        q.setParameter("url", memeDTO.getImageUrl());
        
        if (q.getResultList().size() > 0) {
            return (Meme) q.getResultList().get(0);
        } else {
            Meme meme = new Meme(memeDTO.getImageUrl(), memeDTO.getTitle());
            if (meme.getTitle() == null) {
                meme.setTitle("none");
            }
            em.getTransaction().begin();
            em.persist(meme);
            em.getTransaction().commit();
            return meme;
        }
    }
    
    public boolean checkHasUpvoted(Meme meme, User user, EntityManager em) {
        if (user.getUpvotedMemes().contains(meme)) {
            em.getTransaction().begin();
            user.getUpvotedMemes().remove(meme);
            meme.getUpvoters().remove(user);
            em.getTransaction().commit();
            return true;   
        } else {
            return false;
        }  
    }
    
    public boolean checkHasDownvoted(Meme meme, User user, EntityManager em) {
        if (user.getDownvotedMemes().contains(meme)) {
            em.getTransaction().begin();
            user.getDownvotedMemes().remove(meme);
            meme.getDownvoters().remove(user);
            em.getTransaction().commit();
            return true;
        } else {
            return false;
        }
    }

    public List<MemeDTO> getAllDownvotedMemes() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query = em.createQuery("SELECT DISTINCT m FROM Meme m JOIN m.downvoters d", Meme.class);
            List<Meme> memesList = query.getResultList();
            List<MemeDTO> memeDTOsList = new ArrayList<>();
            for (Meme meme : memesList) {
                memeDTOsList.add(new MemeDTO(meme));
            }
            return memeDTOsList;
        } finally {
            em.close();
        }
    }
    
    public List<MemeDTO> getAllUpvotedMemes() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query = em.createQuery("SELECT DISTINCT m FROM Meme m JOIN m.upvoters u", Meme.class);
            List<Meme> memesList = query.getResultList();
            List<MemeDTO> memeDTOsList = new ArrayList<>();
            for (Meme meme : memesList) {
                memeDTOsList.add(new MemeDTO(meme));
            }
            return memeDTOsList;
        } finally {
            em.close();
        }
    }
    
       public List<MemeDTO> getFavoriteMemes(String userName) {
        EntityManager em = emf.createEntityManager();
        try{
        TypedQuery<Meme> query = em.createQuery("SELECT m FROM Meme m join m.upvoters u WHERE u.username = :username", Meme.class);
        query.setParameter("username", userName);
        List<Meme> result = query.getResultList();
        List<MemeDTO> memeDTOsList = new ArrayList<>();
            for (Meme meme : result) {
                memeDTOsList.add(new MemeDTO(meme));
            }
            return memeDTOsList;
        } finally {
            em.close();
        }
    }
    

}
