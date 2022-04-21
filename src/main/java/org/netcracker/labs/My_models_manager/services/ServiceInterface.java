package org.netcracker.labs.My_models_manager.services;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface<T> {
    List<T> getAll();

    void save(T entity);

    void save(T entity, Long id);

    void delete(Long id) throws DataIntegrityViolationException;

    List<T> findAllByName(String name);

    Optional<T> findById(Long id);

    void deleteAll();
}
