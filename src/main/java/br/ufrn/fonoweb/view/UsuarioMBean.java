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
package br.ufrn.fonoweb.view;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import br.ufrn.fonoweb.model.Usuario;
import br.ufrn.fonoweb.service.ArquivoService;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author yuri
 */
@Named
@ViewScoped
public class UsuarioMBean extends CrudMBean<Usuario, Long> {

    @Getter
    @Setter
    private boolean uploadFile;

    @Getter
    @Setter
    private UploadedFile file;

    @Inject
    private ArquivoService arquivoService;

    public void startUploadFile() {
        setCurrentState(SEARCH_STATE);
        this.setUploadFile(true);
    }

    @Override
    public void setCurrentState(String currentState) {
        super.setCurrentState(currentState);
        this.setUploadFile(false);
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event.getFile() != null) {
            this.setFile(event.getFile());
            this.saveFile();
            //addMessage(FacesMessage.SEVERITY_INFO, null, event.getFile().getFileName() + " carregado.");
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, null, " Falha ao carregar arquivo.");
        }
    }

    private void saveFile() {
        if (this.getFile() != null) {
            arquivoService.saveFile( this.getFile().getFileName(), this.getFile().getContents() );
            addMessage(FacesMessage.SEVERITY_INFO, null, this.getFile().getFileName() + " salvo com sucesso..");
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, null, " Falha ao gravar arquivo no datastore.");
        }
    }

}
