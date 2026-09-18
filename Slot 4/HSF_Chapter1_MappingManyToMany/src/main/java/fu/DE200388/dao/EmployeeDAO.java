package fu.DE200388.dao;

import fu.DE200388.pojo.Employee;
import fu.DE200388.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EmployeeDAO {

    // ===== CREATE =====
    public void save(Employee employee) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(employee);

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
    public Employee findById(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }


    // ===== READ ALL =====
    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            return em.createQuery(
                    "SELECT e FROM Employee e",
                    Employee.class
            ).getResultList();
        } finally {
            em.close();
        }
    }


    // ===== UPDATE =====
    public Employee update(Employee employee) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            em.getTransaction().begin();

            // Quan trọng: lấy object do merge() trả về
            employee = em.merge(employee);

            em.getTransaction().commit();

            return employee;

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

            Employee employee =
                    em.find(Employee.class, id);

            if (employee != null) {
                em.remove(employee);
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
}
