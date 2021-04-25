/**
 * INF1416 - Segurança da Informação - Trabalho 3: DigestCalculator
 * Prof.: Anderson Oliveira da Silva 
 * 
 * Gustavo Barros Marchesan - 1521500
 * Daniela Brazão Maksoud - 2111121
 * 
 * javac DigestCalculator.java
 * java DigestCalculator <tipo_digest> <path_arq_lista> <path_pasta>
 * 
 * java DigestCalculator MD5 ./arquivo_teste.txt ./arquivos/
 * 
 */

import java.security.*;
import javax.crypto.*;
import java.util.*;
import java.io.*;

public class DigestCalculator {
    private static DigestCalculator digestCalc = new DigestCalculator();
    private MessageDigest messageDigest;
    private String path_arq_lista;
    private String path_pasta;

    private ArrayList<String> arrData = new ArrayList<String>();
    private ArrayList<String> arrFile = new ArrayList<String>();

    public static DigestCalculator getInstance(String tipo_digest, String path_arq_lista, String path_pasta) throws NoSuchAlgorithmException {
        digestCalc.messageDigest = MessageDigest.getInstance(tipo_digest);
        digestCalc.path_arq_lista = path_arq_lista;
        digestCalc.path_pasta = path_pasta;

        digestCalc.readFile();
        digestCalc.setArrFile();
        return digestCalc;
    }

    /*public void setPath(String ) {
        path_arq_lista = new File("");
        System.out.println(path_arq_lista.getAbsolutePath());
    }*/

    /*public void readFile () {
        InputStream in = FileLoader.class.getResourceAsStream(digestCalc.messageDigest);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void readFile() {
        try {
        File myObj = new File(path_arq_lista);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            // System.out.println(data);
            arrData.add(data);
        }
        myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo.");
            e.printStackTrace();
        }
    }

    private void setArrFile() {
        File folder = new File(path_pasta);
        for(File file : folder.listFiles()) {
            arrFile.add(File);
        }
    }

    public void writeFile(String outputFile) {
        try {
            FileWriter myWriter = new FileWriter(outputFile);
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // java DigestCalculator MD5 ./Lista_arquivos.txt ./arquivos/
    public static void main (String[] args) throws Exception {
        // Verifica args e recebe o texto plano
        if (args.length != 3) {
            System.err.println("Usage: java DigestCalculator <tipo_digest> <path_arq_lista> <path_pasta>");
            System.exit(1);
        }

        String Tipo_Digest = args[0];
        String Caminho_ArqListaDigest = args[1];
        String Caminho_da_Pasta_dos_Arquivos = args[2];

        DigestCalculator digestCalc = DigestCalculator.getInstance(Tipo_Digest, Caminho_ArqListaDigest, Caminho_da_Pasta_dos_Arquivos);

        System.out.println( "CODE END" );

    }
}