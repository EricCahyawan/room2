package eric.app.room2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eric.app.room2.database.daftarBelanja
import eric.app.room2.database.daftarBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var DB: daftarBelanjaDB
    private lateinit var adapterDaftar: adapterDaftar
    private var arDaftar: MutableList<daftarBelanja> = mutableListOf()
    lateinit var _fabAdd: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = daftarBelanjaDB.getDatabase(this)
        adapterDaftar = adapterDaftar(arDaftar)
        var _rvNotes = findViewById<RecyclerView>(R.id.rvNotes)
        _rvNotes.layoutManager = LinearLayoutManager(this)
        _rvNotes.adapter = adapterDaftar
        _fabAdd = findViewById(R.id.fabAdd)
        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahDaftar::class.java))
        }

        adapterDaftar.setOnItemClickCallback(object : adapterDaftar.OnItemClickCallback {
            override fun delData(dtBelanja: daftarBelanja) {
                CoroutineScope(Dispatchers.IO).async {
                    DB.fundaftarBelanjaDAO().delete(dtBelanja)
                    val daftar = DB.fundaftarBelanjaDAO().selectAll()
                    withContext(Dispatchers.Main){
                        adapterDaftar.isiData(daftar)
                    }
                }
            }

        })
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
            adapterDaftar.isiData(daftarBelanja)
            Log.d("data ROOM", daftarBelanja.toString())
        }
    }
}