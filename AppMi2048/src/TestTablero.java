import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import static java.lang.System.exit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;




public class TestTablero {       
    public static void main(String[] args) {                            
        int columnas=0;
        String responde=JOptionPane.showInputDialog("Con cuántas columnas quieres jugar?");
        if (responde!=null){
            if (responde.equals(""))
                System.exit(0);                     
            else
                columnas=Integer.parseInt(responde);
        }
        else
           System.exit(0);    
        Tablero t= new Tablero(columnas);
        t.imprimeTodo();
        JFrame app=new JFrame("Mi2048");                          
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        app.setSize(500, 700);//ventana de 500*700 pixeles
        app.setLocationRelativeTo(null);//ventana en el centro de la pantalla
        //app.setResizable(false);//el usuario no puede cambiar el tamaño de la ventana                                               
        app.add(t);             
        app.setVisible(true);
    }
}
