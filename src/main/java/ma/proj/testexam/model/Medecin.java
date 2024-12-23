package ma.proj.testexam.model;

public class Medecin {
    private int idMedecin;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    public Medecin(int idMedecin, String nom, String prenom, String email, String tel) {
        this.idMedecin = idMedecin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = tel;
    }

    public Medecin() {

    }

    // Getters et Setters
    public int getIdMedecin() {
        return idMedecin;
    }

    public void setIdMedecin(int idMedecin) {
        this.idMedecin = idMedecin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return telephone;
    }

    public void setTel(String tel) {
        this.telephone = tel;
    }

    // Méthode toString()
    @Override
    public String toString() {
        return "Medecin{" +
                "idMedecin=" + idMedecin +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + telephone + '\'' +
                '}';
    }
}
