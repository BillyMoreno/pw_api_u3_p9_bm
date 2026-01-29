package materia.infraestructure;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import materia.domain.Materia;

@ApplicationScoped
public class MateriaRepository implements PanacheRepository<Materia> {

	/*@Inject
	EntityManager em;

	// Métodos usando Panache
	/*public List<Materia> findByNombre(String nombre) {
		return list("nombre", nombre);
	}

	public List<Materia> findByCreditos(String creditos) {
		return list("creditos", creditos);
	}*/


	/*public List<Materia> seleccionarPorNombre(String nombre) {
		
		if (nombre == null || nombre.trim().isEmpty()) {
			return listAll();
		}

		TypedQuery<Materia> myQuery = this.em.createQuery(
				"SELECT m FROM Materia m WHERE LOWER(m.nombre) LIKE :nombre", Materia.class);
		myQuery.setParameter("nombre", "%" + nombre.trim().toLowerCase() + "%");
		return myQuery.getResultList();
	}

	public List<Materia> seleccionarPorCreditos(String creditos) {
		TypedQuery<Materia> myQuery = this.em.createQuery("SELECT m FROM Materia m WHERE m.creditos = :creditos", Materia.class);
		myQuery.setParameter("creditos", creditos);
		return myQuery.getResultList();
	}*/

}
