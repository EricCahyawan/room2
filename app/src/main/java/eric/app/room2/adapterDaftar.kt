package eric.app.room2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eric.app.room2.database.daftarBelanja

class adapterDaftar(private val daftarBelanja: MutableList<daftarBelanja>) : RecyclerView.Adapter<adapterDaftar.ListViewHolder>(){
    private lateinit var onItemClickCallBack: OnItemClickCallback
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterDaftar.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    override fun onBindViewHolder(holder: adapterDaftar.ListViewHolder, position: Int) {
        val daftar = daftarBelanja[position]
        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvjumlahBarang.setText(daftar.jumlah)
        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }
        holder._btnDelete.setOnClickListener {
            onItemClickCallBack.delData(daftar)
        }
    }

    class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val _tvItemBarang = itemView.findViewById<TextView>(R.id.tvItemBarang)
        val _tvjumlahBarang = itemView.findViewById<TextView>(R.id.tvjumlahBarang)
        val _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        val _btnEdit = itemView.findViewById<ImageButton>(R.id.btnEdit)
        val _btnDelete = itemView.findViewById<ImageButton>(R.id.btnDelete)
    }

    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    fun isiData(daftar: List<daftarBelanja>){
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }
}

