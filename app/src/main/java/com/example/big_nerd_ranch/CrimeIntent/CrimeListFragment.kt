package com.example.big_nerd_ranch.CrimeIntent

import android.icu.text.DateFormat.getDateTimeInstance
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.big_nerd_ranch.R
import com.example.big_nerd_ranch.model.Crime
import java.text.DateFormat.getDateTimeInstance
import java.util.*


private const val TAG1 = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private val crimeListViewModel: CrimeListViewModel by viewModels()
    private lateinit var crimeRecyclerView: RecyclerView
//    private var adapter: CrimeAdapter? = null
    // настраиваем на новый список когда они будут отображены в LiveData
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // удалили из-за LiveData
//        Log.d(TAG1, "Total crimes: ${crimeListViewModel.crimes.size}")
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView=
            view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

// удалили из-за LiveData
//        updateUI()

        crimeRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimesListLiveData.observe(// используется для регистрации наблюдателя за экземпляром LiveData
            viewLifecycleOwner,// и связи наблюдения с жизненным циклом другого компонента
            androidx.lifecycle.Observer { crimes -> // выполняется всегда когда обновляется список LiveData
                crimes?.let {
                    Log.i(TAG1, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            })
    }

// удалили updateUI() из-за LiveData
    // 9/12
//    private fun updateUI(){
//        val crimes = crimeListViewModel.crimes
//        adapter = CrimeAdapter(crimes)
//        crimeRecyclerView.adapter = adapter
//    }
    // 11.18
        private fun updateUI(crimes: List<Crime>){
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    // 9.9                                                                       9.15
    private inner class CrimeHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var crime: Crime
        val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        //9.15
        init {
            itemView.setOnClickListener(this)
        }

        // кэшируем привязанные преступления в свойства и присвоить значения свойствам
        fun bind(crime: Crime){
            this.crime = crime
            titleTextView.text = this.crime.title
            val date = Date();
//            val stringDate: String = DateFormat.getDateTimeInstance().format(date)
            dateTextView.text = DateFormat.getDateFormat(context).format(this.crime.date)
//            dateTextView.text = this.crime.date.toString()
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            }else {
                View.GONE
            }

        }
        // 9.15
        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed", Toast.LENGTH_SHORT).show()
        }
    }
    // 9.11
    private inner  class CrimeAdapter(var crimes: List<Crime>):
    RecyclerView.Adapter<CrimeHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
           val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)

            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
//            holder.apply {
//                titleTextView.text = crime.title
//                dateTextView.text = crime.date.toString()
//            }

            // 9.14 вызываем всякий раз когда RecyclerView запрашивает привязку CrimeHolder к конкретному преступлению
            holder.bind(crime)
        }

        override fun getItemCount()= crimes.size
    }

    companion object {
        fun neInstance(): CrimeListFragment{
            return CrimeListFragment()
        }
    }

}