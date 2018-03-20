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

import java.io.IOException;
import javax.inject.Named;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author yuri
 */
@Named
public class VozProcessor {

    //recebe o arquivo, extrai e devolve o descritor;
    public double[] gerar_classificador(double numpicos_perct, int tamanho, double kernel, byte[] contents) throws UnsupportedAudioFileException, IOException {
        //==========================================================================
        // Passo 1: Parametros Inicias
        //==========================================================================

        double numpicos = Math.floor((numpicos_perct * tamanho / 2) - 1);

        //==========================================================================
        // Passo 2: Leitura dos Arquivos
        //==========================================================================
        int inicio_janela = 2000 - 1;
        int fim_janela = 20000 - 1;
        double[] amostra = null;

        // double[] aux = FuncoesUtil.wavread(contents, false);
        double[] aux = null;
        double[] janela = new double[fim_janela - inicio_janela + 1];
        int index = 0;
        for (int k = inicio_janela; k < fim_janela + 1; k++) {
            janela[index] = aux[k];
            index++;
        }

        amostra = janela;

        //==========================================================================
        // Passo 3: Normalizacao pelo valor Maximo
        //==========================================================================
        double[] vectorNormalizado = FuncoesUtil.normalizeVector(amostra);

        //==========================================================================
        //Passo 4: Matrizes de Autocorrentropia - 10 vezes
        //==========================================================================
        double[] vetorCorr = null;

        double[][] vetorCorrJanelas = new double[10][];
        for (int j = 0; j < 10; j++) {
             double janelaAleatoria = inicio_janela + ((vectorNormalizado.length - inicio_janela) * Math.random());
             janelaAleatoria = Math.floor(janelaAleatoria - (tamanho + 1));
            
            int janela_fim = (int) Math.floor(janelaAleatoria + tamanho);
            vetorCorrJanelas[j] = FuncoesUtil.autCorrentropyCoef(FuncoesUtil.subArray(vectorNormalizado, (int) janelaAleatoria, janela_fim), kernel);
        }
        vetorCorr = FuncoesUtil.mediaMatrix(vetorCorrJanelas);

        //==========================================================================
        //Passo 5: Subtracao da Media
        //==========================================================================
        double vetorCorrMedia = FuncoesUtil.mediaVector(vetorCorr);
        for (int j = 0; j < vetorCorr.length; j++) {
            vetorCorr[j] = vetorCorr[j] - vetorCorrMedia;
        }

        //==========================================================================
        //Passo 6: Transformada de Fourier
        //==========================================================================
        double[] vetorCorr_Temp = new double[2 * vetorCorr.length];
        double[] vetorCorr_Final = new double[2 * vetorCorr.length];

        vetorCorr_Temp = FuncoesUtil.fft(vetorCorr);

        vetorCorr_Final = FuncoesUtil.subArray(vetorCorr_Temp, 0, (int) Math.floor(vetorCorr_Temp.length / 2) - 1);

        //==========================================================================
        //Passo 7: Pega os maiores picos de cada representacao
        //==========================================================================
        double[] vetorModulo = new double[vetorCorr.length];
        double[] vetorSort_final = new double[vetorCorr.length];

        vetorModulo = FuncoesUtil.calculaModulo(vetorCorr_Final);
        vetorSort_final = FuncoesUtil.quickSortReverso(vetorModulo, 0, vetorModulo.length - 1);

        int[] posicoes_pico_Final = null;
        int posic = (int) numpicos;
        posicoes_pico_Final = FuncoesUtil.posicao(vetorModulo, vetorSort_final, posic);

        double[] vetorClassificador_Final = new double[vetorModulo.length];

        vetorClassificador_Final = FuncoesUtil.calculaVetorPicos(posicoes_pico_Final, vetorCorr_Final, numpicos, vetorModulo.length);

        //==========================================================================
        //Passo 9: Montar Matriz de classificador 
        //==========================================================================
        double[] vetorClassificador_Modulo_Final = new double[vetorClassificador_Final.length / 2];
        vetorClassificador_Modulo_Final = FuncoesUtil.calculaModulo(vetorClassificador_Final);

        return vetorClassificador_Modulo_Final;
    }

