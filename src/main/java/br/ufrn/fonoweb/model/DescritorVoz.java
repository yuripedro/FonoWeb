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
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author yuri
 */
@Entity(name = "descritor_voz")
@Getter
@Setter
@SequenceGenerator(sequenceName = "seq_descritorvoz", name = "ID_SEQUENCE", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class DescritorVoz extends AbstractBean<DescritorVoz, Long> {

    @ManyToOne
    private BancoDescritor banco;

    @OneToOne
    private Arquivo arquivo;

    private Double[] descritor;
    private String descricao;

    @Column(name = "data_ativacao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAtivacao;

    @Override
    public int compareTo(DescritorVoz o) {
        int result;
        if (o.getId().compareTo(this.getId()) > 0) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

}
