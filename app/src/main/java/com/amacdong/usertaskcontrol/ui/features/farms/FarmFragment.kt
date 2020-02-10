package com.amacdong.usertaskcontrol.ui.features.farms


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amacdong.data.model.FarmModel

import com.amacdong.usertaskcontrol.R
import com.amacdong.usertaskcontrol.ui.features.farms.adapters.ListFarmsAdapter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_farm.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class FarmFragment : Fragment(), FarmContract.View {

    private val viewModel: FarmViewModel by currentScope.viewModel(this)

    private lateinit var adapter: ListFarmsAdapter
    private lateinit var listFarms: ArrayList<FarmModel>

    companion object {
        const val TAG = "FarmFragment"
        fun newInstance(): FarmFragment {
            return FarmFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_farm, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel.viewLoaded()
    }

    override fun initializeViews() {
        rvFarms.layoutManager = LinearLayoutManager(context!!)
    }
    override fun updateUi(model: FarmViewModel.UiModel) = when (model) {
        is FarmViewModel.UiModel.ShowFarms -> {
            listFarms = model.listFarms as ArrayList<FarmModel>
            adapter = ListFarmsAdapter(
                context!!,
                listFarms
            ) { farmItem: FarmModel, _: View ->
                var farmName = if(farmItem.farmName.isNullOrEmpty())
                    getString(R.string.not_found_farm_name)
                else
                    farmItem.farmName.toString()

                val toast = Toasty.info(
                    context!!,
                    farmName,
                    Toast.LENGTH_LONG,
                    true
                )
                toast.show()
            }
            rvFarms.adapter = adapter
        }
        is

        FarmViewModel.UiModel.Forbbiden -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.access_forbidden),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()

        }
        FarmViewModel.UiModel.NetWorkError -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.error_connection),
                Toast.LENGTH_LONG,
                true
            )

            toast.show()
        }
    }
}

