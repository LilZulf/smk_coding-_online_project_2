package com.github.lilzulf.masaya.Fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lilzulf.masaya.Adapter.GrateAdapeter3
import com.github.lilzulf.masaya.Adapter.TargetAdapter
import com.github.lilzulf.masaya.Adapter.TargetAdapter2
import com.github.lilzulf.masaya.AddTarget
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.DataItem
import com.github.lilzulf.masaya.Object.MyGrateModel
import com.github.lilzulf.masaya.Object.MyTargetModel
import com.github.lilzulf.masaya.Object.TargetResponse
import com.github.lilzulf.masaya.R
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.dismissLoading
import com.github.lilzulf.masaya.Util.showLoading
import com.github.lilzulf.masaya.Util.tampilToast
import com.github.lilzulf.masaya.viewmodel.ListOtyFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_list_oty.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class ListOtyFragment : Fragment(){
    private var data: SharedPreferences? = null
    lateinit var ref : DatabaseReference
    lateinit var auth : FirebaseAuth
    var dataTarget : ArrayList<MyTargetModel> = ArrayList()
    var dataTarget2: MutableList<MyTargetModel> = ArrayList()
    private val viewModel by viewModels<ListOtyFragmentViewModel>()
    private var adapter: TargetAdapter2? = null

    companion object {
        val REQUEST_CODE = 100
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_oty, container, false)
    }override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = SharedPreferences(activity!!)
        //getTransaksi()+++++
        getData()
        init()
        rv_listTarget.layoutManager = LinearLayoutManager(context)
        viewModel.init(requireContext());
        viewModel.allMyTarget.observe(viewLifecycleOwner, Observer { myTarget ->
// Update the cached copy of the words in the adapter.
           // myFriends?.let { adapter?.setData(it) }
            myTarget?.let {
                //adapter?.updateItems(it)
                rv_listTarget.adapter = TargetAdapter2(context!!, it){}
                Log.d("Mytarget",it.toString())
                dismissLoading(swipeRefreshLayout)
            }

        })

        if(data!!.getString("MODE").equals("ONLINE")){
            rl_target.setOnClickListener {
                navigasiAddTarget()
            }
            btYear.setOnClickListener {
                rvPicker.visibility = View.VISIBLE
                rv_listTarget.visibility = View.GONE
                rl_target.visibility = View.GONE
                setMinsPicker()
            }
            btOk.setOnClickListener {
                rvPicker.visibility = View.GONE
                rl_target.visibility = View.VISIBLE
                rv_listTarget.visibility = View.VISIBLE
                getData()
            }
        }else{
            rl_target.setOnClickListener {
               tampilToast(context!!,"Anda mode offline")
            }
            btYear.setOnClickListener {
                tampilToast(context!!,"Anda mode offline")
            }
            btOk.setOnClickListener {
                tampilToast(context!!,"Anda mode offline")
            }
        }

    }
    private fun init(){
        Log.d("dataTargert2",dataTarget.toString())
        rv_listTarget.layoutManager = LinearLayoutManager(context)
        //adapter = TargetAdapter2(requireContext(), dataTarget2)
        //rv_listTarget.adapter = adapter
        rv_listTarget.adapter = TargetAdapter2(context!!, dataTarget) {
        }
        //adapter?.listener = this
    }

    private fun getTransaksi() {
        showLoading(activity!!, swipeRefreshLayout)
        val TransaksiModel = ServiceRequest.get().doTarget(
            data!!.getString("ID_USER").toString(),
            btYear.text.toString()
        )
        TransaksiModel.enqueue(object : Callback<TargetResponse> {
            override fun onFailure(call: Call<TargetResponse>, t: Throwable) {
                dismissLoading(swipeRefreshLayout)
                tampilToast(activity!!, t.message!!)
            }

            override fun onResponse(call: Call<TargetResponse>, response: Response<TargetResponse>) {
                dismissLoading(swipeRefreshLayout)
                if (response.body()!!.code == 200) {
                    tampilToast(activity!!,response.body()!!.message!!)
                    tampilTarget(response.body()!!.data!!)

                } else {
                   tampilToast(activity!!, response.body()!!.message!!)
                }
            }

        })
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
    private fun tampilTarget(githubUsers: List<DataItem>) {
        rv_listTarget.layoutManager = LinearLayoutManager(context)
        rv_listTarget.adapter = TargetAdapter(context!!, githubUsers) {
        }
    }
    private fun navigasiAddTarget(){
        val intent = Intent(activity!!, AddTarget::class.java)
        intent.putExtra("year",btYear.text.toString())
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                //getTransaksi()
            }else{
                tampilToast(activity!!,"Tidak jadi")
            }
        }
    }

    private fun setMinsPicker() {
        val years = arrayOf("2020", "2021", "2022", "2023","2024","2025")

        val ml = years.size
        yearPicker.setMinValue(2020)
        yearPicker.setMaxValue(2050)
        yearPicker.setOnValueChangedListener(OnValueChangeListener { numberPicker, i, i1 ->
            val valuePicker1: Int = yearPicker.getValue()
            Log.d("picker value", valuePicker1.toString())
            btYear.text = yearPicker.value.toString()
            btYear.isEnabled = true
        })
    }
    private fun getData() {
        //Mendapatkan Referensi Database
        showLoading(activity!!, swipeRefreshLayout)
        val year = btYear.text.toString()
        auth = FirebaseAuth.getInstance()
        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()
        ref = FirebaseDatabase.getInstance().getReference()
        ref
            .child(getUserID)
            .child( "Target" )
            .orderByChild("date")
            .equalTo(year)
            .addValueEventListener( object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //dismissLoading(swipeRefreshLayout)
                Toast.makeText(context!!, "Database Error yaa..." ,
                    Toast. LENGTH_SHORT ).show()
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Inisialisasi ArrayList
                dataTarget = java.util.ArrayList<MyTargetModel>()
                //dataTarget2 = ArrayList()
                for (snapshot in dataSnapshot. children ) {
                    //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                    val target = snapshot.getValue(MyTargetModel:: class . java )
                    //Mengambil Primary Key, digunakan untuk proses Update dan
                    target?.key = snapshot.key!!
                    dataTarget.add(target!!)
                }
                //Memasang Adapter pada RecyclerView
//                rv_listTarget.layoutManager = LinearLayoutManager(context)
//                rv_listTarget.adapter = TargetAdapter2(context!!, dataTarget2) {
//                }
                viewModel.insertAll(dataTarget)
                dismissLoading(swipeRefreshLayout)
            }
        })
    }



}
