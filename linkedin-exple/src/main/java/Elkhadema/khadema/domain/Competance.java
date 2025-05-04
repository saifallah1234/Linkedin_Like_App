package Elkhadema.khadema.domain;

public class Competance {
    private long id;
    private String titre;
    private String technologie;
    private String description;
    private int niveau;

    public Competance(long id, String titre, String technologie, String description, int niveau) {
        this.id = id;
        this.titre = titre;
        this.technologie = technologie;
        this.description = description;
        this.niveau = niveau;
    }

    public Competance(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTechnologie() {
        return technologie;
    }

    public void setTechnologie(String technologie) {
        this.technologie = technologie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNiveau() {
        return niveau;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

}
