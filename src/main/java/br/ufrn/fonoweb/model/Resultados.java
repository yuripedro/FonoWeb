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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_resultados" , name = "ID_SEQUENCE",allocationSize = 1)
public class Resultados extends AbstractBean<Resultados, Long>{
    
    @Column(nullable = false, unique = false)
    private Date dataDosResultados;
    
    @Column(nullable = false, unique = false)
    private int taxaDeAcerto;
    
    @ManyToOne
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    private TipoDiagnostico diagnostico;
    
    @Override
    public int compareTo(Resultados o) {
        int result;
        if(o.getId().compareTo(this.getId())>0){
            result=1;
        }else{
            result=0;
        }
        return result;
    }
}
