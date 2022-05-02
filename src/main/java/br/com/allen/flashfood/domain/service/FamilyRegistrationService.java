package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.EntityInUseException;
import br.com.allen.flashfood.domain.exception.FamilyNotFoundException;
import br.com.allen.flashfood.domain.model.Family;
import br.com.allen.flashfood.domain.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FamilyRegistrationService {
    public static final String FAMILY_IN_USE = "Family with %d code cannot be removed because it is in use.";

    @Autowired
    private FamilyRepository familyRepository;

    @Transactional
    public Family saveFamily(Family family) {
        return familyRepository.save(family);
    }

    @Transactional
    public void deleteFamily(Long familyId) {
        try {
            familyRepository.deleteById(familyId);
            familyRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FamilyNotFoundException(familyId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(FAMILY_IN_USE, familyId));
        }
    }

    public Family findFamilyOrElseThrow(Long familyId) {
        return familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyNotFoundException(familyId));
    }
}
