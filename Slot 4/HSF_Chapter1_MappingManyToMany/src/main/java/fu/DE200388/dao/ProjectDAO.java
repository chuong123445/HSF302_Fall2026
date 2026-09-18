package fu.DE200388.dao;


import fu.DE200388.pojo.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ProjectDAO {

    private final EntityManagerFactory emf;

    public ProjectDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // TODO 5.6 - Save Project
    public void save(Project project) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(project);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // TODO 5.6 - Find Project by ID
    public Project findById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }
}
