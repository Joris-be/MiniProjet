package pharmacie.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Fournisseur {

    @Id
    @Basic(optional = false)
    @NonNull
    @Size(min = 1, max = 5)
    @Column(nullable = false, length = 5)
    private String idFournisseur;

    @Basic(optional = false)
    @NonNull
    @Size(min = 1, max = 40)
    @Column(nullable = false, length = 40)
    private String nom;

    @NonNull
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String adresseElectronique;

    @ManyToMany
    @JoinTable(
            name = "fournisseur_categorie",
            joinColumns = @JoinColumn(name = "idFournisseur"),
            inverseJoinColumns = @JoinColumn(name = "idCategorie")
    )
    @JsonIgnoreProperties("fournisseurs")
    
    private List<Categorie> categories = new ArrayList<>();
}
