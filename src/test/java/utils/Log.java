package utils;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {

    public void iniciarLog() throws IOException {
        // Texto que irá ser gravado no cabeçalho do arquivo
        String[] cabecalho = {"dataHora", "tipo", "mensagem", "duracao"};

        // Definição da data e hora da criação do arquivo de log para colocar no seu nome
        String dataHoraInicial = new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime());
        System.out.println("Data e hora: " + dataHoraInicial);
        Writer writer = Files.newBufferedWriter(Paths.get("target/logs/userDD - " + dataHoraInicial +".csv"));

        // Prepara a escrita no arquivo
        CSVWriter csvWriter = new CSVWriter(writer);

        // Escreve para o arquivo
        csvWriter.writeNext(cabecalho,false);

        // Salve no arquivo
        csvWriter.flush();

        // Fechar o arquivo
        csvWriter.close();
        writer.close();


    }

    public void logar(String dataHora, String tipo, String mensagem, int duracao){

    }

    public void finalizarLog(){

    }
}