    public double[] gerar_classificador(double numpicos_perct, int tamanho, double kernel, String filename) throws UnsupportedAudioFileException, IOException {
        //==========================================================================
        // Passo 1: Parametros Inicias
        //==========================================================================

        double numpicos = Math.floor((numpicos_perct * tamanho / 2) - 1);

        //==========================================================================
        // Passo 2: Leitura dos Arquivos
        //==========================================================================
        int inicio_janela = 2000 - 1;
        int fim_janela = 20000 - 1;
        double[] amostra = null;

        double[] aux = FuncoesUtil.wavread(filename, false);

        double[] janela = new double[fim_janela - inicio_janela + 1];
        int index = 0;
        for (int k = inicio_janela; k < fim_janela + 1; k++) {
            janela[index] = aux[k];
            index++;
        }

        amostra = janela;

        //==========================================================================
        // Passo 3: Normalizacao pelo valor Maximo
        //==========================================================================
        double[] vectorNormalizado = FuncoesUtil.normalizeVector(amostra);

        //==========================================================================
        //Passo 4: Matrizes de Autocorrentropia - 10 vezes
        //==========================================================================
        double[] vetorCorr = null;

        double[][] vetorCorrJanelas = new double[10][];
        for (int j = 0; j < 10; j++) {
            double janelaAleatoria = inicio_janela + ((vectorNormalizado.length - inicio_janela) * Math.random());
            janelaAleatoria = Math.floor(janelaAleatoria - (tamanho + 1));
            
            int janela_fim = (int) Math.floor(janelaAleatoria + tamanho);
            vetorCorrJanelas[j] = FuncoesUtil.autCorrentropyCoef(FuncoesUtil.subArray(vectorNormalizado, (int) janelaAleatoria, janela_fim), kernel);
        }
        vetorCorr = FuncoesUtil.mediaMatrix(vetorCorrJanelas);

        //==========================================================================
        //Passo 5: Subtracao da Media
        //==========================================================================
        double vetorCorrMedia = FuncoesUtil.mediaVector(vetorCorr);
        for (int j = 0; j < vetorCorr.length; j++) {
            vetorCorr[j] = vetorCorr[j] - vetorCorrMedia;
        }

        //==========================================================================
        //Passo 6: Transformada de Fourier
        //==========================================================================
        double[] vetorCorr_Temp = new double[2 * vetorCorr.length];
        double[] vetorCorr_Final = new double[2 * vetorCorr.length];

        vetorCorr_Temp = FuncoesUtil.fft(vetorCorr);

        vetorCorr_Final = FuncoesUtil.subArray(vetorCorr_Temp, 0, (int) Math.floor(vetorCorr_Temp.length / 2) - 1);

        //==========================================================================
        //Passo 7: Pega os maiores picos de cada representacao
        //==========================================================================
        double[] vetorModulo = new double[vetorCorr.length];
        double[] vetorSort_final = new double[vetorCorr.length];

        vetorModulo = FuncoesUtil.calculaModulo(vetorCorr_Final);
        vetorSort_final = FuncoesUtil.quickSortReverso(vetorModulo, 0, vetorModulo.length - 1);
        
        vetorModulo = FuncoesUtil.calculaModulo(vetorCorr_Final);
        
        int[] posicoes_pico_Final = null;
        int posic = (int) numpicos;
        posicoes_pico_Final = FuncoesUtil.posicao(vetorModulo, vetorSort_final, posic);

        double[] vetorClassificador_Final = new double[vetorModulo.length];

        vetorClassificador_Final = FuncoesUtil.calculaVetorPicos(posicoes_pico_Final, vetorCorr_Final, numpicos, vetorModulo.length);


        //==========================================================================
        //Passo 8: Montar Matriz de classificador 
        //==========================================================================
        double[] vetorClassificador_Modulo_Final = new double[vetorClassificador_Final.length / 2];
        vetorClassificador_Modulo_Final = FuncoesUtil.calculaModulo(vetorClassificador_Final);

        return vetorClassificador_Modulo_Final;
    }
}
