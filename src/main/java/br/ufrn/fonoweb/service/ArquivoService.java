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
package br.ufrn.fonoweb.service;

import br.ufrn.fonoweb.model.Arquivo;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author yuri
 */
@Named
public class ArquivoService extends CrudService<Arquivo, Long> {
    @Getter
    @Value("${app.dataStore}")
    private String dataStore;

    public boolean saveFile(String fileName, byte[] contents) {
        boolean status = false;

        try {
            FileUtils.writeByteArrayToFile(new File(fileName), contents);
            status = true;
        } catch (IOException ex) {
            Logger.getLogger(ArquivoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public byte[] openFile(String fileName) {
        byte[] result = null;
        try {
            result = FileUtils.readFileToByteArray(new File(fileName));
        } catch (IOException ex) {
            Logger.getLogger(ArquivoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public boolean deleteFile(String fileName) {
        boolean result = true;
        try {
            FileUtils.forceDelete(new File(fileName));
        } catch (IOException ex) {
            result = false;
            Logger.getLogger(ArquivoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String getEncodedFileName(String originalFile, byte[] contents) {
        MessageDigest md = null;
        String result = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(contents);
            result = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ArquivoService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        result = this.getDataStore().concat("/")
                .concat(result)
                .concat(".")
                .concat(FilenameUtils.getExtension(originalFile));
        return result;
    }
}
