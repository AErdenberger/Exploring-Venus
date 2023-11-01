package learn.venus.domain;

import learn.venus.data.DataAccessException;
import learn.venus.data.OrbiterRepository;
import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;

import java.util.List;

public class OrbiterService {

    private final OrbiterRepository repository;

    public OrbiterService(OrbiterRepository repository){
        this.repository = repository;
    }

    /*
    add:
    1. Can't be null
    2. name required
    3. modules can hold 4 astronauts
    4. module with a dock can hold 4 astronauts and 1 shuttle
     */

    public OrbiterResult add(Orbiter orbiter) throws DataAccessException {
        OrbiterResult result = validateInputs(orbiter);
        if(!result.isSuccessful()){
            return result;
        }

        result = validateDomain(orbiter);
        if(!result.isSuccessful()){
            return result;
        }

        Orbiter o = repository.add(orbiter);
        result.setPayload(o);

        return result;
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

    private OrbiterResult validateDomain(Orbiter orbiter) throws DataAccessException {
        OrbiterResult result = new OrbiterResult();
        List<Orbiter> allOrbiters = repository.findAll();
        if(orbiter.getType() == OrbiterType.ASTRONAUT ||
                orbiter.getType() == OrbiterType.SHUTTLE){

            int astroCount = 0;
            int shuttleCount = 0;
            int modCount = 0;
            int modDockCount = 0;

            for(Orbiter o: allOrbiters){
                switch(o.getType()){
                    case MODULE:
                        modCount++;
                        break;
                    case MODULE_WITH_DOCK:
                        modDockCount++;
                        break;
                    case ASTRONAUT:
                        astroCount++;
                        break;
                    case SHUTTLE:
                        shuttleCount++;
                        break;
                }
            }

            if(orbiter.getType() == OrbiterType.ASTRONAUT){
                if(astroCount + 1 > modCount * 4 + modDockCount * 2){
                    result.addErrorMessage("No room for another astronaut");
                }
            }

            if(orbiter.getType() == OrbiterType.SHUTTLE){
                if(shuttleCount + 1 > modDockCount){
                    result.addErrorMessage("No room for another shuttle");
                }
            }
        }
        return result;
    }
}
