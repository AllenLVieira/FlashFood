package br.com.allen.flashfood.infrastructure.repository;

import br.com.allen.flashfood.domain.model.State;
import br.com.allen.flashfood.domain.repository.StateRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class StateRepositoryJpa implements StateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<State> getAllStates() {
        return entityManager.createQuery("from State", State.class)
                .getResultList();
    }

    @Override
    public State getStateById(Long id) {
        return entityManager.find(State.class, id);
    }

    @Transactional
    @Override
    public State saveState(State state) {
        return entityManager.merge(state);
    }

    @Transactional
    @Override
    public void removeState(Long stateId) {
        State state = getStateById(stateId);
        if (state == null) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(state);
    }
}
