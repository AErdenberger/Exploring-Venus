package learn.venus.models;

public class Orbiter {
    private OrbiterType type;
    private String name;
    private int orbiterId;
    private String sponsor;

    public Orbiter() {
    }

    public Orbiter(int orbiterId, String name, OrbiterType type, String sponsor) {
        this.type = type;
        this.name = name;
        this.orbiterId = orbiterId;
        this.sponsor = sponsor;
    }

    public OrbiterType getType() {
        return type;
    }

    public void setType(OrbiterType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrbiterId() {
        return orbiterId;
    }

    public void setOrbiterId(int orbiterId) {
        this.orbiterId = orbiterId;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
}
