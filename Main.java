import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== LOGIN E-WALLET ===");
        System.out.println("1. Regular User | 2. Premium User | 3. Merchant User");
        System.out.print("Pilih (1-3): ");
        
        int tipeAkun = 1; 
        try {
            tipeAkun = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Input salah! Login sebagai Regular User.");
            scanner.nextLine();
        }

        User activeUser;
        if (tipeAkun == 2) {
            activeUser = new PremiumUser("U001", "User Premium", "085877713117", 500000);
        } else if (tipeAkun == 3) {
            activeUser = new MerchantUser("U001", "User Merchant", "085877713117", 500000);
        } else {
            activeUser = new RegularUser("U001", "User Regular", "085877713117", 500000);
        }
        
        System.out.println("\n[!] Login sebagai: " + activeUser.getAccountType());
        
        // Coba load data dari file
        activeUser.loadDataFromFile();

        System.out.print("[!] Masukkan PIN Anda (Default: 123456): ");
        if (!activeUser.verifyPin(scanner.nextLine())) {
            System.out.println("Login Gagal! PIN Salah.");
            return;
        }

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=== MENU E-WALLET: " + activeUser.getName().toUpperCase() + " ===");
            System.out.println("1. Tampilkan Saldo");
            System.out.println("2. Top Up");
            System.out.println("3. Bayar Tagihan (Pakai Promo)");
            System.out.println("4. Transfer Dana");
            System.out.println("5. Riwayat Transaksi");
            System.out.println("6. Ganti PIN");
            System.out.println("7. Split Bill (Bagi Tagihan Teman)");
            System.out.println("8. Laporan Keuangan Bulanan");
            System.out.println("9. Simpan Data ke File (Save)");
            
            if (activeUser instanceof MerchantUser) {
                System.out.println("10. Terima Pembayaran (Khusus Merchant)");
                System.out.println("11. Keluar");
            } else {
                System.out.println("10. Keluar");
            }
            
            System.out.print("Pilih menu: ");

            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine(); 

                switch (pilihan) {
                    case 1: activeUser.showBalance(); break;
                    case 2:
                        System.out.print("Masukkan nominal Top Up: Rp");
                        activeUser.topUp(scanner.nextDouble());
                        scanner.nextLine();
                        break;
                    case 3:
                        System.out.print("Masukkan nominal Bayar: Rp");
                        double tagihan = scanner.nextDouble();
                        scanner.nextLine();
                        
                        System.out.print("Kode Promo (DISKON10 / CASHBACK10K / Kosongi): ");
                        String kode = scanner.nextLine();
                        
                        double totalAkhir = tagihan;
                        Promo promo = null;
                        
                        if (kode.equalsIgnoreCase("DISKON10")) { promo = new DiscountPromo("DISKON10", 0.10); } 
                        else if (kode.equalsIgnoreCase("CASHBACK10K")) { promo = new CashbackPromo("CASHBACK10K", 10000); }

                        if (promo != null) {
                            double nilaiPromo = promo.calculatePromoValue(tagihan);
                            if (promo instanceof DiscountPromo) {
                                totalAkhir -= nilaiPromo;
                                System.out.println(">> Promo Aktif: " + promo.getPromoDescription());
                            } else if (promo instanceof CashbackPromo) {
                                System.out.println(">> Promo Aktif: " + promo.getPromoDescription());
                            }
                        }
                        
                        activeUser.pay(totalAkhir); 
                        if (promo instanceof CashbackPromo && totalAkhir <= activeUser.getBalance() + totalAkhir) {
                             activeUser.topUp(promo.calculatePromoValue(tagihan));
                        }
                        break;
                    case 4:
                        System.out.print("\nMasukkan PIN: ");
                        if (!activeUser.verifyPin(scanner.nextLine())) {
                            System.out.println("Transfer Dibatalkan: PIN Salah!");
                            break;
                        }

                        System.out.println("1. Bank Transfer | 2. QR Payment | 3. Wallet Transfer");
                        System.out.print("Pilih Metode: ");
                        int metode = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Nominal: Rp");
                        double nom = scanner.nextDouble();
                        scanner.nextLine(); 
                        
                        Payment transaksi = null;
                        if (metode == 1) {
                            System.out.print("Nama Bank: ");
                            transaksi = new BankTransfer(nom, scanner.nextLine());
                        } else if (metode == 2) {
                            System.out.print("ID QR Code: ");
                            transaksi = new QRPayment(nom, scanner.nextLine());
                        } else if (metode == 3) {
                            System.out.print("No HP: ");
                            transaksi = new WalletTransfer(nom, scanner.nextLine());
                        }

                        if (transaksi != null) activeUser.processPayment(transaksi); 
                        break;
                    case 5: activeUser.showTransactionHistory(); break;
                    case 6:
                        System.out.print("Masukkan PIN Lama: ");
                        String oldPin = scanner.nextLine();
                        System.out.print("Masukkan PIN Baru: ");
                        String newPin = scanner.nextLine();
                        activeUser.changePin(oldPin, newPin);
                        break;
                    case 7:
                        System.out.print("Total Tagihan: Rp");
                        double totalBill = scanner.nextDouble();
                        System.out.print("Dibagi berapa orang?: ");
                        int totalPeople = scanner.nextInt();
                        scanner.nextLine();
                        if (totalPeople > 1) {
                            double perPerson = totalBill / totalPeople;
                            System.out.println(">> Masing-masing membayar: Rp" + perPerson);
                            System.out.print("Bayar porsi Anda sekarang? (Y/N): ");
                            if (scanner.nextLine().equalsIgnoreCase("Y")) { activeUser.pay(perPerson); }
                        } else { System.out.println("Gagal: Minimal 2 orang!"); }
                        break;
                    case 8: 
                        activeUser.generateMonthlyReport(); 
                        break;
                    case 9: 
                        activeUser.saveDataToFile(); 
                        break;
                    case 10:
                        if (activeUser instanceof MerchantUser) {
                            System.out.print("Nominal Pembayaran Masuk: Rp");
                            ((MerchantUser) activeUser).receivePayment(scanner.nextDouble());
                            scanner.nextLine();
                        } else {
                            System.out.println("Terima kasih!"); isRunning = false;
                        }
                        break;
                    case 11:
                        if (activeUser instanceof MerchantUser) {
                            System.out.println("Terima kasih!"); isRunning = false;
                        } else {
                            System.out.println("Pilihan tidak valid.");
                        }
                        break;
                    default: System.out.println("Pilihan tidak valid.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input harus angka.");
                scanner.nextLine(); 
            }
        }
    }
}