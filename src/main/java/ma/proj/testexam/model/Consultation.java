package ma.proj.testexam.model;

import java.util.Date;

public class Consultation {
    private int idConsultation;
    private Date dateConsultation;
    private Patient patient;
    private Medecin medecin;

    // Constructeurs
    public Consultation() {}

    public Consultation(int idConsultation, Date dateConsultation, Patient patient, Medecin medecin) {
        this.idConsultation = idConsultation;
        this.dateConsultation = dateConsultation;
        this.patient = patient;
        this.medecin = medecin;
    }

    // Getters et Setters
    public int getIdConsultation() {
        return idConsultation;
    }

    public void setIdConsultation(int idConsultation) {
        this.idConsultation = idConsultation;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    // Méthode toString()
    @Override
    public String toString() {
        return "Consultation{" +
                "idConsultation=" + idConsultation +
                ", dateConsultation=" + dateConsultation +
                ", patient=" + patient +
                ", medecin=" + medecin +
                '}';
    }
}
