package fu.DE200388.dao;

import fu.DE200388.pojo.Employee;
import fu.DE200388.pojo.Project;
import fu.DE200388.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class EmployeeDAO {

    public EmployeeDAO(EntityManagerFactory emf) {
    }

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
    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();


        try {
            em.getTransaction().begin();

            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            employee.assignToProject(project);

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
    public void unassignEmployeeFromProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();


        try {
            em.getTransaction().begin();

            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            employee.unassignFromProject(project);

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
    public List<Employee> findActiveEmployeesWithMultipleProjects() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();


        try {
            String jpql = """
                SELECT e
                FROM Employee e
                WHERE e.active = true
                AND SIZE(e.projects) > 1
                """;

            return em.createQuery(jpql, Employee.class)
                    .getResultList();

        } finally {
            em.close();
        }
    }
    /*
     * When an employee leaves the company, we should not automatically
     * remove the Employee or related Project entities using CascadeType.REMOVE.
     *
     * Deactivation and project unassignment are different operations.
     * Setting active = false preserves employee and project history.
     *
     * If the business requires the employee to be removed from all current
     * projects, the relationships should be explicitly removed from
     * employee.projects instead of cascading REMOVE to Project entities.
     */
    // TODO 5.11
    public void deactivateEmployee(Long employeeId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            em.getTransaction().begin();

            Employee employee = em.find(Employee.class, employeeId);

            if (employee != null) {
                employee.setActive(false);
            }

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

}
