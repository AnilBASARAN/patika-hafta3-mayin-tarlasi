import java.util.*;
public class MineSweeper {

    // sweeper isimli oluşturduğumuz method seçilen koordinatın tüm komşu koordinatlarına giderek eğer yıldız ise sum = 0 olarak atanan değeri 1 arrtıracak
    // ve son olarak bu değeri dönecek.
    public static String sweeper(String[][] array, int row, int col) {
        int sum = 0;
        if ((col + 1 >= 0 && col + 1 <= array[0].length - 1) && (row + 1 >= 0 && row + 1 <= array.length - 1) && array[row + 1][col + 1].equals(" * "))
            sum += 1;
        if ((col - 1 >= 0 && col - 1 <= array[0].length - 1) && (row - 1 >= 0 && row - 1 <= array.length - 1) && array[row - 1][col - 1].equals(" * "))
            sum += 1;
        if ((col - 1 >= 0 && col - 1 <= array[0].length - 1) && (row + 1 >= 0 && row + 1 <= array.length - 1) && array[row + 1][col - 1].equals(" * "))
            sum += 1;
        if ((col + 1 >= 0 && col + 1 <= array[0].length - 1) && (row - 1 >= 0 && row - 1 <= array.length - 1) && array[row - 1][col + 1].equals(" * "))
            sum += 1;
        //////////////////
        if ((col >= 0 && col <= array[0].length - 1) && (row - 1 >= 0 && row - 1 <= array.length - 1) && array[row - 1][col].equals(" * "))
            sum += 1;
        if ((col >= 0 && col <= array[0].length - 1) && (row + 1 >= 0 && row + 1 <= array.length - 1) && array[row + 1][col].equals(" * "))
            sum += 1;
        if ((col + 1 >= 0 && col + 1 <= array[0].length - 1) && (row >= 0 && row <= array.length - 1) && array[row][col + 1].equals(" * "))
            sum += 1;
        if ((col - 1 >= 0 && col - 1 <= array[0].length - 1) && (row >= 0 && row <= array.length - 1) && array[row][col - 1].equals(" * "))
            sum += 1;

        return " " + String.valueOf(sum) + " ";
    }

    public static boolean isMapChecked(String[][] map, String value) {

        // isMapChecked methodu kullanıcının girdiği koordinatı 0 dan 1 e çevirmekte ve aynı zamanda bomba koordinatlarını da -1 e çevirmek için kullandık
        // böylece tüm haritada hiç 0 kalmadığında hala bomba patlamadıysa kullanıcı oyunu kazanacak

        int mapRow = map.length;
        int mapCol = map[0].length;
        boolean isMapChecked = true;
        for (int i = 0; i < mapRow; i++) {

            for (int j = 0; j < mapCol; j++) {
                if (map[i][j].equals(value)) {
                    isMapChecked = false;
                }
            }
        }
        return isMapChecked;
    }

    public static void matrisPrinter(String[][] array) {

// oluşturduğumuz iki boyutlu arrayleri kullanıcıya göstermek için ekrana bastıran bir method

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("-----------");
    }

    public MineSweeper() {

        // minesweeper constructoru

        // mayın tarlasını oluştur(m rol ve n col )
        Scanner input = new Scanner(System.in);
        int m, n;
        do {

            // kullanıcıdan rol ve col sayısı al
            System.out.print("Row sayısını Girin:   ");
            m = input.nextInt();
            if (m <= 1) System.out.println("Row için 1'den büyük bir sayı giriniz.");
        } while (m < 2);

        do {
            System.out.print("Col sayısını Girin:   ");
            n = input.nextInt();
            if (n <= 1) System.out.println("Col için 1'den büyük bir sayı giriniz.");
        } while (n < 2);

        String[][] mayinsizTarlaArray = new String[m][n];
        String[][] mayinliTarlaArray = new String[m][n];


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mayinsizTarlaArray[i][j] = " - ";
                mayinliTarlaArray[i][j] = " - ";
            }
        }

        // mayınlı tarla yapmak için önce mayın sayısını bulalım.

        int mayinSayisi = (m * n) / 4;
        System.out.println("Mayın Sayısı: " + mayinSayisi);


        // acilmiş map arrayi 0 ile doldurup, acıldıkca 1 yapacağız açılan noktayı, hepsi 1 olana kadar devam edilecek
        String[][] acilmisMap = new String[m][n];


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                acilmisMap[i][j] = "0";
            }
        }

        // bombaları koyalım
