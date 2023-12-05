import java.util.Random;
import java.util.Scanner;

public class Main {

    private static int satir;
    private static int sutun;
    private static int mayinSayisi;

    private static char[][] oyunTahtasi;
    private static char[][] mayinTarlasi;
    private static int[][] patlayanMayinKonumlari;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Oyun tahtası boyutunu girin (satır sutun): ");
        satir = scanner.nextInt();
        sutun = scanner.nextInt();

        System.out.print("Zorluk seviyesini seçin (1: Kolay, 2: Orta, 3: Zor): ");
        int zorluk = scanner.nextInt();

        switch (zorluk) {
            case 1:
                mayinSayisi = (int) (satir * sutun * 0.1);
                break;
            case 2:
                mayinSayisi = (int) (satir * sutun * 0.2);
                break;
            case 3:
                mayinSayisi = (int) (satir * sutun * 0.3);
                break;
            default:
                System.out.println("Geçersiz zorluk seviyesi. Varsayılan olarak orta seviye seçildi.");
                mayinSayisi = (int) (satir * sutun * 0.2);
                break;
        }

        oyunTahtasi = new char[satir][sutun];
        mayinTarlasi = new char[satir][sutun];
        patlayanMayinKonumlari = new int[mayinSayisi][2];

        tahtayiBaslat();
        mayinlariYerlestir();

        oyunuBaslat();

        scanner.close();
    }

    private static void tahtayiBaslat() {
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                oyunTahtasi[i][j] = '-';
                mayinTarlasi[i][j] = '-';
            }
        }
    }

    private static void mayinlariYerlestir() {
        Random random = new Random();
        int mayinSayac = 0;

        while (mayinSayac < mayinSayisi) {
            int randomSatir = random.nextInt(satir);
            int randomSutun = random.nextInt(sutun);

            if (mayinTarlasi[randomSatir][randomSutun] != 'M') {
                mayinTarlasi[randomSatir][randomSutun] = 'M';
                mayinSayac++;
            }
        }
    }

    private static void oyunuBaslat() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            tahtayiYazdir();

            System.out.print("Satır girin: ");
            int satirSecim = scanner.nextInt();

            System.out.print("Sütun girin: ");
            int sutunSecim = scanner.nextInt();

            if (mayinTarlasi[satirSecim][sutunSecim] == 'M') {
                System.out.println("Mayına bastınız! Oyun bitti.");
                gosterMayinlari();
                break;
            } else {
                int mayinSayisi = mayinSayisiniHesapla(satirSecim, sutunSecim);
                oyunTahtasi[satirSecim][sutunSecim] = (char) (mayinSayisi + '0');
            }

            if (oyunuKazandikMi()) {
                System.out.println("Tebrikler! Oyunu kazandınız.");
                break;
            }
        }

        scanner.close();
    }

    private static int mayinSayisiniHesapla(int satirSecim, int sutunSecim) {
        int mayinSayisi = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int yeniSatir = satirSecim + i;
                int yeniSutun = sutunSecim + j;

                if (yeniSatir >= 0 && yeniSatir < satir && yeniSutun >= 0 && yeniSutun < sutun) {
                    if (mayinTarlasi[yeniSatir][yeniSutun] == 'M') {
                        mayinSayisi++;
                    }
                }
            }
        }

        return mayinSayisi;
    }

    private static boolean oyunuKazandikMi() {
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                if (oyunTahtasi[i][j] == '-' && mayinTarlasi[i][j] != 'M') {
                    return false;
                }
            }
        }
        return true;
    }

    private static void tahtayiYazdir() {
        System.out.print("  ");
        for (int i = 0; i < sutun; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < satir; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < sutun; j++) {
                System.out.print(oyunTahtasi[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void gosterMayinlari() {
        System.out.println("Mayınların Konumu:");
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                if (mayinTarlasi[i][j] == 'M') {
                    System.out.print("* ");
                } else {
                    System.out.print(oyunTahtasi[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

}
