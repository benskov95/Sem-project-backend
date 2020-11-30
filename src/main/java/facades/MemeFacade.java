package facades;

import dto.MemeDTO;
import entities.Meme;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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

    public int upvoteMeme(String username, MemeDTO memeDTO) {
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
                return meme.getUpvoters().size();
            } finally {
                em.close();
            }
        }
        return 0;
    }
    
    public int downvoteMeme(String username, MemeDTO memeDTO) {
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
                return meme.getDownvoters().size();
            } finally {
                em.close();
            }
        }
        return 0;
    }
    
    public Meme checkIfMemeExists(MemeDTO memeDTO, EntityManager em) {
        Query q = em.createQuery("SELECT m FROM Meme m WHERE m.imageUrl = :url");
        q.setParameter("url", memeDTO.getImageUrl());
        
        if (q.getResultList().size() > 0) {
            return (Meme) q.getResultList().get(0);
        } else {
            Meme meme = new Meme(memeDTO.getImageUrl(), memeDTO.getTitle());
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
    
}
