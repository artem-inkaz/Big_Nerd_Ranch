package com.example.big_nerd_ranch.CrimeIntent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.big_nerd_ranch.R
import com.example.big_nerd_ranch.model.Crime

private const val TAG1 = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private val crimeListViewModel: CrimeListViewModel by viewModels()
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG1,"Total crimes: ${crimeListViewModel.crimes.size}")
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

        updateUI()

        return view
    }

    // 9/12
    private fun updateUI(){
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    // 9.9                                                                       9.15
    private inner class CrimeHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var crime: Crime
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
            dateTextView.text = this.crime.date.toString()
        }
        // 9.15
        override fun onClick(v: View?) {
            Toast.makeText(context,"${crime.title} pressed", Toast.LENGTH_SHORT).show()
        }
    }
    // 9.11
    private inner  class CrimeAdapter(var crimes: List<Crime>):
    RecyclerView.Adapter<CrimeHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
           val view = layoutInflater.inflate(R.layout.list_item_crime,parent,false)

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