//Juan Diego Solorzano y Andrea Paniagua
public class Ruta {
    private String destino;
    private String fuente;
    private Integer distancia;
    /*Esta clase guarda las rutas*/
    public Ruta(String destino,String fuente, Integer distancia){
        this.destino = destino;
        this.fuente = fuente;
        this.distancia = distancia;
    }

    public String getDestino() {
        return destino;
    }

    public String getFuente() {
        return fuente;
    }

    public Integer getDistancia() {
        return distancia;
    }
}
