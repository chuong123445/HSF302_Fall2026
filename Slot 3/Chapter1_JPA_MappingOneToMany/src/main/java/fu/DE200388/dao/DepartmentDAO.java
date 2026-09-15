package fu.DE200388.dao;

import fu.DE200388.pojo.Department;
import fu.DE200388.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DepartmentDAO {

    // ===== CREATE =====
    public void save(Department department) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(department);

            em.getTransaction().commit();
        } catch (RuntimeException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;
        } finally {
            em.close();
        }
    }


    // ===== READ BY ID =====
    public Department findById(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            return em.find(Department.class, id);
        } finally {
            em.close();
        }
    }


    // ===== READ ALL =====
    public List<Department> findAll() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            return em.createQuery(
                    "SELECT d FROM Department d",
                    Department.class
            ).getResultList();
        } finally {
            em.close();
        }
    }


    // ===== UPDATE =====
    public Department update(Department department) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            em.getTransaction().begin();

            // merge() trả về object MANAGED
            department = em.merge(department);

            em.getTransaction().commit();

            return department;

        } catch (RuntimeException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;
        } finally {
            em.close();
        }
    }


    // ===== DELETE =====
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            em.getTransaction().begin();

            Department department =
                    em.find(Department.class, id);

            if (department != null) {
                em.remove(department);
            }

            em.getTransaction().commit();

        } catch (RuntimeException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;
        } finally {
            em.close();
        }
    }
    public Department findByIdWithEmployees(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            return em.createQuery(
                            "SELECT d FROM Department d " +
                                    "JOIN FETCH d.employees " +
                                    "WHERE d.id = :id",
                            Department.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();

        } finally {
            em.close();
        }
    }
    // ===== TODO 2.9 - FIX N+1 =====
    public List<Department> findAllWithEmployees() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            return em.createQuery(
                    "SELECT d FROM Department d " +
                            "JOIN FETCH d.employees",
                    Department.class
            ).getResultList();

        } finally {
            em.close();
        }
    }
}
