package com.github.lilzulf.masaya.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lilzulf.masaya.Adapter.TargetAdapter
import com.github.lilzulf.masaya.Data.EndPoint
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.DataItem
import com.github.lilzulf.masaya.Object.TargetResponse

import com.github.lilzulf.masaya.R
import com.github.lilzulf.masaya.Util.dismissLoading
import com.github.lilzulf.masaya.Util.showLoading
import com.github.lilzulf.masaya.Util.tampilToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_list_oty.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ListOtyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_oty, container, false)
    }override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTransaksi()
    }
    private fun getTransaksi() {
        showLoading(activity!!, swipeRefreshLayout)
        val TransaksiModel = ServiceRequest.get().doTarget(
            "1"
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
                    tampilGithubUser(response.body()!!.data!!)
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
    private fun tampilGithubUser(githubUsers: List<DataItem>) {
        rv_listTarget.layoutManager = LinearLayoutManager(context)
        rv_listTarget.adapter = TargetAdapter(context!!, githubUsers) {
        }
    }

}
