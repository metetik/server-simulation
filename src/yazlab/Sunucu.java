package yazlab;

import java.util.Random;

public class Sunucu extends Thread{
    public int t_id;
    public int kapasite;
    public int istekSayisi;
    public Sunucu anaSunucu;
    
    public Sunucu(int t_id, int kapasite) {
        this.t_id = t_id;
        this.kapasite = kapasite;
        anaSunucu = null;
    }

    public Sunucu(int t_id, int kapasite, Sunucu anaSunucu) {
        this.t_id = t_id;
        this.kapasite = kapasite;
        this.anaSunucu = anaSunucu;
    }
    
    @Override
    public void run() {
        
        if(this.t_id == 1){
            try {
                long baslangic = System.currentTimeMillis();
                
                while(true){
                    ana_istek_donus(baslangic);
                    this.sleep(200);
                    ana_istek_donus(baslangic);
                    this.sleep(200);
                    ana_istek_donus(baslangic);
                    this.sleep(100);
                    istek_kabul(baslangic);
                    this.sleep(100);
                    ana_istek_donus(baslangic);
                    this.sleep(200);
                    ana_istek_donus(baslangic);
                    this.sleep(200);
                    istek_kabul(baslangic);
                    
                    if(this.isInterrupted())
                        break;
                }
            } catch (InterruptedException ex) {
                System.out.println("Thread interrupt");
            }
        }
        else{
            try {
                long baslangic = System.currentTimeMillis();
                
                while(true){
                    istek_donus(baslangic);
                    this.sleep(300);
                    istek_donus(baslangic);
                    this.sleep(200);
                    istek_talep(baslangic);
                    this.sleep(100);
                    istek_donus(baslangic);
                    this.sleep(300);
                    istek_donus(baslangic);
                    this.sleep(100);
                    istek_talep(baslangic);
                    this.sleep(200);
                    istek_donus(baslangic);
                    this.sleep(300);
                    istek_talep(baslangic);
                    
                    if(this.isInterrupted())
                        break;
                }
            } catch (InterruptedException ex) {
                System.out.println("Thread interrupt");
            }
        }
    }
    
    public void istek_donus(long b){
        Random rand = new Random();
        
        int donus = rand.nextInt(1001);
        
        if((istekSayisi - donus)>0){
            istekSayisi -= donus;
            
            System.out.println((this.t_id)+","+donus+" dönüş yaptı. istek sayısı : "+istekSayisi+"  >>"+(System.currentTimeMillis()-b)+"<<");
        }
        else{
            istekSayisi = 0;
            
            System.out.println((this.t_id)+","+donus+" dönüş yaptı. istek sayısı : "+istekSayisi+"  >>"+(System.currentTimeMillis()-b)+"<<");
        }
        
    }
    
    public void ana_istek_donus(long b){
        Random rand = new Random();
        
        int donus = rand.nextInt(1001);
        
        if((istekSayisi - donus)>0){
            istekSayisi -= donus;
            
            System.out.println((this.t_id)+","+donus+" dönüş yaptı. istek sayısı : "+istekSayisi+"  >>"+(System.currentTimeMillis()-b)+"<<");
        }
        else{
            istekSayisi = 0;
            
            System.out.println((this.t_id)+","+donus+" dönüş yaptı. istek sayısı : "+istekSayisi+"  >>"+(System.currentTimeMillis()-b)+"<<");
        }
        
    }
    
    public void istek_kabul(long b){//ana sunucu istek üretiyor
        if(this.anaSunucu == null){
            Random rand = new Random();
            
            int alinacak = rand.nextInt(10001);
            
            if(alinacak+istekSayisi<kapasite)
                this.istekSayisi += alinacak;
            else
                this.istekSayisi = kapasite;
                
            System.out.println("ana sunucu "+alinacak+" istek kabul etti. istek sayısı : "+istekSayisi+"  >>"+(System.currentTimeMillis()-b)+"<<");
        }
    }
    synchronized public void istek_talep(long b){//alt sunucu ana sunucudan istek talep ediyor
        Random rand = new Random();
            
        int alinacak = rand.nextInt(5001);

        if(((anaSunucu.istekSayisi - alinacak)>0)&&((this.istekSayisi + alinacak) < this.kapasite)){
            anaSunucu.istekSayisi -= alinacak;

            this.istekSayisi += alinacak;
        }
        else if((this.istekSayisi + alinacak) < this.kapasite){
            this.istekSayisi += anaSunucu.istekSayisi;

            anaSunucu.istekSayisi = 0;
        }
        System.out.println((this.t_id)+",ana sunucudan "+alinacak+" istek talep etti >>"+(System.currentTimeMillis()-b)+"<<"
        +"\n\tAna Sunucu : "+(anaSunucu.istekSayisi)+"\n\tAlt Sunucu : "+(this.istekSayisi));
    }
}