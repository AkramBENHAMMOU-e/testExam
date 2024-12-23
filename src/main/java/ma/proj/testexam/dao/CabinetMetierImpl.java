package ma.proj.testexam.dao;

import ma.proj.testexam.connectionDB.SingletonConnexionDB;
import ma.proj.testexam.model.Consultation;
import ma.proj.testexam.model.Medecin;
import ma.proj.testexam.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CabinetMetierImpl implements ICabinetMetier{
    private Connection connection;

    public CabinetMetierImpl() {
        this.connection = SingletonConnexionDB.getConnexion();
    }

    // Gestion des patients
    @Override
    public void ajouterPatient(Patient p) {
        String sql = "INSERT INTO patient(nom, prenom, cin, telephone, email, date_naissance) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setString(3, p.getCin());
            ps.setString(4, p.getTelephone());
            ps.setString(5, p.getEmail());
            ps.setDate(6, new java.sql.Date(p.getDateNaissance().getTime()));
            ps.executeUpdate();
            System.out.println("Patient ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> rechercherPatient(String motCle) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE nom LIKE ? OR prenom LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + motCle + "%");
            ps.setString(2, "%" + motCle + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("id_patient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("cin"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getDate("date_naissance")
                );
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public void supprimerPatient(int idPatient) {
        String sql = "DELETE FROM patient WHERE id_patient = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPatient);
            ps.executeUpdate();
            System.out.println("Patient supprimé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> afficherPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("id_patient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("cin"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getDate("date_naissance")
                );
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // Gestion des médecins
    @Override
    public void ajouterMedecin(Medecin m) {
        String sql = "INSERT INTO medecin(nom, prenom, email, tel) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getEmail());
            ps.setString(4, m.getTel());
            ps.executeUpdate();
            System.out.println("Médecin ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerMedecin(int idMedecin) {
        String sql = "DELETE FROM medecin WHERE id_medecin = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idMedecin);
            ps.executeUpdate();
            System.out.println("Médecin supprimé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Medecin> afficherMedecins() {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM medecin";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Medecin m = new Medecin(
                        rs.getInt("id_medecin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("tel")
                );
                medecins.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medecins;
    }

    // Gestion des consultations
    @Override
    public void ajouterConsultation(Consultation c) {
        String sql = "INSERT INTO consultation(date_consultation, id_patient, id_medecin) VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(c.getDateConsultation().getTime()));
            ps.setInt(2, c.getPatient().getIdPatient());
            ps.setInt(3, c.getMedecin().getIdMedecin());
            ps.executeUpdate();
            System.out.println("Consultation ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerConsultation(int idConsultation) {
        String sql = "DELETE FROM consultation WHERE id_consultation = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idConsultation);
            ps.executeUpdate();
            System.out.println("Consultation supprimée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Consultation> afficherConsultations() {
        List<Consultation> consultations = new ArrayList<>();
        String sql = "SELECT * FROM consultation";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Consultation c = new Consultation(
                        rs.getInt("id_consultation"),
                        rs.getDate("date_consultation"),
                        new Patient(rs.getInt("id_patient"), null, null, null, null, null, null),
                        new Medecin(rs.getInt("id_medecin"), null, null, null, null)
                );
                consultations.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultations;
    }
}
