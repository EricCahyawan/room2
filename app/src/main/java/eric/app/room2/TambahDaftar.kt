package eric.app.room2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import eric.app.room2.database.daftarBelanja
import eric.app.room2.database.daftarBelanjaDB
import eric.app.room2.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahDaftar : AppCompatActivity() {
    lateinit var _etItem: EditText
    lateinit var _etJumlah: EditText
    var DB = daftarBelanjaDB.getDatabase(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _etItem = findViewById(R.id.etItem)
        _etJumlah = findViewById(R.id.etJumlah)
        var tanggal = getCurrentDate()
        val _btnTambah = findViewById<Button>(R.id.btnTambah)
        _btnTambah.setOnClickListener{
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = tanggal,
                        item = _etItem.text.toString(),
                        jumlah = _etJumlah.text.toString()
                    )
                )
            }
        }
    }
}