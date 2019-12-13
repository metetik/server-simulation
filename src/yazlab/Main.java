package yazlab;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Sunucu anaSunucu = new Sunucu(1,10000);
        Sunucu altSunucu1 = new Sunucu(2,5000,anaSunucu);
        Sunucu altSunucu2 = new Sunucu(3,5000,anaSunucu);
        
        ThreadKontrol thread_kontrol = new ThreadKontrol(anaSunucu);
        
        thread_kontrol.alt_sunucu_ekle(altSunucu1);
        thread_kontrol.alt_sunucu_ekle(altSunucu2);
        
        thread_kontrol.start();
        
        Arayuz arayuz = new Arayuz();
        
        while(true){
            arayuz.arayuz_ciz(thread_kontrol.sunucular,thread_kontrol.anaSunucu);
            
            Thread.sleep(30);
        }
    } 
}
