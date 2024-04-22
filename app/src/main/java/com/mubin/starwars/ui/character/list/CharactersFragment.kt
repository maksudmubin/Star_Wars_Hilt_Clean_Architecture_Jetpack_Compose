package com.mubin.starwars.ui.character.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mubin.starwars.base.utils.Constants.CHARACTERS
import com.mubin.starwars.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val vm: CharacterListViewModel by viewModels()

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

    private fun initView() {
        with(binding.charactersRv) {
            adapter = CharacterListAdapter()
            isNestedScrollingEnabled = false
            setHasFixedSize(false)
        }
    }

    private fun setUpObserver() {
        vm.apply {
            //getCharacters(CHARACTERS)

            isLoading.observe(viewLifecycleOwner) { isLoading ->

                if (isLoading) {
                    binding.countTv.visibility = View.GONE
                    binding.charactersRv.visibility = View.GONE
                } else {
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

                Log.d("ListCount", "${list?.size}")

            }

        }
    }

}