package facades;

import dto.MemeDTO;
import entities.Meme;
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

    public List<MemeDTO> getAllDownvotedMemes() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query = em.createQuery("SELECT m FROM Meme m ORDER BY downvotes DESC", Meme.class);
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
            TypedQuery query = em.createQuery("SELECT m FROM Meme m ORDER BY upvotes DESC", Meme.class);
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
}
