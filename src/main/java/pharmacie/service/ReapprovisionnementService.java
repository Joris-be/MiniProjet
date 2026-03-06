package pharmacie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pharmacie.dao.MedicamentRepository;
import pharmacie.entity.Categorie;
import pharmacie.entity.Fournisseur;
import pharmacie.entity.Medicament;

@Service
public class ReapprovisionnementService {

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private JavaMailSender mailSender;

    
    public void DemanderReapprovisionnement() {
        List<Medicament> medicamentsACommander = medicamentRepository.findMedicamentsAReapprovisionner();
        
        Map<Fournisseur, Map<Categorie, List<Medicament>>> parFournisseur = new HashMap<>();

        for (Medicament med : medicamentsACommander) {
            Categorie cat = med.getCategorie();
            for (Fournisseur fournisseur : cat.getFournisseurs()) {
                parFournisseur
                    .computeIfAbsent(fournisseur, f -> new HashMap<>())
                    .computeIfAbsent(cat, c -> new ArrayList<>())
                    .add(med);
            }
        }

        
        for (Fournisseur fournisseur : parFournisseur.keySet()) {
            envoyerMail(fournisseur, parFournisseur.get(fournisseur));
        }
    }

    private void envoyerMail(Fournisseur fournisseur, Map<Categorie, List<Medicament>> parCategorie) {

        StringBuilder contenu = new StringBuilder();
        contenu.append("Bonjour ").append(fournisseur.getNom()).append(",\n\n");
        contenu.append("Merci de nous transmettre un devis pour les médicaments suivants :\n\n");

        for (Categorie categorie : parCategorie.keySet()) {
            contenu.append("Catégorie : ").append(categorie.getLibelle()).append("\n");
            for (Medicament med : parCategorie.get(categorie)) {
                contenu.append("- ").append(med.getNom())
                       .append(" (Stock: ").append(med.getUnitesEnStock())
                       .append(", Seuil: ").append(med.getNiveauDeReappro())
                       .append(")\n");
            }
            contenu.append("\n");
        }

        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("joris.beldjoudi@gmail.com");
        message.setTo(fournisseur.getMail());
        message.setSubject("Demande de devis – Réapprovisionnement pharmacie");
        message.setText(contenu.toString());

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Erreur envoi mail à " + fournisseur.getMail() + " : " + e.getMessage());
        }
    }
}