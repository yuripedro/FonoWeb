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

import br.ufrn.fonoweb.model.Arquivo;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import br.ufrn.fonoweb.model.Usuario;
import br.ufrn.fonoweb.service.ArquivoService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
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
    private List<UploadedFile> listUploadedFiles = new ArrayList<>();

    @Getter
    @Setter
    private Map<String, byte[]> mapUploadedFiles = new HashMap<>();

    @Getter
    @Setter
    private String encodedFileName;

    @Inject
    private ArquivoService arquivoService;

    public void startUploadFile(Long id) {
        setCurrentState(SEARCH_STATE);
        this.setUploadFile(true);
        setBean(id);
        listUploadedFiles.clear();
        mapUploadedFiles.clear();
    }

    @Override
    public void setCurrentState(String currentState) {
        super.setCurrentState(currentState);
        this.setUploadFile(false);
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event.getFile() != null) {
            //listUploadedFiles.add(event.getFile());
            mapUploadedFiles.put(event.getFile().getFileName(), event.getFile().getContents());
        }
//        
//        
//        List<UploadedFile> tmpUploadedFiles = new ArrayList<>();
//        tmpUploadedFiles.add(event.getFile());
//        System.out.println("------>>>> passou no upload" );
//
//        for (UploadedFile uploadedFile : tmpUploadedFiles) {
//            if (uploadedFile != null) {
//                //uploadedFiles.add(uploadedFile);
//                //System.out.println("------>>>> " + uploadedFile.getFileName());
//                //this.saveFile(uploadedFile);
//            } else {
//                //addMessage(FacesMessage.SEVERITY_ERROR, null, " Falha ao carregar arquivo.");
//            }
//        }
//        if (!tmpUploadedFiles.isEmpty()) {
//            this.saveFiles(tmpUploadedFiles);
//        } else {
//            addMessage(FacesMessage.SEVERITY_ERROR, null, " Falha no carregamento de arquivo(s).");
//        }
    }

    public void saveFiles() {
        String fileName = "";
        boolean result = true;

//        try {
//            for (UploadedFile uploadedFile : this.getListUploadedFiles()) {
//                if (uploadedFile != null) {
//                    fileName = uploadedFile.getFileName();
//                    String codedFileName = arquivoService.getEncodedFileName(fileName, uploadedFile.getContents());
//                    arquivoService.saveFile(codedFileName, uploadedFile.getContents());
//                    //Adiciona os arquivos ao usuário
//                    this.addArquivoUsuario(uploadedFile, codedFileName);
//                }
//            }
//        } catch (Exception e) {
//            result = false;
//        }
        try {
            for (Map.Entry<String, byte[]> entrySet : mapUploadedFiles.entrySet()) {
                fileName = entrySet.getKey();
                byte[] content = entrySet.getValue();
                String codedFileName = arquivoService.getEncodedFileName(fileName, content);
                arquivoService.saveFile(codedFileName, content);
                //Adiciona os arquivos ao usuário
                this.addArquivoUsuario(fileName, codedFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        if (result == true) {
            processUpdate();
            addMessage(FacesMessage.SEVERITY_INFO, null, "Arquivos carregados com sucesso.");
        } else {
//            for (UploadedFile uploadedFile : this.getListUploadedFiles()) {
//                String codedFileName = arquivoService.getEncodedFileName(fileName, uploadedFile.getContents());
//                arquivoService.deleteFile(codedFileName);
//            }
            addMessage(FacesMessage.SEVERITY_ERROR, null, " Falha ao processar arquivo(s).");
        }
    }

    private void addArquivoUsuario(String descricao, String fileName) {
        Arquivo arquivo = new Arquivo();

        arquivo.setDataInclusao(new Date());
        arquivo.setDesccricao(descricao);
        //arquivo.setDescritor(new DescritorVoz());
        arquivo.setNome(fileName);

        getBean().addArquivo(arquivo);
    }

}
