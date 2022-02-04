import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Listo extends SimpleFileVisitor<Path> {


    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String name = file.toAbsolutePath().toString();

        FileReader fi = null;
        Set<String> Stop1 = new HashSet<String>(Arrays.asList("stop1.txt"));
        Set<String> Stop2 = new HashSet<String>(Arrays.asList("stop2.txt"));
        Set<String> Stop3= new HashSet<String>(Arrays.asList("stop3.txt"));
        Set<String> unicas = new HashSet<String>();
        Set<String> duplicadas = new HashSet<String>();


        int r=0;


        try {
            fi = new FileReader(name);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }


        BufferedReader in = new BufferedReader(new FileReader(name));


        String linea = null;

        if (name.toLowerCase().endsWith(".txt")) {



            String delimiters = "\\s+|,\\s*|\\.\\s*|\\;\\s*|\\:\\s*|\\!\\s*|\\¡\\s*|\\¿\\s*|\\?\\s*|\\-\\s*"
                    + "|\\[\\s*|\\]\\s*|\\(\\s*|\\)\\s*|\\\"\\s*|\\_\\s*|\\%\\s*|\\+\\s*|\\/\\s*|\\#\\s*|\\$\\s*";


            // Lista con todas las palabras diferentes
            ArrayList<String> list = new ArrayList<String>();

            // Tiempo inicial
            long startTime = System.currentTimeMillis();

            try {
                while ((linea = in.readLine()) != null) {



                    if (linea.trim().length() == 0) {
                        continue; // la linea esta vacia, continuar
                    }

                    // separar las palabras en cada linea

                    String words[] = linea.split(delimiters);


                    for (String theWord : words) {

                        if (!Stop1.contains(theWord)) {
                            if (!unicas.add(theWord)){
                                duplicadas.add(theWord);
                                r=r+1;}
                            unicas.removeAll(duplicadas);
                        }
                        if (!Stop2.contains(theWord)) {
                            if (!unicas.add(theWord)){
                                duplicadas.add(theWord);
                                r=r+2;}
                            unicas.removeAll(duplicadas);
                        }
                        if (!Stop3.contains(theWord)) {
                            if (!unicas.add(theWord)){
                                duplicadas.add(theWord);
                                r=r+3;}
                            unicas.removeAll(duplicadas);
                        }

                    }

                }

                System.out.printf("En el directorio %-50s \n", name);

                if (r<130) {
                    System.out.println("Es poco probable que este mensaje sea un intento de robo de identidad");

                }
                if (r>130&&r<190) {
                    System.out.println("Este mensaje tiene probabidiades de ser un intento de robo de identidad ");
                }
if (r>190){
    System.out.println("Es muy  probable que este mensaje sea un intento de robo de identidad ");
}


            } catch(IOException ex){
                System.out.println(ex.getMessage());

            }
        }
        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.printf("No se puede procesar:%30s%n", file.toString()) ;
        return super.visitFileFailed(file, exc);
    }

    public static void main(String[] args)
            throws IOException {

        // /Users/rnavarro/datos
        if (args.length < 1) {
            System.exit(2);
        }

        // iniciar en este directorio
        Path startingDir = Paths.get(args[0]);

        // clase para procesar los archivos
        Listo contadorLineas = new  Listo();

        // iniciar el recorrido de los archivos
        Files.walkFileTree(startingDir, contadorLineas);

    }}


