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

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author yuri
 */
@Entity
@Getter
@Setter
@SequenceGenerator(sequenceName = "seq_arquivo", name = "ID_SEQUENCE", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class Arquivo extends AbstractBean<Arquivo, Long>{
    @Column(nullable = false, unique = false)
    private String nome;
    @Column(nullable = false, unique = false)
    private String desccricao;
    @Column(nullable = false, unique = false)
    private Date dataInclusao;
    
    @OneToOne
    private DescritorVoz descritor;
    
    @ManyToOne
    private Usuario usuario;

    @Override
    public int compareTo(Arquivo o) {
        int result;
        if (o.getId().compareTo(this.getId())> 0) {
            result =1;
        }else{
            result= 0 ;
        }
        return result;
    }
    
}