// mayın sayısını her koordinata koyduktan sonra 1 azaltıyoruz ve while dongusuyle eğer koordinatta bomba yoksa koy varsa devam ediyor
        // böylece mayın sayımız azalmıyor


        while (mayinSayisi > 0) {
            int bombGeneratorRow = (int) (Math.random() * m);
            int bombGeneratorCol = (int) (Math.random() * n);
            if (mayinliTarlaArray[bombGeneratorRow][bombGeneratorCol].equals(" - ")) {
                mayinliTarlaArray[bombGeneratorRow][bombGeneratorCol] = " * ";
                acilmisMap[bombGeneratorRow][bombGeneratorCol] = "-1";
            //acilmisMap kısmında ise mayınları koyarken o koordinatları acilmismap arrayinde -1 e eşitliyoruz, böylece hem 0 dan farklı bir sayı oluyor
                // aynı zamanda 1 den farklı olduğu için kullanıcının girdiği sayılardan da farklı oluyor, gerekirse ayırt etme şansımız olur eğer yeni
                // özellikler eklemek istersek vs
                mayinSayisi--;
            }
        }

        matrisPrinter(mayinliTarlaArray);
        int mNew;
        int nNew;
        boolean isMnewBound;
        boolean isNnewBound;
        int count = 0;


        do {

            do {
                System.out.print("Bomba arama (Row sayısını) Girin:   ");

                mNew = input.nextInt();
                isMnewBound = (mNew >= 0 && mNew <= m - 1);
                if (!isMnewBound) {
                    System.out.println("Hatalı Bir sayı girdiniz");
                    System.out.println("0 ile " + m + " arasında bir sayı girdiniz");
                }
                // eğer kullanıcının girdiği sayı o aralıkda değilse kullanıcıdan veri istemeye devam ediyor
            } while (!isMnewBound);

            do {
                System.out.print("Bomba arama (Col sayısını) Girin:   ");
                nNew = input.nextInt();
                isNnewBound = (nNew >= 0 && nNew <= n - 1);

                if (!isNnewBound) {
                    System.out.println("Hatalı Bir sayı girdiniz");
                    System.out.println("0 ile " + n + " arasında bir sayı girdiniz");
                }
                // eğer kullanıcının girdiği sayı o aralıkda değilse kullanıcıdan veri istemeye devam ediyor
            } while (!isNnewBound);


            // girilen koordinat verileri mayınlıtarla arayi ile * kontrolü yapılıyor
            // eğer * varsa o koordinatta oyun bitiriliyor ve BOOM yazısı yazdırılıyor ekrana
            // acilmişmap arrayinde girilen bir değer ise kullanıcı uyarılarak bu koordinat girdiniz yazdırılıyor,
            // değilse o acilmisMap arrayine ekleniyor girilen koordinat ve hala hayattasın mesajı dönülüyor
            // acilmismap tüm elemanları 0 dan farklı bir değer alırsa tüm döngü bitirilerek kazandınız yazısı yazdırılıyor

            if (mayinliTarlaArray[mNew][nNew].equals(" * ")) {

                acilmisMap[mNew][nNew] = "1";
                System.out.println("BOOM");
                return;
            } else if (acilmisMap[mNew][nNew].equals("1")) {
                System.out.println("Bu koordinatı daha önceden girdiniz");
                System.out.println("Deneme sayınız değişmeyecek");
            } else {
                count++;
                System.out.println(count + " denemen, Hala Hayattasın ");
                mayinsizTarlaArray[mNew][nNew] = sweeper(mayinliTarlaArray, mNew, nNew);
                acilmisMap[mNew][nNew] = "1";
            }
            ;

            matrisPrinter(mayinsizTarlaArray);
//mayınsız tarlanın arayi sürekli yenilenicek güncellenmiş haliyle while dongusu !ismapChecked yanlış olana kadar

        } while (!isMapChecked(acilmisMap, "0"));

        // Tüm bombasız koordinatlar seçilirse, bomba koordinatları da haritada işaretlendiği için -1 olarak, gezilen noktalarda 1 olarak,
        // hiç 0 kalmadığında oyun biter ve Kazandınız yazısı döner
System.out.println("Kazandınız");
input.close();


    }
    public static void run(){
        //les go
    }
}