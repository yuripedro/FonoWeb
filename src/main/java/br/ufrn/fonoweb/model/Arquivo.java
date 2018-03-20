/*
 * Copyright 2016 JoinFaces.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.ufrn.fonoweb.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author yuri
 */
@Entity
@Table(name = "arquivo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude={"id", "usuario", "descritor"})
@ToString(callSuper = false)
public class Arquivo implements Serializable{
    @Id
    @SequenceGenerator(name = "id_arquivo", sequenceName = "seq_arquivo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_arquivo")
    private Long id;
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = false)
    private String nome;
    
    @Column(nullable = true, unique = false)
    private String resultadoAnalise;

    @Column(nullable = false, unique = false)
    private String descricao;

    @Column(name = "data_inclusao", nullable = false, unique = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInclusao;

    @OneToOne
    private DescritorVoz descritor;

    @ManyToOne
    private Usuario usuario;

    public int compareTo(Arquivo o) {
        int result;
        if (o.getId().compareTo(this.getId()) > 0) {
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
