package br.ufrn.fonoweb.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude={"id", "arquivos", "resultados"})
@ToString(callSuper = false)

public class Usuario implements Serializable{
    
    @Id
    @SequenceGenerator(name = "id_usuario", sequenceName = "seq_usuario",  allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_usuario")
    private Long id;
    
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = false)
    private String nome;
    
    @Column(nullable = false)
    private String cpf;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Arquivo> arquivos = new TreeSet<>();

    @OneToMany(mappedBy = "usuario", orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Resultado> resultados = new TreeSet<>();

    
    public void addArquivo(Arquivo arquivo){
        arquivos.add(arquivo);
    }
    
    public void addResultado(Resultado resultado){
        resultados.add(resultado);
    }
    
    public int compareTo(Usuario objeto) {
        int result;
        if ( (objeto.getId().compareTo(this.getId()) > 0)) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
