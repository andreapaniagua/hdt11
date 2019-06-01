import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main {
    public static Integer[] encontrarCentro(Integer[][] matrix){
        Integer currentCenter = 1000;
        Integer[] currentCoordinates = new Integer[2];

        for (int i = 0; i < matrix.length; i++){
            Integer currentBiggest = 0;
            Integer[] cBCoordinates = new Integer[2];
            for (int j = 0; j <matrix.length; j++){
                if (matrix[j][i] > currentBiggest){
                    currentBiggest = matrix[j][i];
                    cBCoordinates[0] = j;
                    cBCoordinates[1] = i;
                }
            }
            if (currentBiggest < currentCenter){
                currentCenter = currentBiggest;
                currentCoordinates = cBCoordinates;
            }
        }
        return currentCoordinates;
    }
    public static ArrayList<Integer> encontrarRuta(Integer[][] sequence, Integer origin, Integer destiny){
        ArrayList<Integer> allSteps = new ArrayList<>();
        Integer nextOut = origin;
        while (!sequence[nextOut][destiny].equals(destiny)){
            allSteps.add(sequence[nextOut][destiny]);
            nextOut = sequence[nextOut][destiny];
        }
        allSteps.add(sequence[nextOut][destiny]);
        return allSteps;
    }
    public static String encontrarNombre(HashMap<String, Integer> map, Integer toBeChecked){
        //codigo tomado de: https://www.geeksforgeeks.org/iterate-map-java/
        for (Map.Entry<String, Integer> entry: map.entrySet()){
            if (entry.getValue().equals(toBeChecked)){
                return entry.getKey();
            }
        }
        return "lol";
    }

    public static void main (String[]args) {
        String toSplit = "";
        String[] parts = new String[3];
        ArrayList<Ruta> routes = new ArrayList<>();
        HashMap<String, Integer> cities = new HashMap<>();
        Integer current = 0;
        Integer[][] distancesMatrix;
        Integer[][] sequenceMatrix;
        boolean wantsToContinue = true;

        try {
            File file = new File("guategrafo.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                toSplit = st;
                parts = toSplit.split(",");
                routes.add(new Ruta(parts[0], parts[1], Integer.valueOf(parts[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.print("No se encontró el archivo");
        } catch (IOException e) {
            System.out.println("Algo más salió maln favor intentar nuevamente");
        }

        for (Ruta r: routes) {
            if (!cities.containsKey(r.getFuente())) {
                cities.put(r.getFuente(), current);
                current++;
            }
            if (!cities.containsKey(r.getDestino())) {
                cities.put(r.getDestino(), current);
                current++;
            }
        }

        distancesMatrix = new Integer[cities.size()][cities.size()];



        //distnacias
        for (Ruta r : routes) {
            distancesMatrix[cities.get(r.getFuente())][cities.get(r.getDestino())] = r.getDistancia();
        }

        for (int i = 0; i < distancesMatrix.length; i++) {
            for (int j = 0; j < distancesMatrix.length; j++) {
                if (distancesMatrix[i][j] == null && i != j) {
                    distancesMatrix[i][j] = 99999;
                }
                else if (distancesMatrix[i][j] == null && i == j){
                    distancesMatrix[i][j] = 0;
                }
            }
        }

        FloydNew floyd = new FloydNew(distancesMatrix.length);
        Integer [][] result = floyd.floydWarshall(distancesMatrix);
        Integer [][] sequence = floyd.getSequence();

        floyd.printSolution(result);
        do {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object

            System.out.println("Ingrese la opción que desee ejecutar");
            System.out.println("1. Encontrar distancia entre dos ciudades");
            System.out.println("2. Encontrar centro del grafo");
            System.out.println("3. Ingresar interrupción de tráfico");
            System.out.println("4. Ingresar nueva distancia entre rutas");
            System.out.println("5. Salir");

            String nextOption = myObj.nextLine();

            switch(nextOption){
                case "1":
                    System.out.println("Favor ingrese su ciudad de salida");

                    String origin = myObj.nextLine();

                    System.out.println("Favor ingrese su ciudad de destino");

                    String destiny = myObj.nextLine();
                    try{
                        System.out.println("La ruta más corta es de: " + result[cities.get(origin)][cities.get(destiny)]);

                        System.out.println("La ruta sale de: " + origin);

                        for (Integer i: encontrarRuta(sequence,cities.get(origin), cities.get(destiny))){
                            System.out.println("Pasa por " + encontrarNombre(cities, i));
                        }
                        System.out.println("Y termina en " + destiny);
                    }catch (NullPointerException e){
                        System.out.println("Las ciudades ingresadas no existen, favor intentar nuevamente");
                    }
                    break;
                case "2":
                    System.out.println("El centro de su grafo es " + encontrarNombre(cities,encontrarCentro(distancesMatrix)[1]));
                    break;
                case "3":
                    //interrupción de tráfico
                    System.out.println("Favor ingrese su ciudad de salida de la ruta interrumpida");

                    String originInterrupted = myObj.nextLine();

                    System.out.println("Favor ingrese su ciudad de destino de la ruta interrumpida");

                    String destinyInterrupted = myObj.nextLine();
                    try{
                        distancesMatrix[cities.get(originInterrupted)][cities.get(destinyInterrupted)] = 99999;
                        result = floyd.floydWarshall(distancesMatrix);
                    }catch (NullPointerException e){
                        System.out.println("Las ciudades ingresadas no existen, favor intentar nuevamente");
                    }
                    break;
                case "4":
                    System.out.println("Favor ingrese su ciudad de salida de la ruta cambiada");

                    String originChanged = myObj.nextLine();

                    System.out.println("Favor ingrese su ciudad de destino de la ruta cambiada");

                    String destinyChanged = myObj.nextLine();

                    System.out.println("Favor ingrese la nueva distancia");

                    String newLength = myObj.nextLine();
                    try{
                        distancesMatrix[cities.get(originChanged)][cities.get(destinyChanged)] = Integer.valueOf(newLength);
                        result = floyd.floydWarshall(distancesMatrix);
                        floyd.printSolution(result);
                    }catch (NullPointerException e){
                        System.out.println("Las ciudades ingresadas no existen, favor intentar nuevamente");
                    }catch (NumberFormatException e){
                        System.out.println("Distancia no válida");
                    }
                    break;
                case "5":
                    wantsToContinue = false;
                    break;
                default:
                    System.out.println("Ingreso no válido, favor intentar nuevamente");
            }

        }while (wantsToContinue);
    }
}
