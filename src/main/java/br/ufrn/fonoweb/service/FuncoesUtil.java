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

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 *
 * @author yuri
 */
public class FuncoesUtil {

    public static double calcularVariancia(double[] representacao, int limite_min, int limite_max) {
        double[] subVetor = new double[limite_max - limite_min + 1];
        int index = 0;
        for (int i = limite_min; i <= limite_max; i++) {
            subVetor[index] = representacao[i];
            index++;
        }
        double mediaTemp = 0.0, media = 0.0;

        for (int i = 0; i < subVetor.length; i++) {
            mediaTemp += subVetor[i];
        }
        media = mediaTemp / subVetor.length;

        double varianciaTemp = 0.0, variancia = 0.0;
        for (int i = 0; i < subVetor.length; i++) {
            varianciaTemp += Math.pow(subVetor[i] - media, 2);
        }
        variancia = varianciaTemp / (subVetor.length - 1);

        return variancia;
    }

    public static double[][] MontarMatrixClassificadora(double[][] vetorCorrPicos_Final, int[][] posicoes_pico_Final) {
        double[][] matrix = new double[2 * vetorCorrPicos_Final.length][50];
        double[][] matrixModulo = new double[vetorCorrPicos_Final.length][];

        for (int i = 0; i < matrixModulo.length; i++) {
            matrixModulo[i] = FuncoesUtil.calculaModulo(vetorCorrPicos_Final[i]);
        }
        int index = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < 50; j++) {
                matrix[i][j] = matrixModulo[index][j];
                matrix[i + 1][j] = posicoes_pico_Final[index][j];
            }
            i++;
            index++;
        }
        return matrix;
    }

    public static double[][] montarBase(double[] representador, int[] posicoes) {
        double[][] base = new double[2][50];

        for (int i = 0; i < 50; i++) {
            base[0][i] = representador[i];
        }

        for (int i = 0; i < 50; i++) {
            base[1][i] = posicoes[i];
        }

        return base;
    }

    public static double[] quickSortReverso(double[] arr, int esquerda, int direita) {

        double[] vector = new double[arr.length];
        if (arr == null || arr.length == 0) {
            return null;
        }

        if (esquerda >= direita) {
            return null;
        }

        double pivot = arr[esquerda + (direita - esquerda) / 2];
        int i = esquerda;
        int j = direita;
        double tmp;

        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
        if (esquerda < j) {
            quickSortReverso(arr, esquerda, j);
        }

        if (direita > i) {
            quickSortReverso(arr, i, direita);
        }

        vector = inverter(arr);

        return vector;
    }

    public static double[] inverter(double[] vector) {
        double[] vetorInverso = new double[vector.length];
        int j = vector.length - 1;
        for (int i = 0; i < vetorInverso.length; i++) {
            vetorInverso[i] = vector[j];
            j--;
        }

        return vetorInverso;
    }

    public static double calularMediaAri(double[][] vec_media_arit) {
        double media_Real = 0.0, media_Img = 0.0, somReal = 0.0, somaImg = 0.0, media_modulo = 0.0;
        int tamanho = vec_media_arit.length;
        for (int i = 0; i < vec_media_arit.length; i++) {
            somReal += vec_media_arit[i][0];
            somaImg += vec_media_arit[i][1];
        }
        media_Real = somReal / tamanho;
        media_Img = somaImg / tamanho;

        media_modulo = Math.sqrt((Math.pow(media_Real, 2)) + (Math.pow(media_Img, 2)));

        return media_modulo;
    }

    public static double[] calculaVetorPicos(int[] vetor_pos, double[] vetor_Comp, double numpicos, int tamanhoVetorModulo) {
        int my_first_j, my_last_j;
        double[] vetor_pico = new double[vetor_Comp.length];
        boolean flag;
        int posicao_pico = 0;

        for (int j = 0; j < tamanhoVetorModulo; j++) {

            my_first_j = j * 2;
            my_last_j = my_first_j + 2 - 1;

            flag = false;

            for (int i = 0; i < vetor_pos.length; i++) {
                if (vetor_pos[i] == j && posicao_pico < numpicos) {
                    flag = true;
                }
            }

            if (flag) {
                int index = 2 * vetor_pos[posicao_pico];
                vetor_pico[my_first_j] = vetor_Comp[index];
                vetor_pico[my_last_j] = vetor_Comp[index + 1];
                posicao_pico++;
            }

        }
        return vetor_pico;
    }

    public static int[] posicao(double[] vect, double[] valor_nunpicos, int posicaovector) {

        int[] posicoes_pico = new int[vect.length];
        int tamanho = 0;
        int index = 0;
        int[] vetorAux = null;
        for (int k = 0; k < posicoes_pico.length; k++) {
            BigDecimal bd = new BigDecimal(vect[k]).setScale(12, RoundingMode.HALF_DOWN);
            if (bd.doubleValue() >= valor_nunpicos[posicaovector]) {
                posicoes_pico[k] = k;
                tamanho++;
            } else {
                posicoes_pico[k] = -1;
            }
        }
        vetorAux = new int[tamanho];

        for (int i = 0; i < posicoes_pico.length; i++) {
            if (posicoes_pico[i] >= 0) {
                vetorAux[index] = posicoes_pico[i];
                index++;
            }
        }

        return vetorAux;

    }

    public static double[] calculaModulo(double[] vetorcomplexo) {
        double[] vector_retorno = new double[vetorcomplexo.length / 2];
        double soma_valores = 0.0;
        int my_first_j, my_last_j;
        for (int i = 0; i < vector_retorno.length; i++) {
            my_first_j = i * 2;
            my_last_j = my_first_j + 2 - 1;
            soma_valores = (Math.pow(vetorcomplexo[my_first_j], 2)) + (Math.pow(vetorcomplexo[my_last_j], 2));
            vector_retorno[i] = Math.sqrt(soma_valores);
        }
        return vector_retorno;
    }

    public static double[] autCorrentropyCoef(double[] signal, double kernel) {
        double twokSizeSquare = 2.0 * Math.pow(kernel, 2d);
        int signal_length = signal.length;
        double[] Y = FuncoesUtil.zeros(signal_length);
        double b = 1.0 / (kernel * Math.sqrt(2 * Math.PI));

        for (int m = 0; m < signal_length; m++) {
            for (int n = m + 1; n < signal_length; n++) {
                Y[m] = Y[m] + ((1.0 / (signal_length - m)) * ((b * (Math.exp(-((Math.pow(signal[n] - signal[n - m - 1], 2d)) / twokSizeSquare))))));

            }

        }

        return Y;
    }

    public static double[] fft(double[] arr) {
        DoubleFFT_1D fftDo = new DoubleFFT_1D(arr.length);
        double[] fft = new double[arr.length * 2];
        System.arraycopy(arr, 0, fft, 0, arr.length);
        fftDo.realForwardFull(fft);
        return fft;
    }

    public static double[] fftComplex(double[] arr) {
        DoubleFFT_1D fftDo = new DoubleFFT_1D(arr.length);
        double[] fft = new double[arr.length * 2];
        System.arraycopy(arr, 0, fft, 0, arr.length);
        fftDo.realForwardFull(fft);
        return fft;
    }

    public static double[] mediaMatrix(double[][] arr) {
        int x = arr[0].length;
        int y = arr.length;
        double[] resp = new double[x];
        int counter = 0;
        for (int j = 0; j < x; j++) {
            double sum = 0;
            for (int i = 0; i < y; i++) {
                sum = sum + arr[i][j];
            }
            resp[counter] = sum / y;
            counter++;
        }
        return resp;
    }

    public static int[] mediaMatrixInt(int[][] arr) {
        int x = 50;
        int y = arr.length;
        int[] resp = new int[x];
        int counter = 0;
        for (int j = 0; j < x; j++) {
            int sum = 0;
            for (int i = 0; i < y; i++) {
                sum = sum + arr[i][j];
            }
            resp[counter] = sum / y;
            counter++;
        }
        return resp;
    }

    public static double mediaVector(double[] arr) {
        double resp = 0;
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum = sum + arr[i];
        }
        resp = sum / arr.length;
        return resp;
    }

    public static double[] subArray(double[] arr, int start, int fim) {
        int lenght = fim - start + 1;

        double[] janela = new double[lenght];
        int counter = start;
        for (int i = 0; i < lenght; i++) {
            janela[i] = arr[counter];
            counter++;
        }

        return janela;
    }

    public static double[] wavread(String filename, boolean normalized) throws UnsupportedAudioFileException, IOException {
        int BUFFER_LENGTH = 200;
        AudioFormat audioFormat;
        AudioInputStream inputAIS = AudioSystem.getAudioInputStream(new File(filename));
        audioFormat = inputAIS.getFormat();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int nBufferSize = BUFFER_LENGTH * audioFormat.getFrameSize();

        byte[] abBuffer = new byte[nBufferSize];
        while (true) {
            int nBytesRead = inputAIS.read(abBuffer);
            if (nBytesRead == -1) {
                break;
            }
            baos.write(abBuffer, 0, nBytesRead);
        }
        byte[] abAudioData = baos.toByteArray();
        double[] doubleArray = new double[abAudioData.length / 2];
        int index = 0;

        if (!normalized) {
            for (int j = 0; j < abAudioData.length; j++) {
                doubleArray[index] = ((abAudioData[j] & 0xFF) | (abAudioData[j + 1] << 8));
                j++;
                index++;
            }
            return doubleArray;
        } else {
            double max = ((abAudioData[0] & 0xFF) | (abAudioData[1] << 8));
            double min = ((abAudioData[0] & 0xFF) | (abAudioData[1] << 8));

            for (int j = 0; j < abAudioData.length; j++) {
                doubleArray[index] = ((abAudioData[j] & 0xFF) | (abAudioData[j + 1] << 8));

                if (Math.abs(doubleArray[index]) > Math.abs(max)) {
                    max = Math.abs(doubleArray[index]);
                }
                if (doubleArray[index] < min) {
                    min = doubleArray[index];
                }
                j++;
                index++;

            }

            for (int i = 0; i < doubleArray.length; i++) {
                doubleArray[i] = FuncoesUtil.normalize(doubleArray[i], max);
            }

            return doubleArray;
        }
    }

    public static double normalize(double value, double max) {
        return value / max;
    }

    public static double[] normalizeVector(double[] vector) {
        double max = vector[0];

        for (int i = 0; i < vector.length; i++) {
            if (Math.abs(vector[i]) > Math.abs(max)) {
                max = Math.abs(vector[i]);
            }
        }

        for (int i = 0; i < vector.length; i++) {
            vector[i] = FuncoesUtil.normalize(vector[i], max);
        }
        return vector;
    }

    public static double[][] normalizeMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = FuncoesUtil.normalizeVector(matrix[i]);
        }
        return matrix;
    }

    public static double[] zeros(int size) {
        double[] matrix;
        matrix = new double[size];
        for (int x = 0; x < size; x++) {
            matrix[x] = 0;
        }

        return matrix;
    }

    public static String[] arrayPush(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

    public static String[] listFilesFromFolder(String folderPath, String filetype) {
        ArrayList<String> files = new ArrayList<String>();
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String file;
                file = listOfFiles[i].getAbsolutePath();
                if (filetype == "*" || file.endsWith("." + filetype.toLowerCase()) || file.endsWith("." + filetype.toUpperCase())) {
                    files.add(file);
                }
            }
        }

        String[] arr = files.toArray(new String[files.size()]);
        return arr;
    }

}
