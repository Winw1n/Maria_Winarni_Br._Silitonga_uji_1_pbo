import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner; 

interface Taxable {
    double PPN_RATE = 0.11;

    double calculateTax();
}

abstract class Product {
    protected String name;
    protected double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public abstract double getFinalPrice();
    public abstract String getDescription();
}

class Food extends Product implements Taxable {
    private String expiryDate;

    public Food(String name, double price, String expiryDate) {
        super(name, price);
        this.expiryDate = expiryDate;
    }

    @Override
    public double calculateTax() {
        return this.price * PPN_RATE;
    }

    @Override
    public double getFinalPrice() {
        return this.price + calculateTax();
    }

    @Override
    public String getDescription() {
        return "Makanan: " + name + " (Exp: " + expiryDate + ")";
    }
}

class Beverage extends Product implements Taxable {
    private double volumeMl;

    public Beverage(String name, double price, double volumeMl) {
        super(name, price);
        this.volumeMl = volumeMl;
    }

    @Override
    public double calculateTax() {
        return this.price * PPN_RATE;
    }

    @Override
    public double getFinalPrice() {
        return this.price + calculateTax();
    }

    @Override
    public String getDescription() {
        return "Minuman: " + name + " (" + volumeMl + " ml)";
    }
}

public class CashierApp {

    private static final List<Product> MASTER_PRODUCT_LIST = new ArrayList<>();

    static {
        MASTER_PRODUCT_LIST.add(new Food("Indomie Goreng", 3500.0, "2025-12-30"));
        MASTER_PRODUCT_LIST.add(new Beverage("Aqua 600ml", 5000.0, 600.0));
        MASTER_PRODUCT_LIST.add(new Food("Roti Tawar", 15000.0, "2025-12-25"));
        MASTER_PRODUCT_LIST.add(new Beverage("Coca Cola 330ml", 7500.0, 330.0));
        MASTER_PRODUCT_LIST.add(new Food("Nasi Goreng Spesial", 25000.0, "2025-12-31"));
        MASTER_PRODUCT_LIST.add(new Beverage("Es Teh Manis", 8000.0, 400.0));
        MASTER_PRODUCT_LIST.add(new Food("Sate Ayam", 22000.0, "2025-12-28"));
        MASTER_PRODUCT_LIST.add(new Beverage("Jus Jeruk", 15000.0, 350.0));
        MASTER_PRODUCT_LIST.add(new Food("Gado-gado", 18000.0, "2025-12-29"));
    }

    private static Product findProductByName(String name) {
        for (Product p : MASTER_PRODUCT_LIST) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
    
    private static void displayAvailableProductNames() {
        System.out.println("------------------------------------------");
        System.out.println("Produk Tersedia:");
        for (Product p : MASTER_PRODUCT_LIST) {
            System.out.println("- " + p.getName());
        }
        System.out.println("------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> shoppingCart = new ArrayList<>();

        // Mendapatkan tanggal dan waktu saat ini
        LocalDateTime now = LocalDateTime.now();
        // Memformat tanggal dan waktu
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss"); // Contoh: 25 Mei 2025 22:36:31
        String formattedDateTime = now.format(formatter);


        System.out.println("==========================================");
        System.out.println("         SISTEM KASIR SEDERHANA           ");
        System.out.println("         (Input Nama Produk Saja)         ");
        System.out.println("==========================================");
        System.out.println("Panduan:");
        System.out.println("1. Masukkan nama produk yang ingin dibeli.");
        System.out.println("2. Ketik 'selesai' untuk mengakhiri dan melihat struk.");
        System.out.println("==========================================");

        while (true) {
            displayAvailableProductNames();

            System.out.print("\nMasukkan nama produk (atau 'selesai'): ");
            String inputNama = scanner.nextLine().trim();

            if (inputNama.equalsIgnoreCase("selesai")) {
                break;
            }

            Product foundProduct = findProductByName(inputNama);

            if (foundProduct != null) {
                shoppingCart.add(foundProduct);
                System.out.println("'" + foundProduct.getName() + "' berhasil ditambahkan.");
            } else {
                System.out.println("Produk '" + inputNama + "' tidak ditemukan di daftar.");
            }
        }

        System.out.println("\n==========================================");
        System.out.println("         STRUK BELANJA                    ");
        System.out.println("==========================================");

        double totalBelanja = 0;
        double totalPPN = 0;
        double totalHargaDasar = 0;

        if (shoppingCart.isEmpty()) {
            System.out.println("Keranjang belanja kosong. Tidak ada transaksi.");
        } else {
            System.out.printf("%-25s | %10s | %8s | %10s%n", "Nama Produk", "Harga Dasar", "PPN (11%)", "Harga Final");
            System.out.println("------------------------------------------------------------------");

            for (Product item : shoppingCart) {
                double ppnAmount = ((Taxable) item).calculateTax();
                System.out.printf("%-25s | Rp%8.2f | Rp%6.2f | Rp%8.2f%n",
                                  item.getName(), item.getPrice(), ppnAmount, item.getFinalPrice());
                totalBelanja += item.getFinalPrice();
                totalPPN += ppnAmount;
                totalHargaDasar += item.getPrice();
            }
            System.out.println("------------------------------------------------------------------");
            System.out.printf("%-25s | Rp%8.2f | Rp%6.2f | Rp%8.2f%n", "TOTAL KESELURUHAN", totalHargaDasar, totalPPN, totalBelanja);
        }

        scanner.close();
        System.out.println("\n==========================================");
        System.out.println("         Terima Kasih Telah Berbelanja!   ");
        System.out.println("         Kasir: Maria Winarni Br. Silitonga");
        System.out.println("         Tanggal: " + formattedDateTime);
        System.out.println("==========================================");
    }
}