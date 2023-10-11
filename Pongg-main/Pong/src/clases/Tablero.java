/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Alumno
 */
public class Tablero extends JPanel {
    
    Balon balon = new Balon(0, 0);
    Paleta p1 = new Paleta(350,415);
    private int puntaje = 0;
    private boolean juegoEnCurso = true;
    private int contadorToques = 0;
    private int toquesParaAumentarVelocidad = 5, vidas = 3;
	private Image fondo;
	
    public Tablero() {
        setBackground(Color.BLACK);
        try {
            ImageIcon imgfondo = new ImageIcon(Ventana.class.getResource("/imagen/cancha.jpg"));
            fondo = imgfondo.getImage();
  
            

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void resetgame() {
 	  balon.reset();
      p1.resetpaleta();
 	  puntaje=0;
 	  juegoEnCurso = true;
 	  contadorToques = 0;
      toquesParaAumentarVelocidad = 5;
      vidas = 3;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        dibujar(g2);
        actualizar();
    }
    public void dibujar(Graphics2D g)
    {
        g.fill(balon.getBalon());
        g.fill(p1.getPaleta());
        g.drawString("Puntaje: " + puntaje, 10, 20);
        g.drawString("Vidas: " + vidas, 700, 20);

    }
    public void actualizar()
    {
        balon.mover(getBounds(), colision(p1.getPaleta()));
        p1.mover_raqueta(getBounds());
        if (colision(p1.getPaleta())) {
            balon.mover(getBounds(), true);
            puntaje++;
            contadorToques++;
        }
        if (contadorToques >= toquesParaAumentarVelocidad) {
            balon.aumentarVelocidad();
            contadorToques = 0;
        }
        if (balon.getBalon().getMaxY() >= getHeight()) {
            juegoEnCurso = false;
            balon = new Balon(150, 0);
            vidas = vidas - 1;
            if(vidas == 0)
            {
                JOptionPane.showMessageDialog(this, "¡Juego perdido!\nPuntaje: " + puntaje, "Game Over", JOptionPane.INFORMATION_MESSAGE);
             resetgame();
            }
}
    }
    public void iterar()
    {
    	while(juegoEnCurso)
    	{
    		try {
        		repaint();
                Thread.sleep(6);
            } catch (InterruptedException ex) {
                Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
            }
    	}
    }
    private boolean colision(Rectangle2D r)
    {
    	return balon.getBalon().intersects(r);
    }
}

