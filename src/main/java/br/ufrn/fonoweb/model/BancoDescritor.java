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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@SequenceGenerator(sequenceName = "seq_bancodescritor", name = "ID_SEQUENCE", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class BancoDescritor extends AbstractBean<BancoDescritor, Long>{
    private boolean ativo;
    private Date data;
    private String descricao;
    @OneToMany(mappedBy="bancoDescritor" , cascade = CascadeType.ALL ,orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<DescritorVoz> descritores;

    @Override
    public int compareTo(BancoDescritor o) {
       int result;
       if(o.getId().compareTo(this.getId())>0){
           result=1;
       }else{
           result=0;
       }
       return result;
    }
}
