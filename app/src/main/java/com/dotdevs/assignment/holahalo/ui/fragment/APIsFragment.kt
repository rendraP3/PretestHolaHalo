package com.dotdevs.assignment.holahalo.ui.fragment


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dotdevs.assignment.holahalo.R
import com.dotdevs.assignment.holahalo.adapter.ImageListAdapter
import com.dotdevs.assignment.holahalo.api.ApiClient
import com.dotdevs.assignment.holahalo.api.model.ApiResponse
import com.dotdevs.assignment.holahalo.utils.ErrorState
import com.dotdevs.assignment.holahalo.utils.NetworkState
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

/**
 * A simple [Fragment] subclass.
 */
class APIsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: ImageListAdapter
    private lateinit var apiKey: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hasOptionsMenu()

        adapter = ImageListAdapter(context)
        apiKey = getString(R.string.pixabay_api_key)
        swipeRefresh.setOnRefreshListener(this)

        initRecyclerView()
        initSpinner()
        initSearch()
    }

    private fun initRecyclerView() {
        imageList.hasFixedSize()
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        imageList.layoutManager = layoutManager
        imageList.adapter = adapter
        imageList.itemAnimator = DefaultItemAnimator()

        fetchImageData(null, null)
    }

    private fun initSpinner() {
        categoryList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            @SuppressLint("DefaultLocale")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent?.getItemAtPosition(position) != 0) {
                    val category = parent?.getItemAtPosition(position).toString().toLowerCase()

                    fetchImageData(null, category)
                }
            }

        }
    }

    private fun initSearch() {
        ed_search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                fetchImageData(ed_search.text.toString(), null)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun fetchImageData(query: String?, category: String?) {

        errorLayout.visibility = View.GONE
        imageList.visibility = View.VISIBLE
        adapter.clearItem()
        swipeRefresh.isRefreshing = true

        if (NetworkState().isNetworkConnected(context)) {
            ApiClient().service.getImage(apiKey, query, category, 50)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.d("Error", t.localizedMessage!!)
                        showError(ErrorState.OTHER)
                    }

                    override fun onResponse(
                        call: Call<ApiResponse>,
                        response: Response<ApiResponse>
                    ) {
                        swipeRefresh.isRefreshing = false
                        if (response.body() != null) {
                            val images = response.body()?.hits!!

                            adapter.addItem(images)
                        } else {
                            Log.d("Empty", response.errorBody().toString())
                            showError(ErrorState.NO_RESULT)
                        }
                    }
                })
        } else {
            showError(ErrorState.NO_CONNECTION)
        }
    }

    private fun showError(state: ErrorState) {

        imageList.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
        swipeRefresh.isRefreshing = false

        when (state) {
            ErrorState.NO_CONNECTION -> {
                errorTitle.text = getString(R.string.no_connection_title)
                errorMessage.text = getString(R.string.no_connection_message)
                errorImage.setImageResource(R.drawable.mirage_no_connection)
                errorButton.text = getString(R.string.try_again)
                errorButton.setOnClickListener { fetchImageData(null, null) }
            }
            ErrorState.NO_RESULT -> {
                errorTitle.text = getString(R.string.no_nothing_title)
                errorMessage.text = getString(R.string.no_nothing_message_1)
                errorImage.setImageResource(R.drawable.mirage_bad_gateway)
                errorButton.text = getString(R.string.try_again)
                errorButton.setOnClickListener { fetchImageData(null, null) }
            }
            else -> {
                errorTitle.text = getString(R.string.something_when_wrong_title)
                errorMessage.text = getString(R.string.something_when_wrong_message)
                errorImage.setImageResource(R.drawable.mirage_page_not_found)
                errorButton.text = getString(R.string.got_it)
                errorButton.setOnClickListener { exitProcess(0) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onRefresh() {
        fetchImageData(null, null)
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
