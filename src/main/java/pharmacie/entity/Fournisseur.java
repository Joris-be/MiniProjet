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
import jakarta.validation.constraints.Email;
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
    private String idfournisseur;

    @Basic(optional = false)
    @NonNull
    private String nom;

    @NonNull
    @Email
    private String mail;

    @ManyToMany
    @JoinTable(
            name = "fournisseur_categorie",
            joinColumns = @JoinColumn(name = "id_fournisseur"),
            inverseJoinColumns = @JoinColumn(name = "id_categorie")
    )
    @JsonIgnoreProperties("fournisseurs")
    
    private List<Categorie> categories = new ArrayList<>();
}
