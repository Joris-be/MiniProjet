package pharmacie.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pharmacie.service.ReapprovisionnementService;

@RestController
@RequestMapping("/approvisionnement")
@RequiredArgsConstructor
public class ApprovisionnementController {

    private final ReapprovisionnementService reapprovisionnementService;

    @PostMapping("/declencher")
    public ResponseEntity<String> declencher() {
        reapprovisionnementService.envoyerDemandesDevis();
        return ResponseEntity.ok("Demandes de devis envoyées avec succès !");
    }
}