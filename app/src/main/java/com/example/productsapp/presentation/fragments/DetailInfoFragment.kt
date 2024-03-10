package com.example.productsapp.presentation.fragments

import android.content.Context
import android.graphics.Paint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.productsapp.R
import com.example.productsapp.databinding.FragmentDetailInfoBinding
import com.example.productsapp.presentation.ProductApplication
import com.example.productsapp.presentation.viewmodels.DetailInfoViewModel
import com.example.productsapp.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class DetailInfoFragment : Fragment() {


    private val args by navArgs<DetailInfoFragmentArgs>()
    private var _binding: FragmentDetailInfoBinding? = null
    private val binding: FragmentDetailInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailInfoBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: DetailInfoViewModel

    private val component by lazy {
        (requireActivity().application as ProductApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailInfoViewModel::class.java]
        observe()
    }

    private fun observe() {
        viewModel.getProduct(args.productEntity)
        binding.customToolbar.imageView.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.product.observe(viewLifecycleOwner) {
            binding.customToolbar.tvScreenName.text = it.brand

                binding.tvTitle.text = it.title
            binding.tvDescription.text = it.description
            binding.tvPrice.text = requireContext().getString(R.string.price, it.price.toString())
            binding.tvFullPrice.paintFlags = binding.tvFullPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvFullPrice.text = requireContext().getString(
                R.string.price,
                (it.price / (1 - it.discountPercentage / 100)).toInt().toString()
            )
            binding.tvDiscountPrecent.text =
                requireContext().getString(R.string.disc, it.discountPercentage.toString())
            Glide.with(requireActivity()).load(it.thumbnail).into(binding.ivThumbnail)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}