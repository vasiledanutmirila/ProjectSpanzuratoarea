package pa.project.repositories;

import pa.project.entities.Words;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class WordsRepository {
    EntityManager entityManager;

    public WordsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public int getMaxId() {
        Query query = entityManager.createQuery("select w.id from Words w order by w.id desc");
        return (int) query.getResultList().get(0);
    }

    public Words getById(int id) {
        return entityManager.find(Words.class, id);
    }
}
