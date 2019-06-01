import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Juan Diego Solorzano y Andrea Paniagua
//Extraido de: https://www.geeksforgeeks.org/iterate-map-java/

public class Main {
    public static Integer[] encontrarCentro(Integer[][] matrix){
        Integer cCenter = 1000;
        Integer[] cCoordinates = new Integer[2];

        for (int i = 0; i < matrix.length; i++){
            Integer cBiggest = 0;
            Integer[] Cordenadas = new Integer[2];
            for (int j = 0; j <matrix.length; j++){
                if (matrix[j][i] > cBiggest){
                    cBiggest = matrix[j][i];
                    Cordenadas[0] = j;
                    Cordenadas[1] = i;
                }
            }
            if (cBiggest < cCenter){
                cCenter = cBiggest;
                cCoordinates = Cordenadas;
            }
        }
        return cCoordinates;
    }
    public static ArrayList<Integer> encontrarRuta(Integer[][] seq, Integer actual, Integer destino){
        ArrayList<Integer> res = new ArrayList<>();
        Integer next = actual;
        while (!seq[next][destino].equals(destino)){
            res.add(seq[next][destino]);
            next = seq[next][destino];
        }
        res.add(seq[next][destino]);
        return res;
    }
    public static String encontrarNombre(HashMap<String, Integer> map, Integer toBeChecked){
        for (Map.Entry<String, Integer> entry: map.entrySet()){
            if (entry.getValue().equals(toBeChecked)){
                return entry.getKey();
            }
        }
        String err = "No se encontro";
        return err;
    }

    public static void main (String[]args) {


        ArrayList<Ruta> ruta = new ArrayList<>();
        HashMap<String, Integer> lugares = new HashMap<>();
        Integer current = 0;

        Integer[][] distancesMatrix;

        boolean wantsToContinue = true;

        try {
            String[] valores = new String[3];
            File file = new File("guategrafo.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            String s = "";
            while ((st = br.readLine()) != null) {
                s = st;
                valores = s.split(",");
                ruta.add(new Ruta(valores[0], valores[1], Integer.valueOf(valores[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.print("No existe el archivo");
        } catch (IOException e) {
            System.out.println("Error");
        }

        for (Ruta r : ruta) {
            if (!lugares.containsKey(r.getFuente())) {
                lugares.put(r.getFuente(), current);
                current++;
            }
            if (!lugares.containsKey(r.getDestino())) {
                lugares.put(r.getDestino(), current);
                current++;
            }
        }
        distancesMatrix = new Integer[lugares.size()][lugares.size()];
        for (Ruta r : ruta) {
            distancesMatrix[lugares.get(r.getFuente())][lugares.get(r.getDestino())] = r.getDistancia();
        }

        for (int i = 0; i < distancesMatrix.length; i++) {
            for (int j = 0; j < distancesMatrix.length; j++) {
                if (distancesMatrix[i][j] == null && i != j) {
                    distancesMatrix[i][j] = 99999;
                } else if (distancesMatrix[i][j] == null && i == j) {
                    distancesMatrix[i][j] = 0;
                }
            }
        }

        FloydNew floyd = new FloydNew(distancesMatrix.length);
        Integer[][] resultado = floyd.floydWarshall(distancesMatrix);
        Integer[][] secuencia = floyd.getSequence();

        floyd.printSolution(resultado);
        Scanner sc = new Scanner(System.in);
        //Menu
        int opc = 0;
        while (opc != 5) {
            System.out.println("Menu: \n"
                    + "1. Distancia entre dos ciudades ingresadas \n"
                    + "2. Encontrar el centro de grafo \n"
                    + "3. Nueva relacion entre dos ciudades \n"
                    + "4. Agregar interrupcion de trafico entre dos ciudades \n"
                    + "5. Salir \n"

            );
            opc = sc.nextInt();
            if (opc > 5 || opc < 1) {
                System.out.println("Valor invalido, intente de nuevo");
            } else {
                if (opc == 1) {
                    System.out.println("Nombre de ciudad origen");
                    String opc2 = sc.nextLine();
                    System.out.println("Nombre de ciudad destino");
                    String opc3 = sc.nextLine();

                    int res = resultado[lugares.get(opc2)][lugares.get(opc3)];
                    System.out.println("La distancia entre ambas ciudades es de " + res + "Km \n");
                } else if (opc == 2) {
                    String centro = encontrarNombre(lugares, encontrarCentro(distancesMatrix)[1]);
                    System.out.println("El centro es: " + centro + "\n");
                } else if (opc == 3) {
                    System.out.println("Ingrese la ciudad origen");
                    String origen = sc.nextLine();

                    System.out.println("Ingrese la ciudad destino");
                    String destino = sc.nextLine();

                    System.out.println("Ingrese la distancia entre ambas ciudades");

                    int distancia = sc.nextInt();
                    distancesMatrix[lugares.get(origen)][lugares.get(destino)] = Integer.valueOf(distancia);
                    resultado = floyd.floydWarshall(distancesMatrix);
                    floyd.printSolution(resultado);

                } else if (opc == 4) {
                    System.out.println("Ingrese la ciudad origen del trafico");
                    String orig = sc.nextLine();

                    System.out.println("Ingrese la ciudad destino");
                    String dest = sc.nextLine();

                    distancesMatrix[lugares.get(orig)][lugares.get(dest)] = 99999;
                    resultado = floyd.floydWarshall(distancesMatrix);


                }
            }
        }
        System.out.println("Gracias por utilizar el programa");
    }
}
