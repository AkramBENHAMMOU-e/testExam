package ma.proj.testexam.ApplicationConsole;

import ma.proj.testexam.dao.CabinetMetierImpl;
import ma.proj.testexam.dao.ICabinetMetier;
import ma.proj.testexam.model.Consultation;
import ma.proj.testexam.model.Medecin;
import ma.proj.testexam.model.Patient;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class TestAppConsole {

        public static void main(String[] args) {
            ICabinetMetier metier = new CabinetMetierImpl();
            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (true) {
                System.out.println("\n--- MENU ---");
                System.out.println("1. Ajouter un patient");
                System.out.println("2. Rechercher des patients");
                System.out.println("3. Supprimer un patient");
                System.out.println("4. Afficher tous les patients");
                System.out.println("5. Ajouter un médecin");
                System.out.println("6. Supprimer un médecin");
                System.out.println("7. Afficher tous les médecins");
                System.out.println("8. Ajouter une consultation");
                System.out.println("9. Supprimer une consultation");
                System.out.println("10. Afficher toutes les consultations");
                System.out.println("0. Quitter");
                System.out.print("Votre choix : ");

                int choix = scanner.nextInt();
                scanner.nextLine(); // Consommer la ligne restante

                switch (choix) {
                    case 1:
                        System.out.print("Nom : ");
                        String nom = scanner.nextLine();
                        System.out.print("Prénom : ");
                        String prenom = scanner.nextLine();
                        System.out.print("CIN : ");
                        String cin = scanner.nextLine();
                        System.out.print("Téléphone : ");
                        String telephone = scanner.nextLine();
                        System.out.print("Email : ");
                        String email = scanner.nextLine();
                        System.out.print("Date de naissance (yyyy-MM-dd) : ");
                        String dateNaissance = scanner.nextLine();
                        try {
                            Patient patient = new Patient(0, nom, prenom, cin, telephone, email, dateFormat.parse(dateNaissance));
                            metier.ajouterPatient(patient);
                        } catch (Exception e) {
                            System.out.println("Date invalide.");
                        }
                        break;

                    case 2:
                        System.out.print("Mot clé : ");
                        String motCle = scanner.nextLine();
                        List<Patient> patients = metier.rechercherPatient(motCle);
                        for (Patient p : patients) {
                            System.out.println(p);
                        }
                        break;

                    case 3:
                        System.out.print("ID du patient à supprimer : ");
                        int idPatient = scanner.nextInt();
                        metier.supprimerPatient(idPatient);
                        break;

                    case 4:
                        List<Patient> allPatients = metier.afficherPatients();
                        for (Patient p : allPatients) {
                            System.out.println(p);
                        }
                        break;

                    case 5:
                        System.out.print("Nom : ");
                        String nomMedecin = scanner.nextLine();
                        System.out.print("Prénom : ");
                        String prenomMedecin = scanner.nextLine();
                        System.out.print("Email : ");
                        String emailMedecin = scanner.nextLine();
                        System.out.print("Téléphone : ");
                        String telMedecin = scanner.nextLine();
                        Medecin medecin = new Medecin(0, nomMedecin, prenomMedecin, emailMedecin, telMedecin);
                        metier.ajouterMedecin(medecin);
                        break;

                    case 6:
                        System.out.print("ID du médecin à supprimer : ");
                        int idMedecin = scanner.nextInt();
                        metier.supprimerMedecin(idMedecin);
                        break;

                    case 7:
                        List<Medecin> allMedecins = metier.afficherMedecins();
                        for (Medecin m : allMedecins) {
                            System.out.println(m);
                        }
                        break;

                    case 8:
                        System.out.print("ID du patient : ");
                        int patientId = scanner.nextInt();
                        System.out.print("ID du médecin : ");
                        int medecinId = scanner.nextInt();
                        System.out.print("Date de consultation (yyyy-MM-dd) : ");
                        scanner.nextLine();
                        String dateConsultation = scanner.nextLine();
                        try {
                            Consultation consultation = new Consultation(0, dateFormat.parse(dateConsultation),
                                    new Patient(patientId, null, null, null, null, null, null),
                                    new Medecin(medecinId, null, null, null, null));
                            metier.ajouterConsultation(consultation);
                        } catch (Exception e) {
                            System.out.println("Date invalide.");
                        }
                        break;

                    case 9:
                        System.out.print("ID de la consultation à supprimer : ");
                        int idConsultation = scanner.nextInt();
                        metier.supprimerConsultation(idConsultation);
                        break;

                    case 10:
                        List<Consultation> consultations = metier.afficherConsultations();
                        for (Consultation c : consultations) {
                            System.out.println(c);
                        }
                        break;

                    case 0:
                        System.out.println("Au revoir !");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Choix invalide. Réessayez.");
                }
            }
        }

}
