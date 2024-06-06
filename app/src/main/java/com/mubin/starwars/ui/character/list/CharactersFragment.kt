package com.mubin.starwars.ui.character.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mubin.starwars.R
import com.mubin.starwars.base.utils.Constants.CHARACTERS
import com.mubin.starwars.base.utils.RecyclerViewLazyLoading
import com.mubin.starwars.base.utils.attachSpeedyLayoutManager
import com.mubin.starwars.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(){

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var lazyLoading: RecyclerViewLazyLoading
    private var characterAdapter: CharacterListAdapter = CharacterListAdapter()
    private val vm by viewModels<CharacterListViewModel>()

    private val loadMoreListener = object : RecyclerViewLazyLoading.Listener {
        override fun loadMore() {
            if (vm.next.value != null) {
                vm.getCharacters(vm.next.value ?: CHARACTERS)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCharactersBinding.inflate(layoutInflater).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setUpObserver()

    }

    override fun onResume() {
        super.onResume()
        lazyLoading.registerScrollListener(binding.charactersRv, loadMoreListener)
    }

    override fun onPause() {
        super.onPause()
        lazyLoading.removeListener()
        viewModelStore.clear()
    }

    private fun initView() {
        lazyLoading = RecyclerViewLazyLoading.getInstance()
        binding.charactersRv.attachSpeedyLayoutManager(VERTICAL, false)
        with(binding.charactersRv) {
            adapter = characterAdapter
            isNestedScrollingEnabled = false
            setHasFixedSize(false)
        }
    }

    private fun setUpObserver() {
        vm.apply {

            getCharacters(CHARACTERS)

            isLoading.observe(viewLifecycleOwner) { isLoading ->

                if (isLoading) {
                    if (next.value.isNullOrEmpty()) {
                        binding.progressBarLoadingMore.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                        binding.countTv.visibility = View.GONE
                        binding.charactersRv.visibility = View.GONE
                    } else {
                        binding.progressBarLoadingMore.visibility = View.VISIBLE
                    }

                } else {
                    binding.progressBarLoadingMore.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.countTv.visibility = View.VISIBLE
                    binding.charactersRv.visibility = View.VISIBLE
                }

            }

            count.observe(viewLifecycleOwner) { count ->
                val textToSet = if ((count ?: 0) > 1) {
                    "${count.toString()} Characters Found"
                } else {
                    "${count.toString()} Character Found"
                }
                binding.countTv.text = textToSet
            }

            characterList.observe(viewLifecycleOwner) { list ->

                if (list != null) {
                    characterAdapter.addDataSet(list) {
                        Log.d("loadData", "success $it")
                    }
                }

            }

        }
    }

}