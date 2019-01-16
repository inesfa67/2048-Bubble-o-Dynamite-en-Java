
import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;




import javax.swing.JPanel;



public class Tablero extends JPanel{
    
    //matriz que almacena los valores numéricos del tablero
    private int MatrizTablero[][];
    
    //componentes del GridBagLayout
    private GridBagConstraints gbc = new GridBagConstraints();
    //matriz de botones
    private JButton[][] numeros;
    private JButton[] botones;
    
    private ImageIcon iconoPremio = new ImageIcon(TestTablero.class.getResource("/trofeo.png"));            
    private ImageIcon iconoGameOver = new ImageIcon(TestTablero.class.getResource("/game-over.png")); 
    
    private int generarNumero() {         
        int potencia = (int) (Math.random() * 6) + 1;
        return (int) Math.pow(2, potencia);
    }
    
    private Color colorFondo(int valor){
        switch(valor){
            case 2: return Color.RED;
            case 4: return Color.BLUE;
            case 8: return Color.GREEN;                
            case 16: return Color.GRAY;
            case 32: return Color.BLACK;
            case 64: return Color.YELLOW;
            case 128: return Color.ORANGE;
            case 256: return Color.MAGENTA;
            case 512: return Color.CYAN;
            case 1024: return Color.LIGHT_GRAY;
            case 2048: return Color.PINK;
            default:return Color.WHITE;
        }
    }      
    
    private boolean hay2048() {
        boolean hay2048 = false;
        boolean salir=false;
        for (int i = 0; i < MatrizTablero.length && !salir; i++) {
            for (int j = 0; j < MatrizTablero[0].length && !salir; j++) {
                if (MatrizTablero[i][j] >= 2048){
                    hay2048 = true;
                    salir=true;
                }                        
            }
        }
        return hay2048;
    }
    
    
     private boolean tableroLleno(){
        boolean lleno=true;
        boolean salir=false;
        for (int i=0; i<MatrizTablero.length && !salir;i++){
            for (int j=0; j<MatrizTablero[0].length && !salir; j++){
                if (MatrizTablero[i][j]== 0){
                    lleno=false;
                    salir=true;
                }                
            }
        }
        return lleno;
    }
     
    private void inicializaComponentes(){        
        for (int i=0;i<MatrizTablero[0].length+2;i++){
           for(int j=0;j<MatrizTablero[0].length;j++) {
                numeros[i][j]=new JButton((MatrizTablero[i][j])!=0?Integer.toString(MatrizTablero[i][j]):"");                
                gbc.gridx=j;
                gbc.gridy=i;
                gbc.gridwidth=1;
                gbc.gridheight=1;
                gbc.weightx=1.0;
                gbc.weighty=1.0;            
                gbc.fill=GridBagConstraints.BOTH;                                
                this.add(numeros[i][j],gbc);                    
            }
        }
        int numero=generarNumero();
        System.out.println("Ha salido el numero "+ numero);
        for (int i=0; i<MatrizTablero[0].length;i++){
            botones[i]=new JButton("");            
            botones[i].addActionListener(listener);
            gbc.gridx=i;
            gbc.gridy=MatrizTablero[0].length+2;
            gbc.gridwidth=1;
            gbc.gridheight=1;
            gbc.weightx=1.0;
            gbc.weighty=1.0;
            gbc.fill=GridBagConstraints.BOTH;          
            gbc.anchor = GridBagConstraints.PAGE_END;
            this.add(botones[i],gbc);  
        } 
        botones[(MatrizTablero[0].length-1)/2].setFont(new Font("Arial", Font.BOLD, 25));
        botones[(MatrizTablero[0].length-1)/2].setForeground(Color.WHITE);
        botones[(MatrizTablero[0].length-1)/2].setBackground(colorFondo(numero));
        botones[(MatrizTablero[0].length-1)/2].setText(Integer.toString(numero));        
    }
    
    private void actualizarNumeros(){        
        for (int i=0;i<MatrizTablero[0].length+2;i++){
           for(int j=0;j<MatrizTablero[0].length;j++) {                              
               numeros[i][j].setFont(new Font("Arial", Font.BOLD, 25));
               numeros[i][j].setForeground(Color.WHITE);
               numeros[i][j].setBackground(colorFondo(MatrizTablero[i][j]));
               numeros[i][j].setText((this.MatrizTablero[i][j])!=0?Integer.toString(this.MatrizTablero[i][j]):"");
            }
        }              
    }
    
    private void actualizarBotones(){
        int numero=generarNumero(); 
        System.out.println("Ha salido el numero "+ numero); 
        for (int i=0; i<MatrizTablero[0].length;i++){
            botones[i].setEnabled(true);
        }
        botones[(MatrizTablero[0].length-1)/2].setBackground(colorFondo(numero));
        botones[(MatrizTablero[0].length-1)/2].setText(Integer.toString(numero));           
    }
    
