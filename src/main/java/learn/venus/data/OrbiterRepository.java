package learn.venus.data;

import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;

import java.util.List;

public interface OrbiterRepository {
    List<Orbiter> findAll() throws DataAccessException;

    Orbiter findById(int orbiterID) throws DataAccessException;

    List<Orbiter> findByType(OrbiterType type) throws DataAccessException;

    Orbiter add(Orbiter orbiter) throws DataAccessException;

    boolean update(Orbiter orbiter) throws DataAccessException;

    boolean deleteById(int orbiterID) throws DataAccessException;
}
