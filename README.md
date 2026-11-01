# SecureApp Demo - Praktikum Minggu 6 & 7

Aplikasi Android Java sederhana untuk praktikum Mobile App Tampering & Reverse Engineering serta Autentikasi & Manajemen Sesi.

## Akun demo
- Username: `admin`
- Password: `admin123`

## Build APK tanpa Android Studio (GitHub Actions)
1. Buat repository GitHub baru.
2. Upload seluruh isi folder proyek ini ke repository.
3. Buka tab **Actions**.
4. Pilih workflow **Build SecureApp APK** lalu klik **Run workflow** (atau push ke branch `main`).
5. Tunggu sampai status selesai.
6. Buka hasil workflow, unduh artifact **SecureApp-debug-apk**.
7. Ekstrak ZIP artifact; file `app-debug.apk` adalah APK untuk dipasang di HP Android.

Laptop hanya dipakai untuk upload source melalui browser. Kompilasi berjalan di server GitHub.

## Uji aplikasi
1. Install APK pada HP Android.
2. Login dengan akun demo.
3. Tutup dan buka kembali aplikasi; sesi tetap aktif.
4. Tekan Logout; sesi dihapus dan aplikasi kembali ke halaman login.

## Praktikum reverse engineering
Gunakan APK milik sendiri. Dengan JADX, amati konstanta DEMO_USER dan DEMO_PASS. Dengan Apktool, decompile APK, ubah `res/values/strings.xml` pada `banner_text`, rebuild, lalu sign APK hasil rebuild sebelum dipasang. Lakukan pengujian hanya pada perangkat sendiri.

## Batasan keamanan
Kredensial hardcoded dan SharedPreferences biasa sengaja dipakai sebagai objek pembelajaran agar mudah dianalisis. Ini bukan implementasi aman untuk produksi. Aplikasi produksi harus memakai backend autentikasi, token berumur terbatas, penyimpanan token aman, HTTPS, dan validasi sesi di server.
