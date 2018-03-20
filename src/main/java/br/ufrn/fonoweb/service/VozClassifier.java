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

import javax.inject.Named;

/**
 *
 * @author yuri
 */
@Named
public class VozClassifier {

    public String resultadoDiagnostico(double[] descritor) {
        double variancia = 0.0;
        String diagnostico;
        //mudança nos limites antes era de 34 a 69
        variancia = FuncoesUtil.calcularVariancia(descritor, 34, 69);
        if (variancia < 1.0) {
            diagnostico = "Indicios de voz doente";
        } else {
            //mudança nos limites antes era de 0 a 29
            variancia = FuncoesUtil.calcularVariancia(descritor, 0, 25);
            if (variancia >= 1.0) {
                diagnostico = "Indicios de voz doente";
            } else {
                diagnostico = "Indicios de voz saudavel";
            }
        }
        return diagnostico;
    }
}
