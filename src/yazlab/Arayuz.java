package yazlab;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class Arayuz {
    JFrame frame = new JFrame();
    javax.swing.JTextPane anaSunucuKutu = new javax.swing.JTextPane();
    javax.swing.JProgressBar anaSunucuProgress = new javax.swing.JProgressBar();
    javax.swing.JTextPane[] altSunucularKutu = new javax.swing.JTextPane[16];
    javax.swing.JProgressBar[] altSunucularProgress = new javax.swing.JProgressBar[16];
    javax.swing.JTextPane threadTakip = new javax.swing.JTextPane();
    javax.swing.JLabel threadSayisi = new javax.swing.JLabel();
    
    public Arayuz() {
        //çerçeveyi başlat
        
        frame.setSize(950, 700);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //AnaSunucuKutu
        anaSunucuKutu.setBounds(225,10,150,100);
        anaSunucuKutu.setBackground(new Color(223, 245, 245));
        anaSunucuKutu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        anaSunucuKutu.setEnabled(false);
        anaSunucuKutu.setDisabledTextColor(new Color(51,51,51));
        frame.add(anaSunucuKutu);
        
        //AnaSunucuProgress
        anaSunucuProgress.setBounds(225,115,150,15);
        anaSunucuProgress.setStringPainted(true);
        anaSunucuProgress.setForeground(new Color(0,255,0,200));
        frame.add(anaSunucuProgress);
        
        threadTakip.setBounds(650,150,225,400);
        threadTakip.setBackground(new Color(240,240,240));
        threadTakip.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        threadTakip.setEnabled(false);
        threadTakip.setDisabledTextColor(new Color(50,50,50));
        frame.add(threadTakip);
        
        threadSayisi.setBounds(650, 550,100,20);
        frame.add(threadSayisi);
        
        //alt sunucu kutular ve progressler
        for (int i = 0; i < 16; i++) {
            altSunucularKutu[i] = new javax.swing.JTextPane();
            altSunucularKutu[i].setBounds(20+(i%4)*150,143+(i/4)*120,120,100);
            altSunucularKutu[i].setBackground(new java.awt.Color(223, 245, 245));
            altSunucularKutu[i].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            altSunucularKutu[i].setEnabled(false);
            altSunucularKutu[i].setDisabledTextColor(new java.awt.Color(51,51,51));
            altSunucularKutu[i].setText("AltSunucu"+(i)); 
            
            altSunucularProgress[i] = new javax.swing.JProgressBar();
            altSunucularProgress[i].setStringPainted(true);
            altSunucularProgress[i].setForeground(new Color(0,255,0,200));
            altSunucularProgress[i].setBounds(20+(i%4)*150,245+(i/4)*120,120,15);
            
            if(i > 1){
                altSunucularKutu[i].setVisible(false);
                
                altSunucularProgress[i].setVisible(false);
            }
            frame.add(altSunucularKutu[i]);
            frame.add(altSunucularProgress[i]);
        }
        frame.setVisible(true);
    }
    
    public void arayuz_ciz(ArrayList<Sunucu> sunucular,Sunucu anaSunucu){
        //ana sunucu
        String anaSunucuText = "AnaSunucu\nt_id : "+(anaSunucu.t_id)+"\nKapasite : "+(anaSunucu.kapasite)
                + "\nİstek Sayısı : " +(anaSunucu.istekSayisi);
        anaSunucuKutu.setText(anaSunucuText);
        
        int progressValue = ((100*anaSunucu.istekSayisi)/anaSunucu.kapasite);
        anaSunucuProgress.setValue(progressValue);
            if(progressValue<=40)
                anaSunucuProgress.setForeground(new Color(0,255,0,200));
            else if(progressValue<=50)
                anaSunucuProgress.setForeground(new Color(255,255,0,200));
            else if(progressValue<=60)
                anaSunucuProgress.setForeground(new Color(255,102,0,200));
            else 
                anaSunucuProgress.setForeground(new Color(255,50,0,200));
        
        //alt sunucular
        for(int i = sunucular.size();i<altSunucularKutu.length;i++){
                altSunucularKutu[i].setVisible(false);
                
                altSunucularProgress[i].setVisible(false);
        }
        
        for (int i = 0; i < sunucular.size(); i++) {
            
            String altSunucuText = "AltSunucu\nt_id : "+(sunucular.get(i).t_id)+"\nKapasite : "+(sunucular.get(i).kapasite)
                + "\nİstek Sayısı : " +(sunucular.get(i).istekSayisi);
            
            altSunucularKutu[i].setText(altSunucuText);
            altSunucularKutu[i].setVisible(true);
            
            int progressValue1 = ((100*sunucular.get(i).istekSayisi)/sunucular.get(i).kapasite);
            altSunucularProgress[i].setValue(progressValue1);
            
            if(progressValue1<=40)
                altSunucularProgress[i].setForeground(new Color(0,255,0,200));
            else if(progressValue1<=50)
                altSunucularProgress[i].setForeground(new Color(255,255,0,200));
            else if(progressValue1<=60)
                altSunucularProgress[i].setForeground(new Color(255,102,0,200));
            else 
                altSunucularProgress[i].setForeground(new Color(255,50,0,200));
            
            altSunucularProgress[i].setVisible(true);
        }
        
        //thread bilgi
        String threadBilgi = "\tThread Takip\n\n"
                + "thread_id\tkapasite\tistek sayısı\n"
                + (anaSunucu.t_id)+"\t"+(anaSunucu.kapasite)+"\t"+(anaSunucu.istekSayisi)+"\n";
        
        for (int i = 0; i < sunucular.size(); i++) {
            Sunucu s = sunucular.get(i);
            
            threadBilgi = threadBilgi+(s.t_id)+"\t"+(s.kapasite)+"\t"+(s.istekSayisi)+"\n";
        }
        
        threadTakip.setText(threadBilgi);
        
        threadSayisi.setText("Thread Sayısı : "+(sunucular.size()+1));
    }
}