    private ActionListener listener= new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {            
            int i=0;
            boolean salir=false;
            if (hay2048()==false && tableroLleno()==false){
                for (i=0;i<MatrizTablero.length&&(salir==false);i++){                
                if(ae.getSource().equals(botones[i]))
                        salir=true;                        
                }
                //i es la columna donde debemos colocar el número 
                System.out.println("Vas a colocarlo en la columna "+ (i));
                for (int j=0; j<MatrizTablero[0].length;j++){
                    botones[j].setEnabled(false);
                }
                hacerJugada(i-1);
            }else{
                if(hay2048())
                    JOptionPane.showMessageDialog(null,"FELICIDADES!! HAS GANADO!!","",JOptionPane.INFORMATION_MESSAGE,iconoPremio);
                else{
                    JOptionPane.showMessageDialog(null,"Lo sentimos! Has perdido... \n Vuelve a intentarlo","",JOptionPane.PLAIN_MESSAGE,iconoGameOver);
                    System.exit( 0 );
                }
            }
            
        }                                
    };
    
    
    //contructor con número de columnas c
    public Tablero(int c){
        super();
        this.MatrizTablero = new int[c+2][c];
        this.setLayout(new GridBagLayout()); 
        this.numeros= new JButton[c+2][c];
        this.botones= new JButton[c];
        inicializaComponentes();   
    }

    private void imprimeEncabezado() {
        for (int i = 0; i < MatrizTablero[0].length; i++) {
                System.out.printf(" %4d ", i+1);
        }
        System.out.println();
        System.out.print("---------");
        for (int i = 0; i < MatrizTablero[0].length; i++) {
                System.out.print("-----");
        }
        System.out.println();
    }
    
    private void imprimeTablero() {
        for (int i=0; i<MatrizTablero.length; i++) {
                for (int j=0; j < MatrizTablero[i].length; j++)
                        if (MatrizTablero[i][j] == 0)
                                System.out.print("      ");
                        else
                                System.out.printf(" %4d ",MatrizTablero[i][j]);
                System.out.println();
        }
    }
    
    public void imprimeTodo() {
            imprimeEncabezado();
            imprimeTablero();
    }        
        
        
    private boolean columnaLlena(int c) {		
        boolean columnaLlena = true;
        boolean salir=false;			
        for (int i = 0; i < this.MatrizTablero.length && !salir; i++) {
            if (this.MatrizTablero[i][c] == 0)
            {
                columnaLlena = false;
                salir=true;
            }
        }		
        return columnaLlena;
    }
	
    private void hacerJugada(int c) {

        if (columnaLlena(c)==false){
            int i = 0;
            while (MatrizTablero[i][c] != 0) {
                i++;
            }
            MatrizTablero[i][c] = Integer.parseInt(botones[(MatrizTablero[0].length-1)/2].getText());
            imprimeTodo();          
            actualizarNumeros();            
            hacerFusiones(i, c);
            actualizarBotones();
        }else{
            JOptionPane.showMessageDialog(null,"Lo sentimos! Has perdido... \n Vuelve a intentarlo","",JOptionPane.PLAIN_MESSAGE,iconoGameOver);
            System.exit( 0 ); 
        }
    }

        	
    private void hacerFusiones(int f, int c) { 
        //fusion total
        if ((c!=0)&&(c<MatrizTablero[0].length-1)&&(f!=0)&&(MatrizTablero[f][c]==MatrizTablero[f-1][c])&&((MatrizTablero[f][c]==MatrizTablero[f][c-1])&&((MatrizTablero[f][c]==MatrizTablero[f][c+1])))) {
            MatrizTablero[f-1][c]=MatrizTablero[f][c]*8;            
            for (int i=f; i< MatrizTablero.length-1; i++){
                MatrizTablero[i][c-1]= MatrizTablero[i+1][c-1];
                MatrizTablero[i][c]= MatrizTablero[i+1][c];
                MatrizTablero[i][c+1]= MatrizTablero[i+1][c+1];
            }
            MatrizTablero[MatrizTablero.length-1][c-1]=0;
            MatrizTablero[MatrizTablero.length-1][c]=0;
            MatrizTablero[MatrizTablero.length-1][c+1]=0;
            imprimeTodo();
            actualizarNumeros();               
            hacerFusiones(f-1,c);
            if (MatrizTablero[f][c-1]!=0)
                hacerFusiones(f,c-1);
            if (MatrizTablero[f][c]!=0)
                hacerFusiones(f,c);
            if (MatrizTablero[f][c+1]!=0)
                hacerFusiones(f,c+1);  
        }        
        //fusion tres numeros uno arriba y izd
         else if ((c!=0)&&(f!=0)&&(MatrizTablero[f][c]==MatrizTablero[f-1][c])&&((MatrizTablero[f][c]==MatrizTablero[f][c-1]))) {
            MatrizTablero[f-1][c]=MatrizTablero[f][c]*4;            
            for (int i=f; i< MatrizTablero.length-1; i++){
                MatrizTablero[i][c-1]= MatrizTablero[i+1][c-1];
                MatrizTablero[i][c]= MatrizTablero[i+1][c];               
            }
            MatrizTablero[MatrizTablero.length-1][c-1]=0;            
            MatrizTablero[MatrizTablero.length-1][c]=0;
            imprimeTodo();
            actualizarNumeros();                        
            hacerFusiones(f-1,c);
            if (MatrizTablero[f][c-1]!=0)
                hacerFusiones(f,c-1);          
            if (MatrizTablero[f][c]!=0)
                hacerFusiones(f,c);           
        }
        //fusion tres numeros arriba derecha
        else if((c<MatrizTablero[0].length-1)&&(f!=0)&&(MatrizTablero[f][c]==MatrizTablero[f-1][c])&&((MatrizTablero[f][c]==MatrizTablero[f][c+1]))) {
            MatrizTablero[f-1][c]=MatrizTablero[f][c]*4;          
            for (int i=f; i< MatrizTablero.length-1; i++){                
                MatrizTablero[i][c]= MatrizTablero[i+1][c];
                MatrizTablero[i][c+1]= MatrizTablero[i+1][c+1];
            }            
            MatrizTablero[MatrizTablero.length-1][c]=0;
            MatrizTablero[MatrizTablero.length-1][c+1]=0;            
            imprimeTodo();
            actualizarNumeros();
            hacerFusiones(f-1,c);
            if (MatrizTablero[f][c]!=0)
                hacerFusiones(f,c);
            if (MatrizTablero[f][c+1]!=0)
                hacerFusiones(f,c+1);  
        }
        //fusion tres numeros misma fila
        else if((c!=0)&&(c<MatrizTablero[0].length-1)&&((MatrizTablero[f][c]==MatrizTablero[f][c-1])&&((MatrizTablero[f][c]==MatrizTablero[f][c+1])))) {
            MatrizTablero[f][c]=MatrizTablero[f][c]*4;          
            for (int i=f; i< MatrizTablero.length-1; i++){  
                MatrizTablero[i][c-1]= MatrizTablero[i+1][c-1];                
                MatrizTablero[i][c+1]= MatrizTablero[i+1][c+1];
            }                        
            MatrizTablero[MatrizTablero.length-1][c-1]=0;            
            MatrizTablero[MatrizTablero.length-1][c+1]=0;
            imprimeTodo();
            actualizarNumeros();
            if (MatrizTablero[f][c-1]!=0)
                hacerFusiones(f,c-1);          
            if (MatrizTablero[f][c]!=0)
				hacerFusiones(f,c);
            if (MatrizTablero[f][c+1]!=0)
            hacerFusiones(f,c+1);                      
        }
        //fusion izd dos numeros
       else if((c!=0)&&(MatrizTablero[f][c]==MatrizTablero[f][c-1])){
            MatrizTablero[f][c]=MatrizTablero[f][c]*2;          
            for (int i=f; i< MatrizTablero.length-1; i++){  
                MatrizTablero[i][c-1]= MatrizTablero[i+1][c-1];                               
            }                        
            MatrizTablero[MatrizTablero.length-1][c-1]=0;                        
            imprimeTodo();
            actualizarNumeros();
            if (MatrizTablero[f][c-1]!=0)
                hacerFusiones(f,c-1);
            if (MatrizTablero[f][c]!=0)
		hacerFusiones(f,c);            
        }
        //fusion derecha dos numeros
        else if((c<MatrizTablero[0].length-1)&&(MatrizTablero[f][c]==MatrizTablero[f][c+1])){
            MatrizTablero[f][c]=MatrizTablero[f][c]*2;          
            for (int i=f; i< MatrizTablero.length-1; i++){  
                MatrizTablero[i][c+1]= MatrizTablero[i+1][c+1];                               
            }                        
            MatrizTablero[MatrizTablero.length-1][c+1]=0;                        
            imprimeTodo();
            actualizarNumeros();                       
            hacerFusiones(f,c);
            if (MatrizTablero[f][c+1]!=0)
                hacerFusiones(f,c+1);                  
        }
        //fusion arriba dos numeros
        else if((f!=0)&&(MatrizTablero[f][c]==MatrizTablero[f-1][c])) {
            MatrizTablero[f-1][c]=MatrizTablero[f][c]*2;          
            for (int i=f; i< MatrizTablero.length-1; i++){  
                MatrizTablero[i][c]= MatrizTablero[i+1][c];                               
            }                        
            MatrizTablero[MatrizTablero.length-1][c]=0;                        
            imprimeTodo();
            actualizarNumeros();                        
            hacerFusiones(f-1,c);
            if (MatrizTablero[f][c]!=0)
                hacerFusiones(f,c);                  
        }
    }
   
    
}
