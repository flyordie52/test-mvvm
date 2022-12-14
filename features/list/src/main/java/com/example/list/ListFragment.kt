package com.example.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.UserBaseInfo
import com.example.list.databinding.FragmentListBinding
import com.example.shared.navigation.Destination
import com.example.shared.navigation.navigateTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalPagingApi
class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModel()

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding!!

    private val userAdapter = UserListAdapter { navigateTo(Destination.Details(it.id)) }
    private val loadStateFooter = UserListLoadAdapter { userAdapter.retry() }
    private val adapter = userAdapter.withLoadStateFooter(
        footer = loadStateFooter
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            list.layoutManager = LinearLayoutManager(context)
            list.adapter = adapter
        }

        userAdapter.addLoadStateListener {
            loadStateFooter.loadState = it.refresh
        }
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.await().collectLatest { state ->
                    setupData(state)
                }
            }
        }
    }

    private suspend fun setupData(data: PagingData<UserBaseInfo>) {
        userAdapter.submitData(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
