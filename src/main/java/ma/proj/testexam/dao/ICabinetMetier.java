package ma.proj.testexam.dao;

import ma.proj.testexam.model.Consultation;
import ma.proj.testexam.model.Medecin;
import ma.proj.testexam.model.Patient;

import java.util.List;

public interface ICabinetMetier {
    void ajouterPatient(Patient p);
    List<Patient> rechercherPatient(String motCle);
    void supprimerPatient(int idPatient);
    List<Patient> afficherPatients();

    // Gestion des médecins
    void ajouterMedecin(Medecin m);
    void supprimerMedecin(int idMedecin);
    List<Medecin> afficherMedecins();

    // Gestion des consultations
    void ajouterConsultation(Consultation c);
    void supprimerConsultation(int idConsultation);
    List<Consultation> afficherConsultations();
}
