package id.fokusdesain.secureapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    // Kredensial sengaja dibuat sederhana untuk praktikum reverse engineering.
    // Jangan gunakan pola hardcode seperti ini pada aplikasi produksi.
    private static final String DEMO_USER = "admin";
    private static final String DEMO_PASS = "admin123";
    private static final String PREFS = "secureapp_session";
    private static final String KEY_LOGGED_IN = "logged_in";
    private static final String KEY_USERNAME = "username";

    private SharedPreferences prefs;
    private LinearLayout root;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        showCurrentScreen();
    }

    private void showCurrentScreen() {
        if (prefs.getBoolean(KEY_LOGGED_IN, false)) showDashboard();
        else showLogin();
    }

    private void prepareRoot() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(24), dp(28), dp(24), dp(24));
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(245, 248, 252));
        setContentView(root);
    }

    private TextView text(String value, int size, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(Color.rgb(28, 42, 65));
        if (bold) t.setTypeface(null, Typeface.BOLD);
        t.setGravity(Gravity.CENTER);
        t.setPadding(0, dp(8), 0, dp(8));
        return t;
    }

    private EditText field(String hint, int inputType) {
        EditText e = new EditText(this);
        e.setSingleLine(true);
        e.setHint(hint);
        e.setInputType(inputType);
        e.setTextSize(16);
        e.setPadding(dp(12), dp(8), dp(12), dp(8));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, dp(52));
        lp.setMargins(0, dp(8), 0, dp(8));
        root.addView(e, lp);
        return e;
    }

    private Button button(String label) {
        Button b = new Button(this);
        b.setText(label);
        b.setAllCaps(false);
        b.setTextSize(16);
        b.setTextColor(Color.WHITE);
        b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(28, 91, 160)));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, dp(52));
        lp.setMargins(0, dp(10), 0, dp(6));
        root.addView(b, lp);
        return b;
    }

    private void showLogin() {
        prepareRoot();
        root.addView(text("SecureApp Demo", 28, true));
        root.addView(text(getString(R.string.banner_text), 14, false));
        root.addView(text("LOGIN PENGGUNA", 18, true));
        EditText username = field("Username", 1);
        EditText password = field("Password", 129);
        Button login = button("Masuk");
        TextView info = text("Akun praktikum: admin / admin123", 13, false);
        root.addView(info);
        login.setOnClickListener(v -> {
            String u = username.getText().toString().trim();
            String p = password.getText().toString();
            if (DEMO_USER.equals(u) && DEMO_PASS.equals(p)) {
                prefs.edit().putBoolean(KEY_LOGGED_IN, true)
                    .putString(KEY_USERNAME, u).apply();
                showDashboard();
            } else {
                new AlertDialog.Builder(this)
                    .setTitle("Login gagal")
                    .setMessage("Username atau password salah.")
                    .setPositiveButton("OK", null).show();
            }
        });
    }

    private void showDashboard() {
        prepareRoot();
        String user = prefs.getString(KEY_USERNAME, "pengguna");
        root.addView(text("SecureApp Dashboard", 25, true));
        root.addView(text("Selamat datang, " + user, 19, true));
        root.addView(text("Status autentikasi: BERHASIL", 16, true));
        root.addView(text("Status sesi: AKTIF", 16, true));
        root.addView(text("Data sesi disimpan pada SharedPreferences lokal.", 14, false));
        Button check = button("Periksa Status Sesi");
        check.setOnClickListener(v -> new AlertDialog.Builder(this)
            .setTitle("Status Sesi")
            .setMessage(prefs.getBoolean(KEY_LOGGED_IN, false)
                ? "Sesi aktif untuk " + prefs.getString(KEY_USERNAME, "-")
                : "Tidak ada sesi aktif.")
            .setPositiveButton("Tutup", null).show());
        Button logout = button("Logout");
        logout.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(180, 55, 55)));
        logout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            showLogin();
        });
        root.addView(text("Catatan: aplikasi ini hanya simulasi pembelajaran, bukan sistem autentikasi produksi.", 12, false));
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
