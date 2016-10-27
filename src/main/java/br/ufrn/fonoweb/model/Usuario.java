package br.ufrn.fonoweb.model;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@SequenceGenerator(sequenceName = "seq_usuario", name = "ID_SEQUENCE", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends AbstractBean<Usuario, Long> {

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
    
    @Override
    public int compareTo(Usuario objeto) {
        int result;
        if (objeto.getCpf().compareTo(this.cpf) > 0 && (objeto.getId().compareTo(this.getId()) > 0)) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

}
