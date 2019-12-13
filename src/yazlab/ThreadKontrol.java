package yazlab;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadKontrol extends Thread{
    public Sunucu anaSunucu;
    public ArrayList<Sunucu> sunucular = new ArrayList<>();
    
    ThreadKontrol(Sunucu anaSunucu) {
        this.anaSunucu = anaSunucu;
    }
    
    public void alt_sunucu_ekle(Sunucu s){
        sunucular.add(s);
    }

    @Override
    public void run() {
        anaSunucu.start();
        
        for (Sunucu sunucu : sunucular) {
            sunucu.start();
        }
        
        int max_id=2;
        
        while(true){
            Sunucu eklenecek = null;
            int kapatilacak_index = 0;
            
            for (Sunucu sunucu : sunucular) {
                double oran = ((double)sunucu.istekSayisi/(double)sunucu.kapasite) ;
                
                if(oran > 0.7 && sunucular.size()<16){//istek %70'den büyükse yeni sunucu oluştur.
                    eklenecek = new Sunucu(++max_id,5000,anaSunucu);
                    
                    eklenecek.istekSayisi = sunucu.istekSayisi/2;
                    
                    sunucu.istekSayisi = sunucu.istekSayisi/2;
                    
                    System.out.println("***********************\n************************\n"
                            + (sunucu.t_id)+", sınırı aştı\n"
                            + (sunucu.t_id)+" istek sayısı : "+sunucu.istekSayisi+"\t"
                            + (eklenecek.t_id)+" istek sayısı : "+sunucu.istekSayisi
                            + "\n***********************\n************************\n");
                }
                
                if(sunucu.t_id>max_id)
                    max_id = sunucu.t_id;
                
                if(sunucu.t_id>3 && sunucu.istekSayisi == 0){
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>"+(sunucu.t_id)+" silinecek");
                    
                    kapatilacak_index = sunucular.indexOf(sunucu);
                }
            }
            
            if(eklenecek != null && sunucular.size()<16){
                sunucular.add(eklenecek);
                
                eklenecek.start();
            }
            
            if(kapatilacak_index != 0){
                Sunucu kapatilacak = sunucular.get(kapatilacak_index);
                
                kapatilacak.interrupt();
                
                sunucular.remove(kapatilacak);
                try {
                    kapatilacak.join();
                } catch (InterruptedException ex) {
                    System.out.println("join hata");
                }
                                
                kapatilacak = null;
            }
        }
    }
}