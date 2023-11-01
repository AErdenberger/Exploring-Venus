package learn.venus.domain;

import learn.venus.data.DataAccessException;
import learn.venus.data.OrbiterRepository;
import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrbiterService {

    private final OrbiterRepository repository;

    public OrbiterService(OrbiterRepository repository){
        this.repository = repository;
    }

    public List<Orbiter> findByType(OrbiterType type) throws DataAccessException {
        return repository.findByType(type);
    }

    /*
    add:
    1. Can't be null
    2. name required
    3. modules can hold 4 astronauts
    4. module with a dock can hold 2 astronauts and 1 shuttle
     */

    public OrbiterResult add(Orbiter orbiter) throws DataAccessException {
        OrbiterResult result = validateInputs(orbiter);
        if(!result.isSuccessful()){
            return result;
        }

        Map<OrbiterType, Integer> counts = countTypes();
        counts.put(orbiter.getType(), counts.get(orbiter.getType()) + 1);
        result = validateDomain(counts);
        if(!result.isSuccessful()){
            return result;
        }

        Orbiter o = repository.add(orbiter);
        result.setPayload(o);

        return result;
    }

    public OrbiterResult update(Orbiter orbiter) throws DataAccessException {
        OrbiterResult result = validateInputs(orbiter);
        if(!result.isSuccessful()){
            return result;
        }

        Orbiter existing = repository.findById(orbiter.getOrbiterId());
        if(existing == null){
            result.addErrorMessage("OrbiterID " + orbiter.getOrbiterId() + " not found");
            return result;
        }

        if(existing.getType() != orbiter.getType()){
            result.addErrorMessage("CAnnot update an orbiter's type");
            return result;
        }

        boolean success = repository.update(orbiter);
        if(!success){
            result.addErrorMessage("Could not find OrbiterID " + orbiter.getOrbiterId());
        }

        return result;
    }

    public OrbiterResult deleteByID(int orbiterID) throws DataAccessException {
        OrbiterResult result = new OrbiterResult();
        Orbiter orbiter = repository.findById((orbiterID));
        if(orbiter == null){
            result.addErrorMessage("Could not find OrbiterID: " + orbiterID);
            return result;
        }

        Map<OrbiterType, Integer> counts = countTypes();
        counts.put(orbiter.getType(), counts.get(orbiter.getType()) - 1);
        result = validateDomain(counts);
        if(!result.isSuccessful()){
            return result;
        }

        boolean success = repository.deleteById(orbiterID);
        if(!success){
            result.addErrorMessage("Could not find OrbiterID " + orbiter.getOrbiterId());
        }

        return result;
    }

    private Map<OrbiterType, Integer> countTypes() {

        HashMap<OrbiterType, Integer> counts = new HashMap<>();
        counts.put(OrbiterType.MODULE, 0);
        counts.put(OrbiterType.MODULE_WITH_DOCK, 0);
        counts.put(OrbiterType.ASTRONAUT, 0);
        counts.put(OrbiterType.SHUTTLE, 0);
        counts.put(OrbiterType.VENUSIAN, 0);


        try {
            List<Orbiter> allOrbiters = repository.findAll();
            for(Orbiter o: allOrbiters){
                switch(o.getType()){
                    case MODULE:
                        counts.put(OrbiterType.MODULE, counts.get(OrbiterType.MODULE) + 1);
                        break;
                    case MODULE_WITH_DOCK:
                        counts.put(OrbiterType.MODULE_WITH_DOCK, counts.get(OrbiterType.MODULE_WITH_DOCK) + 1);
                        break;
                    case ASTRONAUT:
                        counts.put(OrbiterType.ASTRONAUT, counts.get(OrbiterType.ASTRONAUT) + 1);
                        break;
                    case SHUTTLE:
                        counts.put(OrbiterType.SHUTTLE, counts.get(OrbiterType.SHUTTLE) + 1);
                        break;
                    case VENUSIAN:
                        counts.put(OrbiterType.VENUSIAN, counts.get(OrbiterType.VENUSIAN) + 1);
                }
            }
        } catch (DataAccessException ex) {

        }

        return counts;

    }

    private OrbiterResult validateInputs(Orbiter orbiter){
        OrbiterResult result = new OrbiterResult();
        if(orbiter == null){
            result.addErrorMessage("orbiter cannot be null");
            return result;
        }

        if(orbiter.getName() == null || orbiter.getName().trim().isEmpty()){
            result.addErrorMessage("name is required.");
        }
        return result;
    }

    private OrbiterResult validateDomain(Map<OrbiterType, Integer> counts) throws DataAccessException {

        int astroCount = counts.get(OrbiterType.ASTRONAUT);
        int shuttleCount = counts.get(OrbiterType.SHUTTLE);
        int modCount = counts.get(OrbiterType.MODULE);
        int modDockCount = counts.get(OrbiterType.MODULE_WITH_DOCK);

        OrbiterResult result = new OrbiterResult();

        if(astroCount > modCount * 4 + modDockCount * 2){
            result.addErrorMessage("No room for another astronaut");
        }

        if(shuttleCount > modDockCount){
            result.addErrorMessage("No room for another shuttle");
        }

        return result;
    }
}
