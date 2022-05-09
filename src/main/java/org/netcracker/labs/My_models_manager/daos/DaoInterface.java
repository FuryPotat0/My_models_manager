package org.netcracker.labs.My_models_manager.daos;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface DaoInterface<T> {
    List<T> getAll();

    void save(T entity);

    void save(T entity, Long id);

    void update(T entity);

    void delete(Long id) throws DataIntegrityViolationException;

    List<T> findAllByName(String name);

    T findById(Long id);

    void deleteAll();
}
