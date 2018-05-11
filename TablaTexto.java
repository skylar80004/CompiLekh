

package Compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public  class TablaTexto{
    
    
    
    
    public  int[][] matrizTransiciones; 
    
    
    public TablaTexto() throws IOException{
        
        this.matrizTransiciones = this.obtener_tabla_transiciones();
        
        
        
    }
    
    
    public  int getCelda(int fila, int columna ){ 
        
            
            return this.matrizTransiciones[fila][columna];
        
      
        
    }
    
    
    public int[][] obtener_tabla_transiciones() throws IOException{
        int[][] resultado = new int[472][71];
        try (
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\josed\\OneDrive\\Documents\\NetBeansProjects\\Compilador\\src\\compilador\\testfile2.txt"))) {
        String line;
        int columna_actual = 0;
        int fila_actual = 0;
        while ((line = br.readLine()) != null) {
            String palabra = "";
        
           for(int i = 0; i < line.length(); i++){
               if(line.charAt(i) == '{' || line.charAt(i) == '}' || line.charAt(i) == ' '){
                   
               }else if(line.charAt(i) == ','){
                   resultado[fila_actual][columna_actual] = Integer.parseInt(palabra);
                   columna_actual++;
                   palabra = "";
               }else if(i+1 == line.length()){
                   resultado[fila_actual][columna_actual] = Integer.parseInt(palabra);
               }
               else{
                   palabra += line.charAt(i);
               }
           }
           //System.out.println(columna_actual);
           fila_actual++;
           columna_actual = 0;
        
        }       

        }
        return resultado;
   }
    
    

    
    
}

