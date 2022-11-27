package com.example.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.BriefInfo
import com.example.list.databinding.FragmentListBinding
import com.example.shared.navigation.Destination
import com.example.shared.navigation.navigateTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalPagingApi
class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModel()

    private lateinit var binding: FragmentListBinding

    private val userAdapter = UserListAdapter { navigateTo(Destination.Details(it.id)) }
    private val loadStateFooter = UserListLoadAdapter { userAdapter.retry() }
    private val adapter = userAdapter.withLoadStateFooter(
        footer = loadStateFooter
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            list.layoutManager = LinearLayoutManager(context)
            list.adapter = adapter
        }
        if (savedInstanceState == null) {
            viewModel.fetchData()
        }
//        userAdapter.addLoadStateListener {
//            loadStateFooter.loadState = it.refresh
//        }
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.data.collectLatest { state ->
                setupData(state)
//                handleUIState()
            }
        }
    }

    private suspend fun setupData(data: PagingData<BriefInfo>) {
        userAdapter.submitData(data)
    }

    private fun handleUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            userAdapter.loadStateFlow.collectLatest { loadStates ->
                loadStateFooter.loadState = loadStates.refresh
                with(binding) {
                    progress.isVisible = loadStates.refresh is LoadState.Loading
                    list.isVisible = loadStates.refresh is LoadState.NotLoading

                    error.errorText.isVisible = loadStates.refresh is LoadState.Error
//                    due to limitation of calls error with the code 403 may happen and this will lead
//                    to error message to pop. Please use api key for proper behaviour. commented out for now
                    if (loadStates.refresh is LoadState.Error && userAdapter.itemCount == 0) {
                        val errorState: LoadState.Error = loadStates.refresh as LoadState.Error
                        error.errorText.text = errorState.error.message
                    }
                }
            }
        }
    }
}
