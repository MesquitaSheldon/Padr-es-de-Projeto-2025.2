package br.ifba.edu.br.questao2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogArquivo {
    
	private static final String CAMINHO_ARQUIVO = "src/log_operacoes.txt";

    public static void salvar(String mensagem) {
        try (FileWriter fw = new FileWriter(CAMINHO_ARQUIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(mensagem);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
